package com.bankjunk;

import com.google.common.html.HtmlEscapers;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.AsyncBufferedImage;
import net.runelite.client.util.LinkBrowser;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Side panel that lists all items currently flagged as junk.
 *
 * <p>Items are grouped by associated quest name, with "Upgrades / Superseded" for
 * YELLOW tier upgrade items and "Miscellaneous" for anything without a quest reference.</p>
 *
 * <p>Group headers are collapsible — click to expand/collapse, right-click to whitelist
 * all items in the group. A search bar filters rows in real time. Right-clicking a row
 * offers "Mark as Keep" (whitelist), "Remove from junk list", and "View on Wiki".</p>
 */
@Slf4j
public class BankJunkPanel extends PluginPanel
{
    private static final Color ROW_HOVER_COLOR = new Color(50, 52, 55);
    private static final Color GROUP_HEADER_BG = new Color(38, 38, 38);
    private static final Color GROUP_HEADER_FG = new Color(180, 180, 180);

    // Client property keys used to tag components for filtering / updating
    private static final String PROP_ITEM_NAME   = "bjItemName";
    private static final String PROP_GROUP_NAME  = "bjGroupName";
    /** Stored on group headers so applyFilter() can update the count label live. */
    private static final String PROP_COUNT_LABEL = "bjCountLabel";

    private static final String PROP_TIER    = "bjTier";
    private static final String PROP_DIVIDER = "bjDivider";
    private static final Color  TOAST_BG   = new Color(35, 38, 42);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private static final String KEPT_GROUP_NAME  = "♥ Kept Items";
    private static final Color  KEPT_HEADER_BG   = new Color(28, 45, 28);
    private static final Color  KEPT_HEADER_FG   = new Color(120, 200, 120);
    private static final Color  WARN_COLOR        = new Color(255, 200, 0);

    private final BankJunkPlugin plugin;
    private final ItemManager    itemManager;

    private final JPanel          listPanel    = new JPanel();
    private final JLabel         summaryLabel = new JLabel();
    private final JTextField     searchField  = new JTextField();

    /** Promoted to field so refresh() can read/restore scroll position. */
    private JScrollPane scroll;

    /** Group names currently collapsed. Persists across refreshes. */
    private final Set<String> collapsedGroups = new HashSet<>();

    // H7: tier filter dropdown
    private final JComboBox<String> tierFilter = new JComboBox<>();

    // H7: sort dropdown
    private final JComboBox<String> sortFilter = new JComboBox<>();

    // Cached last refresh data — used to re-render when sort changes without a rescan
    private Map<Integer, JunkEntry> lastFlagged       = Collections.emptyMap();
    private Map<Integer, Integer>   lastQuantities     = Collections.emptyMap();
    private Set<Integer>            lastQuestWarnings  = Collections.emptySet();
    private Map<Integer, String>    lastWhitelistNames = Collections.emptyMap();

    // H1: scan timestamp
    private final JLabel timestampLabel = new JLabel();

    // H3: toast notification for whitelist actions
    private JPanel toastPanel;
    private JLabel toastLabel;
    private Timer  toastTimer;
    private Runnable lastUndoAction = null;

    // H8: legend collapsible body reference
    private JPanel  legendBody;
    private boolean legendVisible = false;  // H8: collapsed by default; user can expand

    @Inject
    BankJunkPanel(BankJunkPlugin plugin, ItemManager itemManager)
    {
        super(false);
        this.plugin      = plugin;
        this.itemManager = itemManager;
        buildUI();
    }

    // -------------------------------------------------------------------------
    // Build static UI skeleton
    // -------------------------------------------------------------------------

    private void buildUI()
    {
        setLayout(new BorderLayout(0, 4));
        setBorder(new EmptyBorder(8, 8, 8, 8));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Header: title + Scan Now button
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ColorScheme.DARK_GRAY_COLOR);
        header.setBorder(new EmptyBorder(0, 0, 4, 0));

        JLabel title = new JLabel("Dumb Old Man");
        title.setForeground(Color.WHITE);
        title.setFont(FontManager.getRunescapeBoldFont());
        header.add(title, BorderLayout.NORTH);

        summaryLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        summaryLabel.setFont(FontManager.getRunescapeSmallFont());
        summaryLabel.setText("Open your bank to scan.");

        // H1: timestamp label below summary
        timestampLabel.setForeground(new Color(120, 120, 120));
        timestampLabel.setFont(FontManager.getRunescapeSmallFont());
        timestampLabel.setText("");

        JPanel labelStack = new JPanel();
        labelStack.setLayout(new BoxLayout(labelStack, BoxLayout.Y_AXIS));
        labelStack.setBackground(ColorScheme.DARK_GRAY_COLOR);
        labelStack.add(summaryLabel);
        labelStack.add(timestampLabel);
        header.add(labelStack, BorderLayout.CENTER);

        JButton scanButton = new JButton("Scan Now");
        scanButton.setFont(FontManager.getRunescapeSmallFont());
        scanButton.setForeground(Color.WHITE);
        scanButton.setBackground(new Color(55, 58, 62));
        scanButton.setBorderPainted(true);
        scanButton.setFocusPainted(false);
        scanButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        scanButton.setToolTipText("Force a fresh bank scan");
        scanButton.addActionListener(e -> plugin.triggerManualScan());
        header.add(scanButton, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Content panel: legend + search bar + scrollable list
        JPanel content = new JPanel(new BorderLayout(0, 4));
        content.setBackground(ColorScheme.DARK_GRAY_COLOR);

        // Top section: legend stacked above search bar
        JPanel topSection = new JPanel(new BorderLayout(0, 4));
        topSection.setBackground(ColorScheme.DARK_GRAY_COLOR);
        topSection.add(buildLegend(), BorderLayout.NORTH);
        topSection.add(buildSearchBar(), BorderLayout.SOUTH);
        content.add(topSection, BorderLayout.NORTH);

        // Scrollable item list
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

        scroll = new JScrollPane(listPanel,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(ColorScheme.DARK_GRAY_COLOR);
        scroll.getViewport().setBackground(ColorScheme.DARK_GRAY_COLOR);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        content.add(scroll, BorderLayout.CENTER);

        // H3: toast panel at bottom of content area (hidden until needed)
        toastPanel = buildToastPanel();
        content.add(toastPanel, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    // H6 + H8: collapsible legend with styled tier labels instead of colored swatches
    private JPanel buildLegend()
    {
        JPanel wrapper = new JPanel(new BorderLayout(0, 0));
        wrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        wrapper.setBorder(new EmptyBorder(2, 4, 2, 4));

        // Header row: collapse toggle (LEFT, consistent with group headers) + "Legend" label
        JPanel legendHeader = new JPanel(new BorderLayout());
        legendHeader.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        legendHeader.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // H4: toggle on LEFT to match group header collapse arrows
        JLabel toggleBtn = new JLabel(legendVisible ? "▼" : "▶");
        toggleBtn.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        toggleBtn.setFont(FontManager.getRunescapeSmallFont());
        toggleBtn.setBorder(new EmptyBorder(0, 0, 0, 4));
        legendHeader.add(toggleBtn, BorderLayout.WEST);

        JLabel legendTitle = new JLabel("Legend");
        legendTitle.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        legendTitle.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.BOLD));
        legendHeader.add(legendTitle, BorderLayout.CENTER);

        wrapper.add(legendHeader, BorderLayout.NORTH);

        // Body: one row per JunkTier, then heart icon legend (H6)
        legendBody = new JPanel();
        legendBody.setLayout(new BoxLayout(legendBody, BoxLayout.Y_AXIS));
        legendBody.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        legendBody.setBorder(new EmptyBorder(2, 0, 2, 0));
        for (JunkTier tier : JunkTier.values())
        {
            legendBody.add(legendTierRow(tier));
        }
        legendBody.add(legendKeptRow());
        legendBody.setVisible(legendVisible);  // H8: collapsed by default
        wrapper.add(legendBody, BorderLayout.CENTER);

        // Toggle click handler (H4: uses ▼/▶ consistent with group headers)
        MouseAdapter toggleHandler = new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                legendVisible = !legendVisible;
                legendBody.setVisible(legendVisible);
                toggleBtn.setText(legendVisible ? "▼" : "▶");
                wrapper.revalidate();
                wrapper.repaint();
            }
        };
        legendHeader.addMouseListener(toggleHandler);
        legendTitle.addMouseListener(toggleHandler);
        toggleBtn.addMouseListener(toggleHandler);

        return wrapper;
    }

    private JPanel buildSearchBar()
    {
        // H8: three compact rows — search+tier combined, then sort below
        JPanel wrapper = new JPanel(new BorderLayout(0, 2));
        wrapper.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        wrapper.setBorder(new EmptyBorder(4, 4, 4, 4));

        // ── Row 1: [🔍] [search field] [✕] [tier dropdown] ──────────────────
        JPanel searchRow = new JPanel(new BorderLayout(4, 0));
        searchRow.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel icon = new JLabel("🔍");
        icon.setFont(FontManager.getRunescapeSmallFont());
        searchRow.add(icon, BorderLayout.WEST);

        searchField.setBackground(ColorScheme.DARK_GRAY_COLOR);
        searchField.setForeground(Color.WHITE);
        searchField.setCaretColor(Color.WHITE);
        searchField.setFont(FontManager.getRunescapeSmallFont());
        searchField.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
        searchField.setToolTipText("Filter items by name");
        searchRow.add(searchField, BorderLayout.CENTER);

        // Right of search: clear button + tier dropdown in a sub-panel
        JPanel searchRight = new JPanel(new BorderLayout(2, 0));
        searchRight.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JButton clearBtn = new JButton("✕");
        clearBtn.setFont(FontManager.getRunescapeSmallFont());
        clearBtn.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        clearBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        clearBtn.setBorderPainted(false);
        clearBtn.setFocusPainted(false);
        clearBtn.setMargin(new java.awt.Insets(0, 1, 0, 1));  // 1px side padding — just enough for ✕ to render
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.setToolTipText("Clear search");
        clearBtn.addActionListener(e -> searchField.setText(""));
        searchRight.add(clearBtn, BorderLayout.WEST);

        tierFilter.addItem("All Tiers");
        for (JunkTier tier : JunkTier.values())
        {
            tierFilter.addItem(tier.displayName);
        }
        tierFilter.addItem("Whitelisted");
        tierFilter.setMaximumRowCount(tierFilter.getItemCount());
        tierFilter.setBackground(ColorScheme.DARK_GRAY_COLOR);
        tierFilter.setForeground(Color.WHITE);
        tierFilter.setFont(FontManager.getRunescapeSmallFont());
        tierFilter.setToolTipText("Filter items by junk tier");
        tierFilter.addActionListener(e -> applyFilter());
        searchRight.add(tierFilter, BorderLayout.EAST);

        searchRow.add(searchRight, BorderLayout.EAST);

        // Live filter — fires on every keystroke
        searchField.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override public void insertUpdate(DocumentEvent e)  { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e)  { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        wrapper.add(searchRow, BorderLayout.NORTH);

        // ── Row 2: [sort dropdown (full width)] ──────────────────────────────
        sortFilter.addItem("Name A → Z");
        sortFilter.addItem("Name Z → A");
        sortFilter.addItem("Qty High → Low");
        sortFilter.addItem("Qty Low → High");
        sortFilter.setMaximumRowCount(sortFilter.getItemCount());
        sortFilter.setBackground(ColorScheme.DARK_GRAY_COLOR);
        sortFilter.setForeground(Color.WHITE);
        sortFilter.setFont(FontManager.getRunescapeSmallFont());
        sortFilter.setToolTipText("Sort items within each group");
        sortFilter.addActionListener(e -> SwingUtilities.invokeLater(() ->
            refresh(lastFlagged, lastQuantities, lastQuestWarnings, lastWhitelistNames)));

        wrapper.add(sortFilter, BorderLayout.SOUTH);

        return wrapper;
    }

    // -------------------------------------------------------------------------
    // Refresh (called from plugin on EDT)
    // -------------------------------------------------------------------------

    /**
     * Rebuilds the item list grouped by quest. Always called on the EDT.
     *
     * @param flagged        map of item ID → JunkEntry for items to display
     * @param quantities     map of item ID → bank quantity (stack count)
     * @param questWarnings  item IDs whose {@code diaryWarningQuest} is not yet complete
     * @param whitelistNames map of whitelisted item ID → item name for the Kept Items section
     */
    public void refresh(Map<Integer, JunkEntry> flagged, Map<Integer, Integer> quantities,
                        Set<Integer> questWarnings, Map<Integer, String> whitelistNames)
    {
        // Cache data so the sort dropdown can re-render without a full rescan
        lastFlagged       = flagged;
        lastQuantities    = quantities;
        lastQuestWarnings = questWarnings;
        lastWhitelistNames = whitelistNames;

        // Preserve the current scroll offset so the viewport doesn't jump on rescan.
        final int savedScroll = scroll.getVerticalScrollBar().getValue();

        listPanel.removeAll();
        // Count distinct bank slots (item IDs), not map entries: dual-tier / dual-category
        // items (e.g. a STASH item valid in two clue tiers, or a Team cape in STASH + POH)
        // occupy several map entries under synthetic keys but only one bank slot.
        int count = (int) flagged.values().stream()
            .map(je -> je.itemId).distinct().count();

        if (count == 0)
        {
            JLabel empty = new JLabel("Open your bank to scan.");
            empty.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
            empty.setFont(FontManager.getRunescapeSmallFont());
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);
            empty.setBorder(new EmptyBorder(4, 0, 0, 0));
            listPanel.add(empty);
        }
        else
        {
            List<JunkEntry> sorted = new ArrayList<>(flagged.values());

            // H7: apply selected sort within each group
            String sel = (String) sortFilter.getSelectedItem();
            Comparator<JunkEntry> itemSort;
            if ("Name Z → A".equals(sel))
            {
                itemSort = (a, b) -> b.name.compareTo(a.name);
            }
            else if ("Qty High → Low".equals(sel))
            {
                itemSort = (a, b) -> Integer.compare(
                    quantities.getOrDefault(b.itemId, 1),
                    quantities.getOrDefault(a.itemId, 1));
            }
            else if ("Qty Low → High".equals(sel))
            {
                itemSort = (a, b) -> Integer.compare(
                    quantities.getOrDefault(a.itemId, 1),
                    quantities.getOrDefault(b.itemId, 1));
            }
            else // "Name A → Z" (default)
            {
                itemSort = (a, b) -> a.name.compareTo(b.name);
            }

            sorted.sort(Comparator
                .<JunkEntry, String>comparing(this::groupSortKey)
                .thenComparing(itemSort));

            Map<String, List<JunkEntry>> groups = new LinkedHashMap<>();
            for (JunkEntry entry : sorted)
            {
                groups.computeIfAbsent(getGroupName(entry), k -> new ArrayList<>()).add(entry);
            }

            boolean firstGroup = true;
            for (Map.Entry<String, List<JunkEntry>> group : groups.entrySet())
            {
                if (!firstGroup)
                {
                    listPanel.add(buildGroupDivider());
                }
                firstGroup = false;

                String          groupName = group.getKey();
                List<JunkEntry> entries   = group.getValue();
                boolean         collapsed = collapsedGroups.contains(groupName);

                // Build body panel, tag rows for filtering.
                // makeGroupBody() overrides prevent BoxLayout (in listPanel) from
                // compressing this panel below its preferred height, clipping bottom rows.
                JPanel groupBody = makeGroupBody();
                groupBody.setLayout(new BoxLayout(groupBody, BoxLayout.Y_AXIS));
                groupBody.setBackground(ColorScheme.DARK_GRAY_COLOR);
                groupBody.setAlignmentX(Component.LEFT_ALIGNMENT);
                groupBody.putClientProperty(PROP_GROUP_NAME, groupName);
                groupBody.setVisible(!collapsed);

                // H8: suppress redundant tier label when every row in the group shares the same tier
                boolean singleTierGroup = entries.stream().allMatch(e -> e.tier == entries.get(0).tier);
                for (JunkEntry entry : entries)
                {
                    int qty = quantities.getOrDefault(entry.itemId, 1);
                    boolean warn = questWarnings.contains(entry.itemId);
                    JPanel row = buildRow(entry, qty, warn, singleTierGroup);
                    // Tag with lowercase name so applyFilter() can match without re-allocating
                    row.putClientProperty(PROP_ITEM_NAME, entry.name.toLowerCase());
                    groupBody.add(row);
                }

                JPanel groupHeader = buildGroupHeader(groupName, entries, collapsed, groupBody);
                listPanel.add(groupHeader);
                listPanel.add(groupBody);
            }

            // --- Kept Items section (always shown, even when bank is closed) ---
            if (!whitelistNames.isEmpty() && !groups.isEmpty())
            {
                listPanel.add(buildGroupDivider());
            }
            if (!whitelistNames.isEmpty())
            {
                boolean keptCollapsed = collapsedGroups.contains(KEPT_GROUP_NAME);

                JPanel keptBody = makeGroupBody();
                keptBody.setLayout(new BoxLayout(keptBody, BoxLayout.Y_AXIS));
                keptBody.setBackground(ColorScheme.DARK_GRAY_COLOR);
                keptBody.setAlignmentX(Component.LEFT_ALIGNMENT);
                keptBody.putClientProperty(PROP_GROUP_NAME, KEPT_GROUP_NAME);
                keptBody.setVisible(!keptCollapsed);

                // Sort kept items alphabetically
                List<Map.Entry<Integer, String>> keptSorted = new ArrayList<>(whitelistNames.entrySet());
                keptSorted.sort(Comparator.comparing(Map.Entry::getValue));

                for (Map.Entry<Integer, String> ke : keptSorted)
                {
                    JPanel row = buildKeptItemRow(ke.getKey(), ke.getValue());
                    row.putClientProperty(PROP_ITEM_NAME, ke.getValue().toLowerCase());
                    keptBody.add(row);
                }

                JPanel keptHeader = buildKeptGroupHeader(keptSorted.size(), keptCollapsed,
                    keptBody, new ArrayList<>(whitelistNames.keySet()));
                listPanel.add(keptHeader);
                listPanel.add(keptBody);
            }

        }

        listPanel.revalidate();
        listPanel.repaint();

        final String summaryText = (count == 0)
            ? "No junk found — bank looks clean!"
            : count + " slot" + (count == 1 ? "" : "s") + " freed";
        summaryLabel.setText(summaryText);
        scroll.getVerticalScrollBar().setValue(savedScroll);

        // Always call applyFilter() — even with empty field.
        // When active=false it restores any rows that were hidden by a
        // prior filter and clears any stale invisible-row state left over
        // from a previous scan cycle.
        applyFilter();
    }

    // -------------------------------------------------------------------------
    // Live search / filter
    // -------------------------------------------------------------------------

    /**
     * Walks all group header/body pairs in listPanel and shows or hides rows
     * based on the current search text. When a filter is active, collapsed groups
     * are temporarily expanded so matching items are always reachable.
     */
    private void applyFilter()
    {
        String raw     = searchField.getText().trim().toLowerCase();
        boolean active = !raw.isEmpty();

        // H7: tier filter
        String selectedTier     = (String) tierFilter.getSelectedItem();
        boolean tierActive      = selectedTier != null && !"All Tiers".equals(selectedTier);
        boolean filterWhitelist = "Whitelisted".equals(selectedTier);

        // Hide dividers when any filter is active — otherwise they create blank gaps
        // above groups that happen to be the first visible one after hidden groups.
        boolean anyFilter = active || tierActive;
        for (Component comp : listPanel.getComponents())
        {
            if (comp instanceof JPanel
                && Boolean.TRUE.equals(((JPanel) comp).getClientProperty(PROP_DIVIDER)))
            {
                comp.setVisible(!anyFilter);
            }
        }

        // Walk all top-level children. refresh() inserts a divider JPanel *between*
        // each group pair (header, body) — so the layout is:
        //   header0, body0, DIVIDER, header1, body1, DIVIDER, header2, body2 ...
        // We identify real group bodies by PROP_GROUP_NAME and match them to the
        // preceding sibling (the header). Stepping blindly by 2 skips dividers
        // incorrectly, which is why this loop uses a manual scan instead.
        Component[] top = listPanel.getComponents();
        JPanel pendingHeader = null;
        for (Component comp : top)
        {
            if (!(comp instanceof JPanel))
            {
                pendingHeader = null;
                continue;
            }
            JPanel panel = (JPanel) comp;
            String groupName = (String) panel.getClientProperty(PROP_GROUP_NAME);

            if (groupName == null)
            {
                // Either a group header (no PROP_GROUP_NAME) or a divider.
                // Treat as the potential header for the next body we find.
                pendingHeader = panel;
                continue;
            }

            // panel is a group body; pendingHeader is its header.
            if (pendingHeader == null) continue;  // shouldn't happen; defensive guard
            JPanel groupHeader = pendingHeader;
            JPanel groupBody   = panel;
            pendingHeader = null;  // consumed

            int visibleRows = 0;
            int totalRows   = 0;
            for (Component rowComp : groupBody.getComponents())
            {
                if (!(rowComp instanceof JPanel))
                {
                    continue;
                }
                totalRows++;
                JPanel row      = (JPanel) rowComp;
                String itemName = (String) row.getClientProperty(PROP_ITEM_NAME);
                boolean textMatch = !active || (itemName != null && itemName.contains(raw));
                boolean tierMatch;
                if (filterWhitelist)
                {
                    // Kept item rows have no PROP_TIER; junk rows do.
                    tierMatch = !(row.getClientProperty(PROP_TIER) instanceof JunkTier);
                }
                else
                {
                    tierMatch = !tierActive
                        || (row.getClientProperty(PROP_TIER) instanceof JunkTier
                            && ((JunkTier) row.getClientProperty(PROP_TIER)).displayName
                                .equals(selectedTier));
                }
                boolean match = textMatch && tierMatch;
                row.setVisible(match);
                if (match) visibleRows++;
            }

            // Update the live count label in the group header
            JLabel countLbl = (JLabel) groupHeader.getClientProperty(PROP_COUNT_LABEL);
            if (countLbl != null)
            {
                if ((active || tierActive) && visibleRows < totalRows)
                {
                    countLbl.setText("(" + visibleRows + "/" + totalRows + ")");
                }
                else
                {
                    countLbl.setText("(" + totalRows + ")");
                }
            }

            if (active || tierActive)
            {
                // Override collapse state so users can always reach filtered results
                groupHeader.setVisible(visibleRows > 0);
                groupBody.setVisible(visibleRows > 0);
            }
            else
            {
                // Restore normal collapse state
                groupHeader.setVisible(true);
                boolean collapsed = collapsedGroups.contains(groupName);
                groupBody.setVisible(!collapsed);
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // -------------------------------------------------------------------------
    // Grouping helpers
    // -------------------------------------------------------------------------

    private String getGroupName(JunkEntry entry)
    {
        if (entry.requiredQuest != null)
        {
            return entry.requiredQuest.getName();
        }
        switch (entry.tier)
        {
            case POH_STORAGE:   return pohSubGroupName(entry.itemId);
            case STASH_STORAGE: return stashSubGroupName(entry);
            case HOLIDAY:       return "Holiday / Event Items";
            case CUSTOM:        return "Custom Junk";
            case YELLOW:        return "Upgrades / Superseded";
            default:            return "Miscellaneous";
        }
    }

    /**
     * Maps a STASH_STORAGE entry to its clue-tier sub-group name.
     * If the entry has a {@code stashGroupName} set by the plugin (per-unit fill tracking),
     * that value is used directly. Otherwise falls back to the lowest-tier static set lookup.
     */
    private String stashSubGroupName(JunkEntry entry)
    {
        if (entry.getStashGroupName() != null) return entry.getStashGroupName();
        int itemId = entry.itemId;
        if (StashDatabase.BEGINNER_STASH_ITEMS.contains(itemId)) return "Beginner STASH";
        if (StashDatabase.EASY_STASH_ITEMS.contains(itemId))     return "Easy STASH";
        if (StashDatabase.MEDIUM_STASH_ITEMS.contains(itemId))   return "Medium STASH";
        if (StashDatabase.HARD_STASH_ITEMS.contains(itemId))     return "Hard STASH";
        if (StashDatabase.ELITE_STASH_ITEMS.contains(itemId))    return "Elite STASH";
        if (StashDatabase.MASTER_STASH_ITEMS.contains(itemId))   return "Master STASH";
        return "STASH Storable Items";
    }

    /**
     * Maps a POH_STORAGE item ID to its costume-room sub-location name.
     * Falls back to "POH Storable Items" for anything not in a known sub-set.
     */
    private String pohSubGroupName(int itemId)
    {
        if (JunkDatabase.POH_ARMOUR_CASE_ITEMS.contains(itemId))    return "Armour Case Items";
        if (JunkDatabase.POH_CAPE_RACK_ITEMS.contains(itemId))      return "Cape Rack Items";
        if (JunkDatabase.POH_MAGIC_WARDROBE_ITEMS.contains(itemId)) return "Magic Wardrobe Items";
        if (JunkDatabase.POH_FANCY_DRESS_ITEMS.contains(itemId))    return "Fancy Dress Box Items";
        if (JunkDatabase.POH_TOY_BOX_ITEMS.contains(itemId))        return "Toy Box Items";
        return "POH Storable Items";
    }

    /**
     * Sort key controlling the panel group order, matching the config highlight priority:
     *   POH Storable → STASH Storable → Holiday → Custom → Definite Junk (quest groups
     *   + Miscellaneous) → Conditional Junk.
     *
     * Digit prefix ("0_"…"6_") sorts before any letter, so tier groups can be
     * interleaved precisely around the quest-named groups.
     * Quest groups use "4_" + quest name so they sort alphabetically within
     * the Definite Junk slot; "4_ÿ" sorts after all quest names (ÿ = U+00FF).
     */
    private String groupSortKey(JunkEntry entry)
    {
        String g = getGroupName(entry);
        // All POH sub-groups sort before STASH; within POH, sort alphabetically by sub-group name
        if (entry.tier == JunkTier.POH_STORAGE)         return "0_poh_" + g;
        // STASH sub-groups — slot 1, sorted in clue-tier order
        if (g.equals("Beginner STASH"))         return "1_stash_1_beginner";
        if (g.equals("Easy STASH"))             return "1_stash_2_easy";
        if (g.equals("Medium STASH"))           return "1_stash_3_medium";
        if (g.equals("Hard STASH"))             return "1_stash_4_hard";
        if (g.equals("Elite STASH"))            return "1_stash_5_elite";
        if (g.equals("Master STASH"))           return "1_stash_6_master";
        if (g.equals("STASH Storable Items"))   return "1_stash_7_other";
        if (g.equals("Holiday / Event Items"))  return "2_holiday";
        if (g.equals("Custom Junk"))           return "3_custom";
        if (g.equals("Upgrades / Superseded"))  return "5_upgrades";
        if (g.equals("Miscellaneous"))          return "4_ÿmisc"; // after quest names
        // Quest groups (Definite Junk) — slot 4, sorted alphabetically by quest name
        return "4_" + g;
    }

    /**
     * Builds a collapsible group header with click-to-toggle and a right-click
     * "Whitelist all in group" option.
     *
     * @param groupName name of the group (used as collapse key)
     * @param entries   items in this group (used by whitelist-all action)
     * @param collapsed whether the group starts collapsed
     * @param body      body panel whose visibility this header controls
     */
    /** 8px spacer rendered between consecutive category groups. */
    /** Returns the foreground colour for a group header based on tier. */
    private static Color tierHeaderFg(JunkTier tier)
    {
        if (tier == null) return GROUP_HEADER_FG;
        Color b = tier.defaultColor;
        return new Color(
            Math.min(255, b.getRed()   + 40),
            Math.min(255, b.getGreen() + 40),
            Math.min(255, b.getBlue()  + 40));
    }

    /** Returns the background colour for a group header based on tier. */
    private static Color tierHeaderBg(JunkTier tier)
    {
        if (tier == null) return GROUP_HEADER_BG;
        Color b = tier.defaultColor;
        return new Color(b.getRed() / 8, b.getGreen() / 8, b.getBlue() / 8);
    }

    private JPanel buildGroupDivider()
    {
        JPanel divider = new JPanel();
        divider.setBackground(ColorScheme.DARK_GRAY_COLOR);
        divider.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        divider.setPreferredSize(new Dimension(0, 16));
        divider.setMinimumSize(new Dimension(0, 16));
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        divider.putClientProperty(PROP_DIVIDER, Boolean.TRUE);
        return divider;
    }

    /**
     * Creates a group body panel whose preferred size sums the heights of all
     * visible child components, preventing BoxLayout from clipping bottom rows.
     * Callers must still set layout, background, alignment, and visibility.
     */
    private static JPanel makeGroupBody()
    {
        return new JPanel()
        {
            @Override
            public Dimension getPreferredSize()
            {
                int h = 0;
                for (Component c : getComponents())
                {
                    if (c.isVisible())
                    {
                        h += c.getPreferredSize().height;
                    }
                }
                int w = (getParent() != null && getParent().getWidth() > 0)
                    ? getParent().getWidth()
                    : super.getPreferredSize().width;
                return new Dimension(w, h);
            }

            @Override
            public Dimension getMinimumSize()
            {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize()
            {
                return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
            }
        };
    }

    /**
     * Creates the null-layout header panel shared by both group-header builders.
     * {@code doLayout()} guarantees the east panel always gets its exact preferred
     * width, with the arrow fixed at the left and the group-name label filling the
     * remaining space — regardless of how wide or narrow the panel becomes.
     */
    private static JPanel makeHeaderPanel(JLabel arrow, JLabel label, JPanel eastPanel)
    {
        return new JPanel(null)
        {
            @Override
            public void doLayout()
            {
                Insets ins = getInsets();
                int x = ins.left;
                int y = ins.top;
                int w = getWidth()  - ins.left - ins.right;
                int h = getHeight() - ins.top  - ins.bottom;
                if (w <= 0 || h <= 0) return;
                int aw = arrow.getPreferredSize().width;
                int ew = eastPanel.getPreferredSize().width;
                arrow.setBounds(x,             y, aw,                       h);
                eastPanel.setBounds(x + w - ew, y, ew,                       h);
                label.setBounds(x + aw,         y, Math.max(0, w - aw - ew), h);
            }

            @Override public Dimension getPreferredSize() { return new Dimension(0, 22); }
            @Override public Dimension getMinimumSize()   { return new Dimension(0, 22); }
            @Override public Dimension getMaximumSize()   { return new Dimension(Integer.MAX_VALUE, 22); }
        };
    }

    private JPanel buildGroupHeader(String groupName, List<JunkEntry> entries,
                                    boolean collapsed, JPanel body)
    {
        // Sub-components are declared first so the header's doLayout() can capture them.

        JunkTier tier = entries.isEmpty() ? null : entries.get(0).tier;
        Color headerFg = tierHeaderFg(tier);
        Color headerBg = tierHeaderBg(tier);

        JLabel arrow = new JLabel(collapsed ? "▶" : "▼");
        arrow.setForeground(headerFg);
        arrow.setFont(FontManager.getRunescapeSmallFont());
        arrow.setBorder(new EmptyBorder(0, 0, 0, 4));

        JLabel label = new JLabel(groupName);
        label.setForeground(headerFg);
        label.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.BOLD));

        // East panel: "(N) | ♡ all" (+ "✕ junk" for Custom Junk).
        // Plain FlowLayout — no getMinimumSize tricks needed because doLayout() below
        // positions the east panel directly to its getPreferredSize() width.
        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 1, 0));
        eastPanel.setOpaque(false);

        JLabel countLabel = new JLabel("(" + entries.size() + ")");
        countLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        countLabel.setFont(FontManager.getRunescapeSmallFont());
        eastPanel.add(countLabel);

        // H5: thin vertical separator
        JPanel sep = new JPanel();
        sep.setPreferredSize(new java.awt.Dimension(1, 10));
        sep.setBackground(new Color(80, 80, 80));
        eastPanel.add(sep);

        // getPreferredSize() is overridden to add padding because ♡ (U+2661) is not in
        // RunescapeSmallFont; Java falls back to a system font that renders it wider than
        // FontMetrics.stringWidth() measures, causing JLabel to clip the text.
        JLabel heartAllBtn = new JLabel("♡ all") {
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                return new Dimension(d.width + 12, d.height);
            }
        };
        heartAllBtn.setFont(FontManager.getRunescapeSmallFont());
        heartAllBtn.setForeground(new Color(200, 100, 100));
        heartAllBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        heartAllBtn.setToolTipText("Whitelist all items in this group");
        heartAllBtn.setInheritsPopupMenu(true);
        eastPanel.add(heartAllBtn);

        // Shared whitelist-all action
        Runnable doWhitelistAll = () ->
        {
            if (entries.size() > 1)
            {
                int result = JOptionPane.showConfirmDialog(
                    BankJunkPanel.this,
                    "Whitelist all " + entries.size() + " " + groupName + "?\n"
                        + "They won't be flagged in future scans.",
                    "Confirm Bulk Whitelist",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION) return;
            }
            List<Integer> ids = entries.stream()
                .map(entry -> entry.itemId)
                .collect(Collectors.toList());
            plugin.addAllToWhitelist(ids);
            showBulkWhitelistToast(ids.size(), groupName, ids);
        };

        heartAllBtn.addMouseListener(new MouseAdapter()
        {
            @Override public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1) doWhitelistAll.run();
            }
        });

        // Right-click context menu
        JPopupMenu headerMenu = new JPopupMenu();
        JMenuItem whitelistAll = new JMenuItem("Whitelist all in group (" + entries.size() + ")");
        whitelistAll.addActionListener(e -> doWhitelistAll.run());
        headerMenu.add(whitelistAll);

        // "Remove all from junk list" button + menu item — Custom Junk group only.
        // Added to eastPanel HERE (before header creation) so getPreferredSize() is accurate.
        if ("Custom Junk".equals(groupName))
        {
            Runnable doRemoveAllFlags = () ->
            {
                if (entries.size() > 1)
                {
                    int result = JOptionPane.showConfirmDialog(
                        BankJunkPanel.this,
                        "Remove all " + entries.size() + " " + groupName + " from the junk list?\n"
                            + "They won't be flagged in future scans.",
                        "Confirm Bulk Remove",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    if (result != JOptionPane.YES_OPTION) return;
                }
                List<Integer> ids = entries.stream()
                    .map(entry -> entry.itemId)
                    .collect(Collectors.toList());
                plugin.removeAllCustomFlags(ids);
                showBulkRemoveFlagToast(ids.size(), groupName, ids);
            };

            // ✕ (U+2715) same fallback-rendering issue as ♡ — pad preferred size.
            JLabel removeFlagsBtn = new JLabel("✕ junk") {
                @Override public Dimension getPreferredSize() {
                    Dimension d = super.getPreferredSize();
                    return new Dimension(d.width + 14, d.height);
                }
            };
            removeFlagsBtn.setFont(FontManager.getRunescapeSmallFont());
            removeFlagsBtn.setForeground(new Color(180, 100, 50));
            removeFlagsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            removeFlagsBtn.setToolTipText("Remove all items from junk list");
            removeFlagsBtn.setInheritsPopupMenu(true);
            removeFlagsBtn.addMouseListener(new MouseAdapter()
            {
                @Override public void mouseClicked(MouseEvent e)
                {
                    if (e.getButton() == MouseEvent.BUTTON1) doRemoveAllFlags.run();
                }
            });
            eastPanel.add(removeFlagsBtn);

            JMenuItem removeAllFlags = new JMenuItem("Remove all from junk list (" + entries.size() + ")");
            removeAllFlags.addActionListener(e -> doRemoveAllFlags.run());
            headerMenu.add(removeAllFlags);
        }

        eastPanel.setInheritsPopupMenu(true);

        // makeHeaderPanel() guarantees east panel always gets its exact preferred
        // width regardless of what the group name label requires.
        JPanel header = makeHeaderPanel(arrow, label, eastPanel);
        header.setBackground(headerBg);
        header.setBorder(new EmptyBorder(4, 6, 4, 6));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(arrow);
        header.add(label);
        header.add(eastPanel);
        header.putClientProperty(PROP_COUNT_LABEL, countLabel);

        // Left-click: toggle collapse
        header.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1) return;
                if (collapsedGroups.contains(groupName))
                {
                    collapsedGroups.remove(groupName);
                    arrow.setText("▼");
                    body.setVisible(true);
                }
                else
                {
                    collapsedGroups.add(groupName);
                    arrow.setText("▶");
                    body.setVisible(false);
                }
                listPanel.revalidate();
                listPanel.repaint();
            }
        });

        header.setComponentPopupMenu(headerMenu);

        return header;
    }

    // -------------------------------------------------------------------------
    // Kept Items section builders
    // -------------------------------------------------------------------------

    /**
     * Builds the collapsible header for the "✓ Kept Items" section.
     * Right-click offers "Remove all from Keep list."
     */
    private JPanel buildKeptGroupHeader(int count, boolean collapsed,
                                        JPanel body, List<Integer> ids)
    {
        // Sub-components declared first so the header's doLayout() can capture them.

        JLabel arrow = new JLabel(collapsed ? "▶" : "▼");
        arrow.setForeground(KEPT_HEADER_FG);
        arrow.setFont(FontManager.getRunescapeSmallFont());
        arrow.setBorder(new EmptyBorder(0, 0, 0, 4));

        JLabel label = new JLabel(KEPT_GROUP_NAME);
        label.setForeground(KEPT_HEADER_FG);
        label.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.BOLD));

        // East panel: "(N) | ✕ clear"
        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 0));
        eastPanel.setOpaque(false);

        JLabel countLabel = new JLabel("(" + count + ")");
        countLabel.setForeground(KEPT_HEADER_FG);
        countLabel.setFont(FontManager.getRunescapeSmallFont());
        eastPanel.add(countLabel);

        JPanel sep = new JPanel();
        sep.setPreferredSize(new java.awt.Dimension(1, 10));
        sep.setBackground(new Color(80, 80, 80));
        eastPanel.add(sep);

        // ✕ (U+2715) same fallback-rendering issue — pad preferred size.
        JLabel clearBtn = new JLabel("✕ clear") {
            @Override public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                return new Dimension(d.width + 14, d.height);
            }
        };
        clearBtn.setFont(FontManager.getRunescapeSmallFont());
        clearBtn.setForeground(new Color(200, 80, 80));
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.setToolTipText("Remove all items from Keep list");
        clearBtn.setInheritsPopupMenu(true);
        eastPanel.add(clearBtn);

        eastPanel.setInheritsPopupMenu(true);

        JPanel header = makeHeaderPanel(arrow, label, eastPanel);
        header.setBackground(KEPT_HEADER_BG);
        header.setBorder(new EmptyBorder(4, 6, 4, 6));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        header.add(arrow);
        header.add(label);
        header.add(eastPanel);
        header.putClientProperty(PROP_COUNT_LABEL, countLabel);

        Runnable doRemoveAll = () ->
        {
            if (ids.size() > 1)
            {
                int result = JOptionPane.showConfirmDialog(
                    BankJunkPanel.this,
                    "Remove all " + ids.size() + " items from your Keep list?\n"
                        + "They may be flagged as junk again in future scans.",
                    "Confirm Bulk Remove",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION) return;
            }
            List<Integer> toRemove = new ArrayList<>(ids);
            plugin.removeAllFromWhitelist(toRemove);
            showBulkClearKeptToast(toRemove.size(), toRemove);
        };

        clearBtn.addMouseListener(new MouseAdapter()
        {
            @Override public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1) doRemoveAll.run();
            }
        });

        header.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() != MouseEvent.BUTTON1) return;
                if (collapsedGroups.contains(KEPT_GROUP_NAME))
                {
                    collapsedGroups.remove(KEPT_GROUP_NAME);
                    arrow.setText("▼");
                    body.setVisible(true);
                }
                else
                {
                    collapsedGroups.add(KEPT_GROUP_NAME);
                    arrow.setText("▶");
                    body.setVisible(false);
                }
                listPanel.revalidate();
                listPanel.repaint();
            }
        });

        JPopupMenu menu = new JPopupMenu();
        JMenuItem removeAll = new JMenuItem("Remove all from Keep list (" + count + ")");
        removeAll.addActionListener(e -> doRemoveAll.run());
        menu.add(removeAll);
        header.setComponentPopupMenu(menu);

        return header;
    }

    /** Builds a single row for a whitelisted (kept) item. */
    private JPanel buildKeptItemRow(int itemId, String name)
    {
        JPanel row = new JPanel(new BorderLayout(4, 0));
        row.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        row.setBorder(new EmptyBorder(3, 4, 3, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        row.setPreferredSize(new Dimension(0, 36));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(32, 32));
        itemManager.getImage(itemId).addTo(iconLabel);
        row.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(FontManager.getRunescapeSmallFont());
        textPanel.add(nameLabel, BorderLayout.CENTER);

        row.add(textPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel(new BorderLayout(2, 0));
        eastPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JButton heartBtn = new JButton("♥");
        heartBtn.setFont(FontManager.getRunescapeSmallFont());
        heartBtn.setForeground(new Color(200, 60, 60));
        heartBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        heartBtn.setBorderPainted(false);
        heartBtn.setFocusPainted(false);
        heartBtn.setMargin(new java.awt.Insets(0, 2, 0, 0));
        heartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        heartBtn.setToolTipText("Remove from Keep list");
        heartBtn.addActionListener(e ->
        {
            int result = JOptionPane.showConfirmDialog(
                BankJunkPanel.this,
                "Remove \"" + name + "\" from your Keep list?\n"
                    + "It may be flagged as junk again in future scans.",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION)
            {
                plugin.removeFromWhitelist(itemId);
            }
        });
        eastPanel.add(heartBtn, BorderLayout.EAST);

        row.add(eastPanel, BorderLayout.EAST);

        String safeName = HtmlEscapers.htmlEscaper().escape(name);
        row.setToolTipText("<html><b>" + safeName + "</b><br>"
            + "You have marked this item as Keep.<br>"
            + "Click ♥ or right-click → <i>Remove from Keep list</i> to re-enable flagging.</html>");

        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove from Keep list");
        removeItem.addActionListener(e ->
        {
            int result = JOptionPane.showConfirmDialog(
                BankJunkPanel.this,
                "Remove \"" + name + "\" from your Keep list?\n"
                    + "It may be flagged as junk again in future scans.",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_OPTION)
            {
                plugin.removeFromWhitelist(itemId);
            }
        });
        popup.add(removeItem);
        row.setComponentPopupMenu(popup);
        iconLabel.setComponentPopupMenu(popup);
        nameLabel.setComponentPopupMenu(popup);

        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                row.setBackground(ROW_HOVER_COLOR);
                textPanel.setBackground(ROW_HOVER_COLOR);
                eastPanel.setBackground(ROW_HOVER_COLOR);
                heartBtn.setBackground(ROW_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                row.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                textPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                eastPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                heartBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
            }
        });

        return row;
    }

    // -------------------------------------------------------------------------
    // Row builder
    // -------------------------------------------------------------------------

    private JPanel buildRow(JunkEntry entry, int quantity, boolean questWarning, boolean hideTierLabel)
    {
        JPanel row = new JPanel(new BorderLayout(4, 0));
        row.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        row.setBorder(new EmptyBorder(3, 4, 3, 4));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        row.setPreferredSize(new Dimension(0, 36));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(32, 32));
        AsyncBufferedImage img = itemManager.getImage(entry.itemId);
        img.addTo(iconLabel);
        row.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel nameLabel = new JLabel(entry.name);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(FontManager.getRunescapeSmallFont());
        textPanel.add(nameLabel, BorderLayout.CENTER);

        // H8: only show tier label when it adds information (mixed-tier groups or quest warnings)
        if (!hideTierLabel || questWarning)
        {
            JLabel tierLabel;
            if (questWarning)
            {
                tierLabel = new JLabel(entry.tier.displayName + "  ⚠ check before dropping");
                tierLabel.setForeground(WARN_COLOR);
            }
            else
            {
                tierLabel = new JLabel(entry.tier.displayName);
                tierLabel.setForeground(entry.tier.defaultColor);
            }
            tierLabel.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.ITALIC));
            textPanel.add(tierLabel, BorderLayout.SOUTH);
        }

        row.add(textPanel, BorderLayout.CENTER);

        row.putClientProperty(PROP_TIER, entry.tier);

        JPanel eastPanel = new JPanel(new BorderLayout(2, 0));
        eastPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel qtyLabel = new JLabel("×" + quantity);
        qtyLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        qtyLabel.setFont(FontManager.getRunescapeSmallFont());
        qtyLabel.setBorder(new EmptyBorder(0, 4, 0, 0));
        eastPanel.add(qtyLabel, BorderLayout.WEST);

        JButton heartBtn = new JButton("♡");
        heartBtn.setFont(FontManager.getRunescapeSmallFont());
        heartBtn.setForeground(new Color(200, 100, 100));
        heartBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        heartBtn.setBorderPainted(false);
        heartBtn.setFocusPainted(false);
        heartBtn.setMargin(new java.awt.Insets(0, 2, 0, 0));
        heartBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        heartBtn.setToolTipText("Add to Keep list (whitelist)");
        heartBtn.addActionListener(e ->
        {
            plugin.addToWhitelist(entry.itemId);
            showWhitelistToast(entry.name, entry.itemId);
        });
        eastPanel.add(heartBtn, BorderLayout.EAST);

        row.add(eastPanel, BorderLayout.EAST);

        String safeName   = HtmlEscapers.htmlEscaper().escape(entry.name);
        String safeReason = HtmlEscapers.htmlEscaper().escape(entry.reason);
        String tooltip = "<html><b>" + safeName + "</b><br>" + safeReason;
        if (questWarning)
        {
            tooltip += "<br><span style='color:#FFC800'><b>⚠</b> A related quest or diary step "
                + "is not yet complete — verify before dropping.</span>";
        }
        tooltip += "</html>";
        row.setToolTipText(tooltip);

        JPopupMenu popup = new JPopupMenu();

        JMenuItem keepItem = new JMenuItem("Mark as Keep (whitelist)");
        keepItem.addActionListener(e -> plugin.addToWhitelist(entry.itemId));
        popup.add(keepItem);

        if (entry.tier == JunkTier.CUSTOM)
        {
            JMenuItem removeFlag = new JMenuItem("Remove from junk list");
            removeFlag.addActionListener(e -> plugin.removeCustomFlag(entry.itemId));
            popup.add(removeFlag);
        }

        JMenuItem wikiItem = new JMenuItem("View on Wiki");
        wikiItem.addActionListener(e ->
        {
            try
            {
                String url = (entry.getWikiUrl() != null)
                    ? entry.getWikiUrl()
                    : "https://oldschool.runescape.wiki/w/" + URLEncoder
                        .encode(entry.name, StandardCharsets.UTF_8)
                        .replace("+", "_");
                // SECURITY-ANNOTATION (ASVS V15.1.5 / ARCH-03):
                // LinkBrowser.browse() is a one-way OS handoff. SSRF not applicable.
                // URL safety: constant from JunkDatabase or wiki base + URLEncoder output.
                if (!url.startsWith("https://"))
                {
                    log.warn("Blocked non-HTTPS wiki URL for {}: {}", entry.name, url);
                    return;
                }
                LinkBrowser.browse(url);
            }
            catch (Exception ex)
            {
                log.warn("Could not open wiki for {}", entry.name, ex);
            }
        });
        popup.add(wikiItem);

        row.setComponentPopupMenu(popup);
        iconLabel.setComponentPopupMenu(popup);
        nameLabel.setComponentPopupMenu(popup);

        row.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        row.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e)
            {
                row.setBackground(ROW_HOVER_COLOR);
                textPanel.setBackground(ROW_HOVER_COLOR);
                eastPanel.setBackground(ROW_HOVER_COLOR);
                heartBtn.setBackground(ROW_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                row.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                textPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                eastPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                heartBtn.setBackground(ColorScheme.DARKER_GRAY_COLOR);
            }
        });

        return row;
    }

    // -------------------------------------------------------------------------
    // H1: scan timestamp
    // -------------------------------------------------------------------------

    public void updateScanTimestamp()
    {
        String ts = LocalTime.now().format(TIME_FMT);
        SwingUtilities.invokeLater(() -> timestampLabel.setText("Last scanned: " + ts));
    }

    // -------------------------------------------------------------------------
    // H3: whitelist toast notification
    // -------------------------------------------------------------------------

    private JPanel buildToastPanel()
    {
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setBackground(TOAST_BG);
        panel.setBorder(new EmptyBorder(4, 6, 4, 6));
        panel.setVisible(false);

        toastLabel = new JLabel("Added to Keep list");
        toastLabel.setForeground(Color.WHITE);
        toastLabel.setFont(FontManager.getRunescapeSmallFont());
        panel.add(toastLabel, BorderLayout.CENTER);

        JButton undoBtn = new JButton("Undo");
        undoBtn.setFont(FontManager.getRunescapeSmallFont());
        undoBtn.setForeground(new Color(100, 180, 255));
        undoBtn.setBackground(TOAST_BG);
        undoBtn.setBorderPainted(false);
        undoBtn.setFocusPainted(false);
        undoBtn.setHorizontalAlignment(SwingConstants.RIGHT);
        undoBtn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        undoBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        undoBtn.addActionListener(e ->
        {
            if (lastUndoAction != null)
            {
                lastUndoAction.run();
                lastUndoAction = null;
            }
            if (toastTimer != null) toastTimer.stop();
            toastPanel.setVisible(false);
        });

        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        bottomRow.setBackground(TOAST_BG);
        bottomRow.add(undoBtn);
        panel.add(bottomRow, BorderLayout.SOUTH);

        return panel;
    }

    private void showWhitelistToast(String itemName, int itemId)
    {
        List<Integer> ids = Collections.singletonList(itemId);
        showToast("Added to Keep list — " + itemName,
            () -> plugin.removeAllFromWhitelist(ids));
    }

    private void showBulkWhitelistToast(int count, String groupName, List<Integer> ids)
    {
        List<Integer> snapshot = new ArrayList<>(ids);
        showToast("Whitelisted " + count + " item" + (count == 1 ? "" : "s")
            + " in " + groupName,
            () -> plugin.removeAllFromWhitelist(snapshot));
    }

    private void showBulkRemoveFlagToast(int count, String groupName, List<Integer> ids)
    {
        List<Integer> snapshot = new ArrayList<>(ids);
        showToast("Removed " + count + " item" + (count == 1 ? "" : "s")
            + " from junk list",
            () -> plugin.addAllCustomFlags(snapshot));
    }

    private void showBulkClearKeptToast(int count, List<Integer> ids)
    {
        List<Integer> snapshot = new ArrayList<>(ids);
        showToast("Cleared " + count + " item" + (count == 1 ? "" : "s")
            + " from Keep list",
            () -> plugin.addAllToWhitelist(snapshot));
    }

    private void showToast(String message, Runnable undoAction)
    {
        SwingUtilities.invokeLater(() ->
        {
            lastUndoAction = undoAction;
            toastLabel.setText(message);
            toastPanel.setVisible(true);
            toastPanel.revalidate();
            toastPanel.repaint();

            if (toastTimer != null && toastTimer.isRunning())
            {
                toastTimer.stop();
            }
            toastTimer = new Timer(5000, ev -> toastPanel.setVisible(false));
            toastTimer.setRepeats(false);
            toastTimer.start();
        });
    }

    // -------------------------------------------------------------------------
    // H6: legend tier row helpers
    // -------------------------------------------------------------------------

    private JPanel legendTierRow(JunkTier tier)
    {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        Color tierColor = tier.defaultColor;
        JLabel nameLabel = new JLabel(tier.displayName + " — ");
        nameLabel.setForeground(tierColor);
        nameLabel.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.ITALIC));
        JLabel descLabel = new JLabel(legendDescription(tier));
        descLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        descLabel.setFont(FontManager.getRunescapeSmallFont());

        row.add(nameLabel);
        row.add(descLabel);
        return row;
    }

    /** Short description string shown next to the tier colour swatch in the legend. */
    private String legendDescription(JunkTier tier)
    {
        switch (tier)
        {
            case RED:          return "no use, safe to drop";
            case YELLOW:       return "upgrade already banked";
            case CUSTOM:       return "manually flagged";
            case HOLIDAY:      return "reclaimable from Diango";
            case POH_STORAGE:  return "store in costume room";
            case STASH_STORAGE: return "store in STASH unit";
            default:           return "";
        }
    }

    private JPanel legendKeptRow()
    {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        row.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        JLabel keptLabel = new JLabel("\u2665 Kept — ");
        keptLabel.setForeground(KEPT_HEADER_FG);
        keptLabel.setFont(FontManager.getRunescapeSmallFont().deriveFont(Font.ITALIC));

        JLabel descLabel = new JLabel("whitelisted by you");
        descLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        descLabel.setFont(FontManager.getRunescapeSmallFont());

        row.add(keptLabel);
        row.add(descLabel);
        return row;
    }
}

package com.bankjunk;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.ItemContainer;
import net.runelite.api.MenuAction;
import net.runelite.api.QuestState;
import net.runelite.api.coords.WorldPoint;

import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.client.events.ConfigChanged;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
    name = "Dumb Old Man",
    description = "Highlights quest items and other bank clutter you no longer need, with smart upgrade awareness.",
    tags = {"bank", "quest", "items", "cleanup", "junk"}
)
public class BankJunkPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;
    @Inject private ItemManager itemManager;
    @Inject private OverlayManager overlayManager;
    @Inject private ClientToolbar clientToolbar;
    @Inject private ChatMessageManager chatMessageManager;
    @Inject private BankJunkConfig config;
    @Inject private BankJunkOverlay overlay;
    @Inject private BankJunkPanel panel;
    @Inject private ScheduledExecutorService executor;

    private NavigationButton navButton;

    /**
     * Pending debounce task — cancelled and rescheduled on each bank container event.
     * Coalesces rapid bursts (e.g., bulk deposits) into a single scan 150 ms later.
     */
    private ScheduledFuture<?> debounceTask;

    /** True when the next scan should fire a chat notification (armed on startup and each bank close). */
    private boolean notificationArmed = true;

    /**
     * Order-sensitive hash of the last scanned bank (item IDs × quantities).
     * If the incoming bank state hashes to the same value, the full rescan is skipped.
     */
    private long lastBankHash = 0L;

    /**
     * When true, the next call to rebuildFlaggedItems bypasses the dirty-hash check.
     * Set by config changes and manual scan requests so those always take effect.
     */
    private boolean forceScan = false;

    /**
     * Last bank item snapshot from the most recent successful scan.
     * Allows re-evaluation when the bank is closed (e.g. STASH state changed
     * while the player is in the field).
     */
    private Item[] lastBankItems = null;

    /** Set true by triggerManualScan() so the chat notification fires regardless of notificationArmed state. */
    private boolean manualScanNotify = false;

    /**
     * Maps item ID → active JunkEntry for items currently flagged as junk.
     * Only contains items actually present in the bank right now.
     * Re-evaluated whenever the bank container changes.
     * Access only on the client thread; read on EDT via a snapshot.
     */
    @Getter
    private Map<Integer, JunkEntry> activeFlagged = Collections.emptyMap();

    /**
     * Item IDs the user has manually whitelisted ("keep this, don't flag").
     * Persisted via ConfigManager.
     */
    private final Set<Integer> userWhitelist = new HashSet<>();

    /**
     * Item IDs the user has manually flagged as junk (CUSTOM tier).
     * Persisted via ConfigManager.
     */
    private final Set<Integer> userCustomFlags = new HashSet<>();

    // --- STASH fill-state tracking ---
    /**
     * Set of STASH units the player has confirmed filling.
     * Persisted per RSN in ConfigManager as comma-separated enum constant names.
     *
     * <p>Items that belong to multiple tiers (e.g. Steel Longsword in both Easy and
     * Medium STASH) appear in <em>every</em> panel group that still has an unfilled
     * unit. An item is removed from a group only when every unit in that tier
     * containing the item is present in this set.</p>
     */
    private final Set<BankJunkStashUnit> filledStashUnits = new HashSet<>();

    /**
     * Set true when LOGGED_IN fires so we retry loadStashState() on the next
     * game tick once the local player name is guaranteed to be available.
     */
    private boolean pendingStashLoad = false;

    /** Maximum tile distance from the player to a STASH unit for a position match. */
    private static final int STASH_SEARCH_RADIUS = 5;

    // Tier ordering is defined once in BankJunkStashUnit.TIER_TO_DISPLAY.
    // Do NOT duplicate the tier key/name mapping here — reference it directly.

    // -------------------------------------------------------------------------

    @Override
    protected void startUp()
    {
        loadUserLists();
        overlayManager.add(overlay);

        if (config.showPanel())
        {
            addPanel();
        }

        // If the bank is already open/cached when the plugin starts, scan immediately.
        clientThread.invokeLater(() ->
        {
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            if (bank != null)
            {
                rebuildFlaggedItems(bank);
            }
        });
    }

    @Override
    protected void shutDown()
    {
        if (debounceTask != null)
        {
            debounceTask.cancel(false);
            debounceTask = null;
        }
        overlayManager.remove(overlay);
        removePanel();
        activeFlagged = Collections.emptyMap();
    }

    // -------------------------------------------------------------------------
    // Event handlers
    // -------------------------------------------------------------------------

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event)
    {
        if (event.getContainerId() != InventoryID.BANK.getId())
        {
            return;
        }
        // Debounce: cancel any pending scan and schedule a new one 150 ms out.
        // This coalesces rapid container events (bulk deposits) into a single scan.
        //
        // SECURITY-ANNOTATION (ASVS V15.1.5 / ARCH-03):
        // ScheduledExecutorService is the only "dangerous" concurrency primitive used.
        // Safety invariants:
        //   1. At most one pending task at any time (cancel-before-schedule).
        //   2. Both `debounceTask` reads/writes and this entire handler run on the
        //      RuneLite client thread — no cross-thread race on the field.
        //   3. The lambda re-reads bank state at execution time via clientThread
        //      .invokeLater() — no stale snapshot risk.
        //   4. Executor lifecycle is RuneLite-managed; plugin only cancels its own
        //      task in shutDown(). See THREAT_MODEL.md §3.2.
        if (debounceTask != null)
        {
            debounceTask.cancel(false);
        }
        debounceTask = executor.schedule(() ->
            clientThread.invokeLater(() ->
            {
                ItemContainer bank = client.getItemContainer(InventoryID.BANK);
                rebuildFlaggedItems(bank);
            }),
            150, TimeUnit.MILLISECONDS);
    }

    /**
     * Secondary trigger: fires when the bank interface widget loads.
     * More reliable than ItemContainerChanged alone on the first bank open,
     * because the container event can fire before or be missed on first login.
     */
    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event)
    {
        if (event.getGroupId() != InterfaceID.BANK)
        {
            return;
        }
        clientThread.invokeLater(() ->
        {
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            if (bank != null)
            {
                rebuildFlaggedItems(bank);
            }
        });
    }

    /**
     * Re-arm the chat notification when the bank interface closes, so the
     * "junk found" message fires again the next time the bank is opened
     * (rather than only once per session or after junk drops to zero).
     */
    @Subscribe
    public void onWidgetClosed(WidgetClosed event)
    {
        if (event.getGroupId() == InterfaceID.BANK)
        {
            notificationArmed = true;
        }
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals(BankJunkConfig.GROUP))
        {
            return;
        }
        // Re-evaluate the bank whenever any plugin config changes so toggles
        // take effect immediately without needing to reopen the bank.
        // Force rescan — config changes alter what's flagged even if bank is unchanged.
        clientThread.invokeLater(() ->
        {
            forceScan = true;
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            if (bank != null)
            {
                rebuildFlaggedItems(bank);
            }
        });
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() == GameState.LOGGED_IN)
        {
            // LOGGED_IN fires before getLocalPlayer().getName() is populated.
            // Defer the actual load to the first onGameTick when the name is ready.
            pendingStashLoad = true;
        }
        else if (event.getGameState() == GameState.LOGIN_SCREEN
            || event.getGameState() == GameState.HOPPING)
        {
            // Save before clearing — defensive guard for a deposit between the last
            // explicit save and a world hop (saveStashState() no-ops at LOGIN_SCREEN
            // once the local player is already null, but can still persist on HOPPING).
            saveStashState();
            activeFlagged = Collections.emptyMap();
            notificationArmed = true;
            lastBankHash  = 0L;
            filledStashUnits.clear();
            lastBankItems = null;
            // Build whitelist names so the "Kept Items" section stays visible when bank is closed.
            Map<Integer, String> wlNames = new HashMap<>();
            for (int id : userWhitelist)
            {
                String name = itemManager.getItemComposition(id).getName();
                if (name != null && !name.equals("null")) wlNames.put(id, name);
            }
            final Map<Integer, String> wlSnapshot = new HashMap<>(wlNames);
            SwingUtilities.invokeLater(() ->
                panel.refresh(Collections.emptyMap(), Collections.emptyMap(),
                    Collections.emptySet(), wlSnapshot));
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (pendingStashLoad
            && client.getLocalPlayer() != null
            && client.getLocalPlayer().getName() != null)
        {
            pendingStashLoad = false;
            loadStashState();
        }
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        // Only add our entry once per item — hook onto the "Examine" option which fires exactly once.
        if (!event.getOption().equals("Examine"))
        {
            return;
        }

        // Only react to bank item widgets
        if (event.getMenuEntry().getParam1() != ComponentID.BANK_ITEM_CONTAINER)
        {
            return;
        }

        Widget bankContainer = client.getWidget(ComponentID.BANK_ITEM_CONTAINER);
        if (bankContainer == null || bankContainer.isHidden())
        {
            return;
        }

        int slot = event.getMenuEntry().getParam0();
        Widget[] items = bankContainer.getDynamicChildren();
        if (items == null || slot < 0 || slot >= items.length)
        {
            return;
        }

        Widget itemWidget = items[slot];
        if (itemWidget == null || itemWidget.getItemId() <= 0)
        {
            return;
        }

        int rawId = itemWidget.getItemId();
        ItemComposition comp = itemManager.getItemComposition(rawId);
        int itemId = comp.getNote() != -1 ? comp.getLinkedNoteId() : rawId;

        if (!activeFlagged.containsKey(itemId))
        {
            // Not currently flagged — offer to flag it
            client.createMenuEntry(-1)
                .setOption("Flag as Junk")
                .setTarget(event.getTarget())
                .setType(MenuAction.RUNELITE)
                .onClick(e -> addCustomFlag(itemId));
        }
        else
        {
            // Currently flagged — offer to whitelist it
            client.createMenuEntry(-1)
                .setOption("Whitelist (keep)")
                .setTarget(event.getTarget())
                .setType(MenuAction.RUNELITE)
                .onClick(e -> addToWhitelist(itemId));
        }
    }

    /**
     * Listens for STASH deposit/withdraw confirmation messages and updates fill state.
     *
     * <p>We identify which STASH unit was interacted with by reading the player's world
     * position at the time the chat message fires (client thread — safe and immediate).
     * No inventory diffing is required: position alone uniquely identifies the unit.</p>
     */
    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE)
        {
            return;
        }
        // Game appends " (N)" e.g. "You deposit your items into the STASH unit. (4)"
        String msg = Text.removeTags(event.getMessage());

        if (msg.startsWith("You deposit your items into the STASH unit."))
        {
            if (client.getLocalPlayer() == null) return;
            WorldPoint pos = client.getLocalPlayer().getWorldLocation();
            sendDebugChat("[BankJunk] Deposit at " + pos);
            BankJunkStashUnit unit = findNearestStashUnit(pos);
            sendDebugChat("[BankJunk] Matched unit: " + (unit != null ? unit.name() : "none"));
            if (unit != null)
            {
                filledStashUnits.add(unit);
                saveStashState();
                forceScan = true;
                ItemContainer bank = client.getItemContainer(InventoryID.BANK);
                rebuildFlaggedItems(bank);
            }
        }
        else if (msg.startsWith("You withdraw your items from the STASH unit."))
        {
            if (client.getLocalPlayer() == null) return;
            WorldPoint pos = client.getLocalPlayer().getWorldLocation();
            sendDebugChat("[BankJunk] Withdraw at " + pos);
            BankJunkStashUnit unit = findNearestStashUnit(pos);
            sendDebugChat("[BankJunk] Matched unit: " + (unit != null ? unit.name() : "none"));
            if (unit != null)
            {
                filledStashUnits.remove(unit);
                saveStashState();
                forceScan = true;
                ItemContainer bank = client.getItemContainer(InventoryID.BANK);
                rebuildFlaggedItems(bank);
            }
        }
    }

    /**
     * Returns the nearest {@link BankJunkStashUnit} within {@value #STASH_SEARCH_RADIUS}
     * tiles of the given position, or {@code null} if none is within range.
     */
    private BankJunkStashUnit findNearestStashUnit(WorldPoint playerPos)
    {
        BankJunkStashUnit nearest = null;
        int nearestDist = Integer.MAX_VALUE;
        for (BankJunkStashUnit unit : BankJunkStashUnit.values())
        {
            if (unit.getLocation().getPlane() != playerPos.getPlane()) continue;
            int dist = unit.getLocation().distanceTo(playerPos);
            if (dist <= STASH_SEARCH_RADIUS && dist < nearestDist)
            {
                nearestDist = dist;
                nearest = unit;
            }
        }
        return nearest;
    }

    /**
     * Returns the POH costume-room storage reason string for the given resolved item ID,
     * or {@code null} if the item is not storable in any POH furniture.
     * Adding a new storage type requires only a new set in {@link JunkDatabase} and
     * a new branch here.
     */
    private static String pohStorageReason(int id)
    {
        if (JunkDatabase.POH_ARMOUR_CASE_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Armour Case in the Costume Room of your POH.";
        if (JunkDatabase.TEAM_CAPES_1_TO_50.contains(id))
            return "POH storable — this item can be stored in the Cape Rack (Team cape slot).<br>"
                 + "Only ONE Team-1 to Team-50 cape fits the slot — keep the one you want to store and drop the rest.<br>"
                 + "Note: Team capes are also required for 2 Medium STASH units<br>"
                 + "(Castle Wars next to Lanthus, and Barbarian Outpost Agility Course).";
        if (JunkDatabase.POH_CAPE_RACK_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Cape Rack in the Costume Room of your POH.";
        if (JunkDatabase.POH_MAGIC_WARDROBE_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Magic Wardrobe in the Costume Room of your POH.";
        if (JunkDatabase.POH_TOY_BOX_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Toy Box in the Costume Room of your POH.";
        if (JunkDatabase.POH_FANCY_DRESS_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Fancy Dress Box in the Costume Room of your POH.";
        if (JunkDatabase.POH_TREASURE_CHEST_ITEMS.contains(id))
            return "POH storable — this item can be stored in the Treasure Chest in the Costume Room of your POH.";
        return null;
    }

    /**
     * Returns the list of STASH tier display names (e.g. "Easy STASH", "Hard STASH") for
     * which the given item ID belongs to at least one unfilled unit. An empty list means
     * either the item is not a STASH item, or every unit that requires it is already filled.
     *
     * <p>For items tracked in {@link BankJunkStashUnit}, the result may contain multiple
     * tier strings when the item appears across units of different tiers. For items only
     * in {@link StashDatabase} (variant IDs not in any enum entry), a single-group fallback
     * is returned without fill-state checking.</p>
     */
    private List<String> allUnfilledStashGroups(int itemId)
    {
        // Tier iteration order comes from BankJunkStashUnit.TIER_TO_DISPLAY —
        // the single source of truth for tier keys and display names.
        boolean foundAnyUnit = false;
        List<String> groups = new ArrayList<>();

        for (Map.Entry<String, String> tier : BankJunkStashUnit.TIER_TO_DISPLAY.entrySet())
        {
            boolean tierHasUnfilled = false;
            for (BankJunkStashUnit unit : BankJunkStashUnit.values())
            {
                if (!unit.getTier().equals(tier.getKey())) continue;
                if (!unit.getItems().contains(itemId))     continue;
                foundAnyUnit = true;
                if (!filledStashUnits.contains(unit))
                {
                    tierHasUnfilled = true;
                }
            }
            if (tierHasUnfilled) groups.add(tier.getValue());
        }

        if (foundAnyUnit) return groups; // empty = all filled, suppress

        // Variant item not in any BankJunkStashUnit — single-group fallback.
        if (StashDatabase.BEGINNER_STASH_ITEMS.contains(itemId))   groups.add("Beginner STASH");
        else if (StashDatabase.EASY_STASH_ITEMS.contains(itemId))   groups.add("Easy STASH");
        else if (StashDatabase.MEDIUM_STASH_ITEMS.contains(itemId)) groups.add("Medium STASH");
        else if (StashDatabase.HARD_STASH_ITEMS.contains(itemId))   groups.add("Hard STASH");
        else if (StashDatabase.ELITE_STASH_ITEMS.contains(itemId))  groups.add("Elite STASH");
        else if (StashDatabase.MASTER_STASH_ITEMS.contains(itemId)) groups.add("Master STASH");
        return groups;
    }

    /**
     * Sends an orange debug message in game chat.
     * Only fires when the "Debug: STASH Tracking Messages" config option is enabled.
     */
    private void sendDebugChat(String message)
    {
        if (!config.debugStash())
        {
            return;
        }
        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.GAMEMESSAGE)
            .runeLiteFormattedMessage("<col=ff6644>" + message + "</col>")
            .build());
    }

    // -------------------------------------------------------------------------
    // Core logic
    // -------------------------------------------------------------------------

    /**
     * Triggers a manual rescan of the bank, bypassing the dirty-hash check.
     * Safe to call from the EDT; delegates to the client thread.
     */
    public void triggerManualScan()
    {
        clientThread.invokeLater(() ->
        {
            forceScan        = true;
            manualScanNotify = true;  // ensures chat notification fires after this scan
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            if (bank != null)
            {
                rebuildFlaggedItems(bank);
            }
        });
    }

    /**
     * Computes an order-sensitive hash of the bank's item IDs and quantities.
     * Used to detect whether the bank has meaningfully changed between events.
     */
    private static long computeBankHash(Item[] items)
    {
        if (items == null)
        {
            return 0L;
        }
        long h = 1L;
        for (Item item : items)
        {
            int id  = (item != null) ? item.getId()      : 0;
            int qty = (item != null) ? item.getQuantity() : 0;
            h = h * 31L + ((long) id * 1_000_003L + qty);
        }
        return h;
    }

    /**
     * Evaluates the bank container and builds the map of flagged items.
     * Called on the client thread.
     */
    private void rebuildFlaggedItems(ItemContainer bank)
    {
        // Resolve the item array: prefer live container, fall back to cache on
        // force-scans so STASH state changes update the panel even when the bank
        // is closed.
        final Item[] items;
        if (bank != null)
        {
            Item[] raw = bank.getItems();
            lastBankItems = raw != null ? raw.clone() : null; // cache for out-of-bank refreshes
            items = raw != null ? raw : new Item[0];
        }
        else if (forceScan && lastBankItems != null)
        {
            items = lastBankItems;
        }
        else
        {
            return;
        }

        // Dirty check — skip full rescan when bank contents are unchanged and no
        // config change / manual-scan request has been issued.
        long newHash = computeBankHash(items);
        if (!forceScan && newHash == lastBankHash)
        {
            return;
        }
        forceScan    = false;
        lastBankHash = newHash;

        // Build bankedIds AND quantities in a single pass (one getItemComposition per slot).
        Set<Integer>         bankedIds  = new HashSet<>();
        Map<Integer, Integer> quantities = new HashMap<>();
        for (Item item : items)
        {
            if (item == null || item.getId() <= 0)
            {
                continue;
            }
            int id = item.getId();
            ItemComposition comp = itemManager.getItemComposition(id);
            // Skip placeholders — they are not "in the bank"
            if (comp.getPlaceholderTemplateId() != -1)
            {
                continue;
            }
            // Resolve noted items to their base item ID
            if (comp.getNote() != -1)
            {
                id = comp.getLinkedNoteId();
            }
            bankedIds.add(id);
            quantities.merge(id, item.getQuantity(), Integer::sum);
        }

        Map<Integer, JunkEntry> flagged = new HashMap<>();

        // =====================================================================
        // SCAN PRIORITY ORDER (highest → lowest):
        //   1. STASH Storable Items
        //   2. POH Storable Items
        //   3. Quest-related Items (RED — quest gate satisfied)
        //   4. Holiday / Event Items
        //   5. Definite Junk Items (RED — no quest gate)
        //   6. Conditional Items (YELLOW)
        //   7. User-Flagged Items (CUSTOM)
        //   8. Whitelisted Items (shown in "Kept Items" panel section; not re-flagged here)
        //
        // All scans use putIfAbsent (or synthetic keys for dual-category items) so
        // whichever category runs first claims the primary slot.
        // =====================================================================

        // --- PRIORITY 1: STASH storable items ---
        // Flags any emote clue STASH item present in the bank, regardless of build/fill state.
        // The tooltip informs the player they CAN store these items in a STASH unit.
        // Items that serve both STASH and POH purposes (e.g. Team capes) are flagged here;
        // the POH pass (Priority 2) adds a second entry for those items using a synthetic key.
        //
        // Synthetic negative keys (-1 downward) let the flagged map hold multiple entries for
        // the same item when it belongs to more than one tier (e.g. Steel Longsword: Easy + Medium
        // STASH). The panel uses JunkEntry.itemId — not the map key — for display and whitelist
        // operations, so this is transparent to the UI.
        if (config.showStashStorageTier())
        {
            int syntheticKey = -1;

            for (int stashItemId : StashDatabase.STASH_ITEMS)
            {
                if (!bankedIds.contains(stashItemId))    continue;
                if (userWhitelist.contains(stashItemId)) continue;

                List<String> groups = allUnfilledStashGroups(stashItemId);
                if (groups.isEmpty()) continue;

                String name   = itemManager.getItemComposition(stashItemId).getName();
                String reason = "This item can be stored in a STASH unit to free bank space.<br>"
                    + "Build the appropriate STASH unit and deposit these emote clue items.";

                // First tier uses the real item ID as key; additional tiers use synthetic keys.
                JunkEntry primary = new JunkEntry(stashItemId, name, JunkTier.STASH_STORAGE, reason)
                    .withStashGroupName(groups.get(0));
                flagged.putIfAbsent(stashItemId, primary);

                for (int i = 1; i < groups.size(); i++)
                {
                    JunkEntry extra = new JunkEntry(stashItemId, name, JunkTier.STASH_STORAGE, reason)
                        .withStashGroupName(groups.get(i));
                    flagged.put(syntheticKey--, extra);
                }
            }
        }

        // --- PRIORITY 2: POH storable items (player-owned house costume room) ---
        // Items already flagged as STASH_STORAGE (Priority 1) are also added here when they
        // serve both purposes (e.g. Team capes: Cape Rack + 2 Medium STASH units).
        // Dual-category items use a synthetic key block starting at -500000 — well away from
        // the STASH synthetic key range (-1 downward) to prevent collisions.
        if (config.showPohStorageTier())
        {
            int pohSyntheticKey = -500000;
            for (int id : bankedIds)
            {
                if (userWhitelist.contains(id)) continue;
                String reason = pohStorageReason(id);
                if (reason == null) continue;

                String name = itemManager.getItemComposition(id).getName();
                JunkEntry pohEntry = new JunkEntry(id, name, JunkTier.POH_STORAGE, reason);
                String wikiOverride = JunkDatabase.POH_STORAGE_WIKI_URLS.get(id);
                if (wikiOverride != null) pohEntry.withWikiUrl(wikiOverride);

                // If the item was already flagged as STASH (Priority 1), add POH row at a
                // synthetic key so both rows appear in the panel side by side.
                boolean alreadyStash = flagged.containsKey(id)
                    && flagged.get(id).tier == JunkTier.STASH_STORAGE;
                if (alreadyStash)
                {
                    flagged.put(pohSyntheticKey--, pohEntry);
                }
                else if (!flagged.containsKey(id))
                {
                    flagged.put(id, pohEntry);
                }
            }
        }

        // Pre-collect curated database entries — one O(1)-lookup pass through bankedIds,
        // partitioned into three buckets for priorities 3, 5, and 6.
        List<JunkEntry> questRedList    = new ArrayList<>();
        List<JunkEntry> definiteRedList = new ArrayList<>();
        List<JunkEntry> yellowList      = new ArrayList<>();
        {
            Map<Integer, JunkEntry> entriesById = JunkDatabase.getEntriesByItemId();
            for (int id : bankedIds)
            {
                JunkEntry entry = entriesById.get(id);
                if (entry == null)                                                         continue;
                if (userWhitelist.contains(id))                                            continue;
                if (entry.tier == JunkTier.RED    && !config.showRedTier())                continue;
                if (entry.tier == JunkTier.YELLOW && !config.showYellowTier())             continue;
                if (entry.requiredQuest != null
                    && entry.requiredQuest.getState(client) != QuestState.FINISHED)        continue;
                if (entry.getRequiredQuest2() != null
                    && entry.getRequiredQuest2().getState(client) != QuestState.FINISHED)  continue;

                if (entry.tier == JunkTier.YELLOW)
                {
                    boolean upgradePresent = false;
                    for (int upgradeId : entry.upgradeItemIds)
                    {
                        if (bankedIds.contains(upgradeId)) { upgradePresent = true; break; }
                    }
                    if (!upgradePresent) continue;
                    yellowList.add(entry);
                }
                else if (entry.tier == JunkTier.RED && entry.requiredQuest != null)
                {
                    questRedList.add(entry);
                }
                else if (entry.tier == JunkTier.RED)
                {
                    definiteRedList.add(entry);
                }
            }
        }

        // --- PRIORITY 3: Quest-related items (RED — quest gate required and satisfied) ---
        for (JunkEntry e : questRedList)
        {
            flagged.putIfAbsent(e.itemId, e);
        }

        // --- PRIORITY 4: Holiday / event items (reclaimable from Diango in Draynor Village) ---
        if (config.showHolidayTier())
        {
            for (int id : bankedIds)
            {
                if (flagged.containsKey(id) || userWhitelist.contains(id)) continue;
                if (!JunkDatabase.HOLIDAY_ITEMS.contains(id)) continue;
                String name = itemManager.getItemComposition(id).getName();
                JunkEntry holidayEntry = new JunkEntry(id, name, JunkTier.HOLIDAY,
                    "Holiday item — safe to drop, reclaimable from Diango in Draynor Village.");
                String wikiOverride = JunkDatabase.HOLIDAY_WIKI_URLS.get(id);
                if (wikiOverride != null) holidayEntry.withWikiUrl(wikiOverride);
                flagged.putIfAbsent(id, holidayEntry);
            }
        }

        // --- PRIORITY 5: Definite junk items (RED — no quest gate) ---
        for (JunkEntry e : definiteRedList)
        {
            flagged.putIfAbsent(e.itemId, e);
        }

        // --- PRIORITY 6: Conditional items (YELLOW — upgrade item present in bank) ---
        for (JunkEntry e : yellowList)
        {
            flagged.putIfAbsent(e.itemId, e);
        }

        // --- PRIORITY 7: User-Flagged items (CUSTOM — manually marked via right-click) ---
        if (config.showCustom())
        {
            for (int customId : userCustomFlags)
            {
                if (bankedIds.contains(customId) && !userWhitelist.contains(customId))
                {
                    String name = itemManager.getItemComposition(customId).getName();
                    flagged.putIfAbsent(customId, new JunkEntry(customId, name, JunkTier.CUSTOM,
                        "Manually flagged as junk by you."));
                }
            }
        }

        // Chat notification: fire once when the bank first opens with junk
        if (config.chatNotification() && !flagged.isEmpty() && (notificationArmed || manualScanNotify))
        {
            // Distinct bank slots, not map entries: dual-tier / dual-category items
            // hold multiple synthetic-key entries but occupy a single bank slot.
            int count = (int) flagged.values().stream()
                .map(je -> je.itemId).distinct().count();
            String trigger = manualScanNotify ? "Manual scan" : "Bank opened";
            chatMessageManager.queue(QueuedMessage.builder()
                .type(ChatMessageType.GAMEMESSAGE)
                .runeLiteFormattedMessage(
                    "<col=ff6644>[Dumb Old Man]</col> "
                    + trigger + ": "
                    + count + " junk item" + (count == 1 ? "" : "s")
                    + " found (as of this scan).")
                .build());
        }
        manualScanNotify = false;
        notificationArmed = flagged.isEmpty();

        activeFlagged = Collections.unmodifiableMap(flagged);
        log.debug("BankJunk: {} items flagged", flagged.size());

        // Build quest-warning set: items that are flagged but whose diaryWarningQuest
        // is not yet finished. Must happen on client thread before handing off to EDT.
        Set<Integer> questWarnings = new HashSet<>();
        for (Map.Entry<Integer, JunkEntry> e : flagged.entrySet())
        {
            JunkEntry entry = e.getValue();
            if (entry.getDiaryWarningQuest() != null
                && entry.getDiaryWarningQuest().getState(client) != QuestState.FINISHED)
            {
                questWarnings.add(entry.itemId);
            }
        }

        // Build whitelist name map for the panel's "Kept Items" section.
        // ItemComposition lookup is thread-safe (cached in ItemManager).
        Map<Integer, String> whitelistNames = new HashMap<>();
        for (int id : userWhitelist)
        {
            String name = itemManager.getItemComposition(id).getName();
            if (name != null && !name.equals("null"))
            {
                whitelistNames.put(id, name);
            }
        }

        // Snapshot all maps before handing off to the EDT.
        // Skip the Swing rebuild entirely when the panel isn't visible — activeFlagged
        // is already updated above for the overlay; the panel will pick up the latest
        // data the next time it becomes visible via an explicit refresh call.
        final Map<Integer, JunkEntry>  snapshot         = new HashMap<>(flagged);
        final Map<Integer, Integer>    qtySnapshot      = new HashMap<>(quantities);
        final Set<Integer>             warningSnapshot  = new HashSet<>(questWarnings);
        final Map<Integer, String>     whitelistSnapshot = new HashMap<>(whitelistNames);
        SwingUtilities.invokeLater(() ->
        {
            panel.refresh(snapshot, qtySnapshot, warningSnapshot, whitelistSnapshot);
            // H1: update the "Last scanned: HH:mm" timestamp in the panel header
            panel.updateScanTimestamp();
        });
    }

    // -------------------------------------------------------------------------
    // User list management (whitelist / custom flags)
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // Save-and-rescan helpers — call after mutating userWhitelist / userCustomFlags
    // -------------------------------------------------------------------------

    /** Persists user lists and triggers a normal rescan on the client thread. */
    private void saveAndRescan()
    {
        saveUserLists();
        clientThread.invokeLater(() ->
        {
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            rebuildFlaggedItems(bank);
        });
    }

    /**
     * Persists user lists and triggers a forced rescan (bypasses dirty-hash check).
     * Use when the change may not alter the bank contents but should still update
     * the flagged set (e.g. removing a custom flag or removing from whitelist).
     */
    private void saveAndRescanForced()
    {
        saveUserLists();
        clientThread.invokeLater(() ->
        {
            forceScan = true;
            ItemContainer bank = client.getItemContainer(InventoryID.BANK);
            rebuildFlaggedItems(bank);
        });
    }

    // -------------------------------------------------------------------------
    // Whitelist management
    // -------------------------------------------------------------------------

    /** Whitelists all supplied IDs in one batch: one config save, one rescan. */
    public void addAllToWhitelist(List<Integer> itemIds)
    {
        userWhitelist.addAll(itemIds);
        saveAndRescanForced();
    }

    public void addToWhitelist(int itemId)
    {
        userWhitelist.add(itemId);
        saveAndRescanForced();
    }

    public void removeFromWhitelist(int itemId)
    {
        userWhitelist.remove(itemId);
        saveAndRescanForced();
    }

    /** Removes all supplied IDs from the whitelist in one batch. */
    public void removeAllFromWhitelist(List<Integer> itemIds)
    {
        userWhitelist.removeAll(itemIds);
        saveAndRescanForced();
    }

    // -------------------------------------------------------------------------
    // Custom-flag management
    // -------------------------------------------------------------------------

    public void addCustomFlag(int itemId)
    {
        userCustomFlags.add(itemId);
        userWhitelist.remove(itemId); // un-whitelist if present
        saveAndRescanForced();
    }

    /**
     * Removes an item from the custom-flag set so it is no longer shown as junk.
     * Distinct from whitelisting — this simply stops flagging the item as custom junk.
     */
    public void removeCustomFlag(int itemId)
    {
        userCustomFlags.remove(itemId);
        saveAndRescanForced();
    }

    public void removeAllCustomFlags(List<Integer> itemIds)
    {
        userCustomFlags.removeAll(itemIds);
        saveAndRescanForced();
    }

    public void addAllCustomFlags(List<Integer> itemIds)
    {
        userCustomFlags.addAll(itemIds);
        userWhitelist.removeAll(itemIds);
        saveAndRescanForced();
    }

    public boolean isWhitelisted(int itemId)
    {
        return userWhitelist.contains(itemId);
    }

    public boolean isCustomFlagged(int itemId)
    {
        return userCustomFlags.contains(itemId);
    }

    // -------------------------------------------------------------------------
    // Persistence
    // -------------------------------------------------------------------------

    @Inject private ConfigManager configManager;

    private static final String CFG_WHITELIST    = "whitelist";
    private static final String CFG_CUSTOM_FLAGS = "customFlags";
    private static final String CFG_STASH_PREFIX = "stashStored_";

    private void saveUserLists()
    {
        configManager.setConfiguration(BankJunkConfig.GROUP, CFG_WHITELIST,
            intSetToString(userWhitelist));
        configManager.setConfiguration(BankJunkConfig.GROUP, CFG_CUSTOM_FLAGS,
            intSetToString(userCustomFlags));
    }

    private void loadUserLists()
    {
        userWhitelist.clear();
        userCustomFlags.clear();
        String wl = configManager.getConfiguration(BankJunkConfig.GROUP, CFG_WHITELIST);
        String cf = configManager.getConfiguration(BankJunkConfig.GROUP, CFG_CUSTOM_FLAGS);
        if (wl != null) stringToIntSet(wl, userWhitelist);
        if (cf != null) stringToIntSet(cf, userCustomFlags);
    }

    // --- STASH state persistence (per RSN) ---

    private void saveStashState()
    {
        if (client.getLocalPlayer() == null)
        {
            return;
        }
        // Serialise as comma-separated enum constant names, e.g.
        // "EASY_DRAYNOR_MARKETPLACE,MEDIUM_CASTLE_WARS_LANTHUS"
        StringBuilder sb = new StringBuilder();
        for (BankJunkStashUnit unit : filledStashUnits)
        {
            if (sb.length() > 0) sb.append(',');
            sb.append(unit.name());
        }
        String key = CFG_STASH_PREFIX + client.getLocalPlayer().getName();
        configManager.setConfiguration(BankJunkConfig.GROUP, key, sb.toString());
    }

    private void loadStashState()
    {
        filledStashUnits.clear();
        if (client.getLocalPlayer() == null)
        {
            return;
        }
        String key = CFG_STASH_PREFIX + client.getLocalPlayer().getName();
        String val = configManager.getConfiguration(BankJunkConfig.GROUP, key);
        if (val == null || val.isEmpty())
        {
            return;
        }
        for (String part : val.split(","))
        {
            part = part.trim();
            if (part.isEmpty()) continue;
            try
            {
                filledStashUnits.add(BankJunkStashUnit.valueOf(part));
            }
            catch (IllegalArgumentException ignored)
            {
                // Stale entry from a renamed enum constant — skip silently.
            }
        }
    }

    private static String intSetToString(Set<Integer> set)
    {
        StringBuilder sb = new StringBuilder();
        for (int id : set)
        {
            if (sb.length() > 0) sb.append(',');
            sb.append(id);
        }
        return sb.toString();
    }

    private static void stringToIntSet(String s, Set<Integer> out)
    {
        for (String part : s.split(","))
        {
            part = part.trim();
            if (!part.isEmpty())
            {
                try { out.add(Integer.parseInt(part)); }
                catch (NumberFormatException ignored) {}
            }
        }
    }

    // -------------------------------------------------------------------------
    // Panel management
    // -------------------------------------------------------------------------

    private void addPanel()
    {
        BufferedImage icon;
        try
        {
            icon = ImageUtil.loadImageResource(getClass(), "/com/bankjunk/icon.png");
        }
        catch (Exception e)
        {
            icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        }

        navButton = NavigationButton.builder()
            .tooltip("Dumb Old Man")
            .icon(icon)
            .priority(8)
            .panel(panel)
            .build();
        clientToolbar.addNavigation(navButton);
    }

    private void removePanel()
    {
        if (navButton != null)
        {
            clientToolbar.removeNavigation(navButton);
            navButton = null;
        }
    }

    @Provides
    BankJunkConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(BankJunkConfig.class);
    }
}

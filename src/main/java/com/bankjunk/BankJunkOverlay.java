package com.bankjunk;

import net.runelite.api.Client;
import net.runelite.api.widgets.ComponentID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Map;

public class BankJunkOverlay extends Overlay
{
    private final Client client;
    private final BankJunkPlugin plugin;
    private final BankJunkConfig config;
    private final ItemManager itemManager;
    private final TooltipManager tooltipManager;

    @Inject
    BankJunkOverlay(Client client, BankJunkPlugin plugin, BankJunkConfig config,
                    ItemManager itemManager, TooltipManager tooltipManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.itemManager = itemManager;
        this.tooltipManager = tooltipManager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        // Get the bank item container widget
        Widget bankContainer = client.getWidget(ComponentID.BANK_ITEM_CONTAINER);
        if (bankContainer == null || bankContainer.isHidden())
        {
            return null;
        }

        Widget[] children = bankContainer.getDynamicChildren();
        if (children == null)
        {
            return null;
        }

        Map<Integer, JunkEntry> flagged = plugin.getActiveFlagged();
        boolean doHighlight = config.highlightInBank();
        boolean debugIds    = config.debugShowIds();

        // Early-out only if nothing to do at all
        if (!doHighlight && !debugIds)
        {
            return null;
        }

        // Clip all rendering to the item grid viewport.
        //
        // In OSRS's widget system, a scrollable container's getHeight() is the
        // VIEWPORT height (what's actually visible), not the full content height
        // (that's getScrollHeight()). So bankContainer.getBounds() already gives
        // us the correct clip rectangle: x/y = top-left of the item grid (below
        // the tab row), width/height = the visible item grid area only.
        //
        // Using bankContainer.getParent().getBounds() was wrong — the parent
        // widget encompasses the full bank window including the tab row above the
        // grid, causing highlights to bleed into that area.
        Rectangle visibleArea = bankContainer.getBounds();

        java.awt.Shape originalClip = graphics.getClip();
        graphics.setClip(visibleArea);

        int mouseX = client.getMouseCanvasPosition().getX();
        int mouseY = client.getMouseCanvasPosition().getY();

        for (Widget item : children)
        {
            if (item == null || item.isHidden() || item.getItemId() <= 0)
            {
                continue;
            }

            int itemId = item.getItemId();

            // Resolve noted items to base ID
            net.runelite.api.ItemComposition comp = itemManager.getItemComposition(itemId);
            int baseId = (comp.getNote() != -1) ? comp.getLinkedNoteId() : itemId;

            Rectangle bounds = item.getBounds();

            // Skip items whose bounds are entirely outside the visible area (saves work)
            if (!visibleArea.intersects(bounds))
            {
                continue;
            }

            boolean hovered  = bounds.contains(mouseX, mouseY);

            JunkEntry entry = flagged.get(baseId);

            if (entry != null && doHighlight)
            {
                Color highlight = resolveColor(entry.tier);

                // Draw semi-transparent fill over the item
                graphics.setColor(highlight);
                graphics.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);

                // Thin border for visibility
                Color border = highlight.darker();
                graphics.setColor(new Color(border.getRed(), border.getGreen(), border.getBlue(), 220));
                graphics.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);

                // Junk tooltip — append item ID when debug mode is on
                if (hovered)
                {
                    String tip = "<col=ff6644>" + entry.tier.displayName + "</col>: " + entry.name
                        + "<br>" + entry.reason;
                    if (debugIds)
                    {
                        tip += "<br><col=888888>Item ID: " + baseId + "</col>";
                    }
                    tooltipManager.add(new Tooltip(tip));
                }
            }
            else if (hovered && debugIds)
            {
                // Show item ID for any hovered item (flagged or not) when debug is on
                tooltipManager.add(new Tooltip("<col=888888>Item ID: " + baseId + "</col>"));
            }
        }

        // Restore clip so other overlays are unaffected
        graphics.setClip(originalClip);

        return null;
    }

    private Color resolveColor(JunkTier tier)
    {
        switch (tier)
        {
            case RED:    return config.redColor();
            case YELLOW: return config.yellowColor();
            case CUSTOM:       return config.customColor();
            case HOLIDAY:       return config.holidayColor();
            case POH_STORAGE:   return config.pohStorageColor();
            case STASH_STORAGE: return config.stashStorageColor();
            default: throw new IllegalStateException("Unhandled JunkTier: " + tier);
        }
    }
}

package com.bankjunk;

import java.awt.Color;

/**
 * Classification tiers for junk items.
 */
public enum JunkTier
{
    /**
     * Definitively useless — no secondary purpose, safe to drop.
     * Highlighted in red by default.
     */
    RED("Definite Junk", new Color(220, 50, 50, 150)),

    /**
     * Conditionally useless — only flagged when a prerequisite (upgrade)
     * item is also present in the bank.
     * Example: Silverlight is junk only if Arclight is in the bank.
     * Highlighted in yellow by default.
     */
    YELLOW("Conditional Junk", new Color(220, 180, 50, 150)),

    /**
     * Manually flagged by the user via the panel's right-click context menu.
     * Highlighted in orange by default.
     */
    CUSTOM("User-Flagged", new Color(220, 120, 50, 150)),

    /**
     * Holiday / seasonal event item that can be safely dropped and reclaimed from
     * Diango in Draynor Village (or stored in the player-owned house toy box).
     * Tradeable holiday rares (party hats, H'ween masks, Santa hat) are NOT
     * included — only untradeable reclaimable items.
     * Highlighted in teal by default.
     */
    HOLIDAY("Holiday Item", new Color(50, 200, 200, 150)),

    /**
     * Item that can be stored in the player-owned house costume room.
     * Keeping it in the bank wastes a bank slot when the POH can hold it.
     * Highlighted in green by default.
     */
    POH_STORAGE("POH Storable", new Color(160, 80, 220, 150)),

    /**
     * Item that can be deposited into an unfilled STASH unit to free a bank slot.
     * The unit has not yet been marked as filled — depositing it would clear this flag.
     * Highlighted in blue by default.
     */
    STASH_STORAGE("Stash Storable", new Color(50, 150, 220, 150));

    public final String displayName;
    public final Color defaultColor;

    JunkTier(String displayName, Color defaultColor)
    {
        this.displayName = displayName;
        this.defaultColor = defaultColor;
    }
}

package com.bankjunk;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.Color;

@ConfigGroup(BankJunkConfig.GROUP)
public interface BankJunkConfig extends Config
{
    String GROUP = "bankjunk";

    // -------------------------------------------------------------------------
    // Sections
    // -------------------------------------------------------------------------

    @ConfigSection(
        name = "General",
        description = "Plugin display options",
        position = -1
    )
    String generalSection = "general";

    @ConfigSection(
        name = "Tiers",
        description = "Highlight colours and detection settings per tier",
        position = 0
    )
    String tiersSection = "tiers";

    @ConfigSection(
        name = "Advanced",
        description = "Notifications and debug options",
        position = 2
    )
    String advancedSection = "advanced";

    // -------------------------------------------------------------------------
    // General
    // -------------------------------------------------------------------------

    @ConfigItem(
        keyName = "showPanel",
        name = "Show Side Panel",
        description = "Open the Dumb Old Man panel in the sidebar.",
        section = generalSection,
        position = 0
    )
    default boolean showPanel()
    {
        return true;
    }

    // -------------------------------------------------------------------------
    // Tiers — colour then detection toggle, in detection priority order
    // -------------------------------------------------------------------------

    @ConfigItem(
        keyName = "highlightInBank",
        name = "Highlight in Bank",
        description = "Tint junk items with a colour overlay directly in the bank grid.",
        section = tiersSection,
        position = 0
    )
    default boolean highlightInBank()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "pohStorageColor",
        name = "POH Item Color",
        description = "Highlight colour for items that can be stored in the player-owned house costume room.",
        section = tiersSection,
        position = 1
    )
    default Color pohStorageColor()
    {
        return new Color(160, 80, 220, 160);
    }

    @ConfigItem(
        keyName = "showPohStorageTier",
        name = "Highlight POH Storable Items",
        description = "Flag items that can be stored in the player-owned house costume room, freeing up bank space.",
        section = tiersSection,
        position = 2
    )
    default boolean showPohStorageTier()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "stashStorageColor",
        name = "STASH Item Color",
        description = "Highlight colour for items that can be stored in a STASH unit to free bank space.",
        section = tiersSection,
        position = 3
    )
    default Color stashStorageColor()
    {
        return new Color(50, 19, 224, 160);
    }

    @ConfigItem(
        keyName = "showStashStorageTier",
        name = "Highlight STASH Storable Items",
        description = "Flag bank items that can be stored in a STASH unit to free bank space.",
        section = tiersSection,
        position = 4
    )
    default boolean showStashStorageTier()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "holidayColor",
        name = "Holiday Color",
        description = "Highlight colour for holiday and seasonal event items reclaimable from Diango.",
        section = tiersSection,
        position = 5
    )
    default Color holidayColor()
    {
        return new Color(50, 200, 200, 160);
    }

    @ConfigItem(
        keyName = "showHolidayTier",
        name = "Highlight Holiday/Event Items",
        description = "Flag holiday and seasonal event items that can be safely dropped and reclaimed from Diango in Draynor Village. Does NOT flag tradeable holiday rares (party hats, H'ween masks, Santa hat, etc.).",
        section = tiersSection,
        position = 6
    )
    default boolean showHolidayTier()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "customColor",
        name = "User-Flagged Color",
        description = "Highlight colour for items you manually marked as junk.",
        section = tiersSection,
        position = 7
    )
    default Color customColor()
    {
        return new Color(220, 120, 50, 160);
    }

    @ConfigItem(
        keyName = "showCustom",
        name = "Highlight User-Flagged Junk",
        description = "Show items you have manually marked as junk.",
        section = tiersSection,
        position = 8
    )
    default boolean showCustom()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "redColor",
        name = "Definite Junk Color",
        description = "Highlight colour for items that are always junk (RED tier).",
        section = tiersSection,
        position = 9
    )
    default Color redColor()
    {
        return new Color(220, 50, 50, 160);
    }

    @ConfigItem(
        keyName = "showRedTier",
        name = "Highlight Definite Junk",
        description = "Flag items from the curated list that have no remaining use.",
        section = tiersSection,
        position = 10
    )
    default boolean showRedTier()
    {
        return true;
    }

    @Alpha
    @ConfigItem(
        keyName = "yellowColor",
        name = "Conditional Color",
        description = "Highlight colour for items that are junk only because a better version is banked (YELLOW tier).",
        section = tiersSection,
        position = 11
    )
    default Color yellowColor()
    {
        return new Color(220, 180, 50, 160);
    }

    @ConfigItem(
        keyName = "showYellowTier",
        name = "Highlight Conditional Junk",
        description = "Flag items whose upgrade is already in your bank (e.g. Silverlight when Arclight is banked).",
        section = tiersSection,
        position = 12
    )
    default boolean showYellowTier()
    {
        return true;
    }

    // -------------------------------------------------------------------------
    // Advanced
    // -------------------------------------------------------------------------

    @ConfigItem(
        keyName = "chatNotification",
        name = "Chat Notification on Bank Open",
        description = "Prints a game message to chat when you open the bank and junk items are found.",
        section = advancedSection,
        position = 2
    )
    default boolean chatNotification()
    {
        return true;
    }

    @ConfigItem(
        keyName = "debugShowIds",
        name = "Debug: Show Item IDs on Hover",
        description = "Hover any bank item to see its actual item ID. Use this to verify/fix the junk database.",
        section = advancedSection,
        position = 10
    )
    default boolean debugShowIds()
    {
        return false;
    }

    @ConfigItem(
        keyName = "debugStash",
        name = "Debug: STASH Tracking Messages",
        description = "Print orange chat messages when STASH fill/empty actions are detected, showing which item IDs were recorded. Disable when not diagnosing STASH issues.",
        section = advancedSection,
        position = 11
    )
    default boolean debugStash()
    {
        return false;
    }
}

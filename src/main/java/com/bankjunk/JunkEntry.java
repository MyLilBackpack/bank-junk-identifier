package com.bankjunk;

import net.runelite.api.Quest;

/**
 * Represents a single item in the junk database.
 *
 * <h3>Quest completion check</h3>
 * <p>If {@code requiredQuest} is non-null, this item is only flagged when that
 * quest is {@code QuestState.FINISHED}. Players mid-quest will not see their
 * in-progress items incorrectly flagged as junk.</p>
 *
 * <h3>Upgrade dependency (YELLOW tier)</h3>
 * <p>{@code upgradeItemIds} holds the IDs of items that supersede this one.
 * The item is only flagged when at least one upgrade is present in the bank.</p>
 *
 * <h3>Constructor quick-reference (preferred forms)</h3>
 * <pre>
 *   // RED — junk only when quest is FINISHED (most quest items):
 *   new JunkEntry(id, "Name", "Reason", Quest.QUEST_NAME)
 *
 *   // RED — always junk regardless of quest state:
 *   new JunkEntry(id, "Name", "Reason")
 *
 *   // YELLOW — junk when upgrade is in bank, no quest check:
 *   new JunkEntry(id, "Name", JunkTier.YELLOW, "Reason", upgradeId1, upgradeId2)
 *
 *   // YELLOW — junk when quest FINISHED *and* upgrade is in bank:
 *   new JunkEntry(id, "Name", JunkTier.YELLOW, "Reason", Quest.QUEST_NAME,
 *                 new int[]{upgradeId1, upgradeId2})
 * </pre>
 */
public class JunkEntry
{
    /** The OSRS item ID (un-noted). */
    public final int itemId;

    /** Human-readable name shown in the panel and tooltip. */
    public final String name;

    /** Junk severity / condition type. */
    public final JunkTier tier;

    /** Short explanation shown in the tooltip. */
    public final String reason;

    /**
     * If non-null, only flag when this quest is {@code QuestState.FINISHED}.
     * Null means "always flag if present in bank."
     */
    public final Quest requiredQuest;

    /**
     * For YELLOW tier: IDs of items that make this item obsolete.
     * Flagged only when at least one of these is also in the bank.
     * Empty array for RED/CUSTOM entries.
     */
    public final int[] upgradeItemIds;

    /**
     * Optional second quest that must ALSO be {@code QuestState.FINISHED} before
     * this item is flagged. Use {@link #withRequiredQuest2(Quest)} to set it.
     * Null means no additional quest check.
     *
     * <p>Use this when an item is needed for two separate quests/miniquests
     * that can be completed in any order — the item is only junk when both are done.</p>
     */
    private Quest requiredQuest2 = null;

    /**
     * Optional future-use warning quest. If set and NOT {@code QuestState.FINISHED},
     * the panel displays a ⚠ badge on this row to warn the player that dropping
     * may be premature.
     *
     * <p>Use this when an item is technically junk (its main quest is done) but
     * is also consumed by a DIFFERENT quest or diary step the player hasn't
     * completed yet. Example: an item needed for both a completed quest and an
     * incomplete achievement diary task.</p>
     *
     * <p>Unlike {@code requiredQuest}, a {@code diaryWarningQuest} never blocks
     * flagging — it only adds a visible caution indicator.</p>
     */
    private Quest diaryWarningQuest = null;

    /**
     * Optional override for the OSRS wiki URL shown in the panel right-click menu.
     * If null, the panel auto-generates the URL from {@link #name}.
     * Use {@link #withWikiUrl(String)} to set this on items whose page lives at
     * an anchor or redirect (e.g. {@code Bedsheet#Ectoplasm}).
     */
    private String wikiUrl = null;

    /**
     * For {@link JunkTier#STASH_STORAGE} entries only: the clue-tier sub-group name
     * this item should appear under in the panel (e.g. "Easy STASH", "Medium STASH").
     * Set dynamically by the plugin based on which STASH units are filled.
     * Null falls back to a static lowest-tier lookup in the panel.
     */
    private String stashGroupName = null;

    // -------------------------------------------------------------------------
    // Canonical constructor (int[] — not varargs, avoids JVM signature clash)
    // -------------------------------------------------------------------------

    /**
     * Canonical constructor — all fields explicit.
     * Pass {@code new int[0]} for no upgrade dependency,
     * {@code null} for no quest check.
     */
    public JunkEntry(int itemId, String name, JunkTier tier, String reason,
                     Quest requiredQuest, int[] upgradeItemIds)
    {
        this.itemId         = itemId;
        this.name           = name;
        this.tier           = tier;
        this.reason         = reason;
        this.requiredQuest  = requiredQuest;
        this.upgradeItemIds = (upgradeItemIds != null) ? upgradeItemIds : new int[0];
    }

    // -------------------------------------------------------------------------
    // RED-tier convenience constructors
    // -------------------------------------------------------------------------

    /** RED — only flagged when {@code quest} is {@code QuestState.FINISHED}. */
    public JunkEntry(int itemId, String name, String reason, Quest quest)
    {
        this(itemId, name, JunkTier.RED, reason, quest, new int[0]);
    }

    /** RED — always flagged regardless of quest state. */
    public JunkEntry(int itemId, String name, String reason)
    {
        this(itemId, name, JunkTier.RED, reason, null, new int[0]);
    }

    // -------------------------------------------------------------------------
    // YELLOW-tier convenience constructors (varargs — safe because no Quest param)
    // -------------------------------------------------------------------------

    /**
     * YELLOW — junk when at least one upgrade is in bank (no quest check).
     * Example: {@code new JunkEntry(SILVERLIGHT, "Silverlight", YELLOW, "...", ARCLIGHT)}
     */
    public JunkEntry(int itemId, String name, JunkTier tier, String reason,
                     int... upgradeItemIds)
    {
        this(itemId, name, tier, reason, null, upgradeItemIds);
    }

    // YELLOW with quest check: use canonical constructor directly:
    //   new JunkEntry(id, name, YELLOW, reason, Quest.X, new int[]{upgradeId})

    // -------------------------------------------------------------------------
    // Optional wiki URL override
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // Getters for private optional fields
    // -------------------------------------------------------------------------

    /** @return the second required quest, or {@code null} if none. */
    public Quest getRequiredQuest2()     { return requiredQuest2; }

    /** @return the diary-warning quest, or {@code null} if none. */
    public Quest getDiaryWarningQuest()  { return diaryWarningQuest; }

    /** @return the wiki URL override, or {@code null} to auto-generate. */
    public String getWikiUrl()           { return wikiUrl; }

    /** @return the STASH sub-group name, or {@code null} if not set. */
    public String getStashGroupName()    { return stashGroupName; }

    // -------------------------------------------------------------------------
    // Fluent setters
    // -------------------------------------------------------------------------

    /**
     * Sets a custom wiki URL for this entry and returns {@code this} for
     * inline use in {@link JunkDatabase} initialisers.
     * <pre>
     *   new JunkEntry(ID, "Ectoplasm-covered bedsheet", "...", Quest.GHOSTS_AHOY)
     *       .withWikiUrl("https://oldschool.runescape.wiki/w/Bedsheet#Ectoplasm")
     * </pre>
     */
    public JunkEntry withWikiUrl(String url)
    {
        this.wikiUrl = url;
        return this;
    }

    /**
     * Sets a second required quest and returns {@code this} for inline use.
     * Both {@link #requiredQuest} and {@code quest2} must be
     * {@code QuestState.FINISHED} before the item is flagged.
     */
    public JunkEntry withRequiredQuest2(Quest quest2)
    {
        this.requiredQuest2 = quest2;
        return this;
    }

    /**
     * Sets the STASH sub-group name and returns {@code this}.
     * Used by the plugin to assign the tier label after construction.
     */
    public JunkEntry withStashGroupName(String name)
    {
        this.stashGroupName = name;
        return this;
    }

    /**
     * Sets a diary/future-use warning quest and returns {@code this} for inline use.
     * If {@code quest} is not yet {@code QuestState.FINISHED} at scan time,
     * the panel shows a ⚠ badge on this row.
     *
     * <pre>
     *   new JunkEntry(SHIELD_OF_ARRAV_CERTIFICATE, "Shield of Arrav certificate", "...",
     *                 Quest.SHIELD_OF_ARRAV_PHOENIX_GANG)
     *       .withDiaryWarning(Quest.ARDOUGNE_DIARY)  // hypothetical: if diary also uses it
     * </pre>
     */
    public JunkEntry withDiaryWarning(Quest quest)
    {
        this.diaryWarningQuest = quest;
        return this;
    }
}

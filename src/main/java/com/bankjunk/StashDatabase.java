package com.bankjunk;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Derives STASH item sets directly from {@link BankJunkStashUnit} so the two
 * files can never go out of sync.  To add or change STASH items, edit
 * {@code BankJunkStashUnit} only — this class updates automatically at
 * class-load time.
 */
public final class StashDatabase
{
    private StashDatabase() {}

    public static final Set<Integer> BEGINNER_STASH_ITEMS;
    public static final Set<Integer> EASY_STASH_ITEMS;
    public static final Set<Integer> MEDIUM_STASH_ITEMS;
    public static final Set<Integer> HARD_STASH_ITEMS;
    public static final Set<Integer> ELITE_STASH_ITEMS;
    public static final Set<Integer> MASTER_STASH_ITEMS;

    /** Union of all tier sets — iterated by the STASH scan in BankJunkPlugin. */
    public static final Set<Integer> STASH_ITEMS;

    static
    {
        Map<String, Set<Integer>> tierMap = new HashMap<>();
        Set<Integer> all = new HashSet<>();

        for (BankJunkStashUnit unit : BankJunkStashUnit.values())
        {
            tierMap.computeIfAbsent(unit.getTier(), k -> new HashSet<>())
                   .addAll(unit.getItems());
            all.addAll(unit.getItems());
        }

        BEGINNER_STASH_ITEMS = unmod(tierMap, "BEGINNER");
        EASY_STASH_ITEMS     = unmod(tierMap, "EASY");
        MEDIUM_STASH_ITEMS   = unmod(tierMap, "MEDIUM");
        HARD_STASH_ITEMS     = unmod(tierMap, "HARD");
        ELITE_STASH_ITEMS    = unmod(tierMap, "ELITE");
        MASTER_STASH_ITEMS   = unmod(tierMap, "MASTER");
        STASH_ITEMS          = Collections.unmodifiableSet(all);
    }

    private static Set<Integer> unmod(Map<String, Set<Integer>> map, String tier)
    {
        Set<Integer> s = map.get(tier);
        return s != null ? Collections.unmodifiableSet(s) : Collections.emptySet();
    }
}

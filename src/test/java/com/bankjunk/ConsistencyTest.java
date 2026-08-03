package com.bankjunk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Structural invariants for the Dumb Old Man plugin's item databases.
 *
 * <p>These guard the relationships that were previously only verified by hand
 * (see CLAUDE.md), so the build fails if a future edit breaks one of them:
 * <ul>
 *   <li>Every Toy Box item is also a Holiday item.</li>
 *   <li>The auto-derived {@link StashDatabase} sets exactly reflect
 *       {@link BankJunkStashUnit} (union + per-tier partition).</li>
 *   <li>Every STASH unit has a valid tier and a non-empty item set.</li>
 *   <li>{@link JunkDatabase#getEntries()} contains no duplicate item IDs and
 *       no blank names.</li>
 * </ul>
 */
public class ConsistencyTest
{
	private static final Set<String> VALID_TIERS = new HashSet<>(Arrays.asList(
		"BEGINNER", "EASY", "MEDIUM", "HARD", "ELITE", "MASTER"));

	/**
	 * Plugin invariant (CLAUDE.md): every POH Toy Box item ID must also be a
	 * Holiday item. If this fails, someone added a Toy Box item without adding
	 * it to HOLIDAY_ITEMS.
	 */
	@Test
	public void toyBoxIsSubsetOfHoliday()
	{
		List<Integer> missing = new ArrayList<>();
		for (Integer id : JunkDatabase.POH_TOY_BOX_ITEMS)
		{
			if (!JunkDatabase.HOLIDAY_ITEMS.contains(id))
			{
				missing.add(id);
			}
		}
		assertTrue(
			"Toy Box item IDs missing from HOLIDAY_ITEMS (add them to HOLIDAY_ITEMS too): " + missing,
			missing.isEmpty());
	}

	/** There must be exactly 119 STASH units (6 clue tiers). */
	@Test
	public void stashUnitCountIs119()
	{
		assertEquals(119, BankJunkStashUnit.values().length);
	}

	/** Every STASH unit has a recognised tier and at least one item. */
	@Test
	public void everyStashUnitIsWellFormed()
	{
		for (BankJunkStashUnit u : BankJunkStashUnit.values())
		{
			assertTrue(u + " has an unrecognised tier: " + u.getTier(),
				VALID_TIERS.contains(u.getTier()));
			assertNotNullLocation(u);
			assertFalse(u + " has an empty item set", u.getItems().isEmpty());
		}
	}

	private static void assertNotNullLocation(BankJunkStashUnit u)
	{
		assertTrue(u + " has a null world location", u.getLocation() != null);
	}

	/**
	 * The auto-derived StashDatabase sets must exactly match what
	 * BankJunkStashUnit declares: STASH_ITEMS is the union of all unit items,
	 * and each tier set is the union of that tier's unit items.
	 */
	@Test
	public void stashDatabaseMatchesUnits()
	{
		Map<String, Set<Integer>> byTier = new TreeMap<>();
		Set<Integer> all = new HashSet<>();
		for (String t : VALID_TIERS)
		{
			byTier.put(t, new HashSet<>());
		}
		for (BankJunkStashUnit u : BankJunkStashUnit.values())
		{
			byTier.get(u.getTier()).addAll(u.getItems());
			all.addAll(u.getItems());
		}

		assertEquals("STASH_ITEMS != union of all unit items",
			all, StashDatabase.STASH_ITEMS);
		assertEquals("BEGINNER mismatch", byTier.get("BEGINNER"), StashDatabase.BEGINNER_STASH_ITEMS);
		assertEquals("EASY mismatch",     byTier.get("EASY"),     StashDatabase.EASY_STASH_ITEMS);
		assertEquals("MEDIUM mismatch",   byTier.get("MEDIUM"),   StashDatabase.MEDIUM_STASH_ITEMS);
		assertEquals("HARD mismatch",     byTier.get("HARD"),     StashDatabase.HARD_STASH_ITEMS);
		assertEquals("ELITE mismatch",    byTier.get("ELITE"),    StashDatabase.ELITE_STASH_ITEMS);
		assertEquals("MASTER mismatch",   byTier.get("MASTER"),   StashDatabase.MASTER_STASH_ITEMS);
	}

	/**
	 * No duplicate item IDs in the quest ENTRIES list, and no blank names.
	 * (JunkDatabase already throws at class-load on a dup, but this pins it
	 * with a readable message and also checks names.)
	 */
	@Test
	public void entriesHaveUniqueIdsAndNames()
	{
		Set<Integer> seen = new HashSet<>();
		List<Integer> dups = new ArrayList<>();
		for (JunkEntry e : JunkDatabase.getEntries())
		{
			if (!seen.add(e.itemId))
			{
				dups.add(e.itemId);
			}
			assertTrue("Entry with id " + e.itemId + " has a blank name",
				e.name != null && !e.name.trim().isEmpty());
		}
		assertTrue("Duplicate item IDs in ENTRIES: " + dups, dups.isEmpty());
	}
}

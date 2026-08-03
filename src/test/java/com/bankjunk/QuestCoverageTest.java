package com.bankjunk;

import net.runelite.api.Quest;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Completeness guard for quest junk coverage.
 *
 * <p>Every {@link Quest} value must be accounted for: it either gates at least one
 * {@link JunkEntry} in {@code JunkDatabase.ENTRIES}, or it is explicitly listed in
 * {@link JunkDatabase#REVIEWED_NO_JUNK_QUESTS} as reviewed with no junk items.
 *
 * <p>This fails the build if a future author adds a new quest to RuneLite's
 * {@code Quest} enum (or a new quest section) without either adding its junk items
 * or marking it reviewed — so nothing can be silently missed.
 */
public class QuestCoverageTest
{
	@Test
	public void everyQuestIsGatedOrReviewedNoJunk()
	{
		Set<Quest> gated = JunkDatabase.gatedQuests();

		for (Quest q : Quest.values())
		{
			boolean accountedFor =
				gated.contains(q) || JunkDatabase.REVIEWED_NO_JUNK_QUESTS.contains(q);

			assertTrue(
				q + " is neither gated in ENTRIES nor listed in REVIEWED_NO_JUNK_QUESTS. "
					+ "Add its junk items, or add it to REVIEWED_NO_JUNK_QUESTS if it has none.",
				accountedFor);
		}
	}

	/**
	 * Sanity: a quest should not be in both buckets (gated AND marked no-junk).
	 */
	@Test
	public void noQuestIsBothGatedAndNoJunk()
	{
		Set<Quest> gated = JunkDatabase.gatedQuests();

		for (Quest q : JunkDatabase.REVIEWED_NO_JUNK_QUESTS)
		{
			assertTrue(
				q + " is listed as REVIEWED_NO_JUNK_QUESTS but also gates a JunkEntry — "
					+ "remove it from the no-junk set.",
				!gated.contains(q));
		}
	}
}

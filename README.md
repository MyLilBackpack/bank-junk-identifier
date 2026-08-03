# Dumb Old Man

A RuneLite plugin that highlights the items cluttering your Old School RuneScape
bank that you most likely no longer need — quest leftovers, holiday junk, clue
STASH items, and player-owned-house storables — while staying aware of upgrades
so it never tells you to bin something you still use.

## What it flags

The scanner runs every time your bank opens (and is debounced during bulk
deposits), sorting matches into prioritised tiers:

1. **STASH storable** — emote-clue items that can be stored in a built STASH unit.
   Aware of which clue tier(s) a unit belongs to.
2. **POH storable** — items storable in the Costume Room (Armour Case, Cape Rack,
   Magic Wardrobe, Treasure Chest, Fancy Dress Box, Toy Box) of your player-owned house.
3. **Quest items (RED, gated)** — quest rewards/leftovers that are only flagged
   once the relevant quest is *finished*, so pre-quest items are never touched.
4. **Holiday / event items** — safe to drop; reclaimable from Diango in Draynor Village.
5. **Definite junk (RED, ungated)** — items with no remaining use.
6. **Conditional junk (YELLOW)** — flagged only when a specific upgrade item is also
   in your bank (e.g. a base item made redundant by an upgrade you already own).
7. **Custom** — anything you right-click and mark as junk yourself.

Items you want to keep can be whitelisted; they move to a separate **Kept Items**
section instead of being re-flagged.

## Features

- Side panel listing every flagged item, grouped by tier and sortable.
- Optional in-bank highlight overlay with hover tooltips explaining *why* each
  item was flagged.
- "Slots freed" summary counting distinct bank slots (dual-tier items are counted once).
- Optional chat notification when a bank with junk is opened.
- Right-click actions: whitelist ("keep"), flag as custom junk, and "View on Wiki".
- Quest-state and STASH-fill awareness so suggestions reflect your account.

## Configuration

All toggles live under the **Dumb Old Man** config (group `bankjunk`),
including per-tier show/hide switches (STASH, POH, Holiday, RED quest, YELLOW
conditional, Custom), the in-bank highlight, the side panel, the chat
notification, and developer/debug options (show item IDs, STASH debug).

## Data & privacy

This plugin makes **no network calls** and sends **no data** anywhere. All item
data is bundled locally. The only outbound action is the optional "View on Wiki"
menu item, which opens the OSRS Wiki in your default browser via RuneLite's
`LinkBrowser`.

## Building / contributing

Requires JDK 11 and the RuneLite example-plugin Gradle setup with
`runeLiteVersion = 'latest.release'`. Item data lives in `JunkDatabase.java`
(quest/holiday/POH sets), `BankJunkStashUnit.java` (the 119 clue STASH units),
and `StashDatabase.java` (auto-derived STASH item sets). Consistency invariants
are guarded by `ConsistencyTest` and `QuestCoverageTest`.

## License

BSD 2-Clause (add a `LICENSE` file before submission — see SHIP_CHECKLIST.md).

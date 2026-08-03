package com.bankjunk;

import net.runelite.api.coords.WorldPoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Every STASH unit in OSRS with its world tile, tier, and required item IDs.
 * Data sourced from https://oldschool.runescape.wiki/w/STASH_unit
 *
 * Item IDs cross-referenced against StashDatabase.java (verified 2026-06-22).
 *
 * World coordinates: from OSRS wiki interactive map data and community
 * sources (Explv's map / OSRS wiki maplink data). All coordinates and
 * per-unit item IDs are verified against the wiki / Ryan's in-game
 * checks as of 2026-08-03; none remain outstanding.
 *
 * Notes on "any" items:
 *   All variant IDs for each "any" group are now stored directly in the relevant
 *   unit's item set so that fill-state suppression works for any variant deposited.
 *   Groups and their full ID sets (also in StashDatabase.java for reference):
 *
 *   - Team cape (any)           : 4315–4413 (step 2, all 50)
 *   - Rune heraldic helm (any)  : 10286, 10288, 10290, 10292, 10294 (h1–h5)
 *   - Rune heraldic shield (any): 7336, 7342, 7348, 7354, 7360 (h1–h5)
 *   - Stole (any)               : 10470 Sara, 10472 Guthix, 10474 Zamorak,
 *                                 12201 Ancient, 12257 Armadyl, 12269 Bandos
 *   - Crozier (any)             : 10440 Sara, 10442 Guthix, 10444 Zamorak,
 *                                 12199 Ancient, 12263 Armadyl, 12275 Bandos
 *   - Headband (any)            : 2645 red, 2647 black, 2649 brown,
 *                                 12299 white, 12301 blue, 12303 gold,
 *                                 12305 pink, 12307 green
 *   - Mitre (any)               : 10452 Sara, 10454 Guthix, 10456 Zamorak,
 *                                 12203 Ancient, 12259 Armadyl, 12271 Bandos
 *   - God book (any)            : 3840 Holy, 3842 Unholy, 3844 Balance,
 *                                 12608 War, 12610 Law, 12612 Darkness
 *   - Bob shirt (any)           : 10316 red, 10318 blue, 10320 green,
 *                                 10322 black, 10324 purple
 *   - Bandana eyepatch (any)    : 8924 white, 8925 red, 8926 blue, 8927 brown
 *   - Pirate bandana (any)      : 7112 white, 7124 red, 7130 blue, 7136 brown
 *   - Boater (any)              : 7319 red, 7321 orange, 7323 green, 7325 blue,
 *                                 7327 black, 12309 pink, 12311 purple, 12313 white
 *
 * Dragon spear: 1249 (unp) is the base; 28041 (cr) is also accepted.
 * Dragon med helm: 1149 is the base; 28057 (cr) is also accepted.
 * Dragon sq shield: 1187 is the base; 28059 (cr) is also accepted.
 * Dragon boots: 11840 is the base; 28055 (cr), primordial (13239),
 *   and avernic treads variants are also accepted for the Hero's Guild unit.
 * Helm of neitiznot: 10828 is the base; 28070 (or) is also accepted.
 * Amulet of glory: accepts any charged or uncharged variant (1704–1712, 11976, 11978).
 * Amulet of eternal glory (19707) is also accepted where glory is listed.
 */
public enum BankJunkStashUnit
{
    // =========================================================================
    // BEGINNER (3 units)
    // Construction level 12 | 2 planks + 10 nails
    // =========================================================================

    /** Outside Aris' tent on Varrock Square */
    BEGINNER_VARROCK_ARIS_TENT(
        new WorldPoint(3204, 3424, 0), "BEGINNER",
        setOf(1635,  // Gold ring
              1654)  // Gold necklace
    ),

    /** Outside Bob's Brilliant Axes, Lumbridge */
    BEGINNER_BOBS_BRILLIANT_AXES(
        new WorldPoint(3231, 3203, 0), "BEGINNER",
        setOf(1061,  // Leather boots
              1351)  // Bronze axe
    ),

    /** Outside Thessalia's Fine Clothes, Varrock (next to Aris' tent) */
    BEGINNER_THESSALIAS_FINE_CLOTHES(
        new WorldPoint(3209, 3416, 0), "BEGINNER",
        setOf(1949,  // Chef's hat
              1007)  // Red cape
    ),

    // =========================================================================
    // EASY (31 units)
    // Construction level 27 | 2 planks + 10 nails
    // =========================================================================

    /** Outside the Lumbridge Swamp shed */
    EASY_LUMBRIDGE_SWAMP_SHED(
        new WorldPoint(3201, 3171, 0), "EASY",
        setOf(1205,  // Bronze dagger
              1153,  // Iron full helm
              1635)  // Gold ring
    ),

    /** Draynor Village marketplace */
    EASY_DRAYNOR_MARKETPLACE(
        new WorldPoint(3083, 3254, 0), "EASY",
        setOf(1097,  // Studded chaps
              1191,  // Iron kiteshield
              1295)  // Steel longsword
    ),

    /** Legends' Guild, outside the gates */
    EASY_LEGENDS_GUILD_GATES(
        new WorldPoint(2735, 3350, 0), "EASY",
        setOf(1067,  // Iron platelegs
              1696,  // Emerald amulet
              845)   // Oak longbow
    ),

    /** Near the Monks of Entrana in Port Sarim */
    EASY_PORT_SARIM_ENTRANA_MONKS(
        new WorldPoint(3047, 3236, 0), "EASY",
        setOf(1169,  // Coif
              1083,  // Steel plateskirt
              1656)  // Sapphire necklace
    ),

    /** Next to the fountain in Draynor Manor */
    EASY_DRAYNOR_MANOR_FOUNTAIN(
        new WorldPoint(3089, 3331, 0), "EASY",
        setOf(1115,  // Iron platebody
              1097,  // Studded chaps
              1155)  // Bronze full helm
    ),

    /** Crossroads north of Draynor Village */
    EASY_DRAYNOR_CROSSROADS_NORTH(
        new WorldPoint(3111, 3289, 0), "EASY",
        setOf(1101,  // Iron chainbody
              1637,  // Sapphire ring
              839)   // Longbow
    ),

    /** Varrock Palace Library */
    EASY_VARROCK_PALACE_LIBRARY(
        new WorldPoint(3214, 3490, 0), "EASY",
        setOf(638,   // Green robe top
              4300,  // Ham robe
              1335)  // Iron warhammer
    ),

    /** Outside the Falador Party Room */
    EASY_FALADOR_PARTY_ROOM(
        new WorldPoint(3043, 3371, 0), "EASY",
        setOf(1157,  // Steel full helm
              1119,  // Steel platebody
              1081)  // Iron plateskirt
    ),

    /** Beehives west of Catherby */
    EASY_CATHERBY_BEEHIVES(
        new WorldPoint(2764, 3438, 0), "EASY",
        setOf(1833,  // Desert shirt
              648,   // Green robe bottoms
              1353)  // Steel axe
    ),

    /** Road junction north of Rimmington */
    EASY_FALADOR_RIMMINGTON_JUNCTION(
        new WorldPoint(2981, 3278, 0), "EASY",
        setOf(658,   // Green hat
              642,   // Cream robe top
              1095)  // Leather chaps
    ),

    /** Outside Keep Le Faye */
    EASY_KEEP_LE_FAYE(
        new WorldPoint(2756, 3399, 0), "EASY",
        setOf(1169,  // Coif
              1115,  // Iron platebody
              1059)  // Leather gloves
    ),

    /** Outside the Exam Centre */
    EASY_EXAM_CENTRE(
        new WorldPoint(3353, 3343, 0), "EASY",
        setOf(1005,  // White apron
              628,   // Green boots
              1059)  // Leather gloves
    ),

    /** Emir's Arena ticket office */
    EASY_EMIRS_ARENA(
        new WorldPoint(3316, 3242, 0), "EASY",
        setOf(1101,  // Iron chainbody
              1095,  // Leather chaps
              1169)  // Coif
    ),

    /** Near Herquin's shop in Falador */
    EASY_HERQUINS_GEMS(
        new WorldPoint(2941, 3339, 0), "EASY",
        setOf(1273,  // Mithril pickaxe
              1125,  // Black platebody
              1191)  // Iron kiteshield
    ),

    /** Aubury's Rune Shop, Varrock */
    EASY_AUBURYS_RUNE_SHOP(
        new WorldPoint(3253, 3401, 0), "EASY",
        setOf(5527,  // Air tiara
              1383)  // Staff of water
    ),

    /** Behind the bar on the Pandemonium, Port Sarim */
    EASY_PANDEMONIUM(
        new WorldPoint(3045, 2963, 0), "EASY",
        setOf(1025,  // Right eye patch
              1321)  // Bronze scimitar
    ),

    /** On the bridge towards the Wizards' Tower */
    EASY_WIZARDS_TOWER_BRIDGE(
        new WorldPoint(3115, 3194, 0), "EASY",
        setOf(1137,  // Iron med helm
              1639,  // Emerald ring
              1005)  // White apron
    ),

    /** The Limestone Mine in Silvarea */
    EASY_LIMESTONE_MINE_SILVAREA(
        new WorldPoint(3372, 3498, 0), "EASY",
        setOf(1075,  // Bronze platelegs
              1269,  // Steel pickaxe
              1141)  // Steel med helm
    ),

    /** Mudskipper Point, west of the fairy ring (AIQ) */
    EASY_MUDSKIPPER_POINT(
        new WorldPoint(2987, 3110, 0), "EASY",
        setOf(1019,  // Black cape
              1095,  // Leather chaps
              1424)  // Steel mace
    ),

    /** Al Kharid mine */
    EASY_AL_KHARID_MINE(
        new WorldPoint(3303, 3289, 0), "EASY",
        setOf(1833,  // Desert shirt
              1059,  // Leather gloves
              1061)  // Leather boots
    ),

    /** Wheat field outside the Mill Lane Mill */
    EASY_MILL_LANE_MILL(
        new WorldPoint(3163, 3297, 0), "EASY",
        setOf(640,   // Blue robe top
              654,   // Turquoise robe bottoms
              843)   // Oak shortbow
    ),

    /** Rimmington mine */
    EASY_RIMMINGTON_MINE(
        new WorldPoint(2976, 3240, 0), "EASY",
        setOf(1654,  // Gold necklace
              1635,  // Gold ring
              1237)  // Bronze spear
    ),

    /** Kandarin windmill top floor */
    EASY_KANDARIN_WINDMILL(
        new WorldPoint(2635, 3385, 2), "EASY",
        setOf(640,   // Blue robe top
              4300,  // Ham robe
              5525)  // Tiara
    ),

    /** Stone circle in Taverley */
    EASY_TAVERLEY_STONE_CIRCLE(
        new WorldPoint(2924, 3478, 0), "EASY",
        setOf(579,   // Blue wizard hat
              1307,  // Bronze 2h sword
              4310)  // Ham boots
    ),

    /** Near the parrots in Ardougne Zoo */
    EASY_ARDOUGNE_ZOO_PARROT(
        new WorldPoint(2608, 3284, 0), "EASY",
        setOf(1133,  // Studded body
              1075,  // Bronze platelegs
              1379)  // Staff
    ),

    /** Outside the Fishing Guild */
    EASY_FISHING_GUILD_MASTER_FISHER(
        new WorldPoint(2608, 3393, 0), "EASY",
        setOf(1639,  // Emerald ring
              1694,  // Sapphire amulet
              1103)  // Bronze chainbody
    ),

    /** Road junction south of Sinclair Mansion */
    EASY_SINCLAIR_CAMELOT_JUNCTION(
        new WorldPoint(2735, 3534, 0), "EASY",
        setOf(1167,  // Leather cowl
              1323,  // Iron scimitar
              577)   // Blue wizard robe
    ),

    /** Next to the Sawmill operator at the Lumber Yard */
    EASY_LUMBER_YARD(
        new WorldPoint(3298, 3490, 0), "EASY",
        setOf(1131,  // Hardleather body
              1095,  // Leather chaps
              1351)  // Bronze axe
    ),

    /** Varrock Palace courtyard, north of Varrock Square */
    EASY_VARROCK_PALACE_COURTYARD(
        new WorldPoint(3211, 3456, 0), "EASY",
        setOf(1361,  // Black axe
              1169,  // Coif
              1641)  // Ruby ring
    ),

    /** Southern entrance of the Grand Exchange */
    EASY_GRAND_EXCHANGE_SOUTH(
        new WorldPoint(3159, 3464, 0), "EASY",
        setOf(1013,  // Pink skirt
              636,   // Pink robe top
              5533)  // Body tiara
    ),

    /** South-eastern corner of the Grand Museum in Civitas illa Fortis */
    EASY_CIVITAS_GRAND_MUSEUM(
        new WorldPoint(1723, 3153, 0), "EASY",
        setOf(644,   // Turquoise robe top
              1011,  // Blue skirt
              1658)  // Emerald necklace
    ),

    // =========================================================================
    // MEDIUM (25 units)
    // Construction level 42 | 2 oak planks + 10 nails
    // =========================================================================

    /** Centre of Canifis */
    MEDIUM_CANIFIS_CENTRE(
        new WorldPoint(3492, 3488, 0), "MEDIUM",
        setOf(638,   // Green robe top
              1071,  // Mithril platelegs
              1309)  // Iron 2h sword
    ),

    /** East of Barbarian Village, across the river */
    MEDIUM_BARBARIAN_VILLAGE_EAST(
        new WorldPoint(3107, 3422, 0), "MEDIUM",
        setOf(2942,  // Purple gloves
              1193,  // Steel kiteshield
              1159)  // Mithril full helm
    ),

    /** Next to Lanthus at Castle Wars */
    MEDIUM_CASTLE_WARS_LANTHUS(
        new WorldPoint(2442, 3092, 0), "MEDIUM",
        setOf(1698,  // Ruby amulet
              1329,  // Mithril scimitar
              // Team cape (any) — all 50 variants accepted
              4315, 4317, 4319, 4321, 4323, 4325, 4327, 4329, 4331, 4333,
              4335, 4337, 4339, 4341, 4343, 4345, 4347, 4349, 4351, 4353,
              4355, 4357, 4359, 4361, 4363, 4365, 4367, 4369, 4371, 4373,
              4375, 4377, 4379, 4381, 4383, 4385, 4387, 4389, 4391, 4393,
              4395, 4397, 4399, 4401, 4403, 4405, 4407, 4409, 4411, 4413)
    ),

    /** Gnome Stronghold balancing rope (plane 2) */
    MEDIUM_GNOME_STRONGHOLD_AGILITY(
        new WorldPoint(2473, 3418, 2), "MEDIUM",
        setOf(1193,  // Steel kiteshield
              1099,  // Green d'hide chaps
              2568)  // Ring of forging
    ),

    /** Observatory */
    MEDIUM_OBSERVATORY(
        new WorldPoint(2439, 3166, 0), "MEDIUM",
        setOf(1109,  // Mithril chainbody
              1099,  // Green d'hide chaps
              1698)  // Ruby amulet
    ),

    /** Digsite */
    MEDIUM_DIGSITE(
        new WorldPoint(3370, 3420, 0), "MEDIUM",
        setOf(658,   // Green hat
              6328,  // Snakeskin boots
              1267)  // Iron pickaxe
    ),

    /** Shantay Pass */
    MEDIUM_SHANTAY_PASS(
        new WorldPoint(3308, 3125, 0), "MEDIUM",
        setOf(3343,  // Pointed bruise blue snelm
              1381,  // Staff of air
              1173)  // Bronze sq shield
    ),

    /** Outside the Catherby bank */
    MEDIUM_CATHERBY_BANK(
        new WorldPoint(2808, 3441, 0), "MEDIUM",
        setOf(851,   // Maple longbow
              1099,  // Green d'hide chaps
              1137)  // Iron med helm
    ),

    /** Outside Harry's Fishing Shop, Catherby */
    MEDIUM_HARRYS_FISHING_SHOP(
        new WorldPoint(2837, 3436, 0), "MEDIUM",
        setOf(1183,  // Adamant sq shield
              8872,  // Bone dagger (unp)
              1121)  // Mithril platebody
    ),

    /** Outside the Edgeville General Store */
    MEDIUM_EDGEVILLE_GENERAL_STORE(
        new WorldPoint(3077, 3503, 0), "MEDIUM",
        setOf(1757,  // Brown apron
              1061,  // Leather boots
              1059)  // Leather gloves
    ),

    /** Entrance of the Arceuus library */
    MEDIUM_ARCEUUS_LIBRARY(
        new WorldPoint(1642, 3809, 0), "MEDIUM",
        setOf(2487,  // Blue d'hide vambraces
              4129,  // Adamant boots
              1211)  // Adamant dagger (unp)
    ),

    /** North of Mount Karuulm */
    MEDIUM_MOUNT_KARUULM_NORTH(
        new WorldPoint(1308, 3840, 0), "MEDIUM",
        setOf(1345,  // Adamant warhammer
              4127,  // Mithril boots
              2570)  // Ring of life
    ),

    /** By the fishing spot east of The Proudspire, Civitas illa Fortis */
    MEDIUM_TOWER_OF_ASCENSION_NW(
        new WorldPoint(1668, 3287, 0), "MEDIUM",
        setOf(579,   // Blue wizard hat
              577)   // Blue wizard robe
    ),

    /** Mausoleum, accessed through a tunnel via the eastern-most memorial outside Fenkenstrain's Castle */
    MEDIUM_MAUSOLEUM_FENKENSTRAIN(
        new WorldPoint(3500, 3571, 0), "MEDIUM",
        setOf(1085,  // Mithril plateskirt
              851)   // Maple longbow
    ),

    /** South of the shrine in Tai Bwo Wannai Village */
    MEDIUM_TAI_BWO_WANNAI(
        new WorldPoint(2802, 3081, 0), "MEDIUM",
        setOf(1099,  // Green d'hide chaps
              // Ring of dueling (all charges, 1–8 — equip any one)
              2552, 2554, 2556, 2558, 2560, 2562, 2564, 2566,
              1143)  // Mithril med helm
    ),

    /** Inside the Barbarian Outpost Agility Course */
    MEDIUM_BARBARIAN_OUTPOST_AGILITY(
        new WorldPoint(2541, 3550, 0), "MEDIUM",
        setOf(1119,  // Steel platebody
              853,   // Maple shortbow
              // Team cape (any) — all 50 variants accepted
              4315, 4317, 4319, 4321, 4323, 4325, 4327, 4329, 4331, 4333,
              4335, 4337, 4339, 4341, 4343, 4345, 4347, 4349, 4351, 4353,
              4355, 4357, 4359, 4361, 4363, 4365, 4367, 4369, 4371, 4373,
              4375, 4377, 4379, 4381, 4383, 4385, 4387, 4389, 4391, 4393,
              4395, 4397, 4399, 4401, 4403, 4405, 4407, 4409, 4411, 4413)
    ),

    /** Outside the Yanille bank */
    MEDIUM_YANILLE_BANK(
        new WorldPoint(2603, 3091, 0), "MEDIUM",
        setOf(1757,  // Brown apron
              1145,  // Adamant med helm
              6324)  // Snakeskin chaps
    ),

    /** Inside the ogre cage in the Combat Training Camp */
    MEDIUM_COMBAT_TRAINING_CAMP_OGRE_CAGE(
        new WorldPoint(2533, 3377, 0), "MEDIUM",
        setOf(1135,  // Green d'hide body
              1099,  // Green d'hide chaps
              1177)  // Steel sq shield
    ),

    /** Hickton's Archery Emporium, Catherby */
    MEDIUM_HICKTONS_ARCHERY_EMPORIUM(
        new WorldPoint(2822, 3443, 0), "MEDIUM",
        setOf(630,   // Blue boots
              1131,  // Hardleather body
              2961)  // Silver sickle
    ),

    /** Inside the Lumbridge Swamp Caves (STASH appears at all cave entrances) */
    MEDIUM_LUMBRIDGE_SWAMP_CAVES(
        new WorldPoint(3167, 9570, 0), "MEDIUM",
        setOf(1381,  // Staff of air
              1155,  // Bronze full helm
              1731)  // Amulet of power
    ),

    /** Outside the Seers' Village courthouse */
    MEDIUM_SEERS_VILLAGE_COURTHOUSE(
        new WorldPoint(2731, 3475, 0), "MEDIUM",
        setOf(3200,  // Adamant halberd
              4093,  // Mystic robe bottom (non-variant only)
              1643)  // Diamond ring
    ),

    /** TzHaar-Hur-Tel's Equipment Store */
    MEDIUM_TZHAAR_HUR_TEL(
        new WorldPoint(2479, 5146, 0), "MEDIUM",
        setOf(1295,  // Steel longsword
              2499,  // Blue d'hide body
              4095)  // Mystic gloves (non-variant only)
    ),

    /** North of the Shayzien combat ring */
    MEDIUM_SHAYZIEN_COMBAT_RING_NORTH(
        new WorldPoint(1541, 3631, 0), "MEDIUM",
        setOf(1123,  // Adamant platebody
              1161,  // Adamant full helm
              1073)  // Adamant platelegs (OSRS wiki confirmed)
    ),

    /** Outside the Draynor jail */
    MEDIUM_DRAYNOR_JAIL(
        new WorldPoint(3130, 3250, 0), "MEDIUM",
        setOf(1287,  // Adamant sword
              1694,  // Sapphire amulet
              1091)  // Adamant plateskirt
    ),

    /** Ortus meets Proudspire area, Civitas illa Fortis */
    MEDIUM_EAST_SALVAGER_OVERLOOK_MINE(
        new WorldPoint(1629, 3239, 0), "MEDIUM",
        setOf(851,   // Maple longbow
              1698,  // Ruby amulet
              1069)  // Steel platelegs
    ),

    // =========================================================================
    // HARD (16 units)
    // Construction level 55 | 2 teak planks + 10 nails
    // =========================================================================

    /** East of the level 19 Wilderness Obelisk */
    HARD_WILDERNESS_OBELISK(
        new WorldPoint(3243, 3662, 0), "HARD",
        setOf(1079,  // Rune platelegs
              1115,  // Iron platebody
              2487)  // Blue d'hide vambraces
    ),

    /** Lighthouse top floor (requires Horror from the Deep) */
    HARD_LIGHTHOUSE_TOP(
        new WorldPoint(2511, 3641, 2), "HARD",
        setOf(2499,  // Blue d'hide body
              2487)  // Blue d'hide vambraces
    ),

    /** Bandit Duty Free in the Wilderness Bandit Camp */
    HARD_WILDERNESS_BANDIT_CAMP(
        new WorldPoint(3027, 3699, 0), "HARD",
        setOf(1183,  // Adamant sq shield
              2487,  // Blue d'hide vambraces
              1275)  // Rune pickaxe
    ),

    /** Outside Jokul's tent at the Mountain Camp */
    HARD_MOUNTAIN_CAMP_JOKUL(
        new WorldPoint(2810, 3677, 0), "HARD",
        setOf(1163,  // Rune full helm
              2493,  // Blue d'hide chaps
              1393)  // Fire battlestaff
    ),

    /** Shilo Village bank */
    HARD_SHILO_VILLAGE_BANK(
        new WorldPoint(2852, 2955, 0), "HARD",
        setOf(4089,  // Mystic hat (non-variant only)
              5016,  // Bone spear
              1127)  // Rune platebody
    ),

    /** Easternmost part of the Kharazi Jungle */
    HARD_KHARAZI_JUNGLE_EAST(
        new WorldPoint(2952, 2932, 0), "HARD",
        setOf(// Rune heraldic shield (any) — h1–h5
              7336, 7342, 7348, 7354, 7360,
              // Stole (any) — all 6 god factions
              10470, 10472, 10474, 12201, 12257, 12269)
    ),

    /** Jiggig, near the Jiggig Dungeon entrance */
    HARD_JIGGIG(
        new WorldPoint(2478, 3047, 0), "HARD",
        setOf(1247,  // Rune spear
              1079,  // Rune platelegs
              // Rune heraldic helm (any) — h1–h5
              10286, 10288, 10290, 10292, 10294)
    ),

    /** Mess, south of Kourend Castle */
    HARD_KOUREND_CASTLE_MESS(
        new WorldPoint(1646, 3632, 0), "HARD",
        setOf(3202,  // Rune halberd
              1127,  // Rune platebody
              1725)  // Amulet of strength
    ),

    /** Fishing Guild bank */
    HARD_FISHING_GUILD_BANK(
        new WorldPoint(2593, 3409, 0), "HARD",
        setOf(2890,  // Elemental shield
              2493,  // Blue d'hide chaps
              1347)  // Rune warhammer
    ),

    /** Outside the entrance to Klenter's Pyramid */
    HARD_KLENTERS_PYRAMID(
        new WorldPoint(3291, 2780, 0), "HARD",
        setOf(2570,  // Ring of life
              1704,  // Amulet of glory (uncharged)
              1317)  // Adamant 2h sword
    ),

    /** Western entrance of the banana plantation (Karamja) */
    HARD_BANANA_PLANTATION(
        new WorldPoint(2909, 3169, 0), "HARD",
        setOf(1643,  // Diamond ring
              1731)  // Amulet of power
    ),

    /** Next to Captain Bleemadge on White Wolf Mountain */
    HARD_WHITE_WOLF_MOUNTAIN(
        new WorldPoint(2847, 3498, 0), "HARD",
        setOf(1071,  // Mithril platelegs
              2570,  // Ring of life
              1359)  // Rune axe
    ),

    /** Next to Terry Balando inside the Exam Centre */
    HARD_EXAM_CENTRE_TERRY_BALANDO(
        new WorldPoint(3356, 3333, 0), "HARD",
        setOf(1401,  // Mystic fire staff
              11092, // Diamond bracelet
              4131)  // Rune boots
    ),

    /** Blighted Volcano in north-eastern Wilderness */
    HARD_BLIGHTED_VOLCANO(
        new WorldPoint(3368, 3930, 0), "HARD",
        setOf(// Headband (any) — 8 variants
              2645, 2647, 2649, 12299, 12301, 12303, 12305, 12307,
              // Crozier (any) — all 6 god factions
              10440, 10442, 10444, 12199, 12263, 12275)
    ),

    /** At the start of the Agility Pyramid */
    HARD_AGILITY_PYRAMID(
        new WorldPoint(3359, 2829, 0), "HARD",
        setOf(4091,  // Mystic robe top
              // Rune heraldic shield (any) — h1–h5
              7336, 7342, 7348, 7354, 7360)
    ),

    /** Outside the Twilight Temple (Children of the Sun area, Civitas illa Fortis) */
    HARD_TWILIGHT_TEMPLE(
        new WorldPoint(1693, 3243, 0), "HARD",
        setOf(1303,  // Rune longsword
              1127,  // Rune platebody
              1093)  // Rune plateskirt
    ),

    // =========================================================================
    // ELITE (19 units)
    // Construction level 77 | 2 mahogany planks + 10 nails
    // =========================================================================

    /** West Ardougne church */
    ELITE_WEST_ARDOUGNE_CHURCH(
        new WorldPoint(2527, 3294, 0), "ELITE",
        setOf(1249, 28041,  // Dragon spear (base + cr)
              2495)         // Red d'hide chaps
    ),

    /** Entrance to the Lava Maze Dungeon in the middle of the Lava Maze
     *  Requires knife or 82 Agility */
    ELITE_LAVA_MAZE_DUNGEON_ENTRANCE(
        new WorldPoint(3069, 3862, 0), "ELITE",
        setOf(2497,  // Black d'hide chaps
              10069, // Spotted cape
              7445)  // Rolling pin
    ),

    /** Warriors' Guild bank */
    ELITE_WARRIORS_GUILD_BANK(
        new WorldPoint(2844, 3537, 0), "ELITE",
        setOf(10148) // Black salamander
    ),

    /** South-eastern corner of the Fishing Platform */
    ELITE_FISHING_PLATFORM_SE(
        new WorldPoint(2787, 3277, 0), "ELITE",
        setOf(7462,        // Barrows gloves
              1149, 28057, // Dragon med helm (base + cr)
              // Amulet of glory (all charges: 1–6) + Eternal glory
              1706, 1708, 1710, 1712, 11976, 11978, 19707)
    ),

    /** On top of Trollheim */
    ELITE_TROLLHEIM_TOP(
        new WorldPoint(2886, 3676, 0), "ELITE",
        setOf(3053,  // Lava battlestaff
              2491,  // Black d'hide vambraces
              9731)  // Mind shield
    ),

    /** Inside the Ancient Cavern, just below the whirlpool */
    ELITE_ANCIENT_CAVERN_WHIRLPOOL(
        new WorldPoint(1764, 5367, 1), "ELITE",
        setOf(3122,  // Granite shield
              3387,  // Splitbark body
              // Rune heraldic helm (any) — h1–h5
              10286, 10288, 10290, 10292, 10294)
    ),

    /** War Tent in the Shayzien Encampment */
    ELITE_SHAYZIEN_WAR_TENT(
        new WorldPoint(1488, 3637, 0), "ELITE",
        setOf(4093,  // Mystic robe bottom
              1201,  // Rune kiteshield
              // Bob's shirt (any) — 5 colours
              10316, 10318, 10320, 10322, 10324)
    ),

    /** Ardougne Gem Stall */
    ELITE_ARDOUGNE_GEM_STALL(
        new WorldPoint(2672, 3302, 0), "ELITE",
        setOf(11079, 11081, 11083, // Castle wars bracelet (any charge)
              1702,  // Dragonstone amulet
              2568)  // Ring of forging
    ),

    /** Charcoal furnaces, south-west of Hosidius */
    ELITE_HOSIDIUS_CHARCOAL_FURNACES(
        new WorldPoint(1712, 3470, 0), "ELITE",
        setOf(13646, 13647, // Farmer's strawhat (male/female)
              13381, // Shayzien body (5)
              20706) // Pyromancer robe
    ),

    /** Wintumber Island (Fremennik Isles) */
    ELITE_WINTUMBER_ISLAND(
        new WorldPoint(2072, 2608, 0), "ELITE",
        setOf(7539,  // Crab helmet
              7537)  // Crab claw
    ),

    /** Central Fremennik Isles mine, north-east of Neitiznot */
    ELITE_FREMENNIK_ISLES_MINE(
        new WorldPoint(2374, 3847, 0), "ELITE",
        setOf(4131,  // Rune boots
              9674,  // Proselyte hauberk
              1645)  // Dragonstone ring
    ),

    /** Near the ladder inside the Shadow Dungeon
     *  Requires ring of visibility or ring of shadows */
    ELITE_SHADOW_DUNGEON(
        new WorldPoint(2627, 5071, 0), "ELITE",
        setOf(9185,  // Rune crossbow
              3105,  // Climbing boots
              // Mitre (any) — all 6 god factions
              10452, 10454, 10456, 12203, 12259, 12271)
    ),

    /** South-eastern corner of the Edgeville Monastery */
    ELITE_EDGEVILLE_MONASTERY_SE(
        new WorldPoint(3056, 3482, 0), "ELITE",
        setOf(// God book (any) — all 6
              3840, 3842, 3844, 12608, 12610, 12612)
    ),

    /** Slayer Tower top floor, outside the gargoyle room */
    ELITE_SLAYER_TOWER_2ND_FLOOR(
        new WorldPoint(3423, 3534, 2), "ELITE",
        setOf(6724,         // Seercull
              // Combat bracelet (all charges, 1–6 — equip any one)
              11118, 11120, 11122, 11124, 11972, 11974,
              10828, 28070) // Helm of neitiznot (base + or)
    ),

    /** Fountain of Heroes in the Heroes' Guild basement */
    ELITE_HEROES_GUILD_FOUNTAIN(
        new WorldPoint(2916, 9891, 0), "ELITE",
        setOf(3389,        // Splitbark legs
              11840, 28055, // Dragon boots (base + cr)
              13239,        // Primordial boots (also accepted)
              1303)         // Rune longsword
    ),

    /** Trollweiss Mountain, after sledding towards the trollweiss flowers */
    ELITE_TROLLWEISS_MOUNTAIN(
        new WorldPoint(2782, 3787, 0), "ELITE",
        setOf(2487,        // Blue d'hide vambraces
              1249, 28041, // Dragon spear (base + cr)
              1093)        // Rune plateskirt
    ),

    /** Within the Legends' Guild main building */
    ELITE_LEGENDS_GUILD_MAIN_BUILDING(
        new WorldPoint(2727, 3371, 0), "ELITE",
        setOf(1052,  // Cape of legends
              1377,  // Dragon battleaxe
              // Amulet of glory (all charges: 1–6) + Eternal glory
              1706, 1708, 1710, 1712, 11976, 11978, 19707)
    ),

    /** Outside the Fight Arena Bar */
    ELITE_FIGHT_ARENA_BAR(
        new WorldPoint(2571, 3150, 0), "ELITE",
        setOf(// Pirate bandana OR Bandana eyepatch (any colour — equip any one)
              7112, 7124, 7130, 7136, // Pirate bandana (white, red, blue, brown)
              8924, 8925, 8926, 8927, // Bandana eyepatch (white, red, blue, brown)
              1664,  // Dragon necklace
              859)   // Magic longbow
    ),

    /** Outside the temple where Metzli is first met, SE of Civitas illa Fortis */
    ELITE_CIVITAS_METZLI_TEMPLE(
        new WorldPoint(1702, 3079, 0), "ELITE",
        setOf(28933, // Sunfire fanatic helm (any single piece — cheapest varies)
              28936, // Sunfire fanatic cuirass
              28939) // Sunfire fanatic chausses
    ),

    // =========================================================================
    // MASTER (25 units)
    // Construction level 88 | 2 mahogany planks + 1 gold leaf + 10 nails
    //
    // Coordinates verified against RuneLite STASHUnit.java (HH_MASTER* entries).
    // Item sets verified against RuneLite EmoteClue.java + ItemID.java constants.
    // =========================================================================

    /** Eastern part of Lava Dragon Isle — "Panic by the big egg" */
    MASTER_LAVA_DRAGON_ISLE(
        new WorldPoint(3228, 3830, 0), "MASTER",
        setOf(1149, 28057, // Dragon med helm (base + cr)
              6524,        // Toktz-ket-xil
              11037,       // Brine sabre
              1127,        // Rune platebody
              1704)        // Amulet of glory (uncharged)
    ),

    /** Near the pier in Zul-Andra — "Jump for joy" */
    MASTER_ZUL_ANDRA(
        new WorldPoint(2203, 3059, 0), "MASTER",
        setOf(7158, 28051, // Dragon 2h sword (base + cr)
              11836,       // Bandos boots
              21733,       // Guardian boots (also accepted)
              28945,       // Echo boots (also accepted)
              6568)        // Obsidian cape
    ),

    /** In the room with the Barrows chest — "Do a jig"
     *  Set item accepted, OR all 4 individual pieces (any degradation state). */
    MASTER_BARROWS_CHEST(
        new WorldPoint(3547, 9690, 0), "MASTER",
        setOf(// ── Set items ──────────────────────────────────────────────────
              12873, // Guthan's armour set
              12875, // Verac's armour set
              12877, // Dharok's armour set
              12879, // Torag's armour set
              12881, // Ahrim's armour set
              12883, // Karil's armour set
              // ── Guthan's individual pieces (undamaged / 100 / 75 / 50 / 25) ──
              4724, 4904, 4905, 4906, 4907,  // Helm
              4728, 4916, 4917, 4918, 4919,  // Platebody
              4730, 4922, 4923, 4924, 4925,  // Chainskirt
              4726, 4910, 4911, 4912, 4913,  // Warspear
              // ── Verac's ─────────────────────────────────────────────────────
              4753, 4976, 4977, 4978, 4979,  // Helm
              4757, 4988, 4989, 4990, 4991,  // Brassard
              4759, 4994, 4995, 4996, 4997,  // Plateskirt
              4755, 4982, 4983, 4984, 4985,  // Flail
              // ── Dharok's ────────────────────────────────────────────────────
              4716, 4880, 4881, 4882, 4883,  // Helm
              4720, 4892, 4893, 4894, 4895,  // Platebody
              4722, 4898, 4899, 4900, 4901,  // Platelegs
              4718, 4886, 4887, 4888, 4889,  // Greataxe
              // ── Torag's ─────────────────────────────────────────────────────
              4745, 4952, 4953, 4954, 4955,  // Helm
              4749, 4964, 4965, 4966, 4967,  // Platebody
              4751, 4970, 4971, 4972, 4973,  // Platelegs
              4747, 4958, 4959, 4960, 4961,  // Hammers
              // ── Ahrim's ─────────────────────────────────────────────────────
              4708, 4856, 4857, 4858, 4859,  // Hood
              4712, 4868, 4869, 4870, 4871,  // Robetop
              4714, 4874, 4875, 4876, 4877,  // Robeskirt
              4710, 4862, 4863, 4864, 4865,  // Staff
              // ── Karil's ─────────────────────────────────────────────────────
              4732, 4928, 4929, 4930, 4931,  // Coif
              4734, 4934, 4935, 4936, 4937,  // Crossbow
              4736, 4940, 4941, 4942, 4943,  // Leathertop
              4738, 4946, 4947, 4948, 4949)  // Leatherskirt
    ),

    /** Underground Pass — "Dance in Iban's temple" */
    MASTER_IBANS_TEMPLE(
        new WorldPoint(2006, 4709, 1), "MASTER",
        setOf(1409, 12658, 33330, 33332, // Iban's staff (all variants)
              4101,        // Mystic robe top (dark)
              4103)        // Mystic robe bottom (dark)
    ),

    /** On top of the northern wall of Castle Drakan — "Wave" */
    MASTER_CASTLE_DRAKAN_NORTH_WALL(
        new WorldPoint(3563, 3379, 0), "MASTER",
        setOf(// Boater (any) — 8 colours
              7319, 7321, 7323, 7325, 7327, 12309, 12311, 12313,
              3387,        // Splitbark body
              1187, 28059) // Dragon sq shield (base + cr)
    ),

    /** 7th chamber of Jalsavrah (Pyramid Plunder) — "Yawn"
     *  3-piece set: hat + top + (robe OR kilt); all same colour (purple or red) */
    MASTER_PYRAMID_PLUNDER(
        new WorldPoint(1951, 4431, 0), "MASTER",
        setOf(26948, 26950, 26945,           // Pharaoh's sceptre (all charge variants)
              6392, 6394, 6396, 6398,        // Menaphite purple: hat / top / robe / kilt
              6400, 6402, 6404, 6406)        // Menaphite red:    hat / top / robe / kilt
    ),

    /** Outside the Soul Altar — "Spin" */
    MASTER_SOUL_ALTAR(
        new WorldPoint(1810, 3855, 0), "MASTER",
        setOf(// Dragon pickaxe or any upgraded/variant — equip any one
              11920,        // Dragon pickaxe
              12797,        // Dragon pickaxe (upgraded)
              23677,        // Dragon pickaxe (or)
              25376, 30351, // Dragon pickaxe (or) — Trailblazer / TB Reloaded
              13243, 13244, // Infernal pickaxe (charged / uncharged)
              25063, 25369, // Infernal pickaxe (or) — charged / uncharged
              30345, 30346, // Infernal pickaxe (or) TB Reloaded — charged / uncharged
              23680, 23682, // Crystal pickaxe — active / inactive
              10828, 28070, // Helm of neitiznot (base + or)
              4131)         // Rune boots
    ),

    /** Warriors' Guild bank — "Blow a raspberry" */
    MASTER_WARRIORS_GUILD(
        new WorldPoint(2845, 3545, 0), "MASTER",
        setOf(1377,         // Dragon battleaxe
              12954, // Dragon defender
              24143, // Dragon defender (locked)
              19722, // Dragon defender (t)
              27008, // Dragon defender (t) (locked)
              22322, // Avernic defender
              24186, // Avernic defender (locked)
              // Slayer helmet (any colour, any imbue method) — deposit any one
              // Format: base / NMZ(i) / SW(i) / Emir's(i)
              11864, 11865, 25177, 26674,  // Slayer helmet (base)
              19639, 19641, 25179, 26675,  // Black
              19643, 19645, 25181, 26676,  // Green
              19647, 19649, 25183, 26677,  // Red
              21264, 21266, 25185, 26678,  // Purple
              21888, 21890, 25187, 26679,  // Turquoise
              23073, 23075, 25189, 26680,  // Hydra
              24370, 24444, 25191, 26681,  // Twisted
              25898, 25900, 25902, 26682,  // Tztok
              25904, 25906, 25908, 26683,  // Vampyric
              25910, 25912, 25914, 26684,  // Tzkal
              29816, 29818, 29820, 29822,  // Araxyte
              33066, 33068, 33070, 33072,  // Hooded
              33338, 33439, 33441, 33443,  // Oathplate
              33340, 33445, 33447, 33449)  // Radiant
    ),

    /** Entrana church — "Cheer in full black dragonhide" */
    MASTER_ENTRANA_CHURCH(
        new WorldPoint(2851, 3355, 0), "MASTER",
        setOf(2503,  // Black d'hide body
              2497,  // Black d'hide chaps
              2491)  // Black d'hide vambraces
    ),

    /** TzHaar gem store — "Cry" */
    MASTER_TZHAAR_GEM_STORE(
        new WorldPoint(2466, 5150, 0), "MASTER",
        setOf(// Fire cape or any upgraded cape — equip any one
              6570,  // Fire cape
              24223, // Fire cape (locked)
              21295, // Infernal cape
              24224, // Infernal cape (locked)
              13329, // Fire max cape
              24134, // Fire max cape (locked)
              21285, // Infernal max cape
              24133, // Infernal max cape (locked)
              6522)  // TokTz-Xil-Ul
    ),

    /** Lord Iorwerth's camp — "Bow with charged crystal bow" */
    MASTER_IORWERTH_CAMP(
        new WorldPoint(2198, 3257, 0), "MASTER",
        setOf(23983, 24123, // Crystal bow (active / new)
              // Bow of faerdhinen (charged or clan recolour or Deadman — equip any one)
              25865,        // Bow of faerdhinen (charged, standard)
              25867, 25884, 25886, 25888, 25890, 25892,
              25894, 25896, 33021) // Bow of faerdhinen (c) clan + Deadman variants
    ),

    /** Goblin Village (Mudknuckle's hut) — "Goblin Salute" */
    MASTER_GOBLIN_VILLAGE(
        new WorldPoint(2959, 3502, 0), "MASTER",
        setOf(12480, // Bandos platebody
              12273, // Bandos cloak
              11804, // Bandos godsword
              20370) // Bandos godsword (gilded, also accepted)
    ),

    /** Centre of the Kourend catacombs — "Slap head" */
    MASTER_KOUREND_CATACOMBS(
        new WorldPoint(1661, 10045, 0), "MASTER",
        setOf(19675, 30305, // Arclight (charged + inactive)
              29589,        // Emberlight
              12851, 12853) // Amulet of the damned (full + used/degraded)
    ),

    /** King Black Dragon's lair — "Dance" */
    MASTER_KBD_LAIR(
        new WorldPoint(2286, 4680, 0), "MASTER",
        setOf(2503,  // Black d'hide body
              2491,  // Black d'hide vambraces
              12524) // Black dragon mask
    ),

    /** Outside K'ril Tsutsaroth's room (God Wars Dungeon) — "Blow a kiss" */
    MASTER_KRIL_CHAMBER(
        new WorldPoint(2931, 5337, 2), "MASTER",
        setOf(2657,  // Zamorak full helm
              10858) // Shadow sword
    ),

    /** Varrock Palace gardens by the bear cage (Ellamaria's garden) — "Show anger at Saradomin statue" */
    MASTER_VARROCK_PALACE_GARDENS(
        new WorldPoint(3232, 3494, 0), "MASTER",
        setOf(11808, // Zamorak godsword
              20374) // Zamorak godsword (gilded, also accepted)
    ),

    /** Outside the Wilderness magic axe hut — "Clap in only flared trousers" */
    MASTER_WILDERNESS_AXE_HUT(
        new WorldPoint(3186, 3958, 0), "MASTER",
        setOf(10394) // Flared trousers (only equip slot item in the STASH)
    ),

    /** Top floor of the Yanille Watchtower — "Swing a bullroarer" */
    MASTER_YANILLE_WATCHTOWER(
        new WorldPoint(2930, 4718, 2), "MASTER",
        setOf(716,          // Bullroarer
              4585, 28063,  // Dragon plateskirt (base + cr)
              3140, 28065,  // Dragon chainbody (base + cr)
              3105)         // Climbing boots
    ),

    /** Death Altar (Runecrafting altar) — "Blow a kiss" */
    MASTER_DEATH_ALTAR(
        new WorldPoint(2210, 4842, 0), "MASTER",
        setOf(5547,  // Death tiara
              1052,  // Cape of legends
              // Ring of wealth (all charge states: uncharged and 1–5)
              2572, 11980, 11982, 11984, 11986, 11988)
    ),

    /** Draynor Village (Miss Schism) — "Yawn" */
    MASTER_MISS_SCHISM(
        new WorldPoint(3095, 3254, 0), "MASTER",
        setOf(4151, 12006, // Abyssal whip (base or tentacle)
              1052,         // Cape of legends
              6135)         // Spined chaps
    ),

    /** Enchanted Valley (fairy ring BKQ) — "Flap" */
    MASTER_ENCHANTED_VALLEY(
        new WorldPoint(3022, 4517, 0), "MASTER",
        setOf(6739,           // Dragon axe
              25378, 30352,    // Dragon axe (or) — Trailblazer / TB Reloaded
              13241, 13242,    // Infernal axe (charged / uncharged)
              25066, 25371,    // Infernal axe (or) — charged / uncharged
              30347, 30348,    // Infernal axe (or) TB Reloaded — charged / uncharged
              23673, 23675,    // Crystal axe — active / inactive
              28217,           // Dragon felling axe
              28220, 28223)    // Crystal felling axe — active / inactive
    ),

    /** Prifddinas — Crystalline maple trees, Cadarn district — "Beckon" */
    MASTER_CRYSTALLINE_MAPLES(
        new WorldPoint(2213, 3427, 0), "MASTER",
        setOf(22368, 22370, // Bryophyta's staff (uncharged / charged)
              5541)          // Nature tiara
    ),

    /** Cam Torum (Varlamore) — "Stomp" */
    MASTER_CAM_TORUM(
        new WorldPoint(1428, 3118, 0), "MASTER",
        setOf(31139,        // Blue moon armour set (OR individual pieces below)
              29019, 29041, // Blue moon helm (new/used)
              29013, 29037, // Blue moon chestplate (new/used)
              29016, 29039, // Blue moon tassets (new/used)
              28988)        // Blue moon spear
    ),

    /** Salvager Overlook (Varlamore) — "Yawn" */
    MASTER_SALVAGER_OVERLOOK(
        new WorldPoint(1614, 3296, 0), "MASTER",
        setOf(30073, // Hueycoatl coif
              30082) // Hueycoatl vambraces
    ),

    /** Brittle Isle (Varlamore) — "Crab dance" */
    MASTER_BRITTLE_ISLE(
        new WorldPoint(1952, 4074, 0), "MASTER",
        setOf(32386, // Medallion of the deep
              31583)  // Rosewood blowpipe (charged — must be charged to deposit)
    ),

    ; // end of enum constants

    // -------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------

    private final WorldPoint location;
    private final String tier;
    private final Set<Integer> items;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    BankJunkStashUnit(WorldPoint location, String tier, Set<Integer> items)
    {
        this.location = location;
        this.tier     = tier;
        this.items    = items;
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    /** World tile where the STASH unit is built. */
    public WorldPoint getLocation() { return location; }

    /** Tier string: "BEGINNER", "EASY", "MEDIUM", "HARD", "ELITE", or "MASTER". */
    public String getTier() { return tier; }

    /** Set of item IDs that can be stored in this unit. */
    public Set<Integer> getItems() { return items; }

    // -------------------------------------------------------------------------
    // Tier ordering — single source of truth
    // -------------------------------------------------------------------------

    /**
     * Canonical tier order (lowest → highest), mapping the value returned by
     * {@link #getTier()} to its panel display name.
     *
     * <p>This is the <strong>single source of truth</strong> for both the tier
     * key strings and their display names. Any code that needs to iterate tiers
     * in order (e.g. {@code BankJunkPlugin.allUnfilledStashGroups}) must
     * reference this map rather than maintaining a parallel array.</p>
     *
     * <p>Insertion order is preserved (LinkedHashMap), so
     * {@code TIER_TO_DISPLAY.keySet()} and {@code .values()} iterate in
     * Beginner → Master order.</p>
     */
    public static final Map<String, String> TIER_TO_DISPLAY;
    static
    {
        LinkedHashMap<String, String> m = new LinkedHashMap<>();
        m.put("BEGINNER", "Beginner STASH");
        m.put("EASY",     "Easy STASH");
        m.put("MEDIUM",   "Medium STASH");
        m.put("HARD",     "Hard STASH");
        m.put("ELITE",    "Elite STASH");
        m.put("MASTER",   "Master STASH");
        TIER_TO_DISPLAY = Collections.unmodifiableMap(m);
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    /**
     * Builds an unmodifiable {@link Set} from the given item IDs.
     * IDs equal to {@code 0} are silently ignored (compile-time placeholder).
     */
    private static Set<Integer> setOf(int... ids)
    {
        Set<Integer> set = new HashSet<>(ids.length * 2);
        for (int id : ids)
        {
            if (id != 0) set.add(id);
        }
        return Collections.unmodifiableSet(set);
    }
}
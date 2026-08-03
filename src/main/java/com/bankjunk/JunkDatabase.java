package com.bankjunk;
import net.runelite.api.Quest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Curated database of known junk/quest items.
 *
 * <p>Item IDs match net.runelite.api.ItemID constants. Where multiple variant IDs
 * exist for the same item (e.g. noted versions), only the un-noted ID is listed —
 * the overlay checks both by checking ItemComposition.getLinkedNoteId().</p>
 *
 * <h3>ID Verification</h3>
 * <p>All IDs were sourced from the RuneLite ItemID class (training data, ~2024).
 * Cross-check any uncertain ID against the OSRS wiki infobox or the current
 * ItemID class in your RuneLite checkout:
 * <pre>  https://oldschool.runescape.wiki/w/[ItemName]  (infobox shows "ID")</pre>
 * </p>
 *
 * <h3>Adding items</h3>
 * <pre>
 *   // Always junk:
 *   new JunkEntry(ITEM_ID, "Item Name", "Reason text")
 *
 *   // Only junk if upgrade is also in bank:
 *   new JunkEntry(ITEM_ID, "Item Name", JunkTier.YELLOW,
 *       "Reason text", UPGRADE_ITEM_ID_1, UPGRADE_ITEM_ID_2)
 * </pre>
 */
public final class JunkDatabase
{
    private JunkDatabase() {}
    // =========================================================================
    // Item ID constants (mirrors net.runelite.api.ItemID for readability)
    // =========================================================================
    // --- The Corsair Curse ---
    private static final int OGRE_ARTEFACT_CORSAIR_CURSE         = 21837; // confirmed
    // --- Demon Slayer ---
    // NOTE: ID 2398 is Cave Nightshade (Herblore ingredient / Poison Dynamite), NOT a Silverlight key.
    // The correct ID for Wizard Traiborn's key is unconfirmed — entry removed until verified.
    private static final int SILVERLIGHT_KEY_SIR_PRYSIN         = 2401; // confirmed
    private static final int SILVERLIGHT_KEY_WIZARD_TRAIBORN    = 2399; // confirmed
    private static final int SILVERLIGHT_KEY_CAPTAIN_ROVIN      = 2400; // confirmed
    private static final int SILVERLIGHT                         = 2402; // confirmed
    private static final int ARCLIGHT                            = 19675;
    private static final int DARKLIGHT                           = 6746;
    // --- Dragon Slayer I ---
    private static final int MAP_PART_MELZAR                    = 1535; // confirmed
    private static final int MAP_PART_THALZAR                   = 1537; // confirmed
    private static final int MAP_PART_LOZAR                     = 1536; // confirmed
    private static final int CRANDOR_MAP                        = 1538; // confirmed
    // --- Ernest the Chicken ---
    private static final int PRESSURE_GAUGE                     = 271;
    private static final int RUBBER_TUBE                        = 276; // confirmed (OSRS wiki: Rubber tube, Ernest the Chicken)
    private static final int OIL_CAN                            = 277;
    private static final int KEY_ERNEST_THE_CHICKEN             = 275;  // confirmed
    // --- Goblin Diplomacy ---
    private static final int ORANGE_GOBLIN_MAIL                 = 286; // confirmed
    private static final int BLUE_GOBLIN_MAIL                   = 287; // confirmed
    // --- The Ides of Milk ---
    private static final int THE_GROATS_PRINCIPLES               = 33126; // confirmed
    private static final int MILK_SAMPLE_FIRST                   = 33128; // confirmed (first sample)
    private static final int MILK_SAMPLE_SECOND                  = 33130; // confirmed (second sample)
    // --- Imp Catcher ---
    private static final int RED_BEAD                           = 1470; // confirmed
    private static final int YELLOW_BEAD                        = 1472; // confirmed
    private static final int BLACK_BEAD                         = 1474; // confirmed
    private static final int WHITE_BEAD                         = 1476; // confirmed
    // --- The Knight's Sword ---
    private static final int PORTRAIT                           = 666;  // confirmed (Knight's Sword quest, NOT Pirate's Treasure)
    // --- Misthalin Mystery ---
    private static final int MANOR_KEY_MISTHALIN                 = 21052; // confirmed
    private static final int RUBY_KEY_MISTHALIN                  = 21053; // confirmed
    private static final int EMERALD_KEY_MISTHALIN               = 21054; // confirmed
    private static final int SAPPHIRE_KEY_MISTHALIN              = 21055; // confirmed
    private static final int KILLERS_KNIFE                       = 21059; // confirmed
    private static final int NOTES_MISTHALIN_1                   = 21056; // confirmed
    private static final int NOTES_MISTHALIN_2                   = 21057; // confirmed
    private static final int NOTES_MISTHALIN_3                   = 21058; // confirmed
    // --- Pirate's Treasure ---
    private static final int CHEST_KEY_PIRATES_TREASURE         = 432;  // confirmed
    private static final int PIRATE_MESSAGE                     = 433;  // confirmed
    // --- Romeo & Juliet / Making Friends with My Arm ---
    private static final int MESSAGE_ROMEO_JULIET               = 755;  // confirmed
    private static final int CADAVA_BERRIES                     = 753;  // confirmed — used in Romeo & Juliet and Making Friends with My Arm
    // --- Rune Mysteries ---
    private static final int RESEARCH_PACKAGE                   = 290;   // confirmed
    private static final int RESEARCH_NOTES_RUNE_MYSTERIES      = 291;   // corrected (user data; prior had 10492 which is the Animal Magnetism notes ID)
    // --- Shield of Arrav ---
    private static final int BROKEN_SHIELD_LEFT_HALF            = 763;  // confirmed
    private static final int BROKEN_SHIELD_RIGHT_HALF           = 765;  // confirmed
    private static final int CERTIFICATE_SHIELD_OF_ARRAV        = 769;  // confirmed
    // --- Vampyre Slayer ---
    private static final int STAKE                              = 1549; // confirmed
    // --- X Marks the Spot ---
    private static final int TREASURE_SCROLL_1_X_MARKS           = 23067; // confirmed (step 1)
    private static final int TREASURE_SCROLL_2_X_MARKS           = 23068; // confirmed (step 2)
    private static final int TREASURE_SCROLL_3_X_MARKS           = 23070; // confirmed (step 3)
    private static final int MYSTERIOUS_ORB_X_MARKS              = 23069; // confirmed
    // --- Clock Tower ---
    private static final int WHITE_COG                          = 20;   // confirmed
    private static final int BLACK_COG                          = 21;   // confirmed
    private static final int BLUE_COG                           = 22;   // confirmed
    private static final int RED_COG                            = 23;   // confirmed
    // --- Ghosts Ahoy ---
    private static final int NETTLES                            = 4241;  // confirmed
    private static final int NETTLE_WATER                       = 4237;  // confirmed
    private static final int NETTLE_TEA                         = 4239;  // confirmed
    private static final int NETTLE_TEA_MILKY                   = 4240;  // confirmed
    private static final int CUP_OF_TEA_NETTLE                  = 4242;  // confirmed
    private static final int CUP_OF_TEA_MILKY_NETTLE            = 4243;  // confirmed
    private static final int PORCELAIN_CUP                      = 4244;  // confirmed
    private static final int CUP_OF_TEA_GHOSTS_AHOY             = 4245;  // confirmed
    private static final int MYSTICAL_ROBES                     = 4247;  // confirmed — quest item only, no combat/skilling use
    private static final int BOOK_OF_HARICANTO                  = 4248;  // confirmed
    private static final int TRANSLATION_MANUAL                 = 4249;  // corrected (user data; prior session had wrong ID 4292)
    // NOTE: The regular Ghostspeak amulet (ID 552) is intentionally excluded — still useful
    // for various members quests and the Ectofuntus even after Ghosts Ahoy.
    private static final int MODEL_SHIP                         = 4253;  // confirmed
    private static final int BONE_KEY_GHOSTS_AHOY               = 4272;  // confirmed
    private static final int CHEST_KEY_GHOSTS_AHOY              = 4273;  // confirmed
    private static final int MAP_SCRAP                          = 4274;  // confirmed
    private static final int TREASURE_MAP_GHOSTS_AHOY           = 4277;  // corrected (user data; prior session had wrong ID 4271)
    private static final int PETITION_FORM                      = 4283;  // confirmed
    private static final int BEDSHEET                           = 4284;  // confirmed
    private static final int ECTOPLASM_BEDSHEET                 = 4285;  // confirmed (ectoplasm-covered bedsheet)
    // --- Plague City / Biohazard ---
    private static final int ETHENEA                            = 415;   // confirmed
    private static final int LIQUID_HONEY                       = 416;   // confirmed
    private static final int SULPHURIC_BROLINE                  = 417;   // confirmed
    private static final int PLAGUE_SAMPLE                      = 418;   // confirmed
    private static final int TOUCH_PAPER                        = 419;   // confirmed
    private static final int DISTILLATOR                        = 420;   // confirmed
    private static final int LATHAS_AMULET                      = 421;   // confirmed
    private static final int BIRD_FEED                          = 422;   // confirmed
    // --- Skippy and the Mogres / Plague City ---
    private static final int CHOCOLATEY_MILK                    = 1977;  // confirmed
    private static final int PIGEON_CAGE_FULL                   = 424;   // confirmed
    private static final int PIGEON_CAGE_EMPTY                  = 425;   // confirmed
    private static final int MEDICAL_GOWN                       = 430;   // confirmed — Biohazard + A Tail of Two Cats (junk after latter)
    // --- The Great Brain Robbery ---
    // PRAYER_BOOK_GBR (10890) removed — has ongoing use (prayer restore at altars)
    private static final int CRANIAL_CLAMP                      = 10893; // confirmed
    private static final int BRAIN_TONGS                        = 10894; // confirmed
    private static final int BELL_JAR                           = 10895; // confirmed
    private static final int WOLF_WHISTLE                       = 10896; // confirmed
    private static final int SHIPPING_ORDER                     = 10897; // confirmed
    private static final int FUSE_GBR                           = 10884; // confirmed
    private static final int KEG_GBR                            = 10898; // confirmed
    private static final int CRATE_PART                         = 10899; // confirmed
    private static final int SKULL_STAPLE                       = 10904; // confirmed
    // --- Animal Magnetism ---
    private static final int SELECTED_IRON                      = 10488; // confirmed
    private static final int BAR_MAGNET                         = 10489; // confirmed
    private static final int UNDEAD_TWIGS                       = 10490; // confirmed
    // NOTE: ID 10492 is the Animal Magnetism research notes only.
    // Rune Mysteries notes = 291 (a distinct item object).
    private static final int RESEARCH_NOTES_ANIMAL_MAGNETISM    = 10492; // confirmed
    private static final int TRANSLATED_NOTES                   = 10493; // confirmed
    private static final int A_PATTERN                          = 10494; // confirmed
    private static final int A_CONTAINER                        = 10495; // confirmed
    private static final int CRONE_MADE_AMULET                  = 10500; // confirmed
    // --- Desert Treasure I ---
    private static final int ETCHINGS                           = 4654;  // confirmed
    private static final int TRANSLATION_DESERT_TREASURE        = 4655;  // confirmed
    private static final int WARM_KEY                           = 4656;  // confirmed
    private static final int GARLIC_POWDER                      = 4668;  // confirmed
    private static final int BLOOD_DIAMOND                      = 4670;  // confirmed
    private static final int ICE_DIAMOND                        = 4671;  // confirmed
    private static final int SMOKE_DIAMOND                      = 4672;  // confirmed
    private static final int SHADOW_DIAMOND                     = 4673;  // confirmed
    private static final int GILDED_CROSS                       = 4674;  // confirmed
    // Silver pot and Blessed pot come in multiple states; all are quest-junk after Desert Treasure I.
    // 4658 = Silver pot (Empty), 4659 = Blessed pot (Empty) — prior names were wrong.
    private static final int SILVER_POT_EMPTY                   = 4658;  // confirmed (user data)
    private static final int SILVER_POT_BLOOD                   = 4660;  // confirmed (user data)
    private static final int SILVER_POT_GARLIC                  = 4662;  // confirmed (user data)
    private static final int SILVER_POT_SPICES                  = 4666;  // confirmed (user data)
    private static final int SILVER_POT_COMPLETE                = 4664;  // confirmed (user data)
    private static final int BLESSED_POT_EMPTY                  = 4659;  // confirmed (user data)
    private static final int BLESSED_POT_BLOOD                  = 4661;  // confirmed (user data)
    private static final int BLESSED_POT_GARLIC                 = 4663;  // confirmed (user data)
    private static final int BLESSED_POT_SPICES                 = 4665;  // confirmed (user data)
    private static final int BLESSED_POT_COMPLETE               = 4667;  // confirmed (user data)
    // --- Contact! ---
    private static final int PARCHMENT_CONTACT                  = 10585; // confirmed
    // --- The Feud ---
    // --- Death Plateau ---
    private static final int COMBINATION                        = 3102;  // corrected (user data; prior session had wrong ID 1505)
    private static final int SECRET_WAY_MAP                     = 3104;  // confirmed (Death Plateau Quest)
    private static final int CERTIFICATE_DEATH_PLATEAU          = 3114;  // confirmed
    private static final int STONE_BALL                         = 3110;  // confirmed
    // --- Devious Minds ---
    private static final int SLENDER_BLADE                      = 6817;  // confirmed (Devious Minds — not DT1)
    private static final int BOW_SWORD                          = 6818;  // confirmed (Devious Minds — not DT1)
    private static final int LARGE_POUCH_DEVIOUS_MINDS          = 6819;  // confirmed — not the RC large pouch
    private static final int RELIC_DEVIOUS_MINDS                = 6820;  // confirmed
    private static final int ORB_DEVIOUS_MINDS                  = 6821;  // confirmed
    // --- The Dig Site ---
    private static final int SPECIMEN_JAR                       = 669;   // confirmed
    private static final int ANIMAL_SKULL                       = 671;   // confirmed
    private static final int SPECIAL_CUP                        = 672;   // confirmed
    private static final int TEDDY                              = 673;   // confirmed
    private static final int CRACKED_SAMPLE                     = 674;   // confirmed
    private static final int PANNING_TRAY_EMPTY                 = 677;   // confirmed (user data — empty panning tray; 678 is unobtainable)
    private static final int PANNING_TRAY_MUD                   = 679;   // confirmed (user data — panning tray with mud)
    private static final int NUGGETS                            = 680;   // confirmed
    private static final int ANCIENT_TALISMAN                   = 681;   // confirmed
    private static final int UNSTAMPED_LETTER                   = 682;   // confirmed
    private static final int SEALED_LETTER                      = 683;   // confirmed
    private static final int BELT_BUCKLE                        = 684;   // confirmed
    private static final int RUSTY_SWORD_DIGSITE                = 686;   // confirmed
    private static final int BROKEN_ARROW                       = 687;   // confirmed
    private static final int BUTTONS                            = 688;   // confirmed
    private static final int POLISHED_BUTTONS                   = 10496; // confirmed — Animal Magnetism item (not Dig Site)
    private static final int BROKEN_STAFF                       = 689;   // confirmed
    private static final int LEVEL_1_CERTIFICATE                = 691;   // confirmed
    private static final int LEVEL_2_CERTIFICATE                = 692;   // confirmed
    private static final int LEVEL_3_CERTIFICATE                = 693;   // confirmed
    private static final int CERAMIC_REMAINS                    = 694;   // confirmed
    private static final int OLD_TOOTH                          = 695;   // confirmed
    private static final int INVITATION_LETTER                  = 696;   // confirmed
    private static final int DAMAGED_ARMOUR                     = 697;   // confirmed
    private static final int BROKEN_ARMOUR                      = 698;   // confirmed
    private static final int STONE_TABLET_DIGSITE               = 699;   // confirmed
    // Ammonium nitrate appears as two items: unprocessed (Chemical powder) and processed form.
    private static final int CHEMICAL_POWDER                    = 700;   // confirmed ("Chemical powder" — pre-mixing form)
    private static final int AMMONIUM_NITRATE                   = 701;   // confirmed ("Ammonium nitrate" — processed form)
    // Nitroglycerin appears as two items: unprocessed (Unidentified liquid) and processed form.
    private static final int UNIDENTIFIED_LIQUID                = 702;   // confirmed ("Unidentified liquid" — pre-mixing form)
    private static final int NITROGLYCERIN                      = 703;   // confirmed ("Nitroglycerin" — processed form)
    // Mixed chemicals appears in two stages.
    private static final int MIXED_CHEMICALS_1                  = 705;   // confirmed (step 1)
    private static final int MIXED_CHEMICALS_2                  = 706;   // confirmed (step 2)
    private static final int CHEMICAL_COMPOUND                  = 707;   // confirmed
    private static final int ARCENIA_ROOT                       = 708;   // confirmed
    private static final int CHEST_KEY_DIGSITE                  = 709;   // confirmed
    private static final int BOOK_ON_CHEMICALS                  = 711;   // confirmed
    // --- Keris upgrade relationship (YELLOW tier) ---
    private static final int KERIS                              = 10581; // Keris (unp)
    private static final int KERIS_P                           = 10582; // Keris (p)
    private static final int KERIS_P_PLUS                      = 10583; // Keris (p+)
    private static final int KERIS_P_PLUS_PLUS                 = 10584; // Keris (p++)
    private static final int KERIS_PARTISAN                     = 25979; // in-game verified July 4, 2026
    private static final int KERIS_PARTISAN_OF_CORRUPTION       = 27287; // in-game verified July 4, 2026
    private static final int KERIS_PARTISAN_OF_THE_SUN          = 27291; // in-game verified July 4, 2026
    private static final int KERIS_PARTISAN_OF_BREACHING        = 25981; // in-game verified July 4, 2026
    private static final int KERIS_PARTISAN_OF_AMASCUT          = 30891; // released June 2025 (Summer Sweep Up: Combat)
    // --- Warriors' Guild Defenders (YELLOW tier — no quest gate) ---
    private static final int BRONZE_DEFENDER                    = 8844;
    private static final int BRONZE_DEFENDER_BROKEN             = 20449;
    private static final int BRONZE_DEFENDER_LOCKED             = 24136;
    private static final int IRON_DEFENDER                      = 8845;
    private static final int IRON_DEFENDER_BROKEN               = 20451;
    private static final int IRON_DEFENDER_LOCKED               = 24137;
    private static final int STEEL_DEFENDER                     = 8846;
    private static final int STEEL_DEFENDER_BROKEN              = 20453;
    private static final int STEEL_DEFENDER_LOCKED              = 24138;
    private static final int BLACK_DEFENDER                     = 8847;
    private static final int BLACK_DEFENDER_BROKEN              = 20455;
    private static final int BLACK_DEFENDER_LOCKED              = 24139;
    private static final int MITHRIL_DEFENDER                   = 8848;
    private static final int MITHRIL_DEFENDER_BROKEN            = 20457;
    private static final int MITHRIL_DEFENDER_LOCKED            = 24140;
    private static final int ADAMANT_DEFENDER                   = 8849;
    private static final int ADAMANT_DEFENDER_BROKEN            = 20459;
    private static final int ADAMANT_DEFENDER_LOCKED            = 24141;
    private static final int RUNE_DEFENDER                      = 8850;
    private static final int RUNE_DEFENDER_BROKEN               = 20461;
    private static final int RUNE_DEFENDER_LOCKED               = 24142;
    private static final int RUNE_DEFENDER_T                    = 23230;
    private static final int RUNE_DEFENDER_T_LOCKED             = 27009;
    private static final int DRAGON_DEFENDER                    = 12954;
    private static final int DRAGON_DEFENDER_BROKEN             = 20463;
    private static final int DRAGON_DEFENDER_LOCKED             = 24143;
    private static final int DRAGON_DEFENDER_T                  = 19722;
    private static final int DRAGON_DEFENDER_T_LOCKED           = 27008;
    // --- Witch's Potion (also needed for Rat's tail) ---
    private static final int RATS_TAIL                          = 300;  // confirmed
    // --- The Restless Ghost ---
    private static final int GHOST_SKULL                        = 553;  // confirmed
    // --- Prince Ali Rescue ---
    private static final int BRONZE_KEY_PRINCE_ALI              = 2418; // confirmed
    private static final int SKIN_PASTE                         = 2424; // confirmed
    private static final int WIG_DYED                           = 2419; // confirmed
    private static final int WIG                                = 2421; // confirmed
    // NOTE: ROPE (954) intentionally excluded — used in dozens of other quests and activities
    // --- Waterfall Quest ---
    private static final int BOOK_BAXTORIAN                     = 292;  // confirmed
    private static final int KEY_BAXTORIAN                      = 298;  // confirmed
    // --- Tree Gnome Village ---
    private static final int ORB_OF_PROTECTION                  = 587;  // confirmed (all 3 share same ID)
    // --- Fight Arena ---
    private static final int KHAZARD_CELL_KEYS                  = 76;   // confirmed
    private static final int KHALI_BREW                         = 77;   // confirmed
    // --- Jungle Potion ---
    private static final int GRIMY_ROGUES_PURSE                 = 1533; // confirmed
    private static final int CLEAN_ROGUES_PURSE                 = 1534; // confirmed
    private static final int GRIMY_ARDRIGAL                     = 1527; // confirmed
    private static final int CLEAN_ARDRIGAL                     = 1528; // confirmed
    private static final int GRIMY_SITO_FOIL                    = 1529; // confirmed
    private static final int CLEAN_SITO_FOIL                    = 1530; // confirmed
    private static final int GRIMY_VOLENCIA_MOSS                = 1531; // confirmed
    private static final int CLEAN_VOLENCIA_MOSS                = 1532; // confirmed
    // --- Underground Pass ---
    private static final int ORB_OF_LIGHT                       = 1481; // confirmed
    private static final int ORB_OF_LIGHT_2                     = 1482; // confirmed
    private static final int ORB_OF_LIGHT_3                     = 1483; // confirmed
    private static final int ORB_OF_LIGHT_4                     = 1484; // confirmed
    // --- Nature Spirit ---
    private static final int BARK_SAMPLE                        = 783;  // confirmed
    // NOTE: SILVER_SICKLE_B (2963) intentionally excluded — still useful post-quest
    // --- The Haunted Mine ---
    private static final int GLOWING_FUNGUS                     = 4075; // confirmed
    // --- Zogre Flesh Eaters ---
    private static final int STRANGE_POTION                     = 4836; // confirmed
    // =========================================================================
    // NEW QUEST ITEM CONSTANTS — added June 2026 from user-verified wiki data
    // =========================================================================
    // --- Animal Magnetism (additional) ---
    private static final int UNDEAD_CHICKEN                     = 10487; // confirmed (user data)
    // --- Biohazard (additional) ---
    private static final int KEY_BIOHAZARD                      = 423;   // confirmed (user data)
    // --- Black Knights' Fortress ---
    private static final int CABBAGE_BKF                        = 1965;  // confirmed (user data — also used by Draynor Thieves' Guild, keep named for quest context)
    private static final int DOSSIER_BKF                        = 9589;  // confirmed (user data)
    // --- Client of Kourend ---
    private static final int MYSTERIOUS_ORB_COK                 = 21261; // confirmed (user data)
    private static final int BROKEN_GLASS_SLUG                  = 1469;  // confirmed (user data — used in Sea Slug + Client of Kourend)
    // --- Creature of Fenkenstrain ---
    private static final int STAR_AMULET                        = 4183;  // confirmed (user data)
    private static final int CAVERN_KEY                         = 4184;  // confirmed (user data)
    private static final int TOWER_KEY                          = 4185;  // confirmed (user data)
    private static final int SHED_KEY                           = 4186;  // confirmed (user data)
    private static final int MARBLE_AMULET                      = 4187;  // confirmed (user data)
    private static final int OBSIDIAN_AMULET                    = 4188;  // confirmed (user data)
    private static final int GARDEN_CANE                        = 4189;  // confirmed (user data)
    private static final int GARDEN_BRUSH                       = 4190;  // confirmed (user data)
    private static final int EXTENDED_BRUSH_1                   = 4191;  // confirmed (user data) — 1 cane
    private static final int EXTENDED_BRUSH_2                   = 4192;  // confirmed (user data) — 2 canes
    private static final int EXTENDED_BRUSH_3                   = 4193;  // confirmed (user data) — 3 canes
    private static final int FENKENSTRAIN_TORSO                 = 4194;  // confirmed (user data)
    private static final int FENKENSTRAIN_ARMS                  = 4195;  // confirmed (user data)
    private static final int FENKENSTRAIN_LEGS                  = 4196;  // confirmed (user data)
    private static final int DECAPITATED_HEAD_BRAINLESS         = 4197;  // confirmed (user data)
    private static final int DECAPITATED_HEAD_BRAIN             = 4198;  // confirmed (user data)
    private static final int PICKLED_BRAIN                      = 4199;  // confirmed (user data)
    private static final int CONDUCTOR_MOULD                    = 4200;  // confirmed (user data)
    private static final int JOURNAL_FENKENSTRAIN               = 4203;  // confirmed (user data)
    private static final int LETTER_FENKENSTRAIN                = 4204;  // confirmed (user data)
    // --- Death Plateau (additional) ---
    // NOTE: IOU_DEATH_PLATEAU (3103) is different from IOU = 293 used by The Feud
    private static final int IOU_DEATH_PLATEAU                  = 3103;  // confirmed (user data)
    private static final int STONE_BALL_RED                     = 3109;  // confirmed (user data)
    // STONE_BALL = 3110 (blue) is already defined above
    private static final int STONE_BALL_YELLOW                  = 3111;  // confirmed (user data)
    private static final int STONE_BALL_PURPLE                  = 3112;  // confirmed (user data)
    private static final int STONE_BALL_GREEN                   = 3113;  // confirmed (user data)
    // --- Devious Minds (additional) ---
    private static final int COLOSSAL_POUCH_DEVIOUS_MINDS       = 26906; // confirmed (user data)
    // --- Dragon Slayer I (keys from Melzar's Maze) ---
    private static final int DS1_KEY_RED                        = 1543;  // confirmed (user data)
    private static final int DS1_KEY_ORANGE                     = 1544;  // confirmed (user data)
    private static final int DS1_KEY_YELLOW                     = 1545;  // confirmed (user data)
    private static final int DS1_KEY_BLUE                       = 1546;  // confirmed (user data)
    private static final int DS1_KEY_MAGENTA                    = 1547;  // confirmed (user data)
    private static final int DS1_KEY_GREEN                      = 1548;  // confirmed (user data)
    // --- Dream Mentor ---
    private static final int DREAM_VIAL_EMPTY                   = 11151; // confirmed (user data)
    private static final int DREAM_VIAL_WATER                   = 11152; // confirmed (user data)
    private static final int DREAM_VIAL_HERB                    = 11153; // confirmed (user data)
    private static final int DREAM_POTION                       = 11154; // confirmed (user data)
    private static final int GROUND_ASTRAL_RUNE                 = 11155; // confirmed (user data)
    private static final int ASTRAL_RUNE_SHARDS                 = 11156; // confirmed (user data)
    private static final int CYRISUS_CHEST                      = 11158; // confirmed (user data)
    // --- Druidic Ritual ---
    private static final int ENCHANTED_BEEF                     = 522;   // confirmed (user data)
    private static final int ENCHANTED_RAT                      = 523;   // confirmed (user data)
    private static final int ENCHANTED_BEAR                     = 524;   // confirmed (user data)
    private static final int ENCHANTED_CHICKEN                  = 525;   // confirmed (user data)
    // --- Dwarf Cannon ---
    private static final int DWARF_REMAINS                      = 0;
    private static final int DWARF_CANNON_TOOLKIT               = 1;
    private static final int NULODIONS_NOTES                    = 3;
    private static final int RAILING_DWARF_CANNON               = 14;
    // --- Eadgar's Ruse ---
    private static final int TROLL_THISTLE                      = 3262;  // confirmed (user data)
    private static final int DRIED_THISTLE                      = 3263;  // confirmed (user data)
    private static final int GROUND_THISTLE                     = 3264;  // confirmed (user data)
    private static final int TROLL_POTION                       = 3265;  // confirmed (user data)
    private static final int DRUNK_PARROT                       = 3266;  // confirmed (user data)
    private static final int DIRTY_ROBE                         = 3267;  // confirmed (user data)
    private static final int FAKE_MAN                           = 3268;  // confirmed (user data)
    private static final int STOREROOM_KEY                      = 3269;  // confirmed (user data)
    private static final int ALCO_CHUNKS                        = 3270;  // confirmed (user data)
    // --- Eagles' Peak ---
    private static final int BIRD_BOOK                          = 10173; // confirmed (user data)
    private static final int GOLDEN_FEATHER_EAGLES_PEAK         = 10175; // confirmed (user data)
    private static final int ODD_BIRD_SEED                      = 10178; // confirmed (user data)
    private static final int FEATHERED_JOURNAL                  = 10179; // confirmed (user data)
    // --- Elemental Workshop I ---
    private static final int STONE_BOWL_EMPTY                   = 2888;  // confirmed (user data)
    private static final int STONE_BOWL_FULL                    = 2889;  // confirmed (user data)
    // --- Elemental Workshop II ---
    private static final int CRANE_SCHEMATIC                    = 9718;  // confirmed (user data)
    private static final int LEVER_SCHEMATIC                    = 9719;  // confirmed (user data)
    private static final int CRANE_CLAW                         = 9720;  // confirmed (user data)
    private static final int SCROLL_EW2                         = 9721;  // confirmed (user data)
    private static final int KEY_EW2                            = 9722;  // confirmed (user data)
    private static final int PIPE_EW2                           = 9723;  // confirmed (user data)
    private static final int LARGE_COG_EW2                      = 9724;  // confirmed (user data)
    private static final int MEDIUM_COG_EW2                     = 9725;  // confirmed (user data)
    private static final int SMALL_COG_EW2                      = 9726;  // confirmed (user data)
    // --- Enakhra's Lament ---
    private static final int CAMEL_MASK                         = 7003;  // confirmed (user data)
    // --- Enlightened Journey ---
    private static final int AUGUSTES_SAPLING                   = 9932;  // confirmed (user data)
    private static final int BALLOON_STRUCTURE                  = 9933;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_ORIGAMI            = 9934;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_YELLOW             = 9935;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_BLUE               = 9936;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_RED                = 9937;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_ORANGE             = 9938;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_GREEN              = 9939;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_PURPLE             = 9940;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_PINK               = 9941;  // confirmed (user data)
    private static final int ORIGAMI_BALLOON_BLACK              = 9942;  // confirmed (user data)
    private static final int SANDBAG_EJ                         = 9943;  // confirmed (user data)
    // --- Ernest the Chicken (additional) ---
    private static final int POISONED_FISH_FOOD                 = 274;   // confirmed (user data)
    // --- The Eyes of Glouphrie ---
    private static final int MAGIC_GLUE                         = 9592;  // confirmed (user data)
    private static final int WEIRD_GLOOP                        = 9593;  // confirmed (user data)
    private static final int GROUND_MUD_RUNES                   = 9594;  // confirmed (user data)
    private static final int HAZELMERES_BOOK                    = 9595;  // confirmed (user data)
    // Crystal tokens — red/orange/yellow/green/blue/indigo/violet × circle/triangle/square/pentagon
    private static final int RED_CIRCLE                         = 9597;  // confirmed (user data)
    private static final int RED_TRIANGLE                       = 9598;  // confirmed (user data)
    private static final int RED_SQUARE                         = 9599;  // confirmed (user data)
    private static final int RED_PENTAGON                       = 9600;  // confirmed (user data)
    private static final int ORANGE_CIRCLE                      = 9601;  // confirmed (user data)
    private static final int ORANGE_TRIANGLE                    = 9602;  // confirmed (user data)
    private static final int ORANGE_SQUARE                      = 9603;  // confirmed (user data)
    private static final int ORANGE_PENTAGON                    = 9604;  // confirmed (user data)
    private static final int YELLOW_CIRCLE                      = 9605;  // confirmed (user data)
    private static final int YELLOW_TRIANGLE                    = 9606;  // confirmed (user data)
    private static final int YELLOW_SQUARE                      = 9607;  // confirmed (user data)
    private static final int YELLOW_PENTAGON                    = 9608;  // confirmed (user data)
    private static final int GREEN_CIRCLE                       = 9609;  // confirmed (user data)
    private static final int GREEN_TRIANGLE                     = 9610;  // confirmed (user data)
    private static final int GREEN_SQUARE                       = 9611;  // confirmed (user data)
    private static final int GREEN_PENTAGON                     = 9612;  // confirmed (user data)
    private static final int BLUE_CIRCLE                        = 9613;  // confirmed (user data)
    private static final int BLUE_TRIANGLE                      = 9614;  // confirmed (user data)
    private static final int BLUE_SQUARE                        = 9615;  // confirmed (user data)
    private static final int BLUE_PENTAGON                      = 9616;  // confirmed (user data)
    private static final int INDIGO_CIRCLE                      = 9617;  // confirmed (user data)
    private static final int INDIGO_TRIANGLE                    = 9618;  // confirmed (user data)
    private static final int INDIGO_SQUARE                      = 9619;  // confirmed (user data)
    private static final int INDIGO_PENTAGON                    = 9620;  // confirmed (user data)
    private static final int VIOLET_CIRCLE                      = 9621;  // confirmed (user data)
    private static final int VIOLET_TRIANGLE                    = 9622;  // confirmed (user data)
    private static final int VIOLET_SQUARE                      = 9623;  // confirmed (user data)
    private static final int VIOLET_PENTAGON                    = 9624;  // confirmed (user data)
    // --- Fairytale I - Growing Pains ---
    private static final int DRAYNOR_SKULL                      = 7408;  // confirmed (user data)
    private static final int QUEENS_SECATEURS_FI                = 7410;  // confirmed (user data) — Fairytale I variant
    private static final int SYMPTOMS_LIST                      = 7411;  // confirmed (user data)
    // --- Fairytale II - Cure a Queen ---
    private static final int QUEENS_SECATEURS_FII               = 9020;  // confirmed (user data) — Fairytale II variant
    private static final int NUFFS_CERTIFICATE                  = 9025;  // confirmed (user data)
    // --- Family Crest ---
    private static final int PERFECT_GOLD_ORE                   = 446;   // confirmed (user data)
    private static final int PERFECT_RING                       = 773;   // confirmed (user data)
    private static final int PERFECT_NECKLACE                   = 774;   // confirmed (user data)
    private static final int CREST_PART_AVAN                    = 779;   // confirmed (user data)
    private static final int CREST_PART_CALEB                   = 780;   // confirmed (user data)
    private static final int CREST_PART_JOHNATHON               = 781;   // confirmed (user data)
    private static final int FAMILY_CREST                       = 782;   // confirmed (user data)
    private static final int PERFECT_GOLD_BAR                   = 2365;  // confirmed (user data)
    // --- Fishing Contest ---
    private static final int FISHING_PASS                       = 27;    // confirmed (user data)
    // --- Fremennik Trials ---
    private static final int EXOTIC_FLOWER                      = 3698;  // confirmed (user data)
    private static final int UNUSUAL_FISH                       = 3703;  // confirmed (user data)
    private static final int STRANGE_OBJECT                     = 3713;  // confirmed (user data)
    private static final int LIT_STRANGE_OBJECT                 = 3714;  // confirmed (user data)
    private static final int KEG_OF_BEER_FREMENNIK              = 3711;  // confirmed (user data)
    private static final int FROZEN_KEY_FREMENNIK               = 3741;  // confirmed (user data)
    private static final int MAGNET_FREMENNIK                   = 3718;  // confirmed (user data)
    // Unstrung/plain lyre: YELLOW tier — junk after Fremennik Trials only if an enchanted lyre variant is in bank
    private static final int UNSTRUNG_LYRE                      = 3688;  // confirmed (user data)
    private static final int LYRE                               = 3689;  // confirmed (user data)
    // Enchanted lyre variants (upgrade IDs for the YELLOW check above)
    private static final int ENCHANTED_LYRE_UNCHARGED           = 3690;  // confirmed (user data)
    private static final int ENCHANTED_LYRE_1                   = 3691;  // confirmed (user data)
    private static final int ENCHANTED_LYRE_2                   = 6125;  // confirmed (user data)
    private static final int ENCHANTED_LYRE_3                   = 6126;  // confirmed (user data)
    private static final int ENCHANTED_LYRE_4                   = 6127;  // confirmed (user data)
    private static final int ENCHANTED_LYRE_5                   = 13079; // confirmed (user data)
    private static final int ENCHANTED_LYRE_I                   = 23458; // confirmed (user data)
    // --- Ghosts Ahoy (additional) ---
    private static final int PUDDLE_OF_SLIME                    = 4238;  // confirmed (user data)
    private static final int CUP_OF_TEA_GHOSTS_AHOY_MILKY      = 4246;  // confirmed (user data) — milky variant
    private static final int GHOSTSPEAK_ENCHANTED               = 4250;  // confirmed (user data) — quest-specific enchanted variant
    private static final int MODEL_SHIP_WITH_FLAG               = 4254;  // confirmed (user data)
    private static final int MAP_SCRAP_2                        = 4276;  // confirmed (user data)
    private static final int MAP_SCRAP_3                        = 4275;  // confirmed (user data)
    private static final int RAW_BEEF_UNDEAD                    = 4287;  // confirmed (user data)
    private static final int RAW_CHICKEN_UNDEAD                 = 4289;  // confirmed (user data)
    private static final int COOKED_CHICKEN_UNDEAD              = 4291;  // confirmed (user data)
    private static final int COOKED_MEAT_UNDEAD                 = 4293;  // confirmed (user data)
    // --- The Grand Tree ---
    private static final int GLOUGHS_JOURNAL                    = 785;   // confirmed (user data)
    // --- The Great Brain Robbery (additional) ---
    private static final int KEG_DUMMY_GBR                      = 10885; // confirmed (user data) — dummy keg item
    // --- Horror from the Deep ---
    private static final int JOURNAL_HORROR                     = 3845;  // confirmed
    private static final int DIARY_HORROR                       = 3846;  // confirmed (user data)
    private static final int MANUAL_HORROR                      = 3847;  // confirmed
    private static final int LIGHTHOUSE_KEY                     = 3848;  // confirmed (user data)
    // --- In Aid of the Myreque ---
    private static final int DUSTY_SCROLL_MYREQUE               = 7629;  // confirmed (user data)
    private static final int CRATE_MYREQUE                      = 7630;  // confirmed (user data)
    // --- Jungle Potion (additional herbs) ---
    private static final int GRIMY_SNAKE_WEED                   = 1525;  // confirmed (user data)
    private static final int SNAKE_WEED                         = 1526;  // confirmed (user data)
    private static final int UNFINISHED_POTION_ROGUES_PURSE     = 4840;  // confirmed (user data)
    // --- Legends' Quest ---
    private static final int HOLLOW_REED                        = 727;   // confirmed (user data)
    private static final int SNAKEWEED_MIXTURE                  = 737;   // confirmed (user data)
    private static final int ARDRIGAL_MIXTURE                   = 738;   // confirmed (user data)
    private static final int BRAVERY_POTION                     = 739;   // confirmed (user data)
    // --- Lost City ---
    private static final int DRAMEN_BRANCH                      = 771;   // confirmed (user data) — needed until RFD: Sir Amik Varze complete
    // --- Monkey Madness I ---
    private static final int MONKEY_MM1                         = 4033;  // confirmed (user data) — quest monkey item
    private static final int TENTH_SQUAD_SIGIL                  = 4035;  // confirmed (user data)
    // --- Olaf's Quest ---
    private static final int DAMP_PLANKS                        = 11031; // confirmed (user data)
    private static final int PARCHMENT_OLAFS                    = 11036; // confirmed (user data)
    private static final int KEY_OLAFS_CROSS                    = 11039; // confirmed (user data)
    private static final int KEY_OLAFS_SQUARE                   = 11040; // confirmed (user data)
    private static final int KEY_OLAFS_TRIANGLE                 = 11041; // confirmed (user data)
    private static final int KEY_OLAFS_HEXAGON                  = 11042; // confirmed (user data)
    private static final int KEY_OLAFS_STAR                     = 11043; // confirmed (user data)
    // --- Pirate's Treasure (additional) ---
    private static final int CASKET_PIRATES_TREASURE            = 7956;  // confirmed (user data)
    // --- Romeo & Juliet (additional) ---
    private static final int CADAVA_POTION                      = 756;   // confirmed (user data) — made from Cadava berries
    // --- Shield of Arrav (additional) ---
    private static final int BOOK_SHIELD_OF_ARRAV               = 757;   // confirmed (user data)
    private static final int INTEL_REPORT                       = 761;   // confirmed (user data)
    // --- Sheep Herder ---
    private static final int CATTLEPROD                         = 278;   // confirmed (user data)
    private static final int SHEEP_BONES_1                      = 280;   // confirmed (user data)
    private static final int SHEEP_BONES_2                      = 281;   // confirmed (user data)
    private static final int SHEEP_BONES_3                      = 282;   // confirmed (user data)
    private static final int SHEEP_BONES_4                      = 283;   // confirmed (user data)
    private static final int PLAGUE_JACKET                      = 284;   // confirmed (user data)
    private static final int PLAGUE_TROUSERS                    = 285;   // confirmed (user data)
    // --- Tourist Trap ---
    private static final int BARREL_TOURIST_TRAP                = 1841;  // confirmed (user data)
    private static final int ANA_IN_A_BARREL                    = 1842;  // confirmed (user data)
    private static final int ROCK_TOURIST_TRAP                  = 1855;  // confirmed (user data)
    // --- Underground Pass (additional) ---
    private static final int UNICORN_HORN_UP                    = 1487;  // confirmed (user data) — Underground Pass specific
    private static final int WITCHS_CAT                         = 1491;  // confirmed (user data)
    private static final int IBANS_DOVE                         = 1496;  // confirmed (user data)
    private static final int IBANS_SHADOW                       = 1500;  // confirmed (user data)
    private static final int IBANS_ASHES                        = 1502;  // confirmed (user data)
    // --- Watchtower ---
    private static final int BAT_BONES                          = 530;   // confirmed (user data) — needed for Merlin's Crystal, Forgettable Tale, Watchtower
    private static final int OGRE_RELIC                         = 2372;  // confirmed (user data)
    private static final int CRYSTAL_WATCHTOWER_YELLOW         = 2380;  // confirmed (user data)
    private static final int CRYSTAL_WATCHTOWER_MAGENTA        = 2381;  // confirmed (user data)
    private static final int CRYSTAL_WATCHTOWER_CYAN           = 2382;  // confirmed (user data)
    private static final int CRYSTAL_WATCHTOWER_GREY           = 2383;  // confirmed (user data)
    private static final int FINGERNAILS                        = 2384;  // confirmed (user data)
    private static final int POTION_WATCHTOWER                  = 2394;  // confirmed (user data)
    // --- Witch's House ---
    private static final int DIARY_WITCHS_HOUSE                 = 2408;  // confirmed (user data)
    private static final int DOOR_KEY_WITCHS_HOUSE              = 2409;  // confirmed (user data) — needs Witch's House + Grim Tales
    private static final int MAGNET_WITCHS_HOUSE                = 2410;  // confirmed (user data)
    private static final int KEY_WITCHS_HOUSE                   = 2411;  // confirmed (user data)
    // --- The Golem ---
    private static final int LETTER_GOLEM                          = 4615;
    private static final int STATUETTE_GOLEM                       = 4618;
    private static final int STRANGE_IMPLEMENT                     = 4619;
    private static final int GOLEM_PROGRAM                         = 4624;
    // --- Priest in Peril ---
    private static final int GOLDEN_KEY_PIP                        = 2944;
    private static final int GOLDEN_TINDERBOX_PIP                  = 2946;
    private static final int GOLDEN_CANDLE                         = 2947;
    private static final int GOLDEN_POT_PIP                        = 2948;
    private static final int GOLDEN_HAMMER_PIP                     = 2949;
    private static final int GOLDEN_FEATHER_PIP                    = 2950;
    private static final int GOLDEN_NEEDLE_PIP                     = 2951;
    private static final int IRON_KEY_PIP                          = 2945;
    // --- Scorpion Catcher ---
    private static final int SCORPION_CAGE_EMPTY                   = 456;
    private static final int SCORPION_CAGE_TAVERLEY                = 457;
    private static final int SCORPION_CAGE_TAV_OUTPOST             = 458;
    private static final int SCORPION_CAGE_TAV_MONASTERY           = 459;
    private static final int SCORPION_CAGE_OUTPOST                 = 460;
    private static final int SCORPION_CAGE_OUT_MONASTERY           = 461;
    private static final int SCORPION_CAGE_MONASTERY               = 462;
    private static final int SCORPION_CAGE_FULL                    = 463;
    // --- Shadow of the Storm ---
    private static final int DEMONIC_SIGIL_MOULD                   = 6747;
    private static final int DEMONIC_SIGIL                         = 6748;
    private static final int DEMONIC_TOME                          = 6749;
    // --- Troll Romance ---
    private static final int SLED_UNWAXED                          = 4083;  // Waxed sled (4084) has post-quest use (elite clues), not added
    private static final int TROLLWEISS                            = 4086;
    // --- Murder Mystery ---
    private static final int SILVER_POT_MM                         = 1806;
    private static final int SILVER_POT_MM_DUSTED                  = 1807;
    // --- Icthlarin's Little Helper ---
    private static final int CANOPIC_JAR_HET                       = 4678;
    private static final int CANOPIC_JAR_APMEKEN                   = 4679;
    private static final int CANOPIC_JAR_SCABARAS                  = 4680;
    private static final int CANOPIC_JAR_CRONDIS                   = 4681;
    private static final int HOLY_SYMBOL_ILH                       = 4682;
    private static final int UNHOLY_SYMBOL_ILH                     = 4683;
    private static final int LINEN_ILH                             = 4684;
    // --- Troll Stronghold ---
    private static final int PRISON_KEY_TS                         = 3135;
    // --- Plague City ---
    private static final int BOOK_PLAGUE_CITY                      = 1509;
    // =========================================================================
    // Batch 2 constants — confirmed 2026-06-13
    // =========================================================================
    // --- Ernest the Chicken (additional) ---
    private static final int FISH_FOOD                           = 272;
    // --- Animal Magnetism (additional) ---
    private static final int BLESSED_AXE                        = 10491;
    // --- Biohazard (additional) ---
    private static final int PRIEST_GOWN_TOP                    = 426;
    private static final int PRIEST_GOWN_BOTTOM                 = 428;
    private static final int ROTTEN_APPLE                       = 1984;
    // --- Client of Kourend (additional) ---
    private static final int ENCHANTED_SCROLL_COK               = 21259;
    private static final int ENCHANTED_QUILL_COK                = 21260;
    // --- Dragon Slayer I (additional) ---
    private static final int ELVARG_HEAD                        = 11279;
    // --- Eadgar's Ruse (additional) ---
    private static final int GOUTWEED                           = 3261;
    // --- Eagles' Peak (additional) ---
    private static final int EAGLE_FEATHER                      = 10167;
    private static final int EAGLE_CAPE                         = 10171;
    private static final int FAKE_BEAK                          = 10172;
    private static final int METAL_FEATHER                      = 10174;
    private static final int SILVER_FEATHER_EP                  = 10176;
    private static final int BRONZE_FEATHER                     = 10177;
    // --- Enakhra's Lament (additional) ---
    // --- Fairytale II (additional) ---
    private static final int GORAK_CLAWS                        = 9016;
    private static final int STAR_FLOWER_FII                    = 9017;
    private static final int GORAK_CLAW_POWDER                  = 9018;
    private static final int MAGIC_ESSENCE_UNF                  = 9019;
    private static final int MAGIC_ESSENCE_4                    = 9021;
    private static final int MAGIC_ESSENCE_3                    = 9022;
    private static final int MAGIC_ESSENCE_2                    = 9023;
    private static final int MAGIC_ESSENCE_1                    = 9024;
    // --- Fight Arena (additional) ---
    private static final int KHAZARD_HELMET                     = 74;
    private static final int KHAZARD_ARMOUR                     = 75;
    // --- Fishing Contest (additional) ---
    private static final int RED_VINE_WORM                      = 25;
    private static final int FISHING_TROPHY                     = 26;
    private static final int GIANT_CARP                         = 337;
    private static final int RAW_GIANT_CARP                     = 338;
    // --- Grim Tales ---
    private static final int GRIFFIN_FEATHER_GRIM               = 11196;
    private static final int MIAZRQAS_PENDANT                   = 11197;
    private static final int MUSIC_SHEET_GRIM                   = 11198;
    private static final int RUPERTS_HELMET                     = 11199;
    private static final int SHRINKING_RECIPE                   = 11202;
    private static final int TODO_LIST_GRIM                     = 11203;
    private static final int SHRINK_ME_QUICK                    = 11204;
    private static final int SHRUNK_OGLEROOT                    = 11205;
    private static final int GOLDEN_GOBLIN                      = 11210;
    private static final int MAGIC_BEANS_GRIM                   = 11211;
    // --- The Grand Tree (additional; BARK_SAMPLE=783 and GLOUGHS_JOURNAL=785 already declared) ---
    private static final int TRANSLATION_BOOK_GT                = 784;
    private static final int HAZELMERE_SCROLL_GT                = 786;
    private static final int LUMBER_ORDER_GT                    = 787;
    private static final int GLOUGHS_KEY                        = 788;
    private static final int TWIGS_T                            = 789;
    private static final int TWIGS_U                            = 790;
    private static final int TWIGS_Z                            = 791;
    private static final int TWIGS_O                            = 792;
    private static final int DACONIA_ROCK                       = 793;
    // --- Horror from the Deep (additional; DIARY_HORROR=3846 already declared) ---
    // MANUAL_HORROR = 3847 declared above (replacing JOURNAL_HORROR)
    private static final int RUSTY_CASKET_HORROR                = 3849;
    // --- In Aid of the Myreque (additional) ---
    private static final int ROCK_LIMESTONE                     = 968;
    private static final int BUCKET_RUBBLE_PARTIAL              = 7622;
    private static final int BUCKET_RUBBLE_ALMOST_FULL          = 7624;
    private static final int BUCKET_RUBBLE_TOTALLY_FULL         = 7626;
    private static final int PLASTER_FRAGMENT                   = 7628;
    private static final int TEMPLE_LIBRARY_KEY                 = 7632;
    private static final int THE_SLEEPING_SEVEN                 = 7633;
    private static final int HISTORIES_HALLOWLAND               = 7634;
    private static final int MODERN_DAY_MORYTANIA               = 7635;
    private static final int SILVER_DUST_MYREQUE                = 7650;
    private static final int GUTHIX_BALANCE_UNF_4               = 7652;
    private static final int GUTHIX_BALANCE_UNF_3               = 7654;
    private static final int GUTHIX_BALANCE_UNF_2               = 7656;
    private static final int GUTHIX_BALANCE_UNF_1               = 7658;
    private static final int GUTHIX_BALANCE_4                   = 7660;
    private static final int GUTHIX_BALANCE_3                   = 7662;
    private static final int GUTHIX_BALANCE_2                   = 7664;
    private static final int GUTHIX_BALANCE_1                   = 7666;
    // --- Legends' Quest (additional) ---
    private static final int RADIMUS_NOTES_INCOMPLETE           = 714;
    private static final int RADIMUS_NOTES                      = 715;
    private static final int SCRAWLED_NOTE                      = 717;
    private static final int A_SCRIBBLED_NOTE                   = 718;
    private static final int SCRUMPLED_NOTE                     = 719;
    private static final int SKETCH_LQ                          = 720;
    private static final int GOLD_BOWL_LQ                       = 721;
    private static final int BLESSED_GOLD_BOWL                  = 722;
    private static final int GOLDEN_BOWL_WATER                  = 723;
    private static final int GOLDEN_BOWL_PURE_WATER             = 724;
    private static final int GOLDEN_BOWL_BLESSED_WATER          = 725;
    private static final int GOLDEN_BOWL_BLESSED_PURE           = 726;
    private static final int SHAMAN_TOME                        = 729;
    private static final int BINDING_BOOK                       = 730;
    private static final int ENCHANTED_VIAL_LQ                  = 731;
    private static final int HOLY_WATER_LQ                      = 732;
    private static final int SMASHED_GLASS_LQ                   = 733;
    private static final int YOMMI_SEEDS                        = 735;
    private static final int YOMMI_SEEDS_GERMINATED             = 736;
    private static final int BLUE_HAT_LQ                        = 740;   // confirmed
    private static final int CHUNK_CRYSTAL                      = 741;
    private static final int HUNK_CRYSTAL                       = 742;
    private static final int LUMP_CRYSTAL                       = 743;
    private static final int HEART_CRYSTAL_INACTIVE             = 744;
    private static final int HEART_CRYSTAL_ACTIVE               = 745;
    private static final int DARK_DAGGER_LQ                     = 746;
    private static final int GLOWING_DAGGER                     = 747;
    private static final int HOLY_FORCE                         = 748;   // confirmed
    private static final int YOMMI_TOTEM                        = 749;
    private static final int GILDED_TOTEM                       = 750;
    private static final int RAW_OOMLIE                         = 2337;
    private static final int PALM_LEAF_LQ                       = 2339;
    private static final int WRAPPED_OOMLIE                     = 2341;
    private static final int COOKED_OOMLIE_WRAP                 = 2343;
    // --- Making Friends with My Arm ---
    private static final int OLD_MANS_COFFIN                    = 22588;
    private static final int REDUCED_CADAVA_POTION              = 22589;
    private static final int GOAT_DUNG                          = 22590;
    private static final int WEISS_FIRE_NOTES                   = 22591;
    // --- Monkey Madness I (additional; MONKEY_MM1=4033 and TENTH_SQUAD_SIGIL=4035 already declared) ---
    private static final int SPARE_CONTROLS_MM                  = 4002;
    private static final int GNOME_ROYAL_SEAL                   = 4004;
    private static final int NARNODE_ORDERS                     = 4005;
    private static final int MONKEY_DENTURES                    = 4006;
    private static final int ENCHANTED_BAR_MM                   = 4007;
    private static final int EYE_OF_GNOME                       = 4008;
    private static final int BANANA_STEW                        = 4016;
    private static final int MAMULET_MOULD                      = 4020;
    private static final int MSPEAK_AMULET_UNSTRUNG             = 4022;
    // --- Murder Mystery (additional) ---
    private static final int SILVER_NECKLACE_MM                 = 1796;
    private static final int SILVER_NECKLACE_MM_DUSTED          = 1797;
    private static final int SILVER_CUP_MM                      = 1798;
    private static final int SILVER_CUP_MM_DUSTED               = 1799;
    private static final int SILVER_BOTTLE_MM                   = 1800;
    private static final int SILVER_BOTTLE_MM_DUSTED            = 1801;
    private static final int SILVER_BOOK_MM                     = 1802;
    private static final int SILVER_BOOK_MM_DUSTED              = 1803;
    private static final int SILVER_NEEDLE_MM                   = 1804;
    private static final int SILVER_NEEDLE_MM_DUSTED            = 1805;
    private static final int CRIMINAL_THREAD_RED                = 1808;
    private static final int CRIMINAL_THREAD_GREEN              = 1809;
    private static final int CRIMINAL_THREAD_BLUE               = 1810;
    private static final int FLYPAPER_MM                        = 1811;
    private static final int PUNGENT_POT                        = 1812;
    private static final int CRIMINAL_DAGGER_NORMAL             = 1813;
    private static final int CRIMINAL_DAGGER_DUSTED             = 1814;
    private static final int KILLERS_PRINT                      = 1815;
    private static final int ANNAS_PRINT                        = 1816;
    private static final int BOBS_PRINT                         = 1817;
    private static final int CAROLS_PRINT                       = 1818;
    private static final int DAVIDS_PRINT                       = 1819;
    private static final int ELIZABETHS_PRINT                   = 1820;
    private static final int FRANKS_PRINT                       = 1821;
    private static final int UNKNOWN_PRINT                      = 1822;
    // --- Nature Spirit (additional) ---
    private static final int WASHING_BOWL_NS                    = 2964;
    private static final int MIRROR_NS                          = 2966;
    private static final int JOURNAL_FILLIMAN                   = 2967;
    private static final int DRUIDIC_SPELL                      = 2968;
    private static final int A_USED_SPELL                       = 2969;
    private static final int SICKLE_MOULD                       = 2976;
    // --- Olaf's Quest (additional) ---
    private static final int CRUDE_CARVING                      = 11032;
    private static final int CRUDER_CARVING                     = 11033;
    private static final int SVENS_LAST_MAP                     = 11034;
    private static final int WINDSWEPT_LOGS                     = 11035;
    private static final int ROTTEN_BARREL                      = 11045;
    // --- One Small Favour ---
    private static final int BLUNT_AXE_OSF                      = 4415;
    private static final int HERBAL_TINCTURE                    = 4416;
    private static final int STODGY_MATTRESS                    = 4425;
    private static final int COMFY_MATTRESS                     = 4426;
    private static final int IRON_OXIDE                         = 4427;
    private static final int ANIMATE_ROCK_SCROLL                = 4428;
    private static final int BROKEN_VANE_DIRECTIONALS           = 4429;
    private static final int DIRECTIONALS_OSF                   = 4430;
    private static final int BROKEN_VANE_ORNAMENT               = 4431;
    private static final int ORNAMENT_OSF                       = 4432;
    private static final int BROKEN_VANE_PILLAR                 = 4433;
    private static final int WEATHERVANE_PILLAR_OSF             = 4434;
    private static final int WEATHER_REPORT                     = 4435;
    private static final int AIRTIGHT_POT                       = 4436;
    private static final int UNFIRED_POT_LID                    = 4438;
    private static final int POT_LID_OSF                        = 4440;
    private static final int BREATHING_SALTS                    = 4442;
    private static final int CHICKEN_CAGE_OSF                   = 4443;
    private static final int SHARPENED_AXE_OSF                  = 4444;
    private static final int RED_MAHOGANY_LOG                   = 4445;
    // --- Plague City (additional) ---
    private static final int HANGOVER_CURE                      = 1504;
    // --- Prince Ali Rescue (additional) ---
    private static final int KEY_PRINT_PAR                      = 2423;
    // --- Sea Slug (additional) ---
    private static final int SEA_SLUG_ITEM                      = 1466;
    private static final int DAMP_STICKS                        = 1467;
    private static final int DRY_STICKS                         = 1468;
    // --- Shield of Arrav (additional) ---
    private static final int PHOENIX_CROSSBOW                   = 767;
    private static final int HALF_CERTIFICATE_LEFT              = 11173;
    private static final int HALF_CERTIFICATE_RIGHT             = 11174;
    // --- The Tourist Trap (additional) ---
    private static final int CELL_DOOR_KEY_TT                   = 1840;
    private static final int SLAVE_SHIRT                        = 1844;
    private static final int SLAVE_ROBE                         = 1845;
    private static final int SLAVE_BOOTS                        = 1846;
    private static final int PROTOTYPE_DART                     = 1849;
    private static final int TECHNICAL_PLANS_TT                 = 1850;
    private static final int BEDABIN_KEY                        = 1852;
    private static final int PROTOTYPE_DART_TIP                 = 1853;
    // --- Tree Gnome Village (additional) ---
    // --- Watchtower (additional) ---
    private static final int RELIC_PART_1                       = 2373;
    private static final int RELIC_PART_2                       = 2374;
    private static final int RELIC_PART_3                       = 2375;
    private static final int OGRE_TOOTH                         = 2377;
    private static final int TOBAN_KEY                          = 2378;
    private static final int ROCK_CAKE_WT                       = 2379;
    private static final int OLD_ROBE_WT                        = 2385;
    private static final int UNUSUAL_ARMOUR_WT                  = 2386;
    private static final int DAMAGED_DAGGER_WT                  = 2387;
    private static final int TATTERED_EYE_PATCH                 = 2388;
    private static final int VIAL_JANGERBERRIES                 = 2389;
    private static final int VIAL_JANGERBERRIES_GUAM            = 2390;
    private static final int GROUND_BAT_BONES_WT                = 2391;
    private static final int TOBAN_GOLD                         = 2393;
    private static final int MAGIC_OGRE_POTION                  = 2395;
    private static final int SHAMAN_ROBE_WT                     = 2397;
    // --- Waterfall Quest (additional) ---
    private static final int GLARIAL_AMULET                     = 295;
    private static final int GLARIAL_URN_FULL                   = 296;
    private static final int GLARIAL_URN_EMPTY                  = 297;
    // --- Witch's House (additional) ---
    private static final int BALL_WITCHS_HOUSE                  = 2407;
    // --- Zogre Flesh Eaters (additional) ---
    private static final int BLACK_PRISM                        = 4808;
    private static final int RUINED_BACKPACK                    = 4810;
    private static final int TORN_PAGE_ZFE                      = 4809;
    private static final int DRAGON_INN_TANKARD                 = 4811;
    private static final int SITHIK_PORTRAIT_GOOD               = 4814;
    private static final int SITHIK_PORTRAIT_BAD                = 4815;
    private static final int SIGNED_PORTRAIT                    = 4816;
    private static final int BOOK_OF_PORTRAITURE                = 4817;
    private static final int OGRE_ARTEFACT                      = 4818;

    private static final int BOOK_OF_HAM                        = 4829;
    private static final int NECROMANCY_BOOK_ZFE                = 4837;
    private static final int CUP_OF_TEA_ZFE                     = 4838;
    private static final int OGRE_GATE_KEY                      = 4839;
    // =========================================================================
    // Batch 3 constants — confirmed 2026-06-16
    // =========================================================================
    // --- Goblin Diplomacy (additional mail colours) ---
    private static final int RED_GOBLIN_MAIL                    = 9054;
    private static final int BLACK_GOBLIN_MAIL                  = 9055;
    private static final int YELLOW_GOBLIN_MAIL                 = 9056;
    private static final int GREEN_GOBLIN_MAIL                  = 9057;
    private static final int PURPLE_GOBLIN_MAIL                 = 9058;
    private static final int PINK_GOBLIN_MAIL                   = 9059;
    private static final int WHITE_GOBLIN_MAIL                  = 26567;
    // --- Enakhra's Lament (additional) ---
    private static final int CAMEL_MOULD_P                      = 7001;
    private static final int STONE_HEAD_CAVITY                  = 7002;
    private static final int STONE_HEAD_LAZIM                   = 6989;
    private static final int STONE_HEAD_ZAMORAK                 = 6990;
    private static final int STONE_HEAD_ICTHLARIN               = 6991;
    private static final int STONE_HEAD_CAMEL                   = 6992;
    private static final int Z_SIGIL                            = 6993;
    private static final int M_SIGIL                            = 6994;
    private static final int R_SIGIL                            = 6995;
    private static final int K_SIGIL                            = 6996;
    private static final int STONE_LEFT_ARM                     = 6997;
    private static final int STONE_RIGHT_ARM                    = 6998;
    private static final int STONE_LEFT_LEG                     = 6999;
    private static final int STONE_RIGHT_LEG                    = 7000;
    // --- The Feud (additional) ---
    private static final int KEYS_FEUD                          = 4589;
    private static final int JEWELS_FEUD                        = 4590;
    private static final int SNAKE_CHARM                        = 4605;
    private static final int NOTE_FIBONACCI                     = 4597;
    private static final int NOTE_NUMBERS                       = 4598;
    private static final int RECEIPT_FEUD                       = 4603;
    private static final int HAGS_POISON                        = 4604;
    private static final int SNAKE_BASKET                       = 4606;
    private static final int SNAKE_BASKET_FULL                  = 4607;
    private static final int RED_HOT_SAUCE                      = 4610;
    private static final int DESERT_DISGUISE                    = 4611;
    // --- Song of the Elves (additional) ---
    private static final int HAND_MIRROR_SOTE                   = 23775;
    private static final int RED_CRYSTAL_SOTE                   = 23776;
    private static final int YELLOW_CRYSTAL_SOTE                = 23777;
    private static final int GREEN_CRYSTAL_SOTE                 = 23778;
    private static final int CYAN_CRYSTAL_SOTE                  = 23779;
    private static final int BLUE_CRYSTAL_SOTE                  = 23780;
    private static final int MAGENTA_CRYSTAL_SOTE               = 23781;
    private static final int FRACTURED_CRYSTAL_SOTE             = 23784;
    private static final int ELDER_CADANTINE_SOTE               = 23798;
    private static final int ELDER_CADANTINE_UNF_SOTE           = 23800;
    private static final int CRYSTAL_SOTE                       = 23802;
    private static final int CRYSTAL_DUST_SOTE                  = 23804;
    private static final int INVERSION_POTION                   = 23806;
    private static final int CRYSTAL_SEED_INERT_SOTE            = 23808;
    private static final int CRYSTAL_SEED_ENCHANTED_SOTE        = 23810;
    private static final int ORB_OF_LIGHT_SOTE                  = 23812;
    private static final int CLUE_SCROLL_SOTE_1                 = 23814;
    private static final int CLUE_SCROLL_SOTE_2                 = 23815;
    private static final int CLUE_SCROLL_SOTE_3                 = 23816;
    private static final int CLUE_SCROLL_SOTE_4                 = 23817;
    private static final int EXPLOSIVE_POTION_SOTE              = 23818;
    private static final int ODE_TO_ETERNITY                    = 23796;
    private static final int BLUE_LIQUID_SOTE                   = 23792;
    private static final int GREEN_POWDER_SOTE                  = 23793;
    private static final int CLEAR_LIQUID_SOTE                  = 23794;
    private static final int RED_POWDER_SOTE                    = 23795;
    private static final int ARDOUGNE_KNIGHT_HELM             = 23785;
    private static final int ARDOUGNE_KNIGHT_PLATEBODY        = 23787;
    private static final int ARDOUGNE_KNIGHT_PLATELEGS        = 23789;
    private static final int ARDOUGNE_KNIGHT_TABARD           = 23791;
    private static final int COLOUR_WHEEL_SOTE                = 6638;
    // --- Haunted Mine (corrected IDs) ---
    private static final int DAMP_TINDERBOX                     = 4073;
    private static final int ZEALOTS_KEY                        = 4078;
    // --- Underground Pass (additional, user-confirmed IDs) ---
    private static final int OLD_JOURNAL_UP                     = 1493;
    private static final int HISTORY_OF_IBAN                    = 1494;
    private static final int PIECE_OF_RAILING                   = 1486;
    private static final int OILY_CLOTH                         = 1485;
    private static final int DOLL_OF_IBAN                       = 1492;
    private static final int AMULET_OF_DOOMION                  = 1498;
    private static final int AMULET_OF_HOLTHION                 = 1499;
    private static final int AMULET_OF_OTHANIAN                 = 1497;
    private static final int PALADIN_BADGE_CARL                 = 1489;
    private static final int PALADIN_BADGE_HARRY                = 1490;
    private static final int PALADIN_BADGE_JERRO                = 1488;
    private static final int DWARF_BREW                         = 1501;
    // --- While Guthix Sleeps (user-confirmed IDs) ---
    private static final int DIRTY_SHIRT_WGS                    = 29521;
    private static final int RUBY_KEY_WGS                       = 29523;
    private static final int NOTES_ON_PRESSURE                  = 29524;
    private static final int MOVARIOS_NOTES_VOL1                = 29525;
    private static final int MOVARIOS_NOTES_VOL2                = 29526;
    private static final int ENRICHED_SNAPDRAGON                = 29530;
    private static final int SUPER_TRUTH_SERUM                  = 29531;
    private static final int TRUTH_SERUM_WGS                    = 29532;
    private static final int SUSPECT_SKETCH                     = 29533;
    private static final int CELL_KEY_WGS                       = 29534;
    private static final int STRANGE_TELEORB                    = 29535;
    private static final int TELEORB_AKRISAE                    = 29536;
    private static final int TELEORB_SILIF                      = 29537;
    private static final int ENRICHED_SNAPDRAGON_SEED           = 29538;
    private static final int ROSE_TINTED_LENS                   = 6956;
    // --- While Guthix Sleeps — additional items ---
    private static final int ARMADYL_COMMUNIQUE               = 29517;
    private static final int UNCONSCIOUS_BROAV                = 29518;
    private static final int BROAV                            = 29519;
    private static final int ELITE_BLACK_FULL_HELM            = 29560;
    private static final int ELITE_BLACK_PLATEBODY            = 29562;
    private static final int ELITE_BLACK_PLATELEGS            = 29564;
    private static final int DARK_SQUALL_HOOD                 = 29566;
    private static final int DARK_SQUALL_ROBE_TOP             = 29568;
    private static final int DARK_SQUALL_ROBE_BOTTOM          = 29570;
    private static final int WASTE_PAPER_BASKET               = 29522;
    private static final int WEIGHT_1KG                       = 29527;
    private static final int WEIGHT_2KG                       = 29528;
    private static final int WEIGHT_5KG                       = 29529;
    private static final int AGILITY_DOLMEN                   = 29539;
    private static final int ENERGY_DOLMEN                    = 29540;
    private static final int RESTORATION_DOLMEN               = 29541;
    private static final int ATTACK_DOLMEN                    = 29542;
    private static final int STRENGTH_DOLMEN                  = 29543;
    private static final int DEFENCE_DOLMEN                   = 29544;
    private static final int COMBAT_DOLMEN                    = 29545;
    private static final int RANGED_DOLMEN                    = 29546;
    private static final int PRAYER_DOLMEN                    = 29547;
    private static final int HUNTER_DOLMEN                    = 29548;
    private static final int FISHING_DOLMEN                   = 29549;
    private static final int MAGIC_DOLMEN                     = 29550;
    private static final int BALANCE_DOLMEN                   = 29551;
    private static final int AIR_BLOCK_WGS                    = 29552;
    private static final int EARTH_BLOCK_WGS                  = 29553;
    private static final int FIRE_BLOCK_WGS                   = 29554;
    private static final int WATER_BLOCK_WGS                  = 29555;
    private static final int VINE_FLOWER                      = 29556;
    private static final int GRIMY_NOTE_WGS                   = 29558;
    private static final int SILIF_ITEM                       = 29572;
    private static final int LIT_EXPLOSIVE                    = 29573;
    private static final int DURADELS_NOTES                   = 29596;
    // --- Heroes' Quest ---
    private static final int PETES_CANDLESTICK                  = 1577;
    private static final int THIEVES_ARMBAND                    = 1579;
    private static final int BLAMISH_SNAIL_SLIME                = 1581;
    private static final int BLAMISH_OIL                        = 1582;
    private static final int FIRE_FEATHER                       = 1583;
    private static final int ID_PAPERS                          = 1584;
    private static final int MISCELLANEOUS_KEY                  = 1586;
    private static final int GRIPS_KEYRING                      = 1588;
    private static final int JAIL_KEY_HQ                        = 1591;
    // --- Lunar Diplomacy ---
    private static final int EMERALD_LANTERN_EMPTY              = 20722;
    private static final int EMERALD_LANTERN_UNLIT              = 9064;
    private static final int EMERALD_LANTERN_LIT                = 9065;
    private static final int EMERALD_LENS                       = 9066;
    private static final int VIAL_OF_WATER_LD                   = 9086;
    private static final int WAKING_SLEEP_VIAL                  = 9087;
    private static final int KINDLING_LD                        = 9094;
    private static final int SOAKED_KINDLING                    = 9095;
    private static final int A_SPECIAL_TIARA                    = 9103;
    // --- Mountain Daughter ---
    private static final int SAFETY_GUARANTEE                   = 4484;
    private static final int WHITE_PEARL                        = 4485;
    private static final int WHITE_PEARL_SEED                   = 4486;
    private static final int HALF_A_ROCK                        = 4487;
    private static final int CORPSE_OF_WOMAN                    = 4488;
    private static final int ASLEIFS_NECKLACE                   = 4489;
    private static final int MUD_MD                             = 4490;
    private static final int MUDDY_ROCK                         = 4492;
    private static final int POLE_MD                            = 4494;
    private static final int BROKEN_POLE_MD                     = 4496;
    // --- Ratcatchers ---
    private static final int CAT_ANTIPOISON                     = 6766;
    private static final int POISONED_CHEESE                    = 6768;
    private static final int MUSIC_SCROLL_RC                    = 6769;
    private static final int DIRECTIONS_RC                      = 6770;
    private static final int POT_OF_WEEDS                       = 6771;
    private static final int SMOULDERING_POT                    = 6772;
    private static final int RAT_POLE_EMPTY                     = 6773;
    private static final int RAT_POLE_ONE                       = 6774;
    private static final int RAT_POLE_TWO                       = 6775;
    private static final int RAT_POLE_THREE                     = 6776;
    private static final int RAT_POLE_FOUR                      = 6777;
    private static final int RAT_POLE_FIVE                      = 6778;
    private static final int RAT_POLE_SIX                       = 6779;
    private static final int RAT_POISON                         = 24;
    // --- Cold War ---
    private static final int PENGUIN_BONGOS                     = 10592;
    private static final int COWBELLS                           = 10593;
    private static final int CLOCKWORK_BOOK                     = 10594;
    private static final int MISSION_REPORT_ARDOUGNE            = 10597;
    private static final int MISSION_REPORT_LUMBRIDGE           = 10598;
    private static final int MISSION_REPORT_FAKE                = 10599;
    private static final int KGP_ID_CARD                        = 10600;
    // --- Tower of Life ---
    private static final int TRIANGLE_SANDWICH                  = 6962;
    private static final int RIVETS_TOL                         = 10866;
    private static final int BINDING_FLUID                      = 10870;
    private static final int PIPE_TOL                           = 10871;
    private static final int PIPE_RING                          = 10872;
    private static final int METAL_SHEET_TOL                    = 10873;
    private static final int COLOURED_BALL                      = 10874;
    private static final int VALVE_WHEEL                        = 10875;
    // --- Garden of Tranquillity ---
    private static final int RUNE_SHARDS                        = 6466;
    private static final int RUNE_DUST                          = 6467;
    private static final int PLANT_CURE_GOT                     = 6468;
    private static final int WHITE_TREE_FRUIT                   = 6469;
    private static final int LIST_GOT                           = 6479;
    private static final int RING_OF_CHAROS                     = 4202;  // obtained Creature of Fenkenstrain; YELLOW when Ring of charos (a) in bank
    private static final int RING_OF_CHAROS_A                   = 6465;  // enchanted during Garden of Tranquillity; upgrade trigger only
    // --- The Curse of Arrav ---
    private static final int ELIAS_MESSAGE                      = 30307;
    private static final int MASTABA_KEY_NORTH                  = 30308;
    private static final int MASTABA_KEY_SOUTH                  = 30309;
    private static final int BASE_PLANS_COA                     = 30310;
    private static final int BASE_KEY_COA                       = 30311;
    private static final int CANOPIC_JAR_COA_OIL                = 30312;
    private static final int CANOPIC_JAR_COA_OIL_BERRIES        = 30313;
    private static final int CANOPIC_JAR_COA_FULL               = 30314;
    private static final int CODE_KEY_COA                       = 30316;
    private static final int DECODER_STRIPS                     = 30317;
    private static final int MAHJARRAT_NOTES_AJ                 = 30318;
    private static final int MAHJARRAT_NOTES_KZ                 = 30319;
    private static final int STONE_TABLET_COA                   = 28816;
    private static final int GRANITE_TABLET_COA                 = 28817;
    private static final int SLATE_TABLET_COA                   = 28818;
    private static final int SHALE_TABLET_COA                   = 28819;
    // --- What Lies Below ---
    private static final int DAGONHAI_HISTORY                   = 11001;
    private static final int SINKETHS_DIARY                     = 11002;
    private static final int EMPTY_FOLDER_WLB                   = 11003;
    private static final int USED_FOLDER_WLB                    = 11006;
    private static final int FULL_FOLDER_WLB                    = 11007;
    private static final int RATS_PAPER                         = 11008;
    private static final int LETTER_TO_SUROK                    = 11009;
    private static final int SUROKS_LETTER                      = 11010;
    private static final int ZAFFS_INSTRUCTIONS                 = 11011;
    private static final int WAND_WLB                           = 11012;
    private static final int INFUSED_WAND                       = 11013;
    // --- Hazeel Cult ---
    private static final int CHEST_KEY_HAZEEL                   = 2404;
    private static final int HAZEEL_SCROLL                      = 2403;
    // --- Tribal Totem ---
    private static final int GUIDE_BOOK_TT                      = 1856;
    private static final int TOTEM_TT                           = 1857;
    private static final int ADDRESS_LABEL                      = 1858;
    // --- Holy Grail ---
    private static final int HOLY_TABLE_NAPKIN                  = 15;
    private static final int MAGIC_WHISTLE                      = 16;
    private static final int GRAIL_BELL                         = 17;
    private static final int MAGIC_GOLD_FEATHER                 = 18;
    private static final int HOLY_GRAIL_ITEM                    = 19;
    // --- Merlin's Crystal / Troll Romance ---
    private static final int BUCKET_OF_WAX              = 30;    // confirmed
    private static final int BLACK_CANDLE_LIT            = 32;    // confirmed
    private static final int BLACK_CANDLE_UNLIT          = 38;    // confirmed
    // --- Mourning's End Part I ---
    private static final int BLOODY_MOURNER_TOP          = 6064;  // confirmed
    private static final int RIPPED_MOURNER_TROUSERS     = 6066;  // confirmed
    private static final int MOURNER_LETTER              = 6071;  // confirmed
    private static final int TEGIDS_SOAP                 = 6072;  // confirmed
    private static final int BROKEN_DEVICE               = 6081;  // confirmed
    private static final int FIXED_DEVICE                = 6082;  // confirmed (gated MEP1+SOTE)
    private static final int TARNISHED_KEY               = 6083;  // confirmed
    private static final int BLUE_TOAD                   = 6089;  // confirmed
    private static final int RED_TOAD                    = 6090;  // confirmed
    private static final int YELLOW_TOAD                 = 6091;  // confirmed
    private static final int GREEN_TOAD                  = 6092;  // confirmed
    private static final int APPLE_BARREL                = 6094;  // confirmed
    private static final int NAPHTHA_APPLE_MIX           = 6095;  // confirmed
    private static final int TOXIC_NAPHTHA               = 6096;  // confirmed
    private static final int SIEVE                       = 6097;  // confirmed
    private static final int TOXIC_POWDER                = 6098;  // confirmed
    private static final int BLUE_DYE_BELLOWS            = 6086;  // confirmed
    private static final int GREEN_DYE_BELLOWS           = 6088;  // confirmed
    private static final int RED_DYE_BELLOWS             = 6085;  // confirmed
    private static final int YELLOW_DYE_BELLOWS          = 6087;  // confirmed
    // --- Forgettable Tale ---
    private static final int KELDA_SEED                  = 6112;  // confirmed
    private static final int KELDA_HOPS                  = 6113;  // confirmed
    private static final int KELDA_STOUT                 = 6118;  // confirmed
    private static final int SQUARE_STONE_YELLOW         = 6119;  // confirmed
    private static final int SQUARE_STONE_GREEN          = 6120;  // confirmed
    private static final int LETTER_FORGETTABLE          = 6121;  // confirmed
    // --- Enakhra's Lament (sandstone construction) ---
    private static final int SANDSTONE_20KG              = 6985;  // confirmed
    private static final int SANDSTONE_32KG              = 6986;  // confirmed
    private static final int SANDSTONE_BODY              = 6987;  // confirmed
    private static final int SANDSTONE_BASE              = 6988;  // confirmed
    // --- Recipe for Disaster — Sir Amik Varze ---
    private static final int VANILLA_POD                  = 7465;  // confirmed (RFD - Sir Amik Varze)
    private static final int POT_OF_CORNFLOUR             = 7468;  // confirmed (RFD - Sir Amik Varze)
    private static final int CORNFLOUR_MIXTURE            = 7470;  // confirmed (RFD - Sir Amik Varze)
    private static final int MILKY_MIXTURE                = 7471;  // confirmed (RFD - Sir Amik Varze)
    private static final int CINNAMON_RFD                 = 7472;  // confirmed (RFD - Sir Amik Varze)
    private static final int BRULEE_EGG                   = 7473;  // confirmed (RFD - Sir Amik Varze)
    private static final int BRULEE_VANILLA               = 7474;  // confirmed (RFD - Sir Amik Varze)
    private static final int BRULEE_RAW                   = 7475;  // confirmed (RFD - Sir Amik Varze)
    private static final int BRULEE_SUPREME               = 7476;  // confirmed (RFD - Sir Amik Varze)
    private static final int EVIL_CHICKENS_EGG            = 7477;  // confirmed (RFD - Sir Amik Varze)
    private static final int DRAGON_TOKEN                 = 7478;  // confirmed (RFD - Sir Amik Varze)
    // --- Recipe for Disaster sub-quests ---
    private static final int DIRTY_BLAST_RFD             = 7497;  // confirmed (RFD - Another Cook's Quest)
    private static final int ROTTEN_TOMATO_RFD           = 2518;  // confirmed (RFD - Another Cook's Quest / ME2 dual-gate)
    private static final int EMPTY_SPICE_SHAKER          = 7496;  // confirmed (RFD - Evil Dave)
    private static final int DWARVEN_ROCK_CAKE_HOT       = 7509;  // confirmed (RFD - Mountain Dwarf)
    private static final int DWARVEN_ROCK_CAKE_COOL      = 7510;  // confirmed (RFD - Mountain Dwarf)
    private static final int SPICY_MAGGOTS_RFD           = 7513;  // confirmed (RFD - Wartface & Bentnoze)
    private static final int BREADCRUMBS                 = 7515;  // confirmed (RFD - Pirate Pete)
    private static final int KELP                        = 7516;  // confirmed (RFD - Pirate Pete)
    private static final int GROUND_KELP                 = 7517;  // confirmed (RFD - Pirate Pete)
    private static final int GIANT_CRAB_MEAT             = 7518;  // confirmed (RFD - Pirate Pete)
    private static final int GROUND_GIANT_CRAB_MEAT      = 7527;  // confirmed (RFD - Pirate Pete)
    private static final int GROUND_COD                  = 7528;  // confirmed (RFD - Pirate Pete)
    private static final int RAW_FISHCAKE                = 7529;  // confirmed (RFD - Pirate Pete)
    private static final int COOKED_FISHCAKE             = 7530;  // confirmed (RFD - Pirate Pete)
    private static final int RAW_GUIDE_CAKE              = 7543;  // confirmed (RFD - Lumbridge Guide)
    private static final int CAKE_OF_GUIDANCE            = 7542;  // confirmed (RFD - Lumbridge Guide)
    private static final int ENCHANTED_EGG_RFD           = 7544;  // confirmed (RFD - Lumbridge Guide)
    private static final int ENCHANTED_MILK_RFD          = 7545;  // confirmed (RFD - Lumbridge Guide)
    private static final int ENCHANTED_FLOUR_RFD         = 7546;  // confirmed (RFD - Lumbridge Guide)
    private static final int BALLOON_TOAD                = 7564;  // confirmed (RFD - Skrach Uglogwee)
    private static final int LIMESTONE_RFD               = 7565;  // confirmed (RFD - Skrach Uglogwee)
    private static final int RAW_JUBBLY                  = 7566;  // confirmed (RFD - Skrach Uglogwee)
    private static final int COOKED_JUBBLY               = 7568;  // confirmed (RFD - Skrach Uglogwee)
    private static final int BURNT_JUBBLY                = 7570;  // confirmed (RFD - Skrach Uglogwee)
    private static final int RED_BANANA                  = 7572;  // confirmed (RFD - King Awowogei)
    private static final int TCHIKI_MONKEY_NUTS          = 7573;  // confirmed (RFD - King Awowogei)
    private static final int SLICED_RED_BANANA           = 7574;  // confirmed (RFD - King Awowogei)
    private static final int TCHIKI_NUT_PASTE            = 7575;  // confirmed (RFD - King Awowogei)
    private static final int SNAKE_CORPSE_RFD            = 7576;  // confirmed (RFD - King Awowogei)
    private static final int RAW_STUFFED_SNAKE           = 7577;  // confirmed (RFD - King Awowogei)
    private static final int ODD_STUFFED_SNAKE           = 7578;  // confirmed (RFD - King Awowogei)
    private static final int STUFFED_SNAKE               = 7579;  // confirmed (RFD - King Awowogei)
    // --- Land of the Goblins ---
    private static final int PHARMAKOS_BERRIES           = 26569; // confirmed
    private static final int EKELESHUUN_KEY              = 26571; // confirmed
    private static final int NAROGOSHUUN_KEY             = 26572; // confirmed
    private static final int HUZAMOGAARB_KEY             = 26573; // confirmed
    private static final int SARAGORGAK_KEY              = 26574; // confirmed
    private static final int HOROGOTHGAR_KEY             = 26575; // confirmed
    private static final int YURKOLGOKH_KEY              = 26576; // confirmed
    private static final int WHITEFISH                   = 26579; // confirmed
    private static final int GOBLIN_POTION_4             = 26581; // confirmed (gated LOTG+HOPESPEARS_WILL)
    private static final int GOBLIN_POTION_3             = 26583; // confirmed
    private static final int GOBLIN_POTION_2             = 26585; // confirmed
    private static final int GOBLIN_POTION_1             = 26587; // confirmed
    private static final int SNOTHEAD_BONE               = 26589; // confirmed (Hopespear's Will)
    private static final int SNAILFEET_BONE              = 26590; // confirmed (Hopespear's Will)
    private static final int MOSSCHIN_BONE               = 26591; // confirmed (Hopespear's Will)
    private static final int REDEYES_BONE                = 26592; // confirmed (Hopespear's Will)
    private static final int STRONGBONES_BONE            = 26593; // confirmed (Hopespear's Will)
    // =========================================================================
    // Batch 4 constants — Novice quest additions, confirmed July 2026
    // =========================================================================
    // --- A Porcine of Interest ---
    private static final int SOURHOG_FOOT                        = 24944; // confirmed
    // --- Current Affairs ---
    private static final int FORM_CR_4P                          = 31327; // confirmed
    private static final int FORM_7R4_5H_UNSIGNED                = 31328; // confirmed
    private static final int FORM_7R4_5H_SIGNED                  = 31329; // confirmed
    private static final int MAYORAL_FISHBOWL                    = 31330; // confirmed
    private static final int TINY_NET_CA                         = 6674;  // confirmed (also used in Big Chompy Bird Hunting — flag on Current Affairs complete)
    // --- Ethically Acquired Antiquities ---
    private static final int TATTERED_SAILS_EAA                  = 29903; // confirmed
    private static final int SAILS_EAA                           = 29904; // confirmed
    private static final int BETTYS_NOTES_EAA                    = 29905; // confirmed
    private static final int STOREROOM_KEY_EAA                   = 29906; // confirmed (distinct from STOREROOM_KEY = 3269, Eadgar's Ruse)
    // --- Gertrude's Cat ---
    private static final int SEASONED_SARDINE                    = 1552;  // confirmed
    private static final int FLUFFS_KITTEN                       = 1554;  // confirmed
    private static final int DOOGLE_LEAVES                       = 1573;  // confirmed (also needed for Big Chompy Bird Hunting — junk only after both complete)
    // --- Monk's Friend ---
    private static final int CHILDS_BLANKET                      = 90;    // confirmed
    // --- Pandemonium ---
    private static final int MYSTERIOUS_MEDALLION_PAND           = 31337; // confirmed
    private static final int OLD_CUP_PAND                        = 31338; // confirmed
    private static final int CAPTAINS_LOG_DURING                 = 31985; // confirmed (during-quest variant; after-quest 31986 not flagged)
    private static final int CRATE_SHIP_PARTS_PAND               = 32807; // confirmed
    // --- Rag and Bone Man I ---
    // 9 bone types × 3 states (unpolished / polished / in vinegar) = 27 IDs
    private static final int GOBLIN_SKULL_UNPOLISHED             = 7812;  // confirmed
    private static final int BONE_IN_VINEGAR_GOBLIN_SKULL        = 7813;  // confirmed
    private static final int GOBLIN_SKULL_POLISHED               = 7814;  // confirmed
    private static final int BEAR_RIBS_UNPOLISHED                = 7815;  // confirmed
    private static final int BONE_IN_VINEGAR_BEAR_RIBS           = 7816;  // confirmed
    private static final int BEAR_RIBS_POLISHED                  = 7817;  // confirmed
    private static final int RAM_SKULL_UNPOLISHED                = 7818;  // confirmed
    private static final int BONE_IN_VINEGAR_RAM_SKULL           = 7819;  // confirmed
    private static final int RAM_SKULL_POLISHED                  = 7820;  // confirmed
    private static final int UNICORN_BONE_UNPOLISHED             = 7821;  // confirmed
    private static final int BONE_IN_VINEGAR_UNICORN             = 7822;  // confirmed
    private static final int UNICORN_BONE_POLISHED               = 7823;  // confirmed
    private static final int GIANT_RAT_BONE_UNPOLISHED           = 7824;  // confirmed
    private static final int BONE_IN_VINEGAR_GIANT_RAT           = 7825;  // confirmed
    private static final int GIANT_RAT_BONE_POLISHED             = 7826;  // confirmed
    private static final int GIANT_BAT_WING_UNPOLISHED           = 7827;  // confirmed
    private static final int BONE_IN_VINEGAR_GIANT_BAT_WING      = 7828;  // confirmed
    private static final int GIANT_BAT_WING_POLISHED             = 7829;  // confirmed
    private static final int MONKEY_PAW_UNPOLISHED               = 7854;  // confirmed
    private static final int BONE_IN_VINEGAR_MONKEY_PAW          = 7855;  // confirmed
    private static final int MONKEY_PAW_POLISHED                 = 7856;  // confirmed
    private static final int CAVE_GOBLIN_SKULL_UNPOLISHED        = 7905;  // confirmed
    private static final int BONE_IN_VINEGAR_CAVE_GOBLIN_SKULL   = 7906;  // confirmed
    private static final int CAVE_GOBLIN_SKULL_POLISHED          = 7907;  // confirmed
    private static final int BIG_FROG_LEG_UNPOLISHED             = 7908;  // confirmed
    private static final int BONE_IN_VINEGAR_BIG_FROG_LEG        = 7909;  // confirmed
    private static final int BIG_FROG_LEG_POLISHED               = 7910;  // confirmed
    // --- The Ribbiting Tale of a Lily Pad Labour Dispute ---
    private static final int LOVE_LETTER_RIBBITING               = 28986; // confirmed
    private static final int PLUSHY_RIBBITING                    = 28987; // confirmed
    // =========================================================================
    // Batch 5 constants — Intermediate quest additions, confirmed July 2026
    // =========================================================================
    // --- Spirits of the Elid ---
    private static final int BALLAD_SPIRITS_ELID                = 6793;  // confirmed
    private static final int STATUETTE_SPIRITS_ELID             = 6785;  // confirmed
    // --- The Lost Tribe ---
    private static final int GOBLIN_SYMBOL_BOOK                 = 5009;  // confirmed
    private static final int KEY_LOST_TRIBE                     = 5010;  // confirmed
    // --- Tale of the Righteous ---
    private static final int DUSTY_NOTE_TALE                    = 25706; // confirmed
    // --- Death on the Isle ---
    private static final int PROP_SWORD_DOTI                    = 29911; // confirmed
    private static final int BUTLERS_TRAY_DOTI_1                = 29912; // confirmed (4 variants)
    private static final int BUTLERS_TRAY_DOTI_2                = 29913; // confirmed
    private static final int BUTLERS_TRAY_DOTI_3                = 30156; // confirmed
    private static final int BUTLERS_TRAY_DOTI_4                = 30157; // confirmed
    private static final int WOLF_MASK_DOTI_1                   = 29930; // confirmed (2 variants)
    private static final int WOLF_MASK_DOTI_2                   = 29936; // confirmed
    private static final int BIRD_MASK_DOTI_1                   = 29931; // confirmed (2 variants)
    private static final int BIRD_MASK_DOTI_2                   = 29938; // confirmed
    private static final int CASE_FILE_DOTI                     = 29922; // confirmed
    private static final int DRINKING_FLASK_DOTI                = 29925; // confirmed
    private static final int JAGUAR_MASK_DOTI_1                 = 29932; // confirmed (2 variants)
    private static final int JAGUAR_MASK_DOTI_2                 = 29940; // confirmed
    private static final int RAM_MASK_DOTI_1                    = 29929; // confirmed (2 variants)
    private static final int RAM_MASK_DOTI_2                    = 29934; // confirmed
    private static final int SHIPPING_CONTRACT_DOTI             = 29927; // confirmed
    private static final int SNAKE_MASK_DOTI_1                  = 29933; // confirmed (2 variants)
    private static final int SNAKE_MASK_DOTI_2                  = 29942; // confirmed
    private static final int THREATENING_NOTE_DOTI              = 29926; // confirmed
    private static final int WINE_LABELS_DOTI                   = 29928; // confirmed
    // --- Wanted! ---
    private static final int SOLUS_HAT                          = 6636;  // confirmed
    // --- Shades of Mort'ton ---
    private static final int DIARY_SHADES_MORTTON               = 3395;  // confirmed
    // --- Scrambled! ---
    private static final int DRAGON_EGG_SCRAMBLED               = 30968; // confirmed
    // --- Twilight's Promise ---
    private static final int QUETZAL_FEED_TP                    = 28975; // confirmed
    // --- The Garden of Death ---
    private static final int STONE_TABLET_GARDEN_MOUNT_QUID     = 27519; // confirmed (Mount Quidamortem)
    private static final int STONE_TABLET_GARDEN_LAKE_MOLCH     = 27520; // confirmed (Lake Molch Island)
    private static final int STONE_TABLET_GARDEN_KEBOS          = 27521; // confirmed (Kebos Swamp)
    private static final int STONE_TABLET_GARDEN_RUINS_MORRA    = 27522; // confirmed (Ruins of Morra)
    // --- The Forsaken Tower ---
    private static final int OLD_NOTES_FORSAKEN_TOWER           = 22774; // confirmed
    // --- The Depths of Despair ---
    private static final int ROYAL_ACCORD_TWILL                 = 21758; // confirmed
    // --- The Queen of Thieves ---
    private static final int LETTER_QUEEN_OF_THIEVES            = 21774; // confirmed
    // --- Tears of Guthix ---
    private static final int MAGIC_STONE_TEARS_GUTHIX           = 4703;  // confirmed
    // --- Shilo Village ---
    private static final int COINS_SHILO_VILLAGE                = 617;   // confirmed (quest-specific coin item, distinct from regular coins 995)
    private static final int BONE_SHARD_SHILO                   = 604;   // confirmed
    // --- Making History ---
    private static final int JOURNAL_MAKING_HISTORY             = 6755;  // confirmed
    private static final int SCROLL_MAKING_HISTORY              = 6758;  // confirmed
    private static final int CHEST_MAKING_HISTORY               = 6759;  // confirmed
    // --- Observatory Quest ---
    private static final int OBSERVATORY_LENS                   = 603;   // confirmed
    // --- Temple of Ikov ---
    private static final int LEVER_IKOV                         = 83;    // confirmed
    // --- The Slug Menace ---
    private static final int WATER_RUNE_SLUG_MENACE             = 9691;  // confirmed (quest-specific variant, distinct from regular water rune 555)
    private static final int MIND_RUNE_SLUG_MENACE              = 9697;  // confirmed (quest-specific variant, distinct from regular mind rune 558)
    private static final int FIRE_RUNE_SLUG_MENACE              = 9699;  // confirmed (quest-specific variant, distinct from regular fire rune 554)
    private static final int EARTH_RUNE_SLUG_MENACE             = 9695;  // confirmed (quest-specific variant, distinct from regular earth rune 557)
    private static final int AIR_RUNE_SLUG_MENACE               = 9693;  // confirmed (quest-specific variant, distinct from regular air rune 556)
    // =========================================================================
    // Batch 6 constants — Experienced quest additions, confirmed July 2026
    // =========================================================================
    // --- Big Chompy Bird Hunting ---
    private static final int RAW_CHOMPY                          = 2876;  // confirmed
    private static final int COOKED_CHOMPY                       = 2878;  // confirmed
    private static final int SEASONED_CHOMPY                     = 2882;  // confirmed
    private static final int IRON_SPIT                           = 7225;  // confirmed
    // --- My Arm’s Big Adventure ---
    private static final int GOUTWEEDY_LUMP                      = 9901;  // confirmed
    private static final int FARMING_MANUAL_MABA                 = 9903;  // confirmed
    private static final int HARDY_GOUT_TUBER                    = 4001;  // confirmed
    private static final int HARDY_GOUT_TUBERS                   = 9902;  // confirmed
    // --- Haunted Mine ---
    private static final int SALVE_SHARD                         = 4082;  // confirmed
    private static final int SALVE_AMULET                        = 4081;  // confirmed
    private static final int SALVE_AMULET_E                      = 10588; // confirmed
    private static final int SALVE_AMULET_I_NMZ                  = 12017; // confirmed
    private static final int SALVE_AMULET_EI_NMZ                 = 12018; // confirmed
    private static final int SALVE_AMULET_I_SW                   = 25250; // confirmed
    private static final int SALVE_AMULET_EI_SW                  = 25278; // confirmed
    private static final int SALVE_AMULET_I_EA                   = 26763; // confirmed
    private static final int SALVE_AMULET_EI_EA                  = 26782; // confirmed
    // --- Shadow of the Storm ---
    private static final int DARK_DYE                            = 24729; // confirmed
    // --- Rag and Bone Man II (27 bone types × 3 states; cave goblin skull 7905/7906/7907 already declared) ---
    private static final int WEREWOLF_BONE_UNPOLISHED             = 7866;  // confirmed
    private static final int WEREWOLF_BONE_IN_VINEGAR             = 7867;  // confirmed
    private static final int WEREWOLF_BONE_POLISHED               = 7868;  // confirmed
    private static final int WOLF_BONE_UNPOLISHED                 = 7830;  // confirmed
    private static final int WOLF_BONE_IN_VINEGAR                 = 7831;  // confirmed
    private static final int WOLF_BONE_POLISHED                   = 7832;  // confirmed
    private static final int BABY_DRAGON_BONE_UNPOLISHED          = 7839;  // confirmed
    private static final int BABY_DRAGON_BONE_IN_VINEGAR          = 7840;  // confirmed
    private static final int BABY_DRAGON_BONE_POLISHED            = 7841;  // confirmed
    private static final int BAT_WING_UNPOLISHED                  = 7833;  // confirmed
    private static final int BAT_WING_IN_VINEGAR                  = 7834;  // confirmed
    private static final int BAT_WING_POLISHED                    = 7835;  // confirmed
    private static final int RAT_BONE_UNPOLISHED                  = 7836;  // confirmed
    private static final int RAT_BONE_IN_VINEGAR                  = 7837;  // confirmed
    private static final int RAT_BONE_POLISHED                    = 7838;  // confirmed
    private static final int BASILISK_BONE_UNPOLISHED             = 7899;  // confirmed
    private static final int BASILISK_BONE_IN_VINEGAR             = 7900;  // confirmed
    private static final int BASILISK_BONE_POLISHED               = 7901;  // confirmed
    private static final int DAGANNOTH_RIBS_UNPOLISHED            = 7857;  // confirmed
    private static final int DAGANNOTH_RIBS_IN_VINEGAR            = 7858;  // confirmed
    private static final int DAGANNOTH_RIBS_POLISHED              = 7859;  // confirmed
    private static final int DESERT_LIZARD_BONE_UNPOLISHED        = 7902;  // confirmed
    private static final int DESERT_LIZARD_BONE_IN_VINEGAR        = 7903;  // confirmed
    private static final int DESERT_LIZARD_BONE_POLISHED          = 7904;  // confirmed
    private static final int EXPERIMENT_BONE_UNPOLISHED           = 7893;  // confirmed
    private static final int EXPERIMENT_BONE_IN_VINEGAR           = 7894;  // confirmed
    private static final int EXPERIMENT_BONE_POLISHED             = 7895;  // confirmed
    private static final int FIRE_GIANT_BONE_UNPOLISHED           = 7872;  // confirmed
    private static final int FIRE_GIANT_BONE_IN_VINEGAR           = 7873;  // confirmed
    private static final int FIRE_GIANT_BONE_POLISHED             = 7874;  // confirmed
    private static final int GHOUL_BONE_UNPOLISHED                = 7881;  // confirmed
    private static final int GHOUL_BONE_IN_VINEGAR                = 7882;  // confirmed
    private static final int GHOUL_BONE_POLISHED                  = 7883;  // confirmed
    private static final int ICE_GIANT_RIBS_UNPOLISHED            = 7875;  // confirmed
    private static final int ICE_GIANT_RIBS_IN_VINEGAR            = 7876;  // confirmed
    private static final int ICE_GIANT_RIBS_POLISHED              = 7877;  // confirmed
    private static final int JACKAL_BONE_UNPOLISHED               = 7914;  // confirmed
    private static final int JACKAL_BONE_IN_VINEGAR               = 7915;  // confirmed
    private static final int JACKAL_BONE_POLISHED                 = 7916;  // confirmed
    private static final int JOGRE_BONE_UNPOLISHED                = 7845;  // confirmed
    private static final int JOGRE_BONE_IN_VINEGAR                = 7846;  // confirmed
    private static final int JOGRE_BONE_POLISHED                  = 7847;  // confirmed
    private static final int MOGRE_BONE_UNPOLISHED                = 7851;  // confirmed
    private static final int MOGRE_BONE_IN_VINEGAR                = 7852;  // confirmed
    private static final int MOGRE_BONE_POLISHED                  = 7853;  // confirmed
    private static final int MOSS_GIANT_BONE_UNPOLISHED           = 7869;  // confirmed
    private static final int MOSS_GIANT_BONE_IN_VINEGAR           = 7870;  // confirmed
    private static final int MOSS_GIANT_BONE_POLISHED             = 7871;  // confirmed
    private static final int OGRE_RIBS_UNPOLISHED                 = 7842;  // confirmed
    private static final int OGRE_RIBS_IN_VINEGAR                 = 7843;  // confirmed
    private static final int OGRE_RIBS_POLISHED                   = 7844;  // confirmed
    private static final int RABBIT_BONE_UNPOLISHED               = 7896;  // confirmed
    private static final int RABBIT_BONE_IN_VINEGAR               = 7897;  // confirmed
    private static final int RABBIT_BONE_POLISHED                 = 7898;  // confirmed
    private static final int SEAGULL_WING_UNPOLISHED              = 7887;  // confirmed
    private static final int SEAGULL_WING_IN_VINEGAR              = 7888;  // confirmed
    private static final int SEAGULL_WING_POLISHED                = 7889;  // confirmed
    private static final int SNAKE_SPINE_UNPOLISHED               = 7860;  // confirmed
    private static final int SNAKE_SPINE_IN_VINEGAR               = 7861;  // confirmed
    private static final int SNAKE_SPINE_POLISHED                 = 7862;  // confirmed
    private static final int TERRORBIRD_WING_UNPOLISHED           = 7878;  // confirmed
    private static final int TERRORBIRD_WING_IN_VINEGAR           = 7879;  // confirmed
    private static final int TERRORBIRD_WING_POLISHED             = 7880;  // confirmed
    private static final int TROLL_BONE_UNPOLISHED                = 7884;  // confirmed
    private static final int TROLL_BONE_IN_VINEGAR                = 7885;  // confirmed
    private static final int TROLL_BONE_POLISHED                  = 7886;  // confirmed
    private static final int UNDEAD_COW_RIBS_UNPOLISHED           = 7890;  // confirmed
    private static final int UNDEAD_COW_RIBS_IN_VINEGAR           = 7891;  // confirmed
    private static final int UNDEAD_COW_RIBS_POLISHED             = 7892;  // confirmed
    private static final int VULTURE_WING_UNPOLISHED              = 7911;  // confirmed
    private static final int VULTURE_WING_IN_VINEGAR              = 7912;  // confirmed
    private static final int VULTURE_WING_POLISHED                = 7913;  // confirmed
    private static final int ZOGRE_BONE_UNPOLISHED                = 7848;  // confirmed
    private static final int ZOGRE_BONE_IN_VINEGAR                = 7849;  // confirmed
    private static final int ZOGRE_BONE_POLISHED                  = 7850;  // confirmed
    private static final int ZOMBIE_BONE_UNPOLISHED               = 7863;  // confirmed
    private static final int ZOMBIE_BONE_IN_VINEGAR               = 7864;  // confirmed
    private static final int ZOMBIE_BONE_POLISHED                 = 7865;  // confirmed
    private static final int JUG_OF_VINEGAR_RBM                   = 7810;  // confirmed
    private static final int POT_OF_VINEGAR_RBM                   = 7811;  // confirmed
    // --- King’s Ransom ---
    private static final int ADDRESS_FORM                         = 11680; // confirmed
    private static final int BLACK_KNIGHT_HELM_KR                 = 11678; // confirmed
    private static final int SCRAP_PAPER_KR                       = 11681; // confirmed
    private static final int HAIR_CLIP_KR                         = 11682; // confirmed
    // --- The Fremennik Isles ---
    private static final int SPLIT_LOG_ISLES                      = 10812; // confirmed
    private static final int HAIR_ISLES                           = 10814; // confirmed
    private static final int YAK_HIDE                             = 10818; // confirmed
    private static final int CURED_YAK_HIDE                       = 10820; // confirmed
    private static final int ROYAL_DECREE_ISLES                   = 10830; // confirmed
    private static final int LIGHT_TAX_BAG                        = 10832; // confirmed
    private static final int NORMAL_TAX_BAG                       = 10833; // confirmed
    private static final int HEFTY_TAX_BAG                        = 10834; // confirmed
    private static final int DECAPITATED_HEAD_ISLES               = 10842; // confirmed
    // --- Darkness of Hallowvale ---
    private static final int MESSAGE_VERTIDA                      = 9633;  // confirmed
    private static final int VYREWATCH_TOP                        = 9634;  // confirmed
    private static final int VYREWATCH_LEGS                       = 9636;  // confirmed
    private static final int VYREWATCH_SHOES                      = 9638;  // confirmed
    private static final int CITIZEN_TOP_DOH                      = 9640;  // confirmed
    private static final int CITIZEN_TROUSERS_DOH                 = 9642;  // confirmed
    private static final int CITIZEN_SHOES_DOH                    = 9644;  // confirmed
    private static final int CASTLE_SKETCH_1                      = 9646;  // confirmed
    private static final int CASTLE_SKETCH_2                      = 9647;  // confirmed
    private static final int CASTLE_SKETCH_3                      = 9648;  // confirmed
    private static final int MESSAGE_FIREPLACE                     = 9649;  // confirmed
    private static final int LARGE_ORNATE_KEY                     = 9651;  // confirmed
    private static final int HAEMALCHEMY_VOLUME_1                  = 9652;  // confirmed
    private static final int SEALED_MESSAGE_DOH                   = 9653;  // confirmed
    private static final int DOOR_KEY_DOH                         = 9654;  // confirmed
    private static final int LADDER_TOP_DOH                       = 9655;  // confirmed
    // --- Royal Trouble ---
    private static final int MINING_PROP                          = 7958;  // confirmed
    private static final int HEAVY_BOX_RT                         = 7959;  // confirmed
    private static final int EMPTY_BOX_RT                         = 7960;  // confirmed
    private static final int BURNT_DIARY_ONE_PAGE                 = 7961;  // confirmed
    private static final int BURNT_DIARY_TWO_PAGES                = 7962;  // confirmed
    private static final int BURNT_DIARY_THREE_PAGES              = 7963;  // confirmed
    private static final int BURNT_DIARY_FOUR_PAGES               = 7964;  // confirmed
    private static final int BURNT_DIARY_FIVE_PAGES               = 7965;  // confirmed
    private static final int LETTER_RT                            = 7966;  // confirmed
    private static final int ENGINE_RT                            = 7967;  // confirmed
    private static final int SCROLL_RT                            = 7968;  // confirmed
    private static final int PULLEY_BEAM                          = 7969;  // confirmed
    private static final int LONG_PULLEY_BEAM                     = 7970;  // confirmed
    private static final int LONGER_PULLEY_BEAM                   = 7971;  // confirmed
    private static final int LIFT_MANUAL                          = 7972;  // confirmed
    private static final int BEAM_RT                              = 7973;  // confirmed
    // --- Cabin Fever ---
    private static final int GUNPOWDER_CF                         = 7108;  // confirmed
    private static final int FUSE_CF                              = 7109;  // confirmed
    private static final int CANNON_BALL_CF                       = 7119;  // confirmed (quest-specific; distinct from regular cannon ball 2)
    private static final int RAMROD                               = 7120;  // confirmed
    private static final int PLUNDER_CF                           = 7143;  // confirmed
    private static final int CANNON_BARREL_CF                     = 7145;  // confirmed
    private static final int BROKEN_CANNON                        = 7146;  // confirmed
    private static final int REPAIR_PLANK                         = 7148;  // confirmed
    private static final int CANISTER_CF                          = 7149;  // confirmed
    private static final int TACKS                                = 7150;  // confirmed
    private static final int ROPE_CF                              = 7155;  // confirmed (quest-specific; distinct from regular rope 954)
    private static final int TINDERBOX_CF                         = 7156;  // confirmed (quest-specific; distinct from regular tinderbox 590)
    // --- Rum Deal ---
    private static final int BRAINDEATH_RUM                       = 7157;  // confirmed
    private static final int BLINDWEED_SEED                       = 6710;  // confirmed
    private static final int BLINDWEED                            = 6711;  // confirmed
    private static final int BUCKET_OF_WATER_RD                   = 6712;  // confirmed (quest-specific)
    private static final int WRENCH_RD                            = 6713;  // confirmed
    private static final int FISHBOWL_AND_NET                     = 6673;  // confirmed
    private static final int SLUGLINGS                            = 6715;  // confirmed
    private static final int KARAMTHULHU                          = 6716;  // confirmed
    private static final int FEVER_SPIDER_BODY                    = 6718;  // confirmed
    private static final int UNSANITARY_SWILL                     = 6719;  // confirmed
    // --- Between a Rock... ---
    private static final int GOLD_HELMET                          = 4567;  // confirmed
    private static final int DWARVEN_LORE                         = 4568;  // confirmed
    private static final int BOOK_PAGE_1_BAR                      = 4569;  // confirmed
    private static final int BOOK_PAGE_2_BAR                      = 4570;  // confirmed
    private static final int BOOK_PAGE_3_BAR                      = 4571;  // confirmed
    private static final int PAGES_BAR                             = 4573;  // confirmed (combined book pages, auto-formed when all 3 pages in inventory)
    private static final int BASE_SCHEMATICS                      = 4574;  // confirmed
    private static final int SCHEMATIC_DONDAKAN                   = 4575;  // confirmed
    private static final int SCHEMATICS_DWARF_ENGINEER            = 4576;  // confirmed
    private static final int SCHEMATICS_KHORVAK                   = 4577;  // confirmed
    private static final int SCHEMATIC_COMPLETE                   = 4578;  // confirmed
    private static final int CANNON_BALL_BAR                      = 4579;  // confirmed (quest-specific)
    // --- Roving Elves ---
    private static final int CONSECRATION_SEED_UNENCHANTED        = 4205;  // confirmed
    private static final int CONSECRATION_SEED_ENCHANTED          = 4206;  // confirmed
    private static final int GLARIAL_PEBBLE                       = 294;   // confirmed
    // --- Throne of Miscellania ---
    private static final int AWFUL_ANTHEM                         = 3894;  // confirmed
    private static final int GOOD_ANTHEM                          = 3895;  // confirmed
    private static final int TREATY_TOM                           = 3896;  // confirmed
    private static final int GIANT_NIB                            = 3897;  // confirmed
    private static final int GIANT_PEN                            = 3898;  // confirmed
    // --- Regicide ---
    private static final int KINGS_MESSAGE                        = 3206;  // confirmed
    private static final int IORWERTHS_MESSAGE                    = 3207;  // confirmed
    private static final int CRYSTAL_PENDANT_REG                  = 3208;  // confirmed
    private static final int SULPHUR_REG                          = 3209;  // confirmed
    private static final int QUICKLIME                            = 3213;  // confirmed
    private static final int POT_OF_QUICKLIME                     = 3214;  // confirmed
    private static final int GROUND_SULPHUR                       = 3215;  // confirmed
    private static final int BARREL_BOMB_UNFUSED                  = 3218;  // confirmed
    private static final int BARREL_BOMB_FUSED                    = 3219;  // confirmed
    private static final int BARREL_OF_COAL_TAR                   = 3220;  // confirmed
    private static final int BARREL_OF_NAPHTHA                    = 3221;  // confirmed
    private static final int NAPHTHA_MIX_SULPHUR                  = 3222;  // confirmed
    private static final int NAPHTHA_MIX_QUICKLIME                = 3223;  // confirmed
    private static final int STRIP_OF_CLOTH_REG                   = 3224;  // confirmed
    private static final int BIG_BOOK_OF_BANGS                    = 3230;  // confirmed
    // --- The Path of Glouphrie (non-shape items) ---
    private static final int CHEST_KEY_POG                        = 28573; // confirmed
    private static final int STRONGROOM_KEY                       = 28574; // confirmed
    private static final int YEWNOCKS_NOTES                       = 28579; // confirmed
    // --- A Kingdom Divided ---
    private static final int RECEIPT_AKD                          = 25793; // confirmed
    private static final int BONE_AKD                             = 25794; // confirmed
    private static final int ROSE_AKD                             = 25795; // confirmed
    private static final int DELIVERY_CONFIRMATION                = 25796; // confirmed
    private static final int ORDER_FORM_AKD                       = 25797; // confirmed
    private static final int DEMONIC_INCANTATIONS                 = 25798; // confirmed
    private static final int BLOODY_KNIFE                         = 25799; // confirmed
    private static final int CULTIST_ROBE_AKD                     = 25800; // confirmed
    private static final int KOUREND_MAP                          = 25801; // confirmed
    private static final int ROSES_DIARY                          = 25802; // confirmed
    private static final int BLUISH_KEY                           = 25803; // confirmed
    private static final int COLD_KEY                             = 25804; // confirmed
    private static final int ROSES_NOTE_MARTIN_HOLT               = 25805; // confirmed
    private static final int ROSES_NOTE_FORTHOS_RUIN              = 25806; // confirmed
    private static final int ROSES_NOTE_SETTLEMENT_RUINS          = 25807; // confirmed
    private static final int ROSES_NOTE_THE_LEGLESS_FAUN          = 25808; // confirmed
    private static final int LIZARDMAN_EGG                        = 25809; // confirmed
    private static final int DAMP_KEY                             = 25810; // confirmed
    private static final int BROKEN_REDIRECTOR                    = 25811; // confirmed
    private static final int SULPHUR_POTION                       = 25812; // confirmed
    private static final int SHIELDING_POTION                     = 25813; // confirmed
    private static final int DECLARATION_AKD                      = 25814; // confirmed
    private static final int DARK_NULLIFIER                       = 25815; // confirmed
    private static final int SHAYZIEN_JOURNAL                     = 25816; // confirmed
    private static final int ROYAL_ACCORD_OF_TWILL                = 25817; // confirmed
    private static final int PROTEST_BANNER                       = 25822; // confirmed
    private static final int RESEARCH_NOTES_AKD                   = 25824; // confirmed
    private static final int TATTY_NOTE                           = 23007; // confirmed
    // --- Shadows of Custodia ---
    private static final int WET_FABRIC_SCRAP                     = 30936; // confirmed
    // --- The Red Reef ---
    private static final int DEEP_SEA_HELMET                      = 31401; // confirmed
    private static final int DEEP_SEA_APPARATUS                   = 31403; // confirmed
    // --- Troubled Tortugans ---
    private static final int MAKESHIFT_BANDAGES                   = 31392; // confirmed
    private static final int TORTUGAN_SCUTE                       = 31393; // confirmed
    private static final int SEA_SHELL_TT                         = 31395; // confirmed
    private static final int LIST_OF_REPAIRS                      = 31397; // confirmed
    // --- Meat and Greet ---
    private static final int TEST_KEBAB_CONNOISSEUR               = 29898; // confirmed
    private static final int TEST_KEBAB_LELIA                     = 29899; // confirmed
    // --- The Heart of Darkness ---
    private static final int STONE_TABLET_THOD                    = 29876; // confirmed
    private static final int TOWER_KEY_THOD                       = 29877; // confirmed
    private static final int BOOK_THOD                            = 29878; // confirmed
    private static final int POEM_THOD                            = 29879; // confirmed
    private static final int SCRAP_OF_PAPER_1                     = 29880; // confirmed
    private static final int SCRAP_OF_PAPER_2                     = 29881; // confirmed
    private static final int SCRAP_OF_PAPER_3                     = 29882; // confirmed
    private static final int COMPLETED_NOTE_THOD                  = 29883; // confirmed
    private static final int BANDAGES_THOD                        = 29884; // confirmed
    private static final int ICON_FIRE_THOD                       = 29885; // confirmed
    private static final int ICON_EARTH_THOD                      = 29886; // confirmed
    private static final int ICON_AIR_THOD                        = 29887; // confirmed
    private static final int ICON_WATER_THOD                      = 29888; // confirmed
    // --- Defender of Varrock ---
    private static final int GRUBBY_KEY_DOV                       = 28803; // confirmed
    private static final int BOTTLE_DOV                           = 28804; // confirmed
    private static final int BOTTLE_OF_MIST                       = 28805; // confirmed
    private static final int IMBUED_BARRONITE                     = 28806; // confirmed
    private static final int SHIELD_OF_ARRAV_ITEM                 = 28807; // confirmed
    private static final int LIST_OF_ELDERS                       = 28808; // confirmed
    // --- A Taste of Hope ---
    private static final int MYSTERIOUS_HERB_ATOH                 = 22402; // confirmed
    private static final int MYSTERIOUS_MEAT                      = 22403; // confirmed
    private static final int MYSTERIOUS_CRUSHED_MEAT              = 22404; // confirmed
    private static final int VIAL_OF_BLOOD_ATOH                   = 22405; // confirmed
    private static final int UNFINISHED_BLOOD_POTION              = 22406; // confirmed
    private static final int BLOOD_POTION                         = 22407; // confirmed
    private static final int UNFINISHED_POTION_ATOH               = 22408; // confirmed
    private static final int POTION_ATOH                          = 22409; // confirmed
    private static final int OLD_NOTES_ATOH                       = 22410; // confirmed
    private static final int OLD_DIARY_ATOH                       = 22411; // confirmed
    private static final int FLAYGIANS_NOTES                      = 22413; // confirmed
    private static final int CHAIN_ATOH                           = 22414; // confirmed
    private static final int EMERALD_SICKLE_B                     = 22433; // confirmed
    // --- Monkey Madness II ---
    private static final int MYSTERIOUS_NOTE_BLANK            = 19505;
    private static final int MYSTERIOUS_NOTE_LEMON            = 19507;
    private static final int MYSTERIOUS_NOTE_HEATED           = 19509;
    private static final int SCRAWLED_NOTE_MM2                = 19511;
    private static final int TRANSLATED_NOTE_MM2              = 19513;
    private static final int BOOK_OF_SPYOLOGY                 = 19515;
    private static final int BRUSH_MM2                        = 19517;
    private static final int JUICE_COATED_BRUSH               = 19519;
    private static final int HANDKERCHIEF                     = 19521;
    private static final int KRUKS_PAW                        = 19523;
    // Kruk monkey greegree (19525) — usable after quest; not added
    private static final int SATCHEL_MM2_EMPTY                = 19527;
    private static final int SATCHEL_MM2_FILLED               = 19528;
    private static final int NIEVE_ITEM                       = 19558;
    private static final int ELYSIAN_SPIRIT_SHIELD_MM2        = 19559;
    private static final int CHARGED_ONYX_MM2                 = 19560;
    private static final int DECONSTRUCTED_ONYX_MM2           = 19562;
    // Royal seed pod (19564) — usable after quest; not added
    private static final int BRONZE_KEY_MM2                   = 19566;
    private static final int COMBAT_SCRATCHED_KEY             = 19568;
    private static final int COMBAT_DAMAGED_KEY               = 19569;
    // TRANSLATION_BOOK_GT (784) — existing constant, reused; update its entry to add MM2 gate
    // --- Dragon Slayer II ---
    private static final int AIVAS_DIARY                      = 22033;
    private static final int VARROCK_CENSUS_RECORDS           = 22035;
    private static final int MALUMAC_JOURNAL                  = 22037;
    private static final int MAP_PIECE_DS2_1                  = 22009;
    private static final int MAP_PIECE_DS2_2                  = 22010;
    private static final int MAP_PIECE_DS2_3                  = 22011;
    private static final int MAP_PIECE_DS2_4                  = 22012;
    private static final int MAP_PIECE_DS2_5                  = 22013;
    private static final int MAP_PIECE_DS2_6                  = 22014;
    private static final int MAP_PIECE_DS2_7                  = 22015;
    private static final int MAP_PIECE_DS2_8                  = 22016;
    private static final int MAP_PIECE_DS2_9                  = 22017;
    private static final int MAP_PIECE_DS2_10                 = 22018;
    private static final int MAP_PIECE_DS2_11                 = 22019;
    private static final int MAP_PIECE_DS2_12                 = 22020;
    private static final int MAP_PIECE_DS2_13                 = 22021;
    private static final int MAP_PIECE_DS2_14                 = 22022;
    private static final int MAP_PIECE_DS2_15                 = 22023;
    private static final int MAP_PIECE_DS2_16                 = 22024;
    private static final int MAP_PIECE_DS2_17                 = 22025;
    private static final int MAP_PIECE_DS2_18                 = 22026;
    private static final int MAP_PIECE_DS2_19                 = 22027;
    private static final int MAP_PIECE_DS2_20                 = 22028;
    private static final int MAP_PIECE_DS2_21                 = 22029;
    private static final int MAP_PIECE_DS2_22                 = 22030;
    private static final int MAP_PIECE_DS2_23                 = 22031;
    private static final int MAP_PIECE_DS2_24                 = 22032;
    private static final int OLD_NOTES_DS2_CRANDOR            = 22051;
    private static final int OLD_NOTES_DS2_UNGAEL_1           = 22053;
    private static final int OLD_NOTES_DS2_UNGAEL_2           = 22055;
    private static final int OLD_NOTES_DS2_UNGAEL_3           = 22057;
    private static final int OLD_NOTES_DS2_UNGAEL_4           = 22059;
    private static final int OLD_NOTES_DS2_UNGAEL_5           = 22061;
    private static final int OLD_NOTES_DS2_UNGAEL_6           = 22063;
    private static final int OLD_NOTES_DS2_LITHKREN_1         = 22065;
    private static final int OLD_NOTES_DS2_LITHKREN_2         = 22067;
    private static final int OLD_NOTES_DS2_LITHKREN_3         = 22069;
    private static final int OLD_NOTES_DS2_LITHKREN_4         = 22071;
    private static final int OLD_NOTES_DS2_LITHKREN_5         = 22073;
    private static final int OLD_NOTES_DS2_LITHKREN_6         = 22075;
    private static final int OLD_NOTES_DS2_LITHKREN_7         = 22077;
    private static final int INERT_LOCATOR_ORB                = 22079;
    // Locator orb (22081) — usable after quest; not added
    private static final int AIVAS_BUST                       = 22086;
    private static final int DRAGON_KEY_DS2                   = 22087;
    private static final int DRAGON_KEY_PIECE_UNGAEL          = 22088;
    private static final int DRAGON_KEY_PIECE_KOUREND         = 22089;
    private static final int DRAGON_KEY_PIECE_MORYTANIA       = 22090;
    private static final int DRAGON_KEY_PIECE_KARAMJA         = 22091;
    private static final int ANCIENT_KEY_DS2                  = 22093;
    private static final int WATER_CONTAINER_DS2              = 22094;
    private static final int SWAMP_PASTE_DS2                  = 22095;
    private static final int REVITALISATION_POTION_DS2        = 22096;
    private static final int ROBERT_BUST                      = 22083;
    private static final int CAMORRA_BUST                     = 22084;
    private static final int TRISTAN_BUST                     = 22085;
    private static final int UNGAEL_LAB_NOTES                 = 25702;
    private static final int LITHKREN_VAULT_NOTES             = 25704;
    private static final int ANCIENT_DIARY                    = 21631;  // shared with DT2 — separate entries
    // DREAM_POTION (11154) — existing constant, add separate DS2 entry
    // --- Desert Treasure II - The Fallen Empire ---
    private static final int BLACKSTONE_FRAG_NORMAL           = 28356;
    private static final int BLACKSTONE_FRAG_GLOWING          = 28357;
    private static final int STRANGE_ICON_DT2                 = 28360;
    private static final int ICON_SEG_PALM_THUMB              = 28361;
    private static final int ICON_SEG_FINGERS                 = 28362;
    private static final int VERY_LONG_ROPE                   = 28363;
    private static final int BASIC_SHADOW_TORCH               = 28364;
    private static final int SUPERIOR_SHADOW_TORCH            = 28365;
    private static final int PERFECTED_SHADOW_TORCH           = 28366;
    private static final int SHADOW_BLOCKER                   = 28367;
    private static final int REVITALISING_IDOL                = 28368;
    private static final int ANIMA_PORTAL                     = 28369;
    private static final int SHADOW_KEY_PURPLE                = 28370;
    private static final int SHADOW_KEY_BLUE                  = 28371;
    private static final int SHADOW_KEY_WHITE                 = 28372;
    private static final int SHADOW_KEY_RED                   = 28373;
    private static final int SHADOW_KEY_GREEN                 = 28374;
    private static final int ANIMA_PORTAL_SCHEMATIC           = 28375;
    private static final int REVITALISING_IDOL_SCHEMATIC      = 28376;
    private static final int SHADOW_BLOCKER_SCHEMATIC         = 28377;
    private static final int BASIC_SHADOW_TORCH_SCHEMATIC     = 28378;
    private static final int SUPERIOR_SHADOW_TORCH_SCHEMATIC  = 28379;
    private static final int PERFECTED_SHADOW_TORCH_SCH_1     = 28380;
    private static final int PERFECTED_SHADOW_TORCH_SCH_2     = 28381;
    private static final int POTION_NOTE_DT2                  = 28382;
    private static final int STRANGE_POTION_DT2               = 28383;
    private static final int KORBAL_HERB                      = 28384;
    private static final int ARGIAN_BERRIES                   = 28385;
    private static final int UNFINISHED_SERUM_1               = 28386;
    private static final int UNFINISHED_SERUM_2               = 28387;
    private static final int STRANGLER_SERUM                  = 28388;
    private static final int TEMPLE_KEY_DT2                   = 28389;
    private static final int BARRICADE_DT2                    = 28390;
    private static final int SATCHEL_DT2                      = 28392;
    private static final int DETONATOR_DT2                    = 28393;
    private static final int TATTY_PAGE_DAY1                  = 28394;
    private static final int TATTY_PAGE_DAY2                  = 28395;
    private static final int TATTY_PAGE_DAY3                  = 28396;
    private static final int TATTY_PAGE_DAY4                  = 28397;
    private static final int TATTY_PAGE_DAY5                  = 28398;
    private static final int TATTY_PAGE_DAY6                  = 28399;
    private static final int TATTY_PAGE_DAY7                  = 28400;
    private static final int MUCKY_NOTE_DT2                   = 28401;
    private static final int UNCHARGED_CELL_DT2               = 28402;
    private static final int CHARGED_CELL_DT2                 = 28403;
    private static final int VARDORVIS_MEDALLION              = 28404;
    private static final int PERSERIYA_MEDALLION              = 28405;
    private static final int SUCELLUS_MEDALLION               = 28406;
    private static final int WHISPERER_MEDALLION              = 28407;
    private static final int HAIR_CLIP_DT2                    = 28408;
    private static final int PRISONER_LETTER                  = 28412;
    private static final int KNIFE_DT2                        = 28413;
    private static final int CHISEL_DT2                       = 28414;
    private static final int LOCKPICK_DT2                     = 28415;
    private static final int SAPPHIRE_KEY_DT2                 = 28416;
    private static final int EMERALD_KEY_DT2                  = 28417;
    private static final int RUBY_KEY_DT2                     = 28418;
    private static final int DIAMOND_KEY_DT2                  = 28419;
    private static final int DRAGONSTONE_KEY_DT2              = 28420;
    private static final int ONYX_KEY_DT2                     = 28421;
    private static final int RATIONS_DT2                      = 28422;
    private static final int REQUISITION_NOTE                 = 28423;
    private static final int GRID_NOTE_DT2                    = 28424;
    private static final int CODE_CONVERTER                   = 28425;
    private static final int MAGIC_LANTERN_DT2               = 28426;
    private static final int STRANGE_SLIDER                   = 28427;
    private static final int LIBRARY_NOTE                     = 28428;
    private static final int WARNING_LETTER                   = 28429;
    private static final int ODD_KEY                          = 28430;
    private static final int ORDERS_NOTE                      = 28431;
    private static final int REFUGEES_NOTE                    = 28432;
    private static final int REQUEST_NOTE                     = 28433;
    private static final int PRAYER_NOTE_DT2                  = 28434;
    private static final int THANK_YOU_NOTE                   = 28435;
    private static final int PROTEST_NOTE                     = 28436;
    private static final int EVACUATION_NOTE                  = 28437;
    private static final int OLD_TABLET_DT2                   = 28438;
    private static final int DAMP_TABLET_INCIDENT             = 28439;
    private static final int DAMP_TABLET_REJOICE              = 28440;
    private static final int SLIMY_KEY_DT2                    = 28441;
    private static final int GUNPOWDER_DT2                    = 28442;
    private static final int SCARRED_SCRAPS                   = 28443;
    private static final int WITHERED_NOTE                    = 28444;
    private static final int ABYSSAL_OBSERVATIONS             = 28461;
    private static final int CRIMSON_FIBRE                    = 28462;
    private static final int RADIANT_FIBRE                    = 28463;
    private static final int ILLUMINATING_LURE                = 28464;
    private static final int SLIMY_TABLET_PART1               = 28465;
    private static final int SLIMY_TABLET_PART2               = 28466;
    private static final int SLIMY_TABLET_PART3               = 28467;
    private static final int GOOEY_NOTE_PART1                 = 28468;
    private static final int GOOEY_NOTE_PART2                 = 28469;
    private static final int GOOEY_NOTE_PART3                 = 28470;
    private static final int STINK_BOMB                       = 28471;
    // Coin purse variants (30941/30942/30943) — NOT bankable; not added
    // Mushrooms/powders/poison (28341/28342/28345/28346/28349/28351) — usable after quest; not added
    // --- The Blood Moon Rises (no Quest enum in RuneLite yet) ---
    private static final int TBMR_FULL_MOON_KEY               = 33724;
    private static final int TBMR_HALF_MOON_KEY               = 33725;
    private static final int TBMR_CRESCENT_MOON_KEY           = 33726;
    private static final int TBMR_GIBBOUS_MOON_KEY            = 33727;
    private static final int TBMR_GILDED_KEY                  = 33729;
    private static final int TBMR_SOLID_KEY                   = 33730;
    private static final int TBMR_DRAKAN_EMBLEM               = 33733;
    private static final int TBMR_LEFT_CREST_HALF             = 33734;
    private static final int TBMR_RIGHT_CREST_HALF            = 33735;
    private static final int TBMR_ANCIENT_SYMBOL              = 33737;
    private static final int TBMR_ANCIENT_SHIELD              = 33738;
    private static final int TBMR_ORNATE_SKULL                = 33741;
    private static final int TBMR_ORNATE_KNIFE                = 33740;
    private static final int TBMR_ORNATE_HOURGLASS            = 33742;
    private static final int TBMR_EXPLOSIVE_BARREL            = 33743;
    private static final int TBMR_SMALL_CLOCK_HAND            = 33744;
    private static final int TBMR_LARGE_CLOCK_HAND            = 33745;
    private static final int TBMR_POEM_SCROLL                 = 33746;
    private static final int TBMR_BROKEN_PIPE                 = 33748;
    private static final int TBMR_SHARP_KNIFE                 = 33749;
    private static final int TBMR_TONGS                       = 33750;
    private static final int TBMR_SYRINGE_NEEDLE              = 33751;
    private static final int TBMR_SYRINGE_BARREL              = 33752;
    private static final int TBMR_SYRINGE_PLUNGER             = 33753;
    private static final int TBMR_EMPTY_SYRINGE               = 33754;
    private static final int TBMR_FULL_SYRINGE                = 33755;
    private static final int TBMR_VENATOR_STOMACH             = 33756;
    private static final int TBMR_SWORD                       = 33757;
    private static final int TBMR_SPEAR                       = 33758;
    private static final int TBMR_BATTLEAXE                   = 33759;
    private static final int TBMR_MACE                        = 33760;
    private static final int TBMR_LOCKBOX                     = 33761;
    private static final int TBMR_ANCESTRAL_DAGGER            = 33762;
    private static final int TBMR_GRID_NOTE                   = 33763;
    private static final int TBMR_MYSTERIOUS_BOOK             = 33764;
    private static final int TBMR_FANCY_GEM_1                 = 33765;
    private static final int TBMR_FANCY_GEM_2                 = 33766;
    private static final int TBMR_GILDED_BOOK                 = 33767;
    private static final int TBMR_VAMPYRE_BOOK                = 33768;
    private static final int TBMR_CLOUDY_GREY_POTION          = 33769;
    private static final int TBMR_WEIGHTLESS_BLACK_POTION     = 33770;
    private static final int TBMR_THICK_RED_POTION            = 33771;
    private static final int TBMR_COLD_BLUISH_WHITE_POTION    = 33772;
    private static final int TBMR_LAB_NOTES                   = 33773;
    private static final int TBMR_CHEMICAL_VIAL               = 33778;
    private static final int TBMR_OLD_COG                     = 33779;
    private static final int TBMR_JOVKAI_KEY                  = 33780;
    private static final int TBMR_MYRMEL_KEY                  = 33783;
    private static final int TBMR_SHADUM_KEY                  = 33784;
    private static final int TBMR_VITUR_KEY                   = 33785;
    private static final int TBMR_TRAPDOOR_KEY                = 33786;
    private static final int TBMR_BOLT_CUTTERS                = 33787;
    private static final int TBMR_CRANK_WHEEL                 = 33788;
    private static final int TBMR_DEAD_BLOOD_SERPENT          = 33791;
    private static final int TBMR_SERPENT_ROPE                = 33792;
    private static final int TBMR_ROTTEN_DIARY                = 33795;
    private static final int TBMR_AMITIRE_LEAVES              = 33796;
    private static final int TBMR_AMITIRE_STEW                = 33797;
    private static final int TBMR_HALLOWED_MARKS              = 33798;
    private static final int TBMR_MYSTERIOUS_JERKY            = 33802;
    private static final int TBMR_PUTRID_STICKY_1             = 33803;
    private static final int TBMR_PUTRID_STICKY_2             = 33804;
    private static final int TBMR_PUTRID_STICKY_3             = 33805;
    private static final int TBMR_PUTRID_STICKY_4             = 33806;
    private static final int TBMR_FOUL_CHUNKY_1               = 33807;
    private static final int TBMR_FOUL_CHUNKY_2               = 33808;
    private static final int TBMR_FOUL_CHUNKY_3               = 33809;
    private static final int TBMR_FOUL_CHUNKY_4               = 33810;
    private static final int TBMR_RANCID_SLIMY_1              = 33811;
    private static final int TBMR_RANCID_SLIMY_2              = 33812;
    private static final int TBMR_RANCID_SLIMY_3              = 33813;
    private static final int TBMR_RANCID_SLIMY_4              = 33814;
    private static final int TBMR_RANK_FROTHY_1               = 33815;
    private static final int TBMR_RANK_FROTHY_2               = 33816;
    private static final int TBMR_RANK_FROTHY_3               = 33817;
    private static final int TBMR_RANK_FROTHY_4               = 33818;
    private static final int TBMR_JAR_OF_CONGEALED_BLOOD      = 33819;
    private static final int TBMR_SMELLY_KEBAB                = 33820;
    private static final int TBMR_SQUIRE_JOURNAL              = 33701;
    private static final int TBMR_FROM_MISTHALIN              = 33702;
    private static final int TBMR_SARL_JOURNAL                = 33703;
    private static final int TBMR_SCRUFFY_NOTEBOOK            = 33704;
    private static final int TBMR_PIOUS_PROCEEDINGS           = 33705;
    private static final int TBMR_LIFE_OF_FRIAR               = 33706;
    private static final int TBMR_ESSIANDAR_NOTES             = 33707;
    private static final int TBMR_IVANDIS_WRITINGS            = 33708;
    // --- Alfred Grimhand's Barcrawl ---
    private static final int BARCRAWL_CARD                     = 455;
    // --- Bear Your Soul ---
    private static final int DAMAGED_SOUL_BEARER               = 19636;
    private static final int SOUL_JOURNEY                      = 19637;
    // --- Daddy's Home ---
    private static final int MARLOS_CRATE                      = 24940;
    private static final int WAXWOOD_LOG                       = 24938;
    private static final int WAXWOOD_PLANK                     = 24939;
    // --- The Enchanted Key ---
    private static final int ENCHANTED_KEY_MQ                  = 6754;
    private static final int GUTHIX_MJOLNIR                    = 6760;
    private static final int SARADOMIN_MJOLNIR                 = 6762;
    private static final int ZAMORAK_MJOLNIR                   = 6764;
    // --- Enter the Abyss ---
    private static final int ABYSSAL_BOOK                      = 5520;
    // --- The Frozen Door ---
    private static final int IMPORTANT_LETTER_FROZEN_DOOR      = 26366;
    private static final int FROZEN_KEY                        = 26356;  // confirmed (The Frozen Door)
    private static final int FROZEN_KEY_PIECE_ARMADYL          = 26358;  // confirmed (The Frozen Door)
    private static final int FROZEN_KEY_PIECE_BANDOS           = 26360;  // confirmed (The Frozen Door)
    private static final int FROZEN_KEY_PIECE_ZAMORAK          = 26362;  // confirmed (The Frozen Door)
    private static final int FROZEN_KEY_PIECE_SARADOMIN        = 26364;  // confirmed (The Frozen Door)
    // --- The General's Shadow ---
    private static final int SEVERED_LEG                       = 10857;
    private static final int SIN_SEERS_NOTE                    = 10856;
    // --- His Faithful Servants ---
    private static final int CRYPT_MAP                         = 28133;
    private static final int STRANGE_ICON_HFS                  = 28130; // His Faithful Servants variant (cf. STRANGE_ICON_DT2 = 28360)
    // --- In Search of Knowledge ---
    private static final int TATTERED_MOON_PAGE               = 23510;
    private static final int TATTERED_SUN_PAGE                = 23512;
    private static final int TATTERED_TEMPLE_PAGE             = 23514;
    private static final int TOME_OF_THE_MOON                 = 23504;
    private static final int TOME_OF_THE_SUN                  = 23506;
    private static final int TOME_OF_THE_TEMPLE               = 23508;
    // --- Vale Totems ---
    private static final int SACRAMENTS_OF_ENT_FOLK           = 31016;
    // =========================================================================
    // Batch 7 constants — Direction B additions, confirmed July 2026
    // =========================================================================
    // --- A Night at the Theatre ---
    private static final int CRYPT_KEY_ANATT                   = 25963;  // confirmed
    private static final int RANIS_HEAD                        = 25964;  // confirmed
    private static final int STRANGE_SPIDER_EGGS               = 25965;  // confirmed
    private static final int SULPHURIC_ACID_ANATT              = 25966;  // confirmed
    private static final int STICKY_NOTE_ANATT                 = 25967;  // confirmed
    private static final int HESPORI_BARK                      = 25968;  // confirmed
    // --- Beneath Cursed Sands ---
    private static final int MESSAGE_BCS                       = 26942;  // confirmed
    private static final int SCARAB_MOULD                      = 26952;  // confirmed
    private static final int SCARAB_EMBLEM_IRON                = 26953;  // confirmed
    private static final int STONE_TABLET_BCS                  = 26954;  // confirmed
    private static final int CHEST_BCS                         = 26955;  // confirmed
    private static final int SCARAB_EMBLEM_GOLD                = 26956;  // confirmed
    private static final int HUMAN_EMBLEM                      = 26957;  // confirmed
    private static final int BABOON_EMBLEM                     = 26958;  // confirmed
    private static final int CROCODILE_EMBLEM                  = 26959;  // confirmed
    private static final int RUSTY_KEY_BCS                     = 26960;  // confirmed
    private static final int LILY_OF_THE_ELID                  = 26961;  // confirmed
    private static final int CURE_CRATE                        = 26962;  // confirmed
    private static final int ODD_SPECTACLES                    = 26963;  // confirmed
    private static final int BOTTLE_OF_TONIC                   = 26965;  // confirmed
    // --- Mourning's End Part II ---
    private static final int HAND_MIRROR_MEPII                 = 6639;   // confirmed
    private static final int RED_CRYSTAL_MEPII                 = 6640;   // confirmed
    private static final int GREEN_CRYSTAL_MEPII               = 6642;   // confirmed
    private static final int CYAN_CRYSTAL_MEPII                = 6643;   // confirmed
    private static final int BLUE_CRYSTAL_MEPII                = 6644;   // confirmed
    private static final int MAGENTA_CRYSTAL_MEPII             = 6645;   // confirmed
    private static final int FRACTURED_CRYSTAL_H               = 6646;   // confirmed
    private static final int FRACTURED_CRYSTAL_V               = 6647;   // confirmed
    private static final int ITEM_LIST_MEPII                   = 6648;   // confirmed
    private static final int EDERENS_JOURNAL                   = 6649;   // confirmed
    private static final int YELLOW_CRYSTAL_MEPII               = 6641;   // confirmed
    private static final int SCRAWLED_NOTES_MEPII              = 23773;  // confirmed
    // --- My Arm's Big Adventure (additional) ---
    // --- Perilous Moons ---
    private static final int ENCHANTED_WATER_TALISMAN_PM       = 28964;  // confirmed
    private static final int ENCHANTED_EARTH_TALISMAN_PM       = 28965;  // confirmed
    private static final int INFUSED_WATER_TALISMAN            = 28966;  // confirmed
    private static final int INFUSED_EARTH_TALISMAN            = 28967;  // confirmed
    private static final int BUILDING_SUPPLIES_PM              = 28968;  // confirmed
    private static final int MOSS_LIZARD_TAIL                  = 28969;  // confirmed
    private static final int BREAM_SCALES                      = 28970;  // confirmed
    private static final int MOONLIGHT_GRUB_PASTE              = 29079;  // confirmed
    // --- RFD - King Awowogei (additional) ---
    private static final int SNAKE_OVERCOOKED                  = 7580;   // confirmed
    // --- RFD - Mountain Dwarf (additional) ---
    private static final int ASGOLDIAN_ALE                     = 7508;   // confirmed
    // --- RFD - Pirate Pete (additional) ---
    private static final int BURNT_GIANT_CRAB_MEAT             = 7520;   // confirmed
    private static final int COOKED_GIANT_CRAB_MEAT_FIVE       = 7521;   // confirmed
    private static final int COOKED_GIANT_CRAB_MEAT_THREE      = 7523;   // confirmed
    private static final int COOKED_GIANT_CRAB_MEAT_TWO        = 7524;   // confirmed
    private static final int COOKED_GIANT_CRAB_MEAT_ONE        = 7525;   // confirmed
    private static final int COOKED_GIANT_CRAB_MEAT_FOUR       = 7526;   // confirmed
    private static final int BURNT_FISHCAKE                    = 7531;   // confirmed
    private static final int MUDSKIPPER_HIDE                   = 7532;   // confirmed
    private static final int BROKEN_CRAB_CLAW                  = 7540;   // confirmed
    private static final int BROKEN_CRAB_SHELL                 = 7541;   // confirmed
    private static final int FRESH_CRAB_CLAW                   = 7536;   // conditional YELLOW (RFD - Pirate Pete)
    private static final int CRAB_CLAW                         = 7537;   // upgrade item — gates Fresh crab claw
    private static final int FRESH_CRAB_SHELL                  = 7538;   // conditional YELLOW (RFD - Pirate Pete)
    private static final int CRAB_HELMET                       = 7539;   // upgrade item — gates Fresh crab shell
    // --- RFD - Sir Amik Varze (additional) ---
    private static final int BOOK_ON_CHICKENS                  = 7464;   // confirmed
    // --- RFD - Wartface & Bentnoze (additional) ---
    private static final int SLOP_OF_COMPROMISE                = 7511;   // confirmed
    private static final int SOGGY_BREAD                       = 7512;   // confirmed
    private static final int DYED_ORANGE                       = 7514;   // confirmed
    // --- Secrets of the North ---
    private static final int DUSTY_SCROLL_SOTN                 = 27595;  // confirmed
    private static final int TULLIA_LETTER                     = 27596;  // confirmed
    private static final int ANCIENT_MAP_SOTN                  = 27597;  // confirmed
    private static final int STRANGE_CIPHER_SOTN               = 27598;  // confirmed
    private static final int STRANGE_LIST_SOTN                 = 27599;  // confirmed
    private static final int DUKE_NOTE_SOTN                    = 27600;  // confirmed
    private static final int NUMBERS_NOTE_SOTN                 = 27601;  // confirmed
    private static final int SETTLEMENTS_NOTE_SOTN             = 27602;  // confirmed
    private static final int LEVER_HANDLE_SOTN                 = 27603;  // confirmed
    private static final int ICY_CHEST_SOTN                    = 27604;  // confirmed
    private static final int JEWEL_SHARD_PILLAR                = 27605;  // confirmed
    private static final int JEWEL_SHARD_CHEST                 = 27606;  // confirmed
    private static final int ANCIENT_JEWEL_SOTN                = 27607;  // confirmed
    private static final int ICY_KEY_SOTN                      = 27608;  // confirmed
    // --- Sins of the Father ---
    private static final int HAEMALCHEMY_VOL_2                 = 24672;  // confirmed
    private static final int VYRE_NOBLE_TOP_UNSCENTED           = 24673;  // confirmed (Sins of the Father)
    private static final int VYRE_NOBLE_LEGS_UNSCENTED          = 24674;  // confirmed (Sins of the Father)
    private static final int VYRE_NOBLE_SHOES_UNSCENTED         = 24675;  // confirmed (Sins of the Father)
    private static final int VYRE_NOBLE_TOP                     = 24676;  // confirmed (Sins of the Father)
    private static final int VYRE_NOBLE_LEGS                    = 24678;  // confirmed (Sins of the Father)
    private static final int VYRE_NOBLE_SHOES                   = 24680;  // confirmed (Sins of the Father)
    private static final int OLD_NOTE_SOTF                     = 24682;  // confirmed
    private static final int TATTY_NOTE_SOTF                   = 24684;  // confirmed (Sins of the Father)
    // --- Swan Song ---
    private static final int IRON_SHEET_SWAN                   = 7941;   // confirmed
    private static final int FRESH_MONKFISH_RAW                = 7942;   // confirmed
    private static final int FRESH_MONKFISH_COOKED             = 7943;   // confirmed
    // --- The Final Dawn ---
    private static final int ANCIENT_TELEPORTER                = 30966;  // confirmed
    private static final int BRANCH_FINAL_DAWN                 = 30945;  // confirmed
    private static final int EMISSARY_SCROLL_TFD               = 30949;  // confirmed
    private static final int KEY_FOREBEARER_JANUS              = 30946;  // confirmed
    private static final int KEY_FINAL_DAWN                    = 30951;  // confirmed
    private static final int MAKESHIFT_BLACKJACK               = 30944;  // confirmed
    private static final int POTATO_SACK_TFD                   = 30947;  // confirmed
    private static final int STONE_TABLET_TFD_MOKHAIOTL        = 30952;  // confirmed (Ryan in-game)
    private static final int STONE_TABLET_TFD_PUZZLE           = 30954;  // confirmed
    private static final int ANCIENT_ROOTS_TFD                 = 30963;  // confirmed
    private static final int CANVAS_PIECE_TFD                  = 30950;  // confirmed
    private static final int KEYSTONE_FRAGMENT_TFD             = 30961;  // confirmed
    private static final int KNIFE_BLADE_TFD                   = 30965;  // confirmed
    private static final int KUHU_ESSENCE                      = 30962;  // confirmed
    private static final int ROOT_KINDLING_TFD                 = 30964;  // confirmed
    // --- The Fremennik Exiles ---
    private static final int FANG_FREMENNIK_EXILES             = 24254;  // confirmed
    private static final int V_SIGIL                           = 24258;  // confirmed
    private static final int V_SIGIL_E                         = 24259;  // confirmed
    private static final int MOLTEN_GLASS_I                    = 24260;  // confirmed
    private static final int LUNAR_GLASS                       = 24261;  // confirmed
    private static final int POLISHING_ROCK                    = 24262;  // confirmed
    private static final int VS_SHIELD                         = 24265;  // confirmed
    private static final int UNSEALED_LETTER_BRUNDT            = 24257;  // confirmed
    private static final int UNSEALED_LETTER_SANDPIT           = 24256;  // confirmed
    private static final int VENOM_GLAND_FE                    = 24255;  // confirmed

    // ---- Batch 8: txt<->Java reconciliation (July 9, 2026) ------------------
    // Another Slice of H.A.M.
    private static final int ARMOUR_SHARD                      = 11048;
    private static final int ARTEFACT_ARMOUR                   = 11049;
    private static final int AXE_HEAD_ASOH                     = 11050;
    private static final int ARTEFACT_AXE                      = 11051;
    private static final int HELMET_FRAGMENT_ASOH              = 11052;
    private static final int ARTEFACT_HELMET                   = 11053;
    private static final int SHIELD_FRAGMENT_ASOH              = 11054;
    private static final int ARTEFACT_SHIELD                   = 11055;
    private static final int SWORD_FRAGMENT_ASOH               = 11056;
    private static final int ARTEFACT_SWORD                    = 11057;
    private static final int MACE_HAM                          = 11058;
    private static final int ARTEFACT_MACE                     = 11059;
    // At First Light
    private static final int SMOOTH_LEAF                       = 28978;
    private static final int STICKY_LEAF                       = 28979;
    private static final int MAKESHIFT_POULTICE                = 28980;
    private static final int FUR_SAMPLE                        = 28981;
    private static final int TRIMMED_FUR                       = 28982;
    private static final int FOXS_REPORT                       = 28983;
    // Below Ice Mountain
    private static final int STEAK_SANDWICH_BIM                = 25631;
    // Big Chompy Bird Hunting
    private static final int WOLFBONE_ARROWTIPS                = 2861;
    private static final int ACHEY_TREE_LOGS                   = 2862;
    private static final int OGRE_ARROW_SHAFT                  = 2864;
    private static final int FLIGHTED_OGRE_ARROW               = 2865;
    private static final int BLOATED_TOAD                      = 2875;
    // Bone Voyage
    private static final int SAWMILL_PROPOSAL                  = 21528;
    private static final int SAWMILL_AGREEMENT                 = 21529;
    private static final int BONE_CHARM                        = 21530;
    private static final int POTION_OF_SEALEGS                 = 21531;
    // Children of the Sun
    private static final int VARLAMORE_INVITATION              = 28972;
    // The Ascent of Arceuus
    private static final int A_DARK_DISPOSITION                = 21770;
    // ---- Batch 9: txt<->Java reconciliation (July 10, 2026) -----------------
    private static final int COPPERS_CRIMSON_COLLAR            = 21263; // Client of Kourend
    private static final int CONDUCTOR                         = 4201;  // Creature of Fenkenstrain
    private static final int USELESS_KEY_DOH                   = 9662;  // Darkness of Hallowvale
    private static final int USELESS_KEY_DOH_BANK              = 16684;
    private static final int CHEST_KEY_FANCY                   = 29923; // Death on the Isle
    private static final int ZANIK_ITEM                        = 8870;  // Death to the Dorgeshuun
    private static final int CRATE_WITH_ZANIK                  = 8871;
    private static final int ELIAS_WHITE_ITEM                  = 28809; // Defender of Varrock
    private static final int BANDITS_BREW                      = 4627;  // Desert Treasure I
    private static final int EARTH_NERVE                       = 28445; // Desert Treasure II
    private static final int WATER_NERVE                       = 28446;
    private static final int FIRE_NERVE                        = 28447;
    private static final int AIR_NERVE                         = 28448;
    private static final int MIND_NERVE                        = 28449;
    private static final int SOUL_NERVE                        = 28450;
    private static final int NATURE_NERVE                      = 28451;
    private static final int SMOKE_NERVE                       = 28452;
    private static final int BLOOD_NERVE                       = 28453;
    private static final int LAW_NERVE                         = 28454;
    private static final int COSMIC_NERVE                      = 28455;
    private static final int ASTRAL_NERVE                      = 28456;
    private static final int WRATH_NERVE                       = 28457;
    private static final int DUST_NERVE                        = 28458;
    private static final int STEAM_NERVE                       = 28459;
    private static final int LAVA_NERVE                        = 28460;
    private static final int INSTRUCTION_MANUAL_DC             = 5;     // Dwarf Cannon
    private static final int VARLAMORE_ENVOY                   = 21756; // The Depths of Despair
    private static final int SPECIMEN_BRUSH                    = 670;   // The Dig Site
    private static final int ROCK_PICK                         = 675;
    private static final int TROWEL                            = 676;
    private static final int OLD_BOOT_DIGSITE                  = 685;
    private static final int GROUND_CHARCOAL                   = 704;
    private static final int VASE_DIGSITE                      = 710;

    // =========================================================================
    // The database
    // =========================================================================
    // ===== Batch 10 reconciliation constants (txt->Java backfill, July 10, 2026) =====
    private static final int EAGLE_CAPE_EP                               = 10171;
    private static final int ELEMENTAL_ORE                               = 2892;
    private static final int ELEMENTAL_METAL                             = 2893;
    private static final int ELEMENTAL_HELMET                            = 9729;
    private static final int PRIMED_BAR                                  = 9727;
    private static final int PRIMED_MIND_BAR                             = 9728;
    private static final int MIND_HELMET                                 = 9733;
    private static final int SCRYING_ORB_FULL                            = 5518;
    private static final int SCRYING_ORB_EMPTY                           = 5519;
    private static final int POISON_ITEM                                 = 273;
    private static final int STEEL_GAUNTLETS                             = 778;
    private static final int WHITE_ROSE_SEED                             = 6453;
    private static final int RED_ROSE_SEED                               = 6454;
    private static final int PINK_ROSE_SEED                              = 6455;
    private static final int VINE_SEED                                   = 6456;
    private static final int DELPHINIUM_SEED                             = 6457;
    private static final int ORCHID_SEED_PINK                            = 6458;
    private static final int ORCHID_SEED_YELLOW                          = 6459;
    private static final int SNOWDROP_SEED                               = 6460;
    private static final int WHITE_TREE_SHOOT_SHOOT                      = 6461;
    private static final int WHITE_TREE_SHOOT_POT                        = 6462;
    private static final int WHITE_TREE_SHOOT_WATERED                    = 6463;
    private static final int WHITE_TREE_SAPLING                          = 6464;
    private static final int TROLLEY                                     = 6478;
    private static final int CLAY_HEAD                                   = 25145;
    private static final int FUR_HEAD                                    = 25146;
    private static final int BLOODY_HEAD                                 = 25147;
    private static final int NEILAN_S_JOURNAL                            = 25152;
    private static final int SIGNED_OAK_BOW                              = 4236;
    private static final int ECTOPHIAL_EMPTY                             = 4252;
    private static final int CARNILLEAN_ARMOUR                           = 2405;
    private static final int HAZEEL_S_MARK                               = 2406;
    private static final int RAW_LAVA_EEL                                = 2148;
    private static final int LAVA_EEL                                    = 2149;
    private static final int EMBALMING_MANUAL                            = 4686;
    private static final int PILE_OF_SALT                                = 4689;
    private static final int SPHINX_S_TOKEN                              = 4691;
    private static final int BUCKET_OF_SALTWATER                         = 4693;
    private static final int MAISA_S_MESSAGE                             = 27298;
    private static final int AKILA_S_JOURNAL                             = 27300;
    private static final int HET_S_CAPTURE                               = 27302;
    private static final int APMEKEN_S_CAPTURE                           = 27304;
    private static final int SCABARAS_CAPTURE                            = 27306;
    private static final int CRONDIS_CAPTURE                             = 27308;
    private static final int THE_WARDENS                                 = 27310;
    private static final int THE_JACKAL_S_TORCH                          = 27312;
    private static final int ROD_DUST                                    = 7636;
    private static final int ASTRONOMY_BOOK                              = 600;
    private static final int GOBLIN_KITCHEN_KEY                          = 601;
    private static final int LENS_MOULD                                  = 602;
    private static final int KARAMJAN_RUM                                = 431;
    private static final int WARRANT                                     = 1503;
    private static final int A_SMALL_KEY                                 = 1507;
    private static final int A_SCRUFFY_NOTE                              = 1508;
    private static final int PICTURE                                     = 1510;
    private static final int MURKY_WATER                                 = 2953;
    private static final int BLESSED_WATER                               = 2954;
    private static final int BOOK_RATPITS                                = 6767;
    private static final int ROCK_MOGRE_CAMP                             = 7533;
    private static final int SKEWERED_CHOMPY                             = 7230;
    private static final int MAKEOVER_VOUCHER                            = 5606;
    private static final int A_HANDWRITTEN_BOOK                          = 9627;
    private static final int KHARIDIAN_HEADPIECE                         = 4591;
    private static final int FAKE_BEARD                                  = 4593;
    private static final int KARIDIAN_DISGUISE                           = 4595;
    private static final int COIN_PURSE_FILLED                           = 30941;
    private static final int COIN_PURSE_EMPTY                            = 30942;
    private static final int COIN_PURSE_SANDY                            = 30943;
    private static final int DINH_S_HAMMER                               = 22761;
    private static final int GENERATOR_CRANK                             = 22762;
    private static final int EIGHT_GALLON_JUG                                = 22763;
    private static final int FIVE_GALLON_JUG                                = 22764;
    private static final int ENERGY_DISK_LEVEL_4                         = 22765;
    private static final int ENERGY_DISK_LEVEL_3                         = 22766;
    private static final int ENERGY_DISK_LEVEL_2                         = 22767;
    private static final int ENERGY_DISK_LEVEL_1                         = 22768;
    private static final int UNKNOWN_FLUID_1                             = 22769;
    private static final int UNKNOWN_FLUID_2                             = 22770;
    private static final int UNKNOWN_FLUID_3                             = 22771;
    private static final int UNKNOWN_FLUID_4                             = 22772;
    private static final int UNKNOWN_FLUID_5                             = 22773;
    private static final int ANCIENT_LETTER                              = 22775;
    private static final int LUNAR_ORE                                   = 9076;
    private static final int LUNAR_BAR                                   = 9077;
    private static final int EMPTY_TAX_BAG                               = 10831;
    private static final int BULGING_TAXBAG                              = 10835;
    private static final int GOLDEN_FLEECE                               = 3693;
    private static final int GOLDEN_WOOL                                 = 3694;
    private static final int HUNTERS_TALISMAN_NORMAL                     = 3696;
    private static final int HUNTERS_TALISMAN_UNCHARGED                  = 3697;
    private static final int FREMENNIK_BALLAD                            = 3699;
    private static final int STURDY_BOOTS                                = 3700;
    private static final int TRACKING_MAP                                = 3701;
    private static final int CUSTOM_BOW_STRING                           = 3702;
    private static final int SEA_FISHING_MAP                             = 3704;
    private static final int WEATHER_FORECAST                            = 3705;
    private static final int CHAMPIONS_TOKEN                             = 3706;
    private static final int LEGENDARY_COCKTAIL                          = 3707;
    private static final int FISCAL_STATEMENT                            = 3708;
    private static final int PROMISSORY_NOTE                             = 3709;
    private static final int WARRIORS_CONTRACT                           = 3710;
    private static final int LOW_ALCOHOL_KEG                             = 3712;
    private static final int BLUE_THREAD                                 = 3719;
    private static final int SMALL_PICK                                  = 3720;
    private static final int TOY_SHIP                                    = 3721;
    private static final int FOUR_5THS_FULL_BUCKET                          = 3723;
    private static final int THREE_5THS_FULL_BUCKET                          = 3724;
    private static final int TWO_5THS_FULL_BUCKET                          = 3725;
    private static final int ONE_5THS_FULL_BUCKET                          = 3726;
    private static final int FROZEN_BUCKET                               = 3728;
    private static final int TWO_3RDS_FULL_JUG                             = 3730;
    private static final int ONE_3RDS_FULL_JUG                             = 3731;
    private static final int FROZEN_JUG                                  = 3733;
    private static final int FROZEN_VASE                                 = 3736;
    private static final int VASE_LID                                    = 3737;
    private static final int SEALED_VASE_EMPTY                           = 3738;
    private static final int SEALED_VASE_FROZEN                          = 3739;
    private static final int SEALED_VASE_WATER                           = 3740;
    private static final int RED_HERRING                                 = 3742;
    private static final int RED_DISK                                    = 3743;
    private static final int WOODEN_DISK                                 = 3744;
    private static final int SEER_S_KEY                                  = 3745;
    private static final int STICKY_RED_GOOP                             = 3746;
    private static final int BEER_TANKARD                                = 3803;
    private static final int OLD_RED_DISK                                = 9947;
    private static final int KASONDE_S_JOURNAL                           = 27511;
    private static final int WORD_TRANSLATIONS                           = 27513;
    private static final int DIRTY_NOTE_MOUNT_QUIDAMORTEM                = 27515;
    private static final int DIRTY_NOTE_LAKE_MOLCH_ISLAND                = 27516;
    private static final int DIRTY_NOTE_RUINS_OF_MORRA                   = 27517;
    private static final int WARNING_NOTE                                = 27518;
    private static final int WOOD_CARVING_MOUNT_QUIDAMORTEM_1            = 27523;
    private static final int WOOD_CARVING_MOUNT_QUIDAMORTEM_2            = 27524;
    private static final int WOOD_CARVING_LAKE_MOLCH_ISLAND_1            = 27525;
    private static final int WOOD_CARVING_LAKE_MOLCH_ISLAND_2            = 27526;
    private static final int WOOD_CARVING_LAKE_MOLCH_ISLAND_3            = 27527;
    private static final int WOOD_CARVING_KEBOS_SWAMP_1                  = 27528;
    private static final int WOOD_CARVING_KEBOS_SWAMP_2                  = 27529;
    private static final int WOOD_CARVING_KEBOS_SWAMP_3                  = 27530;
    private static final int WOOD_CARVING_KEBOS_SWAMP_4                  = 27531;
    private static final int COMPASS_THE_GARDEN_OF_DEATH                 = 27532;
    private static final int WOOD_CARVING_RUINS_OF_MORRA_1               = 27533;
    private static final int WOOD_CARVING_RUINS_OF_MORRA_2               = 27534;
    private static final int WOOD_CARVING_RUINS_OF_MORRA_3               = 27535;
    private static final int WOOD_CARVING_RUINS_OF_MORRA_4               = 27536;
    private static final int WOOD_CARVING_RUINS_OF_MORRA_5               = 27537;
    private static final int DWARVEN_BATTLEAXE_RUSTY                     = 5056;
    private static final int DWARVEN_BATTLEAXE_SHARPENED                 = 5057;
    private static final int DWARVEN_BATTLEAXE_SAPPHIRES                 = 5058;
    private static final int DWARVEN_BATTLEAXE_REPAIRED                  = 5059;
    private static final int LEFT_BOOT                                   = 5062;
    private static final int RIGHT_BOOT                                  = 5063;
    private static final int EXQUISITE_BOOTS                             = 5064;
    private static final int BOOK_ON_COSTUMES                            = 5065;
    private static final int MEETING_NOTES                               = 5066;
    private static final int EXQUISITE_CLOTHES                           = 5067;
    private static final int VARMEN_S_NOTES                              = 4616;
    private static final int DISPLAY_CABINET_KEY                         = 4617;
    private static final int BLACK_MUSHROOM                              = 4620;
    private static final int PHOENIX_FEATHER                             = 4621;
    private static final int BLACK_DYE                                   = 4622;
    private static final int PHOENIX_QUILL_PEN                           = 4623;
    private static final int INVASION_PLANS                              = 794;
    private static final int WOODEN_CAT                                  = 10891;
    private static final int SANDY_HAND                                  = 6945;
    private static final int BEER_SOAKED_HAND                            = 6946;
    private static final int BERT_S_ROTA                                 = 6947;
    private static final int SANDY_S_ROTA                                = 6948;
    private static final int A_MAGIC_SCROLL                              = 6949;
    private static final int MAGICAL_ORB_INACTIVE                        = 6950;
    private static final int MAGICAL_ORB_ACTIVE                          = 6951;
    private static final int TRUTH_SERUM                                 = 6952;
    private static final int BOTTLED_WATER                               = 6953;
    private static final int REDBERRY_JUICE                              = 6954;
    private static final int PINK_DYE                                    = 6955;
    private static final int WIZARD_S_HEAD                               = 6957;
    private static final int SAND                                        = 6958;
    private static final int PRINCE_ITZLA_ARKAN_ITEM                     = 29867;

    // ===== Batch 11 constants (txt->Java union add, July 10, 2026) =====
    private static final int LUNAR_STAFF_PT1                          = 9091;  // Lunar Diplomacy
    private static final int LUNAR_STAFF_PT2                          = 9092;  // Lunar Diplomacy
    private static final int LUNAR_STAFF_PT3                          = 9093;  // Lunar Diplomacy
    private static final int BLURITE_SWORD                            = 667;  // Blurite sword
    private static final int BLURITE_ORE                              = 668;  // Blurite ore
    private static final int PAPYRUS                                  = 970;  // Papyrus
    private static final int BROOCH                                   = 5008;  // Brooch
    private static final int SILVERWARE                               = 5011;  // Silverware
    private static final int PEACE_TREATY                             = 5012;  // Peace treaty
    private static final int LETTER_KING_LATHAS                       = 6756;  // Letter (King Lathas)
    private static final int LETTER_JORRAL                            = 6757;  // Letter (Jorral)
    private static final int PRIFDDINAS_HISTORY                       = 6073;  // Prifddinas' history
    private static final int EASTERN_DISCOVERY                        = 6075;  // Eastern discovery
    private static final int EASTERN_SETTLEMENT                       = 6077;  // Eastern settlement
    private static final int THE_GREAT_DIVIDE                         = 6079;  // The great divide
    private static final int ROTTEN_APPLES                            = 6093;  // Rotten apples
    private static final int BLACKENED_CRYSTAL                        = 6650;  // Blackened crystal
    private static final int NEWLY_MADE_CRYSTAL_UNCHARGED             = 6651;  // Newly made crystal (uncharged)
    private static final int ROD_MOULD                                = 7649;  // Rod mould
    private static final int DREAM_LOG                                = 9067;  // Dream log
    private static final int MOONCLAN_MANUAL                          = 9078;  // Moonclan manual
    private static final int SUQAH_HIDE                               = 9080;  // Suqah hide
    private static final int SUQAH_LEATHER                            = 9081;  // Suqah leather
    private static final int EMPTY_VIAL                               = 9085;  // Empty vial
    private static final int GUAM_VIAL                                = 9088;  // Guam vial
    private static final int MARR_VIAL                                = 9089;  // Marr vial
    private static final int GUAM_MARR_VIAL                           = 9090;  // Guam-marr vial
    private static final int PLAIN_OF_MUD_SPHERE                      = 26577;  // Plain of mud sphere
    private static final int LARGE_EGG                                = 30967;  // Large egg
    private static final int JAGUAR_EGG                               = 30969;  // Jaguar egg
    private static final int EGG_HUMPHREY_DUMPHREY                    = 30970;  // Egg (Humphrey Dumphrey)
    private static final int ALAN_S_BONES                             = 30973;  // Alan's bones
    private static final int ALAN_S_BONEMEAL                          = 30975;  // Alan's bonemeal
    private static final int DAMIANA_LEAVES                           = 30977;  // Damiana leaves
    private static final int DAMIANA_WATER                            = 30979;  // Damiana water
    private static final int DAMIANA_TEA                              = 30981;  // Damiana tea
    private static final int DAMIANA_TEA_MILKY                        = 30983;  // Damiana tea (milky)
    private static final int CUP_OF_TEA_DAMIANA                       = 30985;  // Cup of tea (damiana)
    private static final int CUP_OF_TEA_MILKY_DAMIANA                 = 30987;  // Cup of tea (milky damiana)
    private static final int ACATZIN_S_AXE_DAMAGED                    = 30989;  // Acatzin's axe (damaged)
    private static final int ACATZIN_S_AXE_REPAIRED                   = 30990;  // Acatzin's axe (repaired)
    private static final int ALAN_S_BLESSED_BONES                     = 31075;  // Alan's blessed bones

    private static List<JunkEntry> buildBatch1A() { return Arrays.asList(
        // ---- The Corsair Curse --------------------------------------------------
        // Quest.THE_CORSAIR_CURSE — confirmed (ID: 18)
        new JunkEntry(OGRE_ARTEFACT_CORSAIR_CURSE, "Ogre artefact (The Corsair Curse)", "Quest complete — artefact given to Chief Tess mid-quest.", Quest.THE_CORSAIR_CURSE),
        // ---- Demon Slayer -------------------------------------------------------
        // Quest.DEMON_SLAYER — confirmed
        new JunkEntry(SILVERLIGHT_KEY_SIR_PRYSIN,
            "Silverlight key (Sir Prysin)",
            "Quest complete — key no longer needed.", Quest.DEMON_SLAYER),
        new JunkEntry(SILVERLIGHT_KEY_WIZARD_TRAIBORN,
            "Silverlight key (Wizard Traiborn)",
            "Quest complete — key no longer needed.", Quest.DEMON_SLAYER),
        new JunkEntry(SILVERLIGHT_KEY_CAPTAIN_ROVIN,
            "Silverlight key (Captain Rovin)",
            "Quest complete — key no longer needed.", Quest.DEMON_SLAYER),
        // Silverlight: YELLOW — junk if Demon Slayer done AND Darklight or Arclight banked
        new JunkEntry(SILVERLIGHT, "Silverlight", JunkTier.YELLOW,
            "Superseded by Darklight or Arclight — only flagged if Darklight or Arclight is in bank.",
            Quest.DEMON_SLAYER, new int[]{DARKLIGHT, ARCLIGHT}),
        // ---- Dragon Slayer I ---------------------------------------------------
        // Quest.DRAGON_SLAYER_I — confirmed
        new JunkEntry(MAP_PART_MELZAR,  "Map part (Melzar)",  "Quest complete — map fragment useless.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(MAP_PART_THALZAR, "Map part (Thalzar)", "Quest complete — map fragment useless.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(MAP_PART_LOZAR,   "Map part (Lozar)",   "Quest complete — map fragment useless.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(CRANDOR_MAP,      "Crandor map",        "Quest complete — map no longer needed.", Quest.DRAGON_SLAYER_I),
        // --- Dragon Slayer II ---
        new JunkEntry(ANCIENT_KEY_DS2, "Ancient key",
            "Quest complete (Dragon Slayer II) — opens the ancient door on Lithkren.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(DRAGON_KEY_PIECE_UNGAEL, "Dragon key piece",
            "Quest complete (Dragon Slayer II) — Ungael piece of the Dragon key.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(DRAGON_KEY_PIECE_KOUREND, "Dragon key piece",
            "Quest complete (Dragon Slayer II) — Kourend piece of the Dragon key.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(DRAGON_KEY_PIECE_MORYTANIA, "Dragon key piece",
            "Quest complete (Dragon Slayer II) — Morytania piece of the Dragon key.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(DRAGON_KEY_PIECE_KARAMJA, "Dragon key piece",
            "Quest complete (Dragon Slayer II) — Karamja piece of the Dragon key.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(DRAGON_KEY_DS2, "Dragon key",
            "Quest complete (Dragon Slayer II) — assembled key to open the vault.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(AIVAS_DIARY, "Aivas' diary",
            "Quest complete (Dragon Slayer II) — journal found in Myths' Guild; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_CRANDOR, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Crandor notes; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_1, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 1.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_2, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 2.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_3, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 3.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_4, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 4.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_5, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 5.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_6, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 6.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_LITHKREN_7, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Lithkren notes part 7.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_1, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 1.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_2, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 2.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_3, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 3.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_4, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 4.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_5, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 5.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(OLD_NOTES_DS2_UNGAEL_6, "Old notes (Dragon Slayer II)",
            "Quest complete (Dragon Slayer II) — Ungael notes part 6.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(REVITALISATION_POTION_DS2, "Revitalisation potion",
            "Quest complete (Dragon Slayer II) — restores stats in the final fight; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(SWAMP_PASTE_DS2, "Swamp paste",
            "Quest complete (Dragon Slayer II) — used to repair the ship; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_1, "Map piece", "Quest complete (Dragon Slayer II) — piece 1.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_2, "Map piece", "Quest complete (Dragon Slayer II) — piece 2.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_3, "Map piece", "Quest complete (Dragon Slayer II) — piece 3.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_4, "Map piece", "Quest complete (Dragon Slayer II) — piece 4.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_5, "Map piece", "Quest complete (Dragon Slayer II) — piece 5.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_6, "Map piece", "Quest complete (Dragon Slayer II) — piece 6.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_7, "Map piece", "Quest complete (Dragon Slayer II) — piece 7.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_8, "Map piece", "Quest complete (Dragon Slayer II) — piece 8.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_9, "Map piece", "Quest complete (Dragon Slayer II) — piece 9.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_10, "Map piece", "Quest complete (Dragon Slayer II) — piece 10.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_11, "Map piece", "Quest complete (Dragon Slayer II) — piece 11.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_12, "Map piece", "Quest complete (Dragon Slayer II) — piece 12.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_13, "Map piece", "Quest complete (Dragon Slayer II) — piece 13.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_14, "Map piece", "Quest complete (Dragon Slayer II) — piece 14.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_15, "Map piece", "Quest complete (Dragon Slayer II) — piece 15.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_16, "Map piece", "Quest complete (Dragon Slayer II) — piece 16.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_17, "Map piece", "Quest complete (Dragon Slayer II) — piece 17.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_18, "Map piece", "Quest complete (Dragon Slayer II) — piece 18.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_19, "Map piece", "Quest complete (Dragon Slayer II) — piece 19.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_20, "Map piece", "Quest complete (Dragon Slayer II) — piece 20.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_21, "Map piece", "Quest complete (Dragon Slayer II) — piece 21.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_22, "Map piece", "Quest complete (Dragon Slayer II) — piece 22.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_23, "Map piece", "Quest complete (Dragon Slayer II) — piece 23.", Quest.DRAGON_SLAYER_II),
        new JunkEntry(MAP_PIECE_DS2_24, "Map piece", "Quest complete (Dragon Slayer II) — piece 24.", Quest.DRAGON_SLAYER_II),
        // DREAM_POTION (11154) — already flagged via Dream Mentor entry (DS2 requires Dream Mentor; no duplicate entry needed)
        new JunkEntry(INERT_LOCATOR_ORB, "Inert locator orb",
            "Quest complete (Dragon Slayer II) — becomes Locator orb when charged; inert version is junk.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(WATER_CONTAINER_DS2, "Water container",
            "Quest complete (Dragon Slayer II) — used to collect water at Lithkren; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(VARROCK_CENSUS_RECORDS, "Varrock census records",
            "Quest complete (Dragon Slayer II) — used to research Camorra's family history.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(MALUMAC_JOURNAL, "Malumac's journal",
            "Quest complete (Dragon Slayer II) — found at Ungael; details the vault.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(UNGAEL_LAB_NOTES, "Ungael lab notes",
            "Quest complete (Dragon Slayer II) — laboratory records; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(LITHKREN_VAULT_NOTES, "Lithkren vault notes",
            "Quest complete (Dragon Slayer II) — vault records; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(AIVAS_BUST, "Aivas bust",
            "Quest complete (Dragon Slayer II) — ornamental bust; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(CAMORRA_BUST, "Camorra bust",
            "Quest complete (Dragon Slayer II) — ornamental bust; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(ROBERT_BUST, "Robert bust",
            "Quest complete (Dragon Slayer II) — ornamental bust; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        new JunkEntry(TRISTAN_BUST, "Tristan bust",
            "Quest complete (Dragon Slayer II) — ornamental bust; no use after quest.",
            Quest.DRAGON_SLAYER_II),
        // ---- Ernest the Chicken ------------------------------------------------
        // Quest.ERNEST_THE_CHICKEN — confirmed
        new JunkEntry(PRESSURE_GAUGE,         "Pressure gauge",              "Quest complete — puzzle component.", Quest.ERNEST_THE_CHICKEN),
        new JunkEntry(RUBBER_TUBE,            "Rubber tube",                 "Quest complete — puzzle component.", Quest.ERNEST_THE_CHICKEN),
        new JunkEntry(OIL_CAN,               "Oil can",                     "Quest complete — puzzle component.", Quest.ERNEST_THE_CHICKEN),
        new JunkEntry(RATS_TAIL,              "Rat's tail",                  "Quest complete — ingredient no longer needed.", Quest.WITCHS_POTION),
        new JunkEntry(KEY_ERNEST_THE_CHICKEN, "Key (Ernest the Chicken)",    "Quest complete — door key.", Quest.ERNEST_THE_CHICKEN),
        // ---- Goblin Diplomacy --------------------------------------------------
        // Goblin mail colours. Red/Black/Yellow/Green/Purple/Pink/White gate on Land of the Goblins only (Ryan ruling Jul 10 2026).
        // Orange/Blue remain gated on both Goblin Diplomacy and Land of the Goblins. Plain "Goblin mail" (688) is NOT junk — removed.
        new JunkEntry(ORANGE_GOBLIN_MAIL, "Orange goblin mail",  "Used in Goblin Diplomacy and Land of the Goblins — junk after both.", Quest.GOBLIN_DIPLOMACY).withRequiredQuest2(Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(BLUE_GOBLIN_MAIL,   "Blue goblin mail",    "Used in Goblin Diplomacy and Land of the Goblins — junk after both.", Quest.GOBLIN_DIPLOMACY).withRequiredQuest2(Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(GREEN_GOBLIN_MAIL,  "Green goblin mail",   "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(RED_GOBLIN_MAIL,    "Red goblin mail",     "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(BLACK_GOBLIN_MAIL,  "Black goblin mail",   "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(PURPLE_GOBLIN_MAIL, "Purple goblin mail",  "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(PINK_GOBLIN_MAIL,   "Pink goblin mail",    "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(YELLOW_GOBLIN_MAIL, "Yellow goblin mail",  "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(WHITE_GOBLIN_MAIL,  "White goblin mail",   "Quest complete (Land of the Goblins) \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        // ---- Imp Catcher -------------------------------------------------------
        // Quest.IMP_CATCHER — confirmed
        new JunkEntry(RED_BEAD,    "Red bead",    "Quest complete — bead no longer needed.", Quest.IMP_CATCHER),
        new JunkEntry(YELLOW_BEAD, "Yellow bead", "Quest complete — bead no longer needed.", Quest.IMP_CATCHER),
        new JunkEntry(BLACK_BEAD,  "Black bead",  "Quest complete — bead no longer needed.", Quest.IMP_CATCHER),
        new JunkEntry(WHITE_BEAD,  "White bead",  "Quest complete — bead no longer needed.", Quest.IMP_CATCHER),
        // ---- The Ides of Milk --------------------------------------------------
        // Quest.THE_IDES_OF_MILK — confirmed (ID: 9645)
        new JunkEntry(THE_GROATS_PRINCIPLES, "The groats principles", "Quest complete — book snatched by Cassius mid-quest.", Quest.THE_IDES_OF_MILK),
        new JunkEntry(MILK_SAMPLE_FIRST,     "Milk sample",           "Quest complete — first sample consumed mid-quest.",  Quest.THE_IDES_OF_MILK),
        new JunkEntry(MILK_SAMPLE_SECOND,    "Milk sample",           "Quest complete — second sample consumed mid-quest.", Quest.THE_IDES_OF_MILK),
        // ---- The Knight's Sword ------------------------------------------------
        // Quest.THE_KNIGHTS_SWORD — confirmed
        new JunkEntry(PORTRAIT, "Portrait", "Quest complete (The Knight's Sword) — painting, no further use.", Quest.THE_KNIGHTS_SWORD),
        // ---- Misthalin Mystery -------------------------------------------------
        // Quest.MISTHALIN_MYSTERY — confirmed (ID: 94)
        new JunkEntry(MANOR_KEY_MISTHALIN,   "Manor key (Misthalin Mystery)",    "Quest complete — door key.",  Quest.MISTHALIN_MYSTERY),
        new JunkEntry(RUBY_KEY_MISTHALIN,    "Ruby key (Misthalin Mystery)",     "Quest complete — room key.",  Quest.MISTHALIN_MYSTERY),
        new JunkEntry(EMERALD_KEY_MISTHALIN, "Emerald key (Misthalin Mystery)",  "Quest complete — room key.",  Quest.MISTHALIN_MYSTERY),
        new JunkEntry(SAPPHIRE_KEY_MISTHALIN,"Sapphire key (Misthalin Mystery)", "Quest complete — room key.",  Quest.MISTHALIN_MYSTERY),
        new JunkEntry(KILLERS_KNIFE,         "Killer's knife",                   "Quest complete — combat item found in manor.", Quest.MISTHALIN_MYSTERY),
        new JunkEntry(NOTES_MISTHALIN_1,     "Notes (Misthalin Mystery, 1)",     "Quest complete — clue note.", Quest.MISTHALIN_MYSTERY),
        new JunkEntry(NOTES_MISTHALIN_2,     "Notes (Misthalin Mystery, 2)",     "Quest complete — clue note.", Quest.MISTHALIN_MYSTERY),
        new JunkEntry(NOTES_MISTHALIN_3,     "Notes (Misthalin Mystery, 3)",     "Quest complete — clue note.", Quest.MISTHALIN_MYSTERY),
        // ---- Pirate's Treasure -------------------------------------------------
        // Quest.PIRATES_TREASURE — confirmed
        new JunkEntry(CHEST_KEY_PIRATES_TREASURE, "Chest key (Pirate's Treasure)", "Quest complete — key.", Quest.PIRATES_TREASURE),
        new JunkEntry(PIRATE_MESSAGE,          "Pirate message",               "Quest complete — message.", Quest.PIRATES_TREASURE),
        // ---- Romeo & Juliet ----------------------------------------------------
        // Quest.ROMEO__JULIET — confirmed
        new JunkEntry(MESSAGE_ROMEO_JULIET, "Message (Romeo & Juliet)", "Quest complete — letter.",  Quest.ROMEO__JULIET),
        // Cadava berries: also needed for "Making Friends with My Arm" — only junk when BOTH are done.
        // Quest.MAKING_FRIENDS_WITH_MY_ARM — confirmed (ID: 91)
        new JunkEntry(CADAVA_BERRIES, "Cadava berries",
            "Used in Romeo & Juliet and Making Friends with My Arm — junk only after Making Friends with My Arm is complete.",
            Quest.MAKING_FRIENDS_WITH_MY_ARM)
            .withWikiUrl("https://oldschool.runescape.wiki/w/Cadava_berries"),
        // ---- Rune Mysteries ----------------------------------------------------
        // Quest.RUNE_MYSTERIES — confirmed
        new JunkEntry(RESEARCH_PACKAGE,              "Research package",                    "Quest complete — package.", Quest.RUNE_MYSTERIES),
        new JunkEntry(RESEARCH_NOTES_RUNE_MYSTERIES, "Research notes",     "Quest complete (Rune Mysteries) — notes.", Quest.RUNE_MYSTERIES),
        // ---- Shield of Arrav ---------------------------------------------------
        // Quest.SHIELD_OF_ARRAV — confirmed
        new JunkEntry(BROKEN_SHIELD_LEFT_HALF,     "Broken shield (left half)",     "Quest complete — shield fragment.", Quest.SHIELD_OF_ARRAV),
        new JunkEntry(BROKEN_SHIELD_RIGHT_HALF,    "Broken shield (right half)",    "Quest complete — shield fragment.", Quest.SHIELD_OF_ARRAV),
        new JunkEntry(CERTIFICATE_SHIELD_OF_ARRAV, "Certificate (Shield of Arrav)", "Quest complete — certificate.",     Quest.SHIELD_OF_ARRAV),
        // ---- Vampyre Slayer ----------------------------------------------------
        // Quest.VAMPYRE_SLAYER — confirmed
        new JunkEntry(STAKE, "Stake", "Quest complete — vampire-killing stake.", Quest.VAMPYRE_SLAYER),
        // ---- X Marks the Spot --------------------------------------------------
        // Quest.X_MARKS_THE_SPOT — confirmed (ID: 162)
        new JunkEntry(TREASURE_SCROLL_1_X_MARKS, "Treasure scroll", "Quest complete — step 1 clue scroll given by Veos.", Quest.X_MARKS_THE_SPOT),
        new JunkEntry(TREASURE_SCROLL_2_X_MARKS, "Treasure scroll", "Quest complete — step 2 clue scroll.",             Quest.X_MARKS_THE_SPOT),
        new JunkEntry(TREASURE_SCROLL_3_X_MARKS, "Treasure scroll", "Quest complete — step 3 clue scroll.",             Quest.X_MARKS_THE_SPOT),
        new JunkEntry(MYSTERIOUS_ORB_X_MARKS,    "Mysterious orb (X Marks the Spot)", "Quest complete — orb dug up during quest.", Quest.X_MARKS_THE_SPOT),
        // ---- F2P quests with no bankable junk items ----------------------------
        // The following F2P quests were audited and confirmed to have no quest-specific
        // bankable items. All items used are general-use items obtainable and useful
        // outside the quest context. Listed here so future maintainers know each
        // quest was considered deliberately — a missing entry is not an oversight.
        //   (Below Ice Mountain reclassified July 9, 2026 — Steak sandwich 25631 now flagged as junk; see buildBatch4.)
        //   Cook's Assistant    (COOKS_ASSISTANT, ID 17)       — egg, bucket of milk, pot of flour are general
        //   Doric's Quest       (DORICS_QUEST, ID 30)          — clay, copper ore, iron ore are general
        //   Learning the Ropes  (LEARNING_THE_ROPES, ID 9643) — tutorial; no unique items exist
        //   Sheep Shearer       (SHEEP_SHEARER, ID 131)        — balls of wool are general
        // ---- Clock Tower -------------------------------------------------------
        // Quest.CLOCK_TOWER — confirmed
        new JunkEntry(WHITE_COG, "White cog", "Quest complete — cog.", Quest.CLOCK_TOWER),
        new JunkEntry(BLACK_COG, "Black cog", "Quest complete — cog.", Quest.CLOCK_TOWER),
        new JunkEntry(BLUE_COG,  "Blue cog",  "Quest complete — cog.", Quest.CLOCK_TOWER),
        new JunkEntry(RED_COG, "Red cog", "Quest complete — cog.", Quest.CLOCK_TOWER),
        // ---- Ghosts Ahoy -------------------------------------------------------
        // Quest.GHOSTS_AHOY — confirmed
        // NOTE: Nettle Tea is also needed for miniquest "Skippy and the Mogres" —
        // those entries use .withRequiredQuest2() once the enum name is confirmed.
        new JunkEntry(NETTLES,                "Nettles",                    "Quest complete — ingredient.",              Quest.GHOSTS_AHOY),
        new JunkEntry(NETTLE_WATER,           "Nettle-water",               "Quest complete — ingredient.",              Quest.GHOSTS_AHOY),
        // Nettle Tea: also needed for "Skippy and the Mogres" — only junk when BOTH are done.
        // Quest.SKIPPY_AND_THE_MOGRES — confirmed (ID: 135)
        new JunkEntry(NETTLE_TEA, "Nettle tea",
            "Used in Ghosts Ahoy and Skippy and the Mogres — only junk when both are complete.",
            Quest.GHOSTS_AHOY)
            .withRequiredQuest2(Quest.SKIPPY_AND_THE_MOGRES),
        new JunkEntry(NETTLE_TEA_MILKY,       "Nettle tea (milky)",         "Quest complete — tea.",                     Quest.GHOSTS_AHOY),
        new JunkEntry(CUP_OF_TEA_NETTLE,      "Cup of tea (nettle)",        "Quest complete — tea.",                     Quest.GHOSTS_AHOY),
        new JunkEntry(CUP_OF_TEA_MILKY_NETTLE,"Cup of tea (milky nettle)",  "Quest complete — tea.",                     Quest.GHOSTS_AHOY),
        new JunkEntry(PORCELAIN_CUP,          "Porcelain cup",              "Quest complete — cup.",                     Quest.GHOSTS_AHOY),
        new JunkEntry(CUP_OF_TEA_GHOSTS_AHOY, "Cup of tea (Ghosts Ahoy)",  "Quest complete — tea.",                     Quest.GHOSTS_AHOY),
        new JunkEntry(MYSTICAL_ROBES,         "Mystical robes",             "Quest complete — robe (no combat/skilling use).", Quest.GHOSTS_AHOY),
        new JunkEntry(BOOK_OF_HARICANTO,      "Book of haricanto",          "Quest complete — book.",                    Quest.GHOSTS_AHOY),
        new JunkEntry(TRANSLATION_MANUAL,     "Translation manual",         "Quest complete — manual.",                  Quest.GHOSTS_AHOY),
        new JunkEntry(MODEL_SHIP,               "Model ship (without flag)", "Quest complete — model.",                Quest.GHOSTS_AHOY),
        new JunkEntry(MODEL_SHIP_WITH_FLAG,    "Model ship (with flag)",    "Quest complete — model.",                Quest.GHOSTS_AHOY),
        new JunkEntry(BONE_KEY_GHOSTS_AHOY,    "Bone key (Ghosts Ahoy)",   "Quest complete — key.",                  Quest.GHOSTS_AHOY),
        new JunkEntry(CHEST_KEY_GHOSTS_AHOY,   "Chest key (Ghosts Ahoy)",  "Quest complete — key.",                  Quest.GHOSTS_AHOY),
        new JunkEntry(MAP_SCRAP,               "Map scrap (1)",             "Quest complete — map fragment.",         Quest.GHOSTS_AHOY),
        new JunkEntry(MAP_SCRAP_2,             "Map scrap (2)",             "Quest complete — map fragment.",         Quest.GHOSTS_AHOY),
        new JunkEntry(MAP_SCRAP_3,             "Map scrap (3)",             "Quest complete — map fragment.",         Quest.GHOSTS_AHOY),
        new JunkEntry(TREASURE_MAP_GHOSTS_AHOY,"Treasure map",             "Quest complete — map.",                  Quest.GHOSTS_AHOY),
        new JunkEntry(PETITION_FORM,          "Petition form",              "Quest complete — form.",                    Quest.GHOSTS_AHOY),
        new JunkEntry(BEDSHEET,               "Bedsheet",                   "Quest complete — disguise.",                Quest.GHOSTS_AHOY),
        new JunkEntry(ECTOPLASM_BEDSHEET,     "Ectoplasm-covered bedsheet", "Quest complete — disguise.",                Quest.GHOSTS_AHOY)
            .withWikiUrl("https://oldschool.runescape.wiki/w/Bedsheet#Ectoplasm"),
        // ---- Beneath Cursed Sands ----------------------------------------------
        // Quest.BENEATH_CURSED_SANDS — confirmed (ID: 168)
        new JunkEntry(MESSAGE_BCS, "Message (Beneath Cursed Sands)",
            "Quest complete (Beneath Cursed Sands) — message used in quest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(SCARAB_MOULD, "Scarab mould",
            "Quest complete (Beneath Cursed Sands) — mould for casting scarab emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(SCARAB_EMBLEM_IRON, "Scarab emblem (iron)",
            "Quest complete (Beneath Cursed Sands) — iron scarab emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(STONE_TABLET_BCS, "Stone tablet (Beneath Cursed Sands)",
            "Quest complete (Beneath Cursed Sands) — stone tablet, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(CHEST_BCS, "Chest (Beneath Cursed Sands)",
            "Quest complete (Beneath Cursed Sands) — puzzle chest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(SCARAB_EMBLEM_GOLD, "Scarab emblem (gold)",
            "Quest complete (Beneath Cursed Sands) — gold scarab emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(HUMAN_EMBLEM, "Human emblem",
            "Quest complete (Beneath Cursed Sands) — human-faced emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(BABOON_EMBLEM, "Baboon emblem",
            "Quest complete (Beneath Cursed Sands) — baboon-faced emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(CROCODILE_EMBLEM, "Crocodile emblem",
            "Quest complete (Beneath Cursed Sands) — crocodile-faced emblem, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(RUSTY_KEY_BCS, "Rusty key (Beneath Cursed Sands)",
            "Quest complete (Beneath Cursed Sands) — key used during quest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(LILY_OF_THE_ELID, "Lily of the elid",
            "Quest complete (Beneath Cursed Sands) — flower item used in quest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(CURE_CRATE, "Cure crate",
            "Quest complete (Beneath Cursed Sands) — crate of cure delivered, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(ODD_SPECTACLES, "Odd spectacles",
            "Quest complete (Beneath Cursed Sands) — spectacles used during quest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        new JunkEntry(BOTTLE_OF_TONIC, "Bottle of 'tonic'",
            "Quest complete (Beneath Cursed Sands) — tonic item used during quest, no use after quest.",
            Quest.BENEATH_CURSED_SANDS),
        // ---- Biohazard ---------------------------------------------------------
        // Quest.BIOHAZARD — confirmed (these items are from Biohazard, not Plague City)
        new JunkEntry(ETHENEA,           "Ethenea",           "Quest complete (Biohazard) — plague ingredient.", Quest.BIOHAZARD),
        new JunkEntry(LIQUID_HONEY,      "Liquid honey",      "Quest complete (Biohazard) — plague ingredient.", Quest.BIOHAZARD),
        new JunkEntry(SULPHURIC_BROLINE, "Sulphuric broline", "Quest complete (Biohazard) — plague ingredient.", Quest.BIOHAZARD),
        new JunkEntry(PLAGUE_SAMPLE,     "Plague sample",     "Quest complete (Biohazard) — sample.",            Quest.BIOHAZARD),
        new JunkEntry(TOUCH_PAPER,       "Touch paper",       "Quest complete (Biohazard) — component.",         Quest.BIOHAZARD),
        new JunkEntry(DISTILLATOR,       "Distillator",       "Quest complete (Biohazard) — device.",            Quest.BIOHAZARD),
        new JunkEntry(LATHAS_AMULET,     "Lathas' amulet",    "Quest complete (Biohazard) — amulet (no use after quest).", Quest.BIOHAZARD),
        new JunkEntry(BIRD_FEED,         "Bird feed",         "Quest complete (Biohazard) — feed.",              Quest.BIOHAZARD),
        new JunkEntry(KEY_BIOHAZARD,     "Key (Biohazard)",   "Quest complete (Biohazard) — key.",               Quest.BIOHAZARD),
        // Pigeon Cage: used in Biohazard and One Small Favour. OSF requires Biohazard,
        // so gating on ONE_SMALL_FAVOUR alone covers both. Quest.ONE_SMALL_FAVOUR — confirmed (ID: 107)
        new JunkEntry(PIGEON_CAGE_FULL,  "Pigeon cage",        "Used in Biohazard and One Small Favour — junk only after both are complete.",  Quest.ONE_SMALL_FAVOUR)
            .withWikiUrl("https://oldschool.runescape.wiki/w/Pigeon_cage#Full")
            .withRequiredQuest2(Quest.BIOHAZARD),
        new JunkEntry(PIGEON_CAGE_EMPTY, "Pigeon cage (empty)", "Used in Biohazard and One Small Favour — junk only after both are complete.", Quest.ONE_SMALL_FAVOUR),
        // ---- A Tail of Two Cats ------------------------------------------------
        // Quest.A_TAIL_OF_TWO_CATS — confirmed
        // Medical gown is obtained in Biohazard but reused in A Tail of Two Cats.
        // Gating on the later quest ensures it isn't flagged while still needed.
        new JunkEntry(MEDICAL_GOWN, "Medical gown",
            "Used in Biohazard and A Tail of Two Cats — only junk after both quests are complete.",
            Quest.A_TAIL_OF_TWO_CATS)
            .withRequiredQuest2(Quest.BIOHAZARD),
        // ---- The Great Brain Robbery -------------------------------------------
        // PRAYER_BOOK_GBR removed — has ongoing use (prayer restore at altars)
        // Quest.THE_GREAT_BRAIN_ROBBERY — confirmed
        // BLESSED_LAMP removed — XP lamp is useable after the quest; not junk.
        new JunkEntry(CRANIAL_CLAMP,   "Cranial clamp",    "Quest complete — surgical tool.",   Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(BRAIN_TONGS,     "Brain tongs",      "Quest complete — surgical tool.",   Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(BELL_JAR,        "Bell jar",         "Quest complete — jar.",             Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(WOLF_WHISTLE,    "Wolf whistle",     "Quest complete — whistle.",         Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(SHIPPING_ORDER,  "Shipping order",   "Quest complete — order form.",      Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(FUSE_GBR,        "Fuse",             "Quest complete — fuse.",            Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(KEG_GBR,         "Keg",              "Quest complete — keg.",             Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(CRATE_PART,      "Crate part",       "Quest complete — crate component.", Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(SKULL_STAPLE,    "Skull staple",     "Quest complete — staple.",          Quest.THE_GREAT_BRAIN_ROBBERY),
        // ---- Animal Magnetism --------------------------------------------------
        // Quest.ANIMAL_MAGNETISM — confirmed
        new JunkEntry(SELECTED_IRON,                   "Selected iron",                       "Quest complete — magnetism component.", Quest.ANIMAL_MAGNETISM),
        new JunkEntry(BAR_MAGNET,                      "Bar magnet",                          "Quest complete — magnet.",             Quest.ANIMAL_MAGNETISM),
        new JunkEntry(UNDEAD_TWIGS,                    "Undead twigs",                        "Quest complete — twigs.",              Quest.ANIMAL_MAGNETISM),
        new JunkEntry(RESEARCH_NOTES_ANIMAL_MAGNETISM, "Research notes (Animal Magnetism)",   "Quest complete — notes.",             Quest.ANIMAL_MAGNETISM),
        new JunkEntry(TRANSLATED_NOTES,                "Translated notes",                    "Quest complete — notes.",             Quest.ANIMAL_MAGNETISM),
        new JunkEntry(A_PATTERN,                       "A pattern",                           "Quest complete — pattern.",           Quest.ANIMAL_MAGNETISM),
        new JunkEntry(A_CONTAINER,                     "A container",                         "Quest complete — container.",         Quest.ANIMAL_MAGNETISM),
        new JunkEntry(CRONE_MADE_AMULET,               "Crone-made amulet",                   "Quest complete — amulet.",            Quest.ANIMAL_MAGNETISM),
        new JunkEntry(POLISHED_BUTTONS,                "Polished buttons",                    "Quest complete — used in Animal Magnetism, not The Dig Site.", Quest.ANIMAL_MAGNETISM),
        // ---- Desert Treasure I -------------------------------------------------
        // Quest.DESERT_TREASURE_I — confirmed
        new JunkEntry(ETCHINGS,                   "Etchings",         "Quest complete — etchings.",                  Quest.DESERT_TREASURE_I),
        new JunkEntry(TRANSLATION_DESERT_TREASURE,"Translation",      "Quest complete — translation document.",      Quest.DESERT_TREASURE_I),
        new JunkEntry(GARLIC_POWDER,              "Garlic powder",    "Quest complete — powder.",                    Quest.DESERT_TREASURE_I),
        new JunkEntry(BLOOD_DIAMOND,              "Blood diamond",    "Quest complete — diamond (no GE value).",     Quest.DESERT_TREASURE_I),
        new JunkEntry(ICE_DIAMOND,                "Ice diamond",      "Quest complete — diamond (no GE value).",     Quest.DESERT_TREASURE_I),
        new JunkEntry(SMOKE_DIAMOND,              "Smoke diamond",    "Quest complete — diamond (no GE value).",     Quest.DESERT_TREASURE_I),
        new JunkEntry(SHADOW_DIAMOND,             "Shadow diamond",   "Quest complete — diamond (no GE value).",     Quest.DESERT_TREASURE_I),
        new JunkEntry(GILDED_CROSS,               "Gilded cross",     "Quest complete — cross.",                     Quest.DESERT_TREASURE_I),
        // NOTE: SLENDER_BLADE (6817) and BOW_SWORD (6818) are Devious Minds items — moved to DEVIOUS_MINDS block below.
        new JunkEntry(SILVER_POT_EMPTY,    "Silver pot (empty)",    "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(SILVER_POT_BLOOD,    "Silver pot (blood)",    "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(SILVER_POT_GARLIC,   "Silver pot (garlic)",   "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(SILVER_POT_SPICES,   "Silver pot (spices)",   "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(SILVER_POT_COMPLETE, "Silver pot (complete)", "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(BLESSED_POT_EMPTY,   "Blessed pot (empty)",   "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(BLESSED_POT_BLOOD,   "Blessed pot (blood)",   "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(BLESSED_POT_GARLIC,  "Blessed pot (garlic)",  "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(BLESSED_POT_SPICES,  "Blessed pot (spices)",  "Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(BLESSED_POT_COMPLETE,"Blessed pot (complete)","Quest complete (Desert Treasure I) — pot.", Quest.DESERT_TREASURE_I),
        new JunkEntry(WARM_KEY,            "Warm key",              "Quest complete (Desert Treasure I) — warm key, no use after quest.",              Quest.DESERT_TREASURE_I),
        // --- Desert Treasure II - The Fallen Empire ---
        new JunkEntry(BLACKSTONE_FRAG_NORMAL, "Blackstone fragment",
            "Quest complete (Desert Treasure II) — normal fragment; quest material.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(BLACKSTONE_FRAG_GLOWING, "Blackstone fragment",
            "Quest complete (Desert Treasure II) — glowing fragment; quest material.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STRANGE_ICON_DT2, "Strange icon",
            "Quest complete (Desert Treasure II) — icon assembled during quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ICON_SEG_PALM_THUMB, "Icon segment",
            "Quest complete (Desert Treasure II) — palm and thumb segment.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ICON_SEG_FINGERS, "Icon segment",
            "Quest complete (Desert Treasure II) — fingers segment.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(VERY_LONG_ROPE, "Very long rope",
            "Quest complete (Desert Treasure II) — used to descend into the Stranglewood.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(BASIC_SHADOW_TORCH, "Basic shadow torch",
            "Quest complete (Desert Treasure II) — crafted torch for Verzik boss room.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SUPERIOR_SHADOW_TORCH, "Superior shadow torch",
            "Quest complete (Desert Treasure II) — upgraded torch.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PERFECTED_SHADOW_TORCH, "Perfected shadow torch",
            "Quest complete (Desert Treasure II) — fully upgraded torch.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_BLOCKER, "Shadow blocker",
            "Quest complete (Desert Treasure II) — blocking device for shadow puzzle.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(REVITALISING_IDOL, "Revitalising idol",
            "Quest complete (Desert Treasure II) — crafted idol for the puzzle.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ANIMA_PORTAL, "Anima portal",
            "Quest complete (Desert Treasure II) — portal device used in sequence.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_KEY_PURPLE, "Shadow key",
            "Quest complete (Desert Treasure II) — purple variant key.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_KEY_BLUE, "Shadow key",
            "Quest complete (Desert Treasure II) — blue variant key.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_KEY_WHITE, "Shadow key",
            "Quest complete (Desert Treasure II) — white variant key.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_KEY_RED, "Shadow key",
            "Quest complete (Desert Treasure II) — red variant key.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_KEY_GREEN, "Shadow key",
            "Quest complete (Desert Treasure II) — green variant key.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ANIMA_PORTAL_SCHEMATIC, "Anima portal schematic",
            "Quest complete (Desert Treasure II) — blueprint for anima portal.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(REVITALISING_IDOL_SCHEMATIC, "Revitalising idol schematic",
            "Quest complete (Desert Treasure II) — blueprint for revitalising idol.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SHADOW_BLOCKER_SCHEMATIC, "Shadow blocker schematic",
            "Quest complete (Desert Treasure II) — blueprint for shadow blocker.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(BASIC_SHADOW_TORCH_SCHEMATIC, "Basic shadow torch schematic",
            "Quest complete (Desert Treasure II) — blueprint for basic torch.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SUPERIOR_SHADOW_TORCH_SCHEMATIC, "Superior shadow torch schematic",
            "Quest complete (Desert Treasure II) — blueprint for superior torch.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PERFECTED_SHADOW_TORCH_SCH_1, "Perfected shadow torch schematic",
            "Quest complete (Desert Treasure II) — blueprint part 1.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PERFECTED_SHADOW_TORCH_SCH_2, "Perfected shadow torch schematic",
            "Quest complete (Desert Treasure II) — blueprint part 2.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(POTION_NOTE_DT2, "Potion note",
            "Quest complete (Desert Treasure II) — serum recipe note.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STRANGE_POTION_DT2, "Strange potion",
            "Quest complete (Desert Treasure II) — used in Stranglewood puzzle.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(KORBAL_HERB, "Korbal herb",
            "Quest complete (Desert Treasure II) — serum ingredient.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ARGIAN_BERRIES, "Argian berries",
            "Quest complete (Desert Treasure II) — serum ingredient.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(UNFINISHED_SERUM_1, "Unfinished serum",
            "Quest complete (Desert Treasure II) — serum step 1.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(UNFINISHED_SERUM_2, "Unfinished serum",
            "Quest complete (Desert Treasure II) — serum step 2.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STRANGLER_SERUM, "Strangler serum",
            "Quest complete (Desert Treasure II) — completed serum; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TEMPLE_KEY_DT2, "Temple key",
            "Quest complete (Desert Treasure II) — opens temple door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(BARRICADE_DT2, "Barricade",
            "Quest complete (Desert Treasure II) — used in the temple area.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SATCHEL_DT2, "Satchel",
            "Quest complete (Desert Treasure II) — carries items in Stranglewood.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DETONATOR_DT2, "Detonator",
            "Quest complete (Desert Treasure II) — detonates explosives in the vault.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY1, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 1.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY2, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 2.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY3, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 3.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY4, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 4.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY5, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 5.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY6, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 6.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(TATTY_PAGE_DAY7, "Tatty page",
            "Quest complete (Desert Treasure II) — journal page day 7.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(MUCKY_NOTE_DT2, "Mucky note",
            "Quest complete (Desert Treasure II) — found in Stranglewood; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(UNCHARGED_CELL_DT2, "Uncharged cell",
            "Quest complete (Desert Treasure II) — used to power vault devices.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(CHARGED_CELL_DT2, "Charged cell",
            "Quest complete (Desert Treasure II) — charged version; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(VARDORVIS_MEDALLION, "Vardorvis' medallion",
            "Quest complete (Desert Treasure II) — medallion from Vardorvis' boss encounter.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PERSERIYA_MEDALLION, "Perseriya's medallion",
            "Quest complete (Desert Treasure II) — medallion from Perseriya's encounter.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SUCELLUS_MEDALLION, "Sucellus' medallion",
            "Quest complete (Desert Treasure II) — medallion from Sucellus' encounter.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(WHISPERER_MEDALLION, "Whisperer's medallion",
            "Quest complete (Desert Treasure II) — medallion from Whisperer encounter.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(HAIR_CLIP_DT2, "Hair clip",
            "Quest complete (Desert Treasure II) — used to pick a lock; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PRISONER_LETTER, "Prisoner's letter",
            "Quest complete (Desert Treasure II) — found in a cell; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(KNIFE_DT2, "Knife",
            "Quest complete (Desert Treasure II) — quest-specific knife; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(CHISEL_DT2, "Chisel",
            "Quest complete (Desert Treasure II) — quest-specific chisel; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(LOCKPICK_DT2, "Lockpick",
            "Quest complete (Desert Treasure II) — quest-specific lockpick.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SAPPHIRE_KEY_DT2, "Sapphire key",
            "Quest complete (Desert Treasure II) — opens sapphire door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(EMERALD_KEY_DT2, "Emerald key",
            "Quest complete (Desert Treasure II) — opens emerald door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(RUBY_KEY_DT2, "Ruby key",
            "Quest complete (Desert Treasure II) — opens ruby door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DIAMOND_KEY_DT2, "Diamond key",
            "Quest complete (Desert Treasure II) — opens diamond door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DRAGONSTONE_KEY_DT2, "Dragonstone key",
            "Quest complete (Desert Treasure II) — opens dragonstone door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ONYX_KEY_DT2, "Onyx key",
            "Quest complete (Desert Treasure II) — opens onyx door.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(RATIONS_DT2, "Rations",
            "Quest complete (Desert Treasure II) — food for surviving in Stranglewood.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(REQUISITION_NOTE, "Requisition note",
            "Quest complete (Desert Treasure II) — supplies request; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(GRID_NOTE_DT2, "Grid note",
            "Quest complete (Desert Treasure II) — cipher grid; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(CODE_CONVERTER, "Code converter",
            "Quest complete (Desert Treasure II) — decodes messages; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(MAGIC_LANTERN_DT2, "Magic lantern",
            "Quest complete (Desert Treasure II) — lights dark areas; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STRANGE_SLIDER, "Strange slider",
            "Quest complete (Desert Treasure II) — used in puzzle; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(LIBRARY_NOTE, "Library note",
            "Quest complete (Desert Treasure II) — found in library; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(WARNING_LETTER, "Warning letter",
            "Quest complete (Desert Treasure II) — found in vault; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ODD_KEY, "Odd key",
            "Quest complete (Desert Treasure II) — unusual key; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ORDERS_NOTE, "Orders note",
            "Quest complete (Desert Treasure II) — military orders; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(REFUGEES_NOTE, "Refugees note",
            "Quest complete (Desert Treasure II) — note from refugees; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(REQUEST_NOTE, "Request note",
            "Quest complete (Desert Treasure II) — supply request; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PRAYER_NOTE_DT2, "Prayer note",
            "Quest complete (Desert Treasure II) — religious note; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(THANK_YOU_NOTE, "Thank you note",
            "Quest complete (Desert Treasure II) — gratitude note; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(PROTEST_NOTE, "Protest note",
            "Quest complete (Desert Treasure II) — protest letter; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(EVACUATION_NOTE, "Evacuation note",
            "Quest complete (Desert Treasure II) — evacuation order; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(OLD_TABLET_DT2, "Old tablet",
            "Quest complete (Desert Treasure II) — ancient tablet found in vault.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DAMP_TABLET_INCIDENT, "Damp tablet",
            "Quest complete (Desert Treasure II) — incident report tablet.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DAMP_TABLET_REJOICE, "Damp tablet",
            "Quest complete (Desert Treasure II) — rejoice message tablet.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SLIMY_KEY_DT2, "Slimy key",
            "Quest complete (Desert Treasure II) — slime-covered key; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(GUNPOWDER_DT2, "Gunpowder",
            "Quest complete (Desert Treasure II) — used with detonator; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SCARRED_SCRAPS, "Scarred scraps",
            "Quest complete (Desert Treasure II) — damaged documents; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(WITHERED_NOTE, "Withered note",
            "Quest complete (Desert Treasure II) — decayed note; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ABYSSAL_OBSERVATIONS, "Abyssal observations",
            "Quest complete (Desert Treasure II) — research notes on abyssal creatures.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(CRIMSON_FIBRE, "Crimson fibre",
            "Quest complete (Desert Treasure II) — crafting material for torches.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(RADIANT_FIBRE, "Radiant fibre",
            "Quest complete (Desert Treasure II) — crafting material for torches.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ILLUMINATING_LURE, "Illuminating lure",
            "Quest complete (Desert Treasure II) — fishing lure for blood eels.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SLIMY_TABLET_PART1, "Slimy tablet",
            "Quest complete (Desert Treasure II) — tablet part 1.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SLIMY_TABLET_PART2, "Slimy tablet",
            "Quest complete (Desert Treasure II) — tablet part 2.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SLIMY_TABLET_PART3, "Slimy tablet",
            "Quest complete (Desert Treasure II) — tablet part 3.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(GOOEY_NOTE_PART1, "Gooey note",
            "Quest complete (Desert Treasure II) — gooey note part 1.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(GOOEY_NOTE_PART2, "Gooey note",
            "Quest complete (Desert Treasure II) — gooey note part 2.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(GOOEY_NOTE_PART3, "Gooey note",
            "Quest complete (Desert Treasure II) — gooey note part 3.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STINK_BOMB, "Stink bomb",
            "Quest complete (Desert Treasure II) — used to distract guards; no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        // ANCIENT_DIARY (21631) — also appears in Desert Treasure II but flagged via DS2 entry above (ID 21631, no duplicate allowed)
        // ---- Contact! ----------------------------------------------------------
        // Quest.CONTACT — confirmed
        new JunkEntry(PARCHMENT_CONTACT, "Parchment (Contact!)", "Quest complete — parchment.", Quest.CONTACT),
        // ---- The Feud ----------------------------------------------------------
        // Quest.THE_FEUD — confirmed
        // NOTE: IOU (293) removed — the IOU used in OSRS quests is Death Plateau's IOU_DEATH_PLATEAU (3103),
        // already in the Death Plateau block. IOU (293) has not been confirmed as a Feud item.
        // ---- Death Plateau -----------------------------------------------------
        // Quest.DEATH_PLATEAU — confirmed
        new JunkEntry(COMBINATION,               "Combination",                 "Quest complete (Death Plateau) — lock combination.", Quest.DEATH_PLATEAU),
        new JunkEntry(SECRET_WAY_MAP,            "Secret way map",              "Quest complete (Death Plateau) — map.",              Quest.DEATH_PLATEAU),
        new JunkEntry(CERTIFICATE_DEATH_PLATEAU, "Certificate (Death Plateau)", "Quest complete — certificate.",                      Quest.DEATH_PLATEAU),
        new JunkEntry(STONE_BALL_RED,    "Stone ball (red)",    "Quest complete (Death Plateau) — ball.", Quest.DEATH_PLATEAU),
        new JunkEntry(STONE_BALL,        "Stone ball (blue)",   "Quest complete (Death Plateau) — ball.", Quest.DEATH_PLATEAU),
        new JunkEntry(STONE_BALL_YELLOW, "Stone ball (yellow)", "Quest complete (Death Plateau) — ball.", Quest.DEATH_PLATEAU),
        new JunkEntry(STONE_BALL_PURPLE, "Stone ball (purple)", "Quest complete (Death Plateau) — ball.", Quest.DEATH_PLATEAU),
        new JunkEntry(STONE_BALL_GREEN,  "Stone ball (green)",  "Quest complete (Death Plateau) — ball.", Quest.DEATH_PLATEAU),
        new JunkEntry(IOU_DEATH_PLATEAU, "IOU (Death Plateau)", "Quest complete (Death Plateau) — debt note.", Quest.DEATH_PLATEAU),
        // ---- Devious Minds -----------------------------------------------------
        // Quest.DEVIOUS_MINDS — confirmed
        new JunkEntry(SLENDER_BLADE,             "Slender blade",        "Quest complete (Devious Minds) — component blade.", Quest.DEVIOUS_MINDS),
        new JunkEntry(BOW_SWORD,                 "Bow-sword",            "Quest complete (Devious Minds) — bladed bow.",      Quest.DEVIOUS_MINDS),
        new JunkEntry(LARGE_POUCH_DEVIOUS_MINDS, "Large pouch (Devious Minds)",
            "Quest complete — NOT a Runecrafting pouch; this is the quest-specific item.", Quest.DEVIOUS_MINDS),
        new JunkEntry(RELIC_DEVIOUS_MINDS, "Relic (Devious Minds)", "Quest complete — relic.", Quest.DEVIOUS_MINDS),
        new JunkEntry(ORB_DEVIOUS_MINDS,   "Orb (Devious Minds)",   "Quest complete — orb.",   Quest.DEVIOUS_MINDS),
        // ---- The Dig Site ------------------------------------------------------
        // Quest.THE_DIG_SITE — confirmed
        new JunkEntry(SPECIMEN_JAR,         "Specimen jar",     "Quest complete — specimen.",              Quest.THE_DIG_SITE),
        new JunkEntry(ANIMAL_SKULL,         "Animal skull",     "Quest complete — specimen.",              Quest.THE_DIG_SITE),
        new JunkEntry(SPECIAL_CUP,          "Special cup",      "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(TEDDY,                "Teddy",            "Quest complete — toy.",                   Quest.THE_DIG_SITE),
        new JunkEntry(CRACKED_SAMPLE,       "Cracked sample",   "Quest complete — sample.",                Quest.THE_DIG_SITE),
        new JunkEntry(PANNING_TRAY_EMPTY,   "Panning tray",     "Quest complete — tray (empty).",          Quest.THE_DIG_SITE),
        new JunkEntry(PANNING_TRAY_MUD,     "Panning tray (mud)","Quest complete — tray (with mud).",       Quest.THE_DIG_SITE),
        new JunkEntry(NUGGETS,              "Nuggets",          "Quest complete — nuggets.",               Quest.THE_DIG_SITE),
        new JunkEntry(ANCIENT_TALISMAN,     "Ancient talisman", "Quest complete — talisman.",              Quest.THE_DIG_SITE),
        new JunkEntry(UNSTAMPED_LETTER,     "Unstamped letter", "Quest complete — letter.",                Quest.THE_DIG_SITE),
        new JunkEntry(SEALED_LETTER,        "Sealed letter",    "Quest complete — letter.",                Quest.THE_DIG_SITE),
        new JunkEntry(BELT_BUCKLE,          "Belt buckle",      "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        // RUSTY_SWORD_DIGSITE removed — still needed for Ardougne Diary (Easy); diary gates not supported.
        new JunkEntry(BROKEN_ARROW,         "Broken arrow",     "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(BUTTONS,              "Buttons",          "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(BROKEN_STAFF,         "Broken staff",     "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(LEVEL_1_CERTIFICATE,  "Level 1 certificate", "Quest complete — certificate.",        Quest.THE_DIG_SITE),
        new JunkEntry(LEVEL_2_CERTIFICATE,  "Level 2 certificate", "Quest complete — certificate.",        Quest.THE_DIG_SITE),
        new JunkEntry(LEVEL_3_CERTIFICATE,  "Level 3 certificate", "Quest complete — certificate.",        Quest.THE_DIG_SITE),
        new JunkEntry(CERAMIC_REMAINS,      "Ceramic remains",  "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(OLD_TOOTH,            "Old tooth",        "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(INVITATION_LETTER,    "Invitation letter","Quest complete — letter.",                Quest.THE_DIG_SITE),
        new JunkEntry(DAMAGED_ARMOUR,       "Damaged armour",   "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(BROKEN_ARMOUR,        "Broken armour",    "Quest complete — artefact.",              Quest.THE_DIG_SITE),
        new JunkEntry(STONE_TABLET_DIGSITE, "Stone tablet (The Dig Site)", "Quest complete — tablet.",    Quest.THE_DIG_SITE),
        new JunkEntry(CHEMICAL_POWDER,       "Chemical powder",  "Quest complete — explosive ingredient (pre-mixing form).",  Quest.THE_DIG_SITE),
        new JunkEntry(AMMONIUM_NITRATE,     "Ammonium nitrate", "Quest complete — explosive ingredient (processed form).",   Quest.THE_DIG_SITE),
        new JunkEntry(UNIDENTIFIED_LIQUID,  "Unidentified liquid", "Quest complete — explosive ingredient (pre-mixing form).", Quest.THE_DIG_SITE),
        new JunkEntry(NITROGLYCERIN,        "Nitroglycerin",    "Quest complete — explosive ingredient (processed form).",   Quest.THE_DIG_SITE),
        new JunkEntry(MIXED_CHEMICALS_1,    "Mixed chemicals",  "Quest complete — explosive (step 1).",    Quest.THE_DIG_SITE),
        new JunkEntry(MIXED_CHEMICALS_2,    "Mixed chemicals",  "Quest complete — explosive (step 2).",    Quest.THE_DIG_SITE),
        new JunkEntry(CHEMICAL_COMPOUND,    "Chemical compound","Quest complete — explosive.",             Quest.THE_DIG_SITE),
        new JunkEntry(ARCENIA_ROOT,         "Arcenia root",     "Quest complete — root.",                  Quest.THE_DIG_SITE),
        new JunkEntry(CHEST_KEY_DIGSITE,    "Chest key (Digsite)", "Quest complete — key.",               Quest.THE_DIG_SITE),
        new JunkEntry(BOOK_ON_CHEMICALS,    "Book on chemicals",   "Quest complete (The Dig Site) — read during quest, no further use.",               Quest.THE_DIG_SITE),
        // ---- Keris / Keris (p/p+/p++) (YELLOW — only junk if Contact! done AND any Keris partisan is banked) ---
        new JunkEntry(KERIS, "Keris", JunkTier.YELLOW,
            "Superseded by Keris partisan — only flagged after Contact! is complete and a Keris partisan is in the bank.",
            Quest.CONTACT, new int[]{
                KERIS_PARTISAN,
                KERIS_PARTISAN_OF_CORRUPTION,
                KERIS_PARTISAN_OF_THE_SUN,
                KERIS_PARTISAN_OF_BREACHING,
                KERIS_PARTISAN_OF_AMASCUT}),
        new JunkEntry(KERIS_P, "Keris (p)", JunkTier.YELLOW,
            "Superseded by Keris partisan — only flagged after Contact! is complete and a Keris partisan is in the bank.",
            Quest.CONTACT, new int[]{
                KERIS_PARTISAN,
                KERIS_PARTISAN_OF_CORRUPTION,
                KERIS_PARTISAN_OF_THE_SUN,
                KERIS_PARTISAN_OF_BREACHING,
                KERIS_PARTISAN_OF_AMASCUT}),
        new JunkEntry(KERIS_P_PLUS, "Keris (p+)", JunkTier.YELLOW,
            "Superseded by Keris partisan — only flagged after Contact! is complete and a Keris partisan is in the bank.",
            Quest.CONTACT, new int[]{
                KERIS_PARTISAN,
                KERIS_PARTISAN_OF_CORRUPTION,
                KERIS_PARTISAN_OF_THE_SUN,
                KERIS_PARTISAN_OF_BREACHING,
                KERIS_PARTISAN_OF_AMASCUT}),
        new JunkEntry(KERIS_P_PLUS_PLUS, "Keris (p++)", JunkTier.YELLOW,
            "Superseded by Keris partisan — only flagged after Contact! is complete and a Keris partisan is in the bank.",
            Quest.CONTACT, new int[]{
                KERIS_PARTISAN,
                KERIS_PARTISAN_OF_CORRUPTION,
                KERIS_PARTISAN_OF_THE_SUN,
                KERIS_PARTISAN_OF_BREACHING,
                KERIS_PARTISAN_OF_AMASCUT}),
        // ---- The Restless Ghost ------------------------------------------------
        new JunkEntry(GHOST_SKULL, "Ghost's skull",
            "Quest complete — skull returned to the ghost, no further use.",
            Quest.THE_RESTLESS_GHOST),
        // ---- Prince Ali Rescue -------------------------------------------------
        new JunkEntry(BRONZE_KEY_PRINCE_ALI, "Bronze key (Prince Ali Rescue)",
            "Quest complete — key no longer needed.",
            Quest.PRINCE_ALI_RESCUE),
        new JunkEntry(SKIN_PASTE, "Paste",
            "Quest complete (Prince Ali Rescue) — disguise ingredient, no use after quest.",
            Quest.PRINCE_ALI_RESCUE),
        new JunkEntry(WIG_DYED, "Wig",
            "Quest complete (Prince Ali Rescue) — dyed wig used in disguise, no further use.",
            Quest.PRINCE_ALI_RESCUE),
        new JunkEntry(WIG, "Wig",
            "Quest complete (Prince Ali Rescue) — grey wig used in disguise, no further use.",
            Quest.PRINCE_ALI_RESCUE),
        // ---- Waterfall Quest ---------------------------------------------------
        new JunkEntry(BOOK_BAXTORIAN, "Book (Baxtorian)",
            "Quest complete — book used inside Baxtorian Falls.",
            Quest.WATERFALL_QUEST),
        // Key: used in Waterfall Quest, Roving Elves, and Song of the Elves.
        // SotE requires Roving Elves which requires Waterfall Quest — gate on SotE alone.
        // Quest.SONG_OF_THE_ELVES — confirmed (ID: 137)
        new JunkEntry(KEY_BAXTORIAN, "Key (Waterfall Dungeon)",
            "Used in Waterfall Quest, Roving Elves, and Song of the Elves — junk only after all three are complete.",
            Quest.SONG_OF_THE_ELVES)
            .withWikiUrl("https://oldschool.runescape.wiki/w/Key_(Waterfall_Dungeon)"),
        // ---- Tree Gnome Village ------------------------------------------------
        new JunkEntry(ORB_OF_PROTECTION, "Orb of protection",
            "Quest complete — all 3 orbs retrieved, no further use.",
            Quest.TREE_GNOME_VILLAGE),
        // ---- Fight Arena -------------------------------------------------------
        new JunkEntry(KHAZARD_CELL_KEYS, "Khazard cell keys",
            "Quest complete — keys used to escape the arena cells, no further use.",
            Quest.FIGHT_ARENA),
        new JunkEntry(KHALI_BREW, "Khali brew",
            "Quest complete — brew used to poison the guard, no other use.",
            Quest.FIGHT_ARENA),
        // ---- Jungle Potion / Legends' Quest ------------------------------------
        // Ardrigal: used in Jungle Potion and again in Legends' Quest — junk only after latter.
        // Quest.LEGENDS_QUEST — confirmed
        new JunkEntry(GRIMY_ARDRIGAL, "Grimy ardrigal",
            "Used in Jungle Potion and Legends' Quest — junk only after Legends' Quest is complete.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(CLEAN_ARDRIGAL, "Ardrigal",
            "Used in Jungle Potion and Legends' Quest — junk only after Legends' Quest is complete.",
            Quest.LEGENDS_QUEST),
        // ---- Jungle Potion / Zogre Flesh Eaters -------------------------------
        // Rogue's Purse: used in Jungle Potion and Zogre Flesh Eaters.
        // Quest.JUNGLE_POTION, Quest.ZOGRE_FLESH_EATERS — confirmed
        new JunkEntry(GRIMY_ROGUES_PURSE, "Grimy rogue's purse",
            "Used in Jungle Potion and Zogre Flesh Eaters — junk only after both are complete.",
            Quest.JUNGLE_POTION)
            .withRequiredQuest2(Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(CLEAN_ROGUES_PURSE, "Rogue's purse",
            "Used in Jungle Potion and Zogre Flesh Eaters — junk only after both are complete.",
            Quest.JUNGLE_POTION)
            .withRequiredQuest2(Quest.ZOGRE_FLESH_EATERS),
        // ---- Jungle Potion / Fairytale I ---------------------------------------
        // Volencia Moss: used in Jungle Potion; may also be required by Fairytale I
        // (random herb selection) — definitely junk once Fairytale I is complete.
        // Quest.FAIRYTALE_I__GROWING_PAINS — confirmed (double underscore for dash)
        new JunkEntry(GRIMY_VOLENCIA_MOSS, "Grimy volencia moss",
            "Used in Jungle Potion; also a possible ingredient in Fairytale I - Growing Pains.<br>Junk once Fairytale I is complete.",
            Quest.FAIRYTALE_I__GROWING_PAINS),
        new JunkEntry(CLEAN_VOLENCIA_MOSS, "Volencia moss",
            "Used in Jungle Potion; also a possible ingredient in Fairytale I - Growing Pains.<br>Junk once Fairytale I is complete.",
            Quest.FAIRYTALE_I__GROWING_PAINS),
        // ---- Jungle Potion (remaining herbs — no further quest use) ------------
        new JunkEntry(GRIMY_SITO_FOIL, "Grimy sito foil", "Quest complete (Jungle Potion) — herb ingredient, no further use.", Quest.JUNGLE_POTION),
        new JunkEntry(CLEAN_SITO_FOIL, "Sito foil",       "Quest complete (Jungle Potion) — herb ingredient, no further use.", Quest.JUNGLE_POTION),
        // ---- Underground Pass --------------------------------------------------
        new JunkEntry(ORB_OF_LIGHT,   "Orb of light", "Quest complete — orb used to dispel the barrier.", Quest.UNDERGROUND_PASS),
        new JunkEntry(ORB_OF_LIGHT_2, "Orb of light", "Quest complete — orb used to dispel the barrier.", Quest.UNDERGROUND_PASS),
        new JunkEntry(ORB_OF_LIGHT_3, "Orb of light", "Quest complete — orb used to dispel the barrier.", Quest.UNDERGROUND_PASS),
        new JunkEntry(ORB_OF_LIGHT_4, "Orb of light", "Quest complete — orb used to dispel the barrier.", Quest.UNDERGROUND_PASS),
        // ---- Nature Spirit / The Grand Tree (Bark Sample) -----
        new JunkEntry(BARK_SAMPLE, "Bark sample",
            "Quest complete (The Grand Tree) — given to Hazelmere, no further use.",
            Quest.THE_GRAND_TREE),
        // ---- The Haunted Mine --------------------------------------------------
        new JunkEntry(GLOWING_FUNGUS, "Glowing fungus",
            "Quest complete — light source no longer needed in the mine.",
            Quest.HAUNTED_MINE),
        // Crystal mine key intentionally excluded — can be placed on a Steel Key Ring.
        // ---- Zogre Flesh Eaters ------------------------------------------------
        new JunkEntry(STRANGE_POTION, "Strange potion",
            "Quest complete — potion used during quest, no further use.",
            Quest.ZOGRE_FLESH_EATERS),
        // ---- Animal Magnetism (additional) -------------------------------------
        new JunkEntry(UNDEAD_CHICKEN, "Undead chicken",
            "Quest complete (Animal Magnetism) — quest item, no further use.",
            Quest.ANIMAL_MAGNETISM),
        // ---- Black Knights' Fortress -------------------------------------------
        // Quest.BLACK_KNIGHTS_FORTRESS — confirmed
        new JunkEntry(CABBAGE_BKF,  "Cabbage",
            "Quest complete (Black Knights' Fortress) — used to sabotage the fortress potion.",
            Quest.BLACK_KNIGHTS_FORTRESS),
        new JunkEntry(DOSSIER_BKF,  "Dossier (Black Knights' Fortress)",
            "Quest complete — dossier no longer needed.",
            Quest.BLACK_KNIGHTS_FORTRESS),
        // ---- Client of Kourend -------------------------------------------------
        // Quest.CLIENT_OF_KOUREND — confirmed
        new JunkEntry(MYSTERIOUS_ORB_COK, "Mysterious orb (Client of Kourend)",
            "Quest complete — orb no longer needed.",
            Quest.CLIENT_OF_KOUREND),
        // Broken glass: also needed for Sea Slug — only junk after BOTH are complete.
        // Quest.SEA_SLUG — confirmed
        new JunkEntry(BROKEN_GLASS_SLUG, "Broken glass",
            "Used in Sea Slug and Client of Kourend — junk only after both are complete.",
            Quest.SEA_SLUG)
            .withRequiredQuest2(Quest.CLIENT_OF_KOUREND),
        // ---- Creature of Fenkenstrain ------------------------------------------
        // Quest.CREATURE_OF_FENKENSTRAIN — confirmed
        new JunkEntry(STAR_AMULET,            "Star amulet",           "Quest complete — amulet.",      Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(CAVERN_KEY,             "Cavern key",            "Quest complete — key.",          Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(TOWER_KEY,              "Tower key",             "Quest complete — key.",          Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(SHED_KEY,               "Shed key",              "Quest complete — key.",          Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(MARBLE_AMULET,          "Marble amulet",         "Quest complete — amulet.",      Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(OBSIDIAN_AMULET,        "Obsidian amulet",       "Quest complete — amulet.",      Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(GARDEN_CANE,            "Garden cane",           "Quest complete — cane.",         Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(GARDEN_BRUSH,           "Garden brush",          "Quest complete — brush.",        Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(EXTENDED_BRUSH_1,       "Extended brush (1 cane)",   "Quest complete — brush.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(EXTENDED_BRUSH_2,       "Extended brush (2 canes)",  "Quest complete — brush.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(EXTENDED_BRUSH_3,       "Extended brush (3 canes)",  "Quest complete — brush.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(FENKENSTRAIN_TORSO,     "Torso",                 "Quest complete — body part.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(FENKENSTRAIN_ARMS,      "Arms",                  "Quest complete — body part.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(FENKENSTRAIN_LEGS,      "Legs (Fenkenstrain)",   "Quest complete — body part.",    Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(DECAPITATED_HEAD_BRAINLESS, "Decapitated head (brainless)", "Quest complete — head.", Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(DECAPITATED_HEAD_BRAIN, "Decapitated head (with brain)", "Quest complete — head.", Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(PICKLED_BRAIN,          "Pickled brain",         "Quest complete — brain.",        Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(CONDUCTOR_MOULD,        "Conductor mould",       "Quest complete — mould.",        Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(JOURNAL_FENKENSTRAIN,   "Journal (Creature of Fenkenstrain)", "Quest complete — journal.", Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(LETTER_FENKENSTRAIN,    "Letter (Creature of Fenkenstrain)",  "Quest complete — letter.",  Quest.CREATURE_OF_FENKENSTRAIN),
        // ---- Devious Minds (additional) ----------------------------------------
        new JunkEntry(COLOSSAL_POUCH_DEVIOUS_MINDS, "Colossal pouch (Devious Minds)",
            "Quest complete — NOT a Runecrafting pouch; this is the quest-specific item.",
            Quest.DEVIOUS_MINDS),
        // ---- Dragon Slayer I — Melzar's Maze keys ------------------------------
        // Quest.DRAGON_SLAYER_I — confirmed
        new JunkEntry(DS1_KEY_RED,     "Key (red)",     "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(DS1_KEY_ORANGE,  "Key (orange)",  "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(DS1_KEY_YELLOW,  "Key (yellow)",  "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(DS1_KEY_BLUE,    "Key (blue)",    "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(DS1_KEY_MAGENTA, "Key (magenta)", "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        new JunkEntry(DS1_KEY_GREEN,   "Key (green)",   "Quest complete (Dragon Slayer I) — maze key.", Quest.DRAGON_SLAYER_I),
        // ---- Dream Mentor -------------------------------------------------------
        // Quest.DREAM_MENTOR — confirmed
        new JunkEntry(DREAM_VIAL_EMPTY,   "Dream vial (empty)",  "Quest complete — vial.",          Quest.DREAM_MENTOR),
        new JunkEntry(DREAM_VIAL_WATER,   "Dream vial (water)",  "Quest complete — vial.",          Quest.DREAM_MENTOR),
        new JunkEntry(DREAM_VIAL_HERB,    "Dream vial (herb)",   "Quest complete — vial.",          Quest.DREAM_MENTOR),
        new JunkEntry(DREAM_POTION,       "Dream potion",        "Quest complete — potion.",        Quest.DREAM_MENTOR),
        new JunkEntry(GROUND_ASTRAL_RUNE, "Ground astral rune",  "Quest complete — ingredient.",    Quest.DREAM_MENTOR),
        new JunkEntry(ASTRAL_RUNE_SHARDS, "Astral rune shards",  "Quest complete — ingredient.",    Quest.DREAM_MENTOR),
        new JunkEntry(CYRISUS_CHEST,      "Cyrisus's chest",     "Quest complete — chest item.",    Quest.DREAM_MENTOR),
        // ---- Druidic Ritual ----------------------------------------------------
        // Quest.DRUIDIC_RITUAL — confirmed
        new JunkEntry(ENCHANTED_BEEF,    "Enchanted beef",    "Quest complete — enchanted meat.", Quest.DRUIDIC_RITUAL),
        new JunkEntry(ENCHANTED_RAT,     "Enchanted rat",     "Quest complete — enchanted meat.", Quest.DRUIDIC_RITUAL),
        new JunkEntry(ENCHANTED_BEAR,    "Enchanted bear",    "Quest complete — enchanted meat.", Quest.DRUIDIC_RITUAL),
        new JunkEntry(ENCHANTED_CHICKEN, "Enchanted chicken", "Quest complete — enchanted meat.", Quest.DRUIDIC_RITUAL),
        // ---- Dwarf Cannon -------------------------------------------------------
        // Quest.DWARF_CANNON — confirmed; IDs 0, 1, 3, 14 confirmed correct.
        new JunkEntry(DWARF_REMAINS,         "Dwarf remains",    "Quest complete — remains.",          Quest.DWARF_CANNON),
        new JunkEntry(DWARF_CANNON_TOOLKIT,  "Toolkit",          "Quest complete — toolkit.",          Quest.DWARF_CANNON),
        new JunkEntry(NULODIONS_NOTES,       "Nulodion's notes", "Quest complete — notes.",            Quest.DWARF_CANNON),
        new JunkEntry(RAILING_DWARF_CANNON,  "Railing",          "Quest complete — railing component.", Quest.DWARF_CANNON),
        // ---- Eadgar's Ruse -----------------------------------------------------
        // Quest.EADGARS_RUSE — confirmed
        new JunkEntry(TROLL_THISTLE,  "Troll thistle",  "Quest complete — herb.",       Quest.EADGARS_RUSE),
        new JunkEntry(DRIED_THISTLE,  "Dried thistle",  "Quest complete — herb.",       Quest.EADGARS_RUSE),
        new JunkEntry(GROUND_THISTLE, "Ground thistle", "Quest complete — ingredient.", Quest.EADGARS_RUSE),
        new JunkEntry(TROLL_POTION,   "Troll potion",   "Quest complete — potion.",     Quest.EADGARS_RUSE),
        new JunkEntry(DRUNK_PARROT,   "Drunk parrot",   "Quest complete — parrot.",     Quest.EADGARS_RUSE),
        new JunkEntry(DIRTY_ROBE,     "Dirty robe",     "Quest complete — robe.",       Quest.EADGARS_RUSE),
        new JunkEntry(FAKE_MAN,       "Fake man",       "Quest complete — dummy.",      Quest.EADGARS_RUSE),
        new JunkEntry(STOREROOM_KEY,  "Storeroom key",  "Quest complete — key.",        Quest.EADGARS_RUSE),
        new JunkEntry(ALCO_CHUNKS,    "Alco-chunks",    "Quest complete — ingredient.", Quest.EADGARS_RUSE),
        // ---- Eagles' Peak -------------------------------------------------------
        // Quest.EAGLES_PEAK — confirmed
        new JunkEntry(BIRD_BOOK,                "Bird book",                  "Quest complete — book.",           Quest.EAGLES_PEAK),
        new JunkEntry(GOLDEN_FEATHER_EAGLES_PEAK,"Golden feather (Eagles' Peak)", "Quest complete — feather.",   Quest.EAGLES_PEAK),
        new JunkEntry(ODD_BIRD_SEED,            "Odd bird seed",              "Quest complete — seed.",           Quest.EAGLES_PEAK),
        new JunkEntry(FEATHERED_JOURNAL,        "Feathered journal",          "Quest complete — journal.",        Quest.EAGLES_PEAK),
        // ---- Elemental Workshop I ----------------------------------------------
        // Quest.ELEMENTAL_WORKSHOP_I — confirmed
        new JunkEntry(STONE_BOWL_EMPTY, "A stone bowl (empty)", "Quest complete — bowl.", Quest.ELEMENTAL_WORKSHOP_I),
        new JunkEntry(STONE_BOWL_FULL,  "A stone bowl (full)",  "Quest complete — bowl.", Quest.ELEMENTAL_WORKSHOP_I),
        // ---- Elemental Workshop II ---------------------------------------------
        // Quest.ELEMENTAL_WORKSHOP_II — confirmed
        new JunkEntry(CRANE_SCHEMATIC, "Crane schematic",  "Quest complete — schematic.",  Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(LEVER_SCHEMATIC, "Lever schematic",  "Quest complete — schematic.",  Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(CRANE_CLAW,      "Crane claw",       "Quest complete — component.",  Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(SCROLL_EW2,      "Scroll (Elemental Workshop II)", "Quest complete — scroll.", Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(KEY_EW2,         "Key (Elemental Workshop II)",    "Quest complete — key.",    Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(PIPE_EW2,        "Pipe (Elemental Workshop II)",   "Quest complete — pipe.",   Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(LARGE_COG_EW2,   "Large cog",        "Quest complete — cog.",        Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(MEDIUM_COG_EW2,  "Medium cog",       "Quest complete — cog.",        Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(SMALL_COG_EW2,   "Small cog",        "Quest complete — cog.",        Quest.ELEMENTAL_WORKSHOP_II),
        // ---- Enakhra's Lament --------------------------------------------------
        // Quest.ENAKHRAS_LAMENT — confirmed
        new JunkEntry(CAMEL_MASK, "Camel mask",
            "Quest complete (Enakhra's Lament) — mask has no combat or skilling use.",
            Quest.ENAKHRAS_LAMENT),
        // ---- Enlightened Journey -----------------------------------------------
        // Quest.ENLIGHTENED_JOURNEY — confirmed
        new JunkEntry(AUGUSTES_SAPLING,         "Auguste's sapling",        "Quest complete — sapling.",  Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(BALLOON_STRUCTURE,         "Balloon structure",        "Quest complete — structure.", Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_ORIGAMI,   "Origami balloon",          "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_YELLOW,    "Origami balloon (yellow)", "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_BLUE,      "Origami balloon (blue)",   "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_RED,       "Origami balloon (red)",    "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_ORANGE,    "Origami balloon (orange)", "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_GREEN,     "Origami balloon (green)",  "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_PURPLE,    "Origami balloon (purple)", "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_PINK,      "Origami balloon (pink)",   "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(ORIGAMI_BALLOON_BLACK,     "Origami balloon (black)",  "Quest complete — balloon.",   Quest.ENLIGHTENED_JOURNEY),
        new JunkEntry(SANDBAG_EJ,                "Sandbag",                  "Quest complete — sandbag.",   Quest.ENLIGHTENED_JOURNEY),
        // ---- Ernest the Chicken (additional) -----------------------------------
        new JunkEntry(POISONED_FISH_FOOD, "Poisoned fish food",
            "Quest complete (Ernest the Chicken) — used to poison the piranhas.",
            Quest.ERNEST_THE_CHICKEN),
        // ---- The Eyes of Glouphrie ---------------------------------------------
        // Quest.THE_EYES_OF_GLOUPHRIE — confirmed
        new JunkEntry(MAGIC_GLUE,      "Magic glue",       "Quest complete — adhesive.",    Quest.THE_EYES_OF_GLOUPHRIE),
        new JunkEntry(WEIRD_GLOOP,     "Weird gloop",      "Quest complete — ingredient.",  Quest.THE_EYES_OF_GLOUPHRIE),
        new JunkEntry(GROUND_MUD_RUNES,"Ground mud runes", "Quest complete — ingredient.",  Quest.THE_EYES_OF_GLOUPHRIE),
        new JunkEntry(HAZELMERES_BOOK, "Hazelmere's book", "Quest complete — book.",        Quest.THE_EYES_OF_GLOUPHRIE),
        // Crystal tokens — all 28 (7 colours × 4 shapes); junk only after both Eyes and Path of Glouphrie
        new JunkEntry(RED_CIRCLE,      "Red circle",       "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(RED_TRIANGLE,    "Red triangle",     "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(RED_SQUARE,      "Red square",       "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(RED_PENTAGON,    "Red pentagon",     "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(ORANGE_CIRCLE,   "Orange circle",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(ORANGE_TRIANGLE, "Orange triangle",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(ORANGE_SQUARE,   "Orange square",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(ORANGE_PENTAGON, "Orange pentagon",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(YELLOW_CIRCLE,   "Yellow circle",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(YELLOW_TRIANGLE, "Yellow triangle",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(YELLOW_SQUARE,   "Yellow square",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(YELLOW_PENTAGON, "Yellow pentagon",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(GREEN_CIRCLE,    "Green circle",     "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(GREEN_TRIANGLE,  "Green triangle",   "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(GREEN_SQUARE,    "Green square",     "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(GREEN_PENTAGON,  "Green pentagon",   "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(BLUE_CIRCLE,     "Blue circle",      "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(BLUE_TRIANGLE,   "Blue triangle",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(BLUE_SQUARE,     "Blue square",      "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(BLUE_PENTAGON,   "Blue pentagon",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(INDIGO_CIRCLE,   "Indigo circle",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(INDIGO_TRIANGLE, "Indigo triangle",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(INDIGO_SQUARE,   "Indigo square",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(INDIGO_PENTAGON, "Indigo pentagon",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(VIOLET_CIRCLE,   "Violet circle",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(VIOLET_TRIANGLE, "Violet triangle",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(VIOLET_SQUARE,   "Violet square",    "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(VIOLET_PENTAGON, "Violet pentagon",  "Quest complete — crystal token.", Quest.THE_EYES_OF_GLOUPHRIE)
            .withRequiredQuest2(Quest.THE_PATH_OF_GLOUPHRIE),
        // ---- Fairytale I - Growing Pains --------------------------------------
        // Quest.FAIRYTALE_I__GROWING_PAINS — confirmed (double underscore)
        new JunkEntry(DRAYNOR_SKULL,     "Draynor skull",     "Quest complete — skull.",         Quest.FAIRYTALE_I__GROWING_PAINS),
        new JunkEntry(QUEENS_SECATEURS_FI,"Queen's secateurs","Quest complete (Fairytale I) — secateurs version from this quest.", Quest.FAIRYTALE_I__GROWING_PAINS),
        new JunkEntry(SYMPTOMS_LIST,     "Symptoms list",     "Quest complete — list.",          Quest.FAIRYTALE_I__GROWING_PAINS),
        // ---- Fairytale II - Cure a Queen --------------------------------------
        // Quest.FAIRYTALE_II__CURE_A_QUEEN — confirmed (double underscore)
        new JunkEntry(QUEENS_SECATEURS_FII, "Queen's secateurs (Fairytale II)",
            "Quest complete (Fairytale II) — secateurs version from this quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(NUFFS_CERTIFICATE, "Nuff's certificate",
            "Quest complete (Fairytale II) — certificate.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        // ---- Family Crest -------------------------------------------------------
        // Quest.FAMILY_CREST — confirmed
        new JunkEntry(PERFECT_GOLD_ORE,   "'perfect' gold ore",   "Quest complete — ore.", Quest.FAMILY_CREST),
        new JunkEntry(PERFECT_GOLD_BAR,   "'perfect' gold bar",   "Quest complete — bar.", Quest.FAMILY_CREST),
        new JunkEntry(PERFECT_RING,       "'perfect' ring",       "Quest complete — ring.", Quest.FAMILY_CREST),
        new JunkEntry(PERFECT_NECKLACE,   "'perfect' necklace",   "Quest complete — necklace.", Quest.FAMILY_CREST),
        new JunkEntry(CREST_PART_AVAN,    "Crest part (Avan)",    "Quest complete — crest piece.", Quest.FAMILY_CREST),
        new JunkEntry(CREST_PART_CALEB,   "Crest part (Caleb)",   "Quest complete — crest piece.", Quest.FAMILY_CREST),
        new JunkEntry(CREST_PART_JOHNATHON,"Crest part (Johnathon)","Quest complete — crest piece.", Quest.FAMILY_CREST),
        new JunkEntry(FAMILY_CREST,       "Family crest",         "Quest complete — assembled crest.", Quest.FAMILY_CREST),
        // ---- Fishing Contest ----------------------------------------------------
        // Quest.FISHING_CONTEST — confirmed
        new JunkEntry(FISHING_PASS, "Fishing pass",
            "Quest complete (Fishing Contest) — pass no longer needed.",
            Quest.FISHING_CONTEST),
        // ---- The Fremennik Trials -----------------------------------------------
        // Quest.THE_FREMENNIK_TRIALS — confirmed
        new JunkEntry(EXOTIC_FLOWER,       "Exotic flower",         "Quest complete — gift for Manni.",  Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(UNUSUAL_FISH,        "Unusual fish",          "Quest complete — gift for Peer.",   Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(STRANGE_OBJECT,      "Strange object",        "Quest complete — unlit object.",    Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(LIT_STRANGE_OBJECT,  "Lit strange object",    "Quest complete — lit object.",      Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(KEG_OF_BEER_FREMENNIK,"Keg of beer",          "Quest complete — keg.",             Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FROZEN_KEY_FREMENNIK,"Frozen key",            "Quest complete — key.",             Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(MAGNET_FREMENNIK,    "Magnet (Fremennik)",    "Quest complete — magnet.",          Quest.THE_FREMENNIK_TRIALS),
        // Unstrung lyre and Lyre: YELLOW — only junk after quest AND an enchanted lyre variant is in bank.
        new JunkEntry(UNSTRUNG_LYRE, "Unstrung lyre", JunkTier.YELLOW,
            "Superseded by Enchanted lyre — only flagged after Fremennik Trials is complete<br>"
            + "AND an Enchanted lyre variant is in the bank.",
            Quest.THE_FREMENNIK_TRIALS, new int[]{
                ENCHANTED_LYRE_UNCHARGED, ENCHANTED_LYRE_1, ENCHANTED_LYRE_2,
                ENCHANTED_LYRE_3, ENCHANTED_LYRE_4, ENCHANTED_LYRE_5, ENCHANTED_LYRE_I}),
        new JunkEntry(LYRE, "Lyre", JunkTier.YELLOW,
            "Superseded by Enchanted lyre — only flagged after Fremennik Trials is complete<br>"
            + "AND an Enchanted lyre variant is in the bank.",
            Quest.THE_FREMENNIK_TRIALS, new int[]{
                ENCHANTED_LYRE_UNCHARGED, ENCHANTED_LYRE_1, ENCHANTED_LYRE_2,
                ENCHANTED_LYRE_3, ENCHANTED_LYRE_4, ENCHANTED_LYRE_5, ENCHANTED_LYRE_I}),
        // ---- Ghosts Ahoy (additional items) ------------------------------------
        new JunkEntry(PUDDLE_OF_SLIME,         "Puddle of slime",       "Quest complete — ingredient.",   Quest.GHOSTS_AHOY),
        new JunkEntry(CUP_OF_TEA_GHOSTS_AHOY_MILKY, "Cup of tea (milky, Ghosts Ahoy)", "Quest complete — tea.", Quest.GHOSTS_AHOY),
        // GHOSTSPEAK_ENCHANTED removed — enchanted variant has post-quest use; keep in bank.
        new JunkEntry(RAW_BEEF_UNDEAD,         "Raw beef (undead)",     "Quest complete — undead meat.",  Quest.GHOSTS_AHOY),
        new JunkEntry(RAW_CHICKEN_UNDEAD,      "Raw chicken (undead)",  "Quest complete — undead meat.",  Quest.GHOSTS_AHOY),
        new JunkEntry(COOKED_CHICKEN_UNDEAD,   "Cooked chicken (undead)","Quest complete — undead meat.", Quest.GHOSTS_AHOY),
        new JunkEntry(COOKED_MEAT_UNDEAD,      "Cooked meat (undead)",  "Quest complete — undead meat.",  Quest.GHOSTS_AHOY),
        // ---- The Grand Tree ----------------------------------------------------
        // Quest.THE_GRAND_TREE — confirmed
        new JunkEntry(GLOUGHS_JOURNAL, "Glough's journal",
            "Quest complete — journal no longer needed.",
            Quest.THE_GRAND_TREE),
        // ---- The Great Brain Robbery (additional) ------------------------------
        new JunkEntry(KEG_DUMMY_GBR, "Keg (dummy)",
            "Quest complete — dummy keg prop.",
            Quest.THE_GREAT_BRAIN_ROBBERY),
        // ---- Horror from the Deep -----------------------------------------------
        // Quest.HORROR_FROM_THE_DEEP — confirmed
        new JunkEntry(JOURNAL_HORROR,  "Journal (Horror from the Deep)", "Quest complete — journal.", Quest.HORROR_FROM_THE_DEEP),
        new JunkEntry(MANUAL_HORROR,   "Manual",                          "Quest complete — manual.",  Quest.HORROR_FROM_THE_DEEP),
        new JunkEntry(DIARY_HORROR,    "Diary (Horror from the Deep)",   "Quest complete — diary.",   Quest.HORROR_FROM_THE_DEEP),
        new JunkEntry(LIGHTHOUSE_KEY,  "Lighthouse key",                 "Quest complete — key.",     Quest.HORROR_FROM_THE_DEEP),
        // ---- In Aid of the Myreque ----------------------------------------------
        // Quest.IN_AID_OF_THE_MYREQUE — confirmed
        new JunkEntry(DUSTY_SCROLL_MYREQUE, "Dusty scroll (In Aid of the Myreque)", "Quest complete — scroll.", Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(CRATE_MYREQUE,        "Crate (In Aid of the Myreque)",        "Quest complete — crate.",  Quest.IN_AID_OF_THE_MYREQUE),
        // ---- Jungle Potion (additional herbs) ----------------------------------
        // Grimy/clean snake weed: used in Jungle Potion AND Legends' Quest — gate on latter.
        new JunkEntry(GRIMY_SNAKE_WEED, "Grimy snake weed",
            "Used in Jungle Potion and Legends' Quest — junk only after both are complete.",
            Quest.LEGENDS_QUEST)
            .withRequiredQuest2(Quest.JUNGLE_POTION),
        new JunkEntry(SNAKE_WEED, "Snake weed",
            "Used in Jungle Potion and Legends' Quest — junk only after both are complete.",
            Quest.LEGENDS_QUEST)
            .withRequiredQuest2(Quest.JUNGLE_POTION),
        new JunkEntry(UNFINISHED_POTION_ROGUES_PURSE, "Unfinished potion (Rogue's Purse)",
            "Used in Jungle Potion and Zogre Flesh Eaters — junk only after both are complete.",
            Quest.JUNGLE_POTION)
            .withRequiredQuest2(Quest.ZOGRE_FLESH_EATERS),
        // ---- Legends' Quest ----------------------------------------------------
        // Quest.LEGENDS_QUEST — confirmed
        new JunkEntry(BLUE_HAT_LQ,      "Blue hat (Legends' Quest)", "Quest complete (Legends' Quest) — blue hat worn during quest, no use after quest.", Quest.LEGENDS_QUEST),
        new JunkEntry(HOLLOW_REED,      "Hollow reed",       "Quest complete — reed.",   Quest.LEGENDS_QUEST),
        new JunkEntry(HOLY_FORCE,       "Holy force",        "Quest complete (Legends' Quest) — holy force used during quest, no use after quest.", Quest.LEGENDS_QUEST),
        new JunkEntry(SNAKEWEED_MIXTURE,"Snakeweed mixture", "Quest complete — potion.", Quest.LEGENDS_QUEST),
        new JunkEntry(ARDRIGAL_MIXTURE, "Ardrigal mixture",  "Quest complete — potion.", Quest.LEGENDS_QUEST),
        new JunkEntry(BRAVERY_POTION,   "Bravery potion",    "Quest complete — potion.", Quest.LEGENDS_QUEST),
        // NOTE: Bullroarer (716) is a STASH storable item (Master clue) — add to StashDatabase, NOT here.
        // ---- Lost City ---------------------------------------------------------
        // Dramen branch: needed for Lost City, The Fremennik Trials (Zanaris access), and
        // RFD: Sir Amik Varze. Gated on RECIPE_FOR_DISASTER__SIR_AMIK_VARZE (latest).
        // Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE — confirmed
        new JunkEntry(DRAMEN_BRANCH, "Dramen branch",
            "Needed for Lost City, The Fremennik Trials, and RFD: Sir Amik Varze. "
            + "Only junk after all three are complete.",
            Quest.THE_FREMENNIK_TRIALS)
            .withRequiredQuest2(Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        // ---- Monkey Madness I --------------------------------------------------
        // Quest.MONKEY_MADNESS_I — confirmed
        new JunkEntry(MONKEY_MM1,       "Monkey (Monkey Madness I)",
            "Quest complete — quest monkey, no further use.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(TENTH_SQUAD_SIGIL,"10th squad sigil",
            "Quest complete — sigil no longer needed.",
            Quest.MONKEY_MADNESS_I),
        // --- Monkey Madness II ---
        new JunkEntry(MYSTERIOUS_NOTE_BLANK, "Mysterious note",
            "Quest complete (Monkey Madness II) — blank variant; cipher puzzle piece, no use after quest.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(MYSTERIOUS_NOTE_LEMON, "Mysterious note",
            "Quest complete (Monkey Madness II) — lemon-treated variant; no use after quest.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(MYSTERIOUS_NOTE_HEATED, "Mysterious note",
            "Quest complete (Monkey Madness II) — heat-revealed variant; no use after quest.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(SCRAWLED_NOTE_MM2, "Scrawled note",
            "Quest complete (Monkey Madness II) — found on dead gnome; no use after quest.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(TRANSLATED_NOTE_MM2, "Translated note",
            "Quest complete (Monkey Madness II) — decoded version of mysterious note.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(BOOK_OF_SPYOLOGY, "Book of spyology",
            "Quest complete (Monkey Madness II) — used to learn invisible ink technique.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(BRUSH_MM2, "Brush",
            "Quest complete (Monkey Madness II) — used to apply lemon juice to note.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(JUICE_COATED_BRUSH, "Juice-coated brush",
            "Quest complete (Monkey Madness II) — brush dipped in lemon juice.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(HANDKERCHIEF, "Handkerchief",
            "Quest complete (Monkey Madness II) — soaked in chloroform to subdue Kruk.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(KRUKS_PAW, "Kruk's paw",
            "Quest complete (Monkey Madness II) — Kruk's severed paw; used to make greegree.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(SATCHEL_MM2_EMPTY, "Satchel (Monkey Madness II)",
            "Quest complete (Monkey Madness II) — empty variant; used to smuggle items.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(SATCHEL_MM2_FILLED, "Satchel (Monkey Madness II)",
            "Quest complete (Monkey Madness II) — filled variant; used to smuggle items.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(NIEVE_ITEM, "Nieve (item)",
            "Quest complete (Monkey Madness II) — Nieve in item form after the attack cutscene.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(ELYSIAN_SPIRIT_SHIELD_MM2, "Elysian spirit shield (Monkey Madness II)",
            "Quest complete (Monkey Madness II) — fake prop shield used in distraction; not a real Elysian.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(CHARGED_ONYX_MM2, "Charged onyx",
            "Quest complete (Monkey Madness II) — onyx charged for the monkey controller.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(DECONSTRUCTED_ONYX_MM2, "Deconstructed onyx",
            "Quest complete (Monkey Madness II) — deconstructed onyx from the device.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(BRONZE_KEY_MM2, "Bronze key (Monkey Madness II)",
            "Quest complete (Monkey Madness II) — unlocks the laboratory.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(COMBAT_SCRATCHED_KEY, "Combat scratched key",
            "Quest complete (Monkey Madness II) — partially repaired key.",
            Quest.MONKEY_MADNESS_II),
        new JunkEntry(COMBAT_DAMAGED_KEY, "Combat damaged key",
            "Quest complete (Monkey Madness II) — damaged key found in combat.",
            Quest.MONKEY_MADNESS_II),
        // ---- Olaf's Quest -------------------------------------------------------
        // Quest.OLAFS_QUEST — confirmed
        new JunkEntry(DAMP_PLANKS,      "Damp planks",              "Quest complete — planks.",  Quest.OLAFS_QUEST),
        new JunkEntry(PARCHMENT_OLAFS,  "Parchment (Olaf's Quest)", "Quest complete — parchment.", Quest.OLAFS_QUEST),
        new JunkEntry(KEY_OLAFS_CROSS,  "Key (cross)",              "Quest complete — key.",     Quest.OLAFS_QUEST),
        new JunkEntry(KEY_OLAFS_SQUARE, "Key (square)",             "Quest complete — key.",     Quest.OLAFS_QUEST),
        new JunkEntry(KEY_OLAFS_TRIANGLE,"Key (triangle)",          "Quest complete — key.",     Quest.OLAFS_QUEST),
        new JunkEntry(KEY_OLAFS_HEXAGON,"Key (hexagon)",            "Quest complete — key.",     Quest.OLAFS_QUEST),
        new JunkEntry(KEY_OLAFS_STAR,   "Key (star)",               "Quest complete — key.",     Quest.OLAFS_QUEST),
        // ---- Pirate's Treasure (additional) ------------------------------------
        new JunkEntry(CASKET_PIRATES_TREASURE, "Casket (Pirate's Treasure)",
            "Quest complete — casket retrieved, no further use.",
            Quest.PIRATES_TREASURE),
        // ---- Romeo & Juliet (additional) ---------------------------------------
        new JunkEntry(CADAVA_POTION, "Cadava potion",
            "Quest complete (Romeo & Juliet) — potion used during quest.",
            Quest.ROMEO__JULIET),
        // ---- Shield of Arrav (additional) --------------------------------------
        new JunkEntry(BOOK_SHIELD_OF_ARRAV, "Book (Shield of Arrav)",
            "Quest complete — book no longer needed.",
            Quest.SHIELD_OF_ARRAV),
        new JunkEntry(INTEL_REPORT, "Intel report",
            "Quest complete — intelligence report no longer needed.",
            Quest.SHIELD_OF_ARRAV),
        // ---- Sheep Herder -------------------------------------------------------
        // Quest.SHEEP_HERDER — confirmed
        new JunkEntry(CATTLEPROD,    "Cattleprod",    "Quest complete — prod used to herd sheep.", Quest.SHEEP_HERDER),
        new JunkEntry(SHEEP_BONES_1, "Sheep bones (1)", "Quest complete — incinerated sheep bones.", Quest.SHEEP_HERDER),
        new JunkEntry(SHEEP_BONES_2, "Sheep bones (2)", "Quest complete — incinerated sheep bones.", Quest.SHEEP_HERDER),
        new JunkEntry(SHEEP_BONES_3, "Sheep bones (3)", "Quest complete — incinerated sheep bones.", Quest.SHEEP_HERDER),
        new JunkEntry(SHEEP_BONES_4, "Sheep bones (4)", "Quest complete — incinerated sheep bones.", Quest.SHEEP_HERDER),
        new JunkEntry(PLAGUE_JACKET,  "Plague jacket",  "Quest complete — plague costume.", Quest.SHEEP_HERDER),
        new JunkEntry(PLAGUE_TROUSERS,"Plague trousers","Quest complete — plague costume.", Quest.SHEEP_HERDER),
        // ---- The Tourist Trap --------------------------------------------------
        // Quest.THE_TOURIST_TRAP — confirmed
        new JunkEntry(BARREL_TOURIST_TRAP, "Barrel (The Tourist Trap)", "Quest complete — barrel.", Quest.THE_TOURIST_TRAP),
        new JunkEntry(ANA_IN_A_BARREL,     "Ana in a barrel",           "Quest complete — quest character.", Quest.THE_TOURIST_TRAP),
        new JunkEntry(ROCK_TOURIST_TRAP,   "Rock (The Tourist Trap)",   "Quest complete — rock.",   Quest.THE_TOURIST_TRAP),
        // ---- Underground Pass (additional) ------------------------------------
        new JunkEntry(UNICORN_HORN_UP, "Unicorn horn (Underground Pass)",
            "Quest complete — unicorn horn used in the pass.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(WITCHS_CAT,      "Witch's cat",
            "Quest complete — Koftik's cat returned.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(IBANS_DOVE,      "Iban's dove",   "Quest complete — dove.", Quest.UNDERGROUND_PASS),
        new JunkEntry(IBANS_SHADOW,    "Iban's shadow", "Quest complete — shadow.", Quest.UNDERGROUND_PASS),
        new JunkEntry(IBANS_ASHES,     "Iban's ashes",  "Quest complete — ashes.", Quest.UNDERGROUND_PASS),
        // ---- Watchtower --------------------------------------------------------
        // Quest.WATCHTOWER — confirmed
        // Bat bones: also needed for Merlin's Crystal and Forgettable Tale — gate on the two latest.
        new JunkEntry(BAT_BONES, "Bat bones",
            "Needed for Merlin's Crystal, Forgettable Tale..., and Watchtower. "
            + "Only junk after all three are complete.",
            Quest.WATCHTOWER)
            .withRequiredQuest2(Quest.FORGETTABLE_TALE),
        new JunkEntry(OGRE_RELIC,               "Ogre relic",            "Quest complete — relic.",    Quest.WATCHTOWER),
        new JunkEntry(CRYSTAL_WATCHTOWER_YELLOW,"Crystal (yellow)",      "Quest complete — crystal.",  Quest.WATCHTOWER),
        new JunkEntry(CRYSTAL_WATCHTOWER_MAGENTA,"Crystal (magenta)",    "Quest complete — crystal.",  Quest.WATCHTOWER),
        new JunkEntry(CRYSTAL_WATCHTOWER_CYAN,  "Crystal (cyan)",        "Quest complete — crystal.",  Quest.WATCHTOWER),
        new JunkEntry(CRYSTAL_WATCHTOWER_GREY,  "Crystal (grey)",        "Quest complete — crystal.",  Quest.WATCHTOWER),
        new JunkEntry(FINGERNAILS,              "Fingernails",           "Quest complete — ingredient.", Quest.WATCHTOWER),
        new JunkEntry(POTION_WATCHTOWER,        "Potion (Watchtower)",   "Quest complete — potion.",   Quest.WATCHTOWER),
        // ---- Witch's House -----------------------------------------------------
        // Quest.WITCHS_HOUSE — confirmed
        new JunkEntry(DIARY_WITCHS_HOUSE,  "Diary (Witch's House)",  "Quest complete — diary.",  Quest.WITCHS_HOUSE),
        new JunkEntry(MAGNET_WITCHS_HOUSE, "Magnet (Witch's House)", "Quest complete — magnet.", Quest.WITCHS_HOUSE),
        new JunkEntry(KEY_WITCHS_HOUSE,    "Key (Witch's House)",    "Quest complete — key.",    Quest.WITCHS_HOUSE),
        // Door key: also needed for Grim Tales — only junk after BOTH are complete.
        // Quest.GRIM_TALES — confirmed
        new JunkEntry(DOOR_KEY_WITCHS_HOUSE, "Door key (Witch's House)",
            "Used in Witch's House and Grim Tales — junk only after both are complete.",
            Quest.WITCHS_HOUSE)
            .withRequiredQuest2(Quest.GRIM_TALES),
        // ---- The Golem ---------------------------------------------------------
        // Quest.THE_GOLEM — confirmed
        // Strange implement is found in the lair (also used in Shadow of the Storm).
        new JunkEntry(LETTER_GOLEM,     "Letter (The Golem)",     "Quest complete — letter.",            Quest.THE_GOLEM),
        new JunkEntry(STATUETTE_GOLEM,  "Statuette (The Golem)",  "Quest complete — clay statuette.",    Quest.THE_GOLEM),
        new JunkEntry(STRANGE_IMPLEMENT,"Strange implement",
            "Used in The Golem and Shadow of the Storm — junk only after both are complete.",
            Quest.THE_GOLEM)
            .withRequiredQuest2(Quest.SHADOW_OF_THE_STORM),
        new JunkEntry(GOLEM_PROGRAM,    "Golem program",          "Quest complete — program scroll.",    Quest.THE_GOLEM),
        // ---- Priest in Peril ---------------------------------------------------
        // Quest.PRIEST_IN_PERIL — confirmed
        // Seven golden souvenir items from the Paterdomus mausoleum. Wiki explicitly states
        // each "serves no purpose in the quest, and has no uses outside the quest."
        // Wolfbane (2952) is NOT included — it works against Tier-1 vampyres and is required
        // for the Easy Morytania Diary.
        new JunkEntry(GOLDEN_KEY_PIP,       "Golden key (Priest in Peril)", "Quest complete — ornamental key, no use outside the quest.", Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_TINDERBOX_PIP, "Golden tinderbox",             "Quest complete — cannot light fires.",                       Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_CANDLE,        "Golden candle",                "Quest complete — cannot be used as a light source.",         Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_POT_PIP,       "Golden pot",                   "Quest complete — cannot store materials.",                   Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_HAMMER_PIP,    "Golden hammer",                "Quest complete — cannot be used for smithing or construction.", Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_FEATHER_PIP,   "Golden feather",               "Quest complete — cannot be used for fishing or fletching.",  Quest.PRIEST_IN_PERIL),
        new JunkEntry(GOLDEN_NEEDLE_PIP,    "Golden needle",                "Quest complete — cannot be used for crafting.",              Quest.PRIEST_IN_PERIL),
        new JunkEntry(IRON_KEY_PIP,         "Iron key (Priest in Peril)",   "Quest complete — iron key for the temple door, no use outside the quest.", Quest.PRIEST_IN_PERIL),
        // ---- Scorpion Catcher --------------------------------------------------
        // Quest.SCORPION_CATCHER — confirmed
        new JunkEntry(SCORPION_CAGE_EMPTY,        "Scorpion cage (empty)",              "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_TAVERLEY,     "Scorpion cage (Taverley)",           "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_TAV_OUTPOST,  "Scorpion cage (Taverley + Outpost)", "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_TAV_MONASTERY,"Scorpion cage (Taverley + Monastery)","Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_OUTPOST,      "Scorpion cage (Outpost)",            "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_OUT_MONASTERY,"Scorpion cage (Outpost + Monastery)","Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_MONASTERY,    "Scorpion cage (Monastery)",          "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        new JunkEntry(SCORPION_CAGE_FULL,         "Scorpion cage (full)",               "Quest complete (Scorpion Catcher) — cage, no use after quest.", Quest.SCORPION_CATCHER),
        // ---- Shadow of the Storm -----------------------------------------------
        // Quest.SHADOW_OF_THE_STORM — confirmed
        // Silverlight becomes Darklight as the quest reward. Darklight is YELLOW — superseded by
        // Arclight. Silverlight is also YELLOW — superseded by Darklight or Arclight (see Demon Slayer).
        // Black desert shirt/robe dyed during the quest are general clothing, not added here.
        // Darklight: YELLOW — only junk if Shadow of the Storm done AND Arclight banked
        new JunkEntry(DARKLIGHT, "Darklight", JunkTier.YELLOW,
            "Superseded by Arclight — only flagged if Shadow of the Storm is complete and Arclight is in bank.",
            Quest.SHADOW_OF_THE_STORM, new int[]{ARCLIGHT}),
        new JunkEntry(DEMONIC_SIGIL_MOULD, "Demonic sigil mould",
            "Quest complete (Shadow of the Storm) — mould used to cast the sigil, no further use.",
            Quest.SHADOW_OF_THE_STORM),
        new JunkEntry(DEMONIC_SIGIL, "Demonic sigil",
            "Quest complete (Shadow of the Storm) — sigil used in the ritual, no further use.",
            Quest.SHADOW_OF_THE_STORM),
        new JunkEntry(DEMONIC_TOME, "Demonic tome",
            "Quest complete (Shadow of the Storm) — tome containing the chant, no further use.",
            Quest.SHADOW_OF_THE_STORM),
        // ---- Murder Mystery ----------------------------------------------------
        // Quest.MURDER_MYSTERY — confirmed
        new JunkEntry(SILVER_POT_MM,       "Silver pot (Murder Mystery)",       "Quest complete — pot, no use outside the quest.", Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_POT_MM_DUSTED,"Silver pot (dusted, Murder Mystery)","Quest complete — pot, no use outside the quest.", Quest.MURDER_MYSTERY),
        // ---- Icthlarin's Little Helper -----------------------------------------
        // Quest.ICTHLARINS_LITTLE_HELPER — confirmed
        new JunkEntry(CANOPIC_JAR_HET,     "Canopic jar (Het)",     "Quest complete — canopic jar, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(CANOPIC_JAR_APMEKEN, "Canopic jar (Apmeken)", "Quest complete — canopic jar, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(CANOPIC_JAR_SCABARAS,"Canopic jar (Scabaras)","Quest complete — canopic jar, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(CANOPIC_JAR_CRONDIS, "Canopic jar (Crondis)", "Quest complete — canopic jar, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(HOLY_SYMBOL_ILH,     "Holy symbol (Icthlarin's Little Helper)",  "Quest complete — symbol used in ritual, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(UNHOLY_SYMBOL_ILH,   "Unholy symbol (Icthlarin's Little Helper)","Quest complete — symbol used in ritual, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(LINEN_ILH,           "Linen (Icthlarin's Little Helper)",        "Quest complete — used for mummification ritual, no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        // ---- Troll Stronghold --------------------------------------------------
        // Quest.TROLL_STRONGHOLD — confirmed
        new JunkEntry(PRISON_KEY_TS, "Prison key (Troll Stronghold)",
            "Quest complete — key used to free prisoners, no use after quest.",
            Quest.TROLL_STRONGHOLD),
        // ---- Plague City -------------------------------------------------------
        // Quest.PLAGUE_CITY — confirmed
        // Gas mask (1506) has continued utility post-quest (Smoke Devil Dungeon, sulphur
        // mining, Nex fight) — NOT added as junk.
        new JunkEntry(BOOK_PLAGUE_CITY, "Book (Plague City)",
            "Quest complete — book used during quest, no use after quest.",
            Quest.PLAGUE_CITY),
        // ---- Troll Romance -----------------------------------------------------
        // Quest.TROLL_ROMANCE — confirmed
        // Waxed sled (4084) has post-quest use (Trollweiss Mountain access, 2 elite clue
        // scroll steps) — NOT added. Unwaxed sled has no use once the quest is complete.
        new JunkEntry(SLED_UNWAXED, "Sled (unwaxed)",
            "Quest complete (Troll Romance) — unwaxed sled has no use after the quest; waxed sled (4084) kept.",
            Quest.TROLL_ROMANCE),
        new JunkEntry(TROLLWEISS, "Trollweiss",
            "Quest complete (Troll Romance) — flower given to Ug, no use after quest.",
            Quest.TROLL_ROMANCE)
    ); }
    private static List<JunkEntry> buildBatch1B() { return Arrays.asList(
        // =====================================================================
        // Batch 2 entries — confirmed 2026-06-13
        // =====================================================================
        // --- Ernest the Chicken (additional) ---
        new JunkEntry(FISH_FOOD, "Fish food",
            "Quest complete (Ernest the Chicken) — used to poison fish pond, no use after quest.",
            Quest.ERNEST_THE_CHICKEN),
        // --- Animal Magnetism (additional) ---
        new JunkEntry(BLESSED_AXE, "Blessed axe",
            "Quest complete (Animal Magnetism) — axe blessed for quest, no use after.",
            Quest.ANIMAL_MAGNETISM),
        // --- Biohazard (additional) ---
        new JunkEntry(PRIEST_GOWN_TOP, "Priest gown (top)",
            "Quest complete (Biohazard) — worn as disguise, no use after quest.",
            Quest.BIOHAZARD),
        new JunkEntry(PRIEST_GOWN_BOTTOM, "Priest gown (bottom)",
            "Quest complete (Biohazard) — worn as disguise, no use after quest.",
            Quest.BIOHAZARD),
        new JunkEntry(ROTTEN_APPLE, "Rotten apple",
            "Quest complete (Biohazard) — used to poison apple barrel; also junk after Mourning's End Part I.",
            Quest.BIOHAZARD)
            .withRequiredQuest2(Quest.MOURNINGS_END_PART_I),
        // --- Client of Kourend (additional) ---
        new JunkEntry(ENCHANTED_SCROLL_COK, "Enchanted scroll",
            "Quest complete (Client of Kourend) — used to enchant quill, no use after quest.",
            Quest.CLIENT_OF_KOUREND),
        new JunkEntry(ENCHANTED_QUILL_COK, "Enchanted quill",
            "Quest complete (Client of Kourend) — used to write favour certificate, no use after quest.",
            Quest.CLIENT_OF_KOUREND),
        // --- Dragon Slayer I (additional) ---
        new JunkEntry(ELVARG_HEAD, "Elvarg's head",
            "Quest complete (Dragon Slayer I) — trophy head, no functional use after quest.",
            Quest.DRAGON_SLAYER_I),
        // --- Eadgar's Ruse (additional) ---
        // Goutweed also needed for Dream Mentor and Dragon Slayer II; gate on later 2 quests
        new JunkEntry(GOUTWEED, "Goutweed",
            "Quest complete (Eadgar's Ruse / Dream Mentor / Dragon Slayer II) — herbal item used in all three quests.",
            Quest.DREAM_MENTOR)
            .withRequiredQuest2(Quest.DRAGON_SLAYER_II),
        // --- Eagles' Peak (additional) ---
        new JunkEntry(EAGLE_FEATHER, "Eagle feather",
            "Quest complete (Eagles' Peak) — used to make eagle cape/fake beak, no use after quest.",
            Quest.EAGLES_PEAK),
        // EAGLE_CAPE removed — used post-quest for the Eagle transport system.
        new JunkEntry(FAKE_BEAK, "Fake beak",
            "Quest complete (Eagles' Peak) — disguise, no use after quest.",
            Quest.EAGLES_PEAK),
        new JunkEntry(METAL_FEATHER, "Metal feather",
            "Quest complete (Eagles' Peak) — used to open stone eagle, no use after quest.",
            Quest.EAGLES_PEAK),
        new JunkEntry(SILVER_FEATHER_EP, "Silver feather",
            "Quest complete (Eagles' Peak) — used to open stone eagle, no use after quest.",
            Quest.EAGLES_PEAK),
        new JunkEntry(BRONZE_FEATHER, "Bronze feather",
            "Quest complete (Eagles' Peak) — used to open stone eagle, no use after quest.",
            Quest.EAGLES_PEAK),
        // --- Fairytale II (additional) ---
        new JunkEntry(GORAK_CLAWS, "Gorak claws",
            "Quest complete (Fairytale II) — used to make gorak claw powder, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(STAR_FLOWER_FII, "Star flower",
            "Quest complete (Fairytale II) — used to make magic essence, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(GORAK_CLAW_POWDER, "Gorak claw powder",
            "Quest complete (Fairytale II) — used to make magic essence potion, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(MAGIC_ESSENCE_UNF, "Magic essence (unfinished)",
            "Quest complete (Fairytale II) — intermediate potion, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(MAGIC_ESSENCE_4, "Magic essence(4)",
            "Quest complete (Fairytale II) — used to restore pixies, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(MAGIC_ESSENCE_3, "Magic essence(3)",
            "Quest complete (Fairytale II) — used to restore pixies, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(MAGIC_ESSENCE_2, "Magic essence(2)",
            "Quest complete (Fairytale II) — used to restore pixies, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        new JunkEntry(MAGIC_ESSENCE_1, "Magic essence(1)",
            "Quest complete (Fairytale II) — used to restore pixies, no use after quest.",
            Quest.FAIRYTALE_II__CURE_A_QUEEN),
        // --- Fight Arena (additional) ---
        new JunkEntry(KHAZARD_HELMET, "Khazard helmet",
            "Quest complete (Fight Arena) — Khazard guard disguise, no use after quest.",
            Quest.FIGHT_ARENA),
        new JunkEntry(KHAZARD_ARMOUR, "Khazard armour",
            "Quest complete (Fight Arena) — Khazard guard disguise, no use after quest.",
            Quest.FIGHT_ARENA),
        // --- Fishing Contest (additional) ---
        new JunkEntry(RED_VINE_WORM, "Red vine worm",
            "Quest complete (Fishing Contest) — bait used in contest; also junk after Fairytale I.",
            Quest.FISHING_CONTEST)
            .withRequiredQuest2(Quest.FAIRYTALE_I__GROWING_PAINS),
        new JunkEntry(FISHING_TROPHY, "Fishing trophy",
            "Quest complete (Fishing Contest) — trophy awarded, no use after quest.",
            Quest.FISHING_CONTEST),
        new JunkEntry(GIANT_CARP, "Giant carp",
            "Quest complete (Fishing Contest) — cooked version, no use after quest.",
            Quest.FISHING_CONTEST),
        new JunkEntry(RAW_GIANT_CARP, "Raw giant carp",
            "Quest complete (Fishing Contest) — raw version, no use after quest.",
            Quest.FISHING_CONTEST),
        // --- Grim Tales ---
        new JunkEntry(GRIFFIN_FEATHER_GRIM, "Griffin feather",
            "Quest complete (Grim Tales) — used to tickle the witch, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(MIAZRQAS_PENDANT, "Miazrqa's pendant",
            "Quest complete (Grim Tales) — worn to shrink, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(MUSIC_SHEET_GRIM, "Music sheet",
            "Quest complete (Grim Tales) — used on piano, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(RUPERTS_HELMET, "Rupert's helmet",
            "Quest complete (Grim Tales) — used to lure giant, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(SHRINKING_RECIPE, "Shrinking recipe",
            "Quest complete (Grim Tales) — recipe for shrink-me-quick, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(TODO_LIST_GRIM, "To-do list",
            "Quest complete (Grim Tales) — witch's to-do list, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(SHRINK_ME_QUICK, "Shrink-me-quick",
            "Quest complete (Grim Tales) — shrinking potion, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(SHRUNK_OGLEROOT, "Shrunk ogleroot",
            "Quest complete (Grim Tales) — miniaturised vegetable, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(GOLDEN_GOBLIN, "Golden goblin",
            "Quest complete (Grim Tales) — statuette given to Sylas, no use after quest.",
            Quest.GRIM_TALES),
        new JunkEntry(MAGIC_BEANS_GRIM, "Magic beans",
            "Quest complete (Grim Tales) — planted during quest, no use after quest.",
            Quest.GRIM_TALES),
        // --- The Grand Tree (additional; BARK_SAMPLE=783 and GLOUGHS_JOURNAL=785 already in DB) ---
        new JunkEntry(TRANSLATION_BOOK_GT, "Translation book",
            "Quest complete (The Grand Tree / Monkey Madness II) — translates notes; junk after both quests complete.",
            Quest.THE_GRAND_TREE)
            .withRequiredQuest2(Quest.MONKEY_MADNESS_II),
        new JunkEntry(HAZELMERE_SCROLL_GT, "Hazelmere's scroll",
            "Quest complete (The Grand Tree) — passed to King Narnode, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(LUMBER_ORDER_GT, "Lumber order",
            "Quest complete (The Grand Tree) — incriminating document, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(GLOUGHS_KEY, "Glough's key",
            "Quest complete (The Grand Tree) — used to access Glough's house, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(TWIGS_T, "Twigs (T)",
            "Quest complete (The Grand Tree) — puzzle twigs, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(TWIGS_U, "Twigs (U)",
            "Quest complete (The Grand Tree) — puzzle twigs, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(TWIGS_Z, "Twigs (Z)",
            "Quest complete (The Grand Tree) — puzzle twigs, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(TWIGS_O, "Twigs (O)",
            "Quest complete (The Grand Tree) — puzzle twigs, no use after quest.",
            Quest.THE_GRAND_TREE),
        new JunkEntry(DACONIA_ROCK, "Daconia rock",
            "Quest complete (The Grand Tree) — kills the grand tree if kept, no use after quest.",
            Quest.THE_GRAND_TREE),
        // --- Horror from the Deep (additional) ---
        new JunkEntry(RUSTY_CASKET_HORROR, "Rusty casket",
            "Quest complete (Horror from the Deep) — reward casket, no use after opening.",
            Quest.HORROR_FROM_THE_DEEP),
        // --- In Aid of the Myreque (additional) ---
        new JunkEntry(ROCK_LIMESTONE, "Rock (limestone)",
            "Quest complete (In Aid of the Myreque) — used for construction, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(BUCKET_RUBBLE_PARTIAL, "Bucket of rubble (partially filled)",
            "Quest complete (In Aid of the Myreque) — construction material, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(BUCKET_RUBBLE_ALMOST_FULL, "Bucket of rubble (almost full)",
            "Quest complete (In Aid of the Myreque) — construction material, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(BUCKET_RUBBLE_TOTALLY_FULL, "Bucket of rubble (totally full)",
            "Quest complete (In Aid of the Myreque) — construction material, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(PLASTER_FRAGMENT, "Plaster fragment",
            "Quest complete (In Aid of the Myreque) — construction material, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(TEMPLE_LIBRARY_KEY, "Temple library key",
            "Quest complete (In Aid of the Myreque) — unlocks library, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(THE_SLEEPING_SEVEN, "The Sleeping Seven",
            "Quest complete (In Aid of the Myreque) — book read for lore, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(HISTORIES_HALLOWLAND, "Histories of the Hallowland",
            "Quest complete (In Aid of the Myreque) — book read for lore, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(MODERN_DAY_MORYTANIA, "Modern Day Morytania",
            "Quest complete (In Aid of the Myreque) — book read for lore, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(SILVER_DUST_MYREQUE, "Silver dust",
            "Quest complete (In Aid of the Myreque) — used to make Guthix balance, no use after quest.",
            Quest.IN_AID_OF_THE_MYREQUE),
        // Guthix balance potions — junk after quest; Morytania Medium Diary not in Quest enum
        new JunkEntry(GUTHIX_BALANCE_UNF_4, "Guthix balance (unf, 4)",
            "Quest complete (In Aid of the Myreque) — Morytania Diary also required; no use after quest and diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_UNF_3, "Guthix balance (unf, 3)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_UNF_2, "Guthix balance (unf, 2)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_UNF_1, "Guthix balance (unf, 1)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_4, "Guthix balance(4)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_3, "Guthix balance(3)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_2, "Guthix balance(2)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(GUTHIX_BALANCE_1, "Guthix balance(1)",
            "Quest complete (In Aid of the Myreque) — no use after quest and Morytania Diary.",
            Quest.IN_AID_OF_THE_MYREQUE),
        // --- Legends' Quest (additional) ---
        // Radimus notes gated on Legends' Quest + Recipes for Disaster (Sir Amik Varze)
        new JunkEntry(RADIMUS_NOTES_INCOMPLETE, "Radimus notes (incomplete)",
            "Quest complete (Legends' Quest / Recipes for Disaster) — incomplete notes, no use after both quests.",
            Quest.LEGENDS_QUEST)
            .withRequiredQuest2(Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(RADIMUS_NOTES, "Radimus notes",
            "Quest complete (Legends' Quest / Recipes for Disaster) — completed notes, no use after both quests.",
            Quest.LEGENDS_QUEST)
            .withRequiredQuest2(Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(SCRAWLED_NOTE, "Scrawled note",
            "Quest complete (Legends' Quest) — journal entry, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(A_SCRIBBLED_NOTE, "A scribbled note",
            "Quest complete (Legends' Quest) — journal entry, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(SCRUMPLED_NOTE, "Scrumpled note",
            "Quest complete (Legends' Quest) — journal entry, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(SKETCH_LQ, "Sketch",
            "Quest complete (Legends' Quest) — map sketch, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GOLD_BOWL_LQ, "Gold bowl",
            "Quest complete (Legends' Quest) — made to bless, consumed in ritual.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(BLESSED_GOLD_BOWL, "Blessed gold bowl",
            "Quest complete (Legends' Quest) — consumed in ritual, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GOLDEN_BOWL_WATER, "Blessed gold bowl (water)",
            "Quest complete (Legends' Quest) — intermediate step, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GOLDEN_BOWL_PURE_WATER, "Blessed gold bowl (pure water)",
            "Quest complete (Legends' Quest) — intermediate step, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GOLDEN_BOWL_BLESSED_WATER, "Blessed gold bowl (blessed water)",
            "Quest complete (Legends' Quest) — intermediate step, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GOLDEN_BOWL_BLESSED_PURE, "Blessed gold bowl (blessed pure)",
            "Quest complete (Legends' Quest) — used to grow yommi tree, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(SHAMAN_TOME, "Shaman tome",
            "Quest complete (Legends' Quest) — read for ritual information, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(BINDING_BOOK, "Binding book",
            "Quest complete (Legends' Quest) — used to bind Nezikchened, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(ENCHANTED_VIAL_LQ, "Enchanted vial",
            "Quest complete (Legends' Quest) — used to hold holy water, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(HOLY_WATER_LQ, "Holy water",
            "Quest complete (Legends' Quest) — used to purify bowl, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(SMASHED_GLASS_LQ, "Smashed glass",
            "Quest complete (Legends' Quest) — broken enchanted vial, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(YOMMI_SEEDS, "Yommi tree seeds",
            "Quest complete (Legends' Quest) — used to grow yommi tree, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(YOMMI_SEEDS_GERMINATED, "Germinated yommi seeds",
            "Quest complete (Legends' Quest) — planted to grow yommi tree, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(CHUNK_CRYSTAL, "Chunk of crystal",
            "Quest complete (Legends' Quest) — carved into totem, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(HUNK_CRYSTAL, "Hunk of crystal",
            "Quest complete (Legends' Quest) — carved into totem, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(LUMP_CRYSTAL, "Lump of crystal",
            "Quest complete (Legends' Quest) — carved into totem, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(HEART_CRYSTAL_INACTIVE, "Heart crystal (inactive)",
            "Quest complete (Legends' Quest) — made into yommi totem, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(HEART_CRYSTAL_ACTIVE, "Heart crystal (active)",
            "Quest complete (Legends' Quest) — made into yommi totem, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(DARK_DAGGER_LQ, "Dark dagger",
            "Quest complete (Legends' Quest) — used to banish Nezikchened, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GLOWING_DAGGER, "Glowing dagger",
            "Quest complete (Legends' Quest) — used to banish Nezikchened, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(YOMMI_TOTEM, "Yommi totem",
            "Quest complete (Legends' Quest) — used in ritual, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(GILDED_TOTEM, "Gilded totem",
            "Quest complete (Legends' Quest) — given to tribe leader, no use after quest.",
            Quest.LEGENDS_QUEST),
        // Oomlie wrap chain — Karamja Hard Diary not in Quest enum, gate on Legends' Quest alone
        new JunkEntry(RAW_OOMLIE, "Raw oomlie",
            "Quest complete (Legends' Quest) — food item used in quest, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(PALM_LEAF_LQ, "Palm leaf",
            "Quest complete (Legends' Quest) — used to wrap oomlie, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(WRAPPED_OOMLIE, "Wrapped oomlie",
            "Quest complete (Legends' Quest) — intermediate food, no use after quest.",
            Quest.LEGENDS_QUEST),
        new JunkEntry(COOKED_OOMLIE_WRAP, "Cooked oomlie wrap",
            "Quest complete (Legends' Quest) — cooked food item, no use after quest.",
            Quest.LEGENDS_QUEST),
        // --- Making Friends with My Arm ---
        new JunkEntry(OLD_MANS_COFFIN, "Old man's coffin",
            "Quest complete (Making Friends with My Arm) — used to fake a death, no use after quest.",
            Quest.MAKING_FRIENDS_WITH_MY_ARM),
        new JunkEntry(REDUCED_CADAVA_POTION, "Reduced cadava potion",
            "Quest complete (Making Friends with My Arm) — sleep potion, no use after quest.",
            Quest.MAKING_FRIENDS_WITH_MY_ARM),
        new JunkEntry(GOAT_DUNG, "Goat dung",
            "Quest complete (Making Friends with My Arm) — used as fuel, no use after quest.",
            Quest.MAKING_FRIENDS_WITH_MY_ARM),
        // WEISS_FIRE_NOTES removed — needed to maintain the Weiss herb patch fire post-quest.
        // --- Monkey Madness I (additional) ---
        new JunkEntry(SPARE_CONTROLS_MM, "Spare controls",
            "Quest complete (Monkey Madness I) — used to fix the ship, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(GNOME_ROYAL_SEAL, "Gnome royal seal",
            "Quest complete (Monkey Madness I) — orders from the king, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(NARNODE_ORDERS, "Narnode's orders",
            "Quest complete (Monkey Madness I) — orders document, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(MONKEY_DENTURES, "Monkey dentures",
            "Quest complete (Monkey Madness I) — used to make monkeyspeak amulet, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(ENCHANTED_BAR_MM, "Enchanted bar",
            "Quest complete (Monkey Madness I) — used to make monkeyspeak amulet mould, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(EYE_OF_GNOME, "Eye of gnome",
            "Quest complete (Monkey Madness I) — used to make monkeyspeak amulet, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(BANANA_STEW, "Banana stew",
            "Quest complete (Monkey Madness I) — used to distract Garkor, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(MAMULET_MOULD, "M'amulet mould",
            "Quest complete (Monkey Madness I) — used to cast amulet, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        new JunkEntry(MSPEAK_AMULET_UNSTRUNG, "Monkeyspeak amulet (unstrung)",
            "Quest complete (Monkey Madness I) — unstrung intermediate, no use after quest.",
            Quest.MONKEY_MADNESS_I),
        // --- Murder Mystery (additional) ---
        new JunkEntry(SILVER_NECKLACE_MM, "Silver necklace",
            "Quest complete (Murder Mystery) — suspect item, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_NECKLACE_MM_DUSTED, "Silver necklace (dusted)",
            "Quest complete (Murder Mystery) — dusted evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_CUP_MM, "Silver cup",
            "Quest complete (Murder Mystery) — suspect item, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_CUP_MM_DUSTED, "Silver cup (dusted)",
            "Quest complete (Murder Mystery) — dusted evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_BOTTLE_MM, "Silver bottle",
            "Quest complete (Murder Mystery) — suspect item, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_BOTTLE_MM_DUSTED, "Silver bottle (dusted)",
            "Quest complete (Murder Mystery) — dusted evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_BOOK_MM, "Silver book",
            "Quest complete (Murder Mystery) — suspect item, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_BOOK_MM_DUSTED, "Silver book (dusted)",
            "Quest complete (Murder Mystery) — dusted evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_NEEDLE_MM, "Silver needle",
            "Quest complete (Murder Mystery) — suspect item, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(SILVER_NEEDLE_MM_DUSTED, "Silver needle (dusted)",
            "Quest complete (Murder Mystery) — dusted evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(CRIMINAL_THREAD_RED, "Criminal thread (red)",
            "Quest complete (Murder Mystery) — evidence thread, no use after quest.",
            Quest.MURDER_MYSTERY)
                .withRequiredQuest2(Quest.KINGS_RANSOM),
        new JunkEntry(CRIMINAL_THREAD_GREEN, "Criminal thread (green)",
            "Quest complete (Murder Mystery) — evidence thread, no use after quest.",
            Quest.MURDER_MYSTERY)
                .withRequiredQuest2(Quest.KINGS_RANSOM),
        new JunkEntry(CRIMINAL_THREAD_BLUE, "Criminal thread (blue)",
            "Quest complete (Murder Mystery) — evidence thread, no use after quest.",
            Quest.MURDER_MYSTERY)
                .withRequiredQuest2(Quest.KINGS_RANSOM),
        new JunkEntry(FLYPAPER_MM, "Flypaper",
            "Quest complete (Murder Mystery) — used to lift fingerprints, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(PUNGENT_POT, "Pungent pot",
            "Quest complete (Murder Mystery) — powder for fingerprinting, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(CRIMINAL_DAGGER_NORMAL, "Criminal's dagger",
            "Quest complete (Murder Mystery) — murder weapon, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(CRIMINAL_DAGGER_DUSTED, "Criminal's dagger (dusted)",
            "Quest complete (Murder Mystery) — fingerprinted weapon, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(KILLERS_PRINT, "Killer's print",
            "Quest complete (Murder Mystery) — fingerprint evidence, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(ANNAS_PRINT, "Anna's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(BOBS_PRINT, "Bob's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(CAROLS_PRINT, "Carol's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(DAVIDS_PRINT, "David's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(ELIZABETHS_PRINT, "Elizabeth's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(FRANKS_PRINT, "Frank's print",
            "Quest complete (Murder Mystery) — comparison print, no use after quest.",
            Quest.MURDER_MYSTERY),
        new JunkEntry(UNKNOWN_PRINT, "Unknown print",
            "Quest complete (Murder Mystery) — unmatched print, no use after quest.",
            Quest.MURDER_MYSTERY),
        // --- Nature Spirit (additional) ---
        new JunkEntry(WASHING_BOWL_NS, "Washing bowl",
            "Quest complete (Nature Spirit) — used in washing ritual, no use after quest.",
            Quest.NATURE_SPIRIT),
        new JunkEntry(MIRROR_NS, "Mirror",
            "Quest complete (Nature Spirit) — used to weaken Malak, no use after quest.",
            Quest.NATURE_SPIRIT),
        new JunkEntry(JOURNAL_FILLIMAN, "Filliman's journal",
            "Quest complete (Nature Spirit) — filled in as evidence of life, no use after quest.",
            Quest.NATURE_SPIRIT),
        new JunkEntry(DRUIDIC_SPELL, "Druidic spell",
            "Quest complete (Nature Spirit) — cast to create silver sickle, no use after quest.",
            Quest.NATURE_SPIRIT),
        new JunkEntry(A_USED_SPELL, "A used spell",
            "Quest complete (Nature Spirit) — spent spell, no use after quest.",
            Quest.NATURE_SPIRIT),
        new JunkEntry(SICKLE_MOULD, "Sickle mould",
            "Quest complete (Nature Spirit) — used to cast silver sickle, no use after quest.",
            Quest.NATURE_SPIRIT),
        // --- Olaf's Quest (additional) ---
        new JunkEntry(CRUDE_CARVING, "Crude carving",
            "Quest complete (Olaf's Quest) — carved rune stone, no use after quest.",
            Quest.OLAFS_QUEST),
        new JunkEntry(CRUDER_CARVING, "Cruder carving",
            "Quest complete (Olaf's Quest) — intermediate carving, no use after quest.",
            Quest.OLAFS_QUEST),
        new JunkEntry(SVENS_LAST_MAP, "Sven's last map",
            "Quest complete (Olaf's Quest) — map to treasure, no use after quest.",
            Quest.OLAFS_QUEST),
        new JunkEntry(WINDSWEPT_LOGS, "Windswept logs",
            "Quest complete (Olaf's Quest) — special logs for boat repair, no use after quest.",
            Quest.OLAFS_QUEST),
        new JunkEntry(ROTTEN_BARREL, "Rotten barrel",
            "Quest complete (Olaf's Quest) — waterlogged barrel, no use after quest.",
            Quest.OLAFS_QUEST),
        // --- One Small Favour ---
        new JunkEntry(BLUNT_AXE_OSF, "Blunt axe",
            "Quest complete (One Small Favour) — blunted axe, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(HERBAL_TINCTURE, "Herbal tincture",
            "Quest complete (One Small Favour) — medicinal tincture, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(STODGY_MATTRESS, "Stodgy mattress",
            "Quest complete (One Small Favour) — bed stuffing, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(COMFY_MATTRESS, "Comfy mattress",
            "Quest complete (One Small Favour) — bed stuffing, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(IRON_OXIDE, "Iron oxide",
            "Quest complete (One Small Favour) — rust for animate rock scroll, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(ANIMATE_ROCK_SCROLL, "Animate rock scroll",
            "Quest complete (One Small Favour) — used to animate rock golem, no use after quest.",
            Quest.ONE_SMALL_FAVOUR)
                .withRequiredQuest2(Quest.KINGS_RANSOM),
        new JunkEntry(BROKEN_VANE_DIRECTIONALS, "Broken weather vane (directionals)",
            "Quest complete (One Small Favour) — part of broken vane, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(DIRECTIONALS_OSF, "Weather vane directionals",
            "Quest complete (One Small Favour) — repaired directionals, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(BROKEN_VANE_ORNAMENT, "Broken weather vane (ornament)",
            "Quest complete (One Small Favour) — part of broken vane, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(ORNAMENT_OSF, "Weather vane ornament",
            "Quest complete (One Small Favour) — repaired ornament, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(BROKEN_VANE_PILLAR, "Broken weather vane (pillar)",
            "Quest complete (One Small Favour) — part of broken vane, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(WEATHERVANE_PILLAR_OSF, "Weather vane pillar",
            "Quest complete (One Small Favour) — repaired pillar, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(WEATHER_REPORT, "Weather report",
            "Quest complete (One Small Favour) — weather forecast paper, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(AIRTIGHT_POT, "Airtight pot",
            "Quest complete (One Small Favour) — pot with fitted lid, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(UNFIRED_POT_LID, "Unfired pot lid",
            "Quest complete (One Small Favour) — unfired lid, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(POT_LID_OSF, "Pot lid",
            "Quest complete (One Small Favour) — fired pot lid, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(BREATHING_SALTS, "Breathing salts",
            "Quest complete (One Small Favour) — reviving salts, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(CHICKEN_CAGE_OSF, "Chicken cage",
            "Quest complete (One Small Favour) — cage to transport chicken, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(SHARPENED_AXE_OSF, "Sharpened axe",
            "Quest complete (One Small Favour) — axe sharpened for quest, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        new JunkEntry(RED_MAHOGANY_LOG, "Red mahogany log",
            "Quest complete (One Small Favour) — special wood, no use after quest.",
            Quest.ONE_SMALL_FAVOUR),
        // --- Plague City (additional) ---
        // Hangover cure gated on Plague City + Skippy and the Mogres
        new JunkEntry(HANGOVER_CURE, "Hangover cure",
            "Quest complete (Plague City / Skippy and the Mogres) — given to Edmond to revive, no use after both.",
            Quest.PLAGUE_CITY)
            .withRequiredQuest2(Quest.SKIPPY_AND_THE_MOGRES),
        new JunkEntry(CHOCOLATEY_MILK, "Chocolatey milk",
            "Quest complete (Skippy and the Mogres / Plague City) — given to Skippy, no use after both.",
            Quest.PLAGUE_CITY)
            .withRequiredQuest2(Quest.SKIPPY_AND_THE_MOGRES),
        // --- Prince Ali Rescue (additional) ---
        new JunkEntry(KEY_PRINT_PAR, "Key print",
            "Quest complete (Prince Ali Rescue) — wax impression of cell key, no use after quest.",
            Quest.PRINCE_ALI_RESCUE),
        // --- Sea Slug (additional) ---
        new JunkEntry(SEA_SLUG_ITEM, "Sea slug",
            "Quest complete (Sea Slug) — sea creature found in pier, no use after quest.",
            Quest.SEA_SLUG),
        new JunkEntry(DAMP_STICKS, "Damp sticks",
            "Quest complete (Sea Slug) — wet fire-lighting material, no use after quest.",
            Quest.SEA_SLUG),
        new JunkEntry(DRY_STICKS, "Dry sticks",
            "Quest complete (Sea Slug) — dried fire-lighting material, no use after quest.",
            Quest.SEA_SLUG),
        // --- Shield of Arrav (additional) ---
        new JunkEntry(PHOENIX_CROSSBOW, "Phoenix crossbow",
            "Quest complete (Shield of Arrav) — item from Phoenix Gang, no use after quest.",
            Quest.SHIELD_OF_ARRAV),
        new JunkEntry(HALF_CERTIFICATE_LEFT, "Half certificate (left)",
            "Quest complete (Shield of Arrav) — half of the reward certificate, no use after quest.",
            Quest.SHIELD_OF_ARRAV),
        new JunkEntry(HALF_CERTIFICATE_RIGHT, "Half certificate (right)",
            "Quest complete (Shield of Arrav) — half of the reward certificate, no use after quest.",
            Quest.SHIELD_OF_ARRAV),
        // --- The Tourist Trap (additional) ---
        new JunkEntry(CELL_DOOR_KEY_TT, "Cell door key",
            "Quest complete (The Tourist Trap) — used to escape slave cell, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(SLAVE_SHIRT, "Slave shirt",
            "Quest complete (The Tourist Trap) — slave disguise, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(SLAVE_ROBE, "Slave robe",
            "Quest complete (The Tourist Trap) — slave disguise, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(SLAVE_BOOTS, "Slave boots",
            "Quest complete (The Tourist Trap) — slave disguise, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(PROTOTYPE_DART, "Prototype dart",
            "Quest complete (The Tourist Trap) — crafted dart, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(TECHNICAL_PLANS_TT, "Technical plans",
            "Quest complete (The Tourist Trap) — stolen plans, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(BEDABIN_KEY, "Bedabin key",
            "Quest complete (The Tourist Trap) — used to unlock chest, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        new JunkEntry(PROTOTYPE_DART_TIP, "Prototype dart tip",
            "Quest complete (The Tourist Trap) — intermediate dart component, no use after quest.",
            Quest.THE_TOURIST_TRAP),
        // --- Watchtower (additional) ---
        new JunkEntry(RELIC_PART_1, "Relic part 1",
            "Quest complete (Watchtower) — part of the Watchtower relic, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(RELIC_PART_2, "Relic part 2",
            "Quest complete (Watchtower) — part of the Watchtower relic, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(RELIC_PART_3, "Relic part 3",
            "Quest complete (Watchtower) — part of the Watchtower relic, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(OGRE_TOOTH, "Ogre tooth",
            "Quest complete (Watchtower) — used in Watchtower ritual, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(TOBAN_KEY, "Toban's key",
            "Quest complete (Watchtower) — used to access Toban's hoard, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(ROCK_CAKE_WT, "Rock cake",
            "Quest complete (Watchtower) — used to lure ogre shaman, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(OLD_ROBE_WT, "Old robe",
            "Quest complete (Watchtower) — shaman disguise, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(UNUSUAL_ARMOUR_WT, "Unusual armour",
            "Quest complete (Watchtower) — shaman disguise, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(DAMAGED_DAGGER_WT, "Damaged dagger",
            "Quest complete (Watchtower) — used to learn about Ogres, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(TATTERED_EYE_PATCH, "Tattered eye patch",
            "Quest complete (Watchtower) — shaman disguise, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(VIAL_JANGERBERRIES, "Vial of jangerberries",
            "Quest complete (Watchtower) — ingredient for ogre potion, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(VIAL_JANGERBERRIES_GUAM, "Vial of jangerberries (guam)",
            "Quest complete (Watchtower) — part-made ogre potion, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(GROUND_BAT_BONES_WT, "Ground bat bones",
            "Quest complete (Watchtower) — ingredient for ogre potion, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(TOBAN_GOLD, "Toban's gold",
            "Quest complete (Watchtower) — looted from Toban, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(MAGIC_OGRE_POTION, "Magic ogre potion",
            "Quest complete (Watchtower) — used to enhance ogre shamans, no use after quest.",
            Quest.WATCHTOWER),
        new JunkEntry(SHAMAN_ROBE_WT, "Shaman robe",
            "Quest complete (Watchtower) — shaman disguise, no use after quest.",
            Quest.WATCHTOWER),
        // --- Waterfall Quest (additional) ---
        // GLARIAL_AMULET removed — required for Roving Elves quest (post-Waterfall Quest).
        new JunkEntry(GLARIAL_URN_FULL, "Glarial's urn (full)",
            "Quest complete (Waterfall Quest) — used in waterfall ritual, no use after quest.",
            Quest.WATERFALL_QUEST),
        new JunkEntry(GLARIAL_URN_EMPTY, "Glarial's urn (empty)",
            "Quest complete (Waterfall Quest) — empty urn, no use after quest.",
            Quest.WATERFALL_QUEST),
        // --- Witch's House (additional) ---
        new JunkEntry(BALL_WITCHS_HOUSE, "Ball",
            "Quest complete (Witch's House) — child's ball, no use after quest.",
            Quest.WITCHS_HOUSE),
        // --- Zogre Flesh Eaters (additional) ---
        new JunkEntry(BLACK_PRISM, "Black prism",
            "Quest complete (Zogre Flesh Eaters) — used to reveal the necromancer's plan, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(TORN_PAGE_ZFE, "Torn page",
            "Quest complete (Zogre Flesh Eaters) — piece of necromancy book, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(RUINED_BACKPACK, "Ruined backpack",
            "Quest complete (Zogre Flesh Eaters) — Zavistic Rarve's pack, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(DRAGON_INN_TANKARD, "Dragon inn tankard",
            "Quest complete (Zogre Flesh Eaters) — used to give Sithik ale, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(SITHIK_PORTRAIT_GOOD, "Portrait of Sithik (good)",
            "Quest complete (Zogre Flesh Eaters) — portrait of Sithik, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(SITHIK_PORTRAIT_BAD, "Portrait of Sithik (bad)",
            "Quest complete (Zogre Flesh Eaters) — portrait of Sithik, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(SIGNED_PORTRAIT, "Signed portrait",
            "Quest complete (Zogre Flesh Eaters) — Sithik's confession portrait, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(BOOK_OF_PORTRAITURE, "Book of portraiture",
            "Quest complete (Zogre Flesh Eaters) — painting reference book, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(OGRE_ARTEFACT, "Ogre artefact",
            "Quest complete (Zogre Flesh Eaters) — artefact from ogre tomb, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),

        new JunkEntry(BOOK_OF_HAM, "Book of 'H.A.M.'",
            "Quest complete (Zogre Flesh Eaters) — used to distract Sithik, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(NECROMANCY_BOOK_ZFE, "Necromancy book",
            "Quest complete (Zogre Flesh Eaters) — necromancer's spell book, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(CUP_OF_TEA_ZFE, "Cup of tea",
            "Quest complete (Zogre Flesh Eaters) — used to calm Zavistic, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS),
        new JunkEntry(OGRE_GATE_KEY, "Ogre gate key",
            "Quest complete (Zogre Flesh Eaters) — used to enter ogre enclave, no use after quest.",
            Quest.ZOGRE_FLESH_EATERS)
    ); }
    private static List<JunkEntry> buildBatch2() { return Arrays.asList(
        // =====================================================================
        // Batch 3 entries — confirmed 2026-06-16
        // =====================================================================
        // --- Enakhra's Lament (additional) ---
        new JunkEntry(CAMEL_MOULD_P, "Camel mould (p)",
            "Quest complete (Enakhra's Lament) — clay mould for stone head, no use after quest.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_HEAD_CAVITY, "Stone head (Cavity)",
            "Quest complete (Enakhra's Lament) — intermediate stone head, placed in pedestal.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_HEAD_LAZIM, "Stone head",
            "Quest complete (Enakhra's Lament) — completed stone head (Lazim variant).",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_HEAD_ZAMORAK, "Stone head",
            "Quest complete (Enakhra's Lament) — completed stone head (Zamorak variant).",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_HEAD_ICTHLARIN, "Stone head",
            "Quest complete (Enakhra's Lament) — completed stone head (Icthlarin variant).",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_HEAD_CAMEL, "Stone head",
            "Quest complete (Enakhra's Lament) — completed stone head (Camel variant).",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(Z_SIGIL, "Z sigil",
            "Quest complete (Enakhra's Lament) — sigil used to unlock temple door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(M_SIGIL, "M sigil",
            "Quest complete (Enakhra's Lament) — sigil used to unlock temple door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(R_SIGIL, "R sigil",
            "Quest complete (Enakhra's Lament) — sigil used to unlock temple door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(K_SIGIL, "K sigil",
            "Quest complete (Enakhra's Lament) — sigil used to unlock temple door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_LEFT_ARM, "Stone left arm",
            "Quest complete (Enakhra's Lament) — from fallen statue; unlocks door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_RIGHT_ARM, "Stone right arm",
            "Quest complete (Enakhra's Lament) — from fallen statue; unlocks door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_LEFT_LEG, "Stone left leg",
            "Quest complete (Enakhra's Lament) — from fallen statue; unlocks door.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(STONE_RIGHT_LEG, "Stone right leg",
            "Quest complete (Enakhra's Lament) — from fallen statue; unlocks door.",
            Quest.ENAKHRAS_LAMENT),
        // --- The Feud (additional) ---
        new JunkEntry(KEYS_FEUD, "Keys",
            "Quest complete (The Feud) — stolen from Khazard mansion safe, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(JEWELS_FEUD, "Jewels",
            "Quest complete (The Feud) — stolen alongside the Keys, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(NOTE_FIBONACCI, "Note (Fibonacci)",
            "Quest complete (The Feud) — coded message from Traitorous Ali, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(NOTE_NUMBERS, "Note (numbers)",
            "Quest complete (The Feud) — companion coded message, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(RECEIPT_FEUD, "Receipt",
            "Quest complete (The Feud) — proof of Ali Morrisane's deal, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(HAGS_POISON, "Hag's poison",
            "Quest complete (The Feud) — brewed to kill Traitorous Ali, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(SNAKE_BASKET, "Snake basket",
            "Quest complete (The Feud) — holds desert snakes, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(SNAKE_BASKET_FULL, "Snake basket (full)",
            "Quest complete (The Feud) — basket with charmed snake, no use after quest.",
            Quest.THE_FEUD),
        new JunkEntry(DESERT_DISGUISE, "Desert disguise",
            "Quest complete (The Feud) — used to enter Pollnivneach, no use after quest.",
            Quest.THE_FEUD),
        // Snake charm: used in The Feud + Ratcatchers — junk after both
        new JunkEntry(SNAKE_CHARM, "Snake charm",
            "Used in The Feud and Ratcatchers — junk only after both quests are complete.",
            Quest.THE_FEUD)
            .withRequiredQuest2(Quest.RATCATCHERS),
        // Red hot sauce: used in The Feud + My Arm's Big Adventure — junk after both
        new JunkEntry(RED_HOT_SAUCE, "Red hot sauce",
            "Used in The Feud and My Arm's Big Adventure — junk only after both quests are complete.",
            Quest.THE_FEUD)
            .withRequiredQuest2(Quest.MY_ARMS_BIG_ADVENTURE),
        // --- Song of the Elves (additional) ---
        new JunkEntry(HAND_MIRROR_SOTE, "Hand mirror",
            "Quest complete (Song of the Elves) — used in light puzzle, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(RED_CRYSTAL_SOTE, "Red crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(YELLOW_CRYSTAL_SOTE, "Yellow crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(GREEN_CRYSTAL_SOTE, "Green crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CYAN_CRYSTAL_SOTE, "Cyan crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(BLUE_CRYSTAL_SOTE, "Blue crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(MAGENTA_CRYSTAL_SOTE, "Magenta crystal",
            "Quest complete (Song of the Elves) — light puzzle piece, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(FRACTURED_CRYSTAL_SOTE, "Fractured crystal",
            "Quest complete (Song of the Elves) — splits light beam, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(BLUE_LIQUID_SOTE, "Blue liquid",
            "Quest complete (Song of the Elves) — wrong item from Elena's cabinet, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(GREEN_POWDER_SOTE, "Green powder",
            "Quest complete (Song of the Elves) — wrong item from Elena's cabinet, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(RED_POWDER_SOTE, "Red powder",
            "Quest complete (Song of the Elves) — wrong item from Elena's cabinet, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CLEAR_LIQUID_SOTE, "Clear liquid",
            "Quest complete (Song of the Elves) — sulphuric acid from Elena's cabinet, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ELDER_CADANTINE_SOTE, "Elder cadantine",
            "Quest complete (Song of the Elves) — used to make inversion potion, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ELDER_CADANTINE_UNF_SOTE, "Elder cadantine potion (unf)",
            "Quest complete (Song of the Elves) — intermediate inversion potion, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CRYSTAL_SOTE, "Crystal",
            "Quest complete (Song of the Elves) — ground into crystal dust, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CRYSTAL_DUST_SOTE, "Crystal dust",
            "Quest complete (Song of the Elves) — added to potion, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(INVERSION_POTION, "Inversion potion",
            "Quest complete (Song of the Elves) — used to enter seed-state Prifddinas, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CRYSTAL_SEED_INERT_SOTE, "Crystal seed",
            "Quest complete (Song of the Elves) — inert variant, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CRYSTAL_SEED_ENCHANTED_SOTE, "Crystal seed",
            "Quest complete (Song of the Elves) — enchanted variant used to locate Lord Amlodd, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ORB_OF_LIGHT_SOTE, "Orb of light",
            "Quest complete (Song of the Elves) — unlocks Seren's dark essence, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CLUE_SCROLL_SOTE_1, "Clue scroll",
            "Quest complete (Song of the Elves) — quest-specific clue scroll, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CLUE_SCROLL_SOTE_2, "Clue scroll",
            "Quest complete (Song of the Elves) — quest-specific clue scroll, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CLUE_SCROLL_SOTE_3, "Clue scroll",
            "Quest complete (Song of the Elves) — quest-specific clue scroll, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(CLUE_SCROLL_SOTE_4, "Clue scroll",
            "Quest complete (Song of the Elves) — quest-specific clue scroll, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(EXPLOSIVE_POTION_SOTE, "Explosive potion",
            "Quest complete (Song of the Elves) — used to reinforce Dwarf Camp, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ODE_TO_ETERNITY, "Ode to eternity",
            "Quest complete (Song of the Elves) — book used in quest, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ARDOUGNE_KNIGHT_HELM, "Ardougne knight helm",
            "Quest complete (Song of the Elves) — Ardougne knight disguise, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ARDOUGNE_KNIGHT_PLATEBODY, "Ardougne knight platebody",
            "Quest complete (Song of the Elves) — Ardougne knight disguise, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ARDOUGNE_KNIGHT_PLATELEGS, "Ardougne knight platelegs",
            "Quest complete (Song of the Elves) — Ardougne knight disguise, no use after quest.",
            Quest.SONG_OF_THE_ELVES),
        new JunkEntry(ARDOUGNE_KNIGHT_TABARD, "Ardougne knight tabard",
            "Quest complete (Song of the Elves) — costume piece, no functional use after quest.",
            Quest.SONG_OF_THE_ELVES),
        // Colour wheel — junk only after BOTH Song of the Elves AND Mourning's End Part II are complete
        new JunkEntry(COLOUR_WHEEL_SOTE, "Colour wheel",
            "Junk after both Song of the Elves and Mourning's End Part II — used in light puzzles in both quests.",
            Quest.SONG_OF_THE_ELVES)
            .withRequiredQuest2(Quest.MOURNINGS_END_PART_II),
        // --- Haunted Mine (additional) ---
        new JunkEntry(DAMP_TINDERBOX, "Damp tinderbox",
            "Quest complete (Haunted Mine) — non-functional tinderbox found in mine, no use after quest.",
            Quest.HAUNTED_MINE),
        new JunkEntry(ZEALOTS_KEY, "Zealot's key",
            "Quest complete (Haunted Mine) — used to activate mine lift, no use after quest.",
            Quest.HAUNTED_MINE),
        // --- Underground Pass (additional, user-confirmed IDs) ---
        new JunkEntry(OLD_JOURNAL_UP, "Old journal",
            "Quest complete (Underground Pass) — lore journal near Well of Iban, no use after quest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(HISTORY_OF_IBAN, "History of iban",
            "Quest complete (Underground Pass) — lore book from Kardia's chest, no use after quest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(PIECE_OF_RAILING, "Piece of railing",
            "Quest complete (Underground Pass) — used to dislodge boulder, no use after quest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(OILY_CLOTH, "Oily cloth",
            "Quest complete (Underground Pass) — used to make fire arrows, no use after quest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(DOLL_OF_IBAN, "Doll of iban",
            "Quest complete (Underground Pass) — thrown into Well of the Damned to complete quest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(AMULET_OF_DOOMION, "Amulet of doomion",
            "Quest complete (Underground Pass) — dropped by Doomion demon; placed in chest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(AMULET_OF_HOLTHION, "Amulet of holthion",
            "Quest complete (Underground Pass) — dropped by Holthion demon; placed in chest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(AMULET_OF_OTHANIAN, "Amulet of othanian",
            "Quest complete (Underground Pass) — dropped by Othainian demon; placed in chest.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(PALADIN_BADGE_CARL, "Paladin's badge (Sir Carl)",
            "Quest complete (Underground Pass) — placed in well to open Doors of Iban.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(PALADIN_BADGE_HARRY, "Paladin's badge (Sir Harry)",
            "Quest complete (Underground Pass) — placed in well to open Doors of Iban.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(PALADIN_BADGE_JERRO, "Paladin's badge (Sir Jerro)",
            "Quest complete (Underground Pass) — placed in well to open Doors of Iban.",
            Quest.UNDERGROUND_PASS),
        new JunkEntry(DWARF_BREW, "Dwarf brew",
            "Quest complete (Underground Pass) — poured on Iban's tomb and lit; no use after quest.",
            Quest.UNDERGROUND_PASS),
        // --- While Guthix Sleeps (user-confirmed IDs) ---
        new JunkEntry(DIRTY_SHIRT_WGS, "Dirty shirt",
            "Quest complete (While Guthix Sleeps) — used on Broav to track Movario's scent.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(RUBY_KEY_WGS, "Ruby key",
            "Quest complete (While Guthix Sleeps) — opens Movario's bookcase and bed chest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(NOTES_ON_PRESSURE, "Notes on pressure",
            "Quest complete (While Guthix Sleeps) — weight puzzle hint, no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(MOVARIOS_NOTES_VOL1, "Movario's notes (volume 1)",
            "Quest complete (While Guthix Sleeps) — stolen from desk; handed to Thaerisk.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(MOVARIOS_NOTES_VOL2, "Movario's notes (volume 2)",
            "Quest complete (While Guthix Sleeps) — found in bed chest; handed to Thaerisk.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ENRICHED_SNAPDRAGON, "Enriched snapdragon",
            "Quest complete (While Guthix Sleeps) — combined with truth serum; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(SUPER_TRUTH_SERUM, "Super truth serum",
            "Quest complete (While Guthix Sleeps) — used on captured spy; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(TRUTH_SERUM_WGS, "Truth serum",
            "Quest complete (While Guthix Sleeps) — combined with enriched snapdragon; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(SUSPECT_SKETCH, "Suspect sketch",
            "Quest complete (While Guthix Sleeps) — auto-created after interrogation; given to Idria.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(CELL_KEY_WGS, "Cell key",
            "Quest complete (While Guthix Sleeps) — opens Silif's cell; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(STRANGE_TELEORB, "Strange teleorb",
            "Quest complete (While Guthix Sleeps) — found in Dark Squall's base; teleports to Lucien's camp.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(TELEORB_AKRISAE, "Teleorb",
            "Quest complete (While Guthix Sleeps) — given by Akrisae; planted on spy to teleport them.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(TELEORB_SILIF, "Teleorb",
            "Quest complete (While Guthix Sleeps) — Silif's variant teleorb; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ENRICHED_SNAPDRAGON_SEED, "Enriched snapdragon seed",
            "Quest complete (While Guthix Sleeps) — created at Betty's; planted on castle roof.",
            Quest.WHILE_GUTHIX_SLEEPS),
        // Rose-tinted lens — junk only after BOTH While Guthix Sleeps AND The Hand in the Sand are complete
        new JunkEntry(ROSE_TINTED_LENS, "Rose-tinted lens",
            "Junk after both While Guthix Sleeps and The Hand in the Sand — lantern lens dyed pink to enrich snapdragon seed.",
            Quest.WHILE_GUTHIX_SLEEPS)
            .withRequiredQuest2(Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(ARMADYL_COMMUNIQUE, "Armadyl communiqué",
            "Quest complete (While Guthix Sleeps) — found in Movario's base; given to Idria.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(UNCONSCIOUS_BROAV, "Unconscious broav",
            "Quest complete (While Guthix Sleeps) — used to track Movario; broav wakes when Movario leaves.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(WASTE_PAPER_BASKET, "Waste-paper basket",
            "Quest complete (While Guthix Sleeps) — found in Movario's base; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(WEIGHT_1KG, "Weight (1kg)",
            "Quest complete (While Guthix Sleeps) — used in weight pressure puzzle.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(WEIGHT_2KG, "Weight (2kg)",
            "Quest complete (While Guthix Sleeps) — used in weight pressure puzzle.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(WEIGHT_5KG, "Weight (5kg)",
            "Quest complete (While Guthix Sleeps) — used in weight pressure puzzle.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(AGILITY_DOLMEN, "Agility dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ENERGY_DOLMEN, "Energy dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(RESTORATION_DOLMEN, "Restoration dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ATTACK_DOLMEN, "Attack dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(STRENGTH_DOLMEN, "Strength dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(DEFENCE_DOLMEN, "Defence dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(COMBAT_DOLMEN, "Combat dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(RANGED_DOLMEN, "Ranged dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(PRAYER_DOLMEN, "Prayer dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(HUNTER_DOLMEN, "Hunter dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(FISHING_DOLMEN, "Fishing dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(MAGIC_DOLMEN, "Magic dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(BALANCE_DOLMEN, "Balance dolmen",
            "Quest complete (While Guthix Sleeps) — Stone of Jas fragment; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(AIR_BLOCK_WGS, "Air block",
            "Quest complete (While Guthix Sleeps) — puzzle block for elemental chamber.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(EARTH_BLOCK_WGS, "Earth block",
            "Quest complete (While Guthix Sleeps) — puzzle block for elemental chamber.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(FIRE_BLOCK_WGS, "Fire block",
            "Quest complete (While Guthix Sleeps) — puzzle block for elemental chamber.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(WATER_BLOCK_WGS, "Water block",
            "Quest complete (While Guthix Sleeps) — puzzle block for elemental chamber.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(VINE_FLOWER, "Vine flower",
            "Quest complete (While Guthix Sleeps) — used to make truth serum; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(GRIMY_NOTE_WGS, "Grimy note",
            "Quest complete (While Guthix Sleeps) — found in Movario's base; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(SILIF_ITEM, "Silif (item)",
            "Quest complete (While Guthix Sleeps) — Silif in item form; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(LIT_EXPLOSIVE, "Lit explosive",
            "Quest complete (While Guthix Sleeps) — used to destroy Guthix's resting chamber door.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(DURADELS_NOTES, "Duradel's notes",
            "Quest complete (While Guthix Sleeps) — found in Azzanadra's pyramid; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(BROAV, "Broav",
            "Quest complete (While Guthix Sleeps) — trained broav used to track Movario's scent; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(DARK_SQUALL_HOOD, "Dark squall hood",
            "Quest complete (While Guthix Sleeps) — Dark Squall disguise from wardrobe; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(DARK_SQUALL_ROBE_BOTTOM, "Dark squall robe bottom",
            "Quest complete (While Guthix Sleeps) — Dark Squall disguise from wardrobe; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(DARK_SQUALL_ROBE_TOP, "Dark squall robe top",
            "Quest complete (While Guthix Sleeps) — Dark Squall disguise from wardrobe; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ELITE_BLACK_FULL_HELM, "Elite black full helm",
            "Quest complete (While Guthix Sleeps) — elite black knight armour; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ELITE_BLACK_PLATEBODY, "Elite black platebody",
            "Quest complete (While Guthix Sleeps) — elite black knight armour; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        new JunkEntry(ELITE_BLACK_PLATELEGS, "Elite black platelegs",
            "Quest complete (While Guthix Sleeps) — elite black knight armour; no use after quest.",
            Quest.WHILE_GUTHIX_SLEEPS),
        // --- Heroes' Quest ---
        new JunkEntry(PETES_CANDLESTICK, "Pete's candlestick",
            "Quest complete (Heroes' Quest) — returned to gang leader for thieves' armband.",
            Quest.HEROES_QUEST),
        new JunkEntry(THIEVES_ARMBAND, "Thieves' armband",
            "Quest complete (Heroes' Quest) — one of three items submitted to Achietties.",
            Quest.HEROES_QUEST),
        new JunkEntry(BLAMISH_SNAIL_SLIME, "Blamish snail slime",
            "Quest complete (Heroes' Quest) — combined with fishing rod to make blamish oil.",
            Quest.HEROES_QUEST),
        new JunkEntry(BLAMISH_OIL, "Blamish oil",
            "Quest complete (Heroes' Quest) — applied to fishing rod to catch lava eels.",
            Quest.HEROES_QUEST),
        new JunkEntry(FIRE_FEATHER, "Fire feather",
            "Quest complete (Heroes' Quest) — dropped by Entrana firebird; one of three items for Achietties.",
            Quest.HEROES_QUEST),
        new JunkEntry(ID_PAPERS, "Id papers",
            "Quest complete (Heroes' Quest) — Black Arm Gang identity papers for Pete's mansion.",
            Quest.HEROES_QUEST),
        new JunkEntry(MISCELLANEOUS_KEY, "Miscellaneous key",
            "Quest complete (Heroes' Quest) — opens windowed room so partner can range Grip.",
            Quest.HEROES_QUEST),
        new JunkEntry(GRIPS_KEYRING, "Grip's keyring",
            "Quest complete (Heroes' Quest) — dropped by Grip; unlocks treasure room.",
            Quest.HEROES_QUEST),
        new JunkEntry(JAIL_KEY_HQ, "Jail key",
            "Quest complete (Heroes' Quest) — frees Velrak; grants lava eel fishing access.",
            Quest.HEROES_QUEST),
        // --- Lunar Diplomacy ---
        new JunkEntry(EMERALD_LANTERN_EMPTY, "Emerald lantern",
            "Quest complete (Lunar Diplomacy) — empty lantern variant, no use after quest.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(EMERALD_LANTERN_UNLIT, "Emerald lantern",
            "Quest complete (Lunar Diplomacy) — unlit lantern variant, no use after quest.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(EMERALD_LANTERN_LIT, "Emerald lantern",
            "Quest complete (Lunar Diplomacy) — lit lantern; used for Dreamland access.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(EMERALD_LENS, "Emerald lens",
            "Quest complete (Lunar Diplomacy) — fitted into bullseye lantern, no use after quest.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(VIAL_OF_WATER_LD, "Vial of water",
            "Quest complete (Lunar Diplomacy) — quest-specific water vial, no use after quest.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(WAKING_SLEEP_VIAL, "Waking sleep vial",
            "Quest complete (Lunar Diplomacy) — poured onto kindling for Dream World ceremony.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(KINDLING_LD, "Kindling",
            "Quest complete (Lunar Diplomacy) — soaked with waking sleep vial for ceremony.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(SOAKED_KINDLING, "Soaked kindling",
            "Quest complete (Lunar Diplomacy) — placed on ceremonial brazier; transports to Dream World.",
            Quest.LUNAR_DIPLOMACY),
        new JunkEntry(A_SPECIAL_TIARA, "A special tiara",
            "Quest complete (Lunar Diplomacy) — dropped by Suqah; returned to Meteora for lunar amulet.",
            Quest.LUNAR_DIPLOMACY),
        // --- Mountain Daughter ---
        new JunkEntry(SAFETY_GUARANTEE, "Safety guarantee",
            "Quest complete (Mountain Daughter) — given by Brundt; allows Svidi safe passage.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(WHITE_PEARL, "White pearl",
            "Quest complete (Mountain Daughter) — picked from thorny bushes; eaten to get seed.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(WHITE_PEARL_SEED, "White pearl seed",
            "Quest complete (Mountain Daughter) — from eating white pearl; given to Jokul.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(HALF_A_ROCK, "Half a rock",
            "Quest complete (Mountain Daughter) — from Ancient Rock; given to Brundt.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(CORPSE_OF_WOMAN, "Corpse of woman",
            "Quest complete (Mountain Daughter) — Asleif's body retrieved; shown to Hamal.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(ASLEIFS_NECKLACE, "Asleif's necklace",
            "Quest complete (Mountain Daughter) — buried alongside the corpse to complete burial.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(MUD_MD, "Mud",
            "Quest complete (Mountain Daughter) — applied to stepping stones.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(MUDDY_ROCK, "Muddy rock",
            "Quest complete (Mountain Daughter) — five placed on burial mound to complete quest.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(POLE_MD, "Pole",
            "Quest complete (Mountain Daughter) — vaults player across lake stones; breaks during use.",
            Quest.MOUNTAIN_DAUGHTER),
        new JunkEntry(BROKEN_POLE_MD, "Broken pole",
            "Quest complete (Mountain Daughter) — broken pole after crossing; no use after quest.",
            Quest.MOUNTAIN_DAUGHTER),
        // --- Ratcatchers ---
        new JunkEntry(DIRECTIONS_RC, "Directions",
            "Quest complete (Ratcatchers) — location of next quest contact, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(POISONED_CHEESE, "Poisoned cheese",
            "Quest complete (Ratcatchers) — placed in rat holes in Varrock warehouse.",
            Quest.RATCATCHERS),
        new JunkEntry(MUSIC_SCROLL_RC, "Music scroll",
            "Quest complete (Ratcatchers) — notes for snake charm in Meiyerditch section.",
            Quest.RATCATCHERS),
        new JunkEntry(CAT_ANTIPOISON, "Cat antipoison",
            "Quest complete (Ratcatchers) — cures your cat after poisoning; no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(POT_OF_WEEDS, "Pot of weeds",
            "Quest complete (Ratcatchers) — lit to create smouldering pot.",
            Quest.RATCATCHERS),
        new JunkEntry(SMOULDERING_POT, "Smouldering pot",
            "Quest complete (Ratcatchers) — smokes rats out of holes.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_EMPTY, "Rat pole",
            "Quest complete (Ratcatchers) — empty rat pole, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_ONE, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 1 rat, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_TWO, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 2 rats, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_THREE, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 3 rats, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_FOUR, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 4 rats, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_FIVE, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 5 rats, no use after quest.",
            Quest.RATCATCHERS),
        new JunkEntry(RAT_POLE_SIX, "Rat pole",
            "Quest complete (Ratcatchers) — rat pole with 6 rats, no use after quest.",
            Quest.RATCATCHERS),
        // Rat poison: used in Ratcatchers + Clock Tower — junk after both
        new JunkEntry(RAT_POISON, "Rat poison",
            "Used in Ratcatchers and Clock Tower — junk only after both quests are complete.",
            Quest.RATCATCHERS)
            .withRequiredQuest2(Quest.CLOCK_TOWER),
        // --- Cold War ---
        new JunkEntry(PENGUIN_BONGOS, "Penguin bongos",
            "Quest complete (Cold War) — crafted clockwork percussion, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(COWBELLS, "Cowbells",
            "Quest complete (Cold War) — from Larry's tent; attracts penguins, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(CLOCKWORK_BOOK, "Clockwork book",
            "Quest complete (Cold War) — describes how to build clockwork suits, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(MISSION_REPORT_ARDOUGNE, "Mission report (Ardougne)",
            "Quest complete (Cold War) — spy report, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(MISSION_REPORT_LUMBRIDGE, "Mission report (Lumbridge)",
            "Quest complete (Cold War) — spy report, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(MISSION_REPORT_FAKE, "Mission report (Fake)",
            "Quest complete (Cold War) — decoy report substituted for a real one, no use after quest.",
            Quest.COLD_WAR),
        new JunkEntry(KGP_ID_CARD, "KGP ID card",
            "Quest complete (Cold War) — identity card for KGP HQ infiltration, no use after quest.",
            Quest.COLD_WAR),
        // --- Tower of Life ---
        new JunkEntry(TRIANGLE_SANDWICH, "Triangle sandwich",
            "Quest complete (Tower of Life) — bribe to worker Bonafido, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(RIVETS_TOL, "Rivets",
            "Quest complete (Tower of Life) — fastens tower components, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(BINDING_FLUID, "Binding fluid",
            "Quest complete (Tower of Life) — attaches pipe rings, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(PIPE_TOL, "Pipe",
            "Quest complete (Tower of Life) — tower pipe section, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(PIPE_RING, "Pipe ring",
            "Quest complete (Tower of Life) — joins pipe sections, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(METAL_SHEET_TOL, "Metal sheet",
            "Quest complete (Tower of Life) — tower construction material, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(COLOURED_BALL, "Coloured ball",
            "Quest complete (Tower of Life) — tower construction piece, no use after quest.",
            Quest.TOWER_OF_LIFE),
        new JunkEntry(VALVE_WHEEL, "Valve wheel",
            "Quest complete (Tower of Life) — installed in the tower, no use after quest.",
            Quest.TOWER_OF_LIFE),
        // --- Garden of Tranquillity ---
        new JunkEntry(RUNE_SHARDS, "Rune shards",
            "Quest complete (Garden of Tranquillity) — crushed rune stones for plant cure.",
            Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(RUNE_DUST, "Rune dust",
            "Quest complete (Garden of Tranquillity) — dust from rune stones for plant cure.",
            Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(PLANT_CURE_GOT, "Plant cure",
            "Quest complete (Garden of Tranquillity) — strengthened cure for diseased statue plants.",
            Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(WHITE_TREE_FRUIT, "White tree fruit",
            "Quest complete (Garden of Tranquillity) — grown on the white tree; no use after quest.",
            Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(LIST_GOT, "List",
            "Quest complete (Garden of Tranquillity) — Queen Ellamaria's plant list; no use after quest.",
            Quest.GARDEN_OF_TRANQUILLITY),
        // Ring of charos: YELLOW — only flagged when Ring of charos (a) is in bank (upgrade confirmed).
        new JunkEntry(RING_OF_CHAROS, "Ring of charos", JunkTier.YELLOW,
            "Superseded by Ring of charos (a) — only flagged after Garden of Tranquillity is complete<br>"
            + "AND Ring of charos (a) is in the bank.",
            Quest.GARDEN_OF_TRANQUILLITY, new int[]{RING_OF_CHAROS_A}),
        // --- The Curse of Arrav ---
        new JunkEntry(BASE_KEY_COA, "Base key",
            "Quest complete (The Curse of Arrav) — key used to access the vault; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(BASE_PLANS_COA, "Base plans",
            "Quest complete (The Curse of Arrav) — vault blueprints; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(CANOPIC_JAR_COA_OIL, "Canopic jar (oil)",
            "Quest complete (The Curse of Arrav) — partially prepared canopic jar; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(CANOPIC_JAR_COA_OIL_BERRIES, "Canopic jar (oil and berries)",
            "Quest complete (The Curse of Arrav) — partially prepared canopic jar; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(CANOPIC_JAR_COA_FULL, "Canopic jar (full)",
            "Quest complete (The Curse of Arrav) — fully prepared canopic jar; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(CODE_KEY_COA, "Code key",
            "Quest complete (The Curse of Arrav) — key used with the cipher; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(DECODER_STRIPS, "Decoder strips",
            "Quest complete (The Curse of Arrav) — strips used to decode the Arrav cipher; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(ELIAS_MESSAGE, "Elias' message",
            "Quest complete (The Curse of Arrav) — message from Elias; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(GRANITE_TABLET_COA, "Granite tablet",
            "Quest complete (The Curse of Arrav) — ritual tablet from the Mahjarrat ritual site; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(MAHJARRAT_NOTES_AJ, "Mahjarrat notes (a-j)",
            "Quest complete (The Curse of Arrav) — research notes on Mahjarrat (a–j); no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(MAHJARRAT_NOTES_KZ, "Mahjarrat notes (k-z)",
            "Quest complete (The Curse of Arrav) — research notes on Mahjarrat (k–z); no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(MASTABA_KEY_NORTH, "Mastaba key (north lever)",
            "Quest complete (The Curse of Arrav) — north lever key for the mastaba; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(MASTABA_KEY_SOUTH, "Mastaba key (south lever)",
            "Quest complete (The Curse of Arrav) — south lever key for the mastaba; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(SHALE_TABLET_COA, "Shale tablet",
            "Quest complete (The Curse of Arrav) — ritual tablet from the Mahjarrat ritual site; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(SLATE_TABLET_COA, "Slate tablet",
            "Quest complete (The Curse of Arrav) — ritual tablet from the Mahjarrat ritual site; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        new JunkEntry(STONE_TABLET_COA, "Stone tablet (The Curse of Arrav)",
            "Quest complete (The Curse of Arrav) — Mahjarrat ritual stone tablet; no use after quest.",
            Quest.THE_CURSE_OF_ARRAV),
        // --- What Lies Below ---
        new JunkEntry(DAGONHAI_HISTORY, "Dagon'hai history",
            "Quest complete (What Lies Below) — book found in Tunnel of Chaos, no use after quest.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(SINKETHS_DIARY, "Sin'keth's diary",
            "Quest complete (What Lies Below) — diary found with the wand, no use after quest.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(EMPTY_FOLDER_WLB, "An empty folder",
            "Quest complete (What Lies Below) — used to collect outlaw papers; no use after quest.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(USED_FOLDER_WLB, "Used folder",
            "Quest complete (What Lies Below) — intermediate folder state; no use after quest.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(FULL_FOLDER_WLB, "Full folder",
            "Quest complete (What Lies Below) — 5 papers collected and handed to Rat.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(RATS_PAPER, "Rat's paper",
            "Quest complete (What Lies Below) — dropped by outlaws; 5 needed for folder.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(LETTER_TO_SUROK, "Letter to surok",
            "Quest complete (What Lies Below) — delivered to Surok as infiltration step.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(SUROKS_LETTER, "Surok's letter",
            "Quest complete (What Lies Below) — delivered back to Rat to reveal his plot.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(ZAFFS_INSTRUCTIONS, "Zaff's instructions",
            "Quest complete (What Lies Below) — explains how to operate beacon ring; no use after quest.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(WAND_WLB, "Wand",
            "Quest complete (What Lies Below) — from Surok; infused at Chaos Altar and returned to him.",
            Quest.WHAT_LIES_BELOW),
        new JunkEntry(INFUSED_WAND, "Infused wand",
            "Quest complete (What Lies Below) — wand after chaos infusion; given to Surok.",
            Quest.WHAT_LIES_BELOW),
        // --- Hazeel Cult ---
        new JunkEntry(HAZEEL_SCROLL, "Hazeel scroll",
            "Quest complete (Hazeel Cult) — handed to Alomone to resurrect Hazeel (evil path).",
            Quest.HAZEEL_CULT),
        new JunkEntry(CHEST_KEY_HAZEEL, "Chest key",
            "Quest complete (Hazeel Cult) — opens secret room chest in Carnillean Mansion (evil path).",
            Quest.HAZEEL_CULT),
        // --- Tribal Totem ---
        new JunkEntry(ADDRESS_LABEL, "Address label",
            "Quest complete (Tribal Totem) — redirects GPDT crate delivery to Handelmort's Mansion.",
            Quest.TRIBAL_TOTEM),
        new JunkEntry(TOTEM_TT, "Totem",
            "Quest complete (Tribal Totem) — delivered to Kangai Mau at quest end.",
            Quest.TRIBAL_TOTEM),
        new JunkEntry(GUIDE_BOOK_TT, "Guide book",
            "Quest complete (Tribal Totem) — contains Lord Handelmort's middle name (KURT) for door.",
            Quest.TRIBAL_TOTEM),
        // --- Holy Grail ---
        new JunkEntry(HOLY_TABLE_NAPKIN, "Holy table napkin",
            "Quest complete (Holy Grail) — required to see magic whistle spawn; no use after quest.",
            Quest.HOLY_GRAIL),
        new JunkEntry(MAGIC_WHISTLE, "Magic whistle",
            "Quest complete (Holy Grail) — blown to enter Fisher Realm; no use after quest.",
            Quest.HOLY_GRAIL),
        new JunkEntry(GRAIL_BELL, "Grail bell",
            "Quest complete (Holy Grail) — rung outside Fisher Realm castle; no post-quest function.",
            Quest.HOLY_GRAIL),
        new JunkEntry(MAGIC_GOLD_FEATHER, "Magic gold feather",
            "Quest complete (Holy Grail) — blown to locate Sir Percival; no use after quest.",
            Quest.HOLY_GRAIL),
        new JunkEntry(HOLY_GRAIL_ITEM, "Holy grail",
            "Quest complete (Holy Grail) — handed to King Arthur at quest end.",
            Quest.HOLY_GRAIL)
                .withRequiredQuest2(Quest.KINGS_RANSOM),
        // ---- Merlin's Crystal / Troll Romance ----------------------------------------
        // Bucket of wax and black candle are needed for both Merlin's Crystal and
        // Troll Romance — gated on the later of the two quests.
        // Quest.MERLINS_CRYSTAL / Quest.TROLL_ROMANCE — confirmed
        new JunkEntry(BUCKET_OF_WAX, "Bucket of wax",
            "Quest complete (Merlin's Crystal / Troll Romance) — used to seal magical barrier; no use after both quests.",
            Quest.MERLINS_CRYSTAL)
            .withRequiredQuest2(Quest.TROLL_ROMANCE),
        new JunkEntry(BLACK_CANDLE_LIT, "Black candle (lit)",
            "Quest complete (Merlin's Crystal / Troll Romance) — used in séance; no use after both quests.",
            Quest.MERLINS_CRYSTAL),
        new JunkEntry(BLACK_CANDLE_UNLIT, "Black candle (unlit)",
            "Quest complete (Merlin's Crystal / Troll Romance) — used in séance; no use after both quests.",
            Quest.MERLINS_CRYSTAL),
        // ---- Mourning's End Part I (additional) --------------------------------
        // Quest.MOURNINGS_END_PART_I — confirmed
        // Rotten apple already gated on BIOHAZARD + MOURNINGS_END_PART_I above.
        // Mourner outfit (6065/6067/6068/6069/6070) and Gas mask (1506) are in
        // POH_MAGIC_WARDROBE_ITEMS — covered as POH_STORAGE, not flagged here.
        new JunkEntry(BLOODY_MOURNER_TOP, "Bloody mourner top",
            "Quest complete (Mourning's End Part I) — damaged disguise top, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(RIPPED_MOURNER_TROUSERS, "Ripped mourner trousers",
            "Quest complete (Mourning's End Part I) — damaged disguise trousers, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(MOURNER_LETTER, "Mourner letter",
            "Quest complete (Mourning's End Part I) — letter used to infiltrate mourner base, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(TARNISHED_KEY, "Tarnished key",
            "Quest complete (Mourning's End Part I) — opens mourner HQ door, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(BROKEN_DEVICE, "Broken device",
            "Quest complete (Mourning's End Part I) — intermediate crafting state for fixed device, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(FIXED_DEVICE, "Fixed device",
            "Quest complete (Mourning's End Part I / Song of the Elves) — used to poison the tainted crystal; no use after both quests.",
            Quest.MOURNINGS_END_PART_I)
            .withRequiredQuest2(Quest.SONG_OF_THE_ELVES),
        new JunkEntry(TEGIDS_SOAP, "Tegid's soap",
            "Quest complete (Mourning's End Part I) — obtained from Tegid's laundry, used to clean outfit, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(SIEVE, "Sieve",
            "Quest complete (Mourning's End Part I) — used to filter naphtha, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(APPLE_BARREL, "Apple barrel",
            "Quest complete (Mourning's End Part I) — used to make naphtha apple mix, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(NAPHTHA_APPLE_MIX, "Naphtha apple mix",
            "Quest complete (Mourning's End Part I) — intermediate step to make toxic naphtha, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(TOXIC_NAPHTHA, "Toxic naphtha",
            "Quest complete (Mourning's End Part I) — used to make toxic powder, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(TOXIC_POWDER, "Toxic powder",
            "Quest complete (Mourning's End Part I) — used to poison apple barrel, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        // --- The Blood Moon Rises ---
        // Activated July 11, 2026 — Quest.THE_BLOOD_MOON_RISES (id 16414) confirmed present in local RuneLite.
        new JunkEntry(TBMR_ANCIENT_SHIELD, "Ancient shield", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ANCIENT_SYMBOL, "Ancient symbol", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_BATTLEAXE, "Battleaxe", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_GRID_NOTE, "Grid note", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_HALLOWED_MARKS, "Hallowed marks", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_LAB_NOTES, "Lab notes", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_LOCKBOX, "Lockbox", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_MACE, "Mace", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SPEAR, "Spear", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SWORD, "Sword", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SQUIRE_JOURNAL, "Squire's journal", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FROM_MISTHALIN, "From Misthalin to Morytania", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SARL_JOURNAL, "Sarl's journal", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SCRUFFY_NOTEBOOK, "Scruffy notebook", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_PIOUS_PROCEEDINGS, "Pious proceedings", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_LIFE_OF_FRIAR, "The Life of Friar", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ESSIANDAR_NOTES, "Essiandar's notes", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_IVANDIS_WRITINGS, "Ivandis' writings", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_GILDED_BOOK, "Gilded book", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_VAMPYRE_BOOK, "Vampyre book", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ROTTEN_DIARY, "Rotten diary", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_DRAKAN_EMBLEM, "Drakan emblem", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_LARGE_CLOCK_HAND, "Large clock hand", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SMALL_CLOCK_HAND, "Small clock hand", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_POEM_SCROLL, "Poem scroll", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_EXPLOSIVE_BARREL, "Explosive barrel", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_BROKEN_PIPE, "Broken pipe", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SHARP_KNIFE, "Sharp knife", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_TONGS, "Tongs", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_VENATOR_STOMACH, "Venator stomach", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SYRINGE_BARREL, "Syringe barrel", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SYRINGE_NEEDLE, "Syringe needle", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SYRINGE_PLUNGER, "Syringe plunger", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_EMPTY_SYRINGE, "Empty syringe", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FULL_SYRINGE, "Full syringe", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_LEFT_CREST_HALF, "Left crest half", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RIGHT_CREST_HALF, "Right crest half", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_HALF_MOON_KEY, "Half moon key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_CRESCENT_MOON_KEY, "Crescent moon key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_GIBBOUS_MOON_KEY, "Gibbous moon key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FULL_MOON_KEY, "Full moon key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_GILDED_KEY, "Gilded key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SOLID_KEY, "Solid key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_VITUR_KEY, "Vitur key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_MYRMEL_KEY, "Myrmel key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SHADUM_KEY, "Shadum key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_TRAPDOOR_KEY, "Trapdoor key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_JOVKAI_KEY, "Jovkai key", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ANCESTRAL_DAGGER, "Ancestral dagger", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_MYSTERIOUS_BOOK, "Mysterious book", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FANCY_GEM_1, "Fancy gem", "Quest complete (The Blood Moon Rises) — gem #1.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FANCY_GEM_2, "Fancy gem", "Quest complete (The Blood Moon Rises) — gem #2.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ORNATE_SKULL, "Ornate skull", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ORNATE_KNIFE, "Ornate knife", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_ORNATE_HOURGLASS, "Ornate hourglass", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_CHEMICAL_VIAL, "Chemical vial", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_THICK_RED_POTION, "Thick red potion", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_COLD_BLUISH_WHITE_POTION, "Cold bluish-white potion", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_CLOUDY_GREY_POTION, "Cloudy grey potion", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_WEIGHTLESS_BLACK_POTION, "Weightless black potion", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_CRANK_WHEEL, "Crank wheel", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_BOLT_CUTTERS, "Bolt cutters", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_OLD_COG, "Old cog", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_DEAD_BLOOD_SERPENT, "Dead blood serpent", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SERPENT_ROPE, "Serpent rope", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_MYSTERIOUS_JERKY, "Mysterious jerky", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_SMELLY_KEBAB, "Smelly kebab", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_JAR_OF_CONGEALED_BLOOD, "Jar of congealed blood", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FOUL_CHUNKY_4, "Foul chunky potion", "Quest complete (The Blood Moon Rises) — 4 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FOUL_CHUNKY_3, "Foul chunky potion", "Quest complete (The Blood Moon Rises) — 3 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FOUL_CHUNKY_2, "Foul chunky potion", "Quest complete (The Blood Moon Rises) — 2 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_FOUL_CHUNKY_1, "Foul chunky potion", "Quest complete (The Blood Moon Rises) — 1 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_PUTRID_STICKY_4, "Putrid sticky potion", "Quest complete (The Blood Moon Rises) — 4 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_PUTRID_STICKY_3, "Putrid sticky potion", "Quest complete (The Blood Moon Rises) — 3 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_PUTRID_STICKY_2, "Putrid sticky potion", "Quest complete (The Blood Moon Rises) — 2 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_PUTRID_STICKY_1, "Putrid sticky potion", "Quest complete (The Blood Moon Rises) — 1 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANK_FROTHY_4, "Rank frothy potion", "Quest complete (The Blood Moon Rises) — 4 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANK_FROTHY_3, "Rank frothy potion", "Quest complete (The Blood Moon Rises) — 3 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANK_FROTHY_2, "Rank frothy potion", "Quest complete (The Blood Moon Rises) — 2 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANK_FROTHY_1, "Rank frothy potion", "Quest complete (The Blood Moon Rises) — 1 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANCID_SLIMY_4, "Rancid slimy potion", "Quest complete (The Blood Moon Rises) — 4 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANCID_SLIMY_3, "Rancid slimy potion", "Quest complete (The Blood Moon Rises) — 3 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANCID_SLIMY_2, "Rancid slimy potion", "Quest complete (The Blood Moon Rises) — 2 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_RANCID_SLIMY_1, "Rancid slimy potion", "Quest complete (The Blood Moon Rises) — 1 dose.", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_AMITIRE_LEAVES, "Amitire leaves", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(TBMR_AMITIRE_STEW, "Amitire stew", "Quest complete (The Blood Moon Rises).", Quest.THE_BLOOD_MOON_RISES),
        new JunkEntry(BLUE_DYE_BELLOWS, "Blue dye bellows",
            "Quest complete (Mourning's End Part I) — bellows loaded with blue dye, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(BLUE_TOAD, "Blue toad",
            "Quest complete (Mourning's End Part I) — catches naphtha drips, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(RED_DYE_BELLOWS, "Red dye bellows",
            "Quest complete (Mourning's End Part I) — bellows loaded with red dye, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(RED_TOAD, "Red toad",
            "Quest complete (Mourning's End Part I) — catches naphtha drips, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(YELLOW_DYE_BELLOWS, "Yellow dye bellows",
            "Quest complete (Mourning's End Part I) — bellows loaded with yellow dye, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(YELLOW_TOAD, "Yellow toad",
            "Quest complete (Mourning's End Part I) — catches naphtha drips, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(GREEN_DYE_BELLOWS, "Green dye bellows",
            "Quest complete (Mourning's End Part I) — bellows loaded with green dye, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        new JunkEntry(GREEN_TOAD, "Green toad",
            "Quest complete (Mourning's End Part I) — catches naphtha drips, no use after quest.",
            Quest.MOURNINGS_END_PART_I),
        // ---- Mourning's End Part II --------------------------------------------
        // Quest.MOURNINGS_END_PART_II — confirmed (ID: 100)
        new JunkEntry(HAND_MIRROR_MEPII, "Hand mirror",
            "Quest complete (Mourning's End Part II) — used to redirect light beams in the Temple of Light, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(ITEM_LIST_MEPII, "Item list",
            "Quest complete (Mourning's End Part II) — quest item list, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(RED_CRYSTAL_MEPII, "Red crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(GREEN_CRYSTAL_MEPII, "Green crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(CYAN_CRYSTAL_MEPII, "Cyan crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(BLUE_CRYSTAL_MEPII, "Blue crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(MAGENTA_CRYSTAL_MEPII, "Magenta crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(FRACTURED_CRYSTAL_H, "Fractured crystal (horizontal)",
            "Quest complete (Mourning's End Part II) — fractured light crystal, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(FRACTURED_CRYSTAL_V, "Fractured crystal (vertical)",
            "Quest complete (Mourning's End Part II) — fractured light crystal, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(EDERENS_JOURNAL, "Edern's journal",
            "Quest complete (Mourning's End Part II) — journal belonging to Edern, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(YELLOW_CRYSTAL_MEPII, "Yellow crystal",
            "Quest complete (Mourning's End Part II) — coloured crystal for light puzzle, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        new JunkEntry(SCRAWLED_NOTES_MEPII, "Scrawled notes",
            "Quest complete (Mourning's End Part II) — notes from the Temple of Light, no use after quest.",
            Quest.MOURNINGS_END_PART_II),
        // ---- Forgettable Tale (additional) ------------------------------------
        // Quest.FORGETTABLE_TALE — confirmed
        // Bat bones already gated on WATCHTOWER + FORGETTABLE_TALE above.
        new JunkEntry(KELDA_SEED, "Kelda seed",
            "Quest complete (Forgettable Tale...) — grown into Kelda hops for the dwarf stout, no use after quest.",
            Quest.FORGETTABLE_TALE),
        new JunkEntry(KELDA_HOPS, "Kelda hops",
            "Quest complete (Forgettable Tale...) — used to brew Kelda stout, no use after quest.",
            Quest.FORGETTABLE_TALE),
        new JunkEntry(KELDA_STOUT, "Kelda stout",
            "Quest complete (Forgettable Tale...) — given to drunken dwarf, no use after quest.",
            Quest.FORGETTABLE_TALE),
        new JunkEntry(SQUARE_STONE_YELLOW, "Square stone (yellow)",
            "Quest complete (Forgettable Tale...) — used in the dwarven machine puzzle, no use after quest.",
            Quest.FORGETTABLE_TALE),
        new JunkEntry(SQUARE_STONE_GREEN, "Square stone (green)",
            "Quest complete (Forgettable Tale...) — used in the dwarven machine puzzle, no use after quest.",
            Quest.FORGETTABLE_TALE),
        new JunkEntry(LETTER_FORGETTABLE, "Letter (Forgettable Tale...)",
            "Quest complete (Forgettable Tale...) — letter from Commander Veldaban, no use after quest.",
            Quest.FORGETTABLE_TALE),
        // ---- Enakhra's Lament (sandstone construction) -------------------------
        // Quest.ENAKHRAS_LAMENT — confirmed
        // Camel mask and locust meat already present above.
        new JunkEntry(SANDSTONE_20KG, "Sandstone (20kg)",
            "Quest complete (Enakhra's Lament) — used to carve sandstone body of the golem, no use after quest.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(SANDSTONE_32KG, "Sandstone (32kg)",
            "Quest complete (Enakhra's Lament) — used to carve sandstone base of the golem, no use after quest.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(SANDSTONE_BODY, "Sandstone body",
            "Quest complete (Enakhra's Lament) — carved sandstone body placed in the golem, no use after quest.",
            Quest.ENAKHRAS_LAMENT),
        new JunkEntry(SANDSTONE_BASE, "Sandstone base",
            "Quest complete (Enakhra's Lament) — carved sandstone base of the golem, no use after quest.",
            Quest.ENAKHRAS_LAMENT),
        // ---- Recipe for Disaster — Another Cook's Quest -----------------------
        // Quest.RECIPE_FOR_DISASTER__ANOTHER_COOKS_QUEST — confirmed
        new JunkEntry(DIRTY_BLAST_RFD, "Dirty blast",
            "Quest complete (RFD - Another Cook's Quest) — given to the Cook; no use after quest.",
            Quest.RECIPE_FOR_DISASTER__ANOTHER_COOKS_QUEST),
        new JunkEntry(ROTTEN_TOMATO_RFD, "Rotten tomato",
            "Junk after both RFD - Another Cook's Quest and Mourning's End Part II — used in both quests.",
            Quest.RECIPE_FOR_DISASTER__ANOTHER_COOKS_QUEST)
            .withRequiredQuest2(Quest.MOURNINGS_END_PART_II),
        // ---- Recipe for Disaster — Evil Dave ----------------------------------
        // Quest.RECIPE_FOR_DISASTER__EVIL_DAVE — confirmed
        new JunkEntry(EMPTY_SPICE_SHAKER, "Empty spice shaker",
            "Quest complete (RFD - Evil Dave) — spice shaker after using its contents on stew, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__EVIL_DAVE),
        // ---- Recipe for Disaster — King Awowogei ------------------------------
        // Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI — confirmed
        new JunkEntry(RED_BANANA, "Red banana",
            "Quest complete (RFD - King Awowogei) — ingredient for stuffed snake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(SLICED_RED_BANANA, "Sliced red banana",
            "Quest complete (RFD - King Awowogei) — sliced for snake stuffing, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(TCHIKI_MONKEY_NUTS, "Tchiki monkey nuts",
            "Quest complete (RFD - King Awowogei) — ingredient for stuffed snake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(TCHIKI_NUT_PASTE, "Tchiki nut paste",
            "Quest complete (RFD - King Awowogei) — crushed nuts for snake stuffing, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(SNAKE_CORPSE_RFD, "Snake corpse",
            "Quest complete (RFD - King Awowogei) — snake used for the stuffed snake dish, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(RAW_STUFFED_SNAKE, "Raw stuffed snake",
            "Quest complete (RFD - King Awowogei) — unstuffed snake before cooking, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(ODD_STUFFED_SNAKE, "Odd stuffed snake",
            "Quest complete (RFD - King Awowogei) — incorrectly stuffed snake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(STUFFED_SNAKE, "Stuffed snake",
            "Quest complete (RFD - King Awowogei) — correctly stuffed snake delivered to Awowogei, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        new JunkEntry(SNAKE_OVERCOOKED, "Snake (overcooked)",
            "Quest complete (RFD - King Awowogei) — overcooked stuffed snake variant, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__KING_AWOWOGEI),
        // ---- Recipe for Disaster — Lumbridge Guide ----------------------------
        // Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE — confirmed
        new JunkEntry(ENCHANTED_EGG_RFD, "Enchanted egg",
            "Quest complete (RFD - Lumbridge Guide) — enchanted ingredient for cake of guidance, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE),
        new JunkEntry(ENCHANTED_MILK_RFD, "Enchanted milk",
            "Quest complete (RFD - Lumbridge Guide) — enchanted ingredient for cake of guidance, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE),
        new JunkEntry(ENCHANTED_FLOUR_RFD, "Enchanted flour",
            "Quest complete (RFD - Lumbridge Guide) — enchanted ingredient for cake of guidance, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE),
        new JunkEntry(RAW_GUIDE_CAKE, "Raw guide cake",
            "Quest complete (RFD - Lumbridge Guide) — unbaked cake of guidance, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE),
        new JunkEntry(CAKE_OF_GUIDANCE, "Cake of guidance",
            "Quest complete (RFD - Lumbridge Guide) — given to the Lumbridge Guide to break the spell, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__LUMBRIDGE_GUIDE),
        // ---- Recipe for Disaster — Mountain Dwarf -----------------------------
        // Quest.RECIPE_FOR_DISASTER__MOUNTAIN_DWARF — confirmed
        new JunkEntry(DWARVEN_ROCK_CAKE_HOT, "Dwarven rock cake (hot)",
            "Quest complete (RFD - Mountain Dwarf) — freshly baked cake; cools into the cool variant, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__MOUNTAIN_DWARF),
        new JunkEntry(DWARVEN_ROCK_CAKE_COOL, "Dwarven rock cake (cool)",
            "Quest complete (RFD - Mountain Dwarf) — cooled dwarven rock cake given to the dwarf, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__MOUNTAIN_DWARF),
        new JunkEntry(ASGOLDIAN_ALE, "Asgoldian ale",
            "Quest complete (RFD - Mountain Dwarf) — ale given to the mountain dwarf, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__MOUNTAIN_DWARF),
        // ---- Recipe for Disaster — Pirate Pete --------------------------------
        // Quest.RECIPE_FOR_DISASTER__PIRATE_PETE — confirmed
        new JunkEntry(BREADCRUMBS, "Breadcrumbs",
            "Quest complete (RFD - Pirate Pete) — ingredient for fishcake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(KELP, "Kelp",
            "Quest complete (RFD - Pirate Pete) — collected from underwater, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(GROUND_KELP, "Ground kelp",
            "Quest complete (RFD - Pirate Pete) — kelp ground for fishcake recipe, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(GIANT_CRAB_MEAT, "Giant crab meat",
            "Quest complete (RFD - Pirate Pete) — crab meat ingredient for fishcake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(GROUND_GIANT_CRAB_MEAT, "Ground giant crab meat",
            "Quest complete (RFD - Pirate Pete) — crab meat ground for fishcake recipe, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(GROUND_COD, "Ground cod",
            "Quest complete (RFD - Pirate Pete) — cod ground for fishcake recipe, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(RAW_FISHCAKE, "Raw fishcake",
            "Quest complete (RFD - Pirate Pete) — uncooked fishcake for Captain Redbeard Frank, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_FISHCAKE, "Cooked fishcake",
            "Quest complete (RFD - Pirate Pete) — cooked fishcake given to Pirate Pete, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(BURNT_GIANT_CRAB_MEAT, "Burnt giant crab meat",
            "Quest complete (RFD - Pirate Pete) — overcooked crab meat, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_GIANT_CRAB_MEAT_ONE, "Cooked giant crab meat (one piece)",
            "Quest complete (RFD - Pirate Pete) — partially complete crab meat (1/5), no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_GIANT_CRAB_MEAT_TWO, "Cooked giant crab meat (two pieces)",
            "Quest complete (RFD - Pirate Pete) — partially complete crab meat (2/5), no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_GIANT_CRAB_MEAT_THREE, "Cooked giant crab meat (three pieces)",
            "Quest complete (RFD - Pirate Pete) — partially complete crab meat (3/5), no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_GIANT_CRAB_MEAT_FOUR, "Cooked giant crab meat (four pieces)",
            "Quest complete (RFD - Pirate Pete) — partially complete crab meat (4/5), no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(COOKED_GIANT_CRAB_MEAT_FIVE, "Cooked giant crab meat (five pieces)",
            "Quest complete (RFD - Pirate Pete) — fully assembled crab meat patty, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(BURNT_FISHCAKE, "Burnt fishcake",
            "Quest complete (RFD - Pirate Pete) — overcooked fishcake, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(MUDSKIPPER_HIDE, "Mudskipper hide",
            "Quest complete (RFD - Pirate Pete) — hide obtained underwater, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(BROKEN_CRAB_CLAW, "Broken crab claw",
            "Quest complete (RFD - Pirate Pete) — claw from the giant crab, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(BROKEN_CRAB_SHELL, "Broken crab shell",
            "Quest complete (RFD - Pirate Pete) — shell from the giant crab, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(FRESH_CRAB_CLAW, "Fresh crab claw", JunkTier.YELLOW,
            "Only junk if a Crab claw is already in the bank.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE, new int[]{CRAB_CLAW}),
        new JunkEntry(FRESH_CRAB_SHELL, "Fresh crab shell", JunkTier.YELLOW,
            "Only junk if a Crab helmet is already in the bank.",
            Quest.RECIPE_FOR_DISASTER__PIRATE_PETE, new int[]{CRAB_HELMET}),
        // ---- Recipe for Disaster — Sir Amik Varze -----------------------------
        // Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE — confirmed
        new JunkEntry(VANILLA_POD,       "Vanilla pod",       "Quest complete (RFD - Sir Amik Varze) — brulee ingredient, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(POT_OF_CORNFLOUR,  "Pot of cornflour",  "Quest complete (RFD - Sir Amik Varze) — brulee ingredient, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(CORNFLOUR_MIXTURE, "Cornflour mixture", "Quest complete (RFD - Sir Amik Varze) — brulee ingredient, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(MILKY_MIXTURE,     "Milky mixture",     "Quest complete (RFD - Sir Amik Varze) — brulee ingredient, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(CINNAMON_RFD,      "Cinnamon",          "Quest complete (RFD - Sir Amik Varze) — brulee ingredient, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(BRULEE_EGG,        "Brulee",            "Quest complete (RFD - Sir Amik Varze) — brulee (egg stage), no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(BRULEE_VANILLA,    "Brulee",            "Quest complete (RFD - Sir Amik Varze) — brulee (vanilla stage), no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(BRULEE_RAW,        "Brulee",            "Quest complete (RFD - Sir Amik Varze) — brulee (raw stage), no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(BRULEE_SUPREME,    "Brulee supreme",    "Quest complete (RFD - Sir Amik Varze) — final brulee, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(EVIL_CHICKENS_EGG, "Evil chicken's egg","Quest complete (RFD - Sir Amik Varze) — egg used in brulee recipe, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(DRAGON_TOKEN,      "Dragon token",      "Quest complete (RFD - Sir Amik Varze) — token used to obtain ice gloves, no use after quest.", Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        new JunkEntry(BOOK_ON_CHICKENS, "Book on chickens",
            "Quest complete (RFD - Sir Amik Varze) — book about chickens, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SIR_AMIK_VARZE),
        // ---- Recipe for Disaster — Skrach Uglogwee ----------------------------
        // Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE — confirmed
        new JunkEntry(BALLOON_TOAD, "Balloon toad",
            "Quest complete (RFD - Skrach Uglogwee) — inflated toad used to attract the jubbly, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        new JunkEntry(LIMESTONE_RFD, "Limestone",
            "Quest complete (RFD - Skrach Uglogwee) — used to make the fire pit for cooking jubbly, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        new JunkEntry(RAW_JUBBLY, "Raw jubbly",
            "Quest complete (RFD - Skrach Uglogwee) — raw jubbly bird before cooking, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        new JunkEntry(COOKED_JUBBLY, "Cooked jubbly",
            "Quest complete (RFD - Skrach Uglogwee) — cooked jubbly given to Skrach Uglogwee, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        new JunkEntry(BURNT_JUBBLY, "Burnt jubbly",
            "Quest complete (RFD - Skrach Uglogwee) — overcooked jubbly; not accepted by Skrach, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        // ---- Recipe for Disaster — Wartface & Bentnoze ------------------------
        // Quest.RECIPE_FOR_DISASTER__WARTFACE__BENTNOZE — confirmed
        new JunkEntry(SPICY_MAGGOTS_RFD, "Spicy maggots",
            "Quest complete (RFD - Wartface & Bentnoze) — spice-seasoned maggots given to the goblin generals, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__WARTFACE__BENTNOZE),
        new JunkEntry(SLOP_OF_COMPROMISE, "Slop of compromise",
            "Quest complete (RFD - Wartface & Bentnoze) — slop dish delivered to the goblin generals, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__WARTFACE__BENTNOZE),
        new JunkEntry(SOGGY_BREAD, "Soggy bread",
            "Quest complete (RFD - Wartface & Bentnoze) — soggy bread ingredient, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__WARTFACE__BENTNOZE),
        new JunkEntry(DYED_ORANGE, "Dyed orange",
            "Quest complete (RFD - Wartface & Bentnoze) — orange dyed for the slop recipe, no use after quest.",
            Quest.RECIPE_FOR_DISASTER__WARTFACE__BENTNOZE),
        // ---- Land of the Goblins (additional) ---------------------------------
        // Quest.LAND_OF_THE_GOBLINS — confirmed
        // Goblin mails already gated on GOBLIN_DIPLOMACY + LAND_OF_THE_GOBLINS above.
        new JunkEntry(PHARMAKOS_BERRIES, "Pharmakos berries",
            "Quest complete (Land of the Goblins) — used to make goblin potion, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(WHITEFISH, "Whitefish",
            "Quest complete (Land of the Goblins) — fed to high priest, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(EKELESHUUN_KEY, "Ekeleshuun key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(NAROGOSHUUN_KEY, "Narogoshuun key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(HUZAMOGAARB_KEY, "Huzamogaarb key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(SARAGORGAK_KEY, "Saragorgak key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(HOROGOTHGAR_KEY, "Horogothgar key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(YURKOLGOKH_KEY, "Yurkolgokh key",
            "Quest complete (Land of the Goblins) — tribe key for goblin village, no use after quest.",
            Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(GOBLIN_POTION_4, "Goblin potion (4)",
            "Quest complete (Land of the Goblins / Hopespear's Will) — used to assume goblin form; no use after both quests.",
            Quest.LAND_OF_THE_GOBLINS)
            .withRequiredQuest2(Quest.HOPESPEARS_WILL),
        new JunkEntry(GOBLIN_POTION_3, "Goblin potion (3)",
            "Quest complete (Land of the Goblins / Hopespear's Will) — used to assume goblin form; no use after both quests.",
            Quest.LAND_OF_THE_GOBLINS)
            .withRequiredQuest2(Quest.HOPESPEARS_WILL),
        new JunkEntry(GOBLIN_POTION_2, "Goblin potion (2)",
            "Quest complete (Land of the Goblins / Hopespear's Will) — used to assume goblin form; no use after both quests.",
            Quest.LAND_OF_THE_GOBLINS)
            .withRequiredQuest2(Quest.HOPESPEARS_WILL),
        new JunkEntry(GOBLIN_POTION_1, "Goblin potion (1)",
            "Quest complete (Land of the Goblins / Hopespear's Will) — used to assume goblin form; no use after both quests.",
            Quest.LAND_OF_THE_GOBLINS)
            .withRequiredQuest2(Quest.HOPESPEARS_WILL)
    ); }
    private static List<JunkEntry> buildBatch3() { return Arrays.asList(
        // =====================================================================
        // Batch 4 entries — Novice quest additions, confirmed July 2026
        // =====================================================================
        // ---- A Porcine of Interest -------------------------------------------
        // Quest.A_PORCINE_OF_INTEREST — confirmed (ID: 110)
        new JunkEntry(SOURHOG_FOOT, "Sourhog foot",
            "Quest complete (A Porcine of Interest) — tail obtained from Sourhog, no use after quest.",
            Quest.A_PORCINE_OF_INTEREST),
        // ---- Members' novice quests with no bankable junk items ---------------
        // Audited and confirmed to have no quest-specific bankable items. Listed so
        // future maintainers know each quest was considered deliberately.
        //   (Children of the Sun reclassified July 9, 2026 — Varlamore invitation 28972 now flagged; see buildBatch4.)
        //   Recruitment Drive    (RECRUITMENT_DRIVE, ID 118)      — all items consumed/removed during quest
        // ---- Current Affairs -------------------------------------------------
        // Quest.CURRENT_AFFAIRS — confirmed (ID: 7105)
        new JunkEntry(FORM_CR_4P, "Form CR-4P",
            "Quest complete (Current Affairs) — bureaucratic entry form, no use after quest.",
            Quest.CURRENT_AFFAIRS),
        new JunkEntry(FORM_7R4_5H_UNSIGNED, "Form 7R4-5H (unsigned)",
            "Quest complete (Current Affairs) — unsigned nomination form, no use after quest.",
            Quest.CURRENT_AFFAIRS),
        new JunkEntry(FORM_7R4_5H_SIGNED, "Form 7R4-5H (signed)",
            "Quest complete (Current Affairs) — signed nomination form, no use after quest.",
            Quest.CURRENT_AFFAIRS),
        new JunkEntry(MAYORAL_FISHBOWL, "Mayoral fishbowl",
            "Quest complete (Current Affairs) — fishbowl used to transport the Mayor of Catherby, no use after quest.",
            Quest.CURRENT_AFFAIRS),
        new JunkEntry(TINY_NET_CA, "Tiny net",
            "Quest complete (Current Affairs) — used to catch the Mayor of Catherby; no use after quest.",
            Quest.CURRENT_AFFAIRS),
        // ---- Ethically Acquired Antiquities ----------------------------------
        // Quest.ETHICALLY_ACQUIRED_ANTIQUITIES — confirmed (ID: 3713)
        new JunkEntry(TATTERED_SAILS_EAA, "Tattered sails",
            "Quest complete (Ethically Acquired Antiquities) — torn ship sails, no use after quest.",
            Quest.ETHICALLY_ACQUIRED_ANTIQUITIES),
        new JunkEntry(SAILS_EAA, "Sails",
            "Quest complete (Ethically Acquired Antiquities) — repaired sails used during quest, no use after.",
            Quest.ETHICALLY_ACQUIRED_ANTIQUITIES),
        new JunkEntry(BETTYS_NOTES_EAA, "Betty's notes",
            "Quest complete (Ethically Acquired Antiquities) — research notes, no use after quest.",
            Quest.ETHICALLY_ACQUIRED_ANTIQUITIES),
        new JunkEntry(STOREROOM_KEY_EAA, "Storeroom key (Ethically Acquired Antiquities)",
            "Quest complete — storeroom key (distinct from the Eadgar's Ruse storeroom key, ID 3269).",
            Quest.ETHICALLY_ACQUIRED_ANTIQUITIES),
        // ---- Gertrude's Cat --------------------------------------------------
        // Quest.GERTRUDES_CAT — confirmed (ID: 60)
        new JunkEntry(SEASONED_SARDINE, "Seasoned sardine",
            "Quest complete (Gertrude's Cat) — doogle-seasoned sardine used to lure Fluffs, no use after quest.",
            Quest.GERTRUDES_CAT),
        new JunkEntry(FLUFFS_KITTEN, "Fluffs' kitten",
            "Quest complete (Gertrude's Cat) — quest kitten returned to Gertrude, no use after quest.",
            Quest.GERTRUDES_CAT),
        // Doogle leaves: used in Gertrude's Cat AND Big Chompy Bird Hunting — junk only after both.
        // Quest.BIG_CHOMPY_BIRD_HUNTING — confirmed (ID: 8)
        new JunkEntry(DOOGLE_LEAVES, "Doogle leaves",
            "Used in Gertrude's Cat and Big Chompy Bird Hunting — junk only after both quests are complete.",
            Quest.GERTRUDES_CAT)
            .withRequiredQuest2(Quest.BIG_CHOMPY_BIRD_HUNTING),
        // ---- Monk's Friend ---------------------------------------------------
        // Quest.MONKS_FRIEND — confirmed (ID: 97)
        new JunkEntry(CHILDS_BLANKET, "Child's blanket",
            "Quest complete (Monk's Friend) — blanket returned with the child, no use after quest.",
            Quest.MONKS_FRIEND),
        // ---- Pandemonium -----------------------------------------------------
        // Quest.PANDEMONIUM — confirmed (ID: 7103)
        new JunkEntry(MYSTERIOUS_MEDALLION_PAND, "Mysterious medallion",
            "Quest complete (Pandemonium) — medallion used during quest, no use after.",
            Quest.PANDEMONIUM),
        new JunkEntry(OLD_CUP_PAND, "Old cup",
            "Quest complete (Pandemonium) — cup used during quest, no use after.",
            Quest.PANDEMONIUM),
        // Captain's log: 31985 = during-quest variant. After-quest variant (31986) is kept — not flagged.
        new JunkEntry(CAPTAINS_LOG_DURING, "Captain's log",
            "Quest complete (Pandemonium) — during-quest variant (31985); after-quest (31986) is kept.",
            Quest.PANDEMONIUM),
        new JunkEntry(CRATE_SHIP_PARTS_PAND, "Crate of ship parts",
            "Quest complete (Pandemonium) — ship repair supplies, no use after quest.",
            Quest.PANDEMONIUM),
        // ---- Rag and Bone Man I ---------------------------------------------
        // Quest.RAG_AND_BONE_MAN_I — confirmed (ID: 114)
        // 9 bone types × 3 states (unpolished / polished / in vinegar) = 27 IDs.
        // All are quest-specific: bones are polished in vinegar then given to the Odd Old Man.
        new JunkEntry(GOBLIN_SKULL_UNPOLISHED,          "Goblin skull",                       "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(GOBLIN_SKULL_POLISHED,            "Goblin skull (polished)",             "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_GOBLIN_SKULL,     "Goblin skull (in vinegar)",           "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(UNICORN_BONE_UNPOLISHED,          "Unicorn bone",                        "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(UNICORN_BONE_POLISHED,            "Unicorn bone (polished)",             "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_UNICORN,          "Unicorn bone (in vinegar)",           "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BEAR_RIBS_UNPOLISHED,             "Bear ribs",                           "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BEAR_RIBS_POLISHED,               "Bear ribs (polished)",                "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_BEAR_RIBS,        "Bear ribs (in vinegar)",              "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(RAM_SKULL_UNPOLISHED,             "Ram skull",                           "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(RAM_SKULL_POLISHED,               "Ram skull (polished)",                "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_RAM_SKULL,        "Ram skull (in vinegar)",              "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BIG_FROG_LEG_UNPOLISHED,          "Big frog leg",                        "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BIG_FROG_LEG_POLISHED,            "Big frog leg (polished)",             "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_BIG_FROG_LEG,     "Big frog leg (in vinegar)",           "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(MONKEY_PAW_UNPOLISHED,            "Monkey paw",                          "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(MONKEY_PAW_POLISHED,              "Monkey paw (polished)",               "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_MONKEY_PAW,       "Monkey paw (in vinegar)",             "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(GIANT_BAT_WING_UNPOLISHED,        "Giant bat wing",                      "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(GIANT_BAT_WING_POLISHED,          "Giant bat wing (polished)",            "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_GIANT_BAT_WING,   "Giant bat wing (in vinegar)",         "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(GIANT_RAT_BONE_UNPOLISHED,        "Giant rat bone",                      "Quest complete (Rag and Bone Man I) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(GIANT_RAT_BONE_POLISHED,          "Giant rat bone (polished)",            "Quest complete (Rag and Bone Man I) — polished specimen.",   Quest.RAG_AND_BONE_MAN_I),
        new JunkEntry(BONE_IN_VINEGAR_GIANT_RAT,        "Giant rat bone (in vinegar)",          "Quest complete (Rag and Bone Man I) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_I),
        // ---- The Ribbiting Tale of a Lily Pad Labour Dispute -----------------
        // Quest.THE_RIBBITING_TALE_OF_A_LILY_PAD_LABOUR_DISPUTE — confirmed (ID: 3515)
        new JunkEntry(LOVE_LETTER_RIBBITING, "Love letter",
            "Quest complete (The Ribbiting Tale of a Lily Pad Labour Dispute) — retrieved for a hopeful frog, no use after quest.",
            Quest.THE_RIBBITING_TALE_OF_A_LILY_PAD_LABOUR_DISPUTE),
        new JunkEntry(PLUSHY_RIBBITING, "Plushy",
            "Quest complete (The Ribbiting Tale of a Lily Pad Labour Dispute) — frog plushy used as evidence, no use after quest.",
            Quest.THE_RIBBITING_TALE_OF_A_LILY_PAD_LABOUR_DISPUTE),

        // =====================================================================
        // Batch 5 entries — Intermediate quest additions, confirmed July 2026
        // =====================================================================
        // ---- Spirits of the Elid -------------------------------------------
        // Quest.SPIRITS_OF_THE_ELID — confirmed (ID: 139)
        new JunkEntry(BALLAD_SPIRITS_ELID, "Ballad",
            "Quest complete (Spirits of the Elid) — quest scroll, no use after quest.",
            Quest.SPIRITS_OF_THE_ELID),
        new JunkEntry(STATUETTE_SPIRITS_ELID, "Statuette",
            "Quest complete (Spirits of the Elid) — statuette offered to the spirits, no use after quest.",
            Quest.SPIRITS_OF_THE_ELID),
        // ---- The Lost Tribe ------------------------------------------------
        // Quest.THE_LOST_TRIBE — confirmed (ID: 87)
        new JunkEntry(GOBLIN_SYMBOL_BOOK, "Goblin symbol book",
            "Quest complete (The Lost Tribe) — used to communicate with the Dorgeshuun, no use after quest.",
            Quest.THE_LOST_TRIBE),
        new JunkEntry(KEY_LOST_TRIBE, "Key",
            "Quest complete (The Lost Tribe) — Lumbridge cellar key, no use after quest.",
            Quest.THE_LOST_TRIBE),
        // ---- Swan Song -----------------------------------------------------
        // Quest.SWAN_SONG — confirmed (ID: 140)
        new JunkEntry(FRESH_MONKFISH_COOKED, "Fresh monkfish (cooked)",
            "Quest complete (Swan Song) — cooked monkfish from quest fishing, no use after quest.",
            Quest.SWAN_SONG),
        new JunkEntry(FRESH_MONKFISH_RAW, "Fresh monkfish (raw)",
            "Quest complete (Swan Song) — raw monkfish from quest fishing, no use after quest.",
            Quest.SWAN_SONG),
        new JunkEntry(IRON_SHEET_SWAN, "Iron sheet",
            "Quest complete (Swan Song) — iron sheet used to repair the pipe, no use after quest.",
            Quest.SWAN_SONG),
        // ---- Tale of the Righteous -----------------------------------------
        // Quest.TALE_OF_THE_RIGHTEOUS — confirmed (ID: 143)
        new JunkEntry(DUSTY_NOTE_TALE, "Dusty note",
            "Quest complete (Tale of the Righteous) — note found in ruins, no use after quest.",
            Quest.TALE_OF_THE_RIGHTEOUS),
        // ---- Death on the Isle ---------------------------------------------
        // Quest.DEATH_ON_THE_ISLE — confirmed (ID: 3711)
        new JunkEntry(PROP_SWORD_DOTI, "Prop sword",
            "Quest complete (Death on the Isle) — theatrical prop sword, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BUTLERS_TRAY_DOTI_1, "Butler's tray",
            "Quest complete (Death on the Isle) — butler's serving tray (variant 1 of 4), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BUTLERS_TRAY_DOTI_2, "Butler's tray",
            "Quest complete (Death on the Isle) — butler's serving tray (variant 2 of 4), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BUTLERS_TRAY_DOTI_3, "Butler's tray",
            "Quest complete (Death on the Isle) — butler's serving tray (variant 3 of 4), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BUTLERS_TRAY_DOTI_4, "Butler's tray",
            "Quest complete (Death on the Isle) — butler's serving tray (variant 4 of 4), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(WOLF_MASK_DOTI_1, "Wolf mask",
            "Quest complete (Death on the Isle) — wolf mask (variant 1 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(WOLF_MASK_DOTI_2, "Wolf mask",
            "Quest complete (Death on the Isle) — wolf mask (variant 2 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BIRD_MASK_DOTI_1, "Bird mask",
            "Quest complete (Death on the Isle) — bird mask (variant 1 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(BIRD_MASK_DOTI_2, "Bird mask",
            "Quest complete (Death on the Isle) — bird mask (variant 2 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(CASE_FILE_DOTI, "Case file",
            "Quest complete (Death on the Isle) — murder investigation case file, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(DRINKING_FLASK_DOTI, "Drinking flask",
            "Quest complete (Death on the Isle) — flask pickpocketed from Pavo during investigation, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(JAGUAR_MASK_DOTI_1, "Jaguar mask",
            "Quest complete (Death on the Isle) — jaguar mask (variant 1 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(JAGUAR_MASK_DOTI_2, "Jaguar mask",
            "Quest complete (Death on the Isle) — jaguar mask (variant 2 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(RAM_MASK_DOTI_1, "Ram mask",
            "Quest complete (Death on the Isle) — ram mask (variant 1 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(RAM_MASK_DOTI_2, "Ram mask",
            "Quest complete (Death on the Isle) — ram mask (variant 2 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(SHIPPING_CONTRACT_DOTI, "Shipping contract",
            "Quest complete (Death on the Isle) — contract pickpocketed from Xocotla during investigation, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(SNAKE_MASK_DOTI_1, "Snake mask",
            "Quest complete (Death on the Isle) — snake mask (variant 1 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(SNAKE_MASK_DOTI_2, "Snake mask",
            "Quest complete (Death on the Isle) — snake mask (variant 2 of 2), no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(THREATENING_NOTE_DOTI, "Threatening note",
            "Quest complete (Death on the Isle) — note pickpocketed from Cozyac during investigation, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(WINE_LABELS_DOTI, "Wine labels",
            "Quest complete (Death on the Isle) — labels pickpocketed from Adala during investigation, no use after quest.",
            Quest.DEATH_ON_THE_ISLE),
        // ---- Wanted! -------------------------------------------------------
        // Quest.WANTED — confirmed (ID: 156)
        new JunkEntry(SOLUS_HAT, "Solus's hat",
            "Quest complete (Wanted!) — hat recovered from Solus Dellagar, no use after quest.",
            Quest.WANTED),
        // ---- Secrets of the North -----------------------------------------
        // Quest.SECRETS_OF_THE_NORTH — confirmed (ID: 2338)
        new JunkEntry(ANCIENT_JEWEL_SOTN, "Ancient jewel",
            "Quest complete (Secrets of the North) — ancient jewel used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(ANCIENT_MAP_SOTN, "Ancient map",
            "Quest complete (Secrets of the North) — map used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(DUKE_NOTE_SOTN, "Duke note",
            "Quest complete (Secrets of the North) — note used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(DUSTY_SCROLL_SOTN, "Dusty scroll (Secrets of the North)",
            "Quest complete (Secrets of the North) — scroll used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(ICY_CHEST_SOTN, "Icy chest",
            "Quest complete (Secrets of the North) — icy chest used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(ICY_KEY_SOTN, "Icy key",
            "Quest complete (Secrets of the North) — icy key used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(JEWEL_SHARD_CHEST, "Jewel shard (chest)",
            "Quest complete (Secrets of the North) — jewel shard from chest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(JEWEL_SHARD_PILLAR, "Jewel shard (pillar)",
            "Quest complete (Secrets of the North) — jewel shard from pillar, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(LEVER_HANDLE_SOTN, "Lever handle",
            "Quest complete (Secrets of the North) — lever handle used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(NUMBERS_NOTE_SOTN, "Numbers note",
            "Quest complete (Secrets of the North) — numbers note used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(SETTLEMENTS_NOTE_SOTN, "Settlements note",
            "Quest complete (Secrets of the North) — settlements note used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(STRANGE_CIPHER_SOTN, "Strange cipher",
            "Quest complete (Secrets of the North) — cipher used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(STRANGE_LIST_SOTN, "Strange list",
            "Quest complete (Secrets of the North) — list used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        new JunkEntry(TULLIA_LETTER, "Tullia's letter",
            "Quest complete (Secrets of the North) — letter used during quest, no use after quest.",
            Quest.SECRETS_OF_THE_NORTH),
        // ---- Shades of Mort'ton --------------------------------------------
        // Quest.SHADES_OF_MORTTON — confirmed (ID: 128)
        new JunkEntry(DIARY_SHADES_MORTTON, "Diary",
            "Quest complete (Shades of Mort'ton) — Drezel's diary, no use after quest.",
            Quest.SHADES_OF_MORTTON),
        // ---- Scrambled! ----------------------------------------------------
        // Quest.SCRAMBLED — confirmed (ID: 5191)
        new JunkEntry(DRAGON_EGG_SCRAMBLED, "Dragon egg",
            "Quest complete (Scrambled!) — dragon egg used during quest, no use after quest.",
            Quest.SCRAMBLED),
        // ---- Twilight's Promise --------------------------------------------
        // Quest.TWILIGHTS_PROMISE — confirmed (ID: 3512)
        new JunkEntry(QUETZAL_FEED_TP, "Quetzal feed",
            "Quest complete (Twilight's Promise) — feed used to befriend the quetzal, no use after quest.",
            Quest.TWILIGHTS_PROMISE),
        // ---- The Garden of Death -------------------------------------------
        // Quest.THE_GARDEN_OF_DEATH — confirmed (ID: 180)
        new JunkEntry(STONE_TABLET_GARDEN_MOUNT_QUID, "Stone tablet",
            "Quest complete (The Garden of Death) — stone tablet from Mount Quidamortem, no use after quest.",
            Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(STONE_TABLET_GARDEN_LAKE_MOLCH, "Stone tablet",
            "Quest complete (The Garden of Death) — stone tablet from Lake Molch Island, no use after quest.",
            Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(STONE_TABLET_GARDEN_KEBOS, "Stone tablet",
            "Quest complete (The Garden of Death) — stone tablet from Kebos Swamp, no use after quest.",
            Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(STONE_TABLET_GARDEN_RUINS_MORRA, "Stone tablet",
            "Quest complete (The Garden of Death) — stone tablet from Ruins of Morra, no use after quest.",
            Quest.THE_GARDEN_OF_DEATH),
        // ---- The Forsaken Tower --------------------------------------------
        // Quest.THE_FORSAKEN_TOWER — confirmed (ID: 54)
        new JunkEntry(OLD_NOTES_FORSAKEN_TOWER, "Old notes",
            "Quest complete (The Forsaken Tower) — notes found in the tower, no use after quest.",
            Quest.THE_FORSAKEN_TOWER),
        // ---- The Depths of Despair -----------------------------------------
        // Quest.THE_DEPTHS_OF_DESPAIR — confirmed (ID: 26)
        new JunkEntry(ROYAL_ACCORD_TWILL, "Royal accord of twill",
            "Quest complete (The Depths of Despair) — original Accord document given to Lord Hosidius, no use after quest.",
            Quest.THE_DEPTHS_OF_DESPAIR),
        // ---- The Queen of Thieves ------------------------------------------
        // Quest.THE_QUEEN_OF_THIEVES — confirmed (ID: 113)
        new JunkEntry(LETTER_QUEEN_OF_THIEVES, "Letter",
            "Quest complete (The Queen of Thieves) — letter used as evidence, no use after quest.",
            Quest.THE_QUEEN_OF_THIEVES),
        // ---- Tears of Guthix -----------------------------------------------
        // Quest.TEARS_OF_GUTHIX — confirmed (ID: 145)
        new JunkEntry(MAGIC_STONE_TEARS_GUTHIX, "Magic stone",
            "Quest complete (Tears of Guthix) — stone used to repair the bowl of tears, no use after quest.",
            Quest.TEARS_OF_GUTHIX),
        // ---- Shilo Village -------------------------------------------------
        // Quest.SHILO_VILLAGE — confirmed (ID: 133)
        // Note: Bone key (605) is used after quest to access Shilo Village bank — not flagged.
        new JunkEntry(COINS_SHILO_VILLAGE, "Coins",
            "Quest complete (Shilo Village) — quest-specific coin item (ID 617), distinct from regular coins (ID 995).",
            Quest.SHILO_VILLAGE),
        new JunkEntry(BONE_SHARD_SHILO, "Bone shard",
            "Quest complete (Shilo Village) — bone shard used during quest, no use after quest.",
            Quest.SHILO_VILLAGE),
        // ---- Sins of the Father --------------------------------------------
        // Quest.SINS_OF_THE_FATHER — confirmed (ID: 134)
        new JunkEntry(HAEMALCHEMY_VOL_2, "Haemalchemy volume 2",
            "Quest complete (Sins of the Father) — tome obtained during quest, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(OLD_NOTE_SOTF, "Old note (Sins of the Father)",
            "Quest complete (Sins of the Father) — old note used during quest, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(TATTY_NOTE_SOTF, "Tatty note",
            "Quest complete (Sins of the Father) — note found in Darkmeyer, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_TOP_UNSCENTED, "Vyre noble top (unscented)",
            "Quest complete (Sins of the Father) — crafted to infiltrate Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_LEGS_UNSCENTED, "Vyre noble legs (unscented)",
            "Quest complete (Sins of the Father) — crafted to infiltrate Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_SHOES_UNSCENTED, "Vyre noble shoes (unscented)",
            "Quest complete (Sins of the Father) — crafted to infiltrate Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_TOP, "Vyre noble top",
            "Quest complete (Sins of the Father) — scented outfit for Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_LEGS, "Vyre noble legs",
            "Quest complete (Sins of the Father) — scented outfit for Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYRE_NOBLE_SHOES, "Vyre noble shoes",
            "Quest complete (Sins of the Father) — scented outfit for Darkmeyer noble quarter, no use after quest.",
            Quest.SINS_OF_THE_FATHER),
        // ---- Making History ------------------------------------------------
        // Quest.MAKING_HISTORY — confirmed (ID: 92)
        new JunkEntry(JOURNAL_MAKING_HISTORY, "Journal",
            "Quest complete (Making History) — journal recovered during quest, no use after quest.",
            Quest.MAKING_HISTORY),
        new JunkEntry(SCROLL_MAKING_HISTORY, "Scroll",
            "Quest complete (Making History) — scroll used during quest, no use after quest.",
            Quest.MAKING_HISTORY),
        new JunkEntry(CHEST_MAKING_HISTORY, "Chest",
            "Quest complete (Making History) — chest used during quest, no use after quest.",
            Quest.MAKING_HISTORY),
        // ---- Observatory Quest ---------------------------------------------
        // Quest.OBSERVATORY_QUEST — confirmed (ID: 105)
        new JunkEntry(OBSERVATORY_LENS, "Observatory lens",
            "Quest complete (Observatory Quest) — lens used to repair the observatory, no use after quest.",
            Quest.OBSERVATORY_QUEST),
        // ---- Temple of Ikov ------------------------------------------------
        // Quest.TEMPLE_OF_IKOV — confirmed (ID: 146)
        new JunkEntry(LEVER_IKOV, "Lever",
            "Quest complete (Temple of Ikov) — lever used to open doors in the temple, no use after quest.",
            Quest.TEMPLE_OF_IKOV),
        // ---- The Slug Menace -----------------------------------------------
        // Quest.THE_SLUG_MENACE — confirmed (ID: 136)
        // 5 quest-specific rune variants used to break the slug enchantment on Witchaven inhabitants.
        new JunkEntry(WATER_RUNE_SLUG_MENACE, "Water rune",
            "Quest complete (The Slug Menace) — quest-specific water rune (ID 9691), distinct from regular water rune (ID 555).",
            Quest.THE_SLUG_MENACE),
        new JunkEntry(MIND_RUNE_SLUG_MENACE, "Mind rune",
            "Quest complete (The Slug Menace) — quest-specific mind rune (ID 9697), distinct from regular mind rune (ID 558).",
            Quest.THE_SLUG_MENACE),
        new JunkEntry(FIRE_RUNE_SLUG_MENACE, "Fire rune",
            "Quest complete (The Slug Menace) — quest-specific fire rune (ID 9699), distinct from regular fire rune (ID 554).",
            Quest.THE_SLUG_MENACE),
        new JunkEntry(EARTH_RUNE_SLUG_MENACE, "Earth rune",
            "Quest complete (The Slug Menace) — quest-specific earth rune (ID 9695), distinct from regular earth rune (ID 557).",
            Quest.THE_SLUG_MENACE),
        new JunkEntry(AIR_RUNE_SLUG_MENACE, "Air rune",
            "Quest complete (The Slug Menace) — quest-specific air rune (ID 9693), distinct from regular air rune (ID 556).",
            Quest.THE_SLUG_MENACE),
        // ---- Intermediate quests with no bankable junk items ---------------
        // Audited and confirmed July 2026. All-general items — nothing quest-specific exists.
        // NOTE (July 9, 2026): At First Light, The Ascent of Arceuus, Another Slice of H.A.M.,
        // and Bone Voyage were REMOVED from this list — they do have quest-specific junk and are
        // now handled in buildBatch4 (txt<->Java reconciliation). Bone Voyage's Fossil island note
        // book (21662) remains usable after the quest and is intentionally NOT flagged.
        //   Prying Times            (PRYING_TIMES, ID 7104)
        //   Sleeping Giants         (SLEEPING_GIANTS, ID 169)
        //   Temple of the Eye       (TEMPLE_OF_THE_EYE, ID 167)
        //   Getting Ahead           (GETTING_AHEAD, ID 61)
        //   In Search of the Myreque (IN_SEARCH_OF_THE_MYREQUE, ID 79)
        //   Death to the Dorgeshuun (DEATH_TO_THE_DORGESHUUN, ID 24)
        //   A Soul's Bane           (A_SOULS_BANE, ID 138)
        //   The Giant Dwarf         (THE_GIANT_DWARF, ID 63)
        //   Lost City               (LOST_CITY, ID 86)
        //   The Hand in the Sand    (THE_HAND_IN_THE_SAND, ID 69)
        //   Tai Bwo Wannai Trio     (TAI_BWO_WANNAI_TRIO, ID 141)
        // ---- Warriors' Guild Defenders (YELLOW — no quest gate) -----------------
        // Tier hierarchy (lowest → highest): Bronze → Iron → Steel → Black → Mithril → Adamant → Rune → Dragon.
        // Dragon tier is EXCLUDED — top tier, never flagged. Each lower tier is junk when any
        // higher-tier defender is present in the bank. Broken and locked variants follow the same rule.
        // See Conditional Junk 7-4-26.txt for the full tier breakdown.
        //
        // Bronze — junk if any Iron through Dragon defender is in bank:
        new JunkEntry(BRONZE_DEFENDER,        "Bronze defender",         JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Iron defender or above is in bank.",
            IRON_DEFENDER,    IRON_DEFENDER_BROKEN,    IRON_DEFENDER_LOCKED,
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(BRONZE_DEFENDER_BROKEN, "Bronze defender (broken)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Iron defender or above is in bank.",
            IRON_DEFENDER,    IRON_DEFENDER_BROKEN,    IRON_DEFENDER_LOCKED,
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(BRONZE_DEFENDER_LOCKED, "Bronze defender (locked)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Iron defender or above is in bank.",
            IRON_DEFENDER,    IRON_DEFENDER_BROKEN,    IRON_DEFENDER_LOCKED,
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Iron — junk if any Steel through Dragon defender is in bank:
        new JunkEntry(IRON_DEFENDER,          "Iron defender",           JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Steel defender or above is in bank.",
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(IRON_DEFENDER_BROKEN,   "Iron defender (broken)",  JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Steel defender or above is in bank.",
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(IRON_DEFENDER_LOCKED,   "Iron defender (locked)",  JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Steel defender or above is in bank.",
            STEEL_DEFENDER,   STEEL_DEFENDER_BROKEN,   STEEL_DEFENDER_LOCKED,
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Steel — junk if any Black through Dragon defender is in bank:
        new JunkEntry(STEEL_DEFENDER,         "Steel defender",          JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Black defender or above is in bank.",
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(STEEL_DEFENDER_BROKEN,  "Steel defender (broken)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Black defender or above is in bank.",
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(STEEL_DEFENDER_LOCKED,  "Steel defender (locked)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Black defender or above is in bank.",
            BLACK_DEFENDER,   BLACK_DEFENDER_BROKEN,   BLACK_DEFENDER_LOCKED,
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Black — junk if any Mithril through Dragon defender is in bank:
        new JunkEntry(BLACK_DEFENDER,         "Black defender",          JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Mithril defender or above is in bank.",
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(BLACK_DEFENDER_BROKEN,  "Black defender (broken)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Mithril defender or above is in bank.",
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(BLACK_DEFENDER_LOCKED,  "Black defender (locked)", JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Mithril defender or above is in bank.",
            MITHRIL_DEFENDER, MITHRIL_DEFENDER_BROKEN, MITHRIL_DEFENDER_LOCKED,
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Mithril — junk if any Adamant through Dragon defender is in bank:
        new JunkEntry(MITHRIL_DEFENDER,       "Mithril defender",        JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Adamant defender or above is in bank.",
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(MITHRIL_DEFENDER_BROKEN,"Mithril defender (broken)",JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Adamant defender or above is in bank.",
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(MITHRIL_DEFENDER_LOCKED,"Mithril defender (locked)",JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Adamant defender or above is in bank.",
            ADAMANT_DEFENDER, ADAMANT_DEFENDER_BROKEN, ADAMANT_DEFENDER_LOCKED,
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Adamant — junk if any Rune or Dragon defender is in bank:
        new JunkEntry(ADAMANT_DEFENDER,       "Adamant defender",        JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Rune or Dragon defender is in bank.",
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(ADAMANT_DEFENDER_BROKEN,"Adamant defender (broken)",JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Rune or Dragon defender is in bank.",
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(ADAMANT_DEFENDER_LOCKED,"Adamant defender (locked)",JunkTier.YELLOW,
            "Superseded by higher-tier defender — junk if any Rune or Dragon defender is in bank.",
            RUNE_DEFENDER,    RUNE_DEFENDER_BROKEN,    RUNE_DEFENDER_LOCKED,
            RUNE_DEFENDER_T,  RUNE_DEFENDER_T_LOCKED,
            DRAGON_DEFENDER,  DRAGON_DEFENDER_BROKEN,  DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // Rune — junk if any Dragon defender is in bank:
        new JunkEntry(RUNE_DEFENDER,          "Rune defender",           JunkTier.YELLOW,
            "Superseded by Dragon defender — junk if any Dragon defender variant is in bank.",
            DRAGON_DEFENDER, DRAGON_DEFENDER_BROKEN, DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(RUNE_DEFENDER_BROKEN,   "Rune defender (broken)",  JunkTier.YELLOW,
            "Superseded by Dragon defender — junk if any Dragon defender variant is in bank.",
            DRAGON_DEFENDER, DRAGON_DEFENDER_BROKEN, DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(RUNE_DEFENDER_LOCKED,   "Rune defender (locked)",  JunkTier.YELLOW,
            "Superseded by Dragon defender — junk if any Dragon defender variant is in bank.",
            DRAGON_DEFENDER, DRAGON_DEFENDER_BROKEN, DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(RUNE_DEFENDER_T,        "Rune defender (t)",       JunkTier.YELLOW,
            "Superseded by Dragon defender — junk if any Dragon defender variant is in bank.",
            DRAGON_DEFENDER, DRAGON_DEFENDER_BROKEN, DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        new JunkEntry(RUNE_DEFENDER_T_LOCKED, "Rune defender (t) (locked)", JunkTier.YELLOW,
            "Superseded by Dragon defender — junk if any Dragon defender variant is in bank.",
            DRAGON_DEFENDER, DRAGON_DEFENDER_BROKEN, DRAGON_DEFENDER_LOCKED,
            DRAGON_DEFENDER_T, DRAGON_DEFENDER_T_LOCKED),
        // ---- Big Chompy Bird Hunting ------------------------------------------
        // Quest.BIG_CHOMPY_BIRD_HUNTING
        new JunkEntry(RAW_CHOMPY, "Raw chompy",
            "Quest complete (Big Chompy Bird Hunting) — raw chompy, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(COOKED_CHOMPY, "Cooked chompy",
            "Quest complete (Big Chompy Bird Hunting) — cooked chompy, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(SEASONED_CHOMPY, "Seasoned chompy",
            "Quest complete (Big Chompy Bird Hunting) — seasoned chompy, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(IRON_SPIT, "Iron spit",
            "Quest complete (Big Chompy Bird Hunting + RFD: Skrach Uglogwee) — no use after both quests.",
            Quest.BIG_CHOMPY_BIRD_HUNTING)
            .withRequiredQuest2(Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        // ---- My Arm's Big Adventure --------------------------------------------
        // Quest.MY_ARMS_BIG_ADVENTURE
        new JunkEntry(GOUTWEEDY_LUMP, "Goutweedy lump",
            "Quest complete (My Arm's Big Adventure) — used to grow gout tubers, no use after quest.",
            Quest.MY_ARMS_BIG_ADVENTURE),
        new JunkEntry(FARMING_MANUAL_MABA, "Farming manual",
            "Quest complete (My Arm's Big Adventure) — manual for growing gout tubers, no use after quest.",
            Quest.MY_ARMS_BIG_ADVENTURE),
        new JunkEntry(HARDY_GOUT_TUBER, "Hardy gout tuber",
            "Quest complete (My Arm's Big Adventure) — tuber for My Arm, no use after quest.",
            Quest.MY_ARMS_BIG_ADVENTURE),
        new JunkEntry(HARDY_GOUT_TUBERS, "Hardy gout tubers",
            "Quest complete (My Arm's Big Adventure) — tubers for My Arm, no use after quest.",
            Quest.MY_ARMS_BIG_ADVENTURE),
        // ---- Haunted Mine (Salve shard) ----------------------------------------
        // Quest.HAUNTED_MINE
        new JunkEntry(SALVE_SHARD, "Salve shard", JunkTier.YELLOW,
            "Shard is only junk if any Salve amulet variant is already in the bank.",
            Quest.HAUNTED_MINE, new int[]{SALVE_AMULET, SALVE_AMULET_E,
                SALVE_AMULET_I_NMZ, SALVE_AMULET_EI_NMZ,
                SALVE_AMULET_I_SW, SALVE_AMULET_EI_SW,
                SALVE_AMULET_I_EA, SALVE_AMULET_EI_EA}),
        // ---- Shadow of the Storm -----------------------------------------------
        // Quest.SHADOW_OF_THE_STORM
        new JunkEntry(DARK_DYE, "Dark dye",
            "Quest complete (Shadow of the Storm) — dye used to colour Silverlight; no use after quest.",
            Quest.SHADOW_OF_THE_STORM),
        // ---- Rag and Bone Man II -----------------------------------------------
        // Quest.RAG_AND_BONE_MAN_II
        // 27 bone types x 3 states = 81 IDs (cave goblin skull declared in RBM I constants block)
        new JunkEntry(WEREWOLF_BONE_UNPOLISHED,          "Werewolf bone",                        "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(WEREWOLF_BONE_POLISHED,            "Werewolf bone (polished)",              "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(WEREWOLF_BONE_IN_VINEGAR,          "Werewolf bone (in vinegar)",            "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(WOLF_BONE_UNPOLISHED,              "Wolf bone",                            "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(WOLF_BONE_POLISHED,                "Wolf bone (polished)",                  "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(WOLF_BONE_IN_VINEGAR,              "Wolf bone (in vinegar)",                "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BABY_DRAGON_BONE_UNPOLISHED,       "Baby dragon bone",                     "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BABY_DRAGON_BONE_POLISHED,         "Baby dragon bone (polished)",           "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BABY_DRAGON_BONE_IN_VINEGAR,       "Baby dragon bone (in vinegar)",         "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(CAVE_GOBLIN_SKULL_UNPOLISHED,      "Cave goblin skull",                    "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(CAVE_GOBLIN_SKULL_POLISHED,        "Cave goblin skull (polished)",          "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BONE_IN_VINEGAR_CAVE_GOBLIN_SKULL, "Cave goblin skull (in vinegar)",        "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BAT_WING_UNPOLISHED,               "Bat wing",                             "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BAT_WING_POLISHED,                 "Bat wing (polished)",                   "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BAT_WING_IN_VINEGAR,               "Bat wing (in vinegar)",                 "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RAT_BONE_UNPOLISHED,               "Rat bone",                             "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RAT_BONE_POLISHED,                 "Rat bone (polished)",                   "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RAT_BONE_IN_VINEGAR,               "Rat bone (in vinegar)",                 "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BASILISK_BONE_UNPOLISHED,          "Basilisk bone",                        "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BASILISK_BONE_POLISHED,            "Basilisk bone (polished)",              "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(BASILISK_BONE_IN_VINEGAR,          "Basilisk bone (in vinegar)",            "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DAGANNOTH_RIBS_UNPOLISHED,         "Dagannoth ribs",                       "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DAGANNOTH_RIBS_POLISHED,           "Dagannoth ribs (polished)",             "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DAGANNOTH_RIBS_IN_VINEGAR,         "Dagannoth ribs (in vinegar)",           "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DESERT_LIZARD_BONE_UNPOLISHED,     "Desert lizard bone",                   "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DESERT_LIZARD_BONE_POLISHED,       "Desert lizard bone (polished)",         "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(DESERT_LIZARD_BONE_IN_VINEGAR,     "Desert lizard bone (in vinegar)",       "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(EXPERIMENT_BONE_UNPOLISHED,        "Experiment bone",                      "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(EXPERIMENT_BONE_POLISHED,          "Experiment bone (polished)",            "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(EXPERIMENT_BONE_IN_VINEGAR,        "Experiment bone (in vinegar)",          "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(FIRE_GIANT_BONE_UNPOLISHED,        "Fire giant bone",                      "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(FIRE_GIANT_BONE_POLISHED,          "Fire giant bone (polished)",            "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(FIRE_GIANT_BONE_IN_VINEGAR,        "Fire giant bone (in vinegar)",          "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(GHOUL_BONE_UNPOLISHED,             "Ghoul bone",                           "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(GHOUL_BONE_POLISHED,               "Ghoul bone (polished)",                 "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(GHOUL_BONE_IN_VINEGAR,             "Ghoul bone (in vinegar)",               "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ICE_GIANT_RIBS_UNPOLISHED,         "Ice giant ribs",                       "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ICE_GIANT_RIBS_POLISHED,           "Ice giant ribs (polished)",             "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ICE_GIANT_RIBS_IN_VINEGAR,         "Ice giant ribs (in vinegar)",           "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JACKAL_BONE_UNPOLISHED,            "Jackal bone",                          "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JACKAL_BONE_POLISHED,              "Jackal bone (polished)",                "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JACKAL_BONE_IN_VINEGAR,            "Jackal bone (in vinegar)",              "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JOGRE_BONE_UNPOLISHED,             "Jogre bone",                           "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JOGRE_BONE_POLISHED,               "Jogre bone (polished)",                 "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JOGRE_BONE_IN_VINEGAR,             "Jogre bone (in vinegar)",               "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOGRE_BONE_UNPOLISHED,             "Mogre bone",                           "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOGRE_BONE_POLISHED,               "Mogre bone (polished)",                 "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOGRE_BONE_IN_VINEGAR,             "Mogre bone (in vinegar)",               "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOSS_GIANT_BONE_UNPOLISHED,        "Moss giant bone",                      "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOSS_GIANT_BONE_POLISHED,          "Moss giant bone (polished)",            "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(MOSS_GIANT_BONE_IN_VINEGAR,        "Moss giant bone (in vinegar)",          "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(OGRE_RIBS_UNPOLISHED,              "Ogre ribs",                            "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(OGRE_RIBS_POLISHED,                "Ogre ribs (polished)",                  "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(OGRE_RIBS_IN_VINEGAR,              "Ogre ribs (in vinegar)",                "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RABBIT_BONE_UNPOLISHED,            "Rabbit bone",                          "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RABBIT_BONE_POLISHED,              "Rabbit bone (polished)",                "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(RABBIT_BONE_IN_VINEGAR,            "Rabbit bone (in vinegar)",              "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SEAGULL_WING_UNPOLISHED,           "Seagull wing",                         "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SEAGULL_WING_POLISHED,             "Seagull wing (polished)",               "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SEAGULL_WING_IN_VINEGAR,           "Seagull wing (in vinegar)",             "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SNAKE_SPINE_UNPOLISHED,            "Snake spine",                          "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SNAKE_SPINE_POLISHED,              "Snake spine (polished)",                "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(SNAKE_SPINE_IN_VINEGAR,            "Snake spine (in vinegar)",              "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TERRORBIRD_WING_UNPOLISHED,        "Terrorbird wing",                      "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TERRORBIRD_WING_POLISHED,          "Terrorbird wing (polished)",            "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TERRORBIRD_WING_IN_VINEGAR,        "Terrorbird wing (in vinegar)",          "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TROLL_BONE_UNPOLISHED,             "Troll bone",                           "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TROLL_BONE_POLISHED,               "Troll bone (polished)",                 "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(TROLL_BONE_IN_VINEGAR,             "Troll bone (in vinegar)",               "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(UNDEAD_COW_RIBS_UNPOLISHED,        "Undead cow ribs",                      "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(UNDEAD_COW_RIBS_POLISHED,          "Undead cow ribs (polished)",            "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(UNDEAD_COW_RIBS_IN_VINEGAR,        "Undead cow ribs (in vinegar)",          "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(VULTURE_WING_UNPOLISHED,           "Vulture wing",                         "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(VULTURE_WING_POLISHED,             "Vulture wing (polished)",               "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(VULTURE_WING_IN_VINEGAR,           "Vulture wing (in vinegar)",             "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOGRE_BONE_UNPOLISHED,             "Zogre bone",                           "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOGRE_BONE_POLISHED,               "Zogre bone (polished)",                 "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOGRE_BONE_IN_VINEGAR,             "Zogre bone (in vinegar)",               "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOMBIE_BONE_UNPOLISHED,            "Zombie bone",                          "Quest complete (Rag and Bone Man II) — unpolished specimen.", Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOMBIE_BONE_POLISHED,              "Zombie bone (polished)",                "Quest complete (Rag and Bone Man II) — polished specimen.",   Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(ZOMBIE_BONE_IN_VINEGAR,            "Zombie bone (in vinegar)",              "Quest complete (Rag and Bone Man II) — bone in vinegar.",     Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(JUG_OF_VINEGAR_RBM, "Jug of vinegar",
            "Quest complete (Rag and Bone Man II) — used to soak bones, no use after quest.",
            Quest.RAG_AND_BONE_MAN_II),
        new JunkEntry(POT_OF_VINEGAR_RBM, "Pot of vinegar",
            "Quest complete (Rag and Bone Man II) — used to soak bones, no use after quest.",
            Quest.RAG_AND_BONE_MAN_II),
        // ---- King's Ransom -----------------------------------------------------
        // Quest.KINGS_RANSOM
        new JunkEntry(ADDRESS_FORM, "Address form",
            "Quest complete (King's Ransom) — form to access Morgan Le Faye, no use after quest.",
            Quest.KINGS_RANSOM),
        new JunkEntry(BLACK_KNIGHT_HELM_KR, "Black knight helm",
            "Quest complete (King's Ransom) — disguise helm, no use after quest.",
            Quest.KINGS_RANSOM),
        new JunkEntry(SCRAP_PAPER_KR, "Scrap paper",
            "Quest complete (King's Ransom) — notes from Camelot, no use after quest.",
            Quest.KINGS_RANSOM),
        new JunkEntry(HAIR_CLIP_KR, "Hair clip",
            "Quest complete (King's Ransom) — used to unlock cell, no use after quest.",
            Quest.KINGS_RANSOM),
        // ---- The Fremennik Isles -----------------------------------------------
        // Quest.THE_FREMENNIK_ISLES
        new JunkEntry(SPLIT_LOG_ISLES, "Split log",
            "Quest complete (The Fremennik Isles) — used to build bridge to Neitiznot, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(HAIR_ISLES, "Hair",
            "Quest complete (The Fremennik Isles) — yak hair for rope, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(YAK_HIDE, "Yak-hide",
            "Quest complete (The Fremennik Isles) — untanned yak hide, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(CURED_YAK_HIDE, "Cured yak-hide",
            "Quest complete (The Fremennik Isles) — tanned yak hide, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(ROYAL_DECREE_ISLES, "Royal decree",
            "Quest complete (The Fremennik Isles) — Brundt's decree, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(LIGHT_TAX_BAG, "Light tax bag",
            "Quest complete (The Fremennik Isles) — tax collection bag, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(NORMAL_TAX_BAG, "Normal tax bag",
            "Quest complete (The Fremennik Isles) — tax collection bag, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(HEFTY_TAX_BAG, "Hefty tax bag",
            "Quest complete (The Fremennik Isles) — tax collection bag, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(DECAPITATED_HEAD_ISLES, "Decapitated head",
            "Quest complete (The Fremennik Isles) — Slug's head, no use after quest.",
            Quest.THE_FREMENNIK_ISLES),
        // ---- The Fremennik Exiles ----------------------------------------------
        // Quest.THE_FREMENNIK_EXILES — confirmed (ID: 55)
        new JunkEntry(FANG_FREMENNIK_EXILES, "Fang (The Fremennik Exiles)",
            "Quest complete (The Fremennik Exiles) — fang item used during quest, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(V_SIGIL, "V sigil",
            "Quest complete (The Fremennik Exiles) — sigil used to craft V's shield, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(V_SIGIL_E, "V sigil (e)",
            "Quest complete (The Fremennik Exiles) — enchanted sigil, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(MOLTEN_GLASS_I, "Molten glass (i)",
            "Quest complete (The Fremennik Exiles) — imbued molten glass, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(LUNAR_GLASS, "Lunar glass",
            "Quest complete (The Fremennik Exiles) — glass made with lunar magic, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(POLISHING_ROCK, "Polishing rock",
            "Quest complete (The Fremennik Exiles) — used to polish the lunar glass, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(VS_SHIELD, "V's shield",
            "Quest complete (The Fremennik Exiles) — shield of V; no ongoing combat use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(UNSEALED_LETTER_BRUNDT, "Unsealed letter (Brundt)",
            "Quest complete (The Fremennik Exiles) — letter to Brundt, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(UNSEALED_LETTER_SANDPIT, "Unsealed letter (Sandpit)",
            "Quest complete (The Fremennik Exiles) — letter to Sandpit, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        new JunkEntry(VENOM_GLAND_FE, "Venom gland",
            "Quest complete (The Fremennik Exiles) — venom gland used during quest, no use after quest.",
            Quest.THE_FREMENNIK_EXILES),
        // ---- Darkness of Hallowvale --------------------------------------------
        // Quest.DARKNESS_OF_HALLOWVALE
        new JunkEntry(LADDER_TOP_DOH, "Ladder top",
            "Quest complete (Darkness of Hallowvale) — ladder component, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(MESSAGE_VERTIDA, "Message (Vertida)",
            "Quest complete (Darkness of Hallowvale) — message from Vertida, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CASTLE_SKETCH_1, "Castle sketch 1",
            "Quest complete (Darkness of Hallowvale) — castle floor sketch, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CASTLE_SKETCH_2, "Castle sketch 2",
            "Quest complete (Darkness of Hallowvale) — castle floor sketch, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CASTLE_SKETCH_3, "Castle sketch 3",
            "Quest complete (Darkness of Hallowvale) — castle floor sketch, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(MESSAGE_FIREPLACE, "Message (fireplace)",
            "Quest complete (Darkness of Hallowvale) — hidden message, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(LARGE_ORNATE_KEY, "Large ornate key",
            "Quest complete (Darkness of Hallowvale) — key to the castle, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(HAEMALCHEMY_VOLUME_1, "Haemalchemy volume 1",
            "Quest complete (Darkness of Hallowvale) — book of vampire alchemy, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(SEALED_MESSAGE_DOH, "Sealed message",
            "Quest complete (Darkness of Hallowvale) — sealed note, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(DOOR_KEY_DOH, "Door key",
            "Quest complete (Darkness of Hallowvale) — used to unlock doors in the castle, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CITIZEN_SHOES_DOH, "Citizen shoes",
            "Quest complete (Darkness of Hallowvale) — Meiyerditch citizen disguise, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CITIZEN_TOP_DOH, "Citizen top",
            "Quest complete (Darkness of Hallowvale) — Meiyerditch citizen disguise, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CITIZEN_TROUSERS_DOH, "Citizen trousers",
            "Quest complete (Darkness of Hallowvale) — Meiyerditch citizen disguise, no use after quest.",
            Quest.DARKNESS_OF_HALLOWVALE),
        // Vyrewatch outfit: junk only after Darkness of Hallowvale + Sins of the Father
        new JunkEntry(VYREWATCH_TOP, "Vyrewatch top",
            "Quest complete (Darkness of Hallowvale + Sins of the Father) — Vyrewatch disguise, no use after both quests.",
            Quest.DARKNESS_OF_HALLOWVALE)
            .withRequiredQuest2(Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYREWATCH_LEGS, "Vyrewatch legs",
            "Quest complete (Darkness of Hallowvale + Sins of the Father) — Vyrewatch disguise, no use after both quests.",
            Quest.DARKNESS_OF_HALLOWVALE)
            .withRequiredQuest2(Quest.SINS_OF_THE_FATHER),
        new JunkEntry(VYREWATCH_SHOES, "Vyrewatch shoes",
            "Quest complete (Darkness of Hallowvale + Sins of the Father) — Vyrewatch disguise, no use after both quests.",
            Quest.DARKNESS_OF_HALLOWVALE)
            .withRequiredQuest2(Quest.SINS_OF_THE_FATHER),
        // ---- Royal Trouble -----------------------------------------------------
        // Quest.ROYAL_TROUBLE
        new JunkEntry(SCROLL_RT, "Scroll",
            "Quest complete (Royal Trouble) — scroll used during the puzzle, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(MINING_PROP, "Mining prop",
            "Quest complete (Royal Trouble) — prop for the mine shaft puzzle, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(LIFT_MANUAL, "Lift manual",
            "Quest complete (Royal Trouble) — manual for the lift puzzle, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(ENGINE_RT, "Engine",
            "Quest complete (Royal Trouble) — engine component, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BEAM_RT, "Beam",
            "Quest complete (Royal Trouble) — beam component, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(PULLEY_BEAM, "Pulley beam",
            "Quest complete (Royal Trouble) — pulley assembly, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(LONG_PULLEY_BEAM, "Long pulley beam",
            "Quest complete (Royal Trouble) — extended pulley assembly, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(LONGER_PULLEY_BEAM, "Longer pulley beam",
            "Quest complete (Royal Trouble) — fully extended pulley assembly, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BURNT_DIARY_ONE_PAGE, "Burnt diary (one page)",
            "Quest complete (Royal Trouble) — partially burnt diary, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BURNT_DIARY_TWO_PAGES, "Burnt diary (two pages)",
            "Quest complete (Royal Trouble) — partially burnt diary, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BURNT_DIARY_THREE_PAGES, "Burnt diary (three pages)",
            "Quest complete (Royal Trouble) — partially burnt diary, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BURNT_DIARY_FOUR_PAGES, "Burnt diary (four pages)",
            "Quest complete (Royal Trouble) — mostly burnt diary, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(BURNT_DIARY_FIVE_PAGES, "Burnt diary (five pages)",
            "Quest complete (Royal Trouble) — complete burnt diary, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(LETTER_RT, "Letter",
            "Quest complete (Royal Trouble) — letter during the investigation, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(HEAVY_BOX_RT, "Heavy box",
            "Quest complete (Royal Trouble) — weighted box used in the puzzle, no use after quest.",
            Quest.ROYAL_TROUBLE),
        new JunkEntry(EMPTY_BOX_RT, "Empty box",
            "Quest complete (Royal Trouble) — empty weighted box, no use after quest.",
            Quest.ROYAL_TROUBLE),
        // ---- Cabin Fever -------------------------------------------------------
        // Quest.CABIN_FEVER
        new JunkEntry(FUSE_CF, "Fuse",
            "Quest complete (Cabin Fever) — used to fire the cannon, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(REPAIR_PLANK, "Repair plank",
            "Quest complete (Cabin Fever) — used to repair the ship, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(TACKS, "Tacks",
            "Quest complete (Cabin Fever) — used to repair the ship, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(PLUNDER_CF, "Plunder",
            "Quest complete (Cabin Fever) — plundered cargo, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(CANNON_BARREL_CF, "Cannon barrel",
            "Quest complete (Cabin Fever) — ship cannon component, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(CANISTER_CF, "Canister",
            "Quest complete (Cabin Fever) — cannon shot canister, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(GUNPOWDER_CF, "Gunpowder",
            "Quest complete (Cabin Fever) — cannon propellant, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(CANNON_BALL_CF, "Cannon ball",
            "Quest complete (Cabin Fever) — quest-specific cannon ball, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(RAMROD, "Ramrod",
            "Quest complete (Cabin Fever) — used to load the cannon, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(BROKEN_CANNON, "Broken cannon",
            "Quest complete (Cabin Fever) — damaged ship cannon, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(ROPE_CF, "Rope",
            "Quest complete (Cabin Fever) — quest-specific rope, no use after quest.",
            Quest.CABIN_FEVER),
        new JunkEntry(TINDERBOX_CF, "Tinderbox",
            "Quest complete (Cabin Fever) — quest-specific tinderbox, no use after quest.",
            Quest.CABIN_FEVER),
        // ---- Rum Deal ----------------------------------------------------------
        // Quest.RUM_DEAL
        new JunkEntry(BUCKET_OF_WATER_RD, "Bucket of water",
            "Quest complete (Rum Deal) — quest-specific bucket of water, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(BLINDWEED, "Blindweed",
            "Quest complete (Rum Deal) — plant used in the rum, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(BLINDWEED_SEED, "Blindweed seed",
            "Quest complete (Rum Deal) — seed for growing blindweed, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(SLUGLINGS, "Sluglings",
            "Quest complete (Rum Deal) — used in the rum recipe, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(KARAMTHULHU, "Karamthulhu",
            "Quest complete (Rum Deal) — used in the rum recipe, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(FISHBOWL_AND_NET, "Fishbowl and net",
            "Quest complete (Rum Deal) — used to capture Karamthulhu, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(WRENCH_RD, "Wrench",
            "Quest complete (Rum Deal) — used to fix the still, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(FEVER_SPIDER_BODY, "Fever spider body",
            "Quest complete (Rum Deal) — used in the rum recipe, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(UNSANITARY_SWILL, "Unsanitary swill",
            "Quest complete (Rum Deal) — failed rum mixture, no use after quest.",
            Quest.RUM_DEAL),
        new JunkEntry(BRAINDEATH_RUM, "Braindeath 'rum'",
            "Quest complete (Rum Deal) — finished quest item, no use after quest.",
            Quest.RUM_DEAL),
        // ---- Between a Rock... -------------------------------------------------
        // Quest.BETWEEN_A_ROCK
        new JunkEntry(GOLD_HELMET, "Gold helmet",
            "Quest complete (Between a Rock...) — obtained from Dondakan, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(DWARVEN_LORE, "Dwarven lore",
            "Quest complete (Between a Rock...) — book given by Dondakan, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(BOOK_PAGE_1_BAR, "Book page 1",
            "Quest complete (Between a Rock...) — page from the engineering tome, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(BOOK_PAGE_2_BAR, "Book page 2",
            "Quest complete (Between a Rock...) — page from the engineering tome, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(BOOK_PAGE_3_BAR, "Book page 3",
            "Quest complete (Between a Rock...) — page from the engineering tome, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(PAGES_BAR, "Pages",
            "Quest complete (Between a Rock...) — combined book pages (auto-formed from pages 1–3), no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(BASE_SCHEMATICS, "Base schematics",
            "Quest complete (Between a Rock...) — incomplete cannon design, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(SCHEMATIC_DONDAKAN, "Schematic",
            "Quest complete (Between a Rock...) — Dondakan's schematic, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(SCHEMATICS_DWARF_ENGINEER, "Schematics",
            "Quest complete (Between a Rock...) — engineer's schematics, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(SCHEMATICS_KHORVAK, "Schematics",
            "Quest complete (Between a Rock...) — Khorvak's schematics, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(SCHEMATIC_COMPLETE, "Schematic (complete)",
            "Quest complete (Between a Rock...) — completed cannon schematic, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        new JunkEntry(CANNON_BALL_BAR, "Cannon ball",
            "Quest complete (Between a Rock...) — quest-specific cannon ball, no use after quest.",
            Quest.BETWEEN_A_ROCK),
        // ---- Roving Elves ------------------------------------------------------
        // Quest.ROVING_ELVES
        new JunkEntry(CONSECRATION_SEED_UNENCHANTED, "Consecration seed",
            "Quest complete (Roving Elves) — unenchanted seed, no use after quest.",
            Quest.ROVING_ELVES),
        new JunkEntry(CONSECRATION_SEED_ENCHANTED, "Consecration seed (enchanted)",
            "Quest complete (Roving Elves) — enchanted seed, no use after quest.",
            Quest.ROVING_ELVES),
        // Glarial's pebble: junk only after both Roving Elves + Waterfall Quest
        new JunkEntry(GLARIAL_PEBBLE, "Glarial's pebble",
            "Used in Waterfall Quest and Roving Elves — junk only after both quests are complete.",
            Quest.ROVING_ELVES)
            .withRequiredQuest2(Quest.WATERFALL_QUEST),
        // ---- Throne of Miscellania ---------------------------------------------
        // Quest.THRONE_OF_MISCELLANIA
        new JunkEntry(AWFUL_ANTHEM, "Awful anthem",
            "Quest complete (Throne of Miscellania) — bad attempt at writing anthem, no use after quest.",
            Quest.THRONE_OF_MISCELLANIA),
        new JunkEntry(GOOD_ANTHEM, "Good anthem",
            "Quest complete (Throne of Miscellania) — successful anthem, no use after quest.",
            Quest.THRONE_OF_MISCELLANIA),
        new JunkEntry(TREATY_TOM, "Treaty",
            "Quest complete (Throne of Miscellania) — peace treaty, no use after quest.",
            Quest.THRONE_OF_MISCELLANIA),
        new JunkEntry(GIANT_NIB, "Giant nib",
            "Quest complete (Throne of Miscellania) — oversized pen nib, no use after quest.",
            Quest.THRONE_OF_MISCELLANIA),
        new JunkEntry(GIANT_PEN, "Giant pen",
            "Quest complete (Throne of Miscellania) — oversized pen, no use after quest.",
            Quest.THRONE_OF_MISCELLANIA),
        // ---- Regicide ----------------------------------------------------------
        // Quest.REGICIDE
        new JunkEntry(KINGS_MESSAGE, "King's message",
            "Quest complete (Regicide) — message from King Lathas, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(IORWERTHS_MESSAGE, "Iorwerth's message",
            "Quest complete (Regicide) — message from Lord Iorwerth, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(CRYSTAL_PENDANT_REG, "Crystal pendant",
            "Quest complete (Regicide) — pendant used to access the Underground Pass, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(SULPHUR_REG, "Sulphur",
            "Quest complete (Regicide) — ingredient for barrel bomb, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(QUICKLIME, "Quicklime",
            "Quest complete (Regicide) — ingredient for barrel bomb, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(POT_OF_QUICKLIME, "Pot of quicklime",
            "Quest complete (Regicide) — quicklime in a pot, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(GROUND_SULPHUR, "Ground sulphur",
            "Quest complete (Regicide) — ground ingredient, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(BARREL_BOMB_UNFUSED, "Barrel bomb (unfused)",
            "Quest complete (Regicide) — unexploded bomb, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(NAPHTHA_MIX_SULPHUR, "Naphtha mix (sulphur)",
            "Quest complete (Regicide) — bomb ingredient mixture, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(NAPHTHA_MIX_QUICKLIME, "Naphtha mix (quicklime)",
            "Quest complete (Regicide) — bomb ingredient mixture, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(BIG_BOOK_OF_BANGS, "Big book of bangs",
            "Quest complete (Regicide) — bomb-making manual, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(BARREL_BOMB_FUSED, "Barrel bomb (fused)",
            "Quest complete (Regicide) — primed bomb, no use after quest.",
            Quest.REGICIDE),
        new JunkEntry(STRIP_OF_CLOTH_REG, "Strip of cloth",
            "Quest complete (Regicide) — fuse material, no use after quest.",
            Quest.REGICIDE),
        // Coal tar and naphtha: junk only after Regicide + Mourning's End Part I
        new JunkEntry(BARREL_OF_NAPHTHA, "Barrel of naphtha",
            "Quest complete (Regicide + Mourning's End Part I) — no use after both quests.",
            Quest.REGICIDE)
            .withRequiredQuest2(Quest.MOURNINGS_END_PART_I),
        new JunkEntry(BARREL_OF_COAL_TAR, "Barrel of coal tar",
            "Quest complete (Regicide + Mourning's End Part I) — no use after both quests.",
            Quest.REGICIDE)
            .withRequiredQuest2(Quest.MOURNINGS_END_PART_I),
        // ---- The Path of Glouphrie ---------------------------------------------
        // Quest.THE_PATH_OF_GLOUPHRIE
        new JunkEntry(CHEST_KEY_POG, "Chest key",
            "Quest complete (The Path of Glouphrie) — key to the locked chest, no use after quest.",
            Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(STRONGROOM_KEY, "Strongroom key",
            "Quest complete (The Path of Glouphrie) — key to the strongroom, no use after quest.",
            Quest.THE_PATH_OF_GLOUPHRIE),
        new JunkEntry(YEWNOCKS_NOTES, "Yewnock's notes",
            "Quest complete (The Path of Glouphrie) — research notes, no use after quest.",
            Quest.THE_PATH_OF_GLOUPHRIE),
        // ---- A Kingdom Divided -------------------------------------------------
        // Quest.A_KINGDOM_DIVIDED
        new JunkEntry(RECEIPT_AKD, "Receipt",
            "Quest complete (A Kingdom Divided) — receipt from Shayzien, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(BONE_AKD, "Bone",
            "Quest complete (A Kingdom Divided) — bone found during investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSE_AKD, "Rose",
            "Quest complete (A Kingdom Divided) — rose from the courier, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(DELIVERY_CONFIRMATION, "Delivery confirmation",
            "Quest complete (A Kingdom Divided) — delivery receipt, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ORDER_FORM_AKD, "Order form",
            "Quest complete (A Kingdom Divided) — suspicious order form, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(DEMONIC_INCANTATIONS, "Demonic incantations",
            "Quest complete (A Kingdom Divided) — book of cultist rituals, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(BLOODY_KNIFE, "Bloody knife",
            "Quest complete (A Kingdom Divided) — evidence of the conspiracy, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(CULTIST_ROBE_AKD, "Cultist robe",
            "Quest complete (A Kingdom Divided) — cultist disguise, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(KOUREND_MAP, "Kourend map",
            "Quest complete (A Kingdom Divided) — map of Kourend, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSES_DIARY, "Rose's diary",
            "Quest complete (A Kingdom Divided) — Rose's personal diary, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(BLUISH_KEY, "Bluish key",
            "Quest complete (A Kingdom Divided) — key used during investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(COLD_KEY, "Cold key",
            "Quest complete (A Kingdom Divided) — key used during investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(DAMP_KEY, "Damp key",
            "Quest complete (A Kingdom Divided) — key used during investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSES_NOTE_MARTIN_HOLT, "Rose's note (Martin Holt)",
            "Quest complete (A Kingdom Divided) — note from Rose, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSES_NOTE_FORTHOS_RUIN, "Rose's note (Forthos Ruin)",
            "Quest complete (A Kingdom Divided) — note from Rose, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSES_NOTE_SETTLEMENT_RUINS, "Rose's note (Settlement Ruins)",
            "Quest complete (A Kingdom Divided) — note from Rose, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROSES_NOTE_THE_LEGLESS_FAUN, "Rose's note (The Legless Faun)",
            "Quest complete (A Kingdom Divided) — note from Rose, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(LIZARDMAN_EGG, "Lizardman egg",
            "Quest complete (A Kingdom Divided) — egg from Shayzien, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(BROKEN_REDIRECTOR, "Broken redirector",
            "Quest complete (A Kingdom Divided) — broken device, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(SULPHUR_POTION, "Sulphur potion",
            "Quest complete (A Kingdom Divided) — potion used during the quest, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(SHIELDING_POTION, "Shielding potion",
            "Quest complete (A Kingdom Divided) — potion used during the quest, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(DECLARATION_AKD, "Declaration",
            "Quest complete (A Kingdom Divided) — declaration document, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(DARK_NULLIFIER, "Dark nullifier",
            "Quest complete (A Kingdom Divided) — device to nullify dark magic, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(SHAYZIEN_JOURNAL, "Shayzien journal",
            "Quest complete (A Kingdom Divided) — journal of Shayzien politics, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(ROYAL_ACCORD_OF_TWILL, "Royal accord of twill",
            "Quest complete (A Kingdom Divided) — royal agreement document, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(PROTEST_BANNER, "Protest banner",
            "Quest complete (A Kingdom Divided) — banner from the protesters, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(RESEARCH_NOTES_AKD, "Research notes",
            "Quest complete (A Kingdom Divided) — notes from the investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        new JunkEntry(TATTY_NOTE, "Tatty note",
            "Quest complete (A Kingdom Divided) — worn note from investigation, no use after quest.",
            Quest.A_KINGDOM_DIVIDED),
        // ---- Shadows of Custodia -----------------------------------------------
        // Quest.SHADOWS_OF_CUSTODIA
        new JunkEntry(WET_FABRIC_SCRAP, "Wet fabric scrap",
            "Quest complete (Shadows of Custodia) — no use after quest.",
            Quest.SHADOWS_OF_CUSTODIA),
        // ---- The Red Reef ------------------------------------------------------
        // Quest.THE_RED_REEF
        new JunkEntry(DEEP_SEA_HELMET, "Deep sea helmet",
            "Quest complete (The Red Reef) — diving helmet, no use after quest.",
            Quest.THE_RED_REEF),
        new JunkEntry(DEEP_SEA_APPARATUS, "Deep sea apparatus",
            "Quest complete (The Red Reef) — diving equipment, no use after quest.",
            Quest.THE_RED_REEF),
        // ---- Troubled Tortugans ------------------------------------------------
        // Quest.TROUBLED_TORTUGANS
        new JunkEntry(MAKESHIFT_BANDAGES, "Makeshift bandages",
            "Quest complete (Troubled Tortugans) — bandages for injured tortoises, no use after quest.",
            Quest.TROUBLED_TORTUGANS),
        new JunkEntry(TORTUGAN_SCUTE, "Tortugan scute",
            "Quest complete (Troubled Tortugans) — shell piece from tortoise, no use after quest.",
            Quest.TROUBLED_TORTUGANS),
        new JunkEntry(SEA_SHELL_TT, "Sea shell",
            "Quest complete (Troubled Tortugans) — used in the repair process, no use after quest.",
            Quest.TROUBLED_TORTUGANS),
        new JunkEntry(LIST_OF_REPAIRS, "List of repairs",
            "Quest complete (Troubled Tortugans) — list of repairs needed, no use after quest.",
            Quest.TROUBLED_TORTUGANS),
        // ---- Meat and Greet ----------------------------------------------------
        // Quest.MEAT_AND_GREET
        new JunkEntry(TEST_KEBAB_CONNOISSEUR, "Test kebab (Connoisseur)",
            "Quest complete (Meat and Greet) — test kebab variant, no use after quest.",
            Quest.MEAT_AND_GREET),
        new JunkEntry(TEST_KEBAB_LELIA, "Test kebab (Lelia)",
            "Quest complete (Meat and Greet) — test kebab variant, no use after quest.",
            Quest.MEAT_AND_GREET),
        // ---- The Heart of Darkness ---------------------------------------------
        // Quest.THE_HEART_OF_DARKNESS
        new JunkEntry(STONE_TABLET_THOD, "Stone tablet",
            "Quest complete (The Heart of Darkness) — stone inscription, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(TOWER_KEY_THOD, "Tower key",
            "Quest complete (The Heart of Darkness) — key to the lighthouse tower, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(BOOK_THOD, "Book",
            "Quest complete (The Heart of Darkness) — book found in the lighthouse, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(POEM_THOD, "Poem",
            "Quest complete (The Heart of Darkness) — poem clue, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(SCRAP_OF_PAPER_1, "Scrap of paper (1)",
            "Quest complete (The Heart of Darkness) — torn note fragment, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(SCRAP_OF_PAPER_2, "Scrap of paper (2)",
            "Quest complete (The Heart of Darkness) — torn note fragment, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(SCRAP_OF_PAPER_3, "Scrap of paper (3)",
            "Quest complete (The Heart of Darkness) — torn note fragment, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(COMPLETED_NOTE_THOD, "Completed note",
            "Quest complete (The Heart of Darkness) — assembled note, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(BANDAGES_THOD, "Bandages",
            "Quest complete (The Heart of Darkness) — used during the quest, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(ICON_FIRE_THOD, "Icon (fire)",
            "Quest complete (The Heart of Darkness) — elemental icon, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(ICON_EARTH_THOD, "Icon (earth)",
            "Quest complete (The Heart of Darkness) — elemental icon, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(ICON_AIR_THOD, "Icon (air)",
            "Quest complete (The Heart of Darkness) — elemental icon, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        new JunkEntry(ICON_WATER_THOD, "Icon (water)",
            "Quest complete (The Heart of Darkness) — elemental icon, no use after quest.",
            Quest.THE_HEART_OF_DARKNESS),
        // ---- Defender of Varrock -----------------------------------------------
        // Quest.DEFENDER_OF_VARROCK
        new JunkEntry(GRUBBY_KEY_DOV, "Grubby key",
            "Quest complete (Defender of Varrock) — key to the hideout, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(BOTTLE_DOV, "Bottle",
            "Quest complete (Defender of Varrock) — empty bottle used in the quest, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(BOTTLE_OF_MIST, "Bottle of mist",
            "Quest complete (Defender of Varrock) — bottle filled with mist, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(IMBUED_BARRONITE, "Imbued barronite",
            "Quest complete (Defender of Varrock) — magically enhanced barronite, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(SHIELD_OF_ARRAV_ITEM, "Shield of arrav",
            "Quest complete (Defender of Varrock) — replica shield used in the quest, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(LIST_OF_ELDERS, "List of elders",
            "Quest complete (Defender of Varrock) — list of Shield of Arrav elders, no use after quest.",
            Quest.DEFENDER_OF_VARROCK),
        // ---- A Taste of Hope ---------------------------------------------------
        // Quest.A_TASTE_OF_HOPE
        new JunkEntry(MYSTERIOUS_HERB_ATOH, "Mysterious herb",
            "Quest complete (A Taste of Hope) — mysterious herb from Darkmeyer, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(MYSTERIOUS_MEAT, "Mysterious meat",
            "Quest complete (A Taste of Hope) — mysterious meat from Darkmeyer, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(MYSTERIOUS_CRUSHED_MEAT, "Mysterious crushed meat",
            "Quest complete (A Taste of Hope) — crushed mysterious meat, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(VIAL_OF_BLOOD_ATOH, "Vial of blood",
            "Quest complete (A Taste of Hope) — blood for the potion, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(UNFINISHED_BLOOD_POTION, "Unfinished blood potion",
            "Quest complete (A Taste of Hope) — partial blood potion, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(BLOOD_POTION, "Blood potion",
            "Quest complete (A Taste of Hope) — completed blood potion, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(UNFINISHED_POTION_ATOH, "Unfinished potion",
            "Quest complete (A Taste of Hope) — partial potion, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(POTION_ATOH, "Potion",
            "Quest complete (A Taste of Hope) — finished potion used in the quest, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(OLD_NOTES_ATOH, "Old notes",
            "Quest complete (A Taste of Hope) — old research notes, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(OLD_DIARY_ATOH, "Old diary",
            "Quest complete (A Taste of Hope) — old diary from Darkmeyer, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(FLAYGIANS_NOTES, "Flaygian's notes",
            "Quest complete (A Taste of Hope) — Flaygian Screwte's notes, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(CHAIN_ATOH, "Chain",
            "Quest complete (A Taste of Hope) — chain used in the quest, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        new JunkEntry(EMERALD_SICKLE_B, "Emerald sickle (b)",
            "Quest complete (A Taste of Hope) — blessed emerald sickle, no use after quest.",
            Quest.A_TASTE_OF_HOPE),
        // ---- A Night at the Theatre --------------------------------------------
        // Quest.A_NIGHT_AT_THE_THEATRE — confirmed (ID: 104)
        new JunkEntry(CRYPT_KEY_ANATT, "Crypt key",
            "Quest complete (A Night at the Theatre) — key used to enter the Theatre of Blood, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        new JunkEntry(RANIS_HEAD, "Ranis' head",
            "Quest complete (A Night at the Theatre) — severed head quest item, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        new JunkEntry(STRANGE_SPIDER_EGGS, "Strange spider eggs",
            "Quest complete (A Night at the Theatre) — quest item, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        new JunkEntry(SULPHURIC_ACID_ANATT, "Sulphuric acid",
            "Quest complete (A Night at the Theatre) — acid used during quest, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        new JunkEntry(STICKY_NOTE_ANATT, "Sticky note",
            "Quest complete (A Night at the Theatre) — note used during quest, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        new JunkEntry(HESPORI_BARK, "Hespori bark",
            "Quest complete (A Night at the Theatre) — bark item used during quest, no use after quest.",
            Quest.A_NIGHT_AT_THE_THEATRE),
        // ---- Perilous Moons ----------------------------------------------------
        // Quest.PERILOUS_MOONS — confirmed (ID: 3514)
        new JunkEntry(ENCHANTED_WATER_TALISMAN_PM, "Enchanted water talisman",
            "Quest complete (Perilous Moons) — talisman used in ritual, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(ENCHANTED_EARTH_TALISMAN_PM, "Enchanted earth talisman",
            "Quest complete (Perilous Moons) — talisman used in ritual, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(INFUSED_WATER_TALISMAN, "Infused water talisman",
            "Quest complete (Perilous Moons) — talisman infused with power, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(INFUSED_EARTH_TALISMAN, "Infused earth talisman",
            "Quest complete (Perilous Moons) — talisman infused with power, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(BUILDING_SUPPLIES_PM, "Building supplies",
            "Quest complete (Perilous Moons) — supplies used to build the daemonheim ritual site, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(MOSS_LIZARD_TAIL, "Moss lizard tail",
            "Quest complete (Perilous Moons) — tail ingredient for ritual, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(BREAM_SCALES, "Bream scales",
            "Quest complete (Perilous Moons) — scales ingredient for ritual, no use after quest.",
            Quest.PERILOUS_MOONS),
        new JunkEntry(MOONLIGHT_GRUB_PASTE, "Moonlight grub paste",
            "Quest complete (Perilous Moons) — paste ingredient for ritual, no use after quest.",
            Quest.PERILOUS_MOONS),
        // ---- The Final Dawn ----------------------------------------------------
        // Quest.THE_FINAL_DAWN — confirmed (ID: 5189)
        new JunkEntry(ANCIENT_TELEPORTER, "Ancient teleporter",
            "Quest complete (The Final Dawn) — teleporter used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(BRANCH_FINAL_DAWN, "Branch (The Final Dawn)",
            "Quest complete (The Final Dawn) — branch item used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(EMISSARY_SCROLL_TFD, "Emissary scroll",
            "Quest complete (The Final Dawn) — scroll used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(KEY_FOREBEARER_JANUS, "Key (Forebearer Janus)",
            "Quest complete (The Final Dawn) — key to Janus's chamber, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(KEY_FINAL_DAWN, "Key (The Final Dawn)",
            "Quest complete (The Final Dawn) — key used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(MAKESHIFT_BLACKJACK, "Makeshift blackjack",
            "Quest complete (The Final Dawn) — improvised weapon used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(POTATO_SACK_TFD, "Potato sack",
            "Quest complete (The Final Dawn) — sack used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(STONE_TABLET_TFD_MOKHAIOTL, "Stone tablet (Mokhaiotl)",
            "Quest complete (The Final Dawn) — stone tablet, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(STONE_TABLET_TFD_PUZZLE, "Stone tablet (puzzle)",
            "Quest complete (The Final Dawn) — puzzle tablet used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(ANCIENT_ROOTS_TFD, "Ancient roots",
            "Quest complete (The Final Dawn) — ancient roots used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(CANVAS_PIECE_TFD, "Canvas piece",
            "Quest complete (The Final Dawn) — canvas piece used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(KEYSTONE_FRAGMENT_TFD, "Keystone fragment",
            "Quest complete (The Final Dawn) — keystone fragment used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(KNIFE_BLADE_TFD, "Knife blade",
            "Quest complete (The Final Dawn) — knife blade used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(KUHU_ESSENCE, "Kuhu essence",
            "Quest complete (The Final Dawn) — essence used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        new JunkEntry(ROOT_KINDLING_TFD, "Root kindling",
            "Quest complete (The Final Dawn) — kindling used during quest, no use after quest.",
            Quest.THE_FINAL_DAWN),
        // ---- Miniquests ----------------------------------------------------
        // Quest.ALFRED_GRIMHANDS_BARCRAWL — confirmed (ID: 4)
        new JunkEntry(BARCRAWL_CARD, "Barcrawl card",
            "Quest complete (Alfred Grimhand's Barcrawl) — card filled in, no use after quest.",
            Quest.ALFRED_GRIMHANDS_BARCRAWL),
        // Quest.BEAR_YOUR_SOUL — confirmed (ID: 5)
        new JunkEntry(DAMAGED_SOUL_BEARER, "Damaged soul bearer",
            "Quest complete (Bear Your Soul) — soul bearer retrieved, no further use.",
            Quest.BEAR_YOUR_SOUL),
        new JunkEntry(SOUL_JOURNEY, "Soul journey",
            "Quest complete (Bear Your Soul) — dialogue item, no further use.",
            Quest.BEAR_YOUR_SOUL),
        // Quest.DADDYS_HOME — confirmed (ID: 21)
        new JunkEntry(MARLOS_CRATE, "Marlo's crate",
            "Quest complete (Daddy's Home) — crate used to rebuild furniture, no use after quest.",
            Quest.DADDYS_HOME),
        new JunkEntry(WAXWOOD_LOG, "Waxwood log",
            "Quest complete (Daddy's Home) — crafting material used in quest, no use after quest.",
            Quest.DADDYS_HOME),
        new JunkEntry(WAXWOOD_PLANK, "Waxwood plank",
            "Quest complete (Daddy's Home) — crafting material used in quest, no use after quest.",
            Quest.DADDYS_HOME),
        // Quest.THE_ENCHANTED_KEY — confirmed (ID: 41)
        new JunkEntry(ENCHANTED_KEY_MQ, "Enchanted key",
            "Quest complete (The Enchanted Key) — key used to unlock treasure, no use after quest.",
            Quest.THE_ENCHANTED_KEY),
        new JunkEntry(GUTHIX_MJOLNIR, "Guthix mjolnir",
            "Quest complete (The Enchanted Key) — cosmetic reward, no combat/skill use.",
            Quest.THE_ENCHANTED_KEY),
        new JunkEntry(SARADOMIN_MJOLNIR, "Saradomin mjolnir",
            "Quest complete (The Enchanted Key) — cosmetic reward, no combat/skill use.",
            Quest.THE_ENCHANTED_KEY),
        new JunkEntry(ZAMORAK_MJOLNIR, "Zamorak mjolnir",
            "Quest complete (The Enchanted Key) — cosmetic reward, no combat/skill use.",
            Quest.THE_ENCHANTED_KEY),
        // Quest.ENTER_THE_ABYSS — confirmed (ID: 43)
        new JunkEntry(ABYSSAL_BOOK, "Abyssal book",
            "Quest complete (Enter the Abyss) — lore book, no further use.",
            Quest.ENTER_THE_ABYSS),
        // Quest.THE_FROZEN_DOOR — confirmed (ID: 164)
        new JunkEntry(IMPORTANT_LETTER_FROZEN_DOOR, "Important letter",
            "Quest complete (The Frozen Door) — letter delivered, no further use.",
            Quest.THE_FROZEN_DOOR),
        new JunkEntry(FROZEN_KEY, "Frozen key",
            "Quest complete (The Frozen Door) — assembled key used to unlock the Frozen Door, no use after quest.",
            Quest.THE_FROZEN_DOOR),
        new JunkEntry(FROZEN_KEY_PIECE_ARMADYL, "Frozen key piece (armadyl)",
            "Quest complete (The Frozen Door) — armadyl piece of the frozen key, no use after quest.",
            Quest.THE_FROZEN_DOOR),
        new JunkEntry(FROZEN_KEY_PIECE_BANDOS, "Frozen key piece (bandos)",
            "Quest complete (The Frozen Door) — bandos piece of the frozen key, no use after quest.",
            Quest.THE_FROZEN_DOOR),
        new JunkEntry(FROZEN_KEY_PIECE_ZAMORAK, "Frozen key piece (zamorak)",
            "Quest complete (The Frozen Door) — zamorak piece of the frozen key, no use after quest.",
            Quest.THE_FROZEN_DOOR),
        new JunkEntry(FROZEN_KEY_PIECE_SARADOMIN, "Frozen key piece (saradomin)",
            "Quest complete (The Frozen Door) — saradomin piece of the frozen key, no use after quest.",
            Quest.THE_FROZEN_DOOR),
        // Quest.THE_GENERALS_SHADOW — confirmed (ID: 59)
        new JunkEntry(SEVERED_LEG, "Severed leg",
            "Quest complete (The General's Shadow) — quest prop, no further use.",
            Quest.THE_GENERALS_SHADOW),
        new JunkEntry(SIN_SEERS_NOTE, "Sin seer's note",
            "Quest complete (The General's Shadow) — lore note, no further use.",
            Quest.THE_GENERALS_SHADOW),
        // Quest.HIS_FAITHFUL_SERVANTS — confirmed (ID: 3250)
        new JunkEntry(CRYPT_MAP, "Crypt map",
            "Quest complete (His Faithful Servants) — map used in quest, no further use.",
            Quest.HIS_FAITHFUL_SERVANTS),
        new JunkEntry(STRANGE_ICON_HFS, "Strange icon",
            "Quest complete (His Faithful Servants) — icon handed over, no further use.",
            Quest.HIS_FAITHFUL_SERVANTS),
        // Quest.IN_SEARCH_OF_KNOWLEDGE — confirmed (ID: 78)
        new JunkEntry(TATTERED_MOON_PAGE, "Tattered moon page",
            "Quest complete (In Search of Knowledge) — page assembled into tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        new JunkEntry(TATTERED_SUN_PAGE, "Tattered sun page",
            "Quest complete (In Search of Knowledge) — page assembled into tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        new JunkEntry(TATTERED_TEMPLE_PAGE, "Tattered temple page",
            "Quest complete (In Search of Knowledge) — page assembled into tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        new JunkEntry(TOME_OF_THE_MOON, "Tome of the moon",
            "Quest complete (In Search of Knowledge) — assembled tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        new JunkEntry(TOME_OF_THE_SUN, "Tome of the sun",
            "Quest complete (In Search of Knowledge) — assembled tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        new JunkEntry(TOME_OF_THE_TEMPLE, "Tome of the temple",
            "Quest complete (In Search of Knowledge) — assembled tome, no further use.",
            Quest.IN_SEARCH_OF_KNOWLEDGE),
        // Quest.VALE_TOTEMS — confirmed (ID: 5194)
        new JunkEntry(SACRAMENTS_OF_ENT_FOLK, "Sacraments of ent folk",
            "Quest complete (Vale Totems) — ceremonial item used in quest, no further use.",
            Quest.VALE_TOTEMS)
        // Dragon tier
        , // ---- Hopespear's Will -----------------------------------------
        // Quest.HOPESPEARS_WILL — confirmed
        new JunkEntry(SNOTHEAD_BONE, "Snothead's bone",
            "Quest complete (Hopespear's Will) — bone of a goblin general, no use after quest.",
            Quest.HOPESPEARS_WILL),
        new JunkEntry(SNAILFEET_BONE, "Snailfeet's bone",
            "Quest complete (Hopespear's Will) — bone of a goblin general, no use after quest.",
            Quest.HOPESPEARS_WILL),
        new JunkEntry(MOSSCHIN_BONE, "Mosschin's bone",
            "Quest complete (Hopespear's Will) — bone of a goblin general, no use after quest.",
            Quest.HOPESPEARS_WILL),
        new JunkEntry(REDEYES_BONE, "Redeyes's bone",
            "Quest complete (Hopespear's Will) — bone of a goblin general, no use after quest.",
            Quest.HOPESPEARS_WILL),
        new JunkEntry(STRONGBONES_BONE, "Strongbones's bone",
            "Quest complete (Hopespear's Will) — bone of a goblin general, no use after quest.",
            Quest.HOPESPEARS_WILL) // (12954/20463/24143/19722/27008) — TOP TIER, never flagged. See Conditional Junk 7-4-26.txt.
    ); }
    // =========================================================================
    // Batch 8: txt<->Java reconciliation (July 9, 2026)
    // Quest-specific RED items present in Quest Items 7-6-26.txt but previously
    // absent from Java (several quests were wrongly listed as "no bankable junk").
    // =========================================================================
    private static List<JunkEntry> buildBatch4() { return Arrays.asList(
        // ---- Another Slice of H.A.M. (Quest.ANOTHER_SLICE_OF_HAM) ----
        new JunkEntry(ARMOUR_SHARD, "Armour shard",
            "Quest complete (Another Slice of H.A.M.) — artefact component, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(AXE_HEAD_ASOH, "Axe head",
            "Quest complete (Another Slice of H.A.M.) — artefact component, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(HELMET_FRAGMENT_ASOH, "Helmet fragment",
            "Quest complete (Another Slice of H.A.M.) — artefact fragment, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(SHIELD_FRAGMENT_ASOH, "Shield fragment",
            "Quest complete (Another Slice of H.A.M.) — artefact fragment, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(SWORD_FRAGMENT_ASOH, "Sword fragment",
            "Quest complete (Another Slice of H.A.M.) — artefact fragment, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_ARMOUR, "Artefact (armour)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_AXE, "Artefact (axe)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_HELMET, "Artefact (helmet)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_SHIELD, "Artefact (shield)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_SWORD, "Artefact (sword)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(ARTEFACT_MACE, "Artefact (mace)",
            "Quest complete (Another Slice of H.A.M.) — reconstructed artefact, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        new JunkEntry(MACE_HAM, "Mace (H.A.M.)",
            "Quest complete (Another Slice of H.A.M.) — quest mace, no use after quest.",
            Quest.ANOTHER_SLICE_OF_HAM),
        // ---- At First Light (Quest.AT_FIRST_LIGHT) ----
        new JunkEntry(SMOOTH_LEAF, "Smooth leaf",
            "Quest complete (At First Light) — poultice component, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        new JunkEntry(STICKY_LEAF, "Sticky leaf",
            "Quest complete (At First Light) — poultice component, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        new JunkEntry(MAKESHIFT_POULTICE, "Makeshift poultice",
            "Quest complete (At First Light) — poultice, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        new JunkEntry(FUR_SAMPLE, "Fur sample",
            "Quest complete (At First Light) — fur sample, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        new JunkEntry(TRIMMED_FUR, "Trimmed fur",
            "Quest complete (At First Light) — trimmed fur, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        new JunkEntry(FOXS_REPORT, "Fox's report",
            "Quest complete (At First Light) — report, no use after quest.",
            Quest.AT_FIRST_LIGHT),
        // ---- Below Ice Mountain (Quest.BELOW_ICE_MOUNTAIN) ----
        new JunkEntry(STEAK_SANDWICH_BIM, "Steak sandwich",
            "Quest complete (Below Ice Mountain) — quest-specific steak sandwich, no use after quest.",
            Quest.BELOW_ICE_MOUNTAIN),
        // ---- Big Chompy Bird Hunting (Quest.BIG_CHOMPY_BIRD_HUNTING) ----
        new JunkEntry(WOLFBONE_ARROWTIPS, "Wolfbone arrowtips",
            "Quest complete (Big Chompy Bird Hunting) — ogre arrow component, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(ACHEY_TREE_LOGS, "Achey tree logs",
            "Quest complete (Big Chompy Bird Hunting) — ogre arrow component, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(OGRE_ARROW_SHAFT, "Ogre arrow shaft",
            "Quest complete (Big Chompy Bird Hunting) — ogre arrow component, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(FLIGHTED_OGRE_ARROW, "Flighted ogre arrow",
            "Quest complete (Big Chompy Bird Hunting) — ogre arrow component, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        new JunkEntry(BLOATED_TOAD, "Bloated toad",
            "Quest complete (Big Chompy Bird Hunting) — chompy bait, no use after quest.",
            Quest.BIG_CHOMPY_BIRD_HUNTING),
        // ---- Bone Voyage (Quest.BONE_VOYAGE) ----
        new JunkEntry(SAWMILL_PROPOSAL, "Sawmill proposal",
            "Quest complete (Bone Voyage) — document, no use after quest.",
            Quest.BONE_VOYAGE),
        new JunkEntry(SAWMILL_AGREEMENT, "Sawmill agreement",
            "Quest complete (Bone Voyage) — document, no use after quest.",
            Quest.BONE_VOYAGE),
        new JunkEntry(BONE_CHARM, "Bone charm",
            "Quest complete (Bone Voyage) — charm, no use after quest.",
            Quest.BONE_VOYAGE),
        new JunkEntry(POTION_OF_SEALEGS, "Potion of sealegs",
            "Quest complete (Bone Voyage) — quest potion, no use after quest.",
            Quest.BONE_VOYAGE),
        // ---- Children of the Sun (Quest.CHILDREN_OF_THE_SUN) ----
        new JunkEntry(VARLAMORE_INVITATION, "Varlamore invitation",
            "Quest complete (Children of the Sun) — legacy invitation, no use after quest.",
            Quest.CHILDREN_OF_THE_SUN),
        // ---- The Ascent of Arceuus (Quest.THE_ASCENT_OF_ARCEUUS) ----
        new JunkEntry(A_DARK_DISPOSITION, "A dark disposition",
            "Quest complete (The Ascent of Arceuus) — quest book, no use after quest.",
            Quest.THE_ASCENT_OF_ARCEUUS)
    ); }
    private static List<JunkEntry> buildBatch5() { return Arrays.asList(
        // ===== Batch 9: txt<->Java reconciliation (July 10, 2026) =====
        new JunkEntry(COPPERS_CRIMSON_COLLAR, "Copper's crimson collar", "Quest complete — no use after quest.", Quest.CLIENT_OF_KOUREND),
        new JunkEntry(CONDUCTOR, "Conductor", "Quest complete — no use after quest.", Quest.CREATURE_OF_FENKENSTRAIN),
        new JunkEntry(USELESS_KEY_DOH, "Useless key", "Quest complete — no use after quest.", Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(USELESS_KEY_DOH_BANK, "Useless key (bank placeholder)", "Quest complete — no use after quest.", Quest.DARKNESS_OF_HALLOWVALE),
        new JunkEntry(CHEST_KEY_FANCY, "Chest key (Fancy chest)", "Quest complete — no use after quest.", Quest.DEATH_ON_THE_ISLE),
        new JunkEntry(ZANIK_ITEM, "Zanik (item)", "Quest complete — no use after quest.", Quest.DEATH_TO_THE_DORGESHUUN),
        new JunkEntry(CRATE_WITH_ZANIK, "Crate with zanik", "Quest complete — no use after quest.", Quest.DEATH_TO_THE_DORGESHUUN),
        new JunkEntry(ELIAS_WHITE_ITEM, "Elias white (item)", "Quest complete — no use after quest.", Quest.DEFENDER_OF_VARROCK),
        new JunkEntry(BANDITS_BREW, "Bandit's brew", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_I),
        new JunkEntry(AIR_NERVE, "Air nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(ASTRAL_NERVE, "Astral nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(BLOOD_NERVE, "Blood nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(COSMIC_NERVE, "Cosmic nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(DUST_NERVE, "Dust nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(EARTH_NERVE, "Earth nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(FIRE_NERVE, "Fire nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(LAVA_NERVE, "Lava nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(LAW_NERVE, "Law nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(MIND_NERVE, "Mind nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(NATURE_NERVE, "Nature nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SMOKE_NERVE, "Smoke nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(SOUL_NERVE, "Soul nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(STEAM_NERVE, "Steam nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(WATER_NERVE, "Water nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(WRATH_NERVE, "Wrath nerve", "Quest complete — no use after quest.", Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE),
        new JunkEntry(INSTRUCTION_MANUAL_DC, "Instruction manual", "Quest complete — no use after quest.", Quest.DWARF_CANNON),
        new JunkEntry(VARLAMORE_ENVOY, "Varlamore envoy", "Quest complete — no use after quest.", Quest.THE_DEPTHS_OF_DESPAIR),
        new JunkEntry(GROUND_CHARCOAL, "Ground charcoal", "Quest complete — no use after quest.", Quest.THE_DIG_SITE),
        new JunkEntry(OLD_BOOT_DIGSITE, "Old boot", "Quest complete — no use after quest.", Quest.THE_DIG_SITE),
        new JunkEntry(ROCK_PICK, "Rock pick", "Quest complete — no use after quest.", Quest.THE_DIG_SITE),
        new JunkEntry(SPECIMEN_BRUSH, "Specimen brush", "Quest complete — no use after quest.", Quest.THE_DIG_SITE),
        new JunkEntry(TROWEL, "Trowel", "Quest complete — no use after quest.", Quest.THE_DIG_SITE),
        new JunkEntry(VASE_DIGSITE, "Vase (Digsite)", "Quest complete — no use after quest.", Quest.THE_DIG_SITE)
    ); }
    private static List<JunkEntry> buildBatch6() { return Arrays.asList(
        // ===== Batch 10: txt->Java reconciliation, whole-alphabet backfill (July 10, 2026) =====
        new JunkEntry(EAGLE_CAPE_EP, "Eagle cape", "Quest complete \u2014 no use after quest.", Quest.EAGLES_PEAK),
        new JunkEntry(ELEMENTAL_ORE, "Elemental ore", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_I),
        new JunkEntry(ELEMENTAL_METAL, "Elemental metal", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_I),
        new JunkEntry(ELEMENTAL_HELMET, "Elemental helmet", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_I),
        new JunkEntry(PRIMED_BAR, "Primed bar", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(PRIMED_MIND_BAR, "Primed mind bar", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(MIND_HELMET, "Mind helmet", "Quest complete \u2014 no use after quest.", Quest.ELEMENTAL_WORKSHOP_II),
        new JunkEntry(SCRYING_ORB_FULL, "Scrying orb (full)", "Quest complete \u2014 no use after quest.", Quest.ENTER_THE_ABYSS),
        new JunkEntry(SCRYING_ORB_EMPTY, "Scrying orb (empty)", "Quest complete \u2014 no use after quest.", Quest.ENTER_THE_ABYSS),
        new JunkEntry(POISON_ITEM, "Poison (item)", "Quest complete \u2014 no use after quest.", Quest.ERNEST_THE_CHICKEN).withRequiredQuest2(Quest.HAZEEL_CULT),
        new JunkEntry(STEEL_GAUNTLETS, "Steel gauntlets", "Quest complete \u2014 no use after quest.", Quest.FAMILY_CREST),
        new JunkEntry(WHITE_ROSE_SEED, "White rose seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(RED_ROSE_SEED, "Red rose seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(PINK_ROSE_SEED, "Pink rose seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(VINE_SEED, "Vine seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(DELPHINIUM_SEED, "Delphinium seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(ORCHID_SEED_PINK, "Orchid seed (pink)", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(ORCHID_SEED_YELLOW, "Orchid seed (yellow)", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(SNOWDROP_SEED, "Snowdrop seed", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(WHITE_TREE_SHOOT_SHOOT, "White tree shoot (shoot)", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(WHITE_TREE_SHOOT_POT, "White tree shoot (pot)", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(WHITE_TREE_SHOOT_WATERED, "White tree shoot (watered)", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(WHITE_TREE_SAPLING, "White tree sapling", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(TROLLEY, "Trolley", "Quest complete \u2014 no use after quest.", Quest.GARDEN_OF_TRANQUILLITY),
        new JunkEntry(CLAY_HEAD, "Clay head", "Quest complete \u2014 no use after quest.", Quest.GETTING_AHEAD),
        new JunkEntry(FUR_HEAD, "Fur head", "Quest complete \u2014 no use after quest.", Quest.GETTING_AHEAD),
        new JunkEntry(BLOODY_HEAD, "Bloody head", "Quest complete \u2014 no use after quest.", Quest.GETTING_AHEAD),
        new JunkEntry(NEILAN_S_JOURNAL, "Neilan's journal", "Quest complete \u2014 no use after quest.", Quest.GETTING_AHEAD),
        new JunkEntry(SIGNED_OAK_BOW, "Signed oak bow", "Quest complete \u2014 no use after quest.", Quest.GHOSTS_AHOY),
        new JunkEntry(ECTOPHIAL_EMPTY, "Ectophial (empty)", "Quest complete \u2014 no use after quest.", Quest.GHOSTS_AHOY),
        new JunkEntry(CARNILLEAN_ARMOUR, "Carnillean armour", "Quest complete \u2014 no use after quest.", Quest.HAZEEL_CULT),
        new JunkEntry(HAZEEL_S_MARK, "Hazeel's mark", "Quest complete \u2014 no use after quest.", Quest.HAZEEL_CULT),
        new JunkEntry(RAW_LAVA_EEL, "Raw lava eel", "Quest complete \u2014 no use after quest.", Quest.HEROES_QUEST),
        new JunkEntry(LAVA_EEL, "Lava eel", "Quest complete \u2014 no use after quest.", Quest.HEROES_QUEST),
        new JunkEntry(EMBALMING_MANUAL, "Embalming manual", "Quest complete \u2014 no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(PILE_OF_SALT, "Pile of salt", "Quest complete \u2014 no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(SPHINX_S_TOKEN, "Sphinx's token", "Quest complete \u2014 no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(BUCKET_OF_SALTWATER, "Bucket of saltwater", "Quest complete \u2014 no use after quest.", Quest.ICTHLARINS_LITTLE_HELPER),
        new JunkEntry(MAISA_S_MESSAGE, "Maisa's message", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(AKILA_S_JOURNAL, "Akila's journal", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(HET_S_CAPTURE, "Het's capture", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(APMEKEN_S_CAPTURE, "Apmeken's capture", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(SCABARAS_CAPTURE, "Scabaras' capture", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(CRONDIS_CAPTURE, "Crondis' capture", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(THE_WARDENS, "The wardens", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(THE_JACKAL_S_TORCH, "The jackal's torch", "Quest complete \u2014 no use after quest.", Quest.INTO_THE_TOMBS),
        new JunkEntry(ROD_DUST, "Rod dust", "Quest complete \u2014 no use after quest.", Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(ASTRONOMY_BOOK, "Astronomy book", "Quest complete \u2014 no use after quest.", Quest.OBSERVATORY_QUEST),
        new JunkEntry(GOBLIN_KITCHEN_KEY, "Goblin kitchen key", "Quest complete \u2014 no use after quest.", Quest.OBSERVATORY_QUEST),
        new JunkEntry(LENS_MOULD, "Lens mould", "Quest complete \u2014 no use after quest.", Quest.OBSERVATORY_QUEST),
        new JunkEntry(KARAMJAN_RUM, "Karamjan rum", "Quest complete \u2014 no use after quest.", Quest.PIRATES_TREASURE).withRequiredQuest2(Quest.TAI_BWO_WANNAI_TRIO),
        new JunkEntry(WARRANT, "Warrant", "Quest complete \u2014 no use after quest.", Quest.PLAGUE_CITY),
        new JunkEntry(A_SMALL_KEY, "A small key", "Quest complete \u2014 no use after quest.", Quest.PLAGUE_CITY),
        new JunkEntry(A_SCRUFFY_NOTE, "A scruffy note", "Quest complete \u2014 no use after quest.", Quest.PLAGUE_CITY),
        new JunkEntry(PICTURE, "Picture", "Quest complete \u2014 no use after quest.", Quest.PLAGUE_CITY),
        new JunkEntry(MURKY_WATER, "Murky water", "Quest complete \u2014 no use after quest.", Quest.PRIEST_IN_PERIL),
        new JunkEntry(BLESSED_WATER, "Blessed water", "Quest complete \u2014 no use after quest.", Quest.PRIEST_IN_PERIL),
        new JunkEntry(BOOK_RATPITS, "Book (Ratpits)", "Quest complete \u2014 no use after quest.", Quest.RATCATCHERS),
        new JunkEntry(ROCK_MOGRE_CAMP, "Rock (Mogre Camp)", "Quest complete \u2014 no use after quest.", Quest.RECIPE_FOR_DISASTER__PIRATE_PETE),
        new JunkEntry(SKEWERED_CHOMPY, "Skewered chompy", "Quest complete \u2014 no use after quest.", Quest.RECIPE_FOR_DISASTER__SKRACH_UGLOGWEE),
        new JunkEntry(MAKEOVER_VOUCHER, "Makeover voucher", "Quest complete \u2014 no use after quest.", Quest.RECRUITMENT_DRIVE),
        new JunkEntry(A_HANDWRITTEN_BOOK, "A handwritten book", "Quest complete \u2014 no use after quest.", Quest.THE_EYES_OF_GLOUPHRIE),
        new JunkEntry(KHARIDIAN_HEADPIECE, "Kharidian headpiece", "Quest complete \u2014 no use after quest.", Quest.THE_FEUD),
        new JunkEntry(FAKE_BEARD, "Fake beard", "Quest complete \u2014 no use after quest.", Quest.THE_FEUD),
        new JunkEntry(KARIDIAN_DISGUISE, "Karidian disguise", "Quest complete \u2014 no use after quest.", Quest.THE_FEUD),
        new JunkEntry(COIN_PURSE_FILLED, "Coin purse (filled)", "Quest complete \u2014 no use after quest.", Quest.THE_FINAL_DAWN),
        new JunkEntry(COIN_PURSE_EMPTY, "Coin purse (empty)", "Quest complete \u2014 no use after quest.", Quest.THE_FINAL_DAWN),
        new JunkEntry(COIN_PURSE_SANDY, "Coin purse (sandy)", "Quest complete \u2014 no use after quest.", Quest.THE_FINAL_DAWN),
        new JunkEntry(DINH_S_HAMMER, "Dinh's hammer", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(GENERATOR_CRANK, "Generator crank", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(EIGHT_GALLON_JUG, "8-gallon jug", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(FIVE_GALLON_JUG, "5-gallon jug", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(ENERGY_DISK_LEVEL_4, "Energy disk (level 4)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(ENERGY_DISK_LEVEL_3, "Energy disk (level 3)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(ENERGY_DISK_LEVEL_2, "Energy disk (level 2)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(ENERGY_DISK_LEVEL_1, "Energy disk (level 1)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(UNKNOWN_FLUID_1, "Unknown fluid (1)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(UNKNOWN_FLUID_2, "Unknown fluid (2)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(UNKNOWN_FLUID_3, "Unknown fluid (3)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(UNKNOWN_FLUID_4, "Unknown fluid (4)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(UNKNOWN_FLUID_5, "Unknown fluid (5)", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(ANCIENT_LETTER, "Ancient letter", "Quest complete \u2014 no use after quest.", Quest.THE_FORSAKEN_TOWER),
        new JunkEntry(LUNAR_ORE, "Lunar ore", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_EXILES).withRequiredQuest2(Quest.LUNAR_DIPLOMACY),
        new JunkEntry(LUNAR_BAR, "Lunar bar", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_EXILES).withRequiredQuest2(Quest.LUNAR_DIPLOMACY),
        new JunkEntry(EMPTY_TAX_BAG, "Empty tax bag", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(BULGING_TAXBAG, "Bulging taxbag", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_ISLES),
        new JunkEntry(GOLDEN_FLEECE, "Golden fleece", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(GOLDEN_WOOL, "Golden wool", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(HUNTERS_TALISMAN_NORMAL, "Hunters' talisman (normal)", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(HUNTERS_TALISMAN_UNCHARGED, "Hunters' talisman (uncharged)", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FREMENNIK_BALLAD, "Fremennik ballad", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(STURDY_BOOTS, "Sturdy boots", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(TRACKING_MAP, "Tracking map", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(CUSTOM_BOW_STRING, "Custom bow string", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SEA_FISHING_MAP, "Sea fishing map", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(WEATHER_FORECAST, "Weather forecast", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(CHAMPIONS_TOKEN, "Champions token", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(LEGENDARY_COCKTAIL, "Legendary cocktail", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FISCAL_STATEMENT, "Fiscal statement", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(PROMISSORY_NOTE, "Promissory note", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(WARRIORS_CONTRACT, "Warriors' contract", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(LOW_ALCOHOL_KEG, "Low alcohol keg", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(BLUE_THREAD, "Blue thread", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SMALL_PICK, "Small pick", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(TOY_SHIP, "Toy ship", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FOUR_5THS_FULL_BUCKET, "4/5ths full bucket", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(THREE_5THS_FULL_BUCKET, "3/5ths full bucket", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(TWO_5THS_FULL_BUCKET, "2/5ths full bucket", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(ONE_5THS_FULL_BUCKET, "1/5ths full bucket", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FROZEN_BUCKET, "Frozen bucket", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(TWO_3RDS_FULL_JUG, "2/3rds full jug", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(ONE_3RDS_FULL_JUG, "1/3rds full jug", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FROZEN_JUG, "Frozen jug", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(FROZEN_VASE, "Frozen vase", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(VASE_LID, "Vase lid", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SEALED_VASE_EMPTY, "Sealed vase (empty)", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SEALED_VASE_FROZEN, "Sealed vase (frozen)", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SEALED_VASE_WATER, "Sealed vase (water)", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(RED_HERRING, "Red herring", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(RED_DISK, "Red disk", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(WOODEN_DISK, "Wooden disk", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(SEER_S_KEY, "Seer's key", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(STICKY_RED_GOOP, "Sticky red goop", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(BEER_TANKARD, "Beer tankard", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(OLD_RED_DISK, "Old red disk", "Quest complete \u2014 no use after quest.", Quest.THE_FREMENNIK_TRIALS),
        new JunkEntry(KASONDE_S_JOURNAL, "Kasonde's journal", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WORD_TRANSLATIONS, "Word translations", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(DIRTY_NOTE_MOUNT_QUIDAMORTEM, "Dirty note (mount quidamortem)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(DIRTY_NOTE_LAKE_MOLCH_ISLAND, "Dirty note (lake molch island)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(DIRTY_NOTE_RUINS_OF_MORRA, "Dirty note (ruins of morra)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WARNING_NOTE, "Warning note", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_MOUNT_QUIDAMORTEM_1, "Wood carving (mount quidamortem, 1)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_MOUNT_QUIDAMORTEM_2, "Wood carving (mount quidamortem, 2)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_LAKE_MOLCH_ISLAND_1, "Wood carving (lake molch island, 1)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_LAKE_MOLCH_ISLAND_2, "Wood carving (lake molch island, 2)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_LAKE_MOLCH_ISLAND_3, "Wood carving (lake molch island, 3)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_KEBOS_SWAMP_1, "Wood carving (kebos swamp, 1)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_KEBOS_SWAMP_2, "Wood carving (kebos swamp, 2)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_KEBOS_SWAMP_3, "Wood carving (kebos swamp, 3)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_KEBOS_SWAMP_4, "Wood carving (kebos swamp, 4)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(COMPASS_THE_GARDEN_OF_DEATH, "Compass (The Garden of Death)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_RUINS_OF_MORRA_1, "Wood carving (ruins of morra, 1)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_RUINS_OF_MORRA_2, "Wood carving (ruins of morra, 2)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_RUINS_OF_MORRA_3, "Wood carving (ruins of morra, 3)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_RUINS_OF_MORRA_4, "Wood carving (ruins of morra, 4)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(WOOD_CARVING_RUINS_OF_MORRA_5, "Wood carving (ruins of morra, 5)", "Quest complete \u2014 no use after quest.", Quest.THE_GARDEN_OF_DEATH),
        new JunkEntry(DWARVEN_BATTLEAXE_RUSTY, "Dwarven battleaxe (rusty)", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(DWARVEN_BATTLEAXE_SHARPENED, "Dwarven battleaxe (sharpened)", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(DWARVEN_BATTLEAXE_SAPPHIRES, "Dwarven battleaxe (sapphires)", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(DWARVEN_BATTLEAXE_REPAIRED, "Dwarven battleaxe (repaired)", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(LEFT_BOOT, "Left boot", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(RIGHT_BOOT, "Right boot", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(EXQUISITE_BOOTS, "Exquisite boots", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(BOOK_ON_COSTUMES, "Book on costumes", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(MEETING_NOTES, "Meeting notes", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(EXQUISITE_CLOTHES, "Exquisite clothes", "Quest complete \u2014 no use after quest.", Quest.THE_GIANT_DWARF),
        new JunkEntry(VARMEN_S_NOTES, "Varmen's notes", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(DISPLAY_CABINET_KEY, "Display cabinet key", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(BLACK_MUSHROOM, "Black mushroom", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(PHOENIX_FEATHER, "Phoenix feather", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(BLACK_DYE, "Black dye", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(PHOENIX_QUILL_PEN, "Phoenix quill pen", "Quest complete \u2014 no use after quest.", Quest.THE_GOLEM),
        new JunkEntry(INVASION_PLANS, "Invasion plans", "Quest complete \u2014 no use after quest.", Quest.THE_GRAND_TREE),
        new JunkEntry(WOODEN_CAT, "Wooden cat", "Quest complete \u2014 no use after quest.", Quest.THE_GREAT_BRAIN_ROBBERY),
        new JunkEntry(SANDY_HAND, "Sandy hand", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(BEER_SOAKED_HAND, "Beer soaked hand", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(BERT_S_ROTA, "Bert's rota", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(SANDY_S_ROTA, "Sandy's rota", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(A_MAGIC_SCROLL, "A magic scroll", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(MAGICAL_ORB_INACTIVE, "Magical orb (inactive)", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(MAGICAL_ORB_ACTIVE, "Magical orb (active)", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(TRUTH_SERUM, "Truth serum", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(BOTTLED_WATER, "Bottled water", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(REDBERRY_JUICE, "Redberry juice", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(PINK_DYE, "Pink dye", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(WIZARD_S_HEAD, "Wizard's head", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(SAND, "Sand", "Quest complete \u2014 no use after quest.", Quest.THE_HAND_IN_THE_SAND),
        new JunkEntry(PRINCE_ITZLA_ARKAN_ITEM, "Prince itzla arkan (item)", "Quest complete \u2014 no use after quest.", Quest.THE_HEART_OF_DARKNESS)
    ); }

    private static List<JunkEntry> buildBatch11() { return Arrays.asList(
        // ===== Batch 11: txt->Java union add (July 10, 2026) — no deletions =====
        new JunkEntry(BLURITE_SWORD, "Blurite sword", "Quest complete \u2014 no use after quest.", Quest.THE_KNIGHTS_SWORD),
        new JunkEntry(BLURITE_ORE, "Blurite ore", "Quest complete \u2014 no use after quest.", Quest.THE_KNIGHTS_SWORD),
        new JunkEntry(PAPYRUS, "Papyrus", "Quest complete \u2014 no use after quest.", Quest.LEGENDS_QUEST),
        new JunkEntry(BROOCH, "Brooch", "Quest complete \u2014 no use after quest.", Quest.THE_LOST_TRIBE),
        new JunkEntry(SILVERWARE, "Silverware", "Quest complete \u2014 no use after quest.", Quest.THE_LOST_TRIBE),
        new JunkEntry(PEACE_TREATY, "Peace treaty", "Quest complete \u2014 no use after quest.", Quest.THE_LOST_TRIBE),
        new JunkEntry(LETTER_KING_LATHAS, "Letter (King Lathas)", "Quest complete \u2014 no use after quest.", Quest.MAKING_HISTORY),
        new JunkEntry(LETTER_JORRAL, "Letter (Jorral)", "Quest complete \u2014 no use after quest.", Quest.MAKING_HISTORY),
        new JunkEntry(PRIFDDINAS_HISTORY, "Prifddinas' history", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_I),
        new JunkEntry(EASTERN_DISCOVERY, "Eastern discovery", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_I),
        new JunkEntry(EASTERN_SETTLEMENT, "Eastern settlement", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_I),
        new JunkEntry(THE_GREAT_DIVIDE, "The great divide", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_I),
        new JunkEntry(ROTTEN_APPLES, "Rotten apples", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_I),
        new JunkEntry(BLACKENED_CRYSTAL, "Blackened crystal", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_II),
        new JunkEntry(NEWLY_MADE_CRYSTAL_UNCHARGED, "Newly made crystal (uncharged)", "Quest complete \u2014 no use after quest.", Quest.MOURNINGS_END_PART_II),
        new JunkEntry(ROD_MOULD, "Rod mould", "Quest complete \u2014 no use after quest.", Quest.IN_AID_OF_THE_MYREQUE),
        new JunkEntry(DREAM_LOG, "Dream log", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(MOONCLAN_MANUAL, "Moonclan manual", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(SUQAH_HIDE, "Suqah hide", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(SUQAH_LEATHER, "Suqah leather", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(EMPTY_VIAL, "Empty vial", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(GUAM_VIAL, "Guam vial", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(MARR_VIAL, "Marr vial", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(GUAM_MARR_VIAL, "Guam-marr vial", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(PLAIN_OF_MUD_SPHERE, "Plain of mud sphere", "Quest complete \u2014 no use after quest.", Quest.LAND_OF_THE_GOBLINS),
        new JunkEntry(LARGE_EGG, "Large egg", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(JAGUAR_EGG, "Jaguar egg", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(EGG_HUMPHREY_DUMPHREY, "Egg (Humphrey Dumphrey)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(ALAN_S_BONES, "Alan's bones", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(ALAN_S_BONEMEAL, "Alan's bonemeal", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(DAMIANA_LEAVES, "Damiana leaves", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(DAMIANA_WATER, "Damiana water", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(DAMIANA_TEA, "Damiana tea", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(DAMIANA_TEA_MILKY, "Damiana tea (milky)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(CUP_OF_TEA_DAMIANA, "Cup of tea (damiana)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(CUP_OF_TEA_MILKY_DAMIANA, "Cup of tea (milky damiana)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(ACATZIN_S_AXE_DAMAGED, "Acatzin's axe (damaged)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(ACATZIN_S_AXE_REPAIRED, "Acatzin's axe (repaired)", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(ALAN_S_BLESSED_BONES, "Alan's blessed bones", "Quest complete \u2014 no use after quest.", Quest.SCRAMBLED),
        new JunkEntry(LUNAR_STAFF_PT1, "Lunar staff - pt1", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(LUNAR_STAFF_PT2, "Lunar staff - pt2", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY),
        new JunkEntry(LUNAR_STAFF_PT3, "Lunar staff - pt3", "Quest complete \u2014 no use after quest.", Quest.LUNAR_DIPLOMACY)
    ); }

    private static final List<JunkEntry> ENTRIES;
    static {
        List<JunkEntry> all = new ArrayList<>();
        all.addAll(buildBatch1A());
        all.addAll(buildBatch1B());
        all.addAll(buildBatch2());
        all.addAll(buildBatch3());
        all.addAll(buildBatch4());
        all.addAll(buildBatch5());
        all.addAll(buildBatch6());
        all.addAll(buildBatch11());
        ENTRIES = Collections.unmodifiableList(all);
    }

    /**
     * Quests that have been reviewed and confirmed to have NO junk items
     * (nothing to flag). Documented so future authors know these were audited,
     * not overlooked.
     *
     * Invariant: every net.runelite.api.Quest value should be either represented
     * in ENTRIES (as a gate on at least one JunkEntry) OR listed here. A unit test
     * can assert this to catch any newly-added quest that wasn't reviewed.
     */
    static final Set<Quest> REVIEWED_NO_JUNK_QUESTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
        Quest.A_SOULS_BANE,
        Quest.BARBARIAN_TRAINING,
        Quest.COOKS_ASSISTANT,
        Quest.CURSE_OF_THE_EMPTY_LORD,
        Quest.DORICS_QUEST,
        // v1 PLACEHOLDER - pending item research for v2 (Fallen From Grace, a
        // Wyrmscraig / Sailing quest released 2026-07-29). Candidates to vet in-game
        // before gating: Scrawled notebook, Golem primer, Staircase key, Sunstone core,
        // Ancient sunstone core, Dull sunstone core. No RED entries defined yet; listed
        // here only so the quest-coverage test passes. Move to a gated block once verified.
        Quest.FALLEN_FROM_GRACE,
        Quest.FAMILY_PEST,
        Quest.IN_SEARCH_OF_THE_MYREQUE,
        Quest.LAIR_OF_TARN_RAZORLOR,
        Quest.LEARNING_THE_ROPES,
        Quest.LOST_CITY,
        Quest.MAGE_ARENA_I,
        Quest.MAGE_ARENA_II,
        Quest.PRYING_TIMES,
        Quest.RECIPE_FOR_DISASTER,
        Quest.RECIPE_FOR_DISASTER__CULINAROMANCER,
        Quest.SHEEP_SHEARER,
        Quest.SLEEPING_GIANTS,
        Quest.TEMPLE_OF_THE_EYE
    )));

    /**
     * Quests that gate at least one JunkEntry (via requiredQuest or requiredQuest2).
     * Package-private so the quest-coverage unit test can verify completeness.
     */
    static Set<Quest> gatedQuests()
    {
        Set<Quest> s = new HashSet<>();
        for (JunkEntry e : ENTRIES)
        {
            if (e.requiredQuest != null) { s.add(e.requiredQuest); }
            if (e.getRequiredQuest2() != null) { s.add(e.getRequiredQuest2()); }
        }
        return s;
    }
    // =========================================================================
    // POH STORABLE ITEMS (costume room)
    //
    // Items that can be stored in the player-owned house costume room, freeing
    // up bank space. All IDs player-confirmed.
    // =========================================================================
    /**
     * Items storable in the Fancy Dress Box in the Costume Room of the
     * player-owned house (random event rewards). All IDs player-confirmed June 2026.
     */
    public static final Set<Integer> POH_FANCY_DRESS_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // Mime random event
            3057,   // Mime mask
            3058,   // Mime top
            3059,   // Mime legs
            3060,   // Mime gloves
            3061,   // Mime boots
            // Drill Demon random event — camo outfit
            6654,   // Camo top
            6655,   // Camo bottoms
            6656,   // Camo helmet
            // Freaky Forester random event — lederhosen
            6180,   // Lederhosen top
            6181,   // Lederhosen shorts
            6182,   // Lederhosen hat
            // Frog Prince/Princess random event
            // NOTE: Frog token (6183) excluded — not POH storable
            6184,   // Royal frog tunic
            6185,   // Royal frog leggings
            6186,   // Royal frog blouse
            6187,   // Royal frog skirt
            6188,   // Frog mask
            // Zombie random event
            7592,   // Zombie shirt
            7593,   // Zombie trousers
            7594,   // Zombie mask
            7595,   // Zombie gloves
            7596,   // Zombie boots
            // Beekeeper random event
            25129,  // Beekeeper's hat
            25131,  // Beekeeper's top
            25133,  // Beekeeper's legs
            25135,  // Beekeeper's gloves
            25137,  // Beekeeper's boots
            // Miscellaneous
            20590,  // Stale baguette
            546,    // Shade robe top                — Shades of Mort'ton random event
            548     // Shade robe                    — Shades of Mort'ton random event
        ))
    );
    /**
     * Items storable in the Toy Box in the Costume Room of the player-owned
     * house (holiday and seasonal event items). These items are also present
     * in {@link #HOLIDAY_ITEMS} where applicable — the plugin runs this scan
     * first so the POH highlight takes priority when both toggles are enabled.
     *
     * <p>Santa hat (1050) is tradeable and therefore excluded from both this set
     * and {@link #HOLIDAY_ITEMS}; it is never flagged as junk.</p>
     *
     * All IDs player-confirmed June 2026.
     */
    public static final Set<Integer> POH_TOY_BOX_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // -----------------------------------------------------------------
            // BIRTHDAY / ANNIVERSARY
            // -----------------------------------------------------------------
            795,    // War ship                      — Birthday 2013
            11919,  // Cow mask                      — Birthday event
            12956,  // Cow top                       — Birthday event
            12957,  // Cow trousers                  — Birthday event
            12958,  // Cow gloves                    — Birthday event
            12959,  // Cow shoes                     — Birthday event
            13655,  // Gnome child hat               — Birthday event
            21209,  // Birthday balloons             — Birthday event
            21211,  // 4th birthday hat              — 4th anniversary
            23108,  // Birthday cake                 — Birthday event
            24525,  // Cat ears                      — Birthday event
            24527,  // Hell cat ears                 — Birthday event
            25322,  // 20th anniversary hat          — 20th anniversary
            25324,  // 20th anniversary top          — 20th anniversary
            25326,  // 20th anniversary bottom       — 20th anniversary
            25328,  // 20th anniversary boots        — 20th anniversary
            25330,  // 20th anniversary gloves       — 20th anniversary
            25332,  // 20th anniversary necklace     — 20th anniversary
            25334,  // 20th anniversary cape         — 20th anniversary
            25336,  // Gnome child mask              — Birthday event
            25338,  // Gnome child icon              — Birthday event
            25500,  // Cursed banana                 — Birthday event
            25502,  // Banana cape                   — Birthday event
            26649,  // Skis                          — Birthday event
            27802,  // Gnome child backpack          — 10th anniversary
            27804,  // Cake hat                      — 10th anniversary
            27806,  // Bob the cat slippers          — 10th anniversary
            27808,  // Jad slippers                  — 10th anniversary
            27810,  // Dragon candle dagger          — 10th anniversary
            27812,  // 10th birthday cape            — 10th anniversary
            27814,  // Jad plush                     — 10th anniversary
            27816,  // Stray dog plush               — 10th anniversary
            27818,  // Gnome child plush             — 10th anniversary
            27820,  // 10th birthday balloons        — 10th anniversary
            27822,  // Old school jumper (variant 1) — Birthday event
            27823,  // Old school jumper (variant 2) — Birthday event
            27824,  // Old school jumper (variant 3) — Birthday event
            27825,  // Old school jumper (variant 4) — Birthday event
            27826,  // Old school jumper (variant 5) — Birthday event
            27827,  // Old school jumper (variant 6) — Birthday event
            27828,  // Silver partyhat               — Birthday event (untradeable)
            30646,  // Classic imp tail              — Birthday event
            30648,  // Classic imp hood              — Birthday event
            33080,  // 25th anniversary helmet           — 25th anniversary
            33082,  // 25th anniversary warrior tabard   — 25th anniversary
            33084,  // 25th anniversary skeleton tabard  — 25th anniversary
            33086,  // 25th anniversary 5x5 hat          — 25th anniversary
            // -----------------------------------------------------------------
            // EASTER
            // -----------------------------------------------------------------
            1037,   // Bunny ears                    — Easter 2004
            4565,   // Easter basket                 — Easter event
            4566,   // Rubber chicken                — Easter event
            7927,   // Easter ring                   — Easter 2007
            11019,  // Chicken feet                  — Easter event
            11020,  // Chicken wings                 — Easter event
            11021,  // Chicken head                  — Easter event
            11022,  // Chicken legs                  — Easter event
            13182,  // Bunny feet                    — Easter event
            13663,  // Bunny top                     — Easter event
            13664,  // Bunny legs                    — Easter event
            13665,  // Bunny paws                    — Easter event
            21214,  // Easter egg helm               — Easter event
            22351,  // Eggshell platebody            — Easter event
            22353,  // Eggshell platelegs            — Easter event
            23446,  // Giant easter egg              — Easter event
            23448,  // Bunnyman mask                 — Easter event
            24535,  // Magic egg ball                — Easter event
            24537,  // Carrot sword                  — Easter event
            24539,  // '24-carat' sword              — Easter event
            25604,  // Gregg's eastdoor              — Easter event
            25606,  // Propeller hat                 — Easter event
            26937,  // Easter hat                    — Easter event
            26939,  // Crate ring                    — Easter event
            27871,  // Giant bronze dagger           — Easter event
            27873,  // Eastfloor spade               — Easter event
            27875,  // Nest hat (Easter egg variant) — Easter event
            27877,  // Nest hat (Chick variant)      — Easter event
            29433,  // Book of egg                   — Easter event
            29437,  // Egg priest robe               — Easter event
            29439,  // Egg priest robe top           — Easter event
            29441,  // Egg priest necklace           — Easter event
            29443,  // Egg priest mitre              — Easter event
            30720,  // Carrot costume hat            — Easter event
            30722,  // Carrot costume body           — Easter event
            30724,  // Carrot costume tights         — Easter event
            30726,  // Carrot costume gloves         — Easter event
            33149,  // Archibald (Event variant)     — Easter event
            33151,  // Archibald                     — Easter event
            33153,  // Archibald (Grid)              — Easter event
            33155,  // Archibald (Bunny)             — Easter event
            33157,  // Archibald (Diamonds)          — Easter event
            33159,  // Archibald (Chick)             — Easter event
            33161,  // Archibald (Melted)            — Easter event
            33163,  // Archibald (Dragon)            — Easter event
            // -----------------------------------------------------------------
            // PRIDE
            // -----------------------------------------------------------------
            21314,  // Rainbow scarf (Pride)         — Pride event
            27035,  // Flower crown (Pride)          — Pride event
            27141,  // Flower crown (Bisexual)       — Pride event
            27143,  // Flower crown (Asexual)        — Pride event
            27145,  // Flower crown (Transgender)    — Pride event
            27147,  // Flower crown (Pansexual)      — Pride event
            27149,  // Flower crown (Non-binary)     — Pride event
            27151,  // Flower crown (Genderqueer)    — Pride event
            27153,  // Flower crown (Lesbian)        — Pride event
            27155,  // Flower crown (Gay)            — Pride event
            28108,  // Rainbow scarf (Bisexual)      — Pride event
            28109,  // Rainbow scarf (Asexual)       — Pride event
            28110,  // Rainbow scarf (Transgender)   — Pride event
            28111,  // Rainbow scarf (Pansexual)     — Pride event
            28112,  // Rainbow scarf (Non-binary)    — Pride event
            28113,  // Rainbow scarf (Genderqueer)   — Pride event
            28114,  // Rainbow scarf (Lesbian)       — Pride event
            28115,  // Rainbow scarf (Gay)           — Pride event
            28116,  // Rainbow jumper (Pride)        — Pride event
            28118,  // Rainbow jumper (Bisexual)     — Pride event
            28119,  // Rainbow jumper (Asexual)      — Pride event
            28120,  // Rainbow jumper (Transgender)  — Pride event
            28121,  // Rainbow jumper (Pansexual)    — Pride event
            28122,  // Rainbow jumper (Non-binary)   — Pride event
            28123,  // Rainbow jumper (Genderqueer)  — Pride event
            28124,  // Rainbow jumper (Lesbian)      — Pride event
            28125,  // Rainbow jumper (Gay)          — Pride event
            28126,  // Poet's jacket                 — Pride event
            28128,  // Love crossbow                 — Pride event
            29489,  // Rainbow cape (Pride)          — Pride event
            29491,  // Rainbow cape (Bisexual)       — Pride event
            29493,  // Rainbow cape (Asexual)        — Pride event
            29495,  // Rainbow cape (Transgender)    — Pride event
            29497,  // Rainbow cape (Pansexual)      — Pride event
            29499,  // Rainbow cape (Non-binary)     — Pride event
            29501,  // Rainbow cape (Genderqueer)    — Pride event
            29503,  // Rainbow cape (Lesbian)        — Pride event
            29505,  // Rainbow cape (Gay)            — Pride event
            29507,  // Rainbow crown shirt (Pride)   — Pride event
            29509,  // Rainbow crown shirt (Bisexual)    — Pride event
            29510,  // Rainbow crown shirt (Asexual)     — Pride event
            29511,  // Rainbow crown shirt (Transgender) — Pride event
            29512,  // Rainbow crown shirt (Pansexual)   — Pride event
            29513,  // Rainbow crown shirt (Non-binary)  — Pride event
            29514,  // Rainbow crown shirt (Genderqueer) — Pride event
            29515,  // Rainbow crown shirt (Lesbian)     — Pride event
            29516,  // Rainbow crown shirt (Gay)         — Pride event
            // -----------------------------------------------------------------
            // HALLOWEEN
            // -----------------------------------------------------------------
            1419,   // Scythe                        — Halloween 2003
            6722,   // Zombie head                   — Halloween event
            9920,   // Jack lantern mask             — Halloween event
            9921,   // Skeleton boots                — Halloween event
            9922,   // Skeleton gloves               — Halloween event
            9923,   // Skeleton leggings             — Halloween event
            9924,   // Skeleton shirt                — Halloween event
            9925,   // Skeleton mask                 — Halloween event
            11847,  // Black h'ween mask             — Halloween event (untradeable variant)
            12845,  // Grim reaper hood              — Halloween event
            13283,  // Gravedigger mask              — Halloween event
            13284,  // Gravedigger top               — Halloween event
            13285,  // Gravedigger leggings          — Halloween event
            13286,  // Gravedigger boots             — Halloween event
            13287,  // Gravedigger gloves            — Halloween event
            13288,  // Anti-panties                  — Halloween event
            20773,  // Banshee mask                  — Halloween event
            20775,  // Banshee top                   — Halloween event
            20777,  // Banshee robe                  — Halloween event
            20779,  // Hunting knife                 — Halloween event
            21720,  // Jonas mask                    — Halloween event
            22684,  // Eek                           — Halloween event
            22689,  // Clown mask                    — Halloween event
            22692,  // Clown bow tie                 — Halloween event
            22695,  // Clown gown                    — Halloween event
            22698,  // Clown trousers                — Halloween event
            22701,  // Clown shoes                   — Halloween event
            24305,  // Spooky hood                   — Halloween event
            24307,  // Spooky robe                   — Halloween event
            24309,  // Spooky skirt                  — Halloween event
            24311,  // Spooky gloves                 — Halloween event
            24313,  // Spooky boots                  — Halloween event
            24315,  // Spookier hood                 — Halloween event
            24317,  // Spookier robe                 — Halloween event
            24319,  // Spookier skirt                — Halloween event
            24321,  // Spookier gloves               — Halloween event
            24323,  // Spookier boots                — Halloween event
            24325,  // Pumpkin lantern               — Halloween event
            24327,  // Skeleton lantern              — Halloween event
            24975,  // Headless head                 — Halloween event
            24977,  // Magical pumpkin               — Halloween event
            26254,  // Saucepan                      — Halloween event
            26256,  // Ugly halloween jumper (Orange) — Halloween event
            26258,  // Ugly halloween jumper (Black)  — Halloween event
            26260,  // Haunted wine bottle           — Halloween event
            27463,  // Treat cauldron (Style 1)      — Halloween event
            27465,  // Treat cauldron (Style 2)      — Halloween event
            27467,  // Treat cauldron (Style 3)      — Halloween event
            27469,  // Treat cauldron (Style 4)      — Halloween event
            27471,  // Treat cauldron (Style 5)      — Halloween event
            27473,  // Witch hat                     — Halloween event
            27475,  // Witch top                     — Halloween event
            27477,  // Witch robes                   — Halloween event
            27479,  // Witch boots                   — Halloween event
            27481,  // Witch cape                    — Halloween event
            27497,  // Halloween wig (Style 1)        — Halloween event 2022
            27499,  // Halloween wig (Style 2)        — Halloween event 2022
            27501,  // Halloween wig (Style 3)        — Halloween event 2022
            27503,  // Halloween wig (Style 4)        — Halloween event 2022
            27505,  // Halloween wig (Style 5)        — Halloween event 2022
            27507,  // Halloween wig (Style 6)        — Halloween event 2022
            28601,  // Cobweb cape                   — Halloween event
            28603,  // Spider hat (Common)           — Halloween event
            28605,  // Spider hat (Venenatis)        — Halloween event
            28607,  // Spider hat (Verzik)           — Halloween event
            28609,  // Spider hat (Sarachnis)        — Halloween event
            28611,  // Spider hat (Old)              — Halloween event
            30232,  // Scarecrow shirt               — Halloween event
            30234,  // Halloween scarecrow           — Halloween event
            31225,  // Spooky chair                  — Halloween event
            31227,  // Spooky pumpkin lantern        — Halloween event
            31229,  // Grim reaper top               — Halloween event
            31231,  // Grim reaper bottoms           — Halloween event
            31233,  // Grim reaper gloves            — Halloween event
            // Carved Pumpkin Head — 7 colours × 9 expressions:
            30237, 30239, 30241, 30242, 30243, 30244, 30245, 30246, 30247, // Beige
            30248, 30249, 30250, 30251, 30252, 30253, 30254, 30255, 30256, // White
            30257, 30258, 30259, 30260, 30261, 30262, 30263, 30264, 30265, // Yellow
            30266, 30267, 30268, 30269, 30270, 30271, 30272, 30273, 30274, // Orange
            30275, 30276, 30277, 30278, 30279, 30280, 30281, 30282, 30283, // Red
            30284, 30285, 30286, 30287, 30288, 30289, 30290, 30291, 30292, // Dark green
            30293, 30294, 30295, 30296, 30297, 30298, 30299, 30300, 30301, // Powder grey
            // -----------------------------------------------------------------
            // CHRISTMAS & WINTER
            // -----------------------------------------------------------------
            6856,   // Bobble hat                    — Christmas event
            6857,   // Bobble scarf                  — Christmas event
            6858,   // Jester hat                    — Christmas event
            6859,   // Jester scarf                  — Christmas event
            6860,   // Tri-jester hat                — Christmas event
            6861,   // Tri-jester scarf              — Christmas event
            6862,   // Woolly hat                    — Christmas event
            6863,   // Woolly scarf                  — Christmas event
            6865,   // Blue marionette               — Christmas event
            6866,   // Green marionette              — Christmas event
            6867,   // Red marionette                — Christmas event
            10507,  // Reindeer hat                  — Christmas event
            11862,  // Black partyhat                — Christmas cracker (untradeable variant)
            11863,  // Rainbow partyhat              — Christmas cracker (untradeable variant)
            12887,  // Santa mask                    — Christmas event costume (untradeable)
            12888,  // Santa jacket                  — Christmas event costume (untradeable)
            12889,  // Santa pantaloons              — Christmas event costume (untradeable)
            12890,  // Santa gloves                  — Christmas event costume (untradeable)
            12891,  // Santa boots                   — Christmas event costume (untradeable)
            12892,  // Antisanta mask                — Christmas event
            12893,  // Antisanta jacket              — Christmas event
            12894,  // Antisanta pantaloons          — Christmas event
            12895,  // Antisanta gloves              — Christmas event
            12896,  // Antisanta boots               — Christmas event
            13343,  // Black santa hat               — Christmas event (untradeable variant)
            13344,  // Inverted santa hat            — Christmas event
            20832,  // Snow globe                    — Christmas event
            20834,  // Sack of presents              — Christmas event
            20836,  // Giant present                 — Christmas event
            21847,  // Snow imp costume head         — Christmas event
            21849,  // Snow imp costume body         — Christmas event
            21851,  // Snow imp costume legs         — Christmas event
            21853,  // Snow imp costume tail         — Christmas event
            21855,  // Snow imp costume gloves       — Christmas event
            21857,  // Snow imp costume feet         — Christmas event
            21859,  // Wise old man's santa hat      — Christmas event
            22713,  // Star-face                     — Christmas event
            22715,  // Tree top                      — Christmas event
            22717,  // Tree skirt                    — Christmas event
            22719,  // Candy cane                    — Christmas event
            24428,  // Green gingerbread shield      — Christmas event
            24430,  // Red gingerbread shield        — Christmas event
            24431,  // Blue gingerbread shield       — Christmas event
            25314,  // Giant boulder                 — Christmas event
            25316,  // Goblin decorations            — Christmas event
            26310,  // Festive elf slippers          — Christmas event
            26312,  // Festive elf hat               — Christmas event
            26314,  // Snowman ring                  — Christmas event
            26316,  // Secret santa present (Red)    — Christmas event
            26318,  // Secret santa present (Blue)   — Christmas event
            26320,  // Secret santa present (Green)  — Christmas event
            26322,  // Secret santa present (Black)  — Christmas event
            26324,  // Secret santa present (Gold)   — Christmas event
            27564,  // Santa's list                  — Christmas event
            27566,  // Christmas jumper              — Christmas event
            27568,  // Snow goggles & hat            — Christmas event
            27570,  // Sack of coal                  — Christmas event
            27572,  // Nutcracker top (Festive)      — Christmas event
            27574,  // Nutcracker trousers (Festive) — Christmas event
            27576,  // Nutcracker hat (Festive)      — Christmas event
            27578,  // Nutcracker boots (Festive)    — Christmas event
            27580,  // Nutcracker staff (Festive)    — Christmas event
            27582,  // Nutcracker top (Sweet)        — Christmas event
            27583,  // Nutcracker trousers (Sweet)   — Christmas event
            27584,  // Nutcracker hat (Sweet)        — Christmas event
            27585,  // Nutcracker boots (Sweet)      — Christmas event
            27586,  // Nutcracker staff (Sweet)      — Christmas event
            27588,  // Festive games crown           — Christmas event
            28786,  // Icy jumper                    — Christmas event
            28788,  // Snowglobe helmet              — Christmas event
            30479,  // Present box hat (Festive)     — Christmas event
            30481,  // Present box hat (Simple)      — Christmas event
            30483,  // Present box hat (Icy)         — Christmas event
            30485,  // Present box hat (Anti-santa)  — Christmas event
            30487,  // Dog disguise                  — Christmas event
            30489,  // Festive scarf                 — Christmas event
            30491,  // Dog boots                     — Christmas event
            32928,  // Lovley jubbly bib             — Christmas event
            32930,  // Beer belly sweater            — Christmas event
            32932,  // Jad jumper                    — Christmas event
            32934,  // Christmas dinner              — Christmas event
            // EXCLUDED: Santa hat (1050) — tradeable; whitelisted (not flagged by either scan)
            // -----------------------------------------------------------------
            // MISCELLANEOUS ONE-OFF EVENTS
            // -----------------------------------------------------------------
            4079,   // Yo-yo                         — Holiday event
            12600,  // Druidic wreath                — Event reward
            13203,  // Mask of balance               — Event reward
            13215,  // Tiger toy                     — Event reward
            13216,  // Lion toy                      — Event reward
            13217,  // Snow leopard toy              — Event reward
            13218,  // Amur leopard toy              — Event reward
            13679,  // Cabbage cape                  — Event reward
            13681,  // Cruciferous codex             — Event reward
            19699,  // Hornwood helm                 — Event reward
            21354,  // Hand fan                      — Event reward
            21695,  // Runefest shield               — Runefest event
            22316,  // Prop sword                    — Event reward
            25840,  // Banana hat                    — Holiday/event item; Toy Box storable
            27645   // Mystic cards                  — Holiday/event item; Toy Box storable
        ))
    );
    /**
     * Items storable in the Armour Case in the Costume Room of the player-owned
     * house. Includes activity/minigame reward sets, skill outfits, void armour,
     * Barbarian Assault gear, Castle Wars decorative armour, Leagues cosmetics,
     * and various other wearable rewards.
     *
     * All IDs player-confirmed June 2026.
     */
    public static final Set<Integer> POH_ARMOUR_CASE_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // -----------------------------------------------------------------
            // ADVENTURER'S OUTFIT (Leagues)
            // -----------------------------------------------------------------
            27388,  // Adventurer's top (t1)
            27390,  // Adventurer's trousers (t1)
            27392,  // Adventurer's hood (t1)
            27394,  // Adventurer's boots (t1)
            27396,  // Adventurer's top (t2)
            27398,  // Adventurer's trousers (t2)
            27400,  // Adventurer's hood (t2)
            27402,  // Adventurer's boots (t2)
            27404,  // Adventurer's top (t3)
            27406,  // Adventurer's trousers (t3)
            27408,  // Adventurer's hood (t3)
            27410,  // Adventurer's boots (t3)
            27412,  // Adventurer's vambraces
            27442,  // Adventurer's cape
            // -----------------------------------------------------------------
            // ALCHEMIST'S OUTFIT (Leagues / Tempoross)
            // -----------------------------------------------------------------
            29974,  // Prescription goggles (Unfocused)
            29976,  // Prescription goggles (Focused)
            29978,  // Alchemist labcoat (Apron off)
            29980,  // Alchemist labcoat (Apron on)
            29982,  // Alchemist pants (Apron off)
            29984,  // Alchemist pants (Apron on)
            29986,  // Alchemist gloves
            // -----------------------------------------------------------------
            // ANGLER'S OUTFIT (Fishing Trawler)
            // -----------------------------------------------------------------
            13258,  // Angler hat
            13259,  // Angler top
            13260,  // Angler waders
            13261,  // Angler boots
            25592,  // Spirit angler headband
            25594,  // Spirit angler top
            25596,  // Spirit angler waders
            25598,  // Spirit angler boots
            // -----------------------------------------------------------------
            // ARDOUGNE KNIGHT ARMOUR
            // -----------------------------------------------------------------
            23785,  // Ardougne knight helm
            23787,  // Ardougne knight platebody
            23789,  // Ardougne knight platelegs
            1069,   // Steel platelegs (Ardougne knight legs slot alt)
            // -----------------------------------------------------------------
            // BOMBER OUTFIT (The Grand Tree)
            // -----------------------------------------------------------------
            9944,   // Bomber jacket
            9945,   // Bomber cap
            9946,   // Cap and goggles
            // -----------------------------------------------------------------
            // BUILDER'S OUTFIT + HARD HAT (Construction)
            // -----------------------------------------------------------------
            10862,  // Hard hat
            10863,  // Builder's shirt
            10864,  // Builder's trousers
            10865,  // Builder's boots
            // -----------------------------------------------------------------
            // BUTLER'S UNIFORM (POH)
            // -----------------------------------------------------------------
            29914,  // Butler's uniform top (male)
            29915,  // Butler's uniform bottom (male)
            29916,  // Butler's uniform top (female)
            29918,  // Butler's uniform bottom (female)
            // -----------------------------------------------------------------
            // CAMO OUTFITS (Hunter)
            // -----------------------------------------------------------------
            10053,  // Wood camo top (inventory)
            10055,  // Wood camo legs (inventory)
            10057,  // Jungle camo top (inventory)
            10059,  // Jungle camo legs (inventory)
            10061,  // Desert camo top (inventory)
            10063,  // Desert camo legs (inventory)
            10065,  // Polar camo top (inventory)
            10067,  // Polar camo legs (inventory)
            // -----------------------------------------------------------------
            // CANES (Leagues cosmetic)
            // -----------------------------------------------------------------
            24395,  // Twisted cane
            25013,  // Trailblazer cane
            26517,  // Shattered cane
            30428,  // Raging echoes cane
            // -----------------------------------------------------------------
            // CHOMPY BIRD HATS (Big Chompy Bird Hunting)
            // -----------------------------------------------------------------
            2978,   // Chompy bird hat (ogre bowman)
            2979,   // Chompy bird hat (bowman)
            2980,   // Chompy bird hat (ogre yeoman)
            2981,   // Chompy bird hat (yeoman)
            2982,   // Chompy bird hat (ogre marksman)
            2983,   // Chompy bird hat (marksman)
            2984,   // Chompy bird hat (ogre woodsman)
            2985,   // Chompy bird hat (woodsman)
            2986,   // Chompy bird hat (ogre forester)
            2987,   // Chompy bird hat (forester)
            2988,   // Chompy bird hat (ogre bowmaster)
            2989,   // Chompy bird hat (bowmaster)
            2990,   // Chompy bird hat (ogre expert)
            2991,   // Chompy bird hat (expert)
            2992,   // Chompy bird hat (ogre dragon archer)
            2993,   // Chompy bird hat (dragon archer)
            2994,   // Chompy bird hat (expert ogre dragon archer)
            2995,   // Chompy bird hat (expert dragon archer)
            // -----------------------------------------------------------------
            // CORRUPTED ARMOUR (Leagues — Trailblazer)
            // -----------------------------------------------------------------
            20838,  // Corrupted helm
            20840,  // Corrupted platebody
            20842,  // Corrupted platelegs
            20844,  // Corrupted plateskirt
            20846,  // Corrupted kiteshield
            // -----------------------------------------------------------------
            // DEADMAN ARMOUR
            // -----------------------------------------------------------------
            24189,  // Deadman's chest (Cosmetic)
            24190,  // Deadman's legs (Cosmetic)
            24191,  // Deadman's cape (Cosmetic)
            // -----------------------------------------------------------------
            // DEMONIC PACTS RELIC HUNTER ARMOUR
            // -----------------------------------------------------------------
            33296,  // Demonic sceptre
            // Tier 1
            33451,  // Demonic pacts relic hunter (t1) armour set
            33260,  // Demonic hood (t1)
            33263,  // Demonic robe top (t1)
            33266,  // Demonic robe bottom (t1)
            33269,  // Demonic boots (t1)
            // Tier 2
            33454,  // Demonic pacts relic hunter (t2) armour set
            33272,  // Demonic hood (t2)
            33275,  // Demonic robe top (t2)
            33278,  // Demonic robe bottom (t2)
            33281,  // Demonic boots (t2)
            // Tier 3
            33457,  // Demonic pacts relic hunter (t3) armour set
            33284,  // Demonic hood (t3)
            33287,  // Demonic robe top (t3)
            33290,  // Demonic robe bottom (t3)
            33293,  // Demonic boots (t3)
            // -----------------------------------------------------------------
            // CASTLE WARS — DECORATIVE ARMOUR
            // -----------------------------------------------------------------
            // Red set
            4068,   // Decorative sword (red)
            4069,   // Decorative armour (red platebody)
            4070,   // Decorative armour (red platelegs)
            4071,   // Decorative helm (red)
            4072,   // Decorative shield (red)
            11893,  // Decorative armour (red plateskirt)
            25163,  // Decorative boots (red)
            25165,  // Decorative full helm (red)
            // White set
            4503,   // Decorative sword (white)
            4504,   // Decorative armour (white platebody)
            4505,   // Decorative armour (white platelegs)
            4506,   // Decorative helm (white)
            4507,   // Decorative shield (white)
            11894,  // Decorative armour (white plateskirt)
            25167,  // Decorative boots (white)
            25169,  // Decorative full helm (white)
            // Gold set (Normal / Broken / Locked variants)
            4508,   // Decorative sword (gold, normal)
            4509,   // Decorative armour (gold platebody, normal)
            4510,   // Decorative armour (gold platelegs, normal)
            4511,   // Decorative helm (gold, normal)
            4512,   // Decorative shield (gold, normal)
            11895,  // Decorative armour (gold plateskirt, normal)
            24157,  // Decorative sword (gold, locked)
            24158,  // Decorative armour (gold platebody, locked)
            24159,  // Decorative armour (gold platelegs, locked)
            24160,  // Decorative helm (gold, locked)
            24161,  // Decorative shield (gold, locked)
            24162,  // Decorative armour (gold plateskirt, locked)
            25171,  // Decorative boots (gold, normal)
            25173,  // Decorative boots (gold, locked)
            25174,  // Decorative full helm (gold, normal)
            25176,  // Decorative full helm (gold, locked)
            // Magic set
            11896,  // Decorative armour (magic top, normal)
            11897,  // Decorative armour (magic legs, normal)
            11898,  // Decorative armour (magic hat, normal)
            24163,  // Decorative armour (magic top, locked)
            24164,  // Decorative armour (magic legs, locked)
            24165,  // Decorative armour (magic hat, locked)
            // Ranged set
            11899,  // Decorative armour (ranged top, normal)
            11900,  // Decorative armour (ranged legs, normal)
            11901,  // Decorative armour (quiver, normal)
            24166,  // Decorative armour (ranged top, locked)
            24167,  // Decorative armour (ranged legs, locked)
            24168,  // Decorative armour (quiver, locked)
            // -----------------------------------------------------------------
            // DRAGONSTONE ARMOUR (Leagues)
            // -----------------------------------------------------------------
            23667,  // Dragonstone armour set
            24034,  // Dragonstone full helm
            24037,  // Dragonstone platebody
            24040,  // Dragonstone platelegs
            24043,  // Dragonstone boots
            24046,  // Dragonstone gauntlets
            // -----------------------------------------------------------------
            // ELITE BLACK ARMOUR
            // -----------------------------------------------------------------
            29560,  // Elite black full helm
            29562,  // Elite black platebody
            29564,  // Elite black platelegs
            // -----------------------------------------------------------------
            // FARMER'S OUTFIT (Tithe Farm)
            // -----------------------------------------------------------------
            13640,  // Farmer's boro trousers (body type A)
            13641,  // Farmer's boro trousers (body type B)
            13642,  // Farmer's jacket
            13643,  // Farmer's shirt
            13644,  // Farmer's boots (body type A)
            13645,  // Farmer's boots (body type B)
            13646,  // Farmer's strawhat (body type A)
            13647,  // Farmer's strawhat (body type B)
            // -----------------------------------------------------------------
            // MISCELLANEOUS REWARDS
            // -----------------------------------------------------------------
            11990,  // Fedora
            27414,  // Giant stopwatch
            // -----------------------------------------------------------------
            // GRID MASTER TABARD / SWORDS AND EMBLEM (OSRS 25th anniversary)
            // -----------------------------------------------------------------
            31181,  // Grid master tabard
            31184,  // Grid master tabard (b)
            31187,  // Grid master tabard (p)
            31190,  // Grid master tabard (g)
            31193,  // Swords and emblem
            31196,  // Swords and emblem (b)
            31199,  // Swords and emblem (p)
            31202,  // Swords and emblem (g)
            // -----------------------------------------------------------------
            // HAM OUTFIT (Misthalin Mystery / HAM hideout)
            // -----------------------------------------------------------------
            4298,   // Ham shirt
            4300,   // Ham robe
            4302,   // Ham hood
            4304,   // Ham cloak
            4306,   // Ham logo
            4308,   // Ham gloves
            4310,   // Ham boots
            // -----------------------------------------------------------------
            // HALOS (God Wars / Soul Wars)
            // -----------------------------------------------------------------
            12637,  // Saradomin halo (normal)
            24169,  // Saradomin halo (locked)
            12638,  // Zamorak halo (normal)
            24170,  // Zamorak halo (locked)
            12639,  // Guthix halo (normal)
            24171,  // Guthix halo (locked)
            24192,  // Armadyl halo (normal)
            24194,  // Armadyl halo (locked)
            24195,  // Bandos halo (normal)
            24197,  // Bandos halo (locked)
            24198,  // Seren halo (normal)
            24200,  // Seren halo (locked)
            24201,  // Ancient halo (normal)
            24203,  // Ancient halo (locked)
            24204,  // Brassica halo (normal)
            24206,  // Brassica halo (locked)
            // -----------------------------------------------------------------
            // HELM OF RAEDWALD
            // -----------------------------------------------------------------
            19687,  // Helm of raedwald
            // -----------------------------------------------------------------
            // HUNTER OUTFITS (Hunter skill)
            // -----------------------------------------------------------------
            10035,  // Kyatt legs
            10037,  // Kyatt top
            10039,  // Kyatt hat
            10041,  // Larupia legs
            10043,  // Larupia top
            10045,  // Larupia hat
            10047,  // Graahk legs
            10049,  // Graahk top
            10051,  // Graahk headdress
            // -----------------------------------------------------------------
            // GUILD HUNTER OUTFIT (Hunter Guild)
            // -----------------------------------------------------------------
            29263,  // Guild hunter headwear
            29265,  // Guild hunter top
            29267,  // Guild hunter legs
            29269,  // Guild hunter boots
            // -----------------------------------------------------------------
            // INQUISITOR'S ARMOUR (Theatre of Blood)
            // -----------------------------------------------------------------
            24419,  // Inquisitor's great helm
            24420,  // Inquisitor's hauberk
            24421,  // Inquisitor's plateskirt
            24488,  // Inquisitor's armour set
            // -----------------------------------------------------------------
            // JUSTICIAR ARMOUR (Theatre of Blood)
            // -----------------------------------------------------------------
            22326,  // Justiciar faceguard
            22327,  // Justiciar chestguard
            22328,  // Justiciar legguards
            22438,  // Justiciar armour set
            // -----------------------------------------------------------------
            // LUMBERJACK OUTFIT (Temple Trekking)
            // -----------------------------------------------------------------
            10933,  // Lumberjack boots
            10939,  // Lumberjack top
            10940,  // Lumberjack legs
            10941,  // Lumberjack hat
            // -----------------------------------------------------------------
            // FORESTRY OUTFIT (Forestry)
            // -----------------------------------------------------------------
            28169,  // Forestry top
            28171,  // Forestry legs
            28173,  // Forestry hat
            28175,  // Forestry boots
            // -----------------------------------------------------------------
            // MASORI ARMOUR (Tombs of Amascut)
            // -----------------------------------------------------------------
            27226,  // Masori mask
            27229,  // Masori body
            27232,  // Masori chaps
            27235,  // Masori mask (f)
            27238,  // Masori body (f)
            27241,  // Masori chaps (f)
            27355,  // Masori armour set (f)
            // -----------------------------------------------------------------
            // MINING GLOVES (Mining Guild)
            // -----------------------------------------------------------------
            21343,  // Mining gloves
            21345,  // Superior mining gloves
            21392,  // Expert mining gloves
            // -----------------------------------------------------------------
            // MIXED HIDE OUTFIT (Hunters' Guild)
            // -----------------------------------------------------------------
            29280,  // Mixed hide top
            29283,  // Mixed hide legs
            29286,  // Mixed hide boots
            29289,  // Mixed hide cape
            // -----------------------------------------------------------------
            // MOURNER OUTFIT + GAS MASK (Mourning's End Part I)
            // -----------------------------------------------------------------
            1506,   // Gas mask
            6065,   // Mourner top
            6067,   // Mourner trousers
            6068,   // Mourner gloves
            6069,   // Mourner boots
            6070,   // Mourner cloak
            // -----------------------------------------------------------------
            // OATHPLATE ARMOUR
            // -----------------------------------------------------------------
            30744,  // Oathplate armour set
            30750,  // Oathplate helm
            30753,  // Oathplate chest
            30756,  // Oathplate legs
            // -----------------------------------------------------------------
            // OBSIDIAN ARMOUR (TzHaar)
            // -----------------------------------------------------------------
            21279,  // Obsidian armour set
            21298,  // Obsidian helmet
            21301,  // Obsidian platebody
            21304,  // Obsidian platelegs
            // -----------------------------------------------------------------
            // ORNATE ARMOUR (Leagues — Trailblazer)
            // -----------------------------------------------------------------
            23091,  // Ornate gloves
            23093,  // Ornate boots
            23095,  // Ornate legs
            23097,  // Ornate top
            23099,  // Ornate cape
            23101,  // Ornate helm
            // -----------------------------------------------------------------
            // BARBARIAN ASSAULT GEAR (Penance / Fighter armour)
            // -----------------------------------------------------------------
            10547,  // Healer hat (normal)
            10548,  // Fighter hat (normal)
            10549,  // Runner hat (normal)
            10550,  // Ranger hat (normal)
            10551,  // Fighter torso (normal)
            10552,  // Runner boots
            10553,  // Penance gloves (inventory)
            10555,  // Penance skirt (normal)
            24172,  // Healer hat (locked)
            24173,  // Fighter hat (locked)
            24174,  // Ranger hat (locked)
            24175,  // Fighter torso (locked)
            24176,  // Penance skirt (locked)
            24533,  // Runner hat (locked)
            28067,  // Fighter torso (or, normal)
            28069,  // Fighter torso (or, locked)
            // -----------------------------------------------------------------
            // PROSPECTOR OUTFIT (Motherlode / Volcanic Mine)
            // -----------------------------------------------------------------
            12013,  // Prospector helmet (Motherlode)
            12014,  // Prospector jacket (Motherlode)
            12015,  // Prospector legs (Motherlode)
            12016,  // Prospector boots (Motherlode)
            25549,  // Golden prospector helmet
            25551,  // Golden prospector jacket
            25553,  // Golden prospector legs
            25555,  // Golden prospector boots
            // -----------------------------------------------------------------
            // RADIANT OATHPLATE ARMOUR
            // -----------------------------------------------------------------
            30777,  // Radiant oathplate helm
            30779,  // Radiant oathplate chest
            30781,  // Radiant oathplate legs
            // -----------------------------------------------------------------
            // RAGING ECHOES OUTFIT (Leagues)
            // -----------------------------------------------------------------
            30331,  // Raging echoes relic hunter (t1) armour set
            30334,  // Raging echoes relic hunter (t2) armour set
            30337,  // Raging echoes relic hunter (t3) armour set
            30404,  // Raging echoes hat (t1)
            30406,  // Raging echoes top (t1)
            30408,  // Raging echoes robeskirt (t1)
            30410,  // Raging echoes boots (t1)
            30412,  // Raging echoes hat (t2)
            30414,  // Raging echoes top (t2)
            30416,  // Raging echoes robeskirt (t2)
            30418,  // Raging echoes boots (t2)
            30420,  // Raging echoes hat (t3)
            30422,  // Raging echoes top (t3)
            30424,  // Raging echoes robeskirt (t3)
            30426,  // Raging echoes boots (t3)
            // -----------------------------------------------------------------
            // ROCK-SHELL ARMOUR (Dagannoth Lair)
            // -----------------------------------------------------------------
            6128,   // Rock-shell helm
            6129,   // Rock-shell plate
            6130,   // Rock-shell legs
            6145,   // Rock-shell boots
            6151,   // Rock-shell gloves
            31151,  // Rock-shell armour set
            // -----------------------------------------------------------------
            // ROGUE OUTFIT (Rogues' Den)
            // -----------------------------------------------------------------
            5553,   // Rogue top
            5554,   // Rogue mask
            5555,   // Rogue trousers
            5556,   // Rogue gloves
            5557,   // Rogue boots
            // -----------------------------------------------------------------
            // SHATTERED OUTFIT (Leagues)
            // -----------------------------------------------------------------
            26427,  // Shattered hood (t1)
            26430,  // Shattered top (t1)
            26433,  // Shattered trousers (t1)
            26436,  // Shattered boots (t1)
            26439,  // Shattered hood (t2)
            26442,  // Shattered top (t2)
            26445,  // Shattered trousers (t2)
            26448,  // Shattered boots (t2)
            26451,  // Shattered hood (t3)
            26454,  // Shattered top (t3)
            26457,  // Shattered trousers (t3)
            26460,  // Shattered boots (t3)
            26554,  // Shattered relic hunter (t1) armour set
            26557,  // Shattered relic hunter (t2) armour set
            26560,  // Shattered relic hunter (t3) armour set
            // -----------------------------------------------------------------
            // SHAYZIEN ARMOUR (Kourend)
            // -----------------------------------------------------------------
            13357, 13358, 13359, 13360, 13361,  // Tier 1 (gloves/boots/helm/greaves/platebody)
            13362, 13363, 13364, 13365, 13366,  // Tier 2
            13367, 13368, 13369, 13370, 13371,  // Tier 3
            13372, 13373, 13374, 13375, 13376,  // Tier 4
            13377, 13378, 13379, 13380, 13381,  // Tier 5
            // -----------------------------------------------------------------
            // SNAKESKIN ARMOUR (Crafting)
            // -----------------------------------------------------------------
            6322,   // Snakeskin body
            6324,   // Snakeskin chaps
            6326,   // Snakeskin bandana
            6328,   // Snakeskin boots
            6330,   // Snakeskin vambraces
            // -----------------------------------------------------------------
            // SPINED ARMOUR (Dagannoth Lair)
            // -----------------------------------------------------------------
            6131,   // Spined helm
            6133,   // Spined body
            6135,   // Spined chaps
            6143,   // Spined boots
            6149,   // Spined gloves
            31157,  // Spined armour set
            // -----------------------------------------------------------------
            // FANCY / FIGHTING BOOTS (Rat Pits)
            // -----------------------------------------------------------------
            9005,   // Fancy boots
            9006,   // Fighting boots
            28672,  // Fancier boots
            // -----------------------------------------------------------------
            // SUNFIRE FANATIC ARMOUR (Fortis Colosseum)
            // -----------------------------------------------------------------
            28933,  // Sunfire fanatic helm
            28936,  // Sunfire fanatic cuirass
            28939,  // Sunfire fanatic chausses
            29424,  // Sunfire fanatic armour set
            // -----------------------------------------------------------------
            // SWIFT BLADE (Inferno / TzKal-Zuk)
            // -----------------------------------------------------------------
            24219,  // Swift blade
            // -----------------------------------------------------------------
            // TEMPLE KNIGHT ARMOUR — INITIATE / PROSELYTE (Recruitment Drive)
            // -----------------------------------------------------------------
            5574,   // Initiate sallet
            5575,   // Initiate hauberk
            5576,   // Initiate cuisse
            9666,   // Proselyte harness m
            9668,   // Initiate harness m
            9670,   // Proselyte harness f
            9672,   // Proselyte sallet
            9674,   // Proselyte hauberk
            9676,   // Proselyte cuisse
            9678,   // Proselyte tasset
            // -----------------------------------------------------------------
            // TRAILBLAZER RELOADED TORCH
            // -----------------------------------------------------------------
            28748,  // Trailblazer reloaded torch
            // -----------------------------------------------------------------
            // TRAILBLAZER OUTFIT (Leagues)
            // -----------------------------------------------------------------
            25001,  // Trailblazer hood (t3)
            25004,  // Trailblazer top (t3)
            25007,  // Trailblazer trousers (t3)
            25010,  // Trailblazer boots (t3)
            25016,  // Trailblazer hood (t2)
            25019,  // Trailblazer top (t2)
            25022,  // Trailblazer trousers (t2)
            25025,  // Trailblazer boots (t2)
            25028,  // Trailblazer hood (t1)
            25031,  // Trailblazer top (t1)
            25034,  // Trailblazer trousers (t1)
            25037,  // Trailblazer boots (t1)
            25380,  // Trailblazer relic hunter (t1) armour set
            25383,  // Trailblazer relic hunter (t2) armour set
            25386,  // Trailblazer relic hunter (t3) armour set
            // -----------------------------------------------------------------
            // TRAILBLAZER RELOADED OUTFIT (Leagues)
            // -----------------------------------------------------------------
            28712,  // Trailblazer reloaded headband (t1)
            28715,  // Trailblazer reloaded top (t1)
            28718,  // Trailblazer reloaded trousers (t1)
            28721,  // Trailblazer reloaded boots (t1)
            28724,  // Trailblazer reloaded headband (t2)
            28727,  // Trailblazer reloaded top (t2)
            28730,  // Trailblazer reloaded trousers (t2)
            28733,  // Trailblazer reloaded boots (t2)
            28736,  // Trailblazer reloaded headband (t3)
            28739,  // Trailblazer reloaded top (t3)
            28742,  // Trailblazer reloaded trousers (t3)
            28745,  // Trailblazer reloaded boots (t3)
            28777,  // Trailblazer reloaded relic hunter (t1) armour set
            28780,  // Trailblazer reloaded relic hunter (t2) armour set
            28783,  // Trailblazer reloaded relic hunter (t3) armour set
            // -----------------------------------------------------------------
            // TRIBAL MASKS (Zogre Flesh Eaters)
            // -----------------------------------------------------------------
            6335,   // Tribal mask (poison)
            6337,   // Tribal mask (disease)
            6339,   // Tribal mask (combat)
            // -----------------------------------------------------------------
            // TWISTED OUTFIT (Leagues)
            // -----------------------------------------------------------------
            24387,  // Twisted hat (t3)
            24389,  // Twisted coat (t3)
            24391,  // Twisted trousers (t3)
            24393,  // Twisted boots (t3)
            24397,  // Twisted hat (t2)
            24399,  // Twisted coat (t2)
            24401,  // Twisted trousers (t2)
            24403,  // Twisted boots (t2)
            24405,  // Twisted hat (t1)
            24407,  // Twisted coat (t1)
            24409,  // Twisted trousers (t1)
            24411,  // Twisted boots (t1)
            24469,  // Twisted relic hunter (t1) armour set
            24472,  // Twisted relic hunter (t2) armour set
            24475,  // Twisted relic hunter (t3) armour set
            // -----------------------------------------------------------------
            // VOID KNIGHT ARMOUR (Pest Control)
            // -----------------------------------------------------------------
            // Regular void
            8839,   // Void knight top (normal)
            8840,   // Void knight robe (normal)
            8842,   // Void knight gloves (normal)
            24177,  // Void knight top (locked)
            24178,  // Elite void top (locked)
            24179,  // Void knight robe (locked)
            24180,  // Elite void robe (locked)
            24182,  // Void knight gloves (locked)
            24183,  // Void mage helm (locked)
            24184,  // Void ranger helm (locked)
            24185,  // Void melee helm (locked)
            // Elite void
            13072,  // Elite void top (normal)
            13073,  // Elite void robe (normal)
            // Void helms
            11663,  // Void mage helm (normal)
            11664,  // Void ranger helm (normal)
            11665,  // Void melee helm (normal)
            // Ornate (or) variants
            26463,  // Void knight top (or, normal)
            26465,  // Void knight robe (or, normal)
            26467,  // Void knight gloves (or, normal)
            26469,  // Elite void top (or, normal)
            26471,  // Elite void robe (or, normal)
            26473,  // Void mage helm (or, normal)
            26475,  // Void ranger helm (or, normal)
            26477,  // Void melee helm (or, normal)
            27000,  // Void knight top (or, locked)
            27001,  // Void knight robe (or, locked)
            27002,  // Void knight gloves (or, locked)
            27003,  // Elite void top (or, locked)
            27004,  // Elite void robe (or, locked)
            27005,  // Void mage helm (or, locked)
            27006,  // Void ranger helm (or, locked)
            27007,  // Void melee helm (or, locked)
            // -----------------------------------------------------------------
            // VYRE NOBLE OUTFIT (Sins of the Father)
            // -----------------------------------------------------------------
            24676,  // Vyre noble top
            24678,  // Vyre noble legs
            24680,  // Vyre noble shoes
            // -----------------------------------------------------------------
            // WHITE ARMOUR (Sir Vyvin / White Knights)
            // -----------------------------------------------------------------
            6617,   // White platebody
            6619,   // White boots
            6623,   // White full helm
            6625,   // White platelegs
            6627,   // White plateskirt
            6629,   // White gloves
            6633,   // White kiteshield
            // -----------------------------------------------------------------
            // XERICIAN ROBES (Chambers of Xeric)
            // -----------------------------------------------------------------
            13385,  // Xerician hat
            13387,  // Xerician top
            13389   // Xerician robe
        ))
    );
    /**
     * The 50 numbered Team capes (Team-1 through Team-50) that share a single
     * Cape Rack slot — only ONE of these can be stored in the POH at a time.
     * Used by the plugin to detect when a player has excess copies in the bank.
     *
     * <p>Team Cape I (20217), Team Cape X (20214), and Team Cape Zero (20211)
     * are separate items stored in their own independent slots and are NOT in
     * this set; they live in {@link #POH_CAPE_RACK_ITEMS} only.</p>
     */
    public static final Set<Integer> TEAM_CAPES_1_TO_50 = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            4315,  // Team-1 cape
            4317,  // Team-2 cape
            4319,  // Team-3 cape
            4321,  // Team-4 cape
            4323,  // Team-5 cape
            4325,  // Team-6 cape
            4327,  // Team-7 cape
            4329,  // Team-8 cape
            4331,  // Team-9 cape
            4333,  // Team-10 cape
            4335,  // Team-11 cape
            4337,  // Team-12 cape
            4339,  // Team-13 cape
            4341,  // Team-14 cape
            4343,  // Team-15 cape
            4345,  // Team-16 cape
            4347,  // Team-17 cape
            4349,  // Team-18 cape
            4351,  // Team-19 cape
            4353,  // Team-20 cape
            4355,  // Team-21 cape
            4357,  // Team-22 cape
            4359,  // Team-23 cape
            4361,  // Team-24 cape
            4363,  // Team-25 cape
            4365,  // Team-26 cape
            4367,  // Team-27 cape
            4369,  // Team-28 cape
            4371,  // Team-29 cape
            4373,  // Team-30 cape
            4375,  // Team-31 cape
            4377,  // Team-32 cape
            4379,  // Team-33 cape
            4381,  // Team-34 cape
            4383,  // Team-35 cape
            4385,  // Team-36 cape
            4387,  // Team-37 cape
            4389,  // Team-38 cape
            4391,  // Team-39 cape
            4393,  // Team-40 cape
            4395,  // Team-41 cape
            4397,  // Team-42 cape
            4399,  // Team-43 cape
            4401,  // Team-44 cape
            4403,  // Team-45 cape
            4405,  // Team-46 cape
            4407,  // Team-47 cape
            4409,  // Team-48 cape
            4411,  // Team-49 cape
            4413   // Team-50 cape
        ))
    );
    /**
     * Items storable in the Cape Rack in the Costume Room of the player-owned
     * house. Includes skill capes and hoods, max capes, achievement/minigame
     * reward capes, Team capes, and various other wearable capes.
     *
     * <p><b>Trouver Parchment (locked / "l" variants):</b> items with a
     * {@code #Locked} suffix or {@code (l)} in their name are the Trouver
     * Parchment version of that cape. These can be stored in the Cape Rack
     * <em>simultaneously</em> with the regular version of the same cape —
     * they occupy a separate slot.</p>
     *
     * <p><b>Team-1 through Team-50 capes:</b> only ONE of these fifty capes
     * can fill the single shared slot in the Cape Rack. Team Cape I (20217),
     * Team Cape X (20214), and Team Cape Zero (20211) are independent items
     * and each fill their own separate slot. The plugin contains special
     * logic to flag excess Team-1..50 capes as junk when more than one is
     * detected in the bank.</p>
     *
     * All IDs player-confirmed June 2026.
     */
    public static final Set<Integer> POH_CAPE_RACK_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // -----------------------------------------------------------------
            // ACHIEVEMENT DIARY CAPE
            // -----------------------------------------------------------------
            13069,  // Achievement diary cape (trimmed)
            13070,  // Achievement diary hood
            19476,  // Achievement diary cape (untrimmed)
            // -----------------------------------------------------------------
            // SKILL CAPES (alphabetical by skill)
            // Each skill has: untrimmed, trimmed, hood.
            // Where two IDs exist (old vs new graphic), both are included.
            // -----------------------------------------------------------------
            9771,   // Agility cape (untrimmed, old)
            9772,   // Agility cape (trimmed, old)
            9773,   // Agility hood
            13340,  // Agility cape (untrimmed, new)
            13341,  // Agility cape (trimmed, new)
            9747,   // Attack cape (untrimmed)
            9748,   // Attack cape (trimmed)
            9749,   // Attack hood
            9789,   // Construction cape (untrimmed)
            9790,   // Construction cape (trimmed)
            9791,   // Construction hood
            9801,   // Cooking cape (untrimmed)
            9802,   // Cooking cape (trimmed)
            9803,   // Cooking hood
            9780,   // Crafting cape (untrimmed)
            9781,   // Crafting cape (trimmed)
            9782,   // Crafting hood
            9753,   // Defence cape (untrimmed)
            9754,   // Defence cape (trimmed)
            9755,   // Defence hood
            9810,   // Farming cape (untrimmed)
            9811,   // Farming cape (trimmed)
            9812,   // Farming hood
            9804,   // Firemaking cape (untrimmed)
            9805,   // Firemaking cape (trimmed)
            9806,   // Firemaking hood
            9798,   // Fishing cape (untrimmed)
            9799,   // Fishing cape (trimmed)
            9800,   // Fishing hood
            9783,   // Fletching cape (untrimmed)
            9784,   // Fletching cape (trimmed)
            9785,   // Fletching hood
            9774,   // Herblore cape (untrimmed)
            9775,   // Herblore cape (trimmed)
            9776,   // Herblore hood
            9768,   // Hitpoints cape (untrimmed)
            9769,   // Hitpoints cape (trimmed)
            9770,   // Hitpoints hood
            9948,   // Hunter cape (untrimmed)
            9949,   // Hunter cape (trimmed)
            9950,   // Hunter hood
            9762,   // Magic cape (untrimmed)
            9763,   // Magic cape (trimmed)
            9764,   // Magic hood
            9792,   // Mining cape (untrimmed)
            9793,   // Mining cape (trimmed)
            9794,   // Mining hood
            13221,  // Music cape (untrimmed)
            13222,  // Music cape (trimmed)
            13223,  // Music hood
            9759,   // Prayer cape (untrimmed)
            9760,   // Prayer cape (trimmed)
            9761,   // Prayer hood
            9756,   // Ranging cape (untrimmed)
            9757,   // Ranging cape (trimmed)
            9758,   // Ranging hood
            9765,   // Runecraft cape (untrimmed)
            9766,   // Runecraft cape (trimmed)
            9767,   // Runecraft hood
            31288,  // Sailing cape (untrimmed)
            31290,  // Sailing cape (trimmed)
            31292,  // Sailing hood
            9786,   // Slayer cape (untrimmed)
            9787,   // Slayer cape (trimmed)
            9788,   // Slayer hood
            9795,   // Smithing cape (untrimmed)
            9796,   // Smithing cape (trimmed)
            9797,   // Smithing hood
            9750,   // Strength cape (untrimmed)
            9751,   // Strength cape (trimmed)
            9752,   // Strength hood
            9777,   // Thieving cape (untrimmed)
            9778,   // Thieving cape (trimmed)
            9779,   // Thieving hood
            9807,   // Woodcutting cape (untrimmed)
            9808,   // Woodcutting cape (trimmed)
            9809,   // Woodcutting hood
            // -----------------------------------------------------------------
            // QUEST / ACHIEVEMENT CAPES
            // -----------------------------------------------------------------
            9813,   // Quest point cape (untrimmed)
            9814,   // Quest point hood
            13068,  // Quest point cape (trimmed)
            // -----------------------------------------------------------------
            // MAX CAPES AND HOODS
            // -----------------------------------------------------------------
            13280,  // Max cape (inventory)
            13281,  // Max hood
            13329,  // Fire max cape (normal)
            13330,  // Fire max hood
            13331,  // Saradomin max cape
            13332,  // Saradomin max hood
            13333,  // Zamorak max cape
            13334,  // Zamorak max hood
            13335,  // Guthix max cape
            13336,  // Guthix max hood
            13337,  // Accumulator max cape
            13338,  // Accumulator max hood
            20760,  // Ardougne max cape
            20764,  // Ardougne max hood
            21282,  // Infernal max hood
            21285,  // Infernal max cape (normal)
            21776,  // Imbued saradomin max cape (normal)
            21778,  // Imbued saradomin max hood
            21780,  // Imbued zamorak max cape (normal)
            21782,  // Imbued zamorak max hood
            21784,  // Imbued guthix max cape (normal)
            21786,  // Imbued guthix max hood
            21898,  // Assembler max cape (normal)
            21900,  // Assembler max hood
            24133,  // Infernal max cape (locked — Trouver Parchment)
            24134,  // Fire max cape (locked — Trouver Parchment)
            24135,  // Assembler max cape (locked — Trouver Parchment)
            24232,  // Imbued saradomin max cape (locked — Trouver Parchment)
            24233,  // Imbued zamorak max cape (locked — Trouver Parchment)
            24234,  // Imbued guthix max cape (locked — Trouver Parchment)
            24855,  // Mythical max cape
            24857,  // Mythical max hood
            27363,  // Masori assembler max cape (normal)
            27365,  // Masori assembler max cape (locked — Trouver Parchment)
            27366,  // Masori assembler max hood
            28902,  // Dizana's max cape (normal)
            28904,  // Dizana's max hood
            28906,  // Dizana's max cape (locked — Trouver Parchment)
            // -----------------------------------------------------------------
            // MINIGAME / REWARD CAPES
            // -----------------------------------------------------------------
            1052,   // Cape of legends
            6568,   // Obsidian cape
            6570,   // Fire cape (normal)
            21295,  // Infernal cape (normal)
            21439,  // Champion's cape
            22114,  // Mythical cape
            23859,  // Gauntlet cape
            24207,  // Victor's cape (1)
            24209,  // Victor's cape (10)
            24211,  // Victor's cape (50)
            24213,  // Victor's cape (100)
            24215,  // Victor's cape (500)
            24520,  // Victor's cape (1000)
            // Trouver Parchment (locked) variants — stored alongside normal:
            24223,  // Fire cape (locked — Trouver Parchment)
            24224,  // Infernal cape (locked — Trouver Parchment)
            // -----------------------------------------------------------------
            // GOD / IMBUED GOD CAPES (Mage Arena)
            // -----------------------------------------------------------------
            2412,   // Saradomin cape
            2413,   // Guthix cape
            2414,   // Zamorak cape
            21791,  // Imbued saradomin cape (normal)
            21793,  // Imbued guthix cape (normal)
            21795,  // Imbued zamorak cape (normal)
            24248,  // Imbued saradomin cape (locked — Trouver Parchment)
            24249,  // Imbued guthix cape (locked — Trouver Parchment)
            24250,  // Imbued zamorak cape (locked — Trouver Parchment)
            29613,  // Imbued zamorak cape (deadman)
            29615,  // Imbued guthix cape (deadman)
            29617,  // Imbued saradomin cape (deadman)
            // -----------------------------------------------------------------
            // THEATRE OF BLOOD — SINHAZA SHROUDS
            // -----------------------------------------------------------------
            22494,  // Sinhaza shroud tier 1
            22496,  // Sinhaza shroud tier 2
            22498,  // Sinhaza shroud tier 3
            22500,  // Sinhaza shroud tier 4
            22502,  // Sinhaza shroud tier 5
            // -----------------------------------------------------------------
            // SOUL WARS — SOUL CAPE
            // -----------------------------------------------------------------
            25344,  // Soul cape (red)
            25346,  // Soul cape (blue)
            // -----------------------------------------------------------------
            // TOMBS OF AMASCUT — ICTHLARIN'S SHROUD
            // -----------------------------------------------------------------
            27257,  // Icthlarin's shroud (tier 1)
            27259,  // Icthlarin's shroud (tier 2)
            27261,  // Icthlarin's shroud (tier 3)
            27263,  // Icthlarin's shroud (tier 4)
            27265,  // Icthlarin's shroud (tier 5)
            // -----------------------------------------------------------------
            // CHAMBERS OF XERIC — XERIC'S TALISMAN CAPES
            // -----------------------------------------------------------------
            22388,  // Xeric's guard
            22390,  // Xeric's warrior
            22392,  // Xeric's sentinel
            22394,  // Xeric's general
            22396,  // Xeric's champion
            // -----------------------------------------------------------------
            // SPOTTED / SPOTTIER CAPES (Hunter)
            // -----------------------------------------------------------------
            10069,  // Spotted cape (inventory)
            10071,  // Spottier cape (inventory)
            // -----------------------------------------------------------------
            // TEAM CAPES — numbered 1–50 (ONE slot shared; extras are junk)
            // See TEAM_CAPES_1_TO_50 and plugin logic for excess-cape detection.
            // -----------------------------------------------------------------
            4315, 4317, 4319, 4321, 4323, 4325, 4327, 4329, 4331, 4333,  // 1–10
            4335, 4337, 4339, 4341, 4343, 4345, 4347, 4349, 4351, 4353,  // 11–20
            4355, 4357, 4359, 4361, 4363, 4365, 4367, 4369, 4371, 4373,  // 21–30
            4375, 4377, 4379, 4381, 4383, 4385, 4387, 4389, 4391, 4393,  // 31–40
            4395, 4397, 4399, 4401, 4403, 4405, 4407, 4409, 4411, 4413,  // 41–50
            // Independent team capes — each has its own slot (not part of the 1–50 group):
            20211, // Team cape zero
            20214, // Team cape x
            20217  // Team cape i
        ))
    );
    /**
     * Wiki URL overrides for holiday items whose page lives at an anchor
     * (e.g. variant items like {@code Beach_boxing_gloves#Pink}).
     *
     * <p>If an item ID is present here, the panel's "View on Wiki" right-click
     * will use this URL instead of the auto-generated one. Add entries whenever
     * a holiday item's wiki page uses a {@code #Fragment} anchor.</p>
     */
    public static final Map<Integer, String> HOLIDAY_WIKI_URLS;
    static
    {
        Map<Integer, String> m = new HashMap<>();
        m.put(1419,  "https://oldschool.runescape.wiki/w/Scythe_(Halloween_event)");
        m.put(11705, "https://oldschool.runescape.wiki/w/Beach_boxing_gloves#Yellow");
        m.put(11706, "https://oldschool.runescape.wiki/w/Beach_boxing_gloves#Pink");
        HOLIDAY_WIKI_URLS = Collections.unmodifiableMap(m);
    }
    /**
     * Override wiki URLs for POH storable items whose in-game name does not auto-generate
     * the correct wiki URL (e.g. disambiguation pages or variant anchors).
     */
    public static final Map<Integer, String> POH_STORAGE_WIKI_URLS;
    static
    {
        Map<Integer, String> m = new HashMap<>();
        m.put(6335, "https://oldschool.runescape.wiki/w/Tribal_mask_(poison)");
        m.put(6337, "https://oldschool.runescape.wiki/w/Tribal_mask_(disease)");
        m.put(6339, "https://oldschool.runescape.wiki/w/Tribal_mask_(combat)");
        POH_STORAGE_WIKI_URLS = Collections.unmodifiableMap(m);
    }
    /**
     * Returns the full, immutable list of junk entries.
     * Consumers should not modify the list; filter as needed.
     */
    public static List<JunkEntry> getEntries()
    {
        return ENTRIES;
    }
    /**
     * Returns the curated entries indexed by item ID for O(1) lookup.
     * Prefer this over {@link #getEntries()} when checking specific item IDs
     * (e.g., iterating bankedIds rather than all DB entries).
     */
    public static Map<Integer, JunkEntry> getEntriesByItemId()
    {
        return ENTRIES_BY_ID;
    }
    // Built after ENTRIES so the field initializer runs in declaration order.
    private static final Map<Integer, JunkEntry> ENTRIES_BY_ID = buildEntriesById();
    private static Map<Integer, JunkEntry> buildEntriesById()
    {
        Map<Integer, JunkEntry> m = new HashMap<>(ENTRIES.size() * 2);
        for (JunkEntry e : ENTRIES)
        {
            JunkEntry displaced = m.put(e.itemId, e);
            if (displaced != null)
            {
                throw new IllegalStateException(
                    "Duplicate itemId in JunkDatabase.ENTRIES: id=" + e.itemId
                    + " name=\"" + e.name + "\" collides with \"" + displaced.name + "\"");
            }
        }
        return Collections.unmodifiableMap(m);
    }
    // =========================================================================
    // HOLIDAY / EVENT ITEMS
    //
    // Untradeable seasonal/event items that can be safely dropped and reclaimed
    // from Diango in Draynor Village, or stored in the player-owned house toy box.
    //
    // RULES:
    //   - ONLY include untradeable, reclaimable items.
    //   - Tradeable holiday rares (original party hats 1038/1040/1042/1044/1046/1048,
    //     H'ween masks 1053/1055/1057, pumpkin 1959, Easter egg 1961, Santa hat 1050,
    //     half full wine jug 1989, disk of returning 981, etc.) must NOT be here.
    //   - All IDs below are player-confirmed as of July 2026.
    // =========================================================================
    /**
     * Untradeable holiday and seasonal event items reclaimable from Diango in
     * Draynor Village (or storable in the player-owned house toy box).
     * All IDs player-confirmed July 2026.
     *
     * <p>EXCLUDED items are documented in comments below with reasons so they
     * are not accidentally re-added in future.</p>
     */
    public static final Set<Integer> HOLIDAY_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // =================================================================
            // BIRTHDAY / ANNIVERSARY
            // =================================================================
            795,    // War ship                      — Birthday 2013
            11919,  // Cow mask                      — Birthday event
            12956,  // Cow top                       — Birthday event
            12957,  // Cow trousers                  — Birthday event
            12958,  // Cow gloves                    — Birthday event
            12959,  // Cow shoes                     — Birthday event
            13655,  // Gnome child hat               — Birthday event
            21209,  // Birthday balloons             — Birthday event
            21211,  // 4th birthday hat              — 4th anniversary
            22316,  // Prop sword                    — Birthday 2018
            24525,  // Cat ears                      — Birthday event
            24527,  // Hell cat ears                 — Birthday event
            25322,  // 20th anniversary hat          — 20th anniversary
            25324,  // 20th anniversary top          — 20th anniversary
            25326,  // 20th anniversary bottom       — 20th anniversary
            25328,  // 20th anniversary boots        — 20th anniversary
            25330,  // 20th anniversary gloves       — 20th anniversary
            25332,  // 20th anniversary necklace     — 20th anniversary
            25334,  // 20th anniversary cape         — 20th anniversary
            25336,  // Gnome child mask              — Birthday event
            25338,  // Gnome child icon              — Birthday event
            25500,  // Cursed banana                 — Birthday event
            25502,  // Banana cape                   — Birthday event
            26649,  // Skis                          — Birthday event
            27802,  // Gnome child backpack          — 10th anniversary
            27804,  // Cake hat                      — 10th anniversary
            27806,  // Bob the cat slippers          — 10th anniversary
            27808,  // Jad slippers                  — 10th anniversary
            27810,  // Dragon candle dagger          — 10th anniversary
            27812,  // 10th birthday cape            — 10th anniversary
            27814,  // Jad plush                     — 10th anniversary
            27816,  // Stray dog plush               — 10th anniversary
            27818,  // Gnome child plush             — 10th anniversary
            27820,  // 10th birthday balloons        — 10th anniversary
            27822,  // Old school jumper (variant 1) — Birthday event
            27823,  // Old school jumper (variant 2) — Birthday event
            27824,  // Old school jumper (variant 3) — Birthday event
            27825,  // Old school jumper (variant 4) — Birthday event
            27826,  // Old school jumper (variant 5) — Birthday event
            27827,  // Old school jumper (variant 6) — Birthday event
            27828,  // Silver partyhat               — Birthday event (untradeable)
            30646,  // Classic imp tail              — Birthday event
            30648,  // Classic imp hood              — Birthday event
            23108,  // Birthday cake                 — Birthday event
            33080,  // 25th anniversary helmet           — 25th anniversary
            33082,  // 25th anniversary warrior tabard   — 25th anniversary
            33084,  // 25th anniversary skeleton tabard  — 25th anniversary
            33086,  // 25th anniversary 5x5 hat          — 25th anniversary
            // =================================================================
            // EASTER
            // =================================================================
            1037,   // Bunny ears                    — Easter 2004
            4565,   // Easter basket                 — Easter event
            4566,   // Rubber chicken                — Easter event
            7927,   // Easter ring                   — Easter 2007
            11019,  // Chicken feet                  — Easter event
            11020,  // Chicken wings                 — Easter event
            11021,  // Chicken head                  — Easter event
            11022,  // Chicken legs                  — Easter event
            13182,  // Bunny feet                    — Easter event
            13663,  // Bunny top                     — Easter event
            13664,  // Bunny legs                    — Easter event
            13665,  // Bunny paws                    — Easter event
            21214,  // Easter egg helm               — Easter event
            22351,  // Eggshell platebody            — Easter event
            22353,  // Eggshell platelegs            — Easter event
            23446,  // Giant easter egg              — Easter event
            23448,  // Bunnyman mask                 — Easter event
            24535,  // Magic egg ball                — Easter event
            24537,  // Carrot sword                  — Easter event
            24539,  // '24-carat' sword              — Easter event
            25604,  // Gregg's eastdoor              — Easter event
            25606,  // Propeller hat                 — Easter event
            26937,  // Easter hat                    — Easter event
            26939,  // Crate ring                    — Easter event
            27871,  // Giant bronze dagger           — Easter event
            27873,  // Eastfloor spade               — Easter event
            27875,  // Nest hat (Easter egg variant) — Easter event
            27877,  // Nest hat (Chick variant)      — Easter event
            29433,  // Book of egg                   — Easter event
            29437,  // Egg priest robe               — Easter event
            29439,  // Egg priest robe top           — Easter event
            29441,  // Egg priest necklace           — Easter event
            29443,  // Egg priest mitre              — Easter event
            30720,  // Carrot costume hat            — Easter event
            30722,  // Carrot costume body           — Easter event
            30724,  // Carrot costume tights         — Easter event
            30726,  // Carrot costume gloves         — Easter event
            33149,  // Archibald (Event variant)     — Easter event
            33151,  // Archibald                     — Easter event
            33153,  // Archibald (Grid)              — Easter event
            33155,  // Archibald (Bunny)             — Easter event
            33157,  // Archibald (Diamonds)          — Easter event
            33159,  // Archibald (Chick)             — Easter event
            33161,  // Archibald (Melted)            — Easter event
            33163,  // Archibald (Dragon)            — Easter event
            // EXCLUDED: Easter egg (1961) — tradeable
            // =================================================================
            // PRIDE
            // =================================================================
            21314,  // Rainbow scarf (Pride)         — Pride event
            27035,  // Flower crown (Pride)          — Pride event
            27141,  // Flower crown (Bisexual)       — Pride event
            27143,  // Flower crown (Asexual)        — Pride event
            27145,  // Flower crown (Transgender)    — Pride event
            27147,  // Flower crown (Pansexual)      — Pride event
            27149,  // Flower crown (Non-binary)     — Pride event
            27151,  // Flower crown (Genderqueer)    — Pride event
            27153,  // Flower crown (Lesbian)        — Pride event
            27155,  // Flower crown (Gay)            — Pride event
            28108,  // Rainbow scarf (Bisexual)      — Pride event
            28109,  // Rainbow scarf (Asexual)       — Pride event
            28110,  // Rainbow scarf (Transgender)   — Pride event
            28111,  // Rainbow scarf (Pansexual)     — Pride event
            28112,  // Rainbow scarf (Non-binary)    — Pride event
            28113,  // Rainbow scarf (Genderqueer)   — Pride event
            28114,  // Rainbow scarf (Lesbian)       — Pride event
            28115,  // Rainbow scarf (Gay)           — Pride event
            28116,  // Rainbow jumper (Pride)        — Pride event
            28118,  // Rainbow jumper (Bisexual)     — Pride event
            28119,  // Rainbow jumper (Asexual)      — Pride event
            28120,  // Rainbow jumper (Transgender)  — Pride event
            28121,  // Rainbow jumper (Pansexual)    — Pride event
            28122,  // Rainbow jumper (Non-binary)   — Pride event
            28123,  // Rainbow jumper (Genderqueer)  — Pride event
            28124,  // Rainbow jumper (Lesbian)      — Pride event
            28125,  // Rainbow jumper (Gay)          — Pride event
            28126,  // Poet's jacket                 — Pride event
            28128,  // Love crossbow                 — Pride event
            29489,  // Rainbow cape (Pride)          — Pride event
            29491,  // Rainbow cape (Bisexual)       — Pride event
            29493,  // Rainbow cape (Asexual)        — Pride event
            29495,  // Rainbow cape (Transgender)    — Pride event
            29497,  // Rainbow cape (Pansexual)      — Pride event
            29499,  // Rainbow cape (Non-binary)     — Pride event
            29501,  // Rainbow cape (Genderqueer)    — Pride event
            29503,  // Rainbow cape (Lesbian)        — Pride event
            29505,  // Rainbow cape (Gay)            — Pride event
            29507,  // Rainbow crown shirt (Pride)   — Pride event
            29509,  // Rainbow crown shirt (Bisexual)    — Pride event
            29510,  // Rainbow crown shirt (Asexual)     — Pride event
            29511,  // Rainbow crown shirt (Transgender) — Pride event
            29512,  // Rainbow crown shirt (Pansexual)   — Pride event
            29513,  // Rainbow crown shirt (Non-binary)  — Pride event
            29514,  // Rainbow crown shirt (Genderqueer) — Pride event
            29515,  // Rainbow crown shirt (Lesbian)     — Pride event
            29516,  // Rainbow crown shirt (Gay)         — Pride event
            // =================================================================
            // HALLOWEEN
            // =================================================================
            1419,   // Scythe                        — Halloween 2003
            6722,   // Zombie head                   — Halloween event
            9920,   // Jack lantern mask             — Halloween event
            9921,   // Skeleton boots                — Halloween event
            9922,   // Skeleton gloves               — Halloween event
            9923,   // Skeleton leggings             — Halloween event
            9924,   // Skeleton shirt                — Halloween event
            9925,   // Skeleton mask                 — Halloween event
            11847,  // Black h'ween mask             — Halloween event (untradeable variant)
            12845,  // Grim reaper hood              — Halloween event
            13283,  // Gravedigger mask              — Halloween event
            13284,  // Gravedigger top               — Halloween event
            13285,  // Gravedigger leggings          — Halloween event
            13286,  // Gravedigger boots             — Halloween event
            13287,  // Gravedigger gloves            — Halloween event
            13288,  // Anti-panties                  — Halloween event
            20773,  // Banshee mask                  — Halloween event
            20775,  // Banshee top                   — Halloween event
            20777,  // Banshee robe                  — Halloween event
            20779,  // Hunting knife                 — Halloween event
            21720,  // Jonas mask                    — Halloween event
            22684,  // Eek                           — Halloween event
            22689,  // Clown mask                    — Halloween event
            22692,  // Clown bow tie                 — Halloween event
            22695,  // Clown gown                    — Halloween event
            22698,  // Clown trousers                — Halloween event
            22701,  // Clown shoes                   — Halloween event
            24305,  // Spooky hood                   — Halloween event
            24307,  // Spooky robe                   — Halloween event
            24309,  // Spooky skirt                  — Halloween event
            24311,  // Spooky gloves                 — Halloween event
            24313,  // Spooky boots                  — Halloween event
            24315,  // Spookier hood                 — Halloween event
            24317,  // Spookier robe                 — Halloween event
            24319,  // Spookier skirt                — Halloween event
            24321,  // Spookier gloves               — Halloween event
            24323,  // Spookier boots                — Halloween event
            24325,  // Pumpkin lantern               — Halloween event
            24327,  // Skeleton lantern              — Halloween event
            24975,  // Headless head                 — Halloween event
            24977,  // Magical pumpkin               — Halloween event
            26254,  // Saucepan                      — Halloween event
            26256,  // Ugly halloween jumper (Orange) — Halloween event
            26258,  // Ugly halloween jumper (Black)  — Halloween event
            26260,  // Haunted wine bottle           — Halloween event
            27463,  // Treat cauldron (Style 1)      — Halloween event
            27465,  // Treat cauldron (Style 2)      — Halloween event
            27467,  // Treat cauldron (Style 3)      — Halloween event
            27469,  // Treat cauldron (Style 4)      — Halloween event
            27471,  // Treat cauldron (Style 5)      — Halloween event
            27473,  // Witch hat                     — Halloween event
            27475,  // Witch top                     — Halloween event
            27477,  // Witch robes                   — Halloween event
            27479,  // Witch boots                   — Halloween event
            27481,  // Witch cape                    — Halloween event
            27497,  // Halloween wig (Style 1)        — Halloween event 2022
            27499,  // Halloween wig (Style 2)        — Halloween event 2022
            27501,  // Halloween wig (Style 3)        — Halloween event 2022
            27503,  // Halloween wig (Style 4)        — Halloween event 2022
            27505,  // Halloween wig (Style 5)        — Halloween event 2022
            27507,  // Halloween wig (Style 6)        — Halloween event 2022
            28601,  // Cobweb cape                   — Halloween event
            28603,  // Spider hat (Common)           — Halloween event
            28605,  // Spider hat (Venenatis)        — Halloween event
            28607,  // Spider hat (Verzik)           — Halloween event
            28609,  // Spider hat (Sarachnis)        — Halloween event
            28611,  // Spider hat (Old)              — Halloween event
            30232,  // Scarecrow shirt               — Halloween event
            30234,  // Halloween scarecrow           — Halloween event
            31225,  // Spooky chair                  — Halloween event
            31227,  // Spooky pumpkin lantern        — Halloween event
            31229,  // Grim reaper top               — Halloween event
            31231,  // Grim reaper bottoms           — Halloween event
            31233,  // Grim reaper gloves            — Halloween event
            // Carved Pumpkin Head — 7 colours × 9 expressions (63 variants total):
            30237, 30239, 30241, 30242, 30243, 30244, 30245, 30246, 30247, // Beige
            30248, 30249, 30250, 30251, 30252, 30253, 30254, 30255, 30256, // White
            30257, 30258, 30259, 30260, 30261, 30262, 30263, 30264, 30265, // Yellow
            30266, 30267, 30268, 30269, 30270, 30271, 30272, 30273, 30274, // Orange
            30275, 30276, 30277, 30278, 30279, 30280, 30281, 30282, 30283, // Red
            30284, 30285, 30286, 30287, 30288, 30289, 30290, 30291, 30292, // Dark green
            30293, 30294, 30295, 30296, 30297, 30298, 30299, 30300, 30301, // Powder grey
            // EXCLUDED: Pumpkin (1959)                — tradeable; not reclaimable from Diango
            // EXCLUDED: Blue h'ween mask (1055)       — tradeable
            // EXCLUDED: Green h'ween mask (1053)      — tradeable
            // EXCLUDED: Red h'ween mask (1057)        — tradeable
            // EXCLUDED: Halloween mask set (13175)    — tradeable
            // =================================================================
            // CHRISTMAS & WINTER
            // =================================================================
            4079,   // Yo-yo                         — Christmas event
            6856,   // Bobble hat                    — Christmas event
            6857,   // Bobble scarf                  — Christmas event
            6858,   // Jester hat                    — Christmas event
            6859,   // Jester scarf                  — Christmas event
            6860,   // Tri-jester hat                — Christmas event
            6861,   // Tri-jester scarf              — Christmas event
            6862,   // Woolly hat                    — Christmas event
            6863,   // Woolly scarf                  — Christmas event
            6865,   // Blue marionette               — Christmas event
            6866,   // Green marionette              — Christmas event
            6867,   // Red marionette                — Christmas event
            10507,  // Reindeer hat                  — Christmas event
            10508,  // Wintumber tree                — Christmas event
            11862,  // Black partyhat                — Christmas cracker (untradeable variant)
            11863,  // Rainbow partyhat              — Christmas cracker (untradeable variant)
            12887,  // Santa mask                    — Christmas event costume (untradeable)
            12888,  // Santa jacket                  — Christmas event costume (untradeable)
            12889,  // Santa pantaloons              — Christmas event costume (untradeable)
            12890,  // Santa gloves                  — Christmas event costume (untradeable)
            12891,  // Santa boots                   — Christmas event costume (untradeable)
            12892,  // Antisanta mask                — Christmas event
            12893,  // Antisanta jacket              — Christmas event
            12894,  // Antisanta pantaloons          — Christmas event
            12895,  // Antisanta gloves              — Christmas event
            12896,  // Antisanta boots               — Christmas event
            13343,  // Black santa hat               — Christmas event (untradeable variant)
            13344,  // Inverted santa hat            — Christmas event
            20832,  // Snow globe                    — Christmas event
            20834,  // Sack of presents              — Christmas event
            20836,  // Giant present                 — Christmas event
            21847,  // Snow imp costume head         — Christmas event
            21849,  // Snow imp costume body         — Christmas event
            21851,  // Snow imp costume legs         — Christmas event
            21853,  // Snow imp costume tail         — Christmas event
            21855,  // Snow imp costume gloves       — Christmas event
            21857,  // Snow imp costume feet         — Christmas event
            21859,  // Wise old man's santa hat      — Christmas event
            22713,  // Star-face                     — Christmas event
            22715,  // Tree top                      — Christmas event
            22717,  // Tree skirt                    — Christmas event
            22719,  // Candy cane                    — Christmas event
            24428,  // Green gingerbread shield      — Christmas event
            24430,  // Red gingerbread shield        — Christmas event
            24431,  // Blue gingerbread shield       — Christmas event
            25314,  // Giant boulder                 — Christmas event
            25316,  // Goblin decorations            — Christmas event
            26310,  // Festive elf slippers          — Christmas event
            26312,  // Festive elf hat               — Christmas event
            26314,  // Snowman ring                  — Christmas event
            26316,  // Secret santa present (Red)    — Christmas event
            26318,  // Secret santa present (Blue)   — Christmas event
            26320,  // Secret santa present (Green)  — Christmas event
            26322,  // Secret santa present (Black)  — Christmas event
            26324,  // Secret santa present (Gold)   — Christmas event
            27564,  // Santa's list                  — Christmas event
            27566,  // Christmas jumper              — Christmas event
            27568,  // Snow goggles & hat            — Christmas event
            27570,  // Sack of coal                  — Christmas event
            27572,  // Nutcracker top (Festive)      — Christmas event
            27574,  // Nutcracker trousers (Festive) — Christmas event
            27576,  // Nutcracker hat (Festive)      — Christmas event
            27578,  // Nutcracker boots (Festive)    — Christmas event
            27580,  // Nutcracker staff (Festive)    — Christmas event
            27582,  // Nutcracker top (Sweet)        — Christmas event
            27583,  // Nutcracker trousers (Sweet)   — Christmas event
            27584,  // Nutcracker hat (Sweet)        — Christmas event
            27585,  // Nutcracker boots (Sweet)      — Christmas event
            27586,  // Nutcracker staff (Sweet)      — Christmas event
            27588,  // Festive games crown           — Christmas event
            28786,  // Icy jumper                    — Christmas event
            28788,  // Snowglobe helmet              — Christmas event
            30479,  // Present box hat (Festive)     — Christmas event
            30481,  // Present box hat (Simple)      — Christmas event
            30483,  // Present box hat (Icy)         — Christmas event
            30485,  // Present box hat (Anti-santa)  — Christmas event
            30487,  // Dog disguise                  — Christmas event
            30489,  // Festive scarf                 — Christmas event
            30491,  // Dog boots                     — Christmas event
            32928,  // Lovley jubbly bib             — Christmas event
            32930,  // Beer belly sweater            — Christmas event
            32932,  // Jad jumper                    — Christmas event
            32934,  // Christmas dinner              — Christmas event
            // EXCLUDED: Santa hat (1050)             — tradeable (original holiday rare); not flagged by either scan
            // EXCLUDED: Christmas cracker (962)      — not reclaimable from Diango
            // =================================================================
            // MISCELLANEOUS ONE-OFF EVENTS
            // =================================================================
            2520,   // Brown toy horsey              — Event reward
            2522,   // White toy horsey              — Event reward
            2524,   // Black toy horsey              — Event reward
            2526,   // Grey toy horsey               — Event reward
            4613,   // Spinning plate                — Event reward
            11705,  // Beach boxing gloves (Yellow)  — Summer event
            11706,  // Beach boxing gloves (Pink)    — Summer event
            11707,  // Cursed goblin hammer          — Goblin Invasion
            11708,  // Cursed goblin bow             — Goblin Invasion
            11709,  // Cursed goblin staff           — Goblin Invasion
            12600,  // Druidic wreath                — Event reward
            12727,  // Goblin paint cannon           — Event reward
            13188,  // Diango's claws                — Event reward
            13203,  // Mask of balance               — Event reward
            13215,  // Tiger toy                     — Event reward
            13216,  // Lion toy                      — Event reward
            13217,  // Snow leopard toy              — Event reward
            13218,  // Amur leopard toy              — Event reward
            13328,  // Green banner                  — Event reward
            13679,  // Cabbage cape                  — Event reward
            13681,  // Cruciferous codex             — Event reward
            19699,  // Hornwood helm                 — Event reward
            21354,  // Hand fan                      — Event reward
            21695,  // Runefest shield               — Runefest event
            22355,  // Holy handegg                  — Event reward
            22358,  // Peaceful handegg              — Event reward
            22361,  // Chaotic handegg               — Event reward
            22364,  // Oculus orb                    — Event reward
            25840,  // Banana hat                    — Holiday/event item; reclaimable
            27645   // Mystic cards                  — Holiday/event item; reclaimable
            // EXCLUDED: Disk of returning (981)      — tradeable
            // EXCLUDED: Half full wine jug (1989)    — tradeable; not reclaimable from Diango
            // EXCLUDED: Clue hunter set (19689-19697)— not easily obtainable
            // EXCLUDED: Corrupted armour (20838-20846)— not reclaimable from Diango
            // EXCLUDED: Ornate set (23091-23101)     — not reclaimable from Diango
            // EXCLUDED: Ruin set (27428-27438)       — in POH_MAGIC_WARDROBE_ITEMS
            // EXCLUDED: Party hats (1038/1040/1042/1044/1046/1048) — tradeable
            // EXCLUDED: Partyhat set (13173)         — tradeable
        ))
    );
    /**
     * Items storable in the Magic Wardrobe of the Costume Room in a player-owned house.
     *
     * <p>Includes magic/skilling outfits, Graceful (all recolours — inventory and worn IDs),
     * Mystic/Infinity/Lunar/Virtus sets, pirate outfits, fishing gear, forestry tools,
     * and miscellaneous wearables.</p>
     *
     * <p>Note: each Graceful piece has two separate item IDs (inventory model vs. worn model);
     * both are included here.</p>
     *
     * All IDs player-confirmed June 2026.
     */
    public static final Set<Integer> POH_MAGIC_WARDROBE_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // -----------------------------------------------------------------
            // AMY'S SAW / IMCANDO HAMMER (skilling tools)
            // -----------------------------------------------------------------
            24880,  // Amy's saw
            29774,  // Amy's saw (off-hand)
            25644,  // Imcando hammer (repaired)
            29775,  // Imcando hammer (off-hand)
            // -----------------------------------------------------------------
            // ANCESTRAL ROBES (standard + twisted)
            // -----------------------------------------------------------------
            21018,  // Ancestral hat
            21021,  // Ancestral robe top
            21024,  // Ancestral robe bottom
            21049,  // Ancestral robes set
            24664,  // Twisted ancestral hat
            24666,  // Twisted ancestral robe top
            24668,  // Twisted ancestral robe bottom
            // -----------------------------------------------------------------
            // ANCIENT CEREMONIAL ROBES
            // -----------------------------------------------------------------
            26221,  // Ancient ceremonial top
            26223,  // Ancient ceremonial legs
            26225,  // Ancient ceremonial mask
            26227,  // Ancient ceremonial gloves
            26229,  // Ancient ceremonial boots
            // -----------------------------------------------------------------
            // BLOODBARK ARMOUR
            // -----------------------------------------------------------------
            25404,  // Bloodbark body
            25407,  // Bloodbark gauntlets
            25410,  // Bloodbark boots
            25413,  // Bloodbark helm
            25416,  // Bloodbark legs
            31163,  // Bloodbark armour set
            // -----------------------------------------------------------------
            // BOUNTY HUNTER HATS
            // -----------------------------------------------------------------
            24338,  // Bounty hunter hat (tier 1)
            24340,  // Bounty hunter hat (tier 2)
            24342,  // Bounty hunter hat (tier 3)
            24344,  // Bounty hunter hat (tier 4)
            24346,  // Bounty hunter hat (tier 5)
            24348,  // Bounty hunter hat (tier 6)
            // -----------------------------------------------------------------
            // BRUMA TORCH (Wintertodt)
            // -----------------------------------------------------------------
            20720,  // Bruma torch
            29777,  // Bruma torch (off-hand)
            // -----------------------------------------------------------------
            // CARPENTER'S OUTFIT (Construction training)
            // -----------------------------------------------------------------
            24872,  // Carpenter's helmet
            24874,  // Carpenter's shirt
            24876,  // Carpenter's trousers
            24878,  // Carpenter's boots
            // -----------------------------------------------------------------
            // COW SLIPPERS
            // -----------------------------------------------------------------
            33093,  // Cow slippers (variant 1)
            33096,  // Cow slippers (variant 2)
            33097,  // Cow slippers (variant 3)
            33098,  // Cow slippers (variant 4)
            // -----------------------------------------------------------------
            // CRYSTAL GLIDER'S OUTFIT (Sailing)
            // -----------------------------------------------------------------
            31760,  // Crystal glider's hat
            31762,  // Crystal glider's jacket
            31764,  // Crystal glider's trousers
            31766,  // Crystal glider's shoes
            // -----------------------------------------------------------------
            // DAGON'HAI ROBES
            // -----------------------------------------------------------------
            24288,  // Dagon'hai hat
            24291,  // Dagon'hai robe top
            24294,  // Dagon'hai robe bottom
            24333,  // Dagon'hai robes set
            // -----------------------------------------------------------------
            // DARK FLIPPERS
            // -----------------------------------------------------------------
            25557,  // Dark flippers
            // -----------------------------------------------------------------
            // DARK SQUALL ROBES
            // -----------------------------------------------------------------
            29566,  // Dark squall hood
            29568,  // Dark squall robe top
            29570,  // Dark squall robe bottom
            // -----------------------------------------------------------------
            // ELDER CHAOS ROBES (base + ornate recolour)
            // -----------------------------------------------------------------
            20517,  // Elder chaos top
            20520,  // Elder chaos robe
            20595,  // Elder chaos hood
            27115,  // Elder chaos top (or)
            27117,  // Elder chaos robe (or)
            27119,  // Elder chaos hood (or)
            // -----------------------------------------------------------------
            // EMISSARY ROBES (Zamorak Sanctum)
            // -----------------------------------------------------------------
            29868,  // Emissary hood
            29870,  // Emissary robe top
            29872,  // Emissary robe bottom
            29874,  // Emissary sandals
            // -----------------------------------------------------------------
            // EVIL CHICKEN OUTFIT (random event)
            // -----------------------------------------------------------------
            20433,  // Evil chicken feet
            20436,  // Evil chicken wings
            20439,  // Evil chicken head
            20442,  // Evil chicken legs
            // -----------------------------------------------------------------
            // FISHING GEAR (fish sack, golden tench, pearl rods)
            // -----------------------------------------------------------------
            22838,  // Fish sack
            22840,  // Golden tench
            22842,  // Pearl barbarian rod
            22844,  // Pearl fly fishing rod
            22846,  // Pearl fishing rod
            23122,  // Oily pearl fishing rod
            25585,  // Fish sack barrel (closed)
            25587,  // Fish sack barrel (open)
            // -----------------------------------------------------------------
            // PIRATE FLAGS
            // -----------------------------------------------------------------
            8966,   // Cutthroat flag
            8967,   // Gilded smile flag
            8968,   // Bronze fist flag
            8969,   // Lucky shot flag
            8970,   // Treasure flag
            8971,   // Phasmatys flag
            // -----------------------------------------------------------------
            // FLETCHING KNIFE
            // -----------------------------------------------------------------
            31043,  // Fletching knife
            // -----------------------------------------------------------------
            // FORESTRY & WOODCUTTING TOOLS
            // -----------------------------------------------------------------
            28136,  // Forestry kit
            28140,  // Log basket (closed)
            28142,  // Log basket (open)
            28143,  // Forestry basket (closed)
            28145,  // Forestry basket (open)
            // -----------------------------------------------------------------
            // GHOSTLY ROBES (Spirit of Summer quest)
            // -----------------------------------------------------------------
            6106,   // Ghostly boots
            6107,   // Ghostly robe (top)
            6108,   // Ghostly robe (bottom)
            6109,   // Ghostly hood
            6110,   // Ghostly gloves
            6111,   // Ghostly cloak
            // -----------------------------------------------------------------
            // GRACEFUL OUTFIT — all recolours, all 6 pieces
            // Inventory model IDs only (worn model IDs excluded — worn items cannot appear in the bank).
            // -----------------------------------------------------------------
            // Base (white / default)
            11850,  // Graceful hood (inventory)
            11852,  // Graceful cape (inventory)
            11854,  // Graceful top (inventory)
            11856,  // Graceful legs (inventory)
            11858,  // Graceful gloves (inventory)
            11860,  // Graceful boots (inventory)
            // Arceuus
            13579,  // Graceful hood (Arceuus, inventory)
            13581,  // Graceful cape (Arceuus, inventory)
            13583,  // Graceful top (Arceuus, inventory)
            13585,  // Graceful legs (Arceuus, inventory)
            13587,  // Graceful gloves (Arceuus, inventory)
            13589,  // Graceful boots (Arceuus, inventory)
            // Piscarilius
            13591,  // Graceful hood (Piscarilius, inventory)
            13593,  // Graceful cape (Piscarilius, inventory)
            13595,  // Graceful top (Piscarilius, inventory)
            13597,  // Graceful legs (Piscarilius, inventory)
            13599,  // Graceful gloves (Piscarilius, inventory)
            13601,  // Graceful boots (Piscarilius, inventory)
            // Lovakengj
            13603,  // Graceful hood (Lovakengj, inventory)
            13605,  // Graceful cape (Lovakengj, inventory)
            13607,  // Graceful top (Lovakengj, inventory)
            13609,  // Graceful legs (Lovakengj, inventory)
            13611,  // Graceful gloves (Lovakengj, inventory)
            13613,  // Graceful boots (Lovakengj, inventory)
            // Shayzien
            13615,  // Graceful hood (Shayzien, inventory)
            13617,  // Graceful cape (Shayzien, inventory)
            13619,  // Graceful top (Shayzien, inventory)
            13621,  // Graceful legs (Shayzien, inventory)
            13623,  // Graceful gloves (Shayzien, inventory)
            13625,  // Graceful boots (Shayzien, inventory)
            // Hosidius
            13627,  // Graceful hood (Hosidius, inventory)
            13629,  // Graceful cape (Hosidius, inventory)
            13631,  // Graceful top (Hosidius, inventory)
            13633,  // Graceful legs (Hosidius, inventory)
            13635,  // Graceful gloves (Hosidius, inventory)
            13637,  // Graceful boots (Hosidius, inventory)
            // Kourend
            13667,  // Graceful hood (Kourend, inventory)
            13669,  // Graceful cape (Kourend, inventory)
            13671,  // Graceful top (Kourend, inventory)
            13673,  // Graceful legs (Kourend, inventory)
            13675,  // Graceful gloves (Kourend, inventory)
            13677,  // Graceful boots (Kourend, inventory)
            // Agility Arena
            21061,  // Graceful hood (Agility Arena, inventory)
            21064,  // Graceful cape (Agility Arena, inventory)
            21067,  // Graceful top (Agility Arena, inventory)
            21070,  // Graceful legs (Agility Arena, inventory)
            21073,  // Graceful gloves (Agility Arena, inventory)
            21076,  // Graceful boots (Agility Arena, inventory)
            // Hallowed
            24743,  // Graceful hood (Hallowed, inventory)
            24746,  // Graceful cape (Hallowed, inventory)
            24749,  // Graceful top (Hallowed, inventory)
            24752,  // Graceful legs (Hallowed, inventory)
            24755,  // Graceful gloves (Hallowed, inventory)
            24758,  // Graceful boots (Hallowed, inventory)
            // Trailblazer
            25069,  // Graceful hood (Trailblazer, inventory)
            25072,  // Graceful cape (Trailblazer, inventory)
            25075,  // Graceful top (Trailblazer, inventory)
            25078,  // Graceful legs (Trailblazer, inventory)
            25081,  // Graceful gloves (Trailblazer, inventory)
            25084,  // Graceful boots (Trailblazer, inventory)
            // Adventurer
            27444,  // Graceful hood (Adventurer, inventory)
            27447,  // Graceful cape (Adventurer, inventory)
            27450,  // Graceful top (Adventurer, inventory)
            27453,  // Graceful legs (Adventurer, inventory)
            27456,  // Graceful gloves (Adventurer, inventory)
            27459,  // Graceful boots (Adventurer, inventory)
            // Varlamore
            30045,  // Graceful hood (Varlamore, inventory)
            30048,  // Graceful cape (Varlamore, inventory)
            30051,  // Graceful top (Varlamore, inventory)
            30054,  // Graceful legs (Varlamore, inventory)
            30057,  // Graceful gloves (Varlamore, inventory)
            30060,  // Graceful boots (Varlamore, inventory)
            // -----------------------------------------------------------------
            // GREENMAN MASKS (Vale of Totems)
            // -----------------------------------------------------------------
            31034,  // Greenman mask (default)
            31037,  // Greenman mask (normal)
            31038,  // Greenman mask (oak)
            31039,  // Greenman mask (willow)
            31040,  // Greenman mask (maple)
            31041,  // Greenman mask (yew)
            31042,  // Greenman mask (magic)
            // -----------------------------------------------------------------
            // INFINITY ROBES (Base + Dark + Light recolours)
            // -----------------------------------------------------------------
            6916,   // Infinity top (base)
            6918,   // Infinity hat (base)
            6920,   // Infinity boots (base)
            6922,   // Infinity gloves (base)
            6924,   // Infinity bottoms (base)
            12419,  // Infinity hat (light)
            12420,  // Infinity top (light)
            12421,  // Infinity bottoms (light)
            12457,  // Infinity hat (dark)
            12458,  // Infinity top (dark)
            12459,  // Infinity bottoms (dark)
            // -----------------------------------------------------------------
            // LUNAR / MOONCLAN EQUIPMENT (Lunar Diplomacy)
            // -----------------------------------------------------------------
            9068,   // Moonclan helm
            9069,   // Moonclan hat
            9070,   // Moonclan armour
            9071,   // Moonclan skirt
            9072,   // Moonclan gloves
            9073,   // Moonclan boots
            9074,   // Moonclan cape
            9096,   // Lunar helm
            9097,   // Lunar torso
            9098,   // Lunar legs
            9099,   // Lunar gloves
            9100,   // Lunar boots
            9101,   // Lunar cape
            9102,   // Lunar amulet
            9104,   // Lunar ring
            // -----------------------------------------------------------------
            // MASK OF REBIRTH (Tombs of Amascut)
            // -----------------------------------------------------------------
            27370,  // Mask of rebirth
            // -----------------------------------------------------------------
            // MUDSKIPPER HAT & FLIPPERS (Mogres)
            // -----------------------------------------------------------------
            6665,   // Mudskipper hat
            6666,   // Flippers
            // -----------------------------------------------------------------
            // MYSTIC ROBES (Blue / Dark / Dusk / Light / Or)
            // -----------------------------------------------------------------
            23113,  // Mystic robes set (blue)
            4089,   // Mystic hat (blue)
            4091,   // Mystic robe top (blue)
            4093,   // Mystic robe bottom (blue)
            4095,   // Mystic gloves (blue)
            4097,   // Mystic boots (blue)
            23116,  // Mystic robes set (dark)
            4099,   // Mystic hat (dark)
            4101,   // Mystic robe top (dark)
            4103,   // Mystic robe bottom (dark)
            4105,   // Mystic gloves (dark)
            4107,   // Mystic boots (dark)
            23110,  // Mystic robes set (light)
            4109,   // Mystic hat (light)
            4111,   // Mystic robe top (light)
            4113,   // Mystic robe bottom (light)
            4115,   // Mystic gloves (light)
            4117,   // Mystic boots (light)
            23119,  // Mystic robes set (dusk)
            23047,  // Mystic hat (dusk)
            23050,  // Mystic robe top (dusk)
            23053,  // Mystic robe bottom (dusk)
            23056,  // Mystic gloves (dusk)
            23059,  // Mystic boots (dusk)
            26531,  // Mystic hat (or)
            26533,  // Mystic robe top (or)
            26535,  // Mystic robe bottom (or)
            26537,  // Mystic gloves (or)
            26539,  // Mystic boots (or)
            // -----------------------------------------------------------------
            // ORANGE HAT (Tlati Rainforest)
            // -----------------------------------------------------------------
            31117,  // Orange (hat)
            // -----------------------------------------------------------------
            // PHEASANT OUTFIT (Pheasant Control Forestry Event)
            // -----------------------------------------------------------------
            28616,  // Pheasant cape
            28618,  // Pheasant boots
            28620,  // Pheasant hat
            28622,  // Pheasant legs
            // -----------------------------------------------------------------
            // PIRATE OUTFITS (Trouble Brewing)
            // -----------------------------------------------------------------
            8952,   // Naval shirt (blue)
            8953,   // Naval shirt (green)
            8954,   // Naval shirt (red)
            8955,   // Naval shirt (brown)
            8956,   // Naval shirt (black)
            8957,   // Naval shirt (purple)
            8958,   // Naval shirt (grey)
            8959,   // Tricorn hat (blue)
            8960,   // Tricorn hat (green)
            8961,   // Tricorn hat (red)
            8962,   // Tricorn hat (brown)
            8963,   // Tricorn hat (black)
            8964,   // Tricorn hat (purple)
            8965,   // Tricorn hat (grey)
            8991,   // Navy slacks (blue)
            8992,   // Navy slacks (green)
            8993,   // Navy slacks (red)
            8994,   // Navy slacks (brown)
            8995,   // Navy slacks (black)
            8996,   // Navy slacks (purple)
            8997,   // Navy slacks (grey)
            // -----------------------------------------------------------------
            // PYROMANCER OUTFIT (Wintertodt)
            // -----------------------------------------------------------------
            20704,  // Pyromancer garb
            20706,  // Pyromancer robe
            20708,  // Pyromancer hood
            20710,  // Pyromancer boots
            // -----------------------------------------------------------------
            // RAIMENTS OF THE EYE (Guardians of the Rift)
            // -----------------------------------------------------------------
            26850,  // Hat of the eye (regular)
            26852,  // Robe top of the eye (regular)
            26854,  // Robe bottom of the eye (regular)
            26856,  // Boots of the eye (regular)
            26858,  // Hat of the eye (red)
            26860,  // Robe top of the eye (red)
            26862,  // Robe bottom of the eye (red)
            26864,  // Hat of the eye (green)
            26866,  // Robe top of the eye (green)
            26868,  // Robe bottom of the eye (green)
            26870,  // Hat of the eye (blue)
            26872,  // Robe top of the eye (blue)
            26874,  // Robe bottom of the eye (blue)
            // -----------------------------------------------------------------
            // ROBES OF RUIN (Crack the Clue III)
            // -----------------------------------------------------------------
            27428,  // Hood of ruin
            27430,  // Robe top of ruin
            27432,  // Robe bottom of ruin
            27434,  // Gloves of ruin
            27436,  // Socks of ruin
            27438,  // Cloak of ruin
            // -----------------------------------------------------------------
            // SKELETAL ARMOUR (Dagannoth Lair)
            // -----------------------------------------------------------------
            31154,  // Skeletal armour set
            6137,   // Skeletal helm
            6139,   // Skeletal top
            6141,   // Skeletal bottoms
            6147,   // Skeletal boots
            6153,   // Skeletal gloves
            // -----------------------------------------------------------------
            // SMITHS OUTFIT (Giants' Foundry)
            // -----------------------------------------------------------------
            27023,  // Smiths tunic
            27025,  // Smiths trousers
            27027,  // Smiths boots
            27029,  // Smiths gloves
            27031,  // Smiths gloves (i)
            // -----------------------------------------------------------------
            // SPLITBARK ARMOUR (Shades of Mort'ton)
            // -----------------------------------------------------------------
            3385,   // Splitbark helm
            3387,   // Splitbark body
            3389,   // Splitbark legs
            3391,   // Splitbark gauntlets
            3393,   // Splitbark boots
            // -----------------------------------------------------------------
            // STORM / SWAMP CRUISER OUTFITS (Sailing)
            // -----------------------------------------------------------------
            31736,  // Storm cruiser hat
            31738,  // Storm cruiser coat
            31740,  // Storm cruiser trousers
            31742,  // Storm cruiser shoes
            31748,  // Swamp cruiser hat
            31750,  // Swamp cruiser jacket
            31752,  // Swamp cruiser trousers
            31754,  // Swamp cruiser shoes
            // -----------------------------------------------------------------
            // SWAMPBARK ARMOUR (Shades of Mort'ton)
            // -----------------------------------------------------------------
            31160,  // Swampbark armour set
            25389,  // Swampbark body
            25392,  // Swampbark gauntlets
            25395,  // Swampbark boots
            25398,  // Swampbark helm
            25401,  // Swampbark legs
            // -----------------------------------------------------------------
            // TRIBAL TOPS + VILLAGER OUTFITS (Tai Bwo Wannai)
            // -----------------------------------------------------------------
            6341,   // Tribal top (brown)
            6343,   // Villager robe (brown)
            6345,   // Villager hat (brown)
            6347,   // Villager armband (brown)
            6349,   // Villager sandals (brown)
            6351,   // Tribal top (blue)
            6353,   // Villager robe (blue)
            6355,   // Villager hat (blue)
            6359,   // Villager armband (blue)
            6357,   // Villager sandals (blue)
            6361,   // Tribal top (yellow)
            6363,   // Villager robe (yellow)
            6365,   // Villager hat (yellow)
            6369,   // Villager armband (yellow)
            6367,   // Villager sandals (yellow)
            6371,   // Tribal top (pink)
            6373,   // Villager robe (pink)
            6375,   // Villager hat (pink)
            6379,   // Villager armband (pink)
            6377,   // Villager sandals (pink)
            // -----------------------------------------------------------------
            // VIRTUS ROBES + ECHO VIRTUS ROBES
            // (Leviathan, Whisperer, Vardorvis & Duke Sucellus)
            // -----------------------------------------------------------------
            31148,  // Virtus armour set
            26241,  // Virtus mask
            26243,  // Virtus robe top
            26245,  // Virtus robe bottom
            30437,  // Echo virtus mask
            30439,  // Echo virtus robe top
            30441,  // Echo virtus robe bottom
            // -----------------------------------------------------------------
            // WARM GLOVES (Wintertodt)
            // -----------------------------------------------------------------
            20712,  // Warm gloves
            // -----------------------------------------------------------------
            // ZEALOT'S ROBES (Shades of Mort'ton)
            // -----------------------------------------------------------------
            25434,  // Zealot's robe top
            25436,  // Zealot's robe bottom
            25438,  // Zealot's helm
            25440   // Zealot's boots
        ))
    );
    /**
     * Items storable in the Treasure Chest of the Costume Room in a player-owned house.
     * Includes clue scroll reward items across all tiers: Beginner, Easy, Medium, Hard,
     * Elite, and Master.
     *
     * <p>For sets where plateskirt and platelegs share a single storage slot, both IDs
     * are included in this set — the plugin flags both; the player stores whichever they
     * prefer and keeps/drops the other.</p>
     *
     * All IDs sourced from POH Storable Items 7-4-26.txt (source of truth).
     */
    public static final Set<Integer> POH_TREASURE_CHEST_ITEMS = Collections.unmodifiableSet(
        new HashSet<>(Arrays.asList(
            // ── BEGINNER ─────────────────────────────────────────────────────────────────────
            // Sandwich lady outfit
            23312,   // Sandwich lady hat
            23315,   // Sandwich lady top
            23318,   // Sandwich lady bottom
            // Monk's robe (t)
            23303,   // Monk's robe top (t)
            23306,   // Monk's robe (t)
            // Rune scimitars (god)
            23330,   // Rune scimitar (guthix)
            23332,   // Rune scimitar (saradomin)
            23334,   // Rune scimitar (zamorak)
            // Slippers
            23285,   // Mole slippers
            23288,   // Frog slippers
            23291,   // Bear feet
            23294,   // Demon feet
            // Misc
            23309,   // Amulet of defence (t)
            23297,   // Jester cape
            23300,   // Shoulder parrot
            // ── EASY ────────────────────────────────────────────────────────────────────────
            // Ancient vestments
            12203,   // Ancient mitre
            12193,   // Ancient robe top
            12195,   // Ancient robe legs
            12197,   // Ancient cloak
            12201,   // Ancient stole
            12199,   // Ancient crozier
            // Armadyl vestments
            12259,   // Armadyl mitre
            12253,   // Armadyl robe top
            12255,   // Armadyl robe legs
            12261,   // Armadyl cloak
            12257,   // Armadyl stole
            12263,   // Armadyl crozier
            // Bandos vestments
            12271,   // Bandos mitre
            12265,   // Bandos robe top
            12267,   // Bandos robe legs
            12273,   // Bandos cloak
            12269,   // Bandos stole
            12275,   // Bandos crozier
            // Guthix vestments
            10454,   // Guthix mitre
            10462,   // Guthix robe top
            10466,   // Guthix robe legs
            10448,   // Guthix cloak
            10472,   // Guthix stole
            10442,   // Guthix crozier
            // Saradomin vestments
            10452,   // Saradomin mitre
            10458,   // Saradomin robe top
            10464,   // Saradomin robe legs
            10446,   // Saradomin cloak
            10470,   // Saradomin stole
            10440,   // Saradomin crozier
            // Zamorak vestments
            10456,   // Zamorak mitre
            10460,   // Zamorak robe top
            10468,   // Zamorak robe legs
            10450,   // Zamorak cloak
            10474,   // Zamorak stole
            10444,   // Zamorak crozier
            // Blessings
            20235,   // Ancient blessing
            20220,   // Holy blessing
            20229,   // Honourable blessing
            20226,   // Peaceful blessing
            20223,   // Unholy blessing
            20232,   // War blessing
            // Black armour (g)
            2595,   // Black full helm (g)
            2591,   // Black platebody (g)
            // Black plateskirt/legs (g) (one slot — any one):
            3473,   // Black plateskirt (g)
            2593,   // Black platelegs (g)
            2597,   // Black kiteshield (g)
            // Black armour (t)
            2587,   // Black full helm (t)
            2583,   // Black platebody (t)
            // Black plateskirt/legs (t) (one slot — any one):
            3472,   // Black plateskirt (t)
            2585,   // Black platelegs (t)
            2589,   // Black kiteshield (t)
            // Black heraldic helms
            10306,   // Black helm (h1)
            10308,   // Black helm (h2)
            10310,   // Black helm (h3)
            10312,   // Black helm (h4)
            10314,   // Black helm (h5)
            // Black heraldic platebodies
            23366,   // Black platebody (h1)
            23369,   // Black platebody (h2)
            23372,   // Black platebody (h3)
            23375,   // Black platebody (h4)
            23378,   // Black platebody (h5)
            // Black heraldic shields
            7332,   // Black shield (h1)
            7338,   // Black shield (h2)
            7344,   // Black shield (h3)
            7350,   // Black shield (h4)
            7356,   // Black shield (h5)
            // Black wizard robes
            12453,   // Black wizard hat (g)
            12449,   // Black wizard robe (g)
            12445,   // Black skirt (g)
            12455,   // Black wizard hat (t)
            12451,   // Black wizard robe (t)
            12447,   // Black skirt (t)
            // Blue wizard robes
            7394,   // Blue wizard hat (g)
            7390,   // Blue wizard robe (g)
            7386,   // Blue skirt (g)
            7396,   // Blue wizard hat (t)
            7392,   // Blue wizard robe (t)
            7388,   // Blue skirt (t)
            // Bronze armour (g)
            12211,   // Bronze full helm (g)
            12205,   // Bronze platebody (g)
            // Bronze plateskirt/legs (g) (one slot — any one):
            12209,   // Bronze plateskirt (g)
            12207,   // Bronze platelegs (g)
            12213,   // Bronze kiteshield (g)
            // Bronze armour (t)
            12221,   // Bronze full helm (t)
            12215,   // Bronze platebody (t)
            // Bronze plateskirt/legs (t) (one slot — any one):
            12219,   // Bronze plateskirt (t)
            12217,   // Bronze platelegs (t)
            12223,   // Bronze kiteshield (t)
            // Iron armour (g)
            12241,   // Iron full helm (g)
            12235,   // Iron platebody (g)
            // Iron plateskirt/legs (g) (one slot — any one):
            12239,   // Iron plateskirt (g)
            12237,   // Iron platelegs (g)
            12243,   // Iron kiteshield (g)
            // Iron armour (t)
            12231,   // Iron full helm (t)
            12225,   // Iron platebody (t)
            // Iron plateskirt/legs (t) (one slot — any one):
            12229,   // Iron plateskirt (t)
            12227,   // Iron platelegs (t)
            12233,   // Iron kiteshield (t)
            // Steel armour (g)
            20178,   // Steel full helm (g)
            20169,   // Steel platebody (g)
            // Steel plateskirt/legs (g) (one slot — any one):
            20175,   // Steel plateskirt (g)
            20172,   // Steel platelegs (g)
            20181,   // Steel kiteshield (g)
            // Steel armour (t)
            20193,   // Steel full helm (t)
            20184,   // Steel platebody (t)
            // Steel plateskirt/legs (t) (one slot — any one):
            20190,   // Steel plateskirt (t)
            20187,   // Steel platelegs (t)
            20196,   // Steel kiteshield (t)
            // Elegant clothes
            10408,   // Blue elegant shirt
            10410,   // Blue elegant legs
            10428,   // Blue elegant blouse
            10430,   // Blue elegant skirt
            10412,   // Green elegant shirt
            10414,   // Green elegant legs
            10432,   // Green elegant blouse
            10434,   // Green elegant skirt
            10404,   // Red elegant shirt
            10406,   // Red elegant legs
            10424,   // Red elegant blouse
            10426,   // Red elegant skirt
            // Bob's shirts
            10316,   // Bob's red shirt
            10318,   // Bob's blue shirt
            10320,   // Bob's green shirt
            10322,   // Bob's black shirt
            10324,   // Bob's purple shirt
            // Studded
            7362,   // Studded body (g)
            7366,   // Studded chaps (g)
            7364,   // Studded body (t)
            7368,   // Studded chaps (t)
            // Leather (g)
            23381,   // Leather body (g)
            23384,   // Leather chaps (g)
            // Monk's robe (g)
            20199,   // Monk's robe top (g)
            20202,   // Monk's robe (g)
            // Berets and masks
            12245,   // Beanie
            11282,   // Beret mask
            2635,   // Black beret
            2633,   // Blue beret
            12247,   // Red beret
            2637,   // White beret
            12251,   // Goblin mask
            2631,   // Highwayman mask
            12249,   // Imp mask
            // Misc
            10392,   // A powdered wig
            10366,   // Amulet of magic (t)
            23354,   // Amulet of power (t)
            12375,   // Black cane
            12297,   // Black pickaxe
            23351,   // Cape of skulls
            10394,   // Flared trousers
            20208,   // Golden apron
            20205,   // Golden chef's hat
            23360,   // Ham joint
            20164,   // Large spade
            10396,   // Pantaloons
            23357,   // Rain bow
            10398,   // Sleeping cap
            23363,   // Staff of bob the cat
            10280,   // Willow comp bow
            20166,   // Wooden shield (g)
            // ── MEDIUM ──────────────────────────────────────────────────────────────────────
            // Adamant armour (g)
            2613,   // Adamant full helm (g)
            2607,   // Adamant platebody (g)
            // Adamant plateskirt/legs (g) (one slot — any one):
            3475,   // Adamant plateskirt (g)
            2609,   // Adamant platelegs (g)
            2611,   // Adamant kiteshield (g)
            // Adamant armour (t)
            2605,   // Adamant full helm (t)
            2599,   // Adamant platebody (t)
            // Adamant plateskirt/legs (t) (one slot — any one):
            3474,   // Adamant plateskirt (t)
            2601,   // Adamant platelegs (t)
            2603,   // Adamant kiteshield (t)
            // Adamant heraldic helms
            10296,   // Adamant helm (h1)
            10298,   // Adamant helm (h2)
            10300,   // Adamant helm (h3)
            10302,   // Adamant helm (h4)
            10304,   // Adamant helm (h5)
            // Adamant heraldic platebodies
            23392,   // Adamant platebody (h1)
            23395,   // Adamant platebody (h2)
            23398,   // Adamant platebody (h3)
            23401,   // Adamant platebody (h4)
            23404,   // Adamant platebody (h5)
            // Adamant heraldic shields
            7334,   // Adamant shield (h1)
            7340,   // Adamant shield (h2)
            7346,   // Adamant shield (h3)
            7352,   // Adamant shield (h4)
            7358,   // Adamant shield (h5)
            // Mithril armour (g)
            12283,   // Mithril full helm (g)
            12277,   // Mithril platebody (g)
            // Mithril plateskirt/legs (g) (one slot — any one):
            12285,   // Mithril plateskirt (g)
            12279,   // Mithril platelegs (g)
            12281,   // Mithril kiteshield (g)
            // Mithril armour (t)
            12293,   // Mithril full helm (t)
            12287,   // Mithril platebody (t)
            // Mithril plateskirt/legs (t) (one slot — any one):
            12295,   // Mithril plateskirt (t)
            12289,   // Mithril platelegs (t)
            12291,   // Mithril kiteshield (t)
            // Boaters
            7319,   // Red boater
            7321,   // Orange boater
            7323,   // Green boater
            7325,   // Blue boater
            7327,   // Black boater
            12309,   // Pink boater
            12311,   // Purple boater
            12313,   // White boater
            // Headbands
            2645,   // Red headband
            2647,   // Black headband
            2649,   // Brown headband
            12299,   // White headband
            12301,   // Blue headband
            12303,   // Gold headband
            12305,   // Pink headband
            12307,   // Green headband
            // Kourend banners
            20251,   // Arceuus banner
            20254,   // Hosidius banner
            20257,   // Lovakengj banner
            20260,   // Piscarilius banner
            20263,   // Shayzien banner
            // Elegant clothes
            10400,   // Black elegant shirt
            10402,   // Black elegant legs
            10420,   // White elegant blouse
            10422,   // White elegant skirt
            12347,   // Gold elegant shirt
            12349,   // Gold elegant legs
            12343,   // Gold elegant blouse
            12345,   // Gold elegant skirt
            12315,   // Pink elegant shirt
            12317,   // Pink elegant legs
            12339,   // Pink elegant blouse
            12341,   // Pink elegant skirt
            10416,   // Purple elegant shirt
            10418,   // Purple elegant legs
            10436,   // Purple elegant blouse
            10438,   // Purple elegant skirt
            // Green d'hide
            7370,   // Green d'hide body (g)
            7378,   // Green d'hide chaps (g)
            7372,   // Green d'hide body (t)
            7380,   // Green d'hide chaps (t)
            // Masks and hats
            20266,   // Black unicorn mask
            12361,   // Cat mask
            12428,   // Penguin mask
            20269,   // White unicorn mask
            23407,   // Wolf mask
            12359,   // Leprechaun hat
            20246,   // Black leprechaun hat
            // Misc
            12377,   // Adamant cane
            10364,   // Strength amulet (t)
            20272,   // Cabbage round shield
            23413,   // Climbing boots (g)
            20249,   // Clueless scroll
            10282,   // Yew comp bow
            12598,   // Holy sandals
            2577,   // Ranger boots
            23389,   // Spiked manacles
            2579,   // Wizard boots
            23410,   // Wolf cloak
            12319,   // Crier hat
            20240,   // Crier coat
            20243,   // Crier bell
            // ── HARD ────────────────────────────────────────────────────────────────────────
            // 3rd age
            10344,   // 3rd age amulet
            10350,   // 3rd age full helmet
            10348,   // 3rd age platebody
            10346,   // 3rd age platelegs
            10352,   // 3rd age kiteshield
            10342,   // 3rd age mage hat
            10338,   // 3rd age robe top
            10340,   // 3rd age robe
            10334,   // 3rd age range coif
            10330,   // 3rd age range top
            10332,   // 3rd age range legs
            10336,   // 3rd age vambraces
            // Amulet of glory (t) (one slot — any one):
            10362,   // Amulet of glory (t) (uncharged)
            10354,   // Amulet of glory (t1)
            10356,   // Amulet of glory (t2)
            10358,   // Amulet of glory (t3)
            10360,   // Amulet of glory (t4)
            11966,   // Amulet of glory (t5)
            11964,   // Amulet of glory (t6)
            // Ancient d'hide
            12496,   // Ancient coif
            12492,   // Ancient d'hide body
            12494,   // Ancient chaps
            12490,   // Ancient bracers
            19921,   // Ancient d'hide boots
            23197,   // Ancient d'hide shield
            // Ancient melee armour
            12466,   // Ancient full helm
            12460,   // Ancient platebody
            // Ancient plateskirt/legs (one slot — any one):
            12464,   // Ancient plateskirt
            12462,   // Ancient platelegs
            12468,   // Ancient kiteshield
            // Armadyl d'hide
            12512,   // Armadyl coif
            12508,   // Armadyl d'hide body
            12510,   // Armadyl chaps
            12506,   // Armadyl bracers
            19930,   // Armadyl d'hide boots
            23200,   // Armadyl d'hide shield
            // Armadyl melee armour
            12476,   // Armadyl full helm
            12470,   // Armadyl platebody
            // Armadyl plateskirt/legs (one slot — any one):
            12474,   // Armadyl plateskirt
            12472,   // Armadyl platelegs
            12478,   // Armadyl kiteshield
            // Bandos d'hide
            12504,   // Bandos coif
            12500,   // Bandos d'hide body
            12502,   // Bandos chaps
            12498,   // Bandos bracers
            19924,   // Bandos d'hide boots
            23203,   // Bandos d'hide shield
            // Bandos melee armour
            12486,   // Bandos full helm
            12480,   // Bandos platebody
            // Bandos plateskirt/legs (one slot — any one):
            12484,   // Bandos plateskirt
            12482,   // Bandos platelegs
            12488,   // Bandos kiteshield
            // Blue d'hide
            7374,   // Blue d'hide body (g)
            7382,   // Blue d'hide chaps (g)
            7376,   // Blue d'hide body (t)
            7384,   // Blue d'hide chaps (t)
            // Cavaliers
            2639,   // Tan cavalier
            2641,   // Dark cavalier
            2643,   // Black cavalier
            12321,   // White cavalier
            12323,   // Red cavalier
            12325,   // Navy cavalier
            11280,   // Cavalier mask
            // Dragon masks
            12518,   // Green dragon mask
            12520,   // Blue dragon mask
            12522,   // Red dragon mask
            12524,   // Black dragon mask
            // Enchanted robes
            7400,   // Enchanted hat
            7399,   // Enchanted top
            7398,   // Enchanted robe
            // Gilded armour
            3486,   // Gilded full helm
            20146,   // Gilded med helm
            3481,   // Gilded platebody
            20149,   // Gilded chainbody
            // Gilded plateskirt/legs (one slot — any one):
            3485,   // Gilded plateskirt
            3483,   // Gilded platelegs
            3488,   // Gilded kiteshield
            20152,   // Gilded sq shield
            // Gilded weapons
            20155,   // Gilded 2h sword
            20158,   // Gilded spear
            20161,   // Gilded hasta
            // Guthix d'hide
            10382,   // Guthix coif
            10378,   // Guthix d'hide body
            10380,   // Guthix chaps
            10376,   // Guthix bracers
            19927,   // Guthix d'hide boots
            23188,   // Guthix d'hide shield
            // Guthix melee armour
            2673,   // Guthix full helm
            2669,   // Guthix platebody
            // Guthix plateskirt/legs (one slot — any one):
            3480,   // Guthix plateskirt
            2671,   // Guthix platelegs
            2675,   // Guthix kiteshield
            // Red d'hide
            12327,   // Red d'hide body (g)
            12329,   // Red d'hide chaps (g)
            12331,   // Red d'hide body (t)
            12333,   // Red d'hide chaps (t)
            // Rune armour (g)
            2619,   // Rune full helm (g)
            2615,   // Rune platebody (g)
            // Rune plateskirt/legs (g) (one slot — any one):
            3476,   // Rune plateskirt (g)
            2617,   // Rune platelegs (g)
            2621,   // Rune kiteshield (g)
            // Rune armour (t)
            2627,   // Rune full helm (t)
            2623,   // Rune platebody (t)
            // Rune plateskirt/legs (t) (one slot — any one):
            3477,   // Rune plateskirt (t)
            2625,   // Rune platelegs (t)
            2629,   // Rune kiteshield (t)
            // Rune heraldic helms
            10286,   // Rune helm (h1)
            10288,   // Rune helm (h2)
            10290,   // Rune helm (h3)
            10292,   // Rune helm (h4)
            10294,   // Rune helm (h5)
            // Rune heraldic platebodies
            23209,   // Rune platebody (h1)
            23212,   // Rune platebody (h2)
            23215,   // Rune platebody (h3)
            23218,   // Rune platebody (h4)
            23221,   // Rune platebody (h5)
            // Rune heraldic shields
            7336,   // Rune shield (h1)
            7342,   // Rune shield (h2)
            7348,   // Rune shield (h3)
            7354,   // Rune shield (h4)
            7360,   // Rune shield (h5)
            // Saradomin d'hide
            10390,   // Saradomin coif
            10386,   // Saradomin d'hide body
            10388,   // Saradomin chaps
            10384,   // Saradomin bracers
            19933,   // Saradomin d'hide boots
            23191,   // Saradomin d'hide shield
            // Saradomin melee armour
            2665,   // Saradomin full helm
            2661,   // Saradomin platebody
            // Saradomin plateskirt/legs (one slot — any one):
            3479,   // Saradomin plateskirt
            2663,   // Saradomin platelegs
            2667,   // Saradomin kiteshield
            // Zamorak d'hide
            10374,   // Zamorak coif
            10370,   // Zamorak d'hide body
            10372,   // Zamorak chaps
            10368,   // Zamorak bracers
            19936,   // Zamorak d'hide boots
            23194,   // Zamorak d'hide shield
            // Zamorak melee armour
            2657,   // Zamorak full helm
            2653,   // Zamorak platebody
            // Zamorak plateskirt/legs (one slot — any one):
            3478,   // Zamorak plateskirt
            2655,   // Zamorak platelegs
            2659,   // Zamorak kiteshield
            // Misc
            12379,   // Rune cane
            10284,   // Magic comp bow
            19915,   // Cyclops head
            23206,   // Dual sai
            22234,   // Dragon boots (g)
            12514,   // Explorer backpack
            19918,   // Nunchaku
            8950,   // Pirate hat
            12516,   // Pith helmet
            2581,   // Robin hood hat
            23224,   // Thieving bag
            19912,   // Zombie head (Treasure Trails)
            // ── ELITE ───────────────────────────────────────────────────────────────────────
            // 3rd age
            12424,   // 3rd age bow
            12426,   // 3rd age longsword
            12422,   // 3rd age wand
            12437,   // 3rd age cloak
            // Black d'hide
            12381,   // Black d'hide body (g)
            12383,   // Black d'hide chaps (g)
            12385,   // Black d'hide body (t)
            12387,   // Black d'hide chaps (t)
            // Gilded d'hide
            23258,   // Gilded coif
            23264,   // Gilded d'hide body
            23261,   // Gilded d'hide vambraces
            23267,   // Gilded d'hide chaps
            // Dragon armour (g)
            12417,   // Dragon full helm (g)
            // Dragon platebody/chainbody (g) (one slot — any one):
            22242,   // Dragon platebody (g)
            12414,   // Dragon chainbody (g)
            // Dragon plateskirt/legs (g) (one slot — any one):
            12416,   // Dragon plateskirt (g)
            12415,   // Dragon platelegs (g)
            // Dragon kiteshield/sq shield (g) (one slot — any one):
            22244,   // Dragon kiteshield (g)
            12418,   // Dragon sq shield (g)
            // Dragon masks
            12363,   // Bronze dragon mask
            12365,   // Iron dragon mask
            12367,   // Steel dragon mask
            12369,   // Mithril dragon mask
            12371,   // Lava dragon mask
            23270,   // Adamant dragon mask
            23273,   // Rune dragon mask
            // Gilded tools and weapons
            23276,   // Gilded pickaxe
            23279,   // Gilded axe
            23282,   // Gilded spade
            12389,   // Gilded scimitar
            12391,   // Gilded boots
            // Kourend scarves
            19943,   // Arceuus scarf
            19946,   // Hosidius scarf
            19949,   // Lovakengj scarf
            19952,   // Piscarilius scarf
            19955,   // Shayzien scarf
            // Musketeer outfit
            12351,   // Musketeer hat
            12441,   // Musketeer tabard
            12443,   // Musketeer pants
            // Dark tuxedo
            19958,   // Dark tuxedo jacket
            19964,   // Dark trousers
            19961,   // Dark tuxedo cuffs
            19967,   // Dark tuxedo shoes
            19970,   // Dark bow tie
            // Light tuxedo
            19973,   // Light tuxedo jacket
            19979,   // Light trousers
            19976,   // Light tuxedo cuffs
            19982,   // Light tuxedo shoes
            19985,   // Light bow tie
            // Rangers'
            12596,   // Rangers' tunic
            23249,   // Rangers' tights
            19994,   // Ranger gloves
            // Royal outfit
            12397,   // Royal crown
            12393,   // Royal gown top
            12395,   // Royal gown bottom
            12439,   // Royal sceptre
            // Misc
            12430,   // Afro
            19988,   // Blacksmith's helm
            12335,   // Briefcase
            19991,   // Bucket helm
            12373,   // Dragon cane
            12540,   // Deerstalker
            20000,   // Dragon scimitar (or)
            23246,   // Fremennik kilt
            23252,   // Giant boot
            19941,   // Heavy casket
            19997,   // Holy wraps
            12357,   // Katana
            12353,   // Monocle
            12399,   // Partyhat & specs
            12412,   // Pirate hat & patch
            12355,   // Big pirate hat
            23185,   // Ring of 3rd age
            20005,   // Ring of nature
            12337,   // Sagacious spectacles
            23255,   // Uri's hat
            12432,   // Top hat
            12434,   // Top hat & monocle
            // ── MASTER ──────────────────────────────────────────────────────────────────────
            // 3rd age tools
            20011,   // 3rd age axe
            28226,   // 3rd age felling axe
            20014,   // 3rd age pickaxe
            // 3rd age druidic robes
            23336,   // 3rd age druidic robe top
            23339,   // 3rd age druidic robe bottoms
            23345,   // 3rd age druidic cloak
            23342,   // 3rd age druidic staff
            // Ankou outfit
            20095,   // Ankou mask
            20098,   // Ankou top
            20104,   // Ankou's leggings
            20101,   // Ankou gloves
            20107,   // Ankou socks
            // Demon masks
            20020,   // Lesser demon mask
            20023,   // Greater demon mask
            20026,   // Black demon mask
            20029,   // Old demon mask
            20032,   // Jungle demon mask
            // Kourend hoods
            20113,   // Arceuus hood
            20116,   // Hosidius hood
            20119,   // Lovakengj hood
            20122,   // Piscarilius hood
            20125,   // Shayzien hood
            // Mummy outfit
            20080,   // Mummy's head
            20083,   // Mummy's body
            20086,   // Mummy's hands
            20089,   // Mummy's legs
            20092,   // Mummy's feet
            // Robes of darkness
            20128,   // Hood of darkness
            20131,   // Robe top of darkness
            20137,   // Robe bottom of darkness
            20134,   // Gloves of darkness
            20140,   // Boots of darkness
            // Samurai outfit
            20035,   // Samurai kasa
            20038,   // Samurai shirt
            20041,   // Samurai gloves
            20044,   // Samurai greaves
            20047,   // Samurai boots
            // Eye patches (one slot — any one):
            19724,   // Left eye patch
            1025,   // Right eye patch
            19727,   // Double eye patch
            // Misc
            20056,   // Ale of the gods
            20110,   // Bowl wig
            20059,   // Bucket helm (g)
            20008,   // Fancy tiara
            20053,   // Half moon spectacles
            20050,   // Obsidian cape (r)
            20017,   // Ring of coins
            22675    // Scroll sack
        )));
}

package usa.devrocoding.synergy.spigot.statistics.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatisticType {

  // Blocks Mined
  BLOCKS_MINED(
      "Most Blocks Mined",
      "blocks mined",
      "Most Blocks Mined"
  ),
  BLOCKS_MINED_DESERT(
      "Most Desert Blocks Mined",
      "blocks mined",
      "Most Desert Blocks Mined"
  ),
  BLOCKS_MINED_OCEAN(
      "Most Ocean Blocks Mined",
      "blocks mined",
      "Most Ocean Blocks Mined"
  ),
  BLOCKS_MINED_RED_MUSHROOM(
      "Most Mushroom Blocks Mined",
      "blocks mined",
      "Most Mushroom Blocks Mined"
  ),
  BLOCKS_MINED_GREEK(
      "Most Greek Blocks Mined",
      "blocks mined",
      "Most Greek Blocks Mined"
  ),
  BLOCKS_MINED_NETHER(
      "Most Nether Blocks Mined",
      "blocks mined",
      "Most Nether Blocks Mined"
  ),
  BLOCKS_MINED_END(
      "Most End Blocks Mined",
      "blocks mined",
      "Most End Blocks Mined"
  ),
  BLOCKS_MINED_WILTHED(
      "Most Wilthed Blocks Mined",
      "blocks mined",
      "Most Wilthed Blocks Mined"
  ),

  // Crops Farmed
  CROPS_FARMED(
      "Most Crops Farmed",
      "crops farmed",
      "Most Crops Farmed"
  ),
  CROPS_FARMED_CACTUS(
      "Most Cactus Farmed",
      "cactus farmed",
      "Most Cactus Farmed"
  ),
  CROPS_FARMED_TERRACOTTA(
      "Most Terracotta Farmed",
      "terracotta farmed",
      "Most Terracotta Farmed"
  ),
  CROPS_FARMED_MUSHROOM(
      "Most Mushroom Farmed",
      "mushroom farmed",
      "Most Mushroom Farmed"
  ),

  MOST_BOSSES_KILLED(
    "Most Bosses Killed",
    "bosses killed",
    "Total Bosses Killed"
  ),
  PLAYER_KILLS(
    "Most Player Killed",
    "kills",
    "Most Player Killed"
  ),
  SHOOTING_RANGE_TARGETS_HIT(
    "Most Targets Hit",
    "targets hit",
    "Most Targets Hit"
  ),
  SPELLS_USED(
    "Most Spells Used",
    "spells used",
    "Most Spells Used"
  ),

  TREES_CUT(
      "Most Trees Sliced",
      "trees sliced",
      "Most Trees Sliced"
  ),
  MOST_XP_EARNED(
    "Most XP Earned",
    "xp earned",
    "Most XP Earned"
  ),
  EARNED_COINS(
      "Highest Balance of players",
      "coins",
      "Highest Balance of players"
  );

  private final String name;
  private final String singleDataName;
  private final String hologramTitle;

}

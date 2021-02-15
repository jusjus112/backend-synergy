package usa.devrocoding.synergy.spigot.statistics.object;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StatisticType {

  MOST_BOSSES_KILLED(
    "Most Bosses Killed",
    "bosses killed",
    "Most Bosses Killed"
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
  BLOCKS_MINED(
      "Most Blocks Mined",
      "blocks mined",
      "Most Blocks Mined"
  ),
  CROPS_FARMED(
      "Most Crops Farmed",
      "crops farmed",
      "Most Crops Farmed"
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

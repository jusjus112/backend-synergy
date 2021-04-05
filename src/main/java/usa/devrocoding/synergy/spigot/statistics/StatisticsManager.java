package usa.devrocoding.synergy.spigot.statistics;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.hologram.Hologram;
import usa.devrocoding.synergy.spigot.hologram.object.EmptyHologramLine;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.statistics.object.Statistic;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.statistics.thread.StatisticsThread;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;

public class StatisticsManager extends Module {

  private final Map<StatisticType, LinkedHashMap<String, Double>> globalStats;
  private final Map<StatisticType, LinkedList<Hologram>> hologramList;
  private final int showLimit = 10;

  /**
   * Update the statistics every X minutes for every type.
   *
   * synergyUser().getStatistics(StatisticsType.MOST_BOSSES_KILLED).add(12.12);
   * synergyUser().getStatistics(StatisticsType.MOST_BOSSES_KILLED).get();
   */

  public StatisticsManager(Core plugin){
    super(plugin, "Statistics Manager", false);

    this.globalStats = Maps.newHashMap();
    this.hologramList = Maps.newHashMap();

    new TableBuilder("statistics", plugin.getDatabaseManager())
        .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
        .addColumn("type", SQLDataType.VARCHAR, 100, false, SQLDefaultType.NO_DEFAULT, false)
        .addColumn("data", SQLDataType.DOUBLE, -1, false, SQLDefaultType.CUSTOM.setCustom(0), false)
        .setConstraints("uuid", "type")
        .execute();

    this.updateGlobalStats(null);

    plugin.getRunnableManager().runTaskTimerAsynchronously("Statistics Thread",
        new StatisticsThread(this), 30L, SynergyPeriod.SECOND.getPeriod() * 5);
  }

  @Override
  public void reload(String s) {

  }

  public void initHologram(StatisticType statisticType, Location location){
    updateStatisticsHologram(statisticType, location, null);
  }

  // TODO: The hologram lines are not updated!
  public void updateStatisticsHologram(StatisticType statisticType, Location location, SynergyUser synergyUser){
    LinkedHashMap<String, Double> map = getGlobalStats(statisticType);
    LinkedList<HologramLine> hologramLines = Lists.newLinkedList();

    hologramLines.add(new HologramLine() {
      @Override
      public String getMessage(SynergyUser synergyUser) {
        return "§5§l" + statisticType.getHologramTitle();
      }
    });

    hologramLines.add(new EmptyHologramLine());

    int i = 1;
    for (String s : map.keySet()) {
      int finalI = i;
      hologramLines.add(new HologramLine() {
        @Override
        public String getMessage(SynergyUser synergyUser) {
          return "§e" + finalI + ". " + s +" §7§l-§e " +
              UtilMath.formatDouble2DP(map.get(s)) + " " +
              statisticType.getSingleDataName();
        }
      });

      i++;
    }

    for (int j = i; j <= this.showLimit; j++) {
      hologramLines.add(new EmptyHologramLine());
    }

    if (this.hologramList.containsKey(statisticType)){
      Iterator<HologramLine> hologramLineIterator = hologramLines.iterator();
      this.hologramList.get(statisticType).forEach(hologram -> {
        if (hologramLineIterator.hasNext() && synergyUser != null) {
          HologramLine hologramLine = hologramLineIterator.next();
          getPlugin().getHologramManager().updateHologramLine(hologramLine, hologram, synergyUser);
        }
      });
      return;
    }
    if (location != null) {
      LinkedList<Hologram> holograms = getPlugin().getHologramManager().createHologram(
          location, player -> true, hologramLines
      );
      this.hologramList.put(statisticType, holograms);
    }
  }

  public LinkedHashMap<String, Double> getGlobalStats(StatisticType statisticType){
    switch (statisticType){
      default:
        return this.globalStats.getOrDefault(statisticType, Maps.newLinkedHashMap());
    }
  }

  public void updateGlobalStats(SynergyUser synergyUser){
    Arrays.stream(StatisticType.values()).forEach(statisticType -> {
      LinkedHashMap<String, Double> map = Maps.newLinkedHashMap();

      ResultSet result = Core.getPlugin().getDatabaseManager().executeQuery(
          "SELECT users.rank, users.name, users.uuid, stats.data FROM synergy_statistics AS stats " +
              "LEFT JOIN synergy_users AS users ON stats.uuid = users.uuid " +
              "WHERE stats.type = '" + statisticType.toString().toUpperCase() + "' " +
              "AND users.rank != 'OWNER' " +
              "AND users.rank != 'MANAGER' " +
              "AND users.rank != 'ADMIN' " +
              "AND users.rank != 'SRDEVELOPER' " +
              "AND users.rank != 'JRDEVELOPER' " +
              "AND users.rank != 'OWNER' "+
              "ORDER BY data DESC LIMIT " + this.showLimit
      );

      if (result != null) {
        try {
          while (result.next()) {
            Rank rank = Rank.valueOf(result.getString("rank"));
            String userName = result.getString("name");
            UUID uuid = UtilSQL.convertBinaryStream(result.getBinaryStream("uuid"));
            double data = result.getDouble("data");

            SynergyUser targetUser = Core.getPlugin().getUserManager().getUser(uuid);
            if (targetUser != null) {
              // User is online
              data = targetUser.getStatistic(statisticType).get();
            }

            if (data <= 0) {
              continue;
            }

            map.put(rank.getPrefix() + " " + rank.getTextColor() + userName + ChatColor.RESET,
                data);
          }
          LinkedHashMap<String, Double> reverseSortedMap = new LinkedHashMap<>();

          map.entrySet()
              .stream()
              .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
              .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));

          this.globalStats.put(statisticType, reverseSortedMap);
        } catch (Exception ignored) {

        }
      }

      if (synergyUser != null) {
        this.updateStatisticsHologram(statisticType, null, synergyUser);
      }
    });
  }

  public Map<StatisticType, Statistic> retrieveStatistics(UUID uuid){
    Map<StatisticType, Statistic> statisticMap = Maps.newHashMap();

    try{
      ResultSet result = Core.getPlugin().getDatabaseManager().getResults(
          "statistics ", "uuid=?", new HashMap<Integer, Object>(){{
            put(1, UtilSQL.convertUniqueId(uuid));
          }}
      );

      while (result.next()){
        StatisticType statisticType = StatisticType.valueOf(result.getString("type").toUpperCase());
        double data = Double.parseDouble(result.getString("data"));

        statisticMap.put(statisticType, new Statistic(data));
      }

      // Fill empty stats
      Arrays.stream(StatisticType.values())
          .filter(statisticType -> !statisticMap.containsKey(statisticType))
          .forEach(statisticType -> {
            statisticMap.put(statisticType, new Statistic(0D));
          });
    }catch (Exception ignored){}
    return statisticMap;
  }

  public void saveStatisticsForUser(SynergyUser synergyUser){
    Core.getPlugin().getRunnableManager().runTaskAsynchronously(
        "Update Stats",
        core -> synergyUser.getStatistics()
            .forEach((statisticType, statistic) -> {

              if (statistic.get() > 0) {

                {
                  HashMap<String, Object> data = new HashMap<String, Object>() {{
                    put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                    put("type", statisticType.toString().toUpperCase());
                    put("data", statistic.get());
                  }};

//                  Synergy.debug(data + " = DATA LEAVE");
                  if (!getPlugin().getDatabaseManager().insert("statistics", data)) {
                    Core.getPlugin().getDatabaseManager().update(
                        "statistics",
                        new HashMap<String, Object>() {{
                          put("data", statistic.get());
                        }},
                        new HashMap<String, Object>() {{
                          put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                          put("type", statisticType.toString().toUpperCase());
                        }}
                    );
                  }
                }

              }
        })
    );
  }

}

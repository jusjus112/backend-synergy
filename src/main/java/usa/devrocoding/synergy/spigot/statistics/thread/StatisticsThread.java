package usa.devrocoding.synergy.spigot.statistics.thread;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.statistics.StatisticsManager;

@RequiredArgsConstructor
public class StatisticsThread implements Consumer<Core> {

  private final StatisticsManager statisticsManager;

  @Override
  public void accept(Core core) {
    Core.getPlugin().getUserManager().getOnlineUsers().forEach(
        this.statisticsManager::updateGlobalStats);

  }

}

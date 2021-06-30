package usa.devrocoding.synergy.spigot.assets;

import lombok.Setter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.commands.*;
import usa.devrocoding.synergy.spigot.listeners.EventHandlers;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;

public class GlobalManager extends Module {

    @Setter
    private UtilDisplay utilDisplay;

    public GlobalManager(Core plugin){
        super(plugin, "Global Manager", true);

        registerCommand(
            new CommandClearInventory(plugin),
            new CommandDeveloper(plugin),
            new CommandEnderchest(plugin),
            new CommandFly(plugin),
            new CommandGamemode(plugin),
            new CommandHeal(plugin),
            new CommandHelp(plugin),
//            new CommandKill(plugin),
            new CommandInvsee(plugin),
            new CommandPlugins(plugin),
            new CommandSpeed(plugin),
            new CommandSynergy(plugin),
            new CommandSynergyReload(plugin),
            new CommandTeleport(plugin),
            new CommandTop(plugin),
            new CommandWorkbench(plugin),
            new CommandSpawn(plugin),
            new CommandWeather(plugin),
            new CommandSystems(plugin),
            new CommandTime(plugin),
            new CommandDiscord(plugin),
            new CommandVotes(plugin),
            new CommandMessageAll(plugin),
            new CommandConsoleMessage(plugin)
        );

        registerListener(
                new EventHandlers()
        );

//        new BukkitRunnable(){
//            @Override
//            public void run() {
//                Synergy.info("-----");
//                Synergy.info("Active Connections: " +
//                    SQLService.getDataSource().getHikariPoolMXBean().getActiveConnections());
//                Synergy.info("Total Connections: " +
//                    SQLService.getDataSource().getHikariPoolMXBean().getTotalConnections());
//                Synergy.info("Idle Connections: " +
//                    SQLService.getDataSource().getHikariPoolMXBean().getIdleConnections());
//            }
//        }.runTaskTimerAsynchronously(getPlugin(), 20, 20 * 10);
    }

    @Override
    public void onReload(String response) {

    }

    public UtilDisplay getUtilDisplay(Player player){
        return this.utilDisplay.setPlayer(player);
    }
}

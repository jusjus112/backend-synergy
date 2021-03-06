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
            new CommandTime(plugin)
        );

        registerListener(
                new EventHandlers()
        );
    }

    @Override
    public void reload(String response) {

    }

    public UtilDisplay getUtilDisplay(Player player){
        return this.utilDisplay.setPlayer(player);
    }
}

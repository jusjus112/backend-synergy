package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.commands.*;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;

public class GlobalManager extends Module {

    @Setter
    private UtilDisplay utilDisplay;

    public GlobalManager(Core plugin){
        super(plugin, "Global Manager", true);

        registerCommand(
            new CommandSynergy(plugin),
            new CommandPlugins(plugin),
            new CommandGamemode(plugin),
            new CommandTeleport(plugin),
            new CommandDeveloper(plugin),
            new CommandSynergyReload(plugin),
            new CommandClearInventory(plugin),
            new CommandFly(plugin),
            new CommandHeal(plugin),
            new CommandTop(plugin)
        );
    }

    @Override
    public void reload(String response) {

    }

    public UtilDisplay getUtilDisplay(Player player){
        return this.utilDisplay.setPlayer(player);
    }
}

package usa.devrocoding.synergy.spigot.assets;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.commands.CommandGamemode;
import usa.devrocoding.synergy.spigot.assets.commands.CommandPlugins;
import usa.devrocoding.synergy.spigot.assets.commands.CommandSynergy;
import usa.devrocoding.synergy.spigot.assets.commands.CommandTeleport;

public class GlobalManager extends Module {

    public GlobalManager(Core plugin){
        super(plugin, "Global Manager");

        registerCommand(
            new CommandSynergy(plugin),
            new CommandPlugins(plugin),
            new CommandGamemode(plugin),
            new CommandTeleport(plugin)
        );
    }

}

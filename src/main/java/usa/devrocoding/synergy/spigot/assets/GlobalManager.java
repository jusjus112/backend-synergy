package usa.devrocoding.synergy.spigot.assets;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.api.commands.CommandSynergy;

public class GlobalManager extends Module {

    public GlobalManager(Core plugin){
        super(plugin, "Global Manager");

        registerCommand(
            new CommandSynergy(plugin)
        );
    }

}

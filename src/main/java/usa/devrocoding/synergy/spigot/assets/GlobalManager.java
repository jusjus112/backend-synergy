package usa.devrocoding.synergy.spigot.assets;

import org.bukkit.Bukkit;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.commands.*;

public class GlobalManager extends Module {

    public GlobalManager(Core plugin){
        super(plugin, "Global Manager", true);

        registerCommand(
            new CommandSynergy(plugin),
            new CommandPlugins(plugin),
            new CommandGamemode(plugin),
            new CommandTeleport(plugin),
            new CommandDeveloper(plugin),
            new CommandSynergyReload(plugin)
        );
    }

    @Override
    public void reload(String response) {

    }
}

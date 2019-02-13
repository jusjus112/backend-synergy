package usa.devrocoding.synergy.spigot.assets;

import org.bukkit.Bukkit;
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

    public Class<?> getServerClass(String afterPackage){
        String servPack = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + servPack + "." + afterPackage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}

package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import org.bukkit.Bukkit;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class DependencyManager extends Module {

    public DependencyManager(Core plugin){
        super(plugin, "Dependency Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public boolean isPluginEnabled(String plugin){
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    public void initDependecies(){

    }

}

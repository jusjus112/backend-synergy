package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.depends.PlaceHolderAPI;

public class DependencyManager extends Module {

    private PlaceHolderAPI placeHolderAPI;

    public DependencyManager(Core plugin){
        super(plugin, "Dependency Manager", false);

        if (isPluginEnabled("PlaceholderAPI")){
            this.placeHolderAPI = new PlaceHolderAPI(getPlugin());
        }
    }

    @Override
    public void reload(String response) {

    }

    @Deprecated
    public PlaceHolderAPI getPlaceHolderAPI() throws NullPointerException{
        return placeHolderAPI;
    }

    public boolean isPluginEnabled(String plugin){
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    public void initDependecies(){

    }

}

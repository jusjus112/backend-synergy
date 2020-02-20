package usa.devrocoding.synergy.spigot.assets;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.depends.PlaceHolderAPI;

import static org.bukkit.Bukkit.getServer;

public class DependencyManager extends Module {

    private PlaceHolderAPI placeHolderAPI;
    private Economy vaultEconomy;

    public DependencyManager(Core plugin){
        super(plugin, "Dependency Manager", false);

        if (isPluginEnabled("PlaceholderAPI")){
            this.placeHolderAPI = new PlaceHolderAPI(getPlugin());
        }

        if (isPluginEnabled("Vault")){
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp != null) {
                this.vaultEconomy = rsp.getProvider();
            }
        }
    }

    @Override
    public void reload(String response) {

    }

    @Deprecated
    public PlaceHolderAPI getPlaceHolderAPI() throws NullPointerException{
        return this.placeHolderAPI;
    }

    @Deprecated
    public Economy getVaultEconomy() throws NullPointerException{
        return this.vaultEconomy;
    }

    public boolean isPluginEnabled(String plugin){
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

}

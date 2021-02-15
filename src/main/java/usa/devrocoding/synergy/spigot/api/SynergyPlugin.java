package usa.devrocoding.synergy.spigot.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public abstract class SynergyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!name().equalsIgnoreCase("Synergy")){
            Synergy.info(Synergy.format("extension", LinuxColorCodes.ANSI_YELLOW, name() + " detected...."));

            if (!Core.getPlugin().isLoaded()){
                Synergy.error("Synergy not detected or enabled! Disabling extension...");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }
        try {
            preInit();
            init();
            if (!name().equalsIgnoreCase("Synergy")) {
                if (!Core.getPlugin().getPluginManager().getPlugins().contains(this)) {
                    Core.getPlugin().getPluginManager().getPlugins().add(this);
                }
                Synergy.info(
                        Synergy.format("extension", LinuxColorCodes.ANSI_YELLOW,
                            "Loaded a total of " + Module.getTotal() + " Modules for extension '" + name() + "'!")
                );
                Module.total = 0;
            }
        }catch (Exception e){
            Synergy.error("Error while loading '" +name()+"'");
            Synergy.error("@ "+e.getStackTrace()[0].toString());
            Synergy.error(e.getMessage());
            e.printStackTrace();
            // TODO: Create error message with bot and SAM
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        preDeInit();
        deinit();
    }

    public String name() {return "Synergy";}
    public void preInit() {}
    public void preDeInit() {}

    public abstract void init();
    public abstract void deinit();

}

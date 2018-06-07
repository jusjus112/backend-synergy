package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    @Getter
    @Setter
    private int x, y, z, e;

    @Getter @Setter
    private static Core plugin;
    @Getter
    private UtilFile cache;

    public void onEnable(){
        setPlugin(this);
        this.cache = new UtilFile(getDataFolder()+"", "data");



        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        new PluginManager(this);
    }

    public void onDisable(){
        this.statisticsManager.save();
    }

}

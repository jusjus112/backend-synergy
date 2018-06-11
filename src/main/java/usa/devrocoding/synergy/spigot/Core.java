package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.spigot.api.Synergy;
import usa.devrocoding.synergy.spigot.api.SynergyAPI;
import usa.devrocoding.synergy.spigot.assets.PluginManager;
import usa.devrocoding.synergy.spigot.assets.SynergyMani;
import usa.devrocoding.synergy.spigot.command.CommandManager;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;

import java.io.FileNotFoundException;

@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy.")
public class Core extends JavaPlugin {

    @Getter @Setter
    private static Core plugin;

    @Getter
    private PluginManager pluginManager;
    @Getter
    private CommandManager commandManager;
    @Getter
    private RunnableManager runnableManager;
    @Getter
    private DatabaseManager databaseManager;

    @Getter
    private SynergyMani manifest;

    public void onEnable(){
        setPlugin(this);

        // Load Files and other important things
        this.pluginManager = new PluginManager(this);
        this.pluginManager.load();

        // Load the Manifest so other it can be used in other classes
        this.manifest = this.getClass().getAnnotation(SynergyMani.class);

        // BungeeCord channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Load the sql Service so SQL can be used
        try{
            // Initialize SQL
            YMLFile f = getPluginManager().getFileStructure().getYMLFile("settings");
            this.databaseManager = new DatabaseManager(new SQLService(
                    f.get().getString("sql.host"),
                    f.get().getString("sql.database"),
                    f.get().getString("sql.username"),
                    f.get().getString("sql.password")));

            // Connect to SQL
            this.databaseManager.connect();
        }catch (FileNotFoundException e){
            Synergy.debug("Can't connect to your SQL service provider");
            getPluginLoader().disablePlugin(this);
        }

        // Load the modules
        this.commandManager = new CommandManager(this);
        this.runnableManager = new RunnableManager(this);

        // Disable this to disable the API
        Synergy.setAPI(new SynergyAPI());
    }

    public void onDisable(){

    }

}

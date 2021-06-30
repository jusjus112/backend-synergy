package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import org.bukkit.Bukkit;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.assets.FileStructure.FileType;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.listeners.EventHandlers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PluginManager extends Module {

    private Core plugin;

    @Getter
    private FileStructure fileStructure;
    @Getter
    private List<SynergyPlugin> plugins = new ArrayList<>();

    public PluginManager(Core plugin){
        super(plugin, "Plugin Manager", false);
        this.plugin = plugin;

        registerListener(
                new EventHandlers()
        );
    }

    @Override
    public void onReload(String response) {

    }

    // Init the database
    public boolean initDatabase(){
        try{
            // Initialize SQL
            YMLFile f = getPlugin().getPluginManager().getFileStructure().getYMLFile("settings");
            getPlugin().setDatabaseManager(new DatabaseManager(new SQLService(
                    f.get().getString("sql.host"),
                    f.get().getString("sql.database"),
                    f.get().getInt("sql.port"),
                    f.get().getString("sql.username"),
                    f.get().getString("sql.password")
                )));

            // Connect to SQL
            Synergy.info("Connecting to SQL....");

            try{

                Synergy.success("Connected to your SQL Service Provider");
                getPlugin().getDatabaseManager().loadDefaultTables(); // Generate the default tables if they didn't exist
                return true;
            }catch (Exception e){
                Synergy.error("Cannot connect to MySQL. Something went wrong.", "Using JSON files as your database!");
                Bukkit.getServer().shutdown();
                return false;
            }
        }catch (FileNotFoundException e){
            //TODO: Create one instead
            Synergy.error("File 'settings.yml' doesn't exists. Creating one now...", "Restarting Plugin.....");
            Bukkit.getServer().getPluginManager().enablePlugin(getPlugin());
            return false;
        }catch (Exception e){
            Synergy.error("I can't connect to your SQL Service provider", e.getMessage(),
                    "Check your SQL settings in the 'settings.yml'", "Shutting down.....");
            Bukkit.getServer().shutdown();
            return false;
        }
    }

    // This will be called on a startup and on reloads
    public void load(){
        this.fileStructure = new FileStructure();

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
            .add("modules", null, "modules", FileType.YML)
//                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
            .add("settings", null, "settings", FileType.YML)
            .add("warps", null, "warps", FileType.JSON)
            .add("example_changelog", "changelogs/server", "example_changelog", FileType.YML)
            .add("en", "lang", "en_EN", FileType.YML)
            .add("nl", "lang", "nl_NL", FileType.YML)
            .add("offensive_words", null, "offensive", FileType.JSON)
            .save();
        try{
            this.fileStructure.getYMLFile("settings").set(
                    new HashMap<String, Object>(){{
                        put("sql.host", "127.0.0.1");
                        put("sql.database", "database");
                        put("sql.username", "username");
                        put("sql.password", "password");
                        put("sql.port", 3306);
                        put("network.name", "Synergy Network");
                        put("network.isLobby", false);
                        put("network.isProduction", false);
                        put("network.serverName", "hub");
                    }}
            );
            this.fileStructure.getYMLFile("modules").setHeader("Disable your modules here to increase load time. List of modules here: 'https://bitbucket.org/devrocoding/synergy-backend/wiki/list-modules'").set(
                    new HashMap<String, Object>(){{
                        put("modules.disabled", new ArrayList<String>());
                    }}
            );
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Try to contact the server developer", e);
        }
    }

}

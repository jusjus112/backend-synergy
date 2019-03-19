package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
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
    public void reload(String response) {

    }

    // This will be called on a startup and on a reloads
    public void load(){
        this.fileStructure = new FileStructure();

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
//                .add("modules", null, "Modules", FileStructure.FileType.YML)
                .add("sam", null, "Sam", FileStructure.FileType.YML)
//                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
                .add("settings", null, "Settings", FileStructure.FileType.YML)
                .add("storage", null, "storage", FileStructure.FileType.JSON)
                .add("example_changelog", "changelogs/server", "example_changelog", FileStructure.FileType.YML)
                .add("en", "lang", "en_EN", FileStructure.FileType.YML)
                .add("nl", "lang", "nl_NL", FileStructure.FileType.YML)
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
                        put("network.serverName", "hub");
                    }}
            );
            this.fileStructure.getYMLFile("sam").set(
                    new HashMap<String, Object>(){{
                        put("messages.prefix_color", "&9");
                        put("messages.message_color", "&7");

                        put("global.logging.cleanUpLogging", "3d");
                        put("global.logging.cleanUpErrorLogging", "3d");
                    }}
            );
//            this.fileStructure.getYMLFile("modules").setHeader("Disable your modules here. List of modules here: 'https://bitbucket.org/devrocoding/synergy-backend/wiki/list-modules'").set(
//                    new HashMap<String, Object>(){{
//                        put("modules.disabled", new ArrayList<String>());
//                    }}
//            );
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Try to contact the server developer", e);
        }
    }

    public void registerPlugin(SynergyPlugin plugin){
        if (!this.getPlugins().contains(plugin)){
            this.getPlugins().add(plugin);
        }
    }

}

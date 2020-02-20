package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
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

    // This will be called on a startup and on reloads
    public void load(){
        this.fileStructure = new FileStructure();

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
                .add("modules", null, "modules", FileStructure.FileType.YML)
//                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
                .add("settings", null, "settings", FileStructure.FileType.YML)
                .add("storage", null, "database", FileStructure.FileType.JSON)
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

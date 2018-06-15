package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.api.commands.CommandSynergy;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class PluginManager extends Module {

    private Core plugin;

    @Getter
    private FileStructure fileStructure;

    public PluginManager(Core plugin){
        super(plugin, "Plugin Manager");
        this.plugin = plugin;

        registerCommand(
                new CommandSynergy(plugin)
        );
    }

    // This will be called on a startup and on a reloads
    public void load(){
        this.fileStructure = new FileStructure(plugin);

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
                .add("modules", null, "Modules", FileStructure.FileType.YML)
                .add("brobot", null, "Brobot", FileStructure.FileType.YML)
                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
                .add("settings", null, "Settings", FileStructure.FileType.YML)
                .save();

        try{
            this.fileStructure.getYMLFile("settings").setup(
                    new HashMap<String, Object>(){{
                        put("sql.host", "127.0.0.1");
                        put("sql.database", "database");
                        put("sql.username", "username");
                        put("sql.password", "password");
                        put("sql.port", 3306);
                    }}
            );
            this.fileStructure.getYMLFile("brobot").setup(
                    new HashMap<String, Object>(){{
                        put("messages.prefix_color", "&9");
                        put("messages.message_color", "&7");
                    }}
            );
        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }
    }

    // This will be called on a disable and a reload
    public void unload(){

    }

}

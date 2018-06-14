package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.io.FileNotFoundException;

public class PluginManager extends Module {

    private Core plugin;

    @Getter
    private FileStructure fileStructure;

    public PluginManager(Core plugin){
        super(plugin, "Plugin Manager");
        this.plugin = plugin;
    }

    // This will be called on a startup and on a reload
    public void load(){
        this.fileStructure = new FileStructure(plugin);

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
//                .add("sql", null, "sql", FileStructure.FileType.JSON)
//                .add("language_nl", "lang", "nl_NL", FileStructure.FileType.JSON)
                .add("modules", null, "Modules", FileStructure.FileType.YML)
                .add("brobot", null, "Brobot", FileStructure.FileType.YML)
                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
                .add("settings", null, "Settings", FileStructure.FileType.YML)
                .save();

        try{
            this.fileStructure.getYMLFile("settings").setup(
                    "sql.host", "127.0.0.1",
                    "sql.database", "synergy",
                    "sql.username", "username",
                    "sql.password", "password",
                    "sql.port", 3301
            );
        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }
    }

    // This will be called on a disable and a reload
    public void unload(){

    }

}

package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class PluginManager extends Module {

    private Core plugin;

    @Getter
    private FileStructure fileStructure;

    public PluginManager(Core plugin){
        super(plugin, "Plugin Manager");
        this.plugin = plugin;
    }

    public void load(){
        this.fileStructure = new FileStructure(plugin);

        /* LOAD DEFAULT FILE STRUCTURE */
        this.fileStructure
//                .add("sql", null, "sql", FileStructure.FileType.JSON)
                .add("language_nl", "lang", "nl_NL", FileStructure.FileType.JSON)
                .add("modules", null, "modules", FileStructure.FileType.YML)
                .add("brobot", null, "Brobot", FileStructure.FileType.YML)
                .add("buddy", null, "Buddy", FileStructure.FileType.YML)
                .add("settings", null, "Settings", FileStructure.FileType.YML)
                .save();
    }

}

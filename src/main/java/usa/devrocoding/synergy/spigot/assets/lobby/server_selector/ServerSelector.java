package usa.devrocoding.synergy.spigot.assets.lobby.server_selector;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.FileStructure;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerSelector {

    @Getter
    private YMLFile selectorConfig;
    @Getter
    private Map<String, List<String>> itemData = new HashMap<>(),
            selectorData = new HashMap<>();

    public void setup(){
        Core.getPlugin().getPluginManager().getFileStructure()
                .add("server_selector", Core.getPlugin().getDataFolder().toString(), "server_selector", FileStructure.FileType.YML).save();
        try{
            this.selectorConfig = Core.getPlugin().getPluginManager().getFileStructure().getYMLFile("server_selector");

            this.selectorConfig.set(new HashMap<String, Object>(){{
                put("selector.name", "&b&lServer Name");
                put("selector.position", "&b&lServer Name");
                put("selector.gui-name", "&b&lServer Name");

                put("gui-items.BARRIER.name", "&b&lServer Name");
                put("gui-items.BARRIER.position", "&b&lServer Name");
                put("gui-items.BARRIER.server", "&b&lServer Name");
                put("gui-items.BARRIER.lore", "&b&lServer Name");
            }});
        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }
    }

    public void loadData(){
        try{
            this.selectorConfig = Core.getPlugin().getPluginManager().getFileStructure().getYMLFile("server_selector");


        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }
    }

}

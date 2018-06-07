package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;
import usa.devrocoding.synergy.spigot.files.yml.UtilFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStructure extends Module {

    private static Map<String, UtilFile> ymlFiles = new HashMap<>();
    private static Map<String, JSONFile> jsonFiles = new HashMap<>();

    public FileStructure(Core plugin){
        super(plugin, "FileStructure Manager");
    }

    public static UtilFile getYMLFile(String key){
        if (ymlFiles.containsKey(key)){
            return ymlFiles.get(key);
        }
        return null;
    }

    public static JSONFile getJSONFile(String key){
        if (jsonFiles.containsKey(key)){
            return jsonFiles.get(key);
        }
        return null;
    }

    public FileStructure add(String key, UtilFile file){
        if (ymlFiles.containsKey(key)){
            return this;
        }
        ymlFiles.put(key, file);
        return this;
    }

    public FileStructure add(String key, JSONFile file){
        if (jsonFiles.containsKey(key)){
            return this;
        }
        jsonFiles.put(key, file);
        return this;
    }

    public void save(){
        for(UtilFile files : ymlFiles.values()){
            files.save();
        }
    }

}

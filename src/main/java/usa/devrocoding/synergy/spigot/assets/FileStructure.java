package usa.devrocoding.synergy.spigot.assets;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class FileStructure extends Module {

    private Map<String, YMLFile> ymlFiles = new HashMap<>();
    private Map<String, JSONFile> jsonFiles = new HashMap<>();

    public enum FileType{
        JSON, YML
    }

    public FileStructure(Core plugin){
        super(plugin, "FileStructure Manager");
    }

    public YMLFile getYMLFile(String key)throws FileNotFoundException {
        if (ymlFiles.containsKey(key)){
            return ymlFiles.get(key);
        }
        return null;
    }

    public JSONFile getJSONFile(String key)throws FileNotFoundException{
        if (jsonFiles.containsKey(key)){
            return jsonFiles.get(key);
        }
        return null;
    }

    public FileStructure add(String key, String folder, String file, FileType type){
        String query = null;
        switch (type){
            case YML:
                if (ymlFiles.containsKey(key)){
                    return this;
                }
                if (folder == null){
                    query = getPlugin().getDataFolder()+"";
                }else{
                    query = getPlugin().getDataFolder()+File.separator+folder;
                }
                ymlFiles.put(key, new YMLFile(query, file));
                break;
            case JSON:
                if (jsonFiles.containsKey(key)){
                    return this;
                }
                jsonFiles.put(key, new JSONFile((folder==null?"":folder+File.separator)+file));
                break;
        }
        return this;
    }

    public void save(){
        for(YMLFile files : ymlFiles.values()){
            if (!files.exists())
                files.save();
        }

        for(JSONFile files : jsonFiles.values()){
            if (!files.exists())
                files.finish();
        }
    }

}

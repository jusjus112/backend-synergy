package usa.devrocoding.synergy.spigot.assets;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class FileStructure {

    private static Map<String, YMLFile> ymlFiles = new HashMap<>();
    private static Map<String, JSONFile> jsonFiles = new HashMap<>();

    public enum FileType{
        JSON, YML
    }


    public YMLFile getYMLFile(String key)throws FileNotFoundException {
        if (ymlFiles.containsKey(key)){
            return ymlFiles.get(key);
        }
        throw new FileNotFoundException("Can't find the file with key '"+key+"'");
    }

    public JSONFile getJSONFile(String key)throws FileNotFoundException{
        if (jsonFiles.containsKey(key)){
            return jsonFiles.get(key);
        }
        throw new FileNotFoundException("Can't find the file with key '"+key+"'");
    }

    public FileStructure add(String key, String folder, String file, FileType type){
        String query;
        switch (type){
            case YML:
                if (ymlFiles.containsKey(key)){
                    return this;
                }
                if (folder == null){
                    query = Core.getPlugin().getDataFolder()+"";
                }else{
                    query = Core.getPlugin().getDataFolder()+File.separator+folder;
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

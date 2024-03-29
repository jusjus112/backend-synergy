package usa.devrocoding.synergy.spigot.assets;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;
import usa.devrocoding.synergy.spigot.files.txt.TXTFile;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class FileStructure {

    private final Map<String, YMLFile> ymlFiles = new HashMap<>();
    private final Map<String, JSONFile> jsonFiles = new HashMap<>();
    private final Map<String, TXTFile> txtFiles = new HashMap<>();

    public enum FileType{
        JSON, YML, TXT
    }

    public YMLFile getYMLFile(String key)throws FileNotFoundException {
        if (ymlFiles.containsKey(key)){
            return ymlFiles.get(key);
        }
        throw new FileNotFoundException("Can't find the YAML file with key '"+key+"'");
    }

    public JSONFile getJSONFile(String key)throws FileNotFoundException{
        if (jsonFiles.containsKey(key)){
            return jsonFiles.get(key);
        }
        throw new FileNotFoundException("Can't find the JSON file with key '"+key+"'");
    }

    public TXTFile getTXTFile(String key)throws FileNotFoundException{
        if (txtFiles.containsKey(key)){
            return txtFiles.get(key);
        }
        throw new FileNotFoundException("Can't find the TXT file with key '"+key+"'");
    }

    public FileStructure add(String key, String folder, String file, FileType type){
        String query;
        if (folder == null){
            query = Core.getPlugin().getDataFolder()+"";
        }else{
            query = Core.getPlugin().getDataFolder()+File.separator+folder;
        }
        switch (type){
            case YML:
                if (ymlFiles.containsKey(key)){
                    return this;
                }
                ymlFiles.put(key, new YMLFile(query, file));
                break;
            case JSON:
                if (jsonFiles.containsKey(key)){
                    return this;
                }
                jsonFiles.put(key, new JSONFile((folder==null?"":folder),file));
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

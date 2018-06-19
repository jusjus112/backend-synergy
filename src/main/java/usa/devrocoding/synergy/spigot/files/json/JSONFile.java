package usa.devrocoding.synergy.spigot.files.json;

import org.json.simple.JSONObject;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;

import java.io.File;
import java.io.FileWriter;

public class JSONFile {

    private JSONObject main = new JSONObject();
    private File file;

    public JSONFile(String fileName){
        try {
            this.file = new File(Core.getPlugin().getDataFolder() + File.separator + fileName + ".json");
            if (!exists()) {
                this.file.createNewFile();
            }
        }catch(Exception e){
            Synergy.error(e.getMessage());
        }
    }

    public boolean exists(){
        return this.file.exists();
    }

    public JSONFile write(String path, String object){
        main.put(path, object);
        return this;
    }

    public void finish(){
        try {
            FileWriter fileWriter = new FileWriter(this.file);
            fileWriter.write(this.main.toJSONString());
            fileWriter.flush();
            fileWriter.close();
            this.main.clear();
        }catch(Exception e){
            Synergy.error(e.getMessage());
        }
    }

    public void read(String path){
        String var = null;
        try {
            
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
    }

}

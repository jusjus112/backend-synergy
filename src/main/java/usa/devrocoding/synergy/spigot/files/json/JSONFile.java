package usa.devrocoding.synergy.spigot.files.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JSONFile {

    private JsonObject main = new JsonObject();
    private File file;

    public JSONFile(String folder, String fileName){
        try {
            File f = new File(Core.getPlugin().getDataFolder()+File.separator+folder);
            if (!f.exists()){
                f.mkdir();
            }

            this.file = new File(f.getAbsolutePath() + File.separator + fileName + ".json");
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

    public JSONFile write(String path, JsonElement object, boolean override){
        if (override || !exists(path)){
            main.add(path, object);
        }
        return this;
    }

    public void finish(){
        try {
            FileWriter fileWriter = new FileWriter(this.file);
            fileWriter.write(this.main.toString());
            fileWriter.flush();
            fileWriter.close();
            this.main = new JsonObject();
        }catch(Exception e){
            Synergy.error(e.getMessage());
        }
    }

    public JsonObject get(){
        try {
            Gson gson = new Gson();
            return gson.fromJson(new FileReader(this.file), JsonObject.class);
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
        return null;
    }

    public boolean exists(String path){
        return get() != null && get().has(path);
    }

}

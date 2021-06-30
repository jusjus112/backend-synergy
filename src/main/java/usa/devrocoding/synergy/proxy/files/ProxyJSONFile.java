package usa.devrocoding.synergy.proxy.files;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.proxy.Core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ProxyJSONFile {

    private JsonObject main = new JsonObject();
    private File file;

    public ProxyJSONFile(String folder, String fileName){
        try {
            File f = new File(Core.getCore().getDataFolder()+File.separator+folder);
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

    public ProxyJSONFile write(String path, JsonElement object, boolean override){
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

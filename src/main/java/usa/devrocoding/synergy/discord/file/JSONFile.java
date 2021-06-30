package usa.devrocoding.synergy.discord.file;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import usa.devrocoding.synergy.includes.Synergy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class JSONFile {

    private File file;
    private JsonObject jsonObject;
    private String name;

    public JSONFile(String fileName){
        this.name = fileName;

        try {
            this.file = new File(fileName + ".json");
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

    public JSONFile write(JsonObject jsonObject){
        this.jsonObject = jsonObject;
        return this;
    }

    public void finish(){
        try (FileWriter fileWriter = new FileWriter(file.getAbsolutePath())) {
            fileWriter.write(this.jsonObject.toString());
            fileWriter.flush();
        }catch(Exception e){
            Synergy.error(e.getMessage());
        }
    }

    public JsonElement read(String key){
        JsonObject jsonObject = null;
        try {
            JsonParser parser = new JsonParser();
            jsonObject = parser.parse(new InputStreamReader(new FileInputStream(this.name + ".json"))).getAsJsonObject();
        } catch (Exception e) {
            Synergy.error(e.getMessage());
        }
        if (jsonObject == null){
            Synergy.error("Contact a developer immediately. JSON Object = null");
            return null;
        }
        return jsonObject.get(key);
    }

}

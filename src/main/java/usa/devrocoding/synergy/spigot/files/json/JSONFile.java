package usa.devrocoding.synergy.spigot.files.json;

import com.sun.istack.internal.NotNull;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import us.jusjus.hubutils.spigot.api.ServerUtilAPI;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JSONFile {

    private File file;
    private String fileName;
    private JSONObject main = new JSONObject();

    public JSONFile(String fileName){
        this.fileName = fileName;

        try {
            this.file = new File(ServerUtilAPI.getAPI().getDataFolder().getAbsolutePath() + File.separator + fileName + ".json");
            if (exists()) {
                this.file.createNewFile();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean exists(){
        return this.file.exists();
    }

    public JSONFile write(@NotNull String path, @NotNull String object){
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
            e.printStackTrace();
        }
    }

    public void read(String path){
        String var = null;
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

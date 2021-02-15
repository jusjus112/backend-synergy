package usa.devrocoding.synergy.spigot.files.txt;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.botsam.Sam;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TXTFile {

    @Getter
    private File file;
    @Getter
    private String dataFolder;
    @Getter
    private String name;

    public TXTFile(String dataFolder, String fileName) {
        this.dataFolder = dataFolder;
        this.name = fileName;

        try {
            file = new File(dataFolder, fileName+".txt");
            if (exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        }catch (Exception e){
            Sam.getRobot().error(Core.getPlugin().getPluginManager(), e.getMessage(), "Contact a developer!", e);
        }
    }

    public File get() {
        return file;
    }

    public void set(ArrayList<String> data) {
        if (data.size() > 0){
            try{
                FileWriter fileWriter = new FileWriter(this.file, true);
                PrintWriter writer = new PrintWriter(fileWriter);

                for(String line : data)
                    writer.println(line);

                writer.flush();
                writer.close();
            }catch (Exception e){
                Sam.getRobot().error(Core.getPlugin().getPluginManager(), e.getMessage(), "Contact a developer!", e);
            }
        }
    }

    public boolean exists() {
        return file.exists();
    }

}

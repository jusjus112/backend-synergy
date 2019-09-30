package usa.devrocoding.synergy.proxy.files;

import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;

import java.io.*;
import java.util.HashMap;

public class ProxyYMLFile {

    @Getter
    private Core plugin;
    @Getter
    private String name;
    @Getter
    private File dataFolder, file;

    public ProxyYMLFile(Core plugin, File dataFolder, String name){
        this.plugin = plugin;
        this.name = name;
        this.dataFolder = dataFolder;

        if (!dataFolder.exists()){
            dataFolder.mkdir();
        }
        String fileName = name+".yml";
        File file = new File(dataFolder, fileName);
        try{
            if (!file.exists()) {
                file.createNewFile();
            }
            this.file = file;

        }catch (IOException e){
            Synergy.error(e.getMessage());
        }
    }

    public void set(HashMap<String, Object> data) {
        Configuration config = getConfiguration();
        if (!data.isEmpty()){
            for(String key : data.keySet()){
                if (key.startsWith("#")){
                    continue;
                }
                if (!config.contains(key)){
                    config.set(key, data.get(key));
                }
            }
        }
        save(config);
    }

    public Configuration getConfiguration(){
        try{
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (IOException e){
            Synergy.error(e.getMessage());
        }
        return null;
    }

    public void save(){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfiguration(), file);
        }catch (IOException e){
            Synergy.error(e.getMessage());
        }
    }

    public void save(Configuration configuration){
        try{
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        }catch (IOException e){
            Synergy.error(e.getMessage());
        }
    }

}

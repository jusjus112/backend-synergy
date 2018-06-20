package usa.devrocoding.synergy.spigot.language;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.util.List;

public class Language {

    @Getter
    private String key;
    @Getter
    private YMLFile file;
    @Getter
    private String name;

    public Language(String key, YMLFile file){
        this.key = key;
        this.file = file;
        this.name = key;
    }

    public String get(String key, String defaultName){
        if (file.get().contains(key)){
            return file.get().getString(key);
        }else{
            Core.getPlugin().getLanguageManager().addToAll(key, defaultName);
            return file.get().getString(key);
        }
    }

    public List<String> getList(String key, String[] defaultName){
        if (file.get().contains(key)){
            return file.get().getStringList(key);
        }else{
            Core.getPlugin().getLanguageManager().addToAll(key, defaultName);
            return file.get().getStringList(key);
        }
    }

}

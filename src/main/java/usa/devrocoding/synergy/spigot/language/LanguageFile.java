package usa.devrocoding.synergy.spigot.language;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.util.List;

public class LanguageFile {

    @Getter
    private String key;
    @Getter
    private YMLFile file;
    @Getter
    private String name;

    public LanguageFile(String key, YMLFile file){
        this.key = key;
        this.file = file;
        this.name = key;
    }

    public String get(Language language){
        if (file.get().contains(language.getKey())) {
            return file.get().getString(language.getKey());
        }
        return null;
    }

    public List<String> getList(Language language){
        if (file.get().contains(language.getKey())) {
            return file.get().getStringList(language.getKey());
        }
        return null;
    }

}

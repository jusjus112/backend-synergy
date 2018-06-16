package usa.devrocoding.synergy.spigot.language;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager extends Module {

    private List<Language> languages = new ArrayList<>();

    public LanguageManager(Core plugin){
        super(plugin, "Language Manager");
    }

    public Language getLanguage(String key){
        for (Language language : languages){
            if (language.getKey().equalsIgnoreCase(key)){
                return language;
            }
        }
        return getDefault();
    }

    public Language getDefault(){
        return languages.get(0);
    }

    public void registerLanguage(File file){
        String key = file.getName().split("_")[0];
        Language language = new Language(key, file);
        this.languages.add(language);
    }

    public void registerLanguage(String key, File file){
        Language language = new Language(key, file);
        this.languages.add(language);
    }

}

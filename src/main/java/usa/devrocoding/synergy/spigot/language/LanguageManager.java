package usa.devrocoding.synergy.spigot.language;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.FileStructure;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager extends Module {

    private List<LanguageFile> language_files = new ArrayList<>();

    public LanguageManager(Core plugin){
        super(plugin, "Language Manager", false);
    }

    @Override
    public void onReload(String response) {

    }

    public LanguageFile getLanguage(String key){
        for (LanguageFile language : language_files){
            if (language.getKey().equalsIgnoreCase(key)){
                return language;
            }
        }
        return registerLanguage(key);
    }

    public LanguageFile getDefault(){
        return language_files.get(0);
    }

    public void registerLanguages(Language[] languages){
        File dataFolder = new File(getPlugin().getDataFolder()+"\\lang");
        File[] files = dataFolder.listFiles();
        if (files == null){
            return;
        }

        for(File f : files) {
            try{
                String key = f.getName().split("_")[0];
                YMLFile file = new FileStructure().getYMLFile(key);
                LanguageFile language = new LanguageFile(key, file);
                this.language_files.add(language);
            }catch (FileNotFoundException e){
                Sam.getRobot().error(this, e.getMessage(), "Contact the server developer!", e);
            }
        }

        addToAll(languages);
    }

    public void addToAll(Language[] languages){
        for(LanguageFile language : language_files){
            YMLFile file = language.getFile();
            for(Language language1 : languages){
                if (!file.get().contains(language1.getKey())) {
                    file.get().set(language1.getKey(), language1.getDefault());
                }
            }
        }
    }

    public LanguageFile registerLanguage(String l){
        String fileName = (l.toLowerCase())+"_"+(l.toUpperCase());
        try {
            FileStructure fileStructure = new FileStructure()
                    .add(l, "lang", fileName, FileStructure.FileType.YML);

            LanguageFile language = new LanguageFile(l, fileStructure.getYMLFile(l));
            this.language_files.add(language);
            return language;
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Contact the server developer!", e);
        }
        return getDefault();
    }

}

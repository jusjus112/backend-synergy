package usa.devrocoding.synergy.spigot.language;

import lombok.Getter;
import java.io.File;

public class Language {

    @Getter
    private String key;
    @Getter
    private File file;
    @Getter
    private String name;

    public Language(String key, File file){
        this.key = key;
        this.file = file;
        this.name = key;
    }

}

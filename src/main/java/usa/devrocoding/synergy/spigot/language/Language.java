package usa.devrocoding.synergy.spigot.language;

public interface Language {

    Language[] register();
    String getKey();
    Object getDefault();

}

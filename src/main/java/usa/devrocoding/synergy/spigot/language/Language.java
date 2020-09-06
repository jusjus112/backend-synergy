package usa.devrocoding.synergy.spigot.language;

public interface Language {

    /**
     *
     * @return
     */
    Language[] register();

    /**
     *
     * @return
     */
    String getKey();

    /**
     *
     * @return
     */
    Object getDefault();

}

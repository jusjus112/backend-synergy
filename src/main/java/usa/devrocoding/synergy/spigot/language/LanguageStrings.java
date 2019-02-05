package usa.devrocoding.synergy.spigot.language;

import usa.devrocoding.synergy.spigot.Core;

public enum LanguageStrings implements Language {

    SETTINGS_GUINAME("{P}'s settings"),
    SETTINGS_ITEM_LANGUAGE_NAME(""),
    SETTINGS_ITEM_LANGUAGE_LORE(new String[]{
            "This wil select your server's language.", "This is only for help purposes.",
            "This wil not take effect for", "minigames and gamemodes!", "",
            "Available Languages: \n{AVAILABLE_LANG}"
    }),
    SETTINGS_ITEM_FRIENDS_NAME(""),
    SETTINGS_ITEM_FRIENDS_LORE(new String[]{
            "This wil select your server's language.", "This is only for help purposes.",
            "This wil not take effect for", "minigames and gamemodes!", "",
            "Available Languages: \n{AVAILABLE_LANG}"
    }),
    ;

    public Object defaultObject;

    LanguageStrings(Object defaultObject){
        this.defaultObject = defaultObject;
    }

    @Override
    public Language[] register() {
        return values();
    }

    @Override
    public String getKey() {
        return this.toString().toLowerCase().replaceAll("_", ".");
    }

    @Override
    public Object getDefault() {
        return this.defaultObject;
    }

}

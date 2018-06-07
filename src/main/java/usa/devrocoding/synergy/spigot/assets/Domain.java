package usa.devrocoding.synergy.spigot.assets;

import org.bukkit.ChatColor;

public enum Domain {

    SYNERGY("Synergy", "SG", "minecraft.devrocoding.com", "minecraft.devrocoding.com/pricing", ChatColor.AQUA);

    private String fullName;
    private String shortName;
    private String website;
    private String store;
    private ChatColor serverColor;

    Domain(String fullName, String shortName, String website, String store, ChatColor serverColor) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.website = website;
        this.store = store;
        this.serverColor = serverColor;
    }

//    public static Domain getDomain() {
//        return Domain.valueOf(System.getProperties().getProperty("server"));
//    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getWebsite() {
        return "https://" + website;
    }

    public String getStore() {
        return "https://" + store;
    }

    public ChatColor getServerColor() {
        return serverColor;
    }
}

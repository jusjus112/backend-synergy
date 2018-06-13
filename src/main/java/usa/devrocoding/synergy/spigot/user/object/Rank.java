package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.Core;

public enum Rank {

    NONE(0, "NONE", "", ChatColor.GRAY, "rank.admin"),
    ADMIN(1, "ADMIN", "ADMIN", ChatColor.RED, "rank.admin");

    @Getter
    private final Integer id;
    @Getter
    private final String codeName;
    @Getter
    private final String prefix;
    @Getter
    private final ChatColor color;
    private final String permission;

    Rank(Integer id, String codeName, String prefix, ChatColor color, String permission) {
        this.id = id;
        this.codeName = codeName;
        this.prefix = prefix;
        this.color = color;
        this.permission = permission;
    }

    public String getPermissions(){
        return Core.getPlugin().getManifest().permission_prefix()+permission;
    }

    public static Rank fromName(String codeName) {
        for (Rank rank : values()) {
            if (rank.getCodeName().equals(codeName)) {
                return rank;
            }
        }
        return null;
    }

    public static Rank getFirstStaffRank() {
        return ADMIN;
    }

    public ChatColor getTextColor() {
        if (this.getId() < Rank.ADMIN.getId()) {
            return ChatColor.GRAY;
        }

        return ChatColor.WHITE;
    }
}

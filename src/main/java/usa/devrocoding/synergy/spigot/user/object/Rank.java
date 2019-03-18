package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.Core;

import java.util.List;

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
    private final String node;
    private List<String> permissions;

    Rank(Integer id, String codeName, String prefix, ChatColor color, String node) {
        this.id = id;
        this.codeName = codeName;
        this.prefix = prefix;
        this.color = color;
        this.node = node;
    }

    public String getPermission(){
        return Core.getPlugin().getManifest().permission_prefix()+"."+this.node;
    }

    public static Rank fromName(String codeName) {
        for (Rank rank : values()) {
            if (rank.getCodeName().equals(codeName.toUpperCase())) {
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

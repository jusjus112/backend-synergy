package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.Core;

import java.util.List;

public enum Rank {

    NONE(0, "NONE", "", ChatColor.GRAY, "rank.none"),
    HELPER(1, "HELPER", "HELPER", ChatColor.YELLOW, "rank.helper"),
    JRMODERATOR(2, "JRMODERATOR", "JR. MOD", ChatColor.BLUE, "rank.jrmoderator"),
    SRMODERATOR(3, "SRMODERATOR", "SR. MOD", ChatColor.BLUE, "rank.srmoderator"),
    ADMIN(4, "ADMIN", "ADMIN", ChatColor.RED, "rank.admin"),
    MANAGER(5, "MANAGER", "MANAGER", ChatColor.GOLD, "rank.manager"),
    JRDEVELOPER(6, "JRDEVELOPER", "JR. DEVELOPER", ChatColor.LIGHT_PURPLE, "rank.jrdeveloper"),
    SRDEVELOPER(7, "SRDEVELOPER", "SR. DEVELOPER", ChatColor.RED, "rank.srdeveloper"),
    OWNER(8, "OWNER", "OWNER", ChatColor.RED, "rank.owner");

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

    public static boolean isStaff(SynergyUser synergyUser){
        if (synergyUser.getRank().getId() > getLowestStaffRank().getId()){
            return true;
        }
        return false;
    }

    public boolean isHigherThan(Rank rank){
        return getId() > rank.getId();
    }

    public boolean isHigherThanAndEqualTo(Rank rank){
        return getId() >= rank.getId();
    }

    public boolean isLowerThan(Rank rank){
        return getId() < rank.getId();
    }

    public boolean isLowerThanAndEqualTo(Rank rank){
        return getId() <= rank.getId();
    }

    public static Rank getLowestStaffRank() {
        return HELPER;
    }
    public static Rank getHighestStaffRank() {
        return MANAGER;
    }
    public static Rank getHighestRank() {
        return OWNER;
    }

    public ChatColor getTextColor() {
        if (this.getId() <= -1) {
            return ChatColor.GRAY;
        }
        return ChatColor.WHITE;
    }
}

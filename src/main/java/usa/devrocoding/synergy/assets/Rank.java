package usa.devrocoding.synergy.assets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import usa.devrocoding.synergy.spigot.Core;

import java.util.List;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@RequiredArgsConstructor
public enum Rank {

    NONE(0, "NONE", "", ChatColor.GRAY, "rank.none"),
    PRISONER(1, "PRISONER", "Prisoner", ChatColor.YELLOW, "rank.prisoner"),
    SORCERER(2, "SORCERER", "Sorcerer", ChatColor.LIGHT_PURPLE, "rank.sorcerer"),
    MAGE(3, "MAGE", "Mage", ChatColor.BLUE, "rank.mage"),
    WIZARD(4, "WIZARD", "Wizard", ChatColor.RED, "rank.wizard"),
    IMMORTAL(5, "IMMORTAL", "Immortal", ChatColor.DARK_AQUA, "rank.immortal"),
    MIRAGE(6, "MIRAGE", "Mirage", ChatColor.DARK_PURPLE, "rank.mirage"),
    YOUTUBER(7, "YOUTUBER", "Youtuber", ChatColor.GRAY, "rank.youtuber"),

    HELPER(10, "HELPER", "HELPER", ChatColor.YELLOW, "rank.helper"),
    JRMODERATOR(11, "JRMODERATOR", "MOD", ChatColor.BLUE, "rank.jrmoderator"),
    SRMODERATOR(12, "SRMODERATOR", "MOD", ChatColor.BLUE, "rank.srmoderator"),
    ADMIN(13, "ADMIN", "ADMIN", ChatColor.RED, "rank.admin"),
    MANAGER(14, "MANAGER", "ADMIN", ChatColor.RED, "rank.manager"),
    JRDEVELOPER(15, "JRDEVELOPER", "DEV", ChatColor.LIGHT_PURPLE, "rank.jrdeveloper"),
    SRDEVELOPER(16, "SRDEVELOPER", "DEV", ChatColor.LIGHT_PURPLE, "rank.srdeveloper"),
    OWNER(17, "OWNER", "OWNER", ChatColor.RED, "rank.owner");

    @Getter
    private final Integer id;
    @Getter
    private final String codeName;
    private final String prefix;
    @Getter
    private final ChatColor color;
    private final String node;

    public String getPrefix() {
        if (this == Rank.NONE){
            return this.color + "";
        }
        if (this.getId() < Rank.HELPER.getId()){
            return this.color + "" + this.prefix;
        }
        return this.color + "" + ChatColor.BOLD + this.prefix;
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

    public boolean isStaff(){
        return this.isHigherThanAndEqualTo(Rank.HELPER);
    }

    public boolean isUserRank(){
        return true;
    }

    public static Rank getLowestDonatorRank(){
        return PRISONER;
    }

    public static Rank getHighestDonatorRank(){
        return MIRAGE;
    }

    public static Rank getLowestStaffRank() {
        return HELPER;
    }
    public static Rank getHighestStaffRank() {
        return MANAGER;
    }

    public static Iterator<Rank> getStaffRanks(){
        Rank[] staffRanks = new Rank[]{
            HELPER,JRMODERATOR,SRMODERATOR,ADMIN,MANAGER,JRDEVELOPER,SRDEVELOPER,OWNER
        };
        return Arrays.stream(staffRanks).iterator();
    }

    public static Iterator<Rank> getDonatorRanks(){
        Rank[] donatorRanks = new Rank[]{
            PRISONER,SORCERER,IMMORTAL,MAGE,WIZARD,MIRAGE
        };
        return Arrays.stream(donatorRanks).iterator();
    }

    public static Rank getRankBasedOnPermission(org.bukkit.entity.Player player){
        return Arrays.stream(values())
            .filter(rank -> player.hasPermission(rank.getPermission()))
            .reduce((a, b) -> b)
            .orElse(NONE);
    }

    public static Rank getHighestRank() {
        return OWNER;
    }

    public ChatColor getTextColor() {
        if (this.getId() <= 0) {
            return ChatColor.GRAY;
        }
        return ChatColor.WHITE;
    }
}

package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.potion.PotionEffect;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.bot_sam.object.SamMessage;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;
import usa.devrocoding.synergy.spigot.utilities.UtilSound;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.util.*;

public class SynergyUser {

    @Getter
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private Rank rank;
    @Getter
    private LanguageFile language;
    @Getter @Setter
    private List<Punishment> punishments = new ArrayList<>();
    @Getter @Setter
    private double networkXP = 0D;
    @Getter @Setter
    private UserExperience userExperience = UserExperience.NOOB;
    @Getter @Setter
    private List<Achievement> achievements = new ArrayList<>();
    @Getter
    private UserLoadEvent.UserLoadType loadType;
    @Getter
    private boolean nicked = false;

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language, UserLoadEvent.UserLoadType loadType, boolean save){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.language = language;
        this.loadType = loadType;

        if (save) {
            Core.getPlugin().getUserManager().getUsers().put(uuid, this);
        }
    }

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language, UserLoadEvent.UserLoadType loadType){
        this(uuid, name, rank, language, loadType, true);
    }

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language){
        this(uuid, name, rank, language, UserLoadEvent.UserLoadType.NEW_INSTANCE, true);
    }

    public boolean hasRank(Rank rank){
        return this.rank == rank;
    }

    public void delete(){
        if (getPlayer() != null && getPlayer().isOnline()){
            return;
        }
        Core.getPlugin().getUserManager().getUsers().remove(getUuid());
    }

    public void addNetworkXP(double xp){
        this.networkXP += xp;
    }

    public Player getPlayer() {
        return Bukkit.getServer().getPlayer(getUuid());
    }

    public void message(String... messages){
        Arrays
            .stream(messages)
            .forEach(s ->
                getPlayer()
                    .sendMessage(
                        C.translateColors(
                            s)));
    }

    public void sendToServer(String server){
        Core.getPlugin().getPluginMessagingManager().sendPlayerToServer(getPlayer(), server);
    }

    public void nick(String name){
        this.nicked = Core.getPlugin().getNickManager().nickPlayer(getPlayer(), name);
    }

    public void unNick(){
        if (this.nicked) {
            this.nicked = false;
            Core.getPlugin().getNickManager().nickPlayer(getPlayer(), getName());
        }
    }

    public void clean(){
        Player player = getPlayer();

        player.getInventory().clear();
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.closeInventory();
        player.setGameMode(GameMode.SURVIVAL);
        player.setFlying(false);
        player.setAllowFlight(false);
        player.setExp(0);
        player.setFlySpeed(1);
        player.setFireTicks(0);

        for(PotionEffect potionEffect : player.getActivePotionEffects()){
            player.removePotionEffect(potionEffect.getType());
        }
    }

    public void teleport(SynergyUser target){
        //TODO: Add a fix so they can't teleport out of combat and add several checks for combat tag e.t.c.
        // And make a function with a countdown..
        getPlayer().teleport(target.getPlayer());
    }

    public void info(String... messages){
        Sam.getRobot().info(getPlayer(), messages);
    }

    public void sam(SamMessage samMessage){
        Sam.getRobot().sam(getPlayer());
    }

    public void error(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }

    public void warning(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }

    public void sendModifactionMessage(MessageModification modification, String... messages){
        switch (modification){
            case CENTERED:
                Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(UtilString.centered(s)));
                break;
            default:
                Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(s));
                break;
        }
    }

    @Deprecated
    public void sendModifactionMessage(MessageModification modification, List<String> messages){
        switch (modification){
            case CENTERED:
                messages.forEach(s -> getPlayer().sendMessage(UtilString.centered(s)));
                break;
            default:
                messages.forEach(s -> getPlayer().sendMessage(s));
                break;
        }

    }

    public void updatePunishments(){
        Synergy.debug("3 UPDATE_PUNISHMENTS");
        Core.getPlugin().getRunnableManager().runTaskAsynchronously("update Punishments", core -> {
            this.punishments = Core.getPlugin().getPunishManager().retrievePunishments(this.uuid);
            Synergy.debug("4 UPDATE_PUNISHMENTS");
            for (Punishment punishment : this.punishments) {
                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.getType().name());
                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.isActive());
                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.getIssued());

                if (punishment.getType() == PunishType.BAN && punishment.isActive()) {
                    Synergy.debug("6 UPDATE_PUNISHMENTS");
                    Core.getPlugin().getPluginMessagingManager().kick(getName(), punishment.getBanMessage());
//                getPlayer().kickPlayer(punishment.getBanMessage());
                    return;
                }
            }
        });
    }

    public UtilSound getSoundControl(){
        return new UtilSound(this);
    }

    public boolean hasPermission(String node){
        String p = Core.getPlugin().getManifest().permission_prefix()+"."+node;
        if (getPlayer().hasPermission(p)){
            return true;
        }else{
            error(SamMessage.NO_PERMISSIONS.getRandom());
            return false;
        }
    }

    public UtilDisplay getDisplay(){
        return Core.getPlugin().getGlobalManager().getUtilDisplay(getPlayer());
    }

    public boolean hasPermission(String node, boolean message){
        String p = Core.getPlugin().getManifest().permission_prefix()+"."+node;
        if (getPlayer().hasPermission(p)){
            return true;
        }else{
            if (message) {
                error(SamMessage.NO_PERMISSIONS.getRandom());
            }
            return false;
        }
    }

    public void unlockAchievement(Achievement achievement){
        if (!hasAchievement(achievement)){
            getAchievements().add(achievement);
        }
    }

    public boolean hasAchievement(Achievement achievement){
        return getAchievements().contains(achievement);
    }
}
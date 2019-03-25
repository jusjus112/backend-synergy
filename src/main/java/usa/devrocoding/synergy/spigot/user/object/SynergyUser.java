package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.bot_sam.object.SamMessage;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
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
//    @Setter
//    private PermissionAttachment permissions;
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

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language, UserLoadEvent.UserLoadType loadType){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.language = language;
        this.loadType = loadType;

//        if (permissions != null){
//            this.permissions = permissions;
//        }
        Core.getPlugin().getUserManager().getUsers().put(uuid, this);
    }

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language){
        this(uuid, name, rank, language, UserLoadEvent.UserLoadType.NEW_INSTANCE);
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

    public void teleport(SynergyUser target){
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

    public void sendRawMessage(MessageModification modification, String... messages){
        switch (modification){
            case CENTERED:
                Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(UtilString.centered(s)));
                break;
            default:
                Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(s));
                break;
        }

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

//    public void addPermissionNode(String node){
//        this.permissions.setPermission(node, true);
//    }
//
//    public void removePermissionNode(String node){
//        this.permissions.unsetPermission(node);
//    }
//
//    public Map<String, Boolean> getPermissions(){
//        return this.permissions.getPermissions();
//    }
}
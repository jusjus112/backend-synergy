package usa.devrocoding.synergy.spigot.user.object;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.botsam.object.SamMessage;
import usa.devrocoding.synergy.spigot.economy.object.Economy;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardPolicy;
import usa.devrocoding.synergy.spigot.scoreboard.ZylemBoard;
import usa.devrocoding.synergy.spigot.statistics.object.Statistic;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;
import usa.devrocoding.synergy.spigot.utilities.UtilSound;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.util.*;

@Getter
public class SynergyUser {

    /**
     * TODO:
     * - Add a gameprofile, as replacement for uuid and name
     * - Change this user object to a interface. Make this implement humanEntity or something.
     */

    private final UUID uuid;
    private final String name;
    private Rank rank;
    private final LanguageFile language;
    @Setter
    private List<Punishment> punishments;
    @Setter
    private UserExperience userExperience;
    @Setter
    private Map<Achievement, Timestamp> achievements;
    private Map<Objective, Timestamp> objectives;
    private Economy economy;
    private final Map<Objective, Date> objectivesToBeUpdated;
    @Setter
    private Objective currentObjective;
    private final Map<Achievement, Date> achievementsToBeUpdated;
    @Setter
    private Map<StatisticType, Statistic> statistics;
    private UserLoadEvent.UserLoadType loadType = UserLoadEvent.UserLoadType.RETRIEVED_FROM_DATABASE;
    private String nickname = null;
    @Setter
    private boolean ableToLoad;
    @Setter
    private String authKey;

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language, UserLoadEvent.UserLoadType loadType, boolean save){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.language = language;
        this.punishments = Lists.newArrayList();
        this.userExperience = UserExperience.NOOB;
        this.achievements = Maps.newHashMap();
        this.statistics = Maps.newHashMap();
        this.economy = new Economy(new BigDecimal(0), 0, 0L);
        this.achievementsToBeUpdated = Maps.newHashMap();
        this.objectives = Maps.newHashMap();
        this.objectivesToBeUpdated = Maps.newHashMap();
        this.loadType = loadType;

        if (save) {
            Core.getPlugin().getUserManager().getUsers().put(uuid, this);
        }
    }

    public void setEconomy(Economy economy) {
        economy.setSynergyUser(this);
        this.economy = economy;
    }

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language, UserLoadEvent.UserLoadType loadType){
        this(uuid, name, rank, language, loadType, true);
    }

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language){
        this(uuid, name, rank, language, UserLoadEvent.UserLoadType.NEW_INSTANCE, true);
    }

    public void updateDatabase(boolean quitEvent){
        Synergy.debug("CHECKING USER IS ONLINE...");
        Synergy.debug(getDisplayName() + " = CHECKING USER IS ONLINE...");
        Synergy.debug(!quitEvent + " = CHECKING USER IS ONLINE...");
        Synergy.debug(this.isOnline() + " = CHECKING USER IS ONLINE...");
        if (!quitEvent && this.isOnline()){
            Synergy.debug("USER IS ONLINE...");
            return;
        }
        Synergy.debug("USER IS OffLINE...");
        Core.getPlugin().getUserManager().updateUserTable(this);
        Core.getPlugin().getAchievementManager().saveAchievementsToDatabase(this);
        Core.getPlugin().getObjectiveManager().saveObjectivesToDatabase(this);
        Core.getPlugin().getUserManager().getUsers().remove(this.uuid);
        Core.getPlugin().getStatisticManager().saveStatisticsForUser(this);
        Core.getPlugin().getEconomyManager().updateEconomyForUser(this);
    }

    public void updateDatabase(){
        this.updateDatabase(false);
    }

    public void attemptLoading(){
        if (getPlayer() != null) {// Only load when player is not null
            UserLoadEvent loadEvent = new UserLoadEvent(this, this.loadType);
            Core.getPlugin().getServer().getPluginManager().callEvent(
                loadEvent
            );

            this.rank = Rank.getRankBasedOnPermission(getPlayer());

            getPlayer().setDisplayName(getDisplayName());

            // Scoreboard stuff
            if (loadEvent.getScoreboardPolicy() != null) {
                Core.getPlugin().getScoreboardManager()
                    .getScoreboards().put(
                    this.uuid, new ZylemBoard(Core.getPlugin(), this,
                        loadEvent.getScoreboardPolicy())
                );
                Core.getPlugin().getScoreboardManager().update(true);
            }
        }
    }

    public boolean isOnline(){
        return getPlayer() != null && getPlayer().isOnline();
    }

    public boolean hasRank(Rank rank){
        return this.rank == rank;
    }

    public boolean needs2FA(){
        return this.rank.isHigherThanAndEqualTo(Rank.JRMODERATOR) && !hasFilledIn2FA() && Synergy.isProduction();
    }

    public boolean hasFilledIn2FA(){
        return Core.getPlugin().getGoogleAuthManager().getFilledInPlayers().contains(this.uuid);
    }

    public boolean has2FAKey(){
        return this.authKey != null;
    }

    public void setScoreboardPolicy(ScoreboardPolicy scoreboardPolicy){
        Core.getPlugin().getScoreboardManager().setScoreboardPolicy(scoreboardPolicy, this);
    }

    public void delete(){
        if (getPlayer() != null && getPlayer().isOnline()){
            return;
        }
        // TODO: Remove also from database
        Core.getPlugin().getUserManager().getUsers().remove(getUuid());
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
        this.nickname = name;
        try{
            Core.getPlugin().getNickManager().nickPlayer(getPlayer(), name);
            getPlayer().setDisplayName(getDisplayName());
        }catch (Exception e){
            error("Something went wrong setting your nickname!");
        }
    }

    public boolean isNicked(){
        return this.nickname != null;
    }

    public void unNick(){
        if (this.isNicked()) {
            this.nickname = null;
            Core.getPlugin().getNickManager().nickPlayer(getPlayer(), getName());
            getPlayer().setDisplayName(getDisplayName());
        }
    }

    public String getDisplayName(){
        if (this.isNicked()){
            return Rank.NONE.getTextColor() + this.nickname;
        }
        if (this.rank == Rank.NONE){
            return this.rank.getTextColor() + this.getName();
        }
        return this.rank.getPrefix() + " " + this.rank.getTextColor() + this.getName() + ChatColor.RESET;
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

    public void heal(){
        Player player = getPlayer();

        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        player.setFireTicks(0);
    }

    public void teleport(SynergyUser target){
        //TODO: Add a fix so they can't teleport out of combat and add several checks for combat tag e.t.c.
        // And make a function with a countdown..
        getPlayer().teleport(target.getPlayer());
    }

    @Deprecated
    public void info(String... messages){
        Sam.getRobot().info(getPlayer(), messages);
    }

    @Deprecated
    public void important(String... messages){
        Sam.getRobot().important(getPlayer(), messages);
    }

    @Deprecated
    public void announcement(String... messages){
        Sam.getRobot().announcement(getPlayer(), messages);
    }

    @Deprecated
    public void sam(SamMessage samMessage){
        Sam.getRobot().sam(getPlayer());
    }

    @Deprecated
    public void error(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }

    @Deprecated
    public void warning(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }

    @Deprecated
    public void sendModifactionMessage(MessageModification modification, String... messages){
        switch (modification){
            case CENTERED:
                Synergy.debug("USER UUID MODIF = " + getUuid());
                Synergy.debug("USER PLAYER MODIF = " + getPlayer());
                Arrays.stream(messages).forEach(s ->
                    getPlayer().
                        sendMessage(
                            UtilString.centered(s)));
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
//        Synergy.debug("3 UPDATE_PUNISHMENTS");
        Core.getPlugin().getRunnableManager().runTaskAsynchronously("update Punishments", core -> {
            this.punishments = Core.getPlugin().getPunishManager().retrievePunishments(this.uuid);
//            Synergy.debug("4 UPDATE_PUNISHMENTS");
            for (Punishment punishment : this.punishments) {
//                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.getType().name());
//                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.isActive());
//                Synergy.debug("5 UPDATE_PUNISHMENTS = "+punishment.getIssued());

                // TODO: If statement is false for some reason
                if (punishment.getType() == PunishType.BAN && punishment.isActive()) {
//                    Synergy.debug("6 UPDATE_PUNISHMENTS");
                    Core.getPlugin().getPluginMessagingManager().kick(getName(), punishment.getBanMessage(
                        Core.getPlugin().getManifest().server_name()));
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
        return this.hasPermission(node, true);
    }

    public UtilDisplay getDisplay(){
        return Core.getPlugin().getGlobalManager().getUtilDisplay(getPlayer());
    }

    public boolean hasPermission(String node, boolean message){
        String p = Core.getPlugin().getManifest().permission_prefix()+"."+node;
        if (getPlayer().hasPermission(p) || this.rank == Rank.getHighestRank()){
            return true;
        }else{
            if (message) {
                this.sendNoPermissions();
            }
            return false;
        }
    }

    public void sendNoPermissions(){
        error(SamMessage.NO_PERMISSIONS.getRandom());
    }

    public void unlockAchievement(Achievement achievement){
        if (!hasAchievement(achievement)){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            this.achievements.put(achievement, timestamp);
            this.achievementsToBeUpdated.put(achievement, timestamp);
        }
    }

    public void unlockObjective(Objective objective){
        if (!hasObjective(objective)){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            this.objectives.put(objective, timestamp);
            this.objectivesToBeUpdated.put(objective, timestamp);
        }
    }

    public boolean hasAchievement(Achievement achievement){
        return getAchievements().containsKey(achievement);
    }

    public boolean hasAchievement(Class<? extends Achievement> achievementClass){
        return this.achievements.keySet().stream().anyMatch(achievement ->
            achievement.getClass() == achievementClass);
    }

    public boolean hasCurrentObjective(){
        return this.currentObjective != null;
    }

    public void setObjectives(Map<Objective, Timestamp> objectives){
        this.objectives = objectives;
        Synergy.debug("STARTED MOST RECENT OBJECTIVE");
        Core.getPlugin().getObjectiveManager().startMostRecentObjective(this);
//        objectives.forEach((objective, date) -> {
//            if (date == null) {
//                if (hasObjective(objective.getUnlockFirst())) {
//                    setCurrentObjective(objective);
//                }
//            }
//        });
    }

    public void kick(String reason){
        getPlayer().kickPlayer(reason);
    }

    public Statistic getStatistic(StatisticType statisticType){
        return this.statistics.get(statisticType);
    }

    public boolean hasObjective(Objective objective){
        return this.objectives.containsKey(objective);
    }

    public boolean hasObjective(Class<? extends Objective> objectiveClass){
        return this.objectives.keySet().stream().anyMatch(objective -> objective.getClass() == objectiveClass);
    }
}
package usa.devrocoding.synergy.spigot.user;

import com.google.common.collect.Maps;
import java.sql.Timestamp;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.economy.object.Economy;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.statistics.object.Statistic;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.user.command.CommandHome;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.listener.UserChatListener;
import usa.devrocoding.synergy.spigot.user.listener.UserJoinListener;
import usa.devrocoding.synergy.spigot.user.listener.UserQuitListener;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserManager extends Module {

    @Getter
    private final Map<UUID, SynergyUser> users = Maps.newHashMap();

    public UserManager(Core plugin){
        super(plugin, "User Manager", false);

        registerListener(
            new UserJoinListener(),
            new UserQuitListener(),
            new UserChatListener()
        );

        registerCommand(
            new CommandHome(getPlugin())
        );
    }

    @Override
    public void reload(String response) {

    }

    public Collection<SynergyUser> getOnlineUsers(){
        if (this.users != null && !this.users.isEmpty()) {
            return this.users.values();
        }
        return Collections.EMPTY_LIST;
    }

    public SynergyUser getFakeUser(String name){
        SynergyUser synergyUser = this.getUser(name, false);
        if (synergyUser != null){
            return synergyUser;
        }
        return retrievePlayer(loadFromUsername(name), false);
    }

    public SynergyUser getUser(String name, Boolean database) {
        for (SynergyUser user : users.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        if (database){
            return retrievePlayer(loadFromUsername(name), true);
        }
        return null;
    }

    public SynergyUser getUser(Player player) {
        return getUser(player.getUniqueId());
    }

    public SynergyUser getUser(UUID uuid) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        }
        return null;
    }

    public ResultSet loadFromUUID(UUID uuid){
        try{
            return Core.getPlugin().getDatabaseManager().getResults(
                    "users ", "uuid=?", new HashMap<Integer, Object>(){{
                        put(1, UtilSQL.convertUniqueId(uuid));
                    }}
            );
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet loadFromUsername(String name){
        try{
            return Core.getPlugin().getDatabaseManager().getResults(
                    "users ", "name=?", new HashMap<Integer, Object>(){{
                        put(1, name);
                    }}
            );
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public SynergyUser retrievePlayer(ResultSet resultSet, boolean save){
        if (resultSet != null) {
            final SynergyUser user;
            Rank rank = Rank.NONE;
            Timestamp joinedOn;
            List<Punishment> punishments;
            Map<Achievement, Timestamp> achievements;
            Map<Objective, Timestamp> objectives;
            Map<StatisticType, Statistic> statistics;
            Economy economy;

            try{
                if (resultSet.next()) {
                    Synergy.debug("USER LOADING...");
                    UUID uuid = UtilSQL.convertBinaryStream(resultSet.getBinaryStream("uuid"));
                    Synergy.debug(uuid + " = UUID");
                    Synergy.debug(uuid.toString() + " = UUID");
                    joinedOn = resultSet.getTimestamp("joined_on");

                    if (Rank.fromName(resultSet.getString("rank")) != null) {
                        Synergy.debug("USER HAS RANK...");
                        rank = Rank.fromName(resultSet.getString("rank"));
//                        Synergy.debug(rank.getCodeName());
                    }

                    punishments = getPlugin().getPunishManager().retrievePunishments(uuid);
                    achievements = getPlugin().getAchievementManager().retrieveAchievements(uuid);
                    objectives = getPlugin().getObjectiveManager().retrieveForPlayer(uuid);
                    statistics = getPlugin().getStatisticManager().retrieveStatistics(uuid);
                    economy = getPlugin().getEconomyManager().retrieveEconomy(uuid);

                    Synergy.debug(statistics + " = STATS");

                    user = new SynergyUser(
                        uuid,
                        resultSet.getString("name"),
                        rank,
                        Core.getPlugin().getLanguageManager().getLanguage("en"),
                        UserLoadEvent.UserLoadType.RETRIEVED_FROM_DATABASE,
                        save
                    );

                    user.setJoinedOn(joinedOn);
                    user.setPunishments(punishments);
                    user.setAchievements(achievements);
                    user.setStatistics(statistics);
                    user.setEconomy(economy);
                    user.setObjectives(objectives);

                    if (user.needs2FA()) {
                        Core.getPlugin().getGoogleAuthManager()
                            .getUserStuffFromDatabase(uuid, user);
                    }

                    resultSet.close();
                    Core.getPlugin().getDatabaseManager().disconnect();
                    return user;
                }else{
                    Synergy.debug("RESULT = EMPTY");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public void cacheExisitingPlayers(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            new SynergyUser(p.getUniqueId(), p.getName(), Rank.NONE,
                    Core.getPlugin().getLanguageManager().getLanguage("en"));
        }
    }

    public void updateUserTable(SynergyUser synergyUser){
        Core.getPlugin().getRunnableManager().runTaskAsynchronously(
            "Update User",
            core -> {
                HashMap<String, Object> data = new HashMap<String, Object>() {{
                    put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                    put("name", synergyUser.getName());
                    put("rank", synergyUser.getRank().toString().toUpperCase());
                    put("joined_on", synergyUser.getJoinedOn());
                    put("user_experience", synergyUser.getUserExperience().toString().toUpperCase());
                }};
                Synergy.debug(data + " = UPDATE USER MAP");
                if (!getPlugin().getDatabaseManager().insert("users", data)) {
                    Core.getPlugin().getDatabaseManager().update(
                        "users",
                        data,
                        new HashMap<String, Object>() {{
                            put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                        }}
                    );
                }
            }
        );
    }

}

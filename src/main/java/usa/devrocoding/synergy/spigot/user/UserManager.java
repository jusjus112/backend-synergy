package usa.devrocoding.synergy.spigot.user;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
import usa.devrocoding.synergy.spigot.punish.PunishCategory;
import usa.devrocoding.synergy.spigot.punish.PunishLevel;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.command.CommandHome;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.listener.UserChatListener;
import usa.devrocoding.synergy.spigot.user.listener.UserJoinEvent;
import usa.devrocoding.synergy.spigot.user.listener.UserQuitEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
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
            new UserJoinEvent(),
            new UserQuitEvent(),
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

    public SynergyUser getUser(String name, Boolean database) {
        for (SynergyUser user : users.values()) {
            if (user.getName().toLowerCase().equals(name.toLowerCase())) {
                return user;
            }
        }
        if (database){
            return retrievePlayer(loadFromUsername(name));
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
                        put(1, uuid.toString());
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

    public SynergyUser retrievePlayer(ResultSet resultSet){
        if (resultSet != null) {
            final SynergyUser user;
            double experience;
            Rank rank = Rank.NONE;
            List<Punishment> punishments;

            try{
                if (resultSet.next()) {
                    experience = resultSet.getDouble("xp");
                    if (Rank.fromName(resultSet.getString("rank")) != null) {
                        rank = Rank.fromName(resultSet.getString("rank"));
                    }

                    punishments = getPlugin().getPunishManager().retrievePunishments(UUID.fromString(resultSet.getString("uuid")));

                    user = new SynergyUser(
                            UUID.fromString(resultSet.getString("uuid")),
                            resultSet.getString("name"),
                            rank,
                            Core.getPlugin().getLanguageManager().getLanguage("en"),
                            UserLoadEvent.UserLoadType.RETRIEVED_FROM_DATABASE
                    );
                    user.setNetworkXP(experience);
                    user.setPunishments(punishments);

                    resultSet.close();
                    Core.getPlugin().getDatabaseManager().disconnect();
                    return user;
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

    public void updateUser(SynergyUser synergyUser){
        Core.getPlugin().getRunnableManager().runTaskAsynchronously(
            "Add User",
            core -> {
                HashMap<String, Object> data = new HashMap<String, Object>() {{
                    put("uuid", synergyUser.getUuid().toString());
                    put("name", synergyUser.getName());
                    put("xp", synergyUser.getNetworkXP());
                    put("rank", synergyUser.getRank().toString().toUpperCase());
                    put("user_experience", synergyUser.getUserExperience().toString().toUpperCase());
                }};
                if (synergyUser.getLoadType() == UserLoadEvent.UserLoadType.NEW) {
                    getPlugin().getDatabaseManager().insert("users", data);
                }else{
                    Core.getPlugin().getDatabaseManager().update("users", data, "uuid = '"+synergyUser.getUuid().toString()+"'");
                }
            }
        );
    }

}

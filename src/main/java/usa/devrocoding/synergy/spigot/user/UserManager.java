package usa.devrocoding.synergy.spigot.user;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.user.listener.UserJoinEvent;
import usa.devrocoding.synergy.spigot.user.listener.UserQuitEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager extends Module {

    @Getter
    private final Map<UUID, SynergyUser> users = Maps.newHashMap();

    public UserManager(Core plugin){
        super(plugin, "User Manager", false);

        registerListener(
            new UserJoinEvent(),
            new UserQuitEvent()
        );
    }

    @Override
    public void reload(String response) {

    }

    public SynergyUser getUser(Player player) {
        return getUser(player.getUniqueId(), false);
    }

    public SynergyUser getUser(String name, Boolean database) {
        for (SynergyUser user : users.values()) {
            if (user.getName().toLowerCase().equals(name.toLowerCase())) {
                return user;
            }
        }
        return null;
    }

    public SynergyUser getUser(Player player, Boolean database) {
        return getUser(player.getUniqueId(), database);
    }

    public SynergyUser getUser(UUID uuid, Boolean database) {
        if (users.containsKey(uuid)) {
            return users.get(uuid);
        }
        if (database){
            return getFromDB(uuid);
        }
        return null;
    }

    private SynergyUser getFromDB(UUID uuid){
        try{
            ResultSet resultSet = Core.getPlugin().getDatabaseManager().getResults(
                    "SELECT * FROM synergy_users WHERE uuid='"+uuid+"'"
            );
            final boolean next = resultSet.next();
            if (next){
                SynergyUser synergyUser = new SynergyUser(
                    UUID.fromString(resultSet.getString("uuid"))      ,
                    resultSet.getString("name"),
                    Rank.NONE,
                    Core.getPlugin().getLanguageManager().getLanguage("en")
                );
                synergyUser.setNetworkXP(resultSet.getDouble("xp"));
                return synergyUser;
            }
        }catch (SQLException e){
            Synergy.error(e.getMessage());
        }
        return null;
    }

    public void cacheExisitingPlayers(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            new SynergyUser(p.getUniqueId(), p.getName(), Rank.NONE,
                    Core.getPlugin().getLanguageManager().getLanguage("en"));
        }
    }

    public boolean updateUser(SynergyUser user){
        return Core.getPlugin().getDatabaseManager().update("users", new HashMap<String, Object>(){{
            put("userexperience", user.getUserExperience().toString().toUpperCase());
            put("xp", user.getNetworkXP());
        }}, "uuid = '"+user.getUuid().toString()+"'");
    }

    public void addUserToDatabase(SynergyUser synergyUser){
        Core.getPlugin().getDatabaseManager().execute("users", new HashMap<String, Object>(){{
            put("uuid", synergyUser.getUuid().toString());
            put("name", synergyUser.getName());
            put("userexperience", synergyUser.getUserExperience().toString().toUpperCase());
        }});
    }

}

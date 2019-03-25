package usa.devrocoding.synergy.spigot.user;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.user.command.CommandHome;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
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
            new UserQuitEvent()
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
                    put("rank", synergyUser.getRank().toString());
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

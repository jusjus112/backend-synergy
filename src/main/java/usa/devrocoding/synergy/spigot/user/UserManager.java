package usa.devrocoding.synergy.spigot.user;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Map;
import java.util.UUID;

public class UserManager extends Module {

    private final Map<UUID, SynergyUser> users = Maps.newHashMap();

    public UserManager(Core plugin){
        super(plugin, "User Manager");
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
        return null;
    }

}

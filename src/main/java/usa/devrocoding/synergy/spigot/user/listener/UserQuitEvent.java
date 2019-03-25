package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserQuitEvent implements Listener {

    @EventHandler
    public void onUserQuit(PlayerQuitEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer());

        if (user != null) {
            Core.getPlugin().getUserManager().updateUser(user);
            Core.getPlugin().getUserManager().getUsers().remove(user.getUuid());
        }
    }

}

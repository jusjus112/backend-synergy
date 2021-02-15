package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.event.UserQuitEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserQuitListener implements Listener {

    @EventHandler
    public void onUserQuit(PlayerQuitEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer());

        e.setQuitMessage(null);
        if (user != null) {
            user.updateDatabase(true);

            Core.getPlugin().getServer().getPluginManager().callEvent(new UserQuitEvent(user, e.getPlayer()));
        }
    }

}

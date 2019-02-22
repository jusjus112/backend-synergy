package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.spigot.Core;

public class UserQuitEvent implements Listener {

    @EventHandler
    public void onUserQuit(PlayerQuitEvent e){
        Core.getPlugin().getUserManager().getUsers().remove(e.getPlayer().getUniqueId());
    }

}

package usa.devrocoding.synergy.spigot.two_factor_authentication.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class PlayerMoveListener implements Listener {

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e){
    if(e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
      SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());

      if (synergyUser.needs2FA()) {
        Synergy.debug("CALLING THIS SHIT");
        e.setCancelled(true);
      }
    }
  }

}

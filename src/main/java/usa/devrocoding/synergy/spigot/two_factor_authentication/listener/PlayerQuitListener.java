package usa.devrocoding.synergy.spigot.two_factor_authentication.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class PlayerQuitListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent e){
    SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());

    if (!synergyUser.hasFilledIn2FA()){
      Core.getPlugin().getGoogleAuthManager().restore(synergyUser);
    }
  }

}

package usa.devrocoding.synergy.spigot.two_factor_authentication.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.command.event.SynergyUserExecuteCommandEvent;

public class PlayerExecuteCommandListener implements Listener {

  @EventHandler
  public void onUserExecuteCommand(SynergyUserExecuteCommandEvent e){
    if (e.getSynergyUser().needs2FA()) {
      e.setCancelled(true);
    }
  }

}

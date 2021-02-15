package usa.devrocoding.synergy.spigot.two_factor_authentication.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.user.event.SynergyUserChatEvent;

public class PlayerChatEvent implements Listener {

  @EventHandler
  public void onUserChat(SynergyUserChatEvent e){
    if (e.getSynergyUser().needs2FA()){
      e.setCancelled(true);
      e.getSynergyUser().error("Your chat is disabled! Finish the 2FA first!");
    }
  }

}

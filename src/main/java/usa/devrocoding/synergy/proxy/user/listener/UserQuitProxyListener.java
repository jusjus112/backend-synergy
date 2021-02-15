package usa.devrocoding.synergy.proxy.user.listener;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

public class UserQuitProxyListener implements Listener {

  // TODO: remove user form list/map
  @EventHandler(priority = EventPriority.LOWEST)
  public void onLogin(PlayerDisconnectEvent e){
    ProxyUser proxyUser = Core.getCore().getUserManager().getUser(e.getPlayer().getUniqueId());

    Core.getCore().getBuddyManager().updateFriendsForUser(proxyUser);
    Core.getCore().getUserManager().getProxyUsers().remove(proxyUser.getUuid());
  }

}

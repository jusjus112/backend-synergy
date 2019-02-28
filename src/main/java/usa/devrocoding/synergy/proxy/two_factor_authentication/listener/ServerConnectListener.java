package usa.devrocoding.synergy.proxy.two_factor_authentication.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.two_factor_authentication.GoogleAuthManager;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onLogin(ServerConnectedEvent e){

    }

}

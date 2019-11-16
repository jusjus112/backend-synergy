package usa.devrocoding.synergy.proxy.assets.listener;

import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onPluginMessage(ServerConnectEvent e){
        Synergy.debug(e.getReason().toString());
    }

}

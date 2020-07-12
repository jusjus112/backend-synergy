package usa.devrocoding.synergy.proxy.maintenance.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

import java.util.concurrent.TimeUnit;

public class LoginListener implements Listener {

    @EventHandler
    public void onPluginMessage(PostLoginEvent e){
        MaintenanceManager maintenanceManager = Core.getCore().getMaintenanceManager();
        if (maintenanceManager.isServerOnMaintenance("proxy")){
            if (!e.getPlayer().hasPermission("synergy.maintenance.override")) {
                // TODO: Fix, will give errors on game servers when on maintenance
                e.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', (String)maintenanceManager.getMotd().get("kick_message"))));
            }
        }
    }

}

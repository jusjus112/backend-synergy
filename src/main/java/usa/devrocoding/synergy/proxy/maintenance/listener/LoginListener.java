package usa.devrocoding.synergy.proxy.maintenance.listener;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

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
        List<String> offlineServers = Core.getCore().getAssetManager().getOfflineServers();
        if (offlineServers.size() > 0){
            e.getPlayer().disconnect(new TextComponent(ChatColor.RED +
                "Server is still RESTARTING..\n"+
                "Please allow the server to boot up.\n\n"+
                ChatColor.GRAY+"[REQUEST]: HTTP 102 Data Received No Response Yet"));
        }
    }

}

package usa.devrocoding.synergy.proxy.maintenance.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onPluginMessage(ServerConnectEvent e){
        MaintenanceManager maintenanceManager = Core.getCore().getMaintenanceManager();
        Synergy.debug("SERVER CONNECT = "+e.getReason());
        Synergy.debug("SERVER CONNECT = "+e.getTarget().getName());
        if (e.getReason() == ServerConnectEvent.Reason.JOIN_PROXY){
            if (maintenanceManager.isServerOnMaintenance("proxy")){
                if (!e.getPlayer().hasPermission("synergy.maintenance.override")) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (maintenanceManager.isServerOnMaintenance(e.getTarget().getName())){
            if (!e.getPlayer().hasPermission("synergy.maintenance.override")) {
                e.setCancelled(true);
                if (e.getPlayer().getServer() == null){
                    e.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', (String)maintenanceManager.getMotd().get("kick_message"))));
                    return;
                }
                e.getPlayer().sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName(ChatColor.GRAY)));
                e.getPlayer().sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', (String)maintenanceManager.getMotd().get("kick_message"))));
                e.getPlayer().sendMessage(new TextComponent(Synergy.SynergyColor.getLine()));
            }
        }
    }

}

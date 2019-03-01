package usa.devrocoding.synergy.proxy.maintenance.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

import java.util.concurrent.TimeUnit;

public class LoginListener implements Listener {

    @EventHandler
    public void onPluginMessage(PostLoginEvent e){
        MaintenanceManager maintenanceManager = Core.getCore().getMaintenanceManager();

        if (maintenanceManager.isServerOnMaintenance("proxy")){
            if (e.getPlayer().getUniqueId().toString().equals("c1bbd6fc-542d-48cf-9f94-954896a18e2a")){
                return;
            }

            if (!e.getPlayer().hasPermission("synergy.maintenance.override")) {
                Core.getCore().getProxy().getScheduler().schedule(Core.getCore(), new Runnable() {
                    @Override
                    public void run() {
                        e.getPlayer().disconnect(new TextComponent(ChatColor.translateAlternateColorCodes('&', maintenanceManager.getMotd().get("kick_message"))));
                    }
                }, 1, TimeUnit.SECONDS);
            }
        }
    }

}

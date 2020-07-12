package usa.devrocoding.synergy.proxy.maintenance.listener;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.assets.utils.UtilMOTD;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;

public class ProxyPingListener implements Listener {

    @EventHandler
    public void onPluginMessage(ProxyPingEvent e){
        MaintenanceManager maintenanceManager = Core.getCore().getMaintenanceManager();

        if (maintenanceManager.isServerOnMaintenance("proxy")) {
            e.getResponse().getVersion().setProtocol(1);
            e.getResponse().getVersion().setName((String)maintenanceManager.getMotd().get("version"));

            e.getResponse().setDescriptionComponent(new TextComponent(UtilMOTD.getCenteredMOTD(
                    (String)maintenanceManager.getMotd().get("motd.first_line"),
                    (String)maintenanceManager.getMotd().get("motd.second_line")
            )));
            e.getResponse().getPlayers().setOnline(0);
            e.getResponse().getPlayers().setMax(0);

//        e.getResponse().getPlayers().setSample(new ServerPing.PlayerInfo[]{
//                new ServerPing.PlayerInfo("TesterdeTest", "")
//        });
        }else{
            e.getResponse().setDescriptionComponent(new TextComponent(UtilMOTD.getCenteredMOTD(
                    (String)maintenanceManager.getMotd().get("motd_normal.first_line"),
                    (String)maintenanceManager.getMotd().get("motd_normal.second_line")
            )));
            e.getResponse().getPlayers().setOnline(
                    e.getResponse().getPlayers().getOnline()+1+
                    (int)maintenanceManager.getMotd().get("motd.fakePlayerCount")
            );
            e.getResponse().getPlayers().setMax(1);
        }

//        Core.getCore().getMaintenanceManager().getServerOnMaintenance().iterator().forEachRemaining(s -> Synergy.debug(s));
    }

}

package usa.devrocoding.synergy.proxy.maintenance.listener;

import java.util.List;
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

        try{
            if (maintenanceManager.isServerOnMaintenance(maintenanceManager.getProxyServer())) {
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

                {
                    List<String> offlineServers = Core.getCore().getAssetManager().getOfflineServers();
                    if (offlineServers.size() > 0){
                        e.getResponse().getVersion().setProtocol(1);
                        e.getResponse().getVersion().setName("LOADING");

                        e.getResponse().setDescriptionComponent(new TextComponent(UtilMOTD.getCenteredMOTD(
                            (String)maintenanceManager.getMotd().get("motd_normal.first_line"),
                            "&c&lSERVER IS (RE)STARTING"
                        )));
                        e.getResponse().getPlayers().setOnline(
                            e.getResponse().getPlayers().getOnline()+1+
                                (int)maintenanceManager.getMotd().get("motd.fakePlayerCount")
                        );
                        e.getResponse().getPlayers().setMax(1);
                        return;
                    }
                }

                e.getResponse().setDescriptionComponent(new TextComponent(UtilMOTD.getCenteredMOTD(
                    (String)maintenanceManager.getMotd().get("motd_normal.first_line"),
                    (String)maintenanceManager.getMotd().get("motd_normal.second_line")
                )));
                e.getResponse().getPlayers().setOnline(
                    e.getResponse().getPlayers().getOnline()+1+
                        (int)maintenanceManager.getMotd().get("motd.fakePlayerCount")
                );
                e.getResponse().getPlayers().setMax(e.getResponse().getPlayers().getOnline() + 2);
            }
        }catch (Exception exception){
            Synergy.error("MOTD Formatting wrong, sending fail safe!");
            Synergy.error(exception.getMessage());
            e.getResponse().setDescriptionComponent(new TextComponent(UtilMOTD.getCenteredMOTD(
                "&5&lMiragePrisons &e[1.12 - 1.16]",
                "&d&lWelcome to the Magical MiragePrisons"
            )));
        }

//        Core.getCore().getMaintenanceManager().getServerOnMaintenance().iterator().forEachRemaining(s -> Synergy.debug(s));
    }

}

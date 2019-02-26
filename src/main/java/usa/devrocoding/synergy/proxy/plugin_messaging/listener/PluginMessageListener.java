package usa.devrocoding.synergy.proxy.plugin_messaging.listener;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.plugin_messaging.object.SynergyPMEvent;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e){
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
        try {
            ProxiedPlayer proxiedPlayer = Core.getCore().getProxy().getPlayer(e.getReceiver().toString());
            String channel = in.readUTF();
            String input = in.readUTF();

            Core.getCore().getProxy().getPluginManager().callEvent(new SynergyPMEvent(channel, input, proxiedPlayer));
        } catch (IOException e1) {
//            Synergy.error(e1.getMessage());
        }
    }

}

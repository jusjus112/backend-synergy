package usa.devrocoding.synergy.spigot.pluginmessaging.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.assets.PluginMessageBuilder;
import usa.devrocoding.synergy.spigot.pluginmessaging.event.SynergyReceiveEvent;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageReceiveListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals(Core.getPlugin().getManifest().proxy())) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();
        short len = in.readShort();
        byte[] msgbytes = new byte[len];
        in.readFully(msgbytes);
        DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
        String content = "";
        try {
            content = msgin.readUTF();
        } catch (IOException ignored) {}

        PluginMessageBuilder pluginMessage = new PluginMessageBuilder().retrieve(subchannel, content);
        Core.getPlugin().getServer().getPluginManager().callEvent(new SynergyReceiveEvent(content, pluginMessage));
    }
}

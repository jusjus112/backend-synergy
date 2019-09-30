package usa.devrocoding.synergy.spigot.plugin_messaging;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.plugin_messaging.event.SynergyReceiveEvent;
import usa.devrocoding.synergy.spigot.plugin_messaging.listener.PluginMessageReceiveListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessagingManager extends Module {

    private String[] channels = new String[]{
            "BungeeCord"
    };

    public PluginMessagingManager(Core plugin){
        super(plugin, "PluginMessaging Manager", false);

        registerChannels();
    }

    @Override
    public void reload(String response) {

    }

    private void registerChannels(){
        getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(getPlugin(),
                getPlugin().getManifest().proxy());

        PluginMessageReceiveListener pluginMessageReceiveListener = new PluginMessageReceiveListener();

        for(String channel : this.channels){
            getPlugin().getServer().getMessenger().registerIncomingPluginChannel(getPlugin(),
                    channel,
                    pluginMessageReceiveListener);
        }
    }

    public void send(String channel, String content) {
        if (Bukkit.getOnlinePlayers().size() == 0)
            return;
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF(channel);
        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            msgout.writeUTF(content);
            msgout.writeShort(1);
        } catch (IOException ignored) {
        }
        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
        p.sendPluginMessage(Core.getPlugin(), this.channels[0], out.toByteArray());
        getPlugin().getServer().getPluginManager().callEvent(new SynergyReceiveEvent(channel, content));
    }

    public void kick(String name, String reason) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(name);
        out.writeUTF(reason);
        sendPluginMessage(out);
    }

    public void sendPlayerToServer(Player player, String server){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(getPlugin(), getPlugin().getManifest().proxy(), b.toByteArray());
    }

    private void sendPluginMessage(ByteArrayDataOutput out) {
        if (Bukkit.getOnlinePlayers().size() == 0)
            return;
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        p.sendPluginMessage(Core.getPlugin(), this.channels[0], out.toByteArray());
    }


}

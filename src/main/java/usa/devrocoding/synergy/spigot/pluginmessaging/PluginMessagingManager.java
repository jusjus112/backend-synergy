package usa.devrocoding.synergy.spigot.pluginmessaging;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.pluginmessaging.listener.PluginMessageReceiveListener;
import usa.devrocoding.synergy.assets.PluginMessageType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginMessagingManager extends Module {

    private final String[] channels = new String[]{
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

    public void send(String targetUUID, PluginMessageType type, String content) {
        Synergy.debug("REPORT ERROR DISCORD 8");

        Synergy.debug(Bukkit.getOnlinePlayers().size() + " = REPORT ERROR PLAYERS");
        if (Bukkit.getOnlinePlayers().size() == 0)
            return;
        Synergy.debug(content.length() + " = REPORT ERROR LENGTH");
        if (content.length() > 1000){
            return;
        }
        Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(targetUUID);
        out.writeUTF(type.toString());
//        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
//        DataOutputStream msgout = new DataOutputStream(msgbytes);
        out.writeUTF(content);
        //        out.writeShort(msgbytes.toByteArray().length);
//        out.write(msgbytes.toByteArray());
        p.sendPluginMessage(Core.getPlugin(), this.channels[0], out.toByteArray());
        Synergy.debug("SEND OUT -------");
        Synergy.debug(type.toString());
        Synergy.debug(content);
        Synergy.debug("SEND OUT -------");
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

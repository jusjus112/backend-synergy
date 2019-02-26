package usa.devrocoding.synergy.spigot.plugin_messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.plugin_messaging.listener.PluginMessageReceiveListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

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

    public boolean send(String channel, String argument){
        try{
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF(channel);
            out.writeUTF(argument);
            Player player = Core.getPlugin().getServer().getOnlinePlayers().iterator().next();
            player.sendPluginMessage(getPlugin(), getPlugin().getManifest().proxy(), out.toByteArray());
            return true;
        }catch (Exception e){
            Synergy.error(e.getMessage());
            return false;
        }
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

}

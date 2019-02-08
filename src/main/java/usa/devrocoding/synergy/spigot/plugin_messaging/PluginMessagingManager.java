package usa.devrocoding.synergy.spigot.plugin_messaging;

import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class PluginMessagingManager extends Module {

    public PluginMessagingManager(Core plugin){
        super(plugin, "PluginMessaging Manager");
    }

    public boolean send(){
        return false;
    }

    public void sendPlayerToServer(Player player, String server){

    }

}

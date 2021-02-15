package usa.devrocoding.synergy.spigot.punish.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.pluginmessaging.event.SynergyReceiveEvent;
import usa.devrocoding.synergy.assets.PluginMessageType;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class SynergyReceiveListener implements Listener {

    @EventHandler
    public void onPunishReceive(SynergyReceiveEvent e){
        if (e.getPluginMessage().getType() == PluginMessageType.ACTION){
            if (e.getRawContent().contains("UPDATE_PUNISHMENTS")){
                Synergy.debug("1 UPDATE_PUNISHMENTS");
                SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPluginMessage().getTargetUUID());
                if (user != null){
                    Synergy.debug("2 UPDATE_PUNISHMENTS");
                    user.updatePunishments();
                }
            }
        }
    }

}

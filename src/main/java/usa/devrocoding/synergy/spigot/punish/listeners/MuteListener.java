package usa.devrocoding.synergy.spigot.punish.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.List;

public class MuteListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMute(AsyncPlayerChatEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
        List<Punishment> punishments = user.getPunishments();
        if (punishments.size() > 0){
            for(Punishment punishment : punishments){
                if (punishment.getType() == PunishType.MUTE){
                    //TODO: Cancel the event, send message and check when active again
                }
            }
        }
    }

}

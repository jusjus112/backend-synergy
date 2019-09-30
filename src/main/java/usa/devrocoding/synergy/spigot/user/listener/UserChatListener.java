package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

public class UserChatListener implements Listener {

    @EventHandler
    public void onUserChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);

        SynergyUser chatter = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
        String prefix = chatter.getRank().getColor()+chatter.getName()+": "+chatter.getRank().getTextColor();
        if (chatter.getRank() != Rank.NONE) {
            prefix = chatter.getRank().getColor()+"["+chatter.getRank().getPrefix()+chatter.getRank().getColor()+"] §f"+chatter.getName()+": ";
        }
        String message = (chatter.getRank().getTextColor()+e.getMessage());

        for(SynergyUser user : Core.getPlugin().getUserManager().getOnlineUsers()){
            if (e.getMessage().contains(user.getName())){
                user.sendModifactionMessage(
                        MessageModification.RAW,
                        prefix + message.replaceAll(
                                user.getName(),
                                "§e"+user.getName()+user.getRank().getTextColor()
                        )
                );
                user.getSoundControl().pling();
                continue;
            }
            user.sendModifactionMessage(
                    MessageModification.RAW,
                    prefix + message
            );
        }
    }

}

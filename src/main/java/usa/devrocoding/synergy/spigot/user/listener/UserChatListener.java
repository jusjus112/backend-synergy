package usa.devrocoding.synergy.spigot.user.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.event.SynergyUserChatEvent;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserChatListener implements Listener {

    @EventHandler
    public void onUserChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);

        SynergyUser chatter = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
        String message = e.getMessage();

        if (chatter.getMessageCache().equalsIgnoreCase(message)){
            chatter.error("You cannot say the same message twice!");
            return;
        }

        Core.getPlugin().getProtectManager().getOffensiveWords().forEach(s -> {
            e.setMessage(e.getMessage().replaceAll(
                "(?i)"+Pattern.quote(s),
                "§c\\*\\*\\*§r")
            );
        });

        Pattern p = Pattern.compile(Synergy.URL_REGEX);
        if (p.matcher(e.getMessage()).find()){
            chatter.error("Advertising is now allowed!");
            return;
        }

        SynergyUserChatEvent synergyUserChatEvent = new SynergyUserChatEvent(
            chatter, e.getMessage(),
            chatter.getDisplayName() + ChatColor.WHITE + ": " +
                chatter.getRank().getTextColor() + e.getMessage()
        );

        Core.getPlugin().getServer().getPluginManager().callEvent(synergyUserChatEvent);

        boolean hasComponent = synergyUserChatEvent.getFormatComponent() != null;

        if (synergyUserChatEvent.isCancelled()){
            return;
        }

        chatter.setMessageCache(message);

        for(SynergyUser user : Core.getPlugin().getUserManager().getOnlineUsers()){
            if (e.getMessage().contains(user.getName())){
                if (hasComponent) {
                    synergyUserChatEvent.getFormatComponent().setText(
                        synergyUserChatEvent.getFormatComponent().getText().replaceAll(user.getName(),
                        ChatColor.YELLOW + user.getName()));
                    user.getPlayer().spigot().sendMessage(synergyUserChatEvent.getFormatComponent());
                }else{
                    user.getPlayer().sendMessage
                        (synergyUserChatEvent.getFormat().replaceAll(
                        user.getName(), ChatColor.YELLOW + user.getName()));
                }
                user.getSoundControl().pling();
                continue;
            }
            if (hasComponent){
                user.getPlayer().spigot().sendMessage(synergyUserChatEvent.getFormatComponent());
            }else{
                user.getPlayer().sendMessage(synergyUserChatEvent.getFormat());
            }
        }
    }

}

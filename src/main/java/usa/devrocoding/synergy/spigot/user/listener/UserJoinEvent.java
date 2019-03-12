package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void preUserJoin(AsyncPlayerPreLoginEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getUniqueId(), true);

        if (user == null) {
            user = new SynergyUser(e.getUniqueId(), e.getName(), Rank.NONE,
                    Core.getPlugin().getLanguageManager().getLanguage("en"), null);
            Core.getPlugin().getUserManager().addUserToDatabase(user);
        }

        Core.getPlugin().getServer().getPluginManager().callEvent(new UserLoadEvent(user));
    }

    @EventHandler
    public void onUserJoin(PlayerJoinEvent e){
        try{
            SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer());

            if (user == null) {
                // TODO, If failed return to hub or kick if in hub
                e.getPlayer().kickPlayer(C.ERROR.getColor() + "Something went wrong while loading your profile!\n"+C.INFO+"Paste this error code on the forums: "+C.CHAT_HIGHLIGHT+"#2332");
            }else{
                user.setPermissions(e.getPlayer().addAttachment(Core.getPlugin()));
            }
        }catch (Exception ex){
            Sam.getRobot().error(null, ex.getMessage(), "Contact the server developer!", ex);
        }
    }

}

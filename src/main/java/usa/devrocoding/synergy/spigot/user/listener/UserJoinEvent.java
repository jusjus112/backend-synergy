package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.language.Language;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.event.UserPreLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void preUserJoin(AsyncPlayerPreLoginEvent e){
        Core.getPlugin().getRunnableManager().runTaskAsynchronously(
            "Load User",
            core -> {
                UserManager userManager = Core.getPlugin().getUserManager();
                final SynergyUser user = userManager.retrievePlayer(userManager.loadFromUUID(e.getUniqueId()));
                if (user == null){
                    SynergyUser userNew = new SynergyUser(
                            e.getUniqueId(),
                            e.getName(),
                            Rank.NONE,
                            Core.getPlugin().getLanguageManager().getLanguage("en"),
                            UserLoadEvent.UserLoadType.NEW
                    );
                    Core.getPlugin().getRunnableManager().runTask("hack main thread", core1 -> {
                        UserPreLoadEvent userPreLoadEvent = new UserPreLoadEvent(userNew);
                        Core.getPlugin().getServer().getPluginManager().callEvent(userPreLoadEvent);
                        if (!userPreLoadEvent.isCancelled()) {
                            Core.getPlugin().getServer().getPluginManager().callEvent(new UserLoadEvent(userNew, UserLoadEvent.UserLoadType.NEW));
                        }
                    });
                }else{
                    Core.getPlugin().getRunnableManager().runTask("hack main thread", core1 -> {
                        UserPreLoadEvent userPreLoadEvent = new UserPreLoadEvent(user);
                        Core.getPlugin().getServer().getPluginManager().callEvent(userPreLoadEvent);
                        if (!userPreLoadEvent.isCancelled()) {
                            Core.getPlugin().getServer().getPluginManager().callEvent(new UserLoadEvent(user, UserLoadEvent.UserLoadType.RETRIEVED_FROM_DATABASE));
                        }
                    });
                }
            }
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUserJoin(UserPreLoadEvent e){
        try{
//            if (e.getUser() == null) {
//                // TODO, If failed return to hub or kick if in hub
//                e.getUser().getPlayer().kickPlayer(C.ERROR.getColor() + "Something went wrong while loading your profile!\n"+C.INFO+"Paste this error code on the forums: "+C.CHAT_HIGHLIGHT+"#2332");
//            }else{
//                e.getUser().setPermissions(e.getUser().getPlayer().addAttachment(Core.getPlugin()));
//            }
        }catch (Exception ex){
            Sam.getRobot().error(null, ex.getMessage(), "Contact the server developer!", ex);
        }
    }

}

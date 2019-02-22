package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void preUserJoin(AsyncPlayerPreLoginEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getUniqueId(), true);

        if (user == null) {
            user = new SynergyUser(e.getUniqueId(), e.getName(), Rank.NONE,
                    Core.getPlugin().getLanguageManager().getLanguage("en"));
            Core.getPlugin().getUserManager().addUserToDatabase(user);
        }
    }

    @EventHandler
    public void onUserJoin(PlayerJoinEvent e){
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer());

        if (user == null) {
            // TODO, If failed return to hub or kick if in hub
            e.getPlayer().kickPlayer(C.ERROR.getColor() + "Something went wrong while loading your profile!");
        }
    }

}

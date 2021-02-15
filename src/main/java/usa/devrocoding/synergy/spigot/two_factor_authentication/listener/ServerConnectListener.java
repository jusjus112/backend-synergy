package usa.devrocoding.synergy.spigot.two_factor_authentication.listener;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.warp.object.Warp;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onLogin(UserLoadEvent e){
        if (e.getUser().needs2FA()){
            GoogleAuthManager googleAuthManager = Core.getPlugin().getGoogleAuthManager();
            Location cachedLocation = e.getUser().getPlayer().getLocation();

            Core.getPlugin().getGoogleAuthManager().sendMessage(e.getUser());

            if (!googleAuthManager.getTeleportedToSpawn().containsKey(e.getUser())){

                Warp warp = Core.getPlugin().getWarpManager().getWarp("spawn");
                warp.teleportTo(e.getUser());

                googleAuthManager.getTeleportedToSpawn().put(e.getUser(), cachedLocation);
            }

        }else{
            // TODO: Send message that 2FA is already enabled.
        }
    }

}

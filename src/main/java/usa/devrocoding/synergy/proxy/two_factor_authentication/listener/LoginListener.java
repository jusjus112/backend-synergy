package usa.devrocoding.synergy.proxy.two_factor_authentication.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.two_factor_authentication.GoogleAuthManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent e){
        GoogleAuthManager gam = Core.getCore().getGoogleAuthManager();
        try{
            ResultSet result = gam.getUserData(e.getPlayer().getUniqueId().toString());
            if (result.next()){
                String key = result.getString("key");
                gam.enable2faMode(e.getPlayer(), key);
            }else{
                String key = gam.getTwoFactorKey(e.getPlayer().getUniqueId());
                Synergy.debug("CONNECTION PLAYER= "+e.getPlayer().getName());
                Synergy.debug("KEY= "+key);
                gam.addToDatabase(e.getPlayer(), key);
                gam.enable2faMode(e.getPlayer(), key);
            }
        }catch (SQLException exc){
            Synergy.error(exc.getMessage());
        }
    }

}

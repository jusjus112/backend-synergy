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
import java.util.HashMap;

public class LoginListener implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent e){
        Core.getCore().getProxy().getScheduler().runAsync(Core.getCore(), new Runnable() {
            @Override
            public void run() {
                try{
                    GoogleAuthManager gam = Core.getCore().getGoogleAuthManager();

                    ResultSet resultSet = gam.getUserData(e.getPlayer().getUniqueId().toString());

                    if (resultSet.next()){
                        gam.enable2faMode(e.getPlayer(), resultSet.getString("key"));
                    }else{
                        String key = gam.getTwoFactorKey(e.getPlayer().getUniqueId());
                        gam.addToDatabase(e.getPlayer(), key);
                        gam.enable2faMode(e.getPlayer(), key);
                    }
                    Core.getCore().getDatabaseManager().disconnect();
                }catch (SQLException ex){
                    Synergy.error("LoginListener - "+ex.getMessage());
                }
            }
        });
    }

}

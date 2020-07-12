package usa.devrocoding.synergy.proxy.user.listener;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.user.UserManager;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.util.List;

public class UserJoinProxyListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(LoginEvent e){
        e.registerIntent(Core.getCore());

        //TODO: Will not work since punishments need to be updated
        //TODO: We cannot save proxyusers as an object, that's why
        UserManager userManager = Core.getCore().getUserManager();

        Core.getCore().getProxy().getScheduler().runAsync(Core.getCore(), new Runnable() {
            @Override
            public void run() {
                List<Punishment> punishments = Core.getCore().getPunishManager().retrievePunishments(e.getConnection().getUniqueId());
                ProxyUser newUser = new ProxyUser(e.getConnection().getUniqueId());
                newUser.setPunishments(punishments);

                newUser.checkPunishments(e);
            }
        });
    }

}

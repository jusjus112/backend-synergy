package usa.devrocoding.synergy.proxy.punish.listener;

import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.util.List;

public class UserLoginListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent e){
        e.registerIntent(Core.getCore());

        Core.getCore().getProxy().getScheduler().runAsync(Core.getCore(), new Runnable() {
            @Override
            public void run() {
                Synergy.debug("1");
                List<Punishment> punishments = Core.getCore().getPunishManager().retrievePunishments(e.getConnection().getUniqueId());
                Synergy.debug("2");
                for (Punishment punishment : punishments) {
                    Synergy.debug("3");
                    if (punishment.getType() == PunishType.BAN && punishment.isActive()) {
                        Synergy.debug("4");
                        e.setCancelReason(punishment.getBanMessage());
                        e.setCancelled(true);
                        e.completeIntent(Core.getCore());
                        Synergy.debug("5");
                        Synergy.debug(e.isCancelled()+" = CANCELLED");
                        return;
                    }
                }
                e.completeIntent(Core.getCore());
            }
        });
    }

}

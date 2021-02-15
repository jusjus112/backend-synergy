package usa.devrocoding.synergy.proxy.user.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.util.List;

public class UserJoinProxyListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(LoginEvent e){
        e.registerIntent(Core.getCore());

        Core.getCore().getProxy().getScheduler().runAsync(Core.getCore(), () -> {
            List<Punishment> punishments = Core.getCore().getPunishManager().retrievePunishments(e.getConnection().getUniqueId());
            List<UUID> friends = Core.getCore().getBuddyManager().getFriendsForUser(e.getConnection().getUniqueId());

            Rank rank = Rank.NONE;

            try {
                ResultSet resultSet = Core.getCore().getDatabaseManager().getResults("users", "uuid=?",
                    new HashMap<Integer, Object>(){{
                        put(1, Core.getCore().getUserManager().convertUniqueId(e.getConnection().getUniqueId()));
                    }}
                );
                Synergy.debug("USER LOADING");
                if (resultSet.next()){
                    Synergy.debug("USER HAS RANK");
                    Synergy.debug("USER HAS RANK = " + resultSet.getString("rank"));
                    rank = Rank.fromName(resultSet.getString("rank"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            ProxyUser newUser = new ProxyUser(e.getConnection().getUniqueId(), rank);
            newUser.setPunishments(punishments);
            newUser.setFriends(friends);

            newUser.checkPunishments(e);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(ServerConnectedEvent e){
        ProxyUser proxyUser = Core.getCore().getUserManager().getUser(e.getPlayer().getUniqueId());
        e.getPlayer().setDisplayName(proxyUser.getDisplayName());
    }

}

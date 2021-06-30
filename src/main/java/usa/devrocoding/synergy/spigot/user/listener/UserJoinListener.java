package usa.devrocoding.synergy.spigot.user.listener;

import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Map;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.economy.object.Economy;
import usa.devrocoding.synergy.spigot.statistics.object.Statistic;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.event.UserPreLoadEvent;
import usa.devrocoding.synergy.includes.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserJoinListener implements Listener {

    @EventHandler
    public void preUserJoin(AsyncPlayerPreLoginEvent e){
        Core.getPlugin().getRunnableManager().runTaskAsynchronously(
            "Load User",
            core -> {
                UserManager userManager = Core.getPlugin().getUserManager();
                SynergyUser user = userManager.getUser(e.getUniqueId());
                if (user == null) {
                    Synergy.debug("USER = NULL");
                    user = userManager.retrievePlayer(userManager.loadFromUUID(e.getUniqueId()), true);
                    if (user == null) {
                        Map<StatisticType, Statistic> statistics = Core.getPlugin()
                            .getStatisticManager().retrieveStatistics(e.getUniqueId());

                        Synergy.debug("USER RETRIEVE = NULL");
                        user = new SynergyUser(
                                e.getUniqueId(),
                                e.getName(),
                                Rank.NONE,
                                Core.getPlugin().getLanguageManager().getLanguage("en"),
                                UserLoadEvent.UserLoadType.NEW
                        );

                        user.setObjectives(Maps.newHashMap());
                        user.setAchievements(Maps.newHashMap());
                        user.setStatistics(statistics);
                        user.setEconomy(new Economy(new BigDecimal(0), 0, 0));

                        SynergyUser finalUser1 = user;
                        Core.getPlugin().getRunnableManager().runTask("hack main thread", core1 -> {
                            UserPreLoadEvent userPreLoadEvent = new UserPreLoadEvent(finalUser1);
                            Core.getPlugin().getServer().getPluginManager().callEvent(userPreLoadEvent);
                            finalUser1.attemptLoading();
                        });
                    } else {
                        SynergyUser finalUser = user;
                        Core.getPlugin().getRunnableManager().runTask("hack main thread", core1 -> {
                            UserPreLoadEvent userPreLoadEvent = new UserPreLoadEvent(finalUser);
                            Core.getPlugin().getServer().getPluginManager().callEvent(userPreLoadEvent);
                            finalUser.attemptLoading();
                        });
                    }
                }
            }
        );
    }

    @EventHandler
    public void onUserPreLogin(PlayerLoginEvent e){
        Synergy.debug(Core.getPlugin().isReady() + " = READY");
        if (!Core.getPlugin().isReady()){
            e.disallow(Result.KICK_FULL,
                "Server is still loading..."
            );
            e.setKickMessage("\nServer is still loading...\nPlease allow the systems some seconds to load ;)");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(null);
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.attemptLoading();
        }
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

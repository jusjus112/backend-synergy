package usa.devrocoding.synergy.spigot.user.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.language.Language;
import usa.devrocoding.synergy.spigot.language.LanguageFile;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserJoinEvent implements Listener {

    @EventHandler
    public void preUserJoin(AsyncPlayerPreLoginEvent e){
        Core.getPlugin().getRunnableManager().runTaskAsynchronously(
            "Load User",
            core -> {
                try{
                    ResultSet resultSet = Core.getPlugin().getDatabaseManager().getResults(
                            "SELECT * FROM synergy_users WHERE uuid='"+e.getUniqueId()+"'"
                    );
                    UUID uuid = e.getUniqueId(); String name = e.getName(); Rank rank = Rank.NONE;
                    LanguageFile language = Core.getPlugin().getLanguageManager().getLanguage("en");
                    double experience = 0D;
                    boolean first_time = true;

                    if (resultSet.next()){
                        first_time = false;
                        experience = resultSet.getDouble("xp");
                        if (Rank.fromName(resultSet.getString("rank")) != null){
                            rank = Rank.fromName(resultSet.getString("rank"));
                        }
                    }
                    SynergyUser user = new SynergyUser(uuid, name, rank, language, first_time);
                    user.setNetworkXP(experience);

                    Core.getPlugin().getServer().getPluginManager().callEvent(new UserLoadEvent(user));
                }catch (SQLException ex){
                    Synergy.error(ex.getMessage());
                }
            }
        );
    }

    @EventHandler
    public void onUserJoin(UserLoadEvent e){
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

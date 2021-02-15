package usa.devrocoding.synergy.spigot.achievement.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;

public class PlayerFinishedAchievementListener implements Listener {

    @EventHandler
    public void onPlayerFinishedAchievement(PlayerFinishedAchievementEvent e){
//        e.getSynergyUser().getEconomy().addXP(e.getAchievement().getRewardExperience());

        e.getSynergyUser()
            .sendModifactionMessage(
                MessageModification.CENTERED,
                " ",
                " ",
                "§9§lAchievement Unlocked",
                "§e\""+e.getAchievement().getName()+"\"",
                " "
        );
        e.getSynergyUser()
            .sendModifactionMessage(
                MessageModification.CENTERED,
                e.getAchievement().rewards()
            );
        e.getSynergyUser()
            .sendModifactionMessage(
                MessageModification.RAW,
                " "
            );
    }

    /*
    ------------------------------------

            ACHIEVEMENT UNLOCKED
            "I'm just observing"

            Received ## Network XP
    ------------------------------------
     */

}

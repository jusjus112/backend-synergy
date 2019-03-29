package usa.devrocoding.synergy.spigot.achievement.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;

public class PlayerFinishedAchievementListener implements Listener {

    @EventHandler
    public void onPlayerFinishedAchievement(PlayerFinishedAchievementEvent e){
        e.getSynergyUser().addNetworkXP(e.getAchievement().getRewardExperience());

        e.getSynergyUser().sendModifactionMessage(
                MessageModification.CENTERED,
                C.getLine(),
                " ",
                "§9§lAchievement Unlocked",
                "§e\""+e.getAchievement().getName()+"\"",
                " ",
                "§eReceived §6"+e.getAchievement().getRewardExperience()+" §eNetwork XP",
                C.getLine()
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

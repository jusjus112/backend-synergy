package usa.devrocoding.synergy.spigot.achievement.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;

public class PlayerFinishedAchievementListener implements Listener {

    @EventHandler
    public void onPlayerFinishedAchievement(PlayerFinishedAchievementEvent e){
        e.getSynergyUser().addNetworkXP(e.getAchievement().rewardExperience());
    }

}

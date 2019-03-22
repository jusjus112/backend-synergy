package usa.devrocoding.synergy.spigot.achievement.achievements;

import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class ACHIEVEMENT_FIRST_JOINED extends Achievement {


    public ACHIEVEMENT_FIRST_JOINED(){
        super("First Time Joined");
    }

    @Override
    public String[] getDescription() {
        return new String[0];
    }

    @Override
    public double rewardExperience() {
        return 0;
    }

    @Override
    public void reward(SynergyUser synergyUser) {

    }

    @Override
    public void mechanics() {

    }
}

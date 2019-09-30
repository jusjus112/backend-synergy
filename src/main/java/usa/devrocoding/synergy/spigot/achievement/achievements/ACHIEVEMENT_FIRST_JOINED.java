package usa.devrocoding.synergy.spigot.achievement.achievements;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class ACHIEVEMENT_FIRST_JOINED extends Achievement {


    public ACHIEVEMENT_FIRST_JOINED(){
        super("Looking for something new", new String[]{"Joining this network for","the first time in your life."});
    }

    @Override
    public double getRewardExperience() {
        return 10D;
    }

    @Override
    public void reward(SynergyUser synergyUser) {

    }

    @Override
    public void mechanics() {
        addListener(new EventListener<UserLoadEvent>() {
            @EventHandler
            public void process(UserLoadEvent e){
                if (e.getLoadType() == UserLoadEvent.UserLoadType.NEW) {
                    unlock(e.getUser());
                }
            }
        });
    }
}

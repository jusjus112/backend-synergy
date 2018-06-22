package usa.devrocoding.synergy.spigot.test;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.events.EventListener;

public class TestAchievement extends Achievement {

    public TestAchievement(){
        super("First Join");
    }

    @Override
    public String[] getDescription() {
        return new String[]{
                "First join", "Achievement"
        };
    }

    @Override
    public ItemStack getIcon() {
        return null;
    }

    @Override // Called when the unlock method is called
    public void reward(PlayerFinishedAchievementEvent e) {

    }

    @Override
    public void mechanics() {
        this.addListener(new EventListener<PlayerJoinEvent>(){
            @Override
            public void process(PlayerJoinEvent event) {
                if (!event.getPlayer().hasPlayedBefore()){
                    unlock();
                }
            }
        });
    }
}

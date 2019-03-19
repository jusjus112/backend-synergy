package usa.devrocoding.synergy.spigot.achievement.object;

import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.listeners.Listeners;

public abstract class Achievement {

    private String name;

    public Achievement(String name){
        this.name = name;
    }

    public abstract String[] getDescription();
    public abstract ItemStack getIcon();
    public abstract void reward(PlayerFinishedAchievementEvent e);
    public abstract void mechanics();

    public void unlock(){

    }

    public void addListener(EventListener<?> listener) {
        Listeners.addListener(listener);
    }

}

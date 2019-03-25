package usa.devrocoding.synergy.spigot.achievement.object;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.listeners.Listeners;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class Achievement {

    @Getter
    private String name;
    @Getter
    private String[] description;

    public Achievement(String name, String[] description){
        this.name = name;
        this.description = description;
        mechanics();
    }

//    public abstract ItemStack getIcon();
    public abstract double getRewardExperience();
    public abstract void reward(SynergyUser synergyUser);
    public abstract void mechanics();

    public void unlock(SynergyUser user){
        if (!user.hasAchievement(this)) {
            reward(user);
            Core.getPlugin().getServer().getPluginManager().callEvent(new PlayerFinishedAchievementEvent(user, this));
            user.unlockAchievement(this);
        }
    }

    public void unlock(Player player){
        unlock(Core.getPlugin().getUserManager().getUser(player));
    }

    public void addListener(EventListener<?> listener) {
        Listeners.addListener(listener);
    }

}

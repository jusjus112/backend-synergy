package usa.devrocoding.synergy.spigot.achievement.object;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.listeners.Listeners;
import usa.devrocoding.synergy.spigot.objectives.event.ObjectiveEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@Getter
public abstract class Achievement {

    private final String name;
    private final String[] description;

    public Achievement(String name, String[] description){
        this.name = name;
        this.description = description;
        mechanics();
    }

    public abstract String[] rewards();
    public abstract void onFinish(SynergyUser synergyUser);
    public abstract void mechanics();

    public void unlock(SynergyUser user){
        if (!user.hasAchievement(this)) {
            onFinish(user);
            Core.getPlugin().getServer().getPluginManager()
                .callEvent(new PlayerFinishedAchievementEvent(
                    user, this));
            user.unlockAchievement(this);
        }
    }

    public void unlock(Player player){
        unlock(Core.getPlugin().getUserManager().getUser(player));
    }

    public void addListener(Class<? extends Event> eventClass, AchievementEvent<? extends Event> listener){
        Core.getPlugin().getServer().getPluginManager().registerEvent(
            eventClass, listener, EventPriority.HIGHEST, listener, Core.getPlugin());
    }

}

package usa.devrocoding.synergy.spigot.objectives.object;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.listeners.Listeners;
import usa.devrocoding.synergy.spigot.objectives.event.ObjectiveFinishedEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class Objective {

    @Getter
    private String objective;
    @Getter
    private String[] description;

    public Objective(String objective, String... description){
        this.objective = objective;
        this.description = description;
    }

    public abstract void mechanics();
    public abstract Objective unlockFirst();
    public abstract Objective next();

    public boolean isAbletoStart(SynergyUser synergyUser){
        return (unlockFirst() != null && synergyUser.hasObjective(unlockFirst()));
    }

    public boolean finish(SynergyUser user){
        if (isAbletoStart(user) && !user.hasObjective(this)) {
            user.setActiveObjective(next());
            Core.getPlugin().getServer().getPluginManager().callEvent(new ObjectiveFinishedEvent(user, this));
            return true;
        }
        return false;
    }

    public void addListener(EventListener<?> listener) {
        Listeners.addListener(listener);
    }

}

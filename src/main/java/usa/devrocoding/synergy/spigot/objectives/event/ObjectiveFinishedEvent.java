package usa.devrocoding.synergy.spigot.objectives.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class ObjectiveFinishedEvent extends SynergyEvent {

    @Getter
    private SynergyUser synergyUser;
    @Getter
    private Objective objective;

    public ObjectiveFinishedEvent(SynergyUser synergyUser, Objective objective){
        this.synergyUser = synergyUser;
        this.objective = objective;
    }

}


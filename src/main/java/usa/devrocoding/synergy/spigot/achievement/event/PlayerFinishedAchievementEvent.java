package usa.devrocoding.synergy.spigot.achievement.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.events.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class PlayerFinishedAchievementEvent extends SynergyEvent {

    @Getter
    private SynergyUser synergyUser;
    @Getter
    private Achievement achievement;

    public PlayerFinishedAchievementEvent(SynergyUser synergyUser, Achievement achievement){
        this.synergyUser = synergyUser;
        this.achievement = achievement;
    }

}

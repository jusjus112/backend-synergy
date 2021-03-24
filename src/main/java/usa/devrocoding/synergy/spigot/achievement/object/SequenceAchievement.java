package usa.devrocoding.synergy.spigot.achievement.object;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.event.PlayerFinishedAchievementEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Collections;

public abstract class SequenceAchievement extends Achievement {

    public SequenceAchievement(String name, String[] description) {
        super(name, description);
    }

    public abstract int[] sequence();

    public void unlock(SynergyUser user, int value) {
        if (Collections.singletonList(sequence()).contains(value)){
            onFinish(user);
            Core.getPlugin().getServer().getPluginManager()
                    .callEvent(new PlayerFinishedAchievementEvent(
                            user, this));
            user.unlockAchievement(this);
        }
    }

    public String toString(int sequence) {
        return super.toString() + "_" + sequence;
    }
}

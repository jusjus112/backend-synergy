package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import lombok.Setter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardPolicy;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserLoadEvent extends SynergyEvent {

    @Getter
    private final SynergyUser user;
    @Getter
    private final UserLoadType loadType;
    @Setter @Getter
    private ScoreboardPolicy scoreboardPolicy;

    public UserLoadEvent(SynergyUser user, UserLoadType loadType) {
        this.user = user;
        this.loadType = loadType;
        this.scoreboardPolicy = Core.getPlugin().getScoreboardManager().getDefaultPolicy();
    }

    public enum UserLoadType{
        NEW,
        RETRIEVED_FROM_DATABASE,
        NEW_INSTANCE
    }
}

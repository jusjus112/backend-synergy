package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserLoadEvent extends SynergyEvent {

    @Getter
    private final SynergyUser user;
    @Getter
    private UserLoadType loadType;

    public UserLoadEvent(SynergyUser user, UserLoadType loadType) {
        this.user = user;
        this.loadType = loadType;
    }

    public enum UserLoadType{
        NEW,
        RETRIEVED_FROM_DATABASE,
        NEW_INSTANCE
    }
}

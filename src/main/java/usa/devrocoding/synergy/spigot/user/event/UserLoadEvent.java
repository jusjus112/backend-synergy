package usa.devrocoding.synergy.spigot.user.event;

import usa.devrocoding.synergy.spigot.events.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserLoadEvent extends SynergyEvent {

    private final SynergyUser user;

    public UserLoadEvent(SynergyUser user) {
        this.user = user;
    }

    public SynergyUser getUser() {
        return user;
    }
}

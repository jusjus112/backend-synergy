package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import org.bukkit.event.Cancellable;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserPreLoadEvent extends SynergyEvent {

    @Getter
    private final SynergyUser user;

    public UserPreLoadEvent(SynergyUser user) {
        this.user = user;
    }
}

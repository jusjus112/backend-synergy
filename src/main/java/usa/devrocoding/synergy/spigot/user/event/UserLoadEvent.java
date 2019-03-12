package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.events.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserLoadEvent extends SynergyEvent {

    @Getter
    private final SynergyUser user;

    public UserLoadEvent(SynergyUser user) {
        this.user = user;
    }
}

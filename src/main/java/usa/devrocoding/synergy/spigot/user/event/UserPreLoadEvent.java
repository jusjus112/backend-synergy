package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import org.bukkit.event.Cancellable;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class UserPreLoadEvent extends SynergyEvent implements Cancellable {

    @Getter
    private final SynergyUser user;
    private boolean cancelled;

    public UserPreLoadEvent(SynergyUser user) {
        this.user = user;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}

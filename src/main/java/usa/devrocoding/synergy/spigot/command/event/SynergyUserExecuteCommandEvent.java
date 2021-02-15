package usa.devrocoding.synergy.spigot.command.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@RequiredArgsConstructor
@Getter
public class SynergyUserExecuteCommandEvent extends SynergyEvent implements Cancellable {

  private boolean cancelled;
  private final SynergyCommand command;
  private final SynergyUser synergyUser;

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
}

package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@RequiredArgsConstructor
@Getter
public class UserQuitEvent extends SynergyEvent {

  private final SynergyUser synergyUser;
  private final Player playerObject;

}

package usa.devrocoding.synergy.spigot.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.Nullable;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@RequiredArgsConstructor
@Getter
public class SynergyUserChatEvent extends SynergyEvent implements Cancellable {

  private boolean cancelled;
  private final SynergyUser synergyUser;
  private final String message;
  private final String format;
  @Setter @Nullable
  private TextComponent formatComponent;

  @Override
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
}

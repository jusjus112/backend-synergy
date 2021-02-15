package usa.devrocoding.synergy.spigot.achievement.object;

import lombok.NonNull;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

public abstract class AchievementEvent<E extends Event> implements EventExecutor, Listener {

  public void execute(@NotNull Listener listener, @NotNull Event event) {
    try{
      this.execute((E) event);
    }catch (Exception ignored){}
  }

  public abstract void execute(@NonNull E e);

}

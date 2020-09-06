package usa.devrocoding.synergy.spigot.objectives;

import org.bukkit.event.inventory.InventoryOpenEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.listeners.EventListener;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;

public class TestObjective extends Objective {

  @Override
  public String name() {
    return "Open your inventory";
  }

  @Override
  public String[] description() {
    return new String[]{};
  }

  @Override
  public void mechanics() {
    addListener(new EventListener<InventoryOpenEvent>() {
      @Override
      public void process(InventoryOpenEvent event) {
        Synergy.debug("CLOSE 1");
        finish(Core.getPlugin().getUserManager().getUser(event.getPlayer().getUniqueId()));
      }
    });
  }

  @Override
  public Objective unlockFirst() {
    return null;
  }

  @Override
  public Objective next() {
    return null;
  }

}

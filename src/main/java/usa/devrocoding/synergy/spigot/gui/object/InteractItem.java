package usa.devrocoding.synergy.spigot.gui.object;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public abstract class InteractItem extends ItemBuilder {

  @Getter
  private static List<InteractItem> interactItems = Lists.newArrayList();

  public InteractItem(Material material){
    super(material);

    interactItems.add(this);
  }

  public abstract void onClick(SynergyUser synergyUser, Action action);

}

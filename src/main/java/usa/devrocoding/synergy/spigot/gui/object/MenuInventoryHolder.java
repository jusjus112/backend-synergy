package usa.devrocoding.synergy.spigot.gui.object;

import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

@Getter
public class MenuInventoryHolder implements InventoryHolder {

  private final SynergyUser viewer;
  private Inventory inventory;

  private final Gui menu;
  private final boolean openParent;

  @Setter
  private int pageNumber;

  @Setter
  private boolean transitingPageState;

  private IntObjectMap<GuiElement> paginatedItems;

  public MenuInventoryHolder(SynergyUser viewer, Gui menu){
    this.viewer = viewer;
    this.menu = menu;
    openParent = true;
  }

  public void setInventory(Inventory inventory){
    Validate.isTrue(this.inventory == null, "Inventory is already set");
    this.inventory = inventory;
  }

  @Override
  public Inventory getInventory() {
    return inventory;
  }

  public void createPaginatedItems() {
    paginatedItems = new IntObjectHashMap<>();
  }
}

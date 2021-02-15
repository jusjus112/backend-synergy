package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Maps;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryBackup {

  private final Map<Integer, ItemStack> cachedItems;

  public InventoryBackup(PlayerInventory inventory){
    this.cachedItems = Maps.newHashMap();

    this.backup(inventory);
  }

  private void backup(Inventory inventory){
    for (int i = 0; i < 104; i++) {
      ItemStack item = inventory.getItem(i);
      if (item != null && item.getType() != Material.AIR){
        this.cachedItems.put(i, item);
        inventory.remove(item);
      }
    }
  }

  public void restore(Inventory inventory){
    inventory.clear();
    this.cachedItems.forEach(inventory::setItem);
  }

}

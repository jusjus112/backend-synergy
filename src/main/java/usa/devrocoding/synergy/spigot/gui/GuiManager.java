package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.List;

public class GuiManager extends Module {

	private final List<Gui> menus = Lists.newArrayList();
	
	public GuiManager(Core plugin) {
		super(plugin, "GuiManager");
	}
	
	@EventHandler
	public void on(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		int slot = event.getRawSlot();
		
		for(Gui menu : Lists.newArrayList(menus)) {
			if(event.getInventory().getName().equals(menu.getName())) {
				if(menu.getCurrentSessions().containsKey(player.getUniqueId())) {
					event.setCancelled(true);
					
					if(event.getCurrentItem() != null) {
						if(menu.getElements().containsKey(slot)) {
							menu.getElements().get(slot).click(player, event.getClick());
						}
					}
				}
			}
		}
	}

	public List<Gui> getMenus() {
		return menus;
	}

}

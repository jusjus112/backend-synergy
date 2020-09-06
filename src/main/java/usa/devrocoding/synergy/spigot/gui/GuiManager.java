package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.gui.object.GuiInteract;
import usa.devrocoding.synergy.spigot.gui.object.GuiInteractElement;
import usa.devrocoding.synergy.spigot.gui.object.MenuInventoryHolder;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.List;

public class GuiManager extends Module {

	@Getter
	private final List<Gui> menus = Lists.newArrayList();
	@Getter
	private final List<GuiInteract> interactMenus = Lists.newArrayList();
	
	public GuiManager(Core plugin) {
		super(plugin, "Gui Manager", false);
	}
	
	@EventHandler
	public void on(InventoryClickEvent event) {
		InventoryView view = event.getView();
		Inventory topInventory = view.getTopInventory();

		if (topInventory.getHolder() instanceof MenuInventoryHolder) {
			event.setCancelled(event.getRawSlot() == event.getSlot() || event.isShiftClick()
					|| event.getClick() == ClickType.DOUBLE_CLICK);

			if (event.getRawSlot() == event.getSlot()) {
				MenuInventoryHolder holder = (MenuInventoryHolder) topInventory.getHolder();
				Gui menu = holder.getMenu();
				int slot = event.getSlot();

				if (menu instanceof PaginatedGui) {
					int newPage = holder.getPageNumber();
					if (slot == ((PaginatedGui) menu).getChangePageSlots().getLeft()) {
						newPage--;
					} else if (slot == ((PaginatedGui) menu).getChangePageSlots().getRight()) {
						newPage++;
					} else {
						return;
					}

					if (newPage >= 1) {
						((PaginatedGui) menu).setPage(holder.getViewer(), newPage);
					}

					return;
				}

				GuiElement item = menu.getElement(slot);

				if (item == null && holder.getPaginatedItems() != null) {
					item = holder.getPaginatedItems().get(slot);
				}

				if (item != null) {
					item.click(holder.getViewer(), event.getClick(), menu);
				}

			}
		}
	}

	/*
		Player player = (Player) event.getWhoClicked();
		SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(player);
		int slot = event.getSlot();

		for(Gui menu : Lists.newArrayList(menus)) {
			if(menu.getName().equalsIgnoreCase("Player Inventory")||
				(event.getView().getTitle().equals(menu.getName())&&menu.getCurrentSessions().containsKey(player.getUniqueId()))) {
				if(event.getCurrentItem() != null) {
					if (!menu.onInsert(event.getCurrentItem())){
						event.setCancelled(true);
					}
					if(menu.getElements().containsKey(slot)) {
						menu.getElements().get(slot).click(synergyUser, event.getClick());
					}
				}
			}
		}
	 */

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player player = e.getPlayer();
		SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(player);
		for(GuiInteract menu : Lists.newArrayList(interactMenus)) {
			if (e.getItem() != null && e.getItem().hasItemMeta()){
				for(GuiInteractElement guiInteractElement : menu.getElements().keySet()) {
					if (guiInteractElement.getIcon(synergyUser).equals(e.getItem())) {
						guiInteractElement.click(synergyUser, e.getAction());
						//TODO: Add a click cooldown to prevent spamming
						break;
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
		InventoryView view = e.getView();
		Inventory topInventory = view.getTopInventory();

		if (topInventory.getHolder() instanceof MenuInventoryHolder) {
			MenuInventoryHolder holder = (MenuInventoryHolder) topInventory.getHolder();
			Gui parent = holder.getMenu().getParent();
			if (parent != null) {
				getPlugin().getRunnableManager().runTaskLater("open parent gui", core ->
						parent.open(holder.getViewer()), 1L);
			}
		}
	}

	@Override
	public void reload(String response) {

	}

}

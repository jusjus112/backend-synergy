package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.gui.object.GuiInteract;
import usa.devrocoding.synergy.spigot.gui.object.GuiInteractElement;
import usa.devrocoding.synergy.spigot.gui.object.InteractItem;
import usa.devrocoding.synergy.spigot.gui.object.MenuInventoryHolder;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.List;

public class GuiManager extends Module {

	@Getter
	private final List<GuiInteract> interactMenus = Lists.newArrayList();
	
	public GuiManager(Core plugin) {
		super(plugin, "Gui Manager", false);
	}
	
	@EventHandler
	public void on(InventoryClickEvent event) {
		InventoryView view = event.getView();
		Inventory topInventory = view.getTopInventory();
		Synergy.debug("GUIMANAGER CLICK -1");

		if (topInventory.getHolder() instanceof MenuInventoryHolder) {
			MenuInventoryHolder holder = (MenuInventoryHolder) topInventory.getHolder();
			Gui menu = holder.getMenu();
			Synergy.debug("GUIMANAGER CLICK -2");
			if (event.getRawSlot() == event.getSlot()) {
				int slot = event.getSlot();
				Synergy.debug("GUIMANAGER CLICK 0");
				if (menu instanceof PaginatedGui) {
					Synergy.debug("GUIMANAGER CLICK 1");

					boolean navigational = false;
					int newPage = holder.getPageNumber();
					if (slot == ((PaginatedGui) menu).getChangePageSlots().getLeft()) {
						newPage--;
						navigational = true;
					} else if (slot == ((PaginatedGui) menu).getChangePageSlots().getRight()) {
						newPage++;
						navigational = true;
					}
					Synergy.debug("GUIMANAGER CLICK 2");
					event.setCancelled(true);

					if (newPage >= 1) {
						((PaginatedGui) menu).setPage(holder.getViewer(), newPage);
					}
					Synergy.debug("GUIMANAGER CLICK 3");
					if (navigational) {
						return;
					}
				}

				GuiElement item = menu.getElement(slot);
				Synergy.debug("GUIMANAGER CLICK 4");

				if (item == null && holder.getPaginatedItems() != null) {
					Synergy.debug("GUIMANAGER CLICK 5");
					item = holder.getPaginatedItems().get(slot);
				}

				Synergy.debug("GUIMANAGER CLICK 6");

				boolean onInsert = menu.onInsert(event.getCurrentItem(), topInventory);

				if (item != null) {
					Synergy.debug("GUIMANAGER CLICK 7");
					if (!item.disableClickEvent()) {
						Synergy.debug("GUIMANAGER CLICK 8");
						if (onInsert) {
							Synergy.debug("GUIMANAGER CLICK 9");
							event.setCancelled(false);
						} else {
							Synergy.debug("GUIMANAGER CLICK 10");
							event.setCancelled((event.getRawSlot() == event.getSlot() || event.isShiftClick()
									|| event.getClick() == ClickType.DOUBLE_CLICK));
						}
					}
					menu.setIgnoringParent(true);
					if (!item.disableClickEvent()) {
						item.click(holder.getViewer(), event.getClick(), menu);
					}
					menu.setIgnoringParent(false);
				}else {
					event.setCancelled(!onInsert);
				}
			}else{
				if (menu.onInsert(event.getCurrentItem(), topInventory)) {
					Synergy.debug("GUIMANAGER CLICK 11");
					event.setCancelled(false);
				} else {
					Synergy.debug("GUIMANAGER CLICK 12");
					event.setCancelled((event.getRawSlot() == event.getSlot() || event.isShiftClick()
							|| event.getClick() == ClickType.DOUBLE_CLICK));
				}
			}
		}
	}

	@EventHandler
	public void on(InventoryDragEvent event){
		if (event.getInventory().getHolder() instanceof MenuInventoryHolder){
			Synergy.debug("GUIMANAGER DRAG 2");
			if (event.getOldCursor() != null) {
				if (event.getInventory() == event.getView().getTopInventory()) {
					Gui gui = ((MenuInventoryHolder) event.getInventory().getHolder()).getMenu();
					Synergy.debug("GUIMANAGER DRAG 3");
					boolean onInsert = gui.onInsert(event.getOldCursor(), event.getInventory());
					event.setCancelled(!onInsert);
					Synergy.debug(onInsert + " = GUIMANAGER DRAG 4");
				}
			}
		}
	}

	@EventHandler
	public void on(InventoryMoveItemEvent event){
		Inventory destination = event.getDestination();
		Synergy.debug("ON INSERT GUI 1");
		if (destination instanceof Gui) {
			boolean onInsert = ((Gui) destination).onInsert(event.getItem(), destination);
			Synergy.debug("ON INSERT GUI 2");
			Synergy.debug(onInsert + " = ON INSERT GUI 2");
			event.setCancelled(!onInsert);
			event.setItem(onInsert ? event.getItem() : null);
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
	public void onItemInteract(PlayerInteractEvent e){
		if (e.getAction() != Action.PHYSICAL){
			for (InteractItem interactItem : InteractItem.getInteractItems()) {
				if (interactItem.isSimilar(e.getItem())){
					SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(e.getPlayer());
					e.setCancelled(true);
					InteractItem.getInteractItems().remove(interactItem);
					interactItem.onClick(synergyUser, e.getAction());
					break;
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
			holder.getMenu().getCurrentSessions().remove(synergyUser.getUuid());
			if (!holder.getMenu().onClose(topInventory, synergyUser)){
				return;
			}
			if (parent != null && !holder.getMenu().isIgnoringParent()) {
				getPlugin().getRunnableManager().runTaskLater("open parent gui", core ->
						parent.open(holder.getViewer()), 1L);
			}
		}
	}

	@Override
	public void onReload(String response) {

	}

}

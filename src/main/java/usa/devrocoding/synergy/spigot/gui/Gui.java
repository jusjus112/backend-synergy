package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public abstract class Gui {

	@Getter
	private final Core plugin;
	private final String name;
	private final GuiSize guiSize;
	private final Map<Integer, GuiElement> elements;
	private final Map<UUID, Inventory> currentSessions;
	
	public Gui(Core plugin, String name, GuiSize guiSize) {
		this.plugin = plugin;
		this.name = name;
		this.guiSize = guiSize;
		this.elements = Maps.newHashMap();
		this.currentSessions = Maps.newHashMap();
		
		plugin.getGUIManager().getMenus().add(this);

		setup();
	}

	public abstract void setup();
	
	public Inventory open(Player player) {
		Inventory inventory = Bukkit.createInventory(null, guiSize.getSlots(), name);
		
		for(Entry<Integer, GuiElement> element : elements.entrySet()) {
			inventory.setItem(element.getKey(), element.getValue().getIcon(player));
		}
		
		player.openInventory(inventory);
		
		for(Gui menu : plugin.getGUIManager().getMenus()) {
			if(!menu.equals(this)) {
				menu.getCurrentSessions().remove(player.getUniqueId());
			}
		}
		
		currentSessions.put(player.getUniqueId(), inventory);
		player.updateInventory();
		
		return inventory;
	}

	public void close(Player player) {
		player.closeInventory();
		currentSessions.remove(player.getUniqueId());
	}
	
	public void addElement(GuiElement element) {
		for (int i = 0; i < this.guiSize.getSlots(); ++i) {
			if (!this.elements.containsKey(i)) {
				this.addElement(i, element);
				return;
			}
		}
	}

	public void line(int first, int last, GuiElement element){
		for (int i=first;i<=last;i++){
			addElement(i, element);
		}
	}
	
	public boolean isFull() {
		for (int i = 0; i < this.guiSize.getSlots(); ++i) {
			if (this.getElement(i) == null) {
				return false;
			}
		}

		return true;
	}

	public void addElement(int slot, GuiElement menuElement) {
		if (slot >= 0) {
			this.elements.put(Integer.valueOf(slot), menuElement);
		}
	}
	
	public void removeElement(int slot) {
		if(this.elements.containsKey(slot)) {
			this.elements.remove(slot);
		}
	}
	
	public GuiElement getElement(int slot) {
		if(elements.containsKey(slot)) {
			return elements.get(slot);
		}
		
		return null;
	}
	
	public String getName() {
		return name;
	}

	public int getSize() {
		return guiSize.getSlots();
	}

	public Map<Integer, GuiElement> getElements() {
		return elements;
	}

	public Map<UUID, Inventory> getCurrentSessions() {
		return currentSessions;
	}
	
}

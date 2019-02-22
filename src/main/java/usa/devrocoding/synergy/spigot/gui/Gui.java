package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.*;
import java.util.Map.Entry;

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
		SynergyUser synergyUser = Core.getPlugin().getUserManager().getUser(player);

		for(Entry<Integer, GuiElement> element : elements.entrySet()) {
			inventory.setItem(element.getKey(), element.getValue().getIcon(synergyUser));
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

	public void surroundWith(GuiElement item) {
		if (getSize() >= GuiSize.THREE_ROWS.getSlots()) {
			Integer[] walls = new Integer[]{9,17,18,26,27,35,36,44};
			List<Integer> slots = new ArrayList<>();
			final int size = this.guiSize.getSlots();

			// Outer walls
			int csize = size;
			for(int i=0;i<9;i++){
				slots.add(--csize);
				slots.add(i);
			}

			slots.addAll(Arrays.asList(walls));
			Object[] slotsArray = slots.toArray();
			Arrays.sort(slotsArray);

			for (Object obj : slotsArray) {
				int i = Integer.valueOf(obj.toString());

				if (i >= size){
					break;
				}
				if (getElement(i) == null) {
					addElement(i, item);
				}
			}
		}
	}

	public List<Integer> getCenterInput(){
		List<Integer> places = new ArrayList<>();

		switch (this.guiSize){
			case ONE_ROW:
				places.addAll(Arrays.asList(new Integer[]{1,2,3,4,5,6,7}));
				break;
			case TWO_ROWS:
				places.addAll(Arrays.asList(new Integer[]{1,2,3,4,5,6,7,10,11,12,13,14,15,16}));
				break;
			case THREE_ROWS:
				places.addAll(Arrays.asList(new Integer[]{10,11,12,13,14,15,16}));
				break;
			case FOUR_ROWS:
				places.addAll(Arrays.asList(new Integer[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25}));
				break;
			case FIVE_ROWS:
				places.addAll(Arrays.asList(new Integer[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,32,33,34}));
				break;
			case SIX_ROWS:
				places.addAll(Arrays.asList(new Integer[]{10,11,12,13,14,15,16,19,20,21,22,23,24,25,28,29,30,31,32,32,33,34,37,38,39,40,41,42,43}));
				break;
		}
		return places;
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

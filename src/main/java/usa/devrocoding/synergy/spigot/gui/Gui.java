package usa.devrocoding.synergy.spigot.gui;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.util.*;
import java.util.Map.Entry;

public abstract class Gui {

	@Getter
	private final Core plugin;
	private final String name;
	private final GuiSize guiSize;
	@Getter
	private Gui backGui = null;
	private final Map<Integer, GuiElement> elements;
	private final Map<UUID, Inventory> currentSessions;
	
	public Gui(Core plugin, String name, GuiSize guiSize) {
		this(plugin, name, guiSize, true);
	}

	public Gui(Core plugin, String name, GuiSize guiSize, boolean setup) {
		this.plugin = plugin;
		this.name = name;
		this.guiSize = guiSize;
		this.elements = Maps.newHashMap();
		this.currentSessions = Maps.newHashMap();

		plugin.getGUIManager().getMenus().add(this);

		if (setup)
			setup();
	}

	public abstract void setup();
	public void onClose(Inventory inventory){}
	public boolean onInsert(ItemStack itemStack){
		return false;
	}

	public void insert(Inventory inventory, SynergyUser user){
		for(Entry<Integer, GuiElement> element : elements.entrySet()) {
			inventory.setItem(element.getKey(), element.getValue().getIcon(user));
		}
	}
	
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

	public Gui setBackGui(Gui backGui){
		this.backGui = backGui;
		return this;
	}

	public boolean hasBackGui(){
		return this.backGui != null;
	}

	public Gui getOuterClazz(){
		return this;
	}

	public GuiElement getBackGuiElement(){
		return getBackGuiElement("§c§l← Go Back", new ItemBuilder(Material.AIR));
	}

	public GuiElement getBackGuiElement(ItemBuilder alternative){
		return getBackGuiElement("§c§l← Go Back", alternative);
	}

	public GuiElement getBackGuiElement(String itemName, ItemBuilder alternative){
		return new GuiElement() {
			@Override
			public ItemStack getIcon(SynergyUser synergyUser) {
				if (!hasBackGui()){
					return alternative.build();
				}
				return new ItemBuilder(Material.ARROW)
						.setName("§7"+itemName)
						.build();
			}

			@Override
			public void click(SynergyUser synergyUser, ClickType clickType) {
				if (hasBackGui()) backGui.open(synergyUser.getPlayer());
			}
		};
	}
	
	public void addElement(GuiElement element) {
		for (int i = 0; i < this.guiSize.getSlots(); ++i) {
			if (!this.elements.containsKey(i)) {
				this.addElement(i, element);
				return;
			}
		}
	}

	public void addUnsafeElement(int slot, GuiElement menuElement) {
		try{
			this.elements.put(Integer.valueOf(slot), menuElement);
		}catch (Exception e){
			Synergy.error(e.getMessage());
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

	public int getCenter(){
		switch (this.guiSize){
			case TWO_ROWS:
				return 4;
			case THREE_ROWS:
				return 13;
			case FOUR_ROWS:
				return 13;
			case FIVE_ROWS:
				return 22;
			case SIX_ROWS:
				return 22;
		}
		return 4;
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

package usa.devrocoding.synergy.spigot.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class GuiElement {

	public abstract ItemStack getIcon(Player player);

	public abstract void click(Player player, ClickType clickType);
	
}

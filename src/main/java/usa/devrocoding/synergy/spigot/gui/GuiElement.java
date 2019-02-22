package usa.devrocoding.synergy.spigot.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class GuiElement {

	public abstract ItemStack getIcon(SynergyUser synergyUser);

	public abstract void click(SynergyUser player, ClickType clickType);
	
}

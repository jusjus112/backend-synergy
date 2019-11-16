package usa.devrocoding.synergy.spigot.gui.object;

import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class GuiInteractElement {

    public abstract ItemStack getIcon(SynergyUser synergyUser);

    public abstract void click(SynergyUser player, Action action);

}

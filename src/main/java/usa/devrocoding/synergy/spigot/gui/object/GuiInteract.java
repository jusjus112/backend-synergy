package usa.devrocoding.synergy.spigot.gui.object;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.inventory.Inventory;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Map;

public abstract class GuiInteract {

    @Getter
    private final Core plugin;
    @Getter
    private final String name;
    @Getter
    private final Map<GuiInteractElement, Integer> elements;

    public GuiInteract(Core plugin) {
        this(plugin, true);
    }

    public GuiInteract(Core plugin, boolean setup) {
        this.plugin = plugin;
        this.name = "Player Inventory";
        this.elements = Maps.newHashMap();

        plugin.getGUIManager().getInteractMenus().add(this);

        if (setup)
            setup();
    }

    public abstract void setup();

    public void insert(Inventory inventory, SynergyUser user){
        for(Map.Entry<GuiInteractElement, Integer> element : elements.entrySet()) {
            inventory.setItem(element.getValue(), element.getKey().getIcon(user));
        }
    }

    public void addElement(GuiInteractElement menuElement, int slot) {
        if (slot >= 0) {
            if (!this.elements.containsValue(slot)) this.elements.put(menuElement, Integer.valueOf(slot));
        }
    }

}

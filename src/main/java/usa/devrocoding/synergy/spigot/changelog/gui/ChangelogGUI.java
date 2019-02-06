package usa.devrocoding.synergy.spigot.changelog.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilInventory;

public class ChangelogGUI extends Gui {

    public ChangelogGUI(Core plugin){
        super(plugin, "Changelog GUI", GuiSize.THREE_ROWS);
    }

    @Override
    public void setup() {
        addElement(10, new GuiElement() {
            @Override
            public ItemStack getIcon(Player player) {
                return new ItemBuilder(Material.EMPTY_MAP)
                        .setName("§test123")
                        .build();
            }

            @Override
            public void click(Player player, ClickType clickType) {

            }
        });
    }
}

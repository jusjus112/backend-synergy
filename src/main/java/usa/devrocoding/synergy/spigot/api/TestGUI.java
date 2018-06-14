package usa.devrocoding.synergy.spigot.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class TestGUI extends Gui {

    public TestGUI(SpigotAPI plugin){
        super(plugin, "Party Test Menu", GuiSize.ONE_ROW);

        setup();
    }

    private void setup(){
        addElement(new GuiElement() {
            @Override
            public ItemStack getIcon(Player player) {
                return new ItemBuilder(Material.ANVIL)
                        .setName(C.ITEM_TITLE.color()+"Click to warp")
                        .addLore("Test Lore", "Just for the show")
                        .build();
            }

            @Override
            public void click(Player player, ClickType clickType) {
                // TODO: Mechanics for the item.
            }
        });
    }

}

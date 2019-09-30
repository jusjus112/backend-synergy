package usa.devrocoding.synergy.spigot.changelog.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.changelog.object.ChangelogType;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilInventory;

public class ChangelogMainGUI extends Gui {

    public ChangelogMainGUI(Core plugin){
        super(plugin, "Changelog GUI", GuiSize.THREE_ROWS);
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                        .setName(" ")
                        .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {}
        });

        addElement(12, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.MAGMA_CREAM)
                        .setName("§e§lSynergy's Changelog")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {

            }
        });

        addElement(14, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.JUKEBOX)
                        .setName("§6§lServer's Changelog")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {
                new ChangelogGUI(getPlugin(), ChangelogType.SERVER).open(synergyUser.getPlayer());
            }
        });
    }
}

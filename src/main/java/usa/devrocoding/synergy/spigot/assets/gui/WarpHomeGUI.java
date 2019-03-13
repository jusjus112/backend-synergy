package usa.devrocoding.synergy.spigot.assets.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.gui.HomeGUI;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.SkullItemBuilder;
import usa.devrocoding.synergy.spigot.warp.gui.WarpGUI;

public class WarpHomeGUI extends Gui {

    public WarpHomeGUI(Core plugin){
        super(plugin, "Warp & Home GUI", GuiSize.FIVE_ROWS);
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .setDamage((short) 15)
                        .setName(" ")
                        .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {}
        });

        addElement(11, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.EYE_OF_ENDER)
                        .setName("§b§lServer Warps")
                        .setLore(" ", "Click to see all the", "&c&lSERVER &7Warps you can", "go to based on permissions.")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {
                new WarpGUI(getPlugin(), synergyUser).open(synergyUser.getPlayer());
            }
        });

        addElement(13, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new SkullItemBuilder(synergyUser.getPlayer())
                        .setName("&e&l"+synergyUser.getName())
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {

            }
        });

        addElement(15, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.COMPASS)
                        .setName("§b§lGo to spawn")
                        .setLore(" ", "Click to go to the spawn")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {

            }
        });

        addElement(31, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.BED)
                        .setName("&e&l"+synergyUser.getName()+"'s &7Homes")
                        .setLore(" ", "Click to see all your", "homes you have set")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType) {
                new HomeGUI(getPlugin(), synergyUser).open(synergyUser.getPlayer());
            }
        });
    }
}

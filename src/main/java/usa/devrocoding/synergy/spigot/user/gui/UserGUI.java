package usa.devrocoding.synergy.spigot.user.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.gui.HomeGUI;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.SkullItemBuilder;
import usa.devrocoding.synergy.spigot.warp.gui.WarpGUI;

public class UserGUI extends Gui {

    private SynergyUser user;

    public UserGUI(Core plugin, SynergyUser synergyUser){
        super(plugin, synergyUser.getName()+"'s Information", GuiSize.FIVE_ROWS);

        this.user = synergyUser;
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setMaterialData(new MaterialData((byte) 7))
                        .setName(" ")
                        .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {}
        });

        addElement(11, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.EYE_OF_ENDER)
                        .setName("§b§lPunishments")
                        .setLore(" ", "Click to see all the", "punishments from "+user.getName())
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        addElement(13, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new SkullItemBuilder(user.getPlayer())
                        .setName("&e&l"+user.getName())
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        addElement(15, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.COMPASS)
                        .setName("§b§lSomething.....")
                        .setLore("Well... This is awkward")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        addElement(31, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.BED)
                        .setName("&b&lAchievements")
                        .setLore(" ", "Click to see all "+user.getName()+"'s", "achievements they have earned.")
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                new HomeGUI(getPlugin(), synergyUser).open(synergyUser.getPlayer());
            }
        });
    }
}

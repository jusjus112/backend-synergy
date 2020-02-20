package usa.devrocoding.synergy.spigot.assets.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public abstract class ConfirmationGUI extends Gui {

    public ConfirmationGUI(Core plugin, String name){
        super(plugin, name, GuiSize.THREE_ROWS);
    }

    public abstract void onAccept(SynergyUser synergyUser);
    public abstract void onDisallow(SynergyUser synergyUser);

    @Override
    public void setup() {
        for(int i : getLeftSide()){
            addElement(i, new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .setMaterialData(new MaterialData((byte) 5))
                            .setName("§a§lYes!")
                            .build();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {
                    player.getPlayer().closeInventory();
                    onAccept(player);
                }
            });
        }
        for(int i : getRightSide()){
            addElement(i, new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .setMaterialData(new MaterialData((byte) 14))
                            .setName("§c§lNo")
                            .build();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {
                    player.getPlayer().closeInventory();
                    onDisallow(player);
                }
            });
        }
        for(int i : getMiddle()){
            addElement(i, new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .setMaterialData(new MaterialData((byte) 15))
                            .setName(" ")
                            .build();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {

                }
            });
        }
    }

    private int[] getLeftSide(){
        return new int[]{0,1,2,3,9,10,11,12,18,19,20,21};
    }

    private int[] getRightSide(){
        return new int[]{5,6,7,8,14,15,16,17,23,24,25,26};
    }

    private int[] getMiddle(){
        return new int[]{4,13,22};
    }
}

package usa.devrocoding.synergy.spigot.warp.gui;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.warp.WarpManager;
import usa.devrocoding.synergy.spigot.warp.object.Warp;

public class WarpGUI extends Gui {

    @Getter
    private SynergyUser user;

    public WarpGUI(Core plugin, SynergyUser user){
        super(plugin, "Server Warps", GuiSize.FIVE_ROWS, false);
        this.user = user;

        setup();
    }

    @Override
    public void setup() {
        surroundWith(new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE)
                    .setDamage((byte) 7)
                    .setName(" ")
                    .build();
            }
            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {}
        });

        WarpManager warpManager = Core.getPlugin().getWarpManager();
        int index = 0;

        if (warpManager.getWarps().size() <= 0){
            addNoWarpsItem();
            return;
        }

        for(Warp warp : warpManager.getWarps()){
            // No warps allowed called Spawn
            if (warp.getName().contains("tutorial")||warp.getName().contains("spawn")||warp.getName().contains("cell")) {
                continue;
            }

//            if (this.getUser().hasPermission(warp.getPermissionNode(), false)) {
                int place = getCenterInput().get(index);

                addElement(place, new GuiElement() {
                    @Override
                    public ItemStack getIcon(SynergyUser synergyUser) {
                        return new ItemBuilder(Material.EYE_OF_ENDER)
                                .setName("&d&l" + warp.getName())
                                .build();
                    }

                    @Override
                    public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                        warp.teleportTo(synergyUser);
                        synergyUser.info("I've teleported you to the warp '"+warp.getName()+"'");
                    }
                });
                index++;
//            }
        }
        if (index <= 0){
            addNoWarpsItem();
        }
    }

    private void addNoWarpsItem(){
        addElement(22, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.BARRIER)
                    .setName("No warps have been found :(")
                    .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });
    }
}

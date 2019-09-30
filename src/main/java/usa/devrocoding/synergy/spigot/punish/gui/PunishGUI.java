package usa.devrocoding.synergy.spigot.punish.gui;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.punish.PunishCategory;
import usa.devrocoding.synergy.spigot.punish.PunishIcon;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.SkullItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.util.List;

public class PunishGUI extends Gui {

    public PunishGUI(Core plugin, SynergyUser user){
        super(plugin, "Punish player "+user.getName(), GuiSize.SIX_ROWS);
    }

    @Override
    public void setup() {
        addElement(4, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return getHeader(synergyUser, "You're just fat");
            }

            @Override
            public void click(SynergyUser player, ClickType clickType) {

            }
        });

        for (PunishCategory category : PunishCategory.values()) {
            addElement(category.getInventoryColumIndex(), new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return category.getItemStack();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {

                }
            });
        }

        for(PunishIcon icon : PunishIcon.values()){
            addElement(icon.getCategory().getFirstFreeSlots(this), new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return icon.getItemStack();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {

                }
            });
        }
    }

    private static ItemStack getHeader(SynergyUser punished, String reason) {
        List<String> l = UtilString.trimList(reason, "§f");
        l.set(0, "§7Reden: " + l.get(0));
        l.add("§7Straffen: §f" + punished.getPunishments().size());
        return new SkullItemBuilder(punished.getPlayer()).setLore(l).setName("§e§l"+punished.getName()).build();
    }
}

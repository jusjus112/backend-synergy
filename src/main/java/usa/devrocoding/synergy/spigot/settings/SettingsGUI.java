package usa.devrocoding.synergy.spigot.settings;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.language.LanguageStrings;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class SettingsGUI extends Gui{

    private SynergyUser synergyUser;

    public SettingsGUI(SynergyUser synergyUser){
        super(Core.getPlugin(), synergyUser.getName()+"''s "+synergyUser.getLanguage().get(LanguageStrings.SETTINGS_GUINAME), GuiSize.SIX_ROWS);
        this.synergyUser = synergyUser;
    }

    @Override
    public void setup(){
        line(0, 8, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setMaterialData(new MaterialData((byte) 7)).setName(" ").build();
            }

            @Override public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {}
        });

        addElement(25, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
//                return new ItemBuilder(UtilItem.getPlayerSkull("§e"+synergyUser.getLanguage().getList(LanguageStrings.SETTINGS_ITEM_LANGUAGE_NAME), "0qt")).setLore(
//                        synergyUser.getLanguage().getList(LanguageStrings.SETTINGS_ITEM_LANGUAGE_LORE)
//                ).build();
                return new ItemBuilder(Material.EYE_OF_ENDER).build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        addElement(25, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
//                return new ItemBuilder(UtilItem.getPlayerSkull("§e"+synergyUser.getLanguage().getList(LanguageStrings.SETTINGS_ITEM_FRIENDS_NAME), "0qt")).setLore(
//                        synergyUser.getLanguage().getList(LanguageStrings.SETTINGS_ITEM_FRIENDS_LORE)
//                ).build();
                return new ItemBuilder(Material.EYE_OF_ENDER).build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        line(45, 53, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setMaterialData(new MaterialData((byte) 7)).setName(" ").build();
            }

            @Override public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {}
        });
    }

}

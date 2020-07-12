package usa.devrocoding.synergy.spigot.punish.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.gui.ConfirmationGUI;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class PunishmentsGUI extends Gui{

    private List<Punishment> punishments;

    public PunishmentsGUI(Core plugin, List<Punishment> punishments, String userName){
        super(plugin, userName+"'s Punishments", GuiSize.TWO_ROWS, false);

        this.punishments = punishments;
        setup();
    }

    @Override
    public void setup() {
        List<Punishment> punishments = this.punishments;
        if (punishments.size() > 0) punishments.sort((o1, o2) -> Boolean.compare(o1.isActive(), o2.isActive()));
        for(Punishment punishment : this.punishments){
            addElement(new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    Material material = Material.REDSTONE_BLOCK;
                    ChatColor color = ChatColor.RED;
                    if (punishment.isActive()){
                        material = Material.GREEN_SHULKER_BOX;
                        color = ChatColor.GREEN;
                    }
                    List<String> lore = new ArrayList<>();
                    lore.add("§ePunishment: §7"+punishment.getType().name());
                    lore.add("§eWeight: §7"+punishment.getLevel().name());
                    lore.add(" ");
                    lore.add("§eStaff: §b"+ Bukkit.getOfflinePlayer(punishment.getPunisher()).getName());
                    lore.add("§eCategory: §7"+punishment.getCategory().getName());
                    lore.add("§eLevel §7"+punishment.getCategory().getName());
                    lore.add("§ePunished On: §7"+punishment.getIssuedFormatted());
                    lore.add("§eTill: §b"+punishment.getPlainerMessage());
                    lore.add("§eActive: §7"+(punishment.isActive()?"Yes":"No"));
                    if (punishment.isActive()) {
                        lore.add(" ");
                        lore.add("§e§lCLICK §7to deactivate this punishment");
                    }

                    return new ItemBuilder(material)
                            .setName(color+punishment.getCategory().getName())
                            .setLore(lore)
                            .build();
                }

                @Override
                public void click(SynergyUser player, ClickType clickType) {
                    if (punishment.isActive()){
                        new ConfirmationGUI(getPlugin(), "Are you sure?"){
                            @Override
                            public void onAccept(SynergyUser synergyUser) {
                                punishment.setActive(false);
                                Core.getPlugin().getPunishManager().updatePunishment(punishment);
                            }

                            @Override
                            public void onDisallow(SynergyUser synergyUser) {
//                                synergyUser.getPlayer().closeInventory();
                                PunishmentsGUI.this.open(synergyUser.getPlayer());
                            }
                        }.open(player.getPlayer());
                    }
                }
            });
        }
    }
}

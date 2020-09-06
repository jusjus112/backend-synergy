package usa.devrocoding.synergy.spigot.punish.gui;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.gui.ConfirmationGUI;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;
import usa.devrocoding.synergy.spigot.punish.PunishCategory;
import usa.devrocoding.synergy.spigot.punish.PunishIcon;
import usa.devrocoding.synergy.spigot.punish.PunishLevel;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.SkullItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilString;
import usa.devrocoding.synergy.spigot.utilities.UtilTime;

import java.util.List;

public class PunishGUI extends Gui {

    private SynergyUser user, punisher;

    public PunishGUI(Core plugin, SynergyUser user, SynergyUser punisher){
        super(plugin, "Punish player "+user.getName(), GuiSize.SIX_ROWS, false);

        this.user = user;
        this.punisher = punisher;

        setup();
    }

    @Override
    public void setup() {
        for(int i=0;i<9;i++){
            addElement(i, new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return new ItemBuilder(Material.STAINED_GLASS_PANE).setMaterialData(new MaterialData((byte) 7)).setName(" ").build();
                }

                @Override
                public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                }
            });
        }

        addElement(0, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.WRITTEN_BOOK)
                        .setName("§eAll punishments from "+user.getName())
                        .addLore(" ", "Total Punishments: "+user.getPunishments().size())
                        .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                new PunishmentsGUI(getPlugin(), user.getPunishments(),  user.getName()).open(synergyUser.getPlayer());
            }
        });

        addElement(4, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return getHeader(user);
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        });

        for (PunishCategory category : PunishCategory.values()) {
            addElement(category.getInventoryColumIndex(), new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return getItemStack(category);
                }

                @Override
                public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

                }
            });
        }

        for(PunishIcon icon : PunishIcon.values()){
            addElement(icon.getCategory().getFirstFreeSlots(this), new GuiElement() {
                @Override
                public ItemStack getIcon(SynergyUser synergyUser) {
                    return getItemStackIcon(icon);
                }

                @Override
                public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                    new ConfirmationGUI(getPlugin(), "Are you sure?"){
                        @Override
                        public void onAccept(SynergyUser synergyUser) {
                            System.out.println("HAS PUNISHED");
                            getPlugin().getPunishManager().punish(user, punisher, icon);
                        }

                        @Override
                        public void onDisallow(SynergyUser synergyUser) {
                            PunishGUI.this.open(synergyUser.getPlayer());
                        }
                    }.open(synergyUser.getPlayer());
                }
            });
        }
    }

    private static ItemStack getHeader(SynergyUser punished) {
        return new SkullItemBuilder(punished.getPlayer())
                .addLore("§7Punishments: §f" + punished.getPunishments().size())
                .setName("§e§l"+punished.getName())
                .build();
    }

    private org.bukkit.Material getMaterialCategory(PunishCategory category){
        switch (category){
            case CHAT:
                return org.bukkit.Material.PAPER;
            case OTHER:
                return  org.bukkit.Material.IRON_PICKAXE;
            case GENERAL:
                return org.bukkit.Material.HOPPER;
            case HACKING:
                return org.bukkit.Material.IRON_SWORD;
        }
        return org.bukkit.Material.PAPER;
    }

    private org.bukkit.inventory.ItemStack getItemStack(PunishCategory category){
        return new ItemBuilder(getMaterialCategory(category))
                .setName("§c"+category.getName())
                .addLore("  ")
                .addLore(category.getDescription())
                .build();
    }

    public org.bukkit.inventory.ItemStack getItemStackIcon(PunishIcon punishIcon){
        return new ItemBuilder(getItemStackLevel(punishIcon.getPunishLevel()).getType())
                .setName("§b"+punishIcon.getPunishLevel().getName())
                .addLore("Straffen: "+ UtilTime.simpleTimeFormat(punishIcon.getPunishTime()) + " "+punishIcon.getType().name())
                .addLore(punishIcon.getDescription())
                .build();
    }

    public org.bukkit.inventory.ItemStack getItemStackLevel(PunishLevel punishLevel) {
        switch (punishLevel){
            case WARNING:
                return new org.bukkit.inventory.ItemStack(org.bukkit.Material.REDSTONE);
            case ONE:
                return new ItemBuilder(Material.WATER_LILY).build();
            case TWO:
                return new ItemBuilder(org.bukkit.Material.ENDER_PEARL).build();
            case THREE:
                return new ItemBuilder(Material.EYE_OF_ENDER).build();
            case PermanentMute:
                return new org.bukkit.inventory.ItemStack(Material.BOOK_AND_QUILL);
            case PermanentBan:
                return new org.bukkit.inventory.ItemStack(org.bukkit.Material.REDSTONE_BLOCK);
        }
        return new ItemBuilder(Material.WATER_LILY).build();
    }
}

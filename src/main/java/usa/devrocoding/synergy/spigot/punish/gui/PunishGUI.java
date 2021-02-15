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

    private final SynergyUser user;
    private final SynergyUser punisher;

    public PunishGUI(Core plugin, SynergyUser user, SynergyUser punisher){
        super(plugin, "Punish player "+user.getName(), GuiSize.SIX_ROWS, false);

        this.user = user;
        this.punisher = punisher;

        setup();
    }

    @Override
    public void setup() {
        line(0, 8, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((byte) 10)
                    .setName(" ")
                    .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
            }
        });
        line(9, 17, new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setDamage((byte) 15)
                    .setName(" ")
                    .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
            }
        });

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
                Gui punishmentsGui = new PunishmentsGUI(getPlugin(), user.getPunishments(),  user.getName());

                punishmentsGui.setParent(PunishGUI.this);
                punishmentsGui.open(synergyUser.getPlayer());
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
                    if (synergyUser.getRank().isLowerThan(icon.getNeededRank())){
                        return getNoAccessItem(icon);
                    }
                    return getItemStackIcon(icon);
                }

                @Override
                public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {
                    if (synergyUser.getRank().isLowerThan(icon.getNeededRank())){
                        return;
                    }
                    new ConfirmationGUI(getPlugin(), "Are you sure?"){
                        @Override
                        public void onAccept(SynergyUser synergyUser) {
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
            .setName("§e§l"+punished.getName())
            .addLore(
                "§7Punishments: §f" + punished.getPunishments().size(),
                "§7Status: " + (punished.isOnline() ? "§aOnline" : "§cOffline")
            )
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
                .setName("§a§l"+category.getName().toUpperCase())
                .addLore("  ")
                .addLore(category.getDescription())
                .build();
    }

    public org.bukkit.inventory.ItemStack getItemStackIcon(PunishIcon punishIcon){
        return getItemStackLevel(punishIcon.getPunishLevel())
                .setName("§b§l"+punishIcon.getPunishLevel().getName().toUpperCase() +
                    (punishIcon.getName() != null ? " §7○ §e§l" + punishIcon.getName() : ""))
                .addLore("§e"+ (punishIcon.getPunishTime() > 0 ? UtilTime.simpleTimeFormat(punishIcon.getPunishTime()) : "Permanent") + " "+punishIcon.getType().name())
                .addLore(" ")
                .addLore(punishIcon.getDescription())
                .build();
    }

    public org.bukkit.inventory.ItemStack getNoAccessItem(PunishIcon punishIcon){
        return new ItemBuilder(Material.BARRIER)
            .setName("§c§l"+punishIcon.getPunishLevel().getName().toUpperCase() +
                (punishIcon.getName() != null ? " §7○ §c§l" + punishIcon.getName() : ""))
            .addLore("§e"+ (punishIcon.getPunishTime() > 0 ? UtilTime.simpleTimeFormat(punishIcon.getPunishTime()) : "Permanent") + " "+punishIcon.getType().name())
            .addLore(" ")
            .addLore(
                "§cYou do not have access to this strength.",
                "§cYour rank is not high enough.",
                "§cYou need to contact a " + punishIcon.getNeededRank().getPrefix()
            )
            .build();
    }

    public ItemBuilder getItemStackLevel(PunishLevel punishLevel) {
        switch (punishLevel){
            case WARNING:
                return new ItemBuilder(Material.REDSTONE);
            case ONE:
                return new SkullItemBuilder("78a42df06fc916de110f61bd76eddbf58ed4249fce5ee51c219ec75a37b414");
            case TWO:
                return new SkullItemBuilder("1ef134f0efa88351b837f7c087afe1b3fb36435ab7d746fa37c0ef155e4f29");
            case THREE:
                return new SkullItemBuilder("1965e9c57c14c95c84e622e5306e1cf23bc5f1e47ac791f3d357b5ae8cded24");
            case PermanentMute:
                return new ItemBuilder(Material.BOOK_AND_QUILL);
            case PermanentBan:
                return new SkullItemBuilder("ff9d9de62ecae9b798555fd23e8ca35e2605291939c1862fe79066698c9508a7");
        }
        return new ItemBuilder(Material.WATER_LILY);
    }
}

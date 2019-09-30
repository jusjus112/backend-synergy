package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public enum PunishCategory {

    Algemeen("General",
            Material.HOPPER, new String[] { "You're using this category,",
            "if it's not under one of the other categories." },
            new Integer[] { 19, 28, 37, 46 }),
    Chat("Chat", Material.PAPER,
            new String[] { "When a player breaks the chat rules." },
            new Integer[] { 21, 30, 39, 48 }),
    hacking("Hacking/mods", Material.IRON_SWORD,
            new String[] { "When a player uses a hackclient", "or a whitelisted mod like x-ray." },
            new Integer[] { 23, 32, 41, 50 }),
    Overig("Other", Material.IRON_PICKAXE,
            new String[] { "All the other options like",
                    "warnings, mutes and perm bans." },
            new Integer[] { 25, 34, 43, 52 });

    @Getter
    private String name;
    private Material material;
    @Getter
    private String[] description;
    @Getter
    private Integer[] slots;

    PunishCategory(String name, Material material, String[] description, Integer[] slots) {
        this.name = name;
        this.material = material;
        this.description = description;
        this.slots = slots;
    }

    public int getColumIndex() {
        for (int i = 0; i < PunishCategory.values().length; i++) {
            if (PunishCategory.values()[i] == this)
                return i + 1;
        }
        return -1;
    }

    public int getInventoryColumIndex() {
        return 8 + 2 * getColumIndex();
    }
    public int getFirstFreeSlots(Gui inv) {
        for (Integer i : getSlots()) {
            if (!inv.getElements().containsKey(i))
                return i;
        }
        return -1;
    }


    public ItemStack getItemStack(){
        return new ItemBuilder(this.material)
                .setName("§c"+getName())
                .addLore("  ")
                .addLore(description)
                .build();
    }


}

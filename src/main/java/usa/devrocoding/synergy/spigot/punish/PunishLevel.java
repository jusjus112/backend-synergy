package usa.devrocoding.synergy.spigot.punish;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public enum PunishLevel {

    one(
            "Strength 1",
            new ItemBuilder(Material.LILY_PAD).build(),
            1
    ),
    two(
            "Strength 2",
            new ItemBuilder(Material.ENDER_PEARL).build(),
            1
    ),
    three(
            "Strength 3",
            new ItemBuilder(Material.ENDER_EYE).build(),
            1
    ),
    warning(
            "Waarschuwing",
            new ItemStack(Material.REDSTONE),
            3
    ),
    permanentMute(
            "Pernamenten Mute",
            new ItemStack(Material.LEGACY_BOOK_AND_QUILL),
            3
    ),
    permanentBan(
            "Pernamenten Ban",
            new ItemStack(Material.REDSTONE_BLOCK),
            3
    );

    private String name;
    private ItemStack icon;
    private int severity;

    private PunishLevel(String name, ItemStack icon, int severity) {
        this.name = name;
        this.icon = icon;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public ItemStack getItemStack() {
        return icon;
    }

    public int getSeverity() {
        return severity;
    }

}

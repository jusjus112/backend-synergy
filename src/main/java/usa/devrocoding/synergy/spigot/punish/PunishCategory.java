package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.gui.Gui;

@Getter
public enum PunishCategory {

    GENERAL("General",
            new String[] { "You're using this category,",
            "if it's not under one of the other categories." },
            new Integer[] { 19, 28, 37, 46 }),
    CHAT("Chat",
            new String[] { "When a player breaks the chat rules." },
            new Integer[] { 21, 30, 39, 48 }),
    HACKING("Hacking/mods",
            new String[] { "When a player uses a hackclient", "or a whitelisted mod like x-ray." },
            new Integer[] { 23, 32, 41, 50 }),
    OTHER("Other",
            new String[] { "All the other options like",
                    "warnings, mutes and perm bans." },
            new Integer[] { 25, 34, 43, 52 });

    private final String name;
    private final String[] description;
    private final Integer[] slots;

    PunishCategory(String name, String[] description, Integer[] slots) {
        this.name = name;
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
}

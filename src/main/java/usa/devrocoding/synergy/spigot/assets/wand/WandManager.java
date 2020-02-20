package usa.devrocoding.synergy.spigot.assets.wand;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.wand.listener.BlockWandListener;
import usa.devrocoding.synergy.spigot.assets.wand.object.SelectorWand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.util.HashMap;
import java.util.Map;

public class WandManager extends Module {

    @Getter
    private Map<SynergyUser, SelectorWand> selectorWands = new HashMap<>();

    public WandManager(Core plugin){
        super(plugin, "Wand Manager", false);

        registerListener(
                new BlockWandListener()
        );
    }

    @Override
    public void reload(String response) {

    }

    public ItemStack getWand(){
        return new ItemBuilder(Material.GOLD_AXE)
                .setName("§6Wand of Selections")
                .setLore(
                        "With this magic wand you are",
                        "able to make selections based",
                        "on the worldedit selection wand.",
                        " ",
                        "§eLEFT CLICK §7for setting the first position",
                        "§eRIGHT CLICK §7for setting the second position"
                )
                .build();
    }
}

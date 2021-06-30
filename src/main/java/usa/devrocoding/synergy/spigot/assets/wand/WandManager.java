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

    public WandManager(Core plugin){
        super(plugin, "Wand Manager", false);

        registerListener(
                new BlockWandListener()
        );
    }

    @Override
    public void onReload(String response) {

    }
}

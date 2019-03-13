package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullItemBuilder extends ItemBuilder{

    private SkullMeta skullMeta;

    public SkullItemBuilder(Player player){
        super(Material.SKULL_ITEM);

        this.skullMeta = (SkullMeta) getItemMeta();
        this.skullMeta.setOwner(player.getName());
        this.setItemMeta(this.skullMeta);
    }

}

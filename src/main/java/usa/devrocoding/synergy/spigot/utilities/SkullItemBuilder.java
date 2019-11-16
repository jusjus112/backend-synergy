package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder{

    private SkullMeta skullMeta;

    public SkullItemBuilder(Player player){
        super(Material.PLAYER_HEAD);

        this.skullMeta = (SkullMeta) getItemMeta();
        this.skullMeta.setOwningPlayer(player);
        this.setItemMeta(this.skullMeta);
    }

    public SkullItemBuilder(String uuid){
        super(Material.PLAYER_HEAD);

        this.skullMeta = (SkullMeta) getItemMeta();
        this.skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        this.setItemMeta(this.skullMeta);
    }

}

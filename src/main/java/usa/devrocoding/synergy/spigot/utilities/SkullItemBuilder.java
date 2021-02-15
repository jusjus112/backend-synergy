package usa.devrocoding.synergy.spigot.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder{

    private final SkullMeta skullMeta;

    public SkullItemBuilder(Player player){
        super(Material.SKULL_ITEM, 1, (byte) 3);

        this.skullMeta = (SkullMeta) getItemMeta();
        this.skullMeta.setOwningPlayer(player);
        this.initItemMeta(this.skullMeta);
    }

    public SkullItemBuilder(UUID uuid){
        super(Material.SKULL_ITEM, 1, (byte) 3);

        this.skullMeta = (SkullMeta) getItemMeta();
        this.skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        this.initItemMeta(this.skullMeta);
    }

    public SkullItemBuilder(String textureURL){
        super(Material.SKULL_ITEM, 1, (byte) 3);
        this.skullMeta = (SkullMeta) getItemMeta();

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64
            .getEncoder().encode(String.format(
                "{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/%s\"}}}",
                textureURL).getBytes()
            );
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = this.skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(this.skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }

        this.initItemMeta(this.skullMeta);
    }

}

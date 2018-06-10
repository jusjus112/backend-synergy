package usa.devrocoding.synergy.spigot.utilities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class UtilItem {

    public static ItemStack getSkull(String name, String url) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(name);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static ItemStack getPlayerSkull(String name, String owner) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(name);
        headMeta.setOwner(owner);

        head.setItemMeta(headMeta);
        return head;
    }

    public static List<ItemStack> sortByAmount(List<ItemStack> oldItems) {
        List<Integer> amounts = new ArrayList<>();
        for (ItemStack i : oldItems) {
            if (i == null)
                continue;
            int amount = i.getAmount();
            if (amount == 0) {
                amount = 1;
            }
            amounts.add(i.getAmount());
        }
        Collections.sort(amounts);
        List<ItemStack> newItems = new ArrayList<>();
        for (Integer i : amounts) {
            if (oldItems.size() > 0) {
                for (ItemStack item : oldItems) {
                    if (item.getAmount() == i) {
                        newItems.add(item);
                        oldItems.remove(item);
                    }
                }
            }
        }
        return newItems;
    }

    public static boolean compareItems(ItemStack i1, ItemStack i2) {
        if (i1 == null || i2 == null)
            return false;
        if (i1.getType() != i2.getType())
            return false;
        if (!i1.hasItemMeta() && !i2.hasItemMeta())
            return true;
        if (!i1.getItemMeta().hasDisplayName() && !i2.getItemMeta().hasDisplayName())
            return true;
        if (!i1.getItemMeta().hasDisplayName() && i2.getItemMeta().hasDisplayName())
            return false;
        if (i1.getItemMeta().hasDisplayName() && !i2.getItemMeta().hasDisplayName())
            return false;
        if (i1.getItemMeta().getDisplayName().equals(i2.getItemMeta().getDisplayName())) {
            if (!i1.getItemMeta().hasLore() && !i2.getItemMeta().hasLore())
                return true;
            if (i1.getItemMeta().getLore() == null || i2.getItemMeta().getLore() == null)
                return true;
            if (i1.getItemMeta().getLore().equals(i2.getItemMeta().getLore()))
                return true;
        }
        return false;
    }
}

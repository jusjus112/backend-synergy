package usa.devrocoding.synergy.spigot.utilities;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBuilder {

    private ItemStack item;

    public PotionBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
    }

    public PotionBuilder(ItemStack item) {
        this.item = item;
    }

    public PotionBuilder addEffect(PotionEffect effect) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.addCustomEffect(effect, true);

        item.setItemMeta(potionMeta);

        return this;
    }

    public PotionBuilder setColor(Color color) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.setColor(color);

        item.setItemMeta(potionMeta);

        return this;
    }

    public PotionBuilder setName(String name) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.setDisplayName(name);

        item.setItemMeta(potionMeta);

        return this;
    }

    public PotionBuilder addItemFlags() {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.setUnbreakable(true);
        potionMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        potionMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        potionMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        potionMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(potionMeta);
        return this;
    }

    public PotionBuilder removeEffect(PotionEffectType type) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.removeCustomEffect(type);

        item.setItemMeta(potionMeta);

        return this;
    }

    public PotionBuilder setMainEffect(PotionEffectType type) {
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();

        potionMeta.setMainEffect(type);

        item.setItemMeta(potionMeta);

        return this;
    }

    public ItemStack build() {
        return item;
    }

}
package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemBuilder {

    private ItemStack itemStack;
    @Getter @Setter
    private ItemMeta itemMeta;

    public ItemBuilder(Material material){
        if (material == Material.SKULL_ITEM){
            this.itemStack = new ItemStack(material, 1, (short) 3);
        }else {
            this.itemStack = new ItemStack(material, 1);
        }
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack stack){
        this.itemStack = stack;
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount){
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount, int damage){
        this.itemStack = new ItemStack(material, amount, (short) damage);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public ItemBuilder setName(String displayName){
        this.itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemBuilder addLore(String... lore) {
        List<String> cur = this.itemMeta.getLore();

        if (cur == null) {
            cur = Lists.newArrayList();
        }

        for (String line : lore) {
            cur.add(line);
        }

        this.itemMeta.setLore(cur);

        return this;
    }

    public ItemBuilder setLore(String... lore){
        List<String> modifiedLore = new ArrayList<>();

        for(String line : lore) modifiedLore.add("§7"+ChatColor.translateAlternateColorCodes('&', line));

        this.itemMeta.setLore(modifiedLore);

        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        List<String> modifiedLore = new ArrayList<>();

        for(String line : lore) modifiedLore.add("§7"+ChatColor.translateAlternateColorCodes('&', line));

        this.itemMeta.setLore(modifiedLore);

        return this;
    }

    public ItemBuilder setAmount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDamage(int damage){
        this.itemStack.setDurability((short) damage);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level){
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments){
        this.itemStack.addUnsafeEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setType(Material type){
        this.itemStack.setType(type);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags){
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder setMaterialData(MaterialData materialData){
        this.itemStack.setData(materialData);
        return this;
    }

    public ItemBuilder hideEnchants() {
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack build(){
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}

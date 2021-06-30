package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagByte;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagFloat;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;
import usa.devrocoding.synergy.spigot.assets.XMaterial;
import usa.devrocoding.synergy.spigot.utilities.item.SynergyNBTCompound;

public class ItemBuilder extends ItemStack{

    public ItemMeta itemMeta;
    private List<String> lore;
    private SynergyNBTCompound synergyNBTCompound;

    public ItemBuilder(final Material material){
        this(material, 1);
    }

    public ItemBuilder(final XMaterial xMaterial){
        super(xMaterial.parseItem());
    }

    public ItemBuilder(final ItemStack stack){
        super(stack);
        initItemMeta();
        if (this.itemMeta.hasLore()){
            this.lore = this.itemMeta.getLore();
        }

//        if (stack instanceof CraftItemStack){
//
//        }
    }

    public ItemBuilder(final Material material, final int amount){
        this(material, amount, (byte) 0);
    }

    public ItemBuilder(final Material material, final int amount, final short damage){
        super(material, amount, damage);
    }

    public ItemBuilder setName(String displayName){
        initItemMeta();
        String t = ChatColor.translateAlternateColorCodes('&', displayName);
        this.itemMeta.setDisplayName(ChatColor.RESET + t);
        this.itemMeta.setLocalizedName(ChatColor.RESET + t);
        return this;
    }

    private void initItemMeta(){
        this.initItemMeta(getItemMeta());
    }

    public void initItemMeta(ItemMeta itemMeta){
        if (this.itemMeta == null){
            this.itemMeta = itemMeta;
        }
    }

    @Deprecated
    public ItemBuilder setLore(String... lore){
        addLore(lore);
        return this;
    }

    public ItemBuilder addLore(String... lore){
        initItemMeta();
        List<String> modifiedLore = new ArrayList<>();
        for(String line : lore) {
            modifiedLore.add("§7" + ChatColor.translateAlternateColorCodes('&', line));
        }
        if (this.lore == null){
            this.lore = Lists.newArrayList();
        }
        this.lore.addAll(modifiedLore);
        return this;
    }

    public ItemBuilder setLore(List<String> lore){
        addLore(lore.toArray(new String[lore.size()]));
        return this;
    }

    public ItemBuilder resetLore(){
        initItemMeta();
        this.itemMeta.setLore(Lists.newArrayList());
        return this;
    }

    public ItemBuilder setDamage(short damage){
        setDurability(damage);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags){
        initItemMeta();
        this.itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        initItemMeta();
        this.itemMeta.setUnbreakable(unbreakable);
        this.itemMeta.spigot().setUnbreakable(true);
        return this;
    }

    public ItemBuilder setMaterialData(MaterialData materialData){
        this.setData(materialData);
        return this;
    }

    public ItemBuilder hideEnchants() {
        this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    public ItemStack build(){
        if (getType() == Material.AIR){
            return this;
        }
        if (this.itemMeta != null) {
            this.itemMeta.setLore(this.lore);
            setItemMeta(this.itemMeta);
        }

        if (this.isNBTItem()){
            net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack
                .asNMSCopy(this);
            nmsStack.setTag(this.synergyNBTCompound);
//            Synergy.debug(nmsStack.getName()+" = NAME");

//            ItemStack item = CraftItemStack.asBukkitCopy(nmsStack);
//            Synergy.debug(item.getItemMeta()+" = ITEMMETA");
//            this.setItemMeta(item.getItemMeta());

        }
        return this;
    }

    public boolean isNBTItem(){
        return this.synergyNBTCompound != null;
    }

    private SynergyNBTCompound getSynergyNBTCompound(){
        if (this.synergyNBTCompound == null) {
            this.synergyNBTCompound = new SynergyNBTCompound();
        }
        return this.synergyNBTCompound;
    }

    private void insert(String s, NBTBase base){
        this.getSynergyNBTCompound().set(s, base);
    }

    public ItemBuilder addNBTTag(String s1, String s2){
        this.insert(s1, new NBTTagString(s2));
        return this;
    }

    public ItemBuilder addNBTTag(String s, int i){
        this.insert(s, new NBTTagInt(i));
        return this;
    }

    public ItemBuilder addNBTTag(String s, byte b){
        this.insert(s, new NBTTagByte(b));
        return this;
    }

    public ItemBuilder addNBTTag(String s, double d){
        this.insert(s, new NBTTagDouble(d));
        return this;
    }

    public ItemBuilder addNBTTag(String s, float f){
        this.insert(s, new NBTTagFloat(f));
        return this;
    }

    public ItemBuilder addNBTTag(String s, long l){
        this.insert(s, new NBTTagLong(l));
        return this;
    }

}

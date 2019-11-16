package usa.devrocoding.synergy.spigot.utilities;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class LeatherArmorBuilder extends ItemBuilder{

    public LeatherArmorBuilder(LeatherArmor leatherArmor, Color color){
        super(leatherArmor.getMaterial());

        LeatherArmorMeta leatherArmorMeta;

        leatherArmorMeta = (LeatherArmorMeta) getItemMeta();
        leatherArmorMeta.setColor(color);
        setItemMeta(leatherArmorMeta);
    }

    public enum LeatherArmor{
        HELMET(Material.LEATHER_HELMET),
        CHESTPLATE(Material.LEATHER_CHESTPLATE),
        LEGGINGS(Material.LEATHER_LEGGINGS),
        BOOTS(Material.LEATHER_BOOTS);

        @Getter
        private Material material;

        LeatherArmor(Material material){
            this.material = material;
        }
    }

}

package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.gui.object.GuiSize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilInventory {

    public static void fill(Inventory inv, ItemStack item) {
        for (int i = 0; i <= 8; i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                inv.setItem(i, item);
            }
        }
    }

    public static void surroundWith(Inventory inv, ItemStack item) {
        if (inv.getSize() >= GuiSize.THREE_ROWS.getSlots()) {

            Integer[] walls = new Integer[]{9,17,18,26,27,35,36,44};
            List<Integer> slots = new ArrayList<>();
            int size = inv.getSize();

            for(int i=0;i<8;i++){
                slots.add(--size);
                slots.add(i);
            }
            slots.addAll(Arrays.asList(walls));

            for (int i : slots) {
                if (i > inv.getSize()){
                    break;
                }
                if (inv.getItem(i) == null && inv.getItem(i).getType() == Material.AIR) {
                    inv.setItem(i, item);
                }
            }
        }else{
            // Else fill it because you can't surround 1 or 2 rows.
            fill(inv, item);
        }
    }

    public static void centralise(Inventory inv, List<ItemStack> l) {
        int center = 31;
        int start = center - getSubtractAmount(l.size());
        for (int k = 0; k < l.size(); k++) {
            ItemStack item = l.get(k);
            inv.setItem(start + k, item);
        }
    }

    public static int getSubtractAmount(int i) {
        if (i % 2 != 0) {
            i--;
        }
        return i / 2;
    }

    public static int getFirstFreeSlot(Player p) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (p.getInventory().getItem(i) == null)
                return i;
        }
        return -1;
    }

    public static void addInColumn(Inventory inv, ItemStack item, int column) {
        List<Integer> slots = new ArrayList<>();
        for (int i = column - 1; i <= 53; i += 9) {
            slots.add(i);
        }
        for (Integer cur : slots) {
            if (inv.getItem(cur) == null) {
                inv.setItem(cur, item);
                return;
            }
            if (inv.getItem(cur).getType() == Material.AIR) {
                inv.setItem(cur, item);
                return;
            }
        }
    }

}

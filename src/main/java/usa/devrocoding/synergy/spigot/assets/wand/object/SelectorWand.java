package usa.devrocoding.synergy.spigot.assets.wand.object;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.wand.event.SelectorWandCompletionEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

@Getter
public abstract class SelectorWand extends ItemBuilder {

    private Location firstLocation;
    private Location secondLocation;

    @Getter
    private static List<SelectorWand> selectorWands = Lists.newArrayList();

    public SelectorWand() {
        super(Material.GOLD_AXE, 1);
        setName("§6Wand of Selections");
        setLore(
            "With this magic wand you are",
            "able to make selections based",
            "on the worldedit selection wand.",
            " ",
            "§eLEFT CLICK §7for setting the first position",
            "§eRIGHT CLICK §7for setting the second position"
        ).build();

        selectorWands.add(this);
    }

    public enum SelectorUnit{
        FIRST,
        SECOND
    }

    public void select(SynergyUser user, SelectorUnit selectorUnit, Block block){
        switch (selectorUnit){
            case FIRST:
                this.firstLocation = block.getLocation();
                break;
            case SECOND:
                this.secondLocation = block.getLocation();
                break;
        }
        if (this.firstLocation != null && this.secondLocation != null){
            user.getPlayer().getInventory().remove(this);
            Region region = new Region(this.firstLocation, this.secondLocation);
            Core.getPlugin().getServer().getPluginManager().callEvent(new SelectorWandCompletionEvent(this, user, region));
            this.onfinish(user, region);
            selectorWands.remove(this);
        }
    }

    public abstract void onfinish(SynergyUser synergyUser, Region region);

}

package usa.devrocoding.synergy.spigot.assets.wand.object;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.wand.event.SelectorWandCompletionEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class SelectorWand {

    @Getter
    private Location firstLocation;
    @Getter
    private Location secondLocation;

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
            user.getPlayer().getInventory().remove(Core.getPlugin().getWandManager().getWand());
            Region region = new Region(this.firstLocation, this.secondLocation);
            Core.getPlugin().getServer().getPluginManager().callEvent(new SelectorWandCompletionEvent(this, user, region));

            Core.getPlugin().getWandManager().getSelectorWands().remove(user);
        }
    }

    public void give(SynergyUser user){
        if (!user.getPlayer().getInventory().contains(Core.getPlugin().getWandManager().getWand())) {
            user.getPlayer().getInventory().addItem(Core.getPlugin().getWandManager().getWand());
            Core.getPlugin().getWandManager().getSelectorWands().put(user, this);
        }
    }

}

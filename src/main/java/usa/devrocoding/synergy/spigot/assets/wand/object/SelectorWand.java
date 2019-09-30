package usa.devrocoding.synergy.spigot.assets.wand.object;

import lombok.Getter;
import org.bukkit.block.Block;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.wand.event.SelectorWandCompletionEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class SelectorWand {

    @Getter
    private Block first = null, second = null;

    public enum SelectorUnit{
        FIRST,
        SECOND
    }

    public void select(SynergyUser user, SelectorUnit selectorUnit, Block block){
        switch (selectorUnit){
            case FIRST:
                this.first = block;
                break;
            case SECOND:
                this.second = block;
                break;
        }
        if (first != null && second != null){
            user.getPlayer().getInventory().remove(Core.getPlugin().getWandManager().getWand());
            Core.getPlugin().getServer().getPluginManager().callEvent(new SelectorWandCompletionEvent(this, user));

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

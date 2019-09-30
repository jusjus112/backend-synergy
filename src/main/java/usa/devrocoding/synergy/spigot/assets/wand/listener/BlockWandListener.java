package usa.devrocoding.synergy.spigot.assets.wand.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.wand.object.SelectorWand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class BlockWandListener implements Listener {

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e){
        if (e.getItem() != null && e.getItem().equals(Core.getPlugin().getWandManager().getWand())){
            e.setCancelled(true);

            SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer().getUniqueId());
            SelectorWand selectorWand = Core.getPlugin().getWandManager().getSelectorWands().get(user);
            if (e.getAction() == Action.LEFT_CLICK_BLOCK){
                user.info("§e§lFIRST §7position set....");
                selectorWand.select(user, SelectorWand.SelectorUnit.FIRST, e.getClickedBlock());
            }else if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
                user.info("§e§lSECOND §7position set....");
                selectorWand.select(user, SelectorWand.SelectorUnit.SECOND, e.getClickedBlock());
            }
        }
    }

}

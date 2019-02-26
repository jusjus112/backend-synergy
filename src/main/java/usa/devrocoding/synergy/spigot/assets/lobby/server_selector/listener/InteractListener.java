package usa.devrocoding.synergy.spigot.assets.lobby.server_selector.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getItem() == null) return;


    }

}

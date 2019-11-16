package usa.devrocoding.synergy.spigot.hologram;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;

import java.util.*;
import java.util.function.Predicate;

public class HologramManager extends Module implements Listener {

    private static final double HOLOGRAM_DISTANCE = 0.3D;
    private static final double SEND_RADIUS_SQUARED = UtilMath.square(40);

    @Getter
    private final List<Hologram> globalHolograms = new ArrayList<>();
    private final Map<UUID, List<Hologram>> holograms = new HashMap<>();

    public HologramManager(Core plugin) {
        super(plugin, "Hologram Manager", false);

        registerListener(
                this
        );

        plugin.getRunnableManager().runTaskTimerAsynchronously("Hologram Update", (echo) -> {
            Lists.newArrayList(getPlugin().getUserManager().getOnlineUsers()).forEach(synergyUser -> update(synergyUser));
        }, 0, 20 * 2);
    }

    @Override
    public void reload(String response) {

    }

    public Hologram createHologram(Location location, HologramLine hologramLine, Predicate<Player> predicate) {
        Hologram hologram = new Hologram(location, hologramLine, predicate);
        this.globalHolograms.add(hologram);
        for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
            createHologramForPlayer(synergyUser.getUuid());
        }
        return hologram;
    }

    public void createHologram(Location location, Predicate<Player> predicate, HologramLine... hologramLines) {
        int i = 0;
        for(HologramLine hologramLine : hologramLines){
            Location loc = new Location(location.getWorld(), location.getX(), location.getY() - i * HOLOGRAM_DISTANCE,location.getZ(), location.getYaw(), location.getPitch());
            Hologram hologram = new Hologram(loc, hologramLine, predicate);
            this.globalHolograms.add(hologram);
            i++;
        }
        for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
            createHologramForPlayer(synergyUser.getUuid());
        }
    }

    private void createHologramForPlayer(UUID uuid){
        List<Hologram> list = new ArrayList<>();

        Lists.newArrayList(this.globalHolograms).forEach(hologram -> {
            try{
                list.add(new Hologram(hologram.getLocation(), hologram.getHologramLine(), hologram.getShouldShow()));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if (!list.isEmpty()) this.holograms.put(uuid, list);
    }

    private void update(SynergyUser synergyUser){
        UUID uuid = synergyUser.getUuid();

        if (holograms.containsKey(uuid)){
            Lists.newArrayList(holograms.get(uuid)).forEach(hologram -> {
                hologram.send(synergyUser);
            });
        }
    }

    @EventHandler
    public void on(UserLoadEvent e) {
        // Async because of the user join server impact
        getPlugin().getRunnableManager().runTaskAsynchronously("Hologram onJoin", (echo) -> {
            createHologramForPlayer(e.getUser().getUuid());
            update(e.getUser());
        });
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        if (this.holograms.containsKey(e.getPlayer().getUniqueId())) {
            for (Hologram hologram : this.holograms.get(e.getPlayer().getUniqueId())) {
                hologram.remove(e.getPlayer());
            }
            this.holograms.remove(e.getPlayer().getUniqueId());
        }
    }

}

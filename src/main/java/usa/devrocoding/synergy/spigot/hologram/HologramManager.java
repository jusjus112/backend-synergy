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
import usa.devrocoding.synergy.spigot.hologram.object.EmptyHologramLine;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilList;
import usa.devrocoding.synergy.spigot.utilities.UtilLoc;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;

import java.util.*;
import java.util.function.Predicate;

public class HologramManager extends Module implements Listener {

    private static final double HOLOGRAM_DISTANCE = 0.3D;
    private static final double SEND_RADIUS_SQUARED = UtilMath.square(40);

    @Getter
    private final List<Hologram> globalHolograms = new ArrayList<>();
    private final List<HologramProt> globalProtHolograms = new ArrayList<>();
    private final Map<UUID, List<Hologram>> holograms = new HashMap<>();

    // Prototype Holograms
    private final Map<UUID, List<HologramProt>> protHolograms = new HashMap<>();

    public HologramManager(Core plugin) {
        super(plugin, "Hologram Manager", false);

        registerListener(
            this
        );

        plugin.getRunnableManager().runTaskTimerAsynchronously("Hologram Update", (echo) -> {
            Lists.newArrayList(getPlugin().getUserManager().getOnlineUsers()).forEach(
                this::update
            );
            Lists.newArrayList(getPlugin().getUserManager().getOnlineUsers()).forEach(
                this::updateProts
            );
        }, 0, 20);
    }

    @Override
    public void reload(String response) {

    }

    public void updateHologramLine(HologramLine hologramLine, Hologram hologram, SynergyUser synergyUser){
        List<Hologram> f = this.holograms.getOrDefault(synergyUser.getUuid(), Lists.newArrayList());

        f.stream().filter(hologram1 ->
            hologram1.getId().equalsIgnoreCase(hologram.getId())
        ).findFirst()
            .ifPresent(value -> value.setHologramLine(hologramLine));
    }

    public Hologram createHologram(Location location, HologramLine hologramLine, Predicate<Player> predicate) {
        Hologram hologram = new Hologram(location, hologramLine, predicate);
        this.globalHolograms.add(hologram);
        for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
            createHologramForPlayer(synergyUser.getUuid());
        }
        return hologram;
    }

    public void createHologramProt(Location location, HologramLine... hologramLines){
        int i = 0;
        for(HologramLine hologramLine : hologramLines){
            Location loc = new Location(location.getWorld(), location.getX(), location.getY() - i * HOLOGRAM_DISTANCE,location.getZ(), location.getYaw(), location.getPitch());
            HologramProt hologramProt = new HologramProt(loc);
            this.globalProtHolograms.add(hologramProt);
            i++;
        }
        for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
            createProtHologramForPlayer(synergyUser.getUuid());
        }
    }

    public List<Hologram> createHologram(Location location, Predicate<Player> predicate, HologramLine... hologramLines) {
        return createHologram(location, predicate, Arrays.asList(hologramLines));
    }

    public LinkedList<Hologram> createHologram(Location location, Predicate<Player> predicate, List<HologramLine> hologramLines) {
        int i = 0;
        LinkedList<Hologram> hologramList = Lists.newLinkedList();
//        if (hologramLines instanceof LinkedList){
//            hologramLines = UtilList.reverseLinkedList(Lists.newLinkedList(hologramLines));
//        }else {
//            Collections.reverse(hologramLines);
//        }

        for(HologramLine hologramLine : hologramLines){
//            if (!(hologramLine instanceof EmptyHologramLine)) {
                Location loc = UtilLoc.newInstance(location)
                    .add(0, hologramLines.size() * HOLOGRAM_DISTANCE, 0)
                    .subtract(0, i * HOLOGRAM_DISTANCE, 0);
                Hologram hologram = new Hologram(loc, hologramLine, predicate);
                hologramList.add(hologram);
                this.globalHolograms.add(hologram);
//            }
            i++;
        }
        for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
            createHologramForPlayer(synergyUser.getUuid());
        }
        return hologramList;
    }

    private void createProtHologramForPlayer(UUID uuid){
        List<HologramProt> list = new ArrayList<>();

        Lists.newArrayList(this.globalProtHolograms).forEach(hologram -> {
            try{
                list.add(new HologramProt(hologram.getLocation()));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if (!list.isEmpty()) this.protHolograms.put(uuid, list);
    }

    private void createHologramForPlayer(UUID uuid){
        List<Hologram> list = new ArrayList<>();

        final int[] i = {0};
        Lists.newArrayList(this.globalHolograms).forEach(hologram -> {
            try{
                list.add(new Hologram(hologram.getId(), hologram.getLocation(),
                    hologram.getHologramLine(), hologram.getShouldShow()));
                i[0]++;
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        if (!list.isEmpty()) {
            this.holograms.put(uuid, list);
        }
    }

    private void update(SynergyUser synergyUser){
        UUID uuid = synergyUser.getUuid();

        if (holograms.containsKey(uuid)){
            try{
                for (Hologram hologram : holograms.get(uuid)) {
                    hologram.send(synergyUser);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    private void updateProts(SynergyUser synergyUser){
        UUID uuid = synergyUser.getUuid();

        if (protHolograms.containsKey(uuid)){
            Lists.newArrayList(protHolograms.get(uuid)).forEach(hologram -> {
                hologram.send(synergyUser);
            });
        }
    }

    @EventHandler
    public void on(UserLoadEvent e) {
        // Async because of the user join server impact
        getPlugin().getRunnableManager().runTaskLater("Hologram onJoin", (echo) -> {
            createHologramForPlayer(e.getUser().getUuid());
//            update(e.getUser());
        }, 30);
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

package usa.devrocoding.synergy.spigot.hologram;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class HologramManager extends Module {

    private final List<Hologram> holograms = Lists.newArrayList();

    public HologramManager(Core plugin) {
        super(plugin, "Hologram Manager");

        plugin.getRunnableManager().runTaskTimer("Hologram Update", (echo) -> {
            Lists.newArrayList(holograms).forEach(hologram -> hologram.send());
        }, 0, 20 * 2);
    }

    public Hologram createHologram(Location location, Predicate<Player> predicate, String... lines) {
        return this.createHologram(location, Lists.newArrayList(lines), predicate);
    }

    public Hologram createHologram(Location location, String... lines) {
        return this.createHologram(location, Lists.newArrayList(lines), null);
    }

    public Hologram createHologram(Location location, List<String> lines) {
        return this.createHologram(location, lines, null);
    }

    public Hologram createHologram(Location location, List<String> lines, Predicate<Player> predicate) {
        Hologram hologram = new Hologram(location, lines, predicate);
        hologram.send();

        holograms.add(hologram);

        return hologram;
    }

    public void remove(Hologram hologram) {
        if (hologram != null) {
            this.holograms.remove(hologram);
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> hologram.remove(), 1);
        }
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        holograms.forEach(hologram -> hologram.send());
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        holograms.forEach(hologram -> {
            if(hologram.getViewers().containsKey(event.getPlayer().getUniqueId())) {
                hologram.getViewers().remove(event.getPlayer().getUniqueId());
            }
        });
    }

    public Hologram getHologram(String line, boolean ignorecase) {
        return holograms.stream().filter(hologram -> hologram.hasLine(line, ignorecase)).findAny().orElse(null);
    }

    public Collection<Hologram> getHolograms() {
        return holograms;
    }

}

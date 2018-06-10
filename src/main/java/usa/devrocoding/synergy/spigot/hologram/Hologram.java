package usa.devrocoding.synergy.spigot.hologram;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;
import usa.devrocoding.synergy.spigot.utilities.UtilPlayer;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class Hologram {

    private static final double HOLOGRAM_DISTANCE = 0.3D;
    private static final double SEND_RADIUS_SQUARED = UtilMath.square(40);

    private List<String> lines = Lists.newArrayList();

    private final Map<UUID, List<Integer>> viewers = Maps.newHashMap();
    private final List<EntityArmorStand> armorStands = Lists.newArrayList();

    private Location location;
    private Predicate<Player> shouldShow;

    public Hologram(Location location, List<String> lines, Predicate<Player> shouldShow) {
        this.location = location;
        this.shouldShow = shouldShow;

        lines.forEach(s -> this.lines.add(ChatColor.translateAlternateColorCodes('&', s)));
    }

    public boolean shouldShow(Player player) {
        return this.shouldShow != null ? this.shouldShow.test(player) : true;
    }

    public void send() {
        send(false);
    }

    public void send(boolean override) {
        for (int i = 0; i < this.lines.size(); i++) {
            EntityArmorStand entityArmorStand = null;

            if (i > this.armorStands.size() - 1) {
                entityArmorStand = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle());
                entityArmorStand.setLocation(this.location.getX(), this.location.getY() - i * HOLOGRAM_DISTANCE, this.location.getZ(), this.location.getYaw(), this.location.getPitch());
                entityArmorStand.setInvisible(true);
                entityArmorStand.setCustomNameVisible(true);
                entityArmorStand.setCustomName(this.lines.get(i));
                entityArmorStand.setNoGravity(true);
                entityArmorStand.setSilent(true);
                entityArmorStand.setMarker(true);
                entityArmorStand.setSmall(true);
                this.armorStands.add(entityArmorStand);
            } else {
                entityArmorStand = this.armorStands.get(i);

                final EntityArmorStand finalArmorStand = entityArmorStand;

                this.location.getWorld().getPlayers().forEach(player -> {
                    if (!player.hasMetadata("NPC") && player.getLocation().distanceSquared(this.location) <= SEND_RADIUS_SQUARED) {
                        if (shouldShow(player)) {
                            if (override) {
                                UtilPlayer.sendPacket(player, new PacketPlayOutSpawnEntityLiving(finalArmorStand));

                                List<Integer> entities = viewers.getOrDefault(player.getUniqueId(), Lists.newArrayList());
                                entities.add(finalArmorStand.getId());

                                viewers.put(player.getUniqueId(), entities);
                            } else {
                                if (!viewers.containsKey(player.getUniqueId()) || !viewers.get(player.getUniqueId()).contains(finalArmorStand.getId())) {
                                    UtilPlayer.sendPacket(player, new PacketPlayOutSpawnEntityLiving(finalArmorStand));

                                    List<Integer> entities = viewers.getOrDefault(player.getUniqueId(), Lists.newArrayList());
                                    entities.add(finalArmorStand.getId());

                                    viewers.put(player.getUniqueId(), entities);
                                }
                            }
                        }
                    } else {
                        if (viewers.containsKey(player.getUniqueId()) && viewers.get(player.getUniqueId()).contains(finalArmorStand.getId())) {
                            UtilPlayer.sendPacket(player, new PacketPlayOutEntityDestroy(finalArmorStand.getId()));
                            viewers.remove(player.getUniqueId());
                        }
                    }
                });
            }
        }
    }

    public void addLine(String line) {
        EntityArmorStand armorStand = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle());
        armorStand.setLocation( this.location.getX(), this.location.getY() - this.lines.size() * HOLOGRAM_DISTANCE, this.location.getZ(), this.location.getYaw(), this.location.getPitch());
        armorStand.setInvisible(true);
        armorStand.setCustomName(line);
        armorStand.setCustomNameVisible(true);
        armorStand.setNoGravity(true);
        armorStand.setSilent(true);
        armorStand.setMarker(true);
        armorStand.setSmall(true);
        this.armorStands.add(armorStand);
        this.lines.add(line);
    }

    public void setLine(int index, String line) {
        if(index >= armorStands.size()) {
            EntityArmorStand armorStand = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle());
            armorStand.setLocation( this.location.getX(), this.location.getY() - this.lines.size() * HOLOGRAM_DISTANCE, this.location.getZ(), this.location.getYaw(), this.location.getPitch());
            armorStand.setInvisible(true);
            armorStand.setCustomName(line);
            armorStand.setCustomNameVisible(true);
            armorStand.setNoGravity(true);
            armorStand.setSilent(true);
            armorStand.setMarker(true);
            armorStand.setSmall(true);
            this.armorStands.add(armorStand);
            this.lines.add(line);
        }

        armorStands.get(index).setCustomName(line);
    }


    public void setLines(List<String> lines) {
        this.lines = lines;

        remove();
        send();
    }

    public boolean hasLine(String line, Boolean ignoreCase) {
        for (String s : lines) {
            if (ignoreCase) {
                if (ChatColor.stripColor(s).toLowerCase().equals(line.toLowerCase())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void remove() {
        this.location.getWorld().getPlayers().forEach(this::remove);
    }

    public Map<UUID, List<Integer>> getViewers() {
        return viewers;
    }

    public void remove(Player player) {
        Lists.newArrayList(armorStands).forEach(armorStand -> {
            UtilPlayer.sendPacket(player, new PacketPlayOutEntityDestroy(armorStand.getId()));
        });

        armorStands.clear();
    }


    public Location getLocation() {
        return location;
    }

}

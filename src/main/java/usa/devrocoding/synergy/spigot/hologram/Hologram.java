package usa.devrocoding.synergy.spigot.hologram;

import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilPlayer;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class Hologram {

    @Getter @Setter
    private HologramLine hologramLine;
    @Getter
    private final Location location;
    private EntityArmorStand entityArmorStand;
    @Getter
    private final Predicate<Player> shouldShow;
    private String lineCache = "";
    private boolean removed = false;
    private boolean rangeUpdate = true;
    @Getter
    private final String id;

    public Hologram(Location location, HologramLine hologramLine, Predicate<Player> shouldShow) {
        this(
            String.format("%05d", ThreadLocalRandom.current().nextInt(10000)),
            location, hologramLine, shouldShow
        );
    }

    public Hologram(String id, Location location, HologramLine hologramLine, Predicate<Player> shouldShow) {
        this.location = location;
        this.hologramLine = hologramLine;
        this.shouldShow = shouldShow;
        this.id = id;
    }

    public boolean shouldShow(Player player) {
        return this.shouldShow == null || this.shouldShow.test(player);
    }

    private boolean needsUpdate(SynergyUser synergyUser){
        return !lineCache.equalsIgnoreCase(hologramLine.getMessage(synergyUser));
    }

    private boolean isEmpty(SynergyUser synergyUser){
        return this.hologramLine.getMessage(synergyUser) == null || this.hologramLine.getMessage(synergyUser).equals("");
    }

    public void send(SynergyUser synergyUser) {
        // (!player.hasMetadata("NPC") && player.getLocation().distanceSquared(this.location) <= SEND_RADIUS_SQUARED)
        if (this.removed){
            remove(synergyUser.getPlayer());
            return;
        }
        if (!isEmpty(synergyUser) && shouldShow(synergyUser.getPlayer())) {
            if (!isValid()) {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        create(synergyUser);
                    }
                }.runTaskLater(Core.getPlugin(), 2L);
            } else {
                if (isInRange(synergyUser)){
                    if (rangeUpdate && !needsUpdate(synergyUser)){
                        // Will not update again after a range update
                        return;
                    }
                    if (needsUpdate(synergyUser)){
                        String line = hologramLine.getMessage(synergyUser);
                        this.lineCache = line;
                        this.entityArmorStand.setCustomNameVisible(true);
                        this.entityArmorStand.setCustomName(line);
                    }
                    send(entityArmorStand, synergyUser.getPlayer());
                    this.rangeUpdate = true;
                }else{
                    this.rangeUpdate = false;
                }
            }
        }else{
            remove(synergyUser.getPlayer());
        }
    }

    private boolean isInRange(SynergyUser synergyUser){
        // Distance of default despawning entities is 75 blocks according to google?
        return synergyUser.getPlayer().getLocation().distance(this.location) < 75;
    }

    private void create(SynergyUser synergyUser){
        String line = hologramLine.getMessage(synergyUser);

        try{
            EntityArmorStand entityArmorStand = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle());
            this.entityArmorStand = entityArmorStand;
            if (!this.location.getChunk().isLoaded()){
                this.location.getChunk().load();
            }
            entityArmorStand.setLocation(this.location.getX(), this.location.getY(), this.location.getZ(), this.location.getYaw(), this.location.getPitch());
            entityArmorStand.setInvisible(true);
            entityArmorStand.setCustomNameVisible(true);
            entityArmorStand.setCustomName(line);
            entityArmorStand.setNoGravity(true);
            ((CraftLivingEntity) entityArmorStand.getBukkitEntity()).setRemoveWhenFarAway(false);
            entityArmorStand.setSilent(true);
            entityArmorStand.setMarker(true);
            entityArmorStand.setSmall(true);
            this.entityArmorStand = entityArmorStand;
            this.lineCache = line;
            send(entityArmorStand, synergyUser.getPlayer());
        }catch (Exception ignored){}

        // Not available in 1.12.. Still need ti find the right field
//        Field disabledSlots;
//        try {
//            disabledSlots = entityArmorStand.getClass().getDeclaredField("bE");
//            disabledSlots.setAccessible(true);
//            disabledSlots.set(entityArmorStand, 2039583);
//        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
    }

    private boolean isValid(){
        return entityArmorStand != null;
    }

    private void send(EntityArmorStand armorStand, Player player){
        UtilPlayer.sendPacket(player, new PacketPlayOutEntityDestroy(armorStand.getId()));
        Core.getPlugin().getRunnableManager().runTaskLater("as", core -> UtilPlayer.sendPacket(player, new PacketPlayOutSpawnEntityLiving(armorStand)), 2L);
//        PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), false);
//        UtilPlayer.sendPacket(player, packetPlayOutEntityMetadata);
    }

    public void remove(){
        this.removed = true;
    }

    public void remove(Player player) {
        if (isValid()){
            UtilPlayer.sendPacket(player, new PacketPlayOutEntityDestroy(this.entityArmorStand.getId()));
            this.entityArmorStand = null;
        }
    }

}

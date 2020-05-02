package usa.devrocoding.synergy.spigot.hologram;

import lombok.Getter;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilPlayer;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class Hologram {

    @Getter
    private HologramLine hologramLine;
    @Getter
    private Location location;
    private EntityArmorStand entityArmorStand;
    @Getter
    private Predicate<Player> shouldShow;
    private String lineCache = "";

    public Hologram(Location location, HologramLine hologramLine, Predicate<Player> shouldShow) {
        this.location = location;
        this.hologramLine = hologramLine;
        this.shouldShow = shouldShow;
    }

    public boolean shouldShow(Player player) {
        return this.shouldShow == null || this.shouldShow.test(player);
    }

    private boolean needsUpdate(SynergyUser synergyUser){
        return !lineCache.equalsIgnoreCase(hologramLine.getMessage(synergyUser));
    }

    public void send(SynergyUser synergyUser) {
        // (!player.hasMetadata("NPC") && player.getLocation().distanceSquared(this.location) <= SEND_RADIUS_SQUARED)
        if (shouldShow(synergyUser.getPlayer())) {
            if (!isValid()) {
                create(synergyUser);
            } else {
                if (needsUpdate(synergyUser)){
                    this.entityArmorStand.setCustomNameVisible(true);
                    this.entityArmorStand.setCustomName(hologramLine.getMessage(synergyUser));
                    send(entityArmorStand, synergyUser.getPlayer());
                }
            }
        }else{
            remove(synergyUser.getPlayer());
        }
    }

    private void create(SynergyUser synergyUser){
        String line = hologramLine.getMessage(synergyUser);

        EntityArmorStand entityArmorStand = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle());
        entityArmorStand.setLocation(this.location.getX(), this.location.getY(), this.location.getZ(), this.location.getYaw(), this.location.getPitch());
        entityArmorStand.setInvisible(true);
        entityArmorStand.setCustomNameVisible(true);
        entityArmorStand.setCustomName(line);
        entityArmorStand.setNoGravity(true);
        entityArmorStand.setSilent(true);
        entityArmorStand.setMarker(true);
        entityArmorStand.setSmall(true);

        // Not available in 1.12.. Still need ti find the right field
//        Field disabledSlots;
//        try {
//            disabledSlots = entityArmorStand.getClass().getDeclaredField("bE");
//            disabledSlots.setAccessible(true);
//            disabledSlots.set(entityArmorStand, 2039583);
//        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

        this.entityArmorStand = entityArmorStand;
        this.lineCache = line;
        send(entityArmorStand, synergyUser.getPlayer());
    }

    private boolean isValid(){
        return entityArmorStand != null;
    }

    private void send(EntityArmorStand armorStand, Player player){
        PacketPlayOutSpawnEntityLiving packetPlayOutSpawnEntity = new PacketPlayOutSpawnEntityLiving(armorStand);
        UtilPlayer.sendPacket(player, packetPlayOutSpawnEntity);
//        PacketPlayOutEntityMetadata packetPlayOutEntityMetadata = new PacketPlayOutEntityMetadata(armorStand.getId(), armorStand.getDataWatcher(), false);
//        UtilPlayer.sendPacket(player, packetPlayOutEntityMetadata);
    }

    public void remove(Player player) {
        if (isValid()){
            UtilPlayer.sendPacket(player, new PacketPlayOutEntityDestroy(this.entityArmorStand.getId()));
            this.entityArmorStand = null;
        }
    }

}

package usa.devrocoding.synergy.spigot.hologram;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.Getter;
import org.bukkit.Location;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.hologram.object.HologramLine;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.UUID;

public class HologramProt {

    @Getter
    private HologramLine hologramLine;
    @Getter
    private Location location;

    public HologramProt(Location location){
        this.location = location;
    }

    public void send(SynergyUser synergyUser){
        Synergy.debug("1 - WRAPPER");
        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();
        packet.setUniqueId(UUID.randomUUID());
        // Generate random integer for the entity's ID, it does not have to be done this way, you can do it in any way you want. You cannot have 2 identical IDs.
        int entityID = Integer.MAX_VALUE;
        packet.setEntityID(entityID);

        // Entity Type ID of the entity, which in this case is an armor stand, so 78.
        Synergy.debug("2 - WRAPPER");
        // Then you set the position with the X, Y and Z values seperately.

        Location location = this.location;

        packet.setX(this.location.getX());
        packet.setY(this.location.getY());
        packet.setZ(this.location.getZ());
        packet.setType(WrapperPlayServerSpawnEntity.ObjectTypes.ARMORSTAND);
        Synergy.debug("3 - WRAPPER");

        WrapperPlayServerEntityMetadata packet2 = new WrapperPlayServerEntityMetadata();

        // Set the entity to associate the packet with, which in this case is the client-side entity we created before.
        packet2.setEntityID(entityID);

        // Create a ProtocolLib WrappedDataWatcher from the entity's current metadata.
        WrappedDataWatcher dataWatcher = new WrappedDataWatcher(packet2.getMetadata());

        WrappedDataWatcher.WrappedDataWatcherObject isInvisibleIndex = new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class));

        dataWatcher.setObject(isInvisibleIndex, (byte) 0x20);

        WrappedDataWatcher.WrappedDataWatcherObject nameValue = new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.get(String.class));
        WrappedDataWatcher.WrappedDataWatcherObject nameVisible = new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class));

        dataWatcher.setObject(nameValue, this.getHologramLine().getMessage(synergyUser));
        dataWatcher.setObject(nameVisible, true);

        packet2.setMetadata(dataWatcher.getWatchableObjects());

        packet.sendPacket(synergyUser.getPlayer());
        packet2.sendPacket(synergyUser.getPlayer());
    }

}

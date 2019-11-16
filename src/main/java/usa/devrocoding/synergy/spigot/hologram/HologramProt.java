package usa.devrocoding.synergy.spigot.hologram;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.minecraft.server.v1_14_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;

import java.util.UUID;

public class HologramProt {

    public HologramProt(Player player){
        Synergy.debug("1 - WRAPPER");
        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();
        packet.setUniqueId(UUID.randomUUID());
        // Generate random integer for the entity's ID, it does not have to be done this way, you can do it in any way you want. You cannot have 2 identical IDs.
        Integer entityID = Integer.MAX_VALUE;
        packet.setEntityID(entityID);

        // Entity Type ID of the entity, which in this case is an armor stand, so 78.
        Synergy.debug("2 - WRAPPER");
        // Then you set the position with the X, Y and Z values seperately.
        packet.setX(player.getLocation().getX());
        packet.setY(player.getLocation().getY());
        packet.setZ(player.getLocation().getZ());
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

        dataWatcher.setObject(nameValue, "§aHello, " + player.getName() + "!");
        dataWatcher.setObject(nameVisible, true);

        packet2.setMetadata(dataWatcher.getWatchableObjects());

        packet.sendPacket(player);
        System.out.println(packet);
        System.out.println("UUID = "+packet.getUniqueId());
        System.out.println("X="+packet.getX());
        System.out.println("Y="+packet.getY());
        System.out.println("Z="+packet.getZ());
        System.out.println("TYPE="+packet.getType());
        packet2.sendPacket(player);
    }

    public HologramProt(Player player, Location location){
        WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving();

//        player.set

        packet.sendPacket(player);
    }

}

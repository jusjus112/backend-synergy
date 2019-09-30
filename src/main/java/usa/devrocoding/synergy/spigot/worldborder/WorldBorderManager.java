package usa.devrocoding.synergy.spigot.worldborder;

import net.minecraft.server.v1_14_R1.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_14_R1.WorldBorder;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilLoc;

import java.lang.reflect.Method;

public class WorldBorderManager extends Module {

    public WorldBorderManager(Core plugin){
        super(plugin, "WorldBorder Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public void send(SynergyUser user, Location l1, Location l2){
        WorldBorder wb = new WorldBorder();
        Location middle = UtilLoc.getMidpoint(l1, l2);
        wb.setCenter(middle.getX(), middle.getZ());
        wb.setSize(10);
        PacketPlayOutWorldBorder packet = new PacketPlayOutWorldBorder(
                wb,
                PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        sendPacket(user.getPlayer(),
                packet);
//        C.debugSystem("Worldborder Sended with distance - "+(middle.distance(area.getMiddle_p1())/2));
    }

    public static void sendPacket(Player player, Object packet){
        try {
            Object playerHandle = player.getClass().getMethod("getHandle").invoke(player);
            Object connection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
            Method sendPacket = connection.getClass().getMethod("sendPacket", Core.getPlugin().getVersionManager().getServerClass("packet"));
            sendPacket.invoke(connection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

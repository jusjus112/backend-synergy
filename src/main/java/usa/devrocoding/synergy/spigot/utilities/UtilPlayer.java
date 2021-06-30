package usa.devrocoding.synergy.spigot.utilities;

import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class UtilPlayer {

    public static void sendPacket(Player player, Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public BlockFace getDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 22.5) {
            return BlockFace.WEST;
        } else if (22.5 <= rotation && rotation < 67.5) {
            return BlockFace.NORTH_WEST;
        } else if (67.5 <= rotation && rotation < 112.5) {
            return BlockFace.NORTH;
        } else if (112.5 <= rotation && rotation < 157.5) {
            return BlockFace.NORTH_EAST;
        } else if (157.5 <= rotation && rotation < 202.5) {
            return BlockFace.EAST;
        } else if (202.5 <= rotation && rotation < 247.5) {
            return BlockFace.SOUTH_EAST;
        } else if (247.5 <= rotation && rotation < 292.5) {
            return BlockFace.SOUTH;
        } else if (292.5 <= rotation && rotation < 337.5) {
            return BlockFace.SOUTH_WEST;
        } else if (337.5 <= rotation && rotation < 360.0) {
            return BlockFace.WEST;
        } else {
            return BlockFace.UP;
        }
    }

//    public static Location getTargetBlock(Player p, boolean face) {
//        Location targetBlock = null;
//        List<Block> LOS = new ArrayList<>();
//        for (Block b : UtilBlock.getLineOfSight(p, 30)) {
//            LOS.add(b);
//        }
//        for (int i = 0; i < LOS.size(); i++) {
//            if (i == 0)
//                continue;
//            if (LOS.get(i).getType() != Material.AIR) {
//                targetBlock = LOS.get(i - (face ? 1 : 0)).getLocation();
//                break;
//            }
//        }
//        if (targetBlock == null)
//            return LOS.get(LOS.size() - 1).getLocation();
//        return targetBlock;
//    }
}

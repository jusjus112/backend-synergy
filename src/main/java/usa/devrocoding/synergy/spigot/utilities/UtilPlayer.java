package usa.devrocoding.synergy.spigot.utilities;

import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UtilPlayer {

    public static void sendPacket(Player player, Packet<?>... packets) {
        for (Packet<?> packet : packets) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static Location getTargetBlock(Player p, boolean face) {
        Location targetBlock = null;
        List<Block> LOS = new ArrayList<>();
        for (Block b : UtilBlock.getLineOfSight(p, 30)) {
            LOS.add(b);
        }
        for (int i = 0; i < LOS.size(); i++) {
            if (i == 0)
                continue;
            if (LOS.get(i).getType() != Material.AIR) {
                targetBlock = LOS.get(i - (face ? 1 : 0)).getLocation();
                break;
            }
        }
        if (targetBlock == null)
            return LOS.get(LOS.size() - 1).getLocation();
        return targetBlock;
    }
}

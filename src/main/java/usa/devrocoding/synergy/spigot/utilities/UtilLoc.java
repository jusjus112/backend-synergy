package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import usa.devrocoding.synergy.spigot.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UtilLoc {

	public static void setYawAndPitch(final Player p, float yaw, float pitch) {
		Location loc = p.getLocation();
		loc.setYaw(yaw);
		loc.setPitch(pitch);
		final Vector velocity = p.getVelocity();
		new BukkitRunnable() {
			public void run() {
				p.setVelocity(velocity);
			}
		}.runTaskLater(Core.getPlugin(), 1);
	}


	public static Location getHighestBlockLocation(Location location){
		Block b = location.getWorld().getHighestBlockAt(location.getBlockX(), location.getBlockZ());
		return new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY() + 1, b.getLocation().getBlockZ());
	}

	public static Location add(Location l, double x, double y, double z) {
		return new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
	}

	public static Location set(Location l, double x, double y, double z) {
		return new Location(l.getWorld(), x, y, z);
	}
	
	public static boolean isOutsideOfBorder(Entity e) {
		return isOutsideOfBorder(e.getLocation());
	}
	
	public static boolean isOutsideOfBorder(Location loc) {
		WorldBorder border = loc.getWorld().getWorldBorder();
		double x = loc.getX();
		double z = loc.getZ();
		double size = border.getSize();
		return ((x > size || (-x) > size) || (z > size || (-z) > size));
	}

	public static boolean isNearLinearSquare(Location l1, Location l2, double radius) {
		if (l1 == null || l2 == null)
			return false;
		if (l1.getWorld() == null || l2.getWorld() == null)
			return false;
		if (l1.getWorld() != l2.getWorld())
			return false;
		if (Math.abs(l1.getX() - l2.getX()) > radius)
			return false;
		if (Math.abs(l1.getY() - l2.getY()) > radius)
			return false;
		if (Math.abs(l1.getZ() - l2.getZ()) > radius)
			return false;
		return true;
	}

	public static double getDistance(Location l1, Location l2) {
		if (l1 == null || l2 == null)
			return 0;
		if (l1.getWorld() == null || l2.getWorld() == null)
			return 0;
		if (l1.getWorld() != l2.getWorld())
			return 0;
		double x1 = l1.getX();
		double y1 = l1.getY();
		double z1 = l1.getZ();
		double x2 = l2.getX();
		double y2 = l2.getY();
		double z2 = l2.getZ();
		double x = Math.abs(x1 - x2);
		double y = Math.abs(y1 - y2);
		double z = Math.abs(z1 - z2);
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double getDistance2D(Location l1, Location l2) {
		if (l1 == null || l2 == null)
			return 0;
		if (l1.getWorld() == null || l2.getWorld() == null)
			return 0;
		if (l1.getWorld() != l2.getWorld())
			return 0;
		double x1 = l1.getX();
		double z1 = l1.getZ();
		double x2 = l2.getX();
		double z2 = l2.getZ();
		double x = Math.abs(x1 - x2);
		double z = Math.abs(z1 - z2);
		return Math.sqrt(x * x + z * z);
	}

	public static float getPitch(Location l1, Location l2) {
		double deltaY = l1.getY() - l2.getY();
		double distance = l1.distance(l2);
		double pitch = Math.toDegrees(Math.asin(deltaY / distance));
		return (float) pitch;
	}

	public static Location getMidpoint(Location loc1, Location loc2) {
		return new Location(loc1.getWorld(), (loc1.getX() + loc2.getX()) / 2, (loc1.getY() + loc2.getY()) / 2,
				(loc1.getZ() + loc2.getZ()) / 2);
	}

	public static float getYaw(Location l1, Location l2) {
		double dx = l2.getX() - l1.getX();
		double dz = l2.getZ() - l1.getZ();
		double yaw = 0;
		if (dx != 0) {
			if (dx < 0) {
				yaw = 1.5 * Math.PI;
			} else {
				yaw = 0.5 * Math.PI;
			}
			yaw -= Math.atan(dz / dx);
		} else if (dz < 0) {
			yaw = Math.PI;
		}
		return (float) (-yaw * 180 / Math.PI - 90) + 90;
	}

	public static Entity[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Entity> radiusEntities = new HashSet<Entity>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk()
						.getEntities()) {
					if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
						radiusEntities.add(e);
				}
			}
		}
		return radiusEntities.toArray(new Entity[radiusEntities.size()]);
	}

	public static void teleportDontResetPitchAndYaw(Player p, Location loc) {
		p.teleport(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), p.getLocation().getYaw(),
				p.getLocation().getPitch()));
	}

	public static List<Location> getCircle(Location center, double radius, int amount) {
		World world = center.getWorld();
		List<Location> l = new ArrayList<>();
		double incrementation = 2 * Math.PI / amount;
		for (int i = 0; i < amount; i += incrementation) {
			double x = center.getX() + radius * Math.cos(i);
			double z = center.getZ() + radius * Math.sin(i);
			l.add(new Location(world, x, center.getY(), z));
		}
		return l;
	}

	public static float fixYaw(float yaw) {
		if (yaw > 360) {
			yaw %= 360;
		} else if (yaw < 0) {
			yaw = 360 - (yaw * -1);
		}
		return yaw;
	}

	public static Location newInstance(Location loc) {
		return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	}

}
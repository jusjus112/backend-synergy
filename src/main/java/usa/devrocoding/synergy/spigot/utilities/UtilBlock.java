package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class UtilBlock {

	public static Map<Location, String> blocksToRestore = Maps.newHashMap();
	public static Set<Byte> blockPassSet = Sets.newHashSet();
	

	public static boolean blockListContainsBlock(List<Block> blocks, Block b) {
		for (Block block : blocks) {
			if (blocksEqual(block, b))
				return true;
		}
		return false;
	}
	
	public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

	public static boolean isSolid(int block) {
		return isSolid((byte) block);
	}

	public static boolean isSolid(byte block) {
		if (blockPassSet.isEmpty()) {
			blockPassSet.add(Byte.valueOf((byte) 0));
			blockPassSet.add(Byte.valueOf((byte) 6));
			blockPassSet.add(Byte.valueOf((byte) 8));
			blockPassSet.add(Byte.valueOf((byte) 9));
			blockPassSet.add(Byte.valueOf((byte) 10));
			blockPassSet.add(Byte.valueOf((byte) 11));
			blockPassSet.add(Byte.valueOf((byte) 27));
			blockPassSet.add(Byte.valueOf((byte) 28));
			blockPassSet.add(Byte.valueOf((byte) 30));
			blockPassSet.add(Byte.valueOf((byte) 31));
			blockPassSet.add(Byte.valueOf((byte) 32));
			blockPassSet.add(Byte.valueOf((byte) 37));
			blockPassSet.add(Byte.valueOf((byte) 38));
			blockPassSet.add(Byte.valueOf((byte) 39));
			blockPassSet.add(Byte.valueOf((byte) 40));
			blockPassSet.add(Byte.valueOf((byte) 50));
			blockPassSet.add(Byte.valueOf((byte) 51));
			blockPassSet.add(Byte.valueOf((byte) 55));
			blockPassSet.add(Byte.valueOf((byte) 59));
			blockPassSet.add(Byte.valueOf((byte) 63));
			blockPassSet.add(Byte.valueOf((byte) 66));
			blockPassSet.add(Byte.valueOf((byte) 68));
			blockPassSet.add(Byte.valueOf((byte) 69));
			blockPassSet.add(Byte.valueOf((byte) 70));
			blockPassSet.add(Byte.valueOf((byte) 72));
			blockPassSet.add(Byte.valueOf((byte) 75));
			blockPassSet.add(Byte.valueOf((byte) 76));
			blockPassSet.add(Byte.valueOf((byte) 77));
			blockPassSet.add(Byte.valueOf((byte) 78));
			blockPassSet.add(Byte.valueOf((byte) 83));
			blockPassSet.add(Byte.valueOf((byte) 90));
			blockPassSet.add(Byte.valueOf((byte) 104));
			blockPassSet.add(Byte.valueOf((byte) 105));
			blockPassSet.add(Byte.valueOf((byte) 115));
			blockPassSet.add(Byte.valueOf((byte) 119));
			blockPassSet.add(Byte.valueOf((byte) 132));
			blockPassSet.add(Byte.valueOf((byte) 143));
			blockPassSet.add(Byte.valueOf((byte) 175));
			blockPassSet.add(Byte.valueOf((byte) -85));
		}
		return !blockPassSet.contains(Byte.valueOf(block));
	}

	public static boolean isSolid(Block block) {
		if (block == null)
			return false;
		return isSolid(block.getTypeId());
	}

	public static void forceRestore() {
		for (Location loc : blocksToRestore.keySet()) {
			try {
				Block b = loc.getBlock();
				String s = blocksToRestore.get(loc);
				Material m = Material.valueOf(s.split(",")[0]);
				byte d = Byte.valueOf(s.split(",")[1]);
				b.setType(m);
				b.setData(d);
			} catch (Exception ignored) {
			}
		}
	}

	/**
	 * Restores the block at the location "loc".
	 *
	 * @param location The location of the block to restore.
	 */
	public static void restoreBlockAt(final Location location) {
		if (!blocksToRestore.containsKey(location)) return;
		Block b = location.getBlock();
		String s = blocksToRestore.get(location);
		Material m = Material.valueOf(s.split(",")[0]);
		byte d = Byte.valueOf(s.split(",")[1]);
		b.getLocation().getWorld().getPlayers().forEach(player -> player.sendBlockChange(location, m, d));
		blocksToRestore.remove(location);
	}


	/**
	 * Replaces a block with a new material and data, and after delay, restore it.
	 *
	 * @param block     The block.
	 * @param newType   The new material.
	 * @param newData   The new data.
	 * @param tickDelay The delay after which the block is restored.
	 */
	public static void setToRestore(final Block block, final Material newType, final byte newData, final int tickDelay) {
		Bukkit.getScheduler().runTaskAsynchronously(Core.getPlugin(), () -> {
			if (blocksToRestore.containsKey(block.getLocation())) return;
			Block bUp = block.getRelative(BlockFace.UP);
			if (!(block.getType() != Material.AIR
					&& block.getType() != Material.SIGN_POST
					&& block.getType() != Material.CHEST
					&& block.getType() != Material.STONE_PLATE
					&& block.getType() != Material.WOOD_PLATE
					&& block.getType() != Material.WALL_SIGN
					&& block.getType() != Material.WALL_BANNER
					&& block.getType() != Material.STANDING_BANNER
					&& block.getType() != Material.CROPS
					&& block.getType() != Material.LONG_GRASS
					&& block.getType() != Material.SAPLING
					&& block.getType() != Material.DEAD_BUSH
					&& block.getType() != Material.RED_ROSE
					&& block.getType() != Material.RED_MUSHROOM
					&& block.getType() != Material.BROWN_MUSHROOM
					&& block.getType() != Material.TORCH
					&& block.getType() != Material.LADDER
					&& block.getType() != Material.VINE
					&& block.getType() != Material.DOUBLE_PLANT
					&& block.getType() != Material.PORTAL
					&& block.getType() != Material.CACTUS
					&& block.getType() != Material.WATER
					&& block.getType() != Material.STATIONARY_WATER
					&& block.getType() != Material.LAVA
					&& block.getType() != Material.STATIONARY_LAVA
					&& block.getType() != Material.PORTAL
					&& block.getType() != Material.ENDER_PORTAL
					&& block.getType() != Material.SOIL
					&& block.getType() != Material.BARRIER
					&& block.getType() != Material.COMMAND
					&& block.getType() != Material.DROPPER
					&& block.getType() != Material.DISPENSER
					&& !block.getType().toString().toLowerCase().contains("door")
					&& block.getType() != Material.BED
					&& block.getType() != Material.BED_BLOCK
					&& !blocksToRestore.containsKey(block.getLocation())
					&& block.getType().isSolid()
					&& block.getType().getId() != 43
					&& block.getType().getId() != 44)) return;

			blocksToRestore.put(block.getLocation(), block.getType().toString() + "," + block.getData());
			for (Player player : block.getLocation().getWorld().getPlayers())
				player.sendBlockChange(block.getLocation(), newType, newData);
			Bukkit.getScheduler().runTaskLater(Core.getPlugin(), () -> {
				restoreBlockAt(block.getLocation());
			}, tickDelay);
		});
	}

	public static Location normalise(Location l) {
		if (l == null)
			return null;
		if (l.getY() <= 1) {
			return new Location(l.getWorld(), l.getX(), 1, l.getZ());
		}
		if (l.getBlock() == null)
			return null;
		return l.getBlock().getLocation().add(0.5, 0.5, 0.5);
	}

	@SuppressWarnings("deprecation")
	public static void playEffect(Block b) {
		playEffect(b, b.getType(), b.getData());
	}

	@SuppressWarnings("deprecation")
	public static void removeBlock(Block b) {
		b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());
		b.setType(Material.AIR);
	}

	public static HashSet<Material> getAllMaterials() {
		HashSet<Material> set = new HashSet<>();
		for (Material material : Material.values()) {
			set.add(material);
		}
		return set;
	}

	public List<Block> fill(Location loc) {
		List<Block> blocks = new ArrayList<>();
		blocks.add(loc.getBlock());
		for (int i = 0; i < 20; i++) {
			for (Block block : UtilBlock.generateCircle(loc, i, false)) {
				if (block.getType() != Material.AIR)
					continue;
				boolean isNear = false;
				for (Block cur : blocks) {
					for (Block dur : UtilBlock.getAdjacentBlocks(cur)) {
						if (UtilBlock.compareBlocks(dur, block)) {
							isNear = true;
						}
					}
				}
				if (isNear) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}

	public static boolean isFoliage(Material material) {
		Material[] array = new Material[]{Material.LEAVES, Material.LEAVES_2, Material.LONG_GRASS,
				Material.DOUBLE_PLANT, Material.YELLOW_FLOWER, Material.RED_ROSE, Material.FENCE, Material.FENCE_GATE,
				Material.TORCH, Material.LADDER, Material.SPRUCE_FENCE, Material.BIRCH_FENCE, Material.JUNGLE_FENCE,
				Material.DARK_OAK_FENCE, Material.ACACIA_FENCE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE,
				Material.JUNGLE_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.ACACIA_FENCE_GATE};
		for (Material cur : array) {
			if (material == cur)
				return true;
		}
		return false;
	}

	public static List<Block> getLineOfSight(Player p, int length) {
		return p.getLineOfSight(getAllMaterials(), length);
	}

	public static Block getTargetBlock(Player p, int length) {
		return UtilPlayer.getTargetBlock(p, true).getBlock();
	}

	@SuppressWarnings("deprecation")
	public static void playEffect(Block b, Material m, byte d) {
		if (b.getType() == Material.AIR)
			return;
		if (UtilBlock.isBlacklisted(b.getType()))
			return;
		b.setType(m);
		b.setData(d);
		b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType().getId());
	}

	public static byte getPrimaryColourData() {
		byte[] colours = new byte[]{11, 14, 4, 5, 1, 10};
		return colours[ThreadLocalRandom.current().nextInt(colours.length)];
	}

	public static void playEffect(Block b, Material m) {
		playEffect(b, m, (byte) 0);
	}

	public static List<Block> generateCircle(Location c, double r) {
		return generateCircle(c, r, false);
	}

	public static List<Block> generateCircle(Location c, double r, boolean hollow) {
		c = UtilBlock.normalise(c);
		List<Block> blocks = new ArrayList<>();
		for (Block b : UtilBlock.getBlocks(UtilLoc.add(c, r, 0, r), UtilLoc.add(c, -r, 0, -r))) {
			double distance = b.getLocation().distance(c);
			if (distance <= r + 0.5) {
				if (!hollow) {
					blocks.add(b);
				} else {
					if (distance >= r - 0.5) {
						blocks.add(b);
					}
				}
			}
		}
		return blocks;
	}

	public static List<Block> generateSphere(Location c, int r) {
		return generateSphere(c, r, false);
	}

	public static Location toLocation(Block b) {
		return new Location(b.getWorld(), b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5);
	}

	public static boolean compareBlocks(Block b1, Block b2) {
		int x1 = b1.getX();
		int y1 = b1.getY();
		int z1 = b1.getZ();
		int x2 = b2.getX();
		int y2 = b2.getY();
		int z2 = b2.getZ();
		if (x1 == x2 && y1 == y2 && z1 == z2)
			return true;
		return false;
	}

	public static List<Block> generateSphere(Location c, double r, boolean hollow) {
		c = UtilBlock.normalise(c);
		List<Block> blocks = new ArrayList<>();
		for (Block b : UtilBlock.getBlocks(UtilLoc.add(c, r, r, r), UtilLoc.add(c, -r, -r, -r))) {
			double distance = b.getLocation().distance(c);
			if (distance <= r + 0.5) {
				if (!hollow) {
					blocks.add(b);
				} else {
					if (distance >= r - 0.25) {
						blocks.add(b);
					}
				}
			}
		}
		return blocks;
	}

	public static List<Block> generateSphereDontNormalise(Location c, double r, boolean hollow) {
		List<Block> blocks = new ArrayList<>();
		for (Block b : UtilBlock.getBlocks(UtilLoc.add(c, r, r, r), UtilLoc.add(c, -r, -r, -r))) {
			double distance = b.getLocation().distance(c);
			if (distance <= r + 0.5) {
				if (!hollow) {
					blocks.add(b);
				} else {
					if (distance >= r - 0.25) {
						blocks.add(b);
					}
				}
			}
		}
		return blocks;
	}

	public static List<Block> generateThirdHollowCircle(Location l, double radius) {
		l = UtilBlock.normalise(l);
		ArrayList<Block> circleBlocks = new ArrayList<>();
		Location p1 = UtilLoc.add(l, radius, 0, radius);
		Location p2 = UtilLoc.add(l, -radius, 0, -radius);
		for (Block b : UtilBlock.getBlocks(p1, p2)) {
			double d = Math.abs(l.distance(b.getLocation()));
			if ((d > radius - 0.5) && (d < radius + 0.5)) {
				if (!UtilBlock.blockListContainsBlock(circleBlocks, b)) {
					boolean shouldAdd = true;
					for (Block bl : circleBlocks) {
						if (bl.getLocation().distance(b.getLocation()) < 4) {
							shouldAdd = false;
						}
					}
					if (shouldAdd) {
						circleBlocks.add(b);
					}
				}
			}
		}
		return circleBlocks;
	}

	public static boolean blocksEqual(Block b1, Block b2) {
		if (!b1.getWorld().getName().equals(b2.getWorld().getName()))
			return false;
		int x1 = b1.getX();
		int y1 = b1.getY();
		int z1 = b1.getZ();
		int x2 = b2.getX();
		int y2 = b2.getY();
		int z2 = b2.getZ();
		if (x1 == x2 && y1 == y2 && z1 == z2) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void makeBlockFly(Block b) {
		if (b.getType() == Material.AIR)
			return;
		final Material m = b.getType();
		final byte d = b.getData();
		boolean fallingBlock = false;
		if (!UtilBlock.isBlacklisted(m)) {
			if (b.getY() > 13) {
				fallingBlock = true;
				b.setType(Material.AIR);
			}
		}
		if (ThreadLocalRandom.current().nextInt(5) == 0) {
			if (fallingBlock) {
				if (!UtilBlock.isBlacklisted(m)) {
					FallingBlock fb = b.getLocation().getWorld().spawnFallingBlock(b.getLocation(), m, d);
					fb.setDropItem(false);
					fb.setVelocity(UtilVector.getRandomVector().multiply(0.5));
				}
			}
		}
	}

	public static boolean isBlacklisted(Material m) {
		List<Material> l = new ArrayList<>();
		l.add(Material.WATER);
		l.add(Material.STATIONARY_WATER);
		l.add(Material.STATIONARY_LAVA);
		l.add(Material.LAVA);
		l.add(Material.SIGN);
		l.add(Material.WALL_SIGN);
		l.add(Material.SIGN_POST);
		l.add(Material.BARRIER);
		l.add(Material.LONG_GRASS);
		l.add(Material.CARPET);
		// l.add(Material.FENCE);
		l.add(Material.TRAP_DOOR);
		l.add(Material.IRON_TRAPDOOR);
		l.add(Material.BROWN_MUSHROOM);
		l.add(Material.RED_MUSHROOM);
		l.add(Material.BANNER);
		l.add(Material.PUMPKIN);
		l.add(Material.JACK_O_LANTERN);
		l.add(Material.BANNER);
		l.add(Material.PORTAL);
		l.add(Material.ENDER_PORTAL);
		l.add(Material.ENDER_PORTAL_FRAME);
		l.add(Material.SKULL_ITEM);
		l.add(Material.SKULL);
		l.add(Material.FLOWER_POT);
		l.add(Material.FLOWER_POT_ITEM);
		l.add(Material.LONG_GRASS);
		l.add(Material.TORCH);
		l.add(Material.CHEST);
		l.add(Material.TNT);
		l.add(Material.ENDER_CHEST);
		l.add(Material.NOTE_BLOCK);
		l.add(Material.JUKEBOX);
		l.add(Material.TRAPPED_CHEST);
		l.add(Material.WATER_LILY);
		l.add(Material.DOUBLE_PLANT);
		l.add(Material.IRON_PLATE);
		l.add(Material.GOLD_PLATE);
		for (Material material : l) {
			if (material == m)
				return true;
		}
		return false;
	}

	public static boolean canColourIn(Block b) {
		return canColourIn(b.getType());
	}

	public static boolean canColourIn(Material m) {
		List<Material> l = new ArrayList<>();
		l.add(Material.WOOL);
		l.add(Material.CARPET);
		l.add(Material.STAINED_CLAY);
		l.add(Material.STAINED_GLASS_PANE);
		l.add(Material.STAINED_GLASS);
		for (Material material : l) {
			if (material == m)
				return true;
		}
		return false;
	}

	public static boolean isLiquid(Block b) {
		return isLiquid(b.getType());
	}

	public static boolean isLiquid(Material m) {
		List<Material> l = new ArrayList<>();
		l.add(Material.WATER);
		l.add(Material.LAVA);
		l.add(Material.STATIONARY_LAVA);
		l.add(Material.STATIONARY_WATER);
		for (Material material : l) {
			if (material == m)
				return true;
		}
		return false;
	}

	public static List<Block> getNearbyBlocks(Location l, double r) {
		List<Block> list = new ArrayList<>();
		Location p1 = UtilLoc.add(l, r, r, r);
		Location p2 = UtilLoc.add(l, -r, -r, -r);
		for (Block b : getBlocks(p1, p2)) {
			if (UtilLoc.add(b.getLocation(), 0.5, 0.5, 0.5).distance(l) <= r) {
				list.add(b);
			}
		}
		return list;
	}

	public static List<Block> getAllCircleBlocks(int min, int max, Location center, double radius) {
		List<Block> blocks = new ArrayList<>();
		for (int i = min; i < max; i++) {
			for (Block b : generateCircle(new Location(center.getWorld(), center.getBlockX(), i, center.getZ()), radius,
					true)) {
				if (!blockListContainsBlock(blocks, b)) {
					if (b != null) {
						if (b.getType() != Material.AIR) {
							blocks.add(b);
						}
					}
				}
			}
		}
		return blocks;
	}

	public static List<Block> getAdjacentBlocks(Block block) {
		return getAdjacentBlocks(block, true);
	}

	public static List<Block> getAdjacentBlocks(Block block, boolean includeAboveAndBelow) {
		List<Block> blocks = new ArrayList<>();
		blocks.add(UtilLoc.add(block.getLocation(), 1, 0, 0).getBlock());
		blocks.add(UtilLoc.add(block.getLocation(), 0, 0, 1).getBlock());
		blocks.add(UtilLoc.add(block.getLocation(), -1, 0, 0).getBlock());
		blocks.add(UtilLoc.add(block.getLocation(), 0, 0, -1).getBlock());
		if (includeAboveAndBelow) {
			blocks.add(UtilLoc.add(block.getLocation(), 0, 1, 0).getBlock());
			blocks.add(UtilLoc.add(block.getLocation(), 0, -1, 0).getBlock());
		}
		return blocks;
	}

	public static Block getTopBlock(Block b) {
		for (int i = b.getWorld().getMaxHeight(); i > 0; i--) {
			if (b.getWorld().getBlockAt(b.getX(), i, b.getZ()).getType() != Material.AIR)
				return b.getWorld().getBlockAt(b.getX(), i + 1, b.getZ());
		}
		return b.getWorld().getBlockAt(b.getX(), 2, b.getY());
	}

	public static Block getTopBlock150(Block b) {
		for (int i = 150; i > 0; i--) {
			if (b.getWorld().getBlockAt(b.getX(), i, b.getZ()).getType() != Material.AIR)
				return b.getWorld().getBlockAt(b.getX(), i + 1, b.getZ());
		}
		return b.getWorld().getBlockAt(b.getX(), 100, b.getZ());
	}

	public static List<Block> getBlocks(Location loc1, Location loc2) {
		List<Block> blocks = new ArrayList<Block>();

		int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
		int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

		int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
		int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

		int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
		int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

		for (int x = bottomBlockX; x <= topBlockX; x++) {
			for (int z = bottomBlockZ; z <= topBlockZ; z++) {
				for (int y = bottomBlockY; y <= topBlockY; y++) {
					Block block = loc1.getWorld().getBlockAt(x, y, z);

					blocks.add(block);
				}
			}
		}
		return blocks;
	}
}

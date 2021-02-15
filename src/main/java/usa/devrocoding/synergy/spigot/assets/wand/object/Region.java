package usa.devrocoding.synergy.spigot.assets.wand.object;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilLoc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
public class Region {

    private final Location firstLocation;
    private final Location secondLocation;
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;

    public Region(@NonNull Location loc1, @NonNull Location loc2){
        this.firstLocation = loc1;
        this.secondLocation = loc2;

        this.xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
        this.xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
        this.yMin = Math.min(loc1.getBlockY(), loc2.getBlockY());
        this.yMax = Math.max(loc1.getBlockY(), loc2.getBlockY());
        this.zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        this.zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
    }

    public boolean isInside(final SynergyUser synergyUser, int addX, int addY, int addZ) {
        return isInside(synergyUser.getPlayer(), addX, addY, addZ);
    }

    public boolean isInside(final Player player, int addX, int addY, int addZ) {
        final ZoneVector curr = new ZoneVector(
            player.getLocation().getBlockX(),
            player.getLocation().getBlockY(),
            player.getLocation().getBlockZ()
        );
        final ZoneVector min = new ZoneVector(
            Math.min(
                    this.getFirstLocation().getBlockX()-addX,
                    this.getSecondLocation().getBlockX()-addX),
            Math.min(
                    this.getFirstLocation().getBlockY()-addY,
                    this.getSecondLocation().getBlockY()-addY),
            Math.min(
                    this.getFirstLocation().getBlockZ()-addZ,
                    this.getSecondLocation().getBlockZ()-addZ
            )
        );
        final ZoneVector max = new ZoneVector(
            Math.max(
                    this.getFirstLocation().getBlockX()+addX,
                    this.getSecondLocation().getBlockX())+addX,
            Math.max(
                    this.getFirstLocation().getBlockY()+addY,
                    this.getSecondLocation().getBlockY()+addY),
            Math.max(
                    this.getFirstLocation().getBlockZ()+addZ,
                    this.getSecondLocation().getBlockZ()+addZ
            )
        );
        return curr.isInAABB(min, max);
    }

    public boolean isInside(final SynergyUser synergyUser){
        return this.isInside(synergyUser.getPlayer(), 0, 0 ,0);
    }

    public boolean isInside(final Player player){
        return this.isInside(player, 0, 0 ,0);
    }

    public boolean isInside(final Block block) {
        final ZoneVector curr = new ZoneVector(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ());
        final ZoneVector min = new ZoneVector(Math.min(this.getFirstLocation().getBlockX(), this.getSecondLocation().getBlockX()), Math.min(this.getFirstLocation().getBlockY(), this.getSecondLocation().getBlockY()), Math.min(this.getFirstLocation().getBlockZ(), this.getSecondLocation().getBlockZ()));
        final ZoneVector max = new ZoneVector(Math.max(this.getFirstLocation().getBlockX(), this.getSecondLocation().getBlockX()), Math.max(this.getFirstLocation().getBlockY(), this.getSecondLocation().getBlockY()), Math.max(this.getFirstLocation().getBlockZ(), this.getSecondLocation().getBlockZ()));
        return curr.isInAABB(min, max);
    }

    public static Region getFromString(String string) throws NullPointerException{
        if (string.contains("region".toUpperCase())){
            String[] args = string.split("//");
            List<Location> locations = new ArrayList<>();
            for(int i=1;i<args.length;i++){
                String arg = args[i];
                String[] parameters = arg.split(",");
                locations.add(new Location(Bukkit.getWorld(parameters[0]), Double.parseDouble(parameters[1]), Double.parseDouble(parameters[2]), Double.parseDouble(parameters[3])));
            }
            return new Region(locations.get(0), locations.get(1));
        }
        throw new NullPointerException("Region String not a valid formatted string!");
    }

    @Override
    public String toString() {
        Location a = this.firstLocation,b = this.secondLocation;
        return "REGION//"+a.getWorld().getName()+","+a.getX()+","+a.getY()+","+a.getZ()+"//"+b.getWorld().getName()+","+b.getX()+","+b.getY()+","+b.getZ();
    }

    public String serialize(){
        return this.toString();
    }

    public Iterator<Block> blockList() {
        List<Block> bL = new ArrayList<>(this.getTotalBlockSize());
        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                for (int z = zMin; z <= zMax; ++z) {
                    bL.add(this.firstLocation.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return bL.iterator();
    }

    public int getHeight() {
        return this.yMax - this.yMin + 1;
    }

    public int getXWidth() {
        return this.xMax - this.xMin + 1;
    }

    public int getZWidth() {
        return this.zMax - this.zMin + 1;
    }

    public int getTotalBlockSize() {
        return this.getHeight() * this.getXWidth() * this.getZWidth();
    }

    public double getDistance() {
        return this.getFirstLocation().distance(this.getSecondLocation());
    }

    public double getDistanceSquared() {
        return this.getFirstLocation().distanceSquared(this.getSecondLocation());
    }

    public Location getFirstLocation(){
        return UtilLoc.newInstance(this.firstLocation);
    }

    public Location getSecondLocation(){
        return UtilLoc.newInstance(this.secondLocation);
    }

    public Location randomLocation(){
        Location min = this.firstLocation,
            max = this.secondLocation;
        Location range = new Location(min.getWorld(), Math.abs(max.getX() - min.getX()),
            min.getY(), Math.abs(max.getZ() - min.getZ()));
        return new Location(min.getWorld(), (Math.random() * range.getX()) +
            (Math.min(min.getX(), max.getX())), range.getY(),
            (Math.random() * range.getZ()) + (Math.min(min.getZ(), max.getZ())));
    }

}

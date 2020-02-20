package usa.devrocoding.synergy.spigot.assets.wand.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;
import java.util.List;

public class Region {

    @Getter @Setter
    private Location firstLocation = null, secondLocation = null;

    public boolean isInside(final SynergyUser user) {
        final ZoneVector curr = new ZoneVector(user.getPlayer().getLocation().getBlockX(), user.getPlayer().getLocation().getBlockY(), user.getPlayer().getLocation().getBlockZ());
        final ZoneVector min = new ZoneVector(Math.min(this.getFirstLocation().getBlockX(), this.getSecondLocation().getBlockX()), Math.min(this.getFirstLocation().getBlockY(), this.getSecondLocation().getBlockY()), Math.min(this.getFirstLocation().getBlockZ(), this.getSecondLocation().getBlockZ()));
        final ZoneVector max = new ZoneVector(Math.max(this.getFirstLocation().getBlockX(), this.getSecondLocation().getBlockX()), Math.max(this.getFirstLocation().getBlockY(), this.getSecondLocation().getBlockY()), Math.max(this.getFirstLocation().getBlockZ(), this.getSecondLocation().getBlockZ()));
        return curr.isInAABB(min, max);
    }

    public Region getFromString(String string) throws NullPointerException{
        if (string.contains("region".toUpperCase())){
            String[] args = string.split("//");
            List<Location> locations = new ArrayList<>();
            for(int i=1;i<=args.length;i++){
                String arg = args[i];
                String[] parameters = arg.split(",");
                locations.add(new Location(Bukkit.getWorld(parameters[0]), Double.valueOf(parameters[0]), Double.valueOf(parameters[0]), Double.valueOf(parameters[0])));
            }
            this.setFirstLocation(locations.get(0));
            this.setFirstLocation(locations.get(1));
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
}

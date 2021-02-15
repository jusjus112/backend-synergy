package usa.devrocoding.synergy.spigot.warp.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.warp.exception.WarpAlreadyExists;

public class Warp {

    @Getter
    private String name;
    @Getter @Setter
    private Location location;

    public Warp(Location location, String name) throws WarpAlreadyExists {
        this.location = location;
        this.name = name;

        Core.getPlugin().getWarpManager().addWarp(this);
    }

    public String getPermissionNode(){
        return "warp."+this.getName();
    }

    public void delete()throws NullPointerException{
        Core.getPlugin().getWarpManager().removeWarp(this.getName());
    }

    public void teleportTo(SynergyUser user){
        user.getPlayer().teleport(
                new Location(
                        this.getLocation().getWorld(),
                        this.getLocation().getX(),
                        this.getLocation().getY(),
                        this.getLocation().getZ(),
                        this.getLocation().getYaw(),
                        this.getLocation().getPitch()
                )
        );
    }

}

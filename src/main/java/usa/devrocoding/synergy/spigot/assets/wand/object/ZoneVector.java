package usa.devrocoding.synergy.spigot.assets.wand.object;

public class ZoneVector
{
    private int x;
    private int y;
    private int z;
    
    public ZoneVector(final int x, final int y, final int z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }
    
    public boolean isInAABB(final ZoneVector min, final ZoneVector max) {
        return this.x <= max.x && this.x >= min.x && this.z <= max.z && this.z >= min.z && this.y <= max.y && this.y >= min.y;
    }
}

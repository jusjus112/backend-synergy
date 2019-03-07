package usa.devrocoding.synergy.spigot.version;

import org.bukkit.Bukkit;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class VersionManager extends Module {

    public VersionManager(Core plugin){
        super(plugin, "Version Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public Class<?> getServerClass(String afterPackage){
        String servPack = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + servPack + "." + afterPackage);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getServerVersion(){
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public boolean checkVersion(double version){
        String pack = Bukkit.getServer().getClass().getPackage().getName().replaceAll("_", ".");
        return pack.contains(Double.toString(version));
    }

}

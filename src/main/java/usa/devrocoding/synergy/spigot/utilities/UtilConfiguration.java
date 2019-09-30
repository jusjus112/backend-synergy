package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class UtilConfiguration {

    public static void locationToCleanStructure(String path, FileConfiguration fileConfiguration, Location location){
        fileConfiguration.set(path+".WORLD", location.getWorld().getName());
        fileConfiguration.set(path+".X", location.getX());
        fileConfiguration.set(path+".Y", location.getY());
        fileConfiguration.set(path+".Z", location.getZ());
    }

    public static Location getFromFile(String path, FileConfiguration fileConfiguration){
        return new Location(
                Bukkit.getWorld(fileConfiguration.getString(path+".WORLD")),
                fileConfiguration.getDouble(path+".X"),
                fileConfiguration.getDouble(path+".Y"),
                fileConfiguration.getDouble(path+".Z")
        );
    }

}

package usa.devrocoding.synergy.spigot.warp;

import com.github.enerccio.gson.builders.JsonBuilder;
import com.github.enerccio.gson.builders.ValueBuilder;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.files.json.JSONFile;
import usa.devrocoding.synergy.spigot.warp.command.CommandWarp;
import usa.devrocoding.synergy.spigot.warp.exception.WarpAlreadyExists;
import usa.devrocoding.synergy.spigot.warp.object.Warp;

import java.util.ArrayList;
import java.util.List;

public class WarpManager extends Module {

    @Getter
    private List<Warp> warps = new ArrayList<>();

    public WarpManager(Core plugin){
        super(plugin, "Warp Manager", false);

        registerCommand(
                new CommandWarp(getPlugin())
        );
    }

    @Override
    public void onReload(String response) {}

    public void addWarp(Warp warp) throws WarpAlreadyExists {
        if (this.warps.contains(warp)){
            throw new WarpAlreadyExists("Warp already exists");
        }

        for (Warp w : this.warps){
            if (w.getName().equalsIgnoreCase(warp.getName())){
                throw new WarpAlreadyExists("Warp with the same name already exists");
            }
        }

        this.warps.add(warp);
        getPlugin().getRunnableManager().runTaskAsynchronously("save warps", core -> this.saveWarps());
    }

    public Warp getWarp(String name) throws NullPointerException{
        for (Warp w : this.warps){
            if (w.getName().equalsIgnoreCase(name)){
                return w;
            }
        }
        throw new NullPointerException("No warp found with the name " + name);
    }

    public void removeWarp(String name)throws NullPointerException{
        Warp warp = getWarp(name);
        this.warps.remove(warp);
    }

    public void cacheSavedWarps(){
        try{
            this.warps.clear();
            JSONFile jsonFile = getPlugin().getPluginManager().getFileStructure().getJSONFile("warps");
            jsonFile.getAs("warps").getAsJsonArray().forEach(jsonElement -> {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                try {
                    new Warp(
                        new Location(
                            Bukkit.getWorld(jsonObject.get("WORLD").getAsString()),
                            jsonObject.get("X").getAsDouble(),
                            jsonObject.get("Y").getAsDouble(),
                            jsonObject.get("Z").getAsDouble(),
                            jsonObject.get("YAW").getAsLong(),
                            jsonObject.get("PITCH").getAsLong()
                        ),
                        jsonObject.get("name").getAsString().replaceAll("'", "")
                    );
                } catch (WarpAlreadyExists warpAlreadyExists) {
                    warpAlreadyExists.printStackTrace();
                }
            });
        }catch (FileNotFoundException exception){
            Synergy.error("Error while caching warps..");
            Synergy.error(exception.getMessage());
        }
    }

    public void saveWarps(){
        try{
            JSONFile jsonFile = getPlugin().getPluginManager().getFileStructure().getJSONFile("warps");
            ValueBuilder valueBuilder = new JsonBuilder().object(object -> {
                object.putArray("warps", array ->
                    this.warps.iterator().forEachRemaining(warp ->
                        array.addObject(warpObject -> {
                            warpObject.put("name", warp.getName());
                            warpObject.put("WORLD", warp.getLocation().getWorld().getName());
                            warpObject.put("X", warp.getLocation().getX());
                            warpObject.put("Y", warp.getLocation().getY());
                            warpObject.put("Z", warp.getLocation().getZ());
                            warpObject.put("YAW", warp.getLocation().getYaw());
                            warpObject.put("PITCH", warp.getLocation().getPitch());
                })));
            });

            jsonFile.write(valueBuilder);
        }catch (FileNotFoundException exception){
            Synergy.error("Error while saving warps.");
            Synergy.error(exception.getMessage());
        }
    }
}

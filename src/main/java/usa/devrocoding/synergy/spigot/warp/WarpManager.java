package usa.devrocoding.synergy.spigot.warp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
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
    public void reload(String response) {}

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
    }

    public void saveAllWarps(){
        try {
            for(Warp warp : getWarps()){
                saveWarp(warp);
            }
        }catch (Exception e){
            Synergy.error(e.getMessage());
        }
    }

    public Warp getWarp(String name) throws NullPointerException{
        for (Warp w : this.warps){
            if (w.getName().equalsIgnoreCase(name)){
                return w;
            }
        }
        throw new NullPointerException("No warp found with the name "+name);
    }

    public void removeWarp(String name)throws NullPointerException{
        Warp warp = getWarp(name);
        this.warps.remove(warp);
    }

    public void cacheSavedWarps(){
        try{
            this.warps.clear();
            JSONFile jsonFile = getPlugin().getPluginManager().getFileStructure().getJSONFile("storage");
            JsonObject mainOBJ = jsonFile.get();
            if (mainOBJ != null && jsonFile.get().has("warps")) {
                JsonElement element = mainOBJ.get("warps");
                if (element.isJsonArray()){
                    JsonArray array = element.getAsJsonArray();
                    array.iterator().forEachRemaining(jsonElement ->
                        {
                            JsonObject object = jsonElement.getAsJsonObject();
                            String name = object.get("name").getAsString();
                            Location location = new Location(
                                    Bukkit.getServer().getWorld(object.get("WORLD").getAsString()),
                                    object.get("X").getAsDouble(),
                                    object.get("Y").getAsDouble(),
                                    object.get("Z").getAsDouble()
                            );
                            try {
                                new Warp(location, name);
                            }catch (WarpAlreadyExists nothing){}
                        }
                    );
                }
            }
        }catch (Exception e){
            Sam.getRobot().error(this, e.getMessage(), "I can't fix it myself so it has to be done by a developer!", e);
        }
    }

    public void saveWarp(Warp warp)throws Exception{
        try{
            JSONFile jsonFile = getPlugin().getPluginManager().getFileStructure().getJSONFile("storage");
            JsonObject mainOBJ = jsonFile.get();
            if (mainOBJ != null && jsonFile.get().has("warps")) {
                JsonObject object = jsonFile.get();
                JsonElement element = object.get("warps");
                if (element.isJsonArray()){
                    JsonArray array = element.getAsJsonArray();

                    JsonArray warps = new JsonArray();
                    JsonObject warp_data = new JsonObject();

                    warp_data.addProperty("name", warp.getName());
                    warp_data.addProperty("WORLD", warp.getLocation().getWorld().getName());
                    warp_data.addProperty("X", warp.getLocation().getX());
                    warp_data.addProperty("Y", warp.getLocation().getY());
                    warp_data.addProperty("Z", warp.getLocation().getZ());

//                    warps.add(warp_data);
                    array.add(warp_data);

                    object.add("warps", element);
                    jsonFile.write("warps", element, true).finish();
                    return;
                }
            }
            JsonArray array = new JsonArray();

//            JsonObject warps = new JsonObject();
            JsonObject warp_data = new JsonObject();

            warp_data.addProperty("name", warp.getName());
            warp_data.addProperty("WORLD", warp.getLocation().getWorld().getName());
            warp_data.addProperty("X", warp.getLocation().getX());
            warp_data.addProperty("Y", warp.getLocation().getY());
            warp_data.addProperty("Z", warp.getLocation().getZ());

//            warps.add(warp.getName(),warp_data);
            array.add(warp_data);

            jsonFile.write("warps", array, true).finish();
        }catch (Exception e){
            Sam.getRobot().error(this, e.getMessage(), "I can't fix it myself so it has to be done by a developer!", e);
            throw new Exception(e.getMessage());
        }
    }
}

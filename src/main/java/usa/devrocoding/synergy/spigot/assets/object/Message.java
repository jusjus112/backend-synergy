package usa.devrocoding.synergy.spigot.assets.object;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.assets.Pair;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Message {

    @Getter
    private static HashMap<String, Object> messageCache = new HashMap<>();

    public static String format(String key, String defaultMessage, Pair<String, String>... replaceList){
        String message;
        if (getMessageCache().containsKey(key)){
            message = C.colorize(getMessageCache().get(key).toString());
        }else{
            message = defaultMessage;
            getMessageCache().put(key, defaultMessage);
            Synergy.error("Message for key '"+key+"' not found, using default one!");
        }

        Pair<String, String>[] replace = (replaceList.length>0?replaceList:new Pair[C.values().length]);
        for(C color : C.values()){
            Arrays.asList(replace).add(new Pair<>("color_"+color.toString().toLowerCase(),"&"+color.getColor().getChar()));
        }

        if (replace.length > 0){
            for (Pair<String, String> map : replace) {
                message.replace(map.getLeft(), map.getRight());
            }
        }

        return C.colorize(message);
    }

    public static void deint(YMLFile messageFile){
        messageFile.set(getMessageCache());
    }

    public static void checkForUpdates(YMLFile messageFile){
        init(messageFile);
    }

    public static void init(YMLFile messageFile){
        FileConfiguration yml = messageFile.get();

        for(String section : yml.getConfigurationSection("messages").getKeys(true)){
            String data = yml.getString("messages."+section);
            if (data != null && !data.equals("") && !data.contains("MemorySection")) {
                getMessageCache().put(section, data);
            }
        }
    }

}

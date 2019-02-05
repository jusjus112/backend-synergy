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
        if (getMessageCache().containsKey("messages."+key)){
            message = getMessageCache().get("messages."+key).toString();
            Synergy.debug("2");
        }else{
            Synergy.debug("3");
            message = defaultMessage;
            getMessageCache().put("messages."+key, defaultMessage);
            Synergy.error("Message for key '"+key+"' not found, using default one!");
        }

        Synergy.debug("Message1: "+message, replaceList.length+" = LENGTH");

        List<Pair<String, String>> replace = new ArrayList<>();

        if (replaceList!= null && replaceList.length > 0)
            replace.addAll(Arrays.asList(replaceList));

        for(C color : C.values()){
            replace.add(new Pair<>(
                            "color_"+color.toString().toLowerCase(),
                            "&"+color.getColor().getChar()
                            )
                        );
        }

        Synergy.debug("Message2: "+message);

        if (replace.size() > 0){
            for (Pair<String, String> map : replace) {
                message.replace(map.getLeft(), map.getRight());
            }
        }

        Synergy.debug("Message3: "+message);

        return C.colorize(message);
    }

    public static void deint(YMLFile messageFile){
//        System.out.print(getMessageCache());
//        messageFile.set(getMessageCache());
    }

    public static void checkForUpdates(YMLFile messageFile){
        messageFile.set(getMessageCache());
    }

    public static void init(YMLFile messageFile){
        FileConfiguration yml = messageFile.get();

        if (yml.contains("messages")) {
            for (String section : yml.getConfigurationSection("messages").getKeys(true)) {
                String data = yml.getString("messages." + section);
                if (data != null && !data.equals("") && !data.contains("MemorySection")) {
                    Synergy.debug("messages."+section+" - "+data);
                    getMessageCache().put("messages."+section, data);
                }
            }
        }else{
            messageFile.set(new HashMap<String, Object>(){{
                put("messages.synergy.info", "DEFAULT");
            }});
            init(messageFile);
        }
    }

}

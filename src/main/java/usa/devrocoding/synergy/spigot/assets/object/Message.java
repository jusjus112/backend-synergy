package usa.devrocoding.synergy.spigot.assets.object;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.assets.Pair;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Message extends Module {

    @Getter
    private static HashMap<String, Object> messageCache = new HashMap<>();
    @Getter
    private static YMLFile messageFile;

    public Message(Core plugin){
        super(plugin, "Message Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public static String format(String key, String defaultMessage, Pair<String, String>... replaceList){
        String message;
        if (getMessageCache().containsKey("messages."+key)){
            message = getMessageCache().get("messages."+key).toString();
//            Synergy.debug("2");
        }else{
//            Synergy.debug("3");
            message = defaultMessage;
            getMessageCache().put("messages."+key, defaultMessage);
//            Synergy.warn("Message for key '"+key+"' not found, adding it now. Using default one.");
        }

//        Synergy.debug("Message1: "+message, replaceList.length+" = LENGTH");

        List<Pair<String, String>> replace = new ArrayList<>();

        if (replaceList.length > 0) {
            replace.addAll(Arrays.asList(replaceList));
        }

        for(C color : C.values()){
            replace.add(new Pair<>(
                    "color_"+color.toString().toLowerCase(),
                    "&"+color.getColor().getChar()
                    )
                );
        }

//        Synergy.debug("Message2: "+message);

        if (replace.size() > 0){
            for (Pair<String, String> map : replace) {
                message.replace(map.getLeft(), map.getRight());
            }
        }

//        Synergy.debug("Message3: "+message);

        if (key.contains("colors")){
            return message;
        }

        return C.colorize(message);
    }

    public void deint(YMLFile messageFile){
//        System.out.print(getMessageCache());
//        messageFile.set(getMessageCache());
    }

    public static void add(HashMap<String, Object> map){
        HashMap<String, Object> map2 = new HashMap<>();

        for(String key : map.keySet()){
            map2.put("messages."+key, map.get(key));
        }

        getMessageCache().putAll(map2);
        update();
    }

    public static void update(){
        messageFile.set(getMessageCache());
    }

    public void init(YMLFile messagefile){
        messageFile = messagefile;

        FileConfiguration yml = messageFile.get();

        if (yml.contains("messages")) {
            for (String section : yml.getConfigurationSection("messages").getKeys(true)) {
                String data = yml.getString("messages." + section);
                if (data != null && !data.equals("") && !data.contains("MemorySection")) {
//                    Synergy.debug("messages."+section+" - "+data);
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

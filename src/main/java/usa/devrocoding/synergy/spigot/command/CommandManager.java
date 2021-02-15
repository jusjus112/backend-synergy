package usa.devrocoding.synergy.spigot.command;

import com.google.common.collect.Lists;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.object.Message;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager extends Module{

    private final List<SynergyCommand> commands = Lists.newArrayList();
    private Map<String, Command> knownCommands;
    private SimpleCommandMap commandMap;

    public CommandManager(Core plugin) {
        super(plugin, "Command Manager", false);

//        if (!getPlugin().getVersionManager().checkVersion(1.8)) {
//            registerListener(
//                new TabCompleteListener()
//            );
//        }

        try {
            this.commandMap = (SimpleCommandMap) getPrivateField(plugin.getServer().getPluginManager(), "commandMap");
            this.knownCommands = (HashMap<String, Command>) getPrivateField(commandMap, "knownCommands"); // Line 293
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Message.add(new HashMap<String, Object>(){{
            put("player.not_found", "Could not find %player% %color_chat% %attempt%");
        }});

        List<String> disabled_cmds = new ArrayList<String>(){{
            addAll(Arrays.asList("pl","plugins","ver","version","deop","testforblocks","setblock","?",
                "me","testfor","scoreboard","tellraw","teleport","defaultgamemode",
                "about","whisper","tell","msg","weather","save-all",
                "seed","xp","tp","playsound","title","say","spreadplayers",
                "advancement","toggledownfall","worldborder","trigger",
                "fill","particle","setidletimeout","setworldspawn","locate","recipe",
                "function","pardon","ban","kick","testforblock","spawnpoint","clone","enchant","w",
                "blockdata","entitydata","stopsound","stats","spigot",
                "banlist","list","clear","execute","replaceitem",
                "ban-ip","pardon-ip","save-off","save-on","restart",
                "debug", "eather","time","effect","gamerule","difficulty",
                "give","help","stop","time","whitelist","spigot","restart",
                "powertool","powertooltoggle","tp2p","tele","tp","gm", "gmc","gma","gmc","gms","gmsp","gmt",
                "creativemode","spectatormode","adventuremode","gamemode",

                "summon",

                "nuke","more","banip","backup","compass","kittycannon",
                "pt","setworth","worth","ul",
                "uptime","v","tptoggle","tnt","whereami","ping","pong","ptt","list","plist",
                "remjail","pttoggle","mem","pardonip","memory","smite",
                "survivalmode","shoutworld","bcw","godmode","news","grenade","vanish",
                "info","fireball","strike","lightning",
                "me","tree","firework","gc","god"
                ));
        }};

//        if (Synergy.isProduction()){
            disabled_cmds.addAll(Arrays.asList("kill","reload","rl","op"));
//        }

        for (String cmd : disabled_cmds){
            unregisterMinecraftCommand(cmd);
        }
    }

    private Object getPrivateField(Object object, String field) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = field.equals("commandMap") ?
                clazz.getDeclaredField(field) :
                field.equals("knownCommands") ?
                        Bukkit.getVersion().contains("1.14") ?
                                clazz.getSuperclass().getDeclaredField(field) :
                                clazz.getDeclaredField(field) :
                        null;
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    @Override
    public void reload(String response) {

    }

    public boolean isCommand(String command){
        for(SynergyCommand cmd : commands){
            for(String alias : cmd.getAliases()){
                if (alias.equalsIgnoreCase(command)){
                    return true;
                }
            }
        }
        return false;
    }

    public void registerCommand(SynergyCommand synergyCommand){
        this.commands.add(synergyCommand);
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            synergyCommand.getAliases().forEach(s -> {
                this.knownCommands.put(s, synergyCommand);
                this.knownCommands.put("synergy:" + s, synergyCommand);
            });
        }, 10L);
    }

    public void unregisterMinecraftCommand(String command) {
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            this.knownCommands.remove(command);
            this.knownCommands.remove("minecraft:" + command);
            this.knownCommands.remove("bukkit:" + command);
            this.knownCommands.remove("spigot:" + command);
            this.knownCommands.remove("essentials:" + command);
            this.knownCommands.remove("essentials:e" + command);
        }, 1L);
    }

    public List<SynergyCommand> getCommands() {
        return commands;
    }
}

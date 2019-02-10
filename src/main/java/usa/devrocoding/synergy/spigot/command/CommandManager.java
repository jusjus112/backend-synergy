package usa.devrocoding.synergy.spigot.command;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.server.TabCompleteEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager extends Module implements Listener {

    private final List<SynergyCommand> commands = Lists.newArrayList();
    private Map<String, SynergyCommand> knownCommands;

    public CommandManager(Core plugin) {
        super(plugin, "Command Manager");

        try {
            SimpleCommandMap commandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();
            Field field = commandMap.getClass().getDeclaredField("knownCommands");

            field.setAccessible(true);
            this.knownCommands = (Map<String, SynergyCommand>) field.get(commandMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Message.add(new HashMap<String, Object>(){{
            put("player.not_found", "Could not find %player% %color_chat% %attempt%");
        }});

        String[] disabled_cmds = new String[]{
                "me","pl","plugins","ver","version","about","help","whisper","gamerule","tell","msg",
                "seed","give","xp","tp","kill","playsound","title","say","testfor","spreadplayers",
                "advancement","scoreboard","tellraw","toggledownfall","worldborder","trigger",
                "fill","particle","setidletimeout","setworldspawn","time","locate","recipe","?",
                "summon","function","pardon","ban","kick","testforblock","spawnpoint","clone","enchant","w",
                "weather","teleport","blockdata","entitydata","difficulty","stopsound","stats",
                "banlist","op","deop","list","clear","execute","replaceitem","testforblocks",
                "ban-ip","defaultgamemode","effect","pardon-ip","save-off","save-on","setblock",
                "debug","reload",

                // Essentials Commands
                "nuke","motd","more","kickall","msgtoggle","banip","backup","compass","condense","kittycannon",
                "pt","powertool","powertooltoggle","realname","nick","setjail","setworth","tpall","worth","ul","unjail",
                "uptime","v","tptoggle","tnt","tp2p","tele","whereami","ping","pong","ptt","list","plist","shout","rmjail",
                "remjail","pttoggle","mem","broadcast","pardonip","memory","rules","essentials","smite",
                "survivalmode","creativemode","spectatormode","adventuremode","shoutworld","bcw","godmode","news","grenade","vanish",
                "gamemode", "gm", "gmc","gma","gmc","gms","gmsp","gmt", "r","heal","feed","exp","afk","fly","hat",
                "info","fireball","strike","lightning","mail","me","jail","book","tree","firework","gc","god","helpop"
        };

        for (String cmd : disabled_cmds){
            unregisterMinecraftCommand(cmd);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        List<String> args = UtilString.convert(e.getMessage().split(" "));
        String cmd = args.get(0).replaceAll("/", "");

        for(SynergyCommand command : commands) {
            if(Arrays.asList(command.getAliases()).contains(cmd.toLowerCase())) {
                e.setCancelled(true);
                args.remove(0);

                SynergyUser user = getPlugin().getUserManager().getUser(e.getPlayer());
//                if(Permission.allowed(user, command.getRank(), false)) {
//                    if(Recharge.recharge(getPlugin(), e.getPlayer(), "Command " + command.getAliases()[0], command.getCooldown())) {
                        command.execute(user, e.getPlayer(), cmd, args.toArray(new String[args.size()]));
                        return;
//                    }
//                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(ServerCommandEvent e) {
        if (e.getSender() instanceof ConsoleCommandSender){
            List<String> args = UtilString.convert(e.getCommand().split(" "));
            String cmd = args.get(0).replaceAll("/", "");
            for(SynergyCommand command : commands) {
                if (command.isConsoleAllowed()) {
                    if (Arrays.asList(command.getAliases()).contains(cmd.toLowerCase())) {
                        e.setCancelled(true);
                        args.remove(0);
                        command.execute(((ConsoleCommandSender) e.getSender()), cmd, args.toArray(new String[args.size()]));
                        return;
                    }
                }
            }
        }
    }

    private boolean isCommand(String command){
        for(SynergyCommand cmd : commands){
            for(String alias : cmd.getAliases()){
                if (alias.equalsIgnoreCase(command)){
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCommand(TabCompleteEvent e) {
        List<String> list = new ArrayList<>();

        if (e.getBuffer().contains("/")) {
            String[] splitter = e.getBuffer().split(" ");
            if (splitter.length > 1) {
                if (isCommand(splitter[0].replace("/",""))) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getName().toLowerCase().startsWith(splitter[splitter.length-1].toLowerCase())){
                            list.add(p.getName());
                        }
                    }
                    e.setCompletions(list);
                }
            }
        }
    }

    public void unregisterMinecraftCommand(String command) {
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            this.knownCommands.remove(command);
            this.knownCommands.remove("minecraft:" + command);
            this.knownCommands.remove("bukkit:" + command);
            this.knownCommands.remove("essentials:" + command);
            this.knownCommands.remove("essentials:e" + command);
        }, 1L);
    }

    public List<SynergyCommand> getCommands() {
        return commands;
    }
}

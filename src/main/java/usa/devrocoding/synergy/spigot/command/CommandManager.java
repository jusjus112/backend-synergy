package usa.devrocoding.synergy.spigot.command;

import com.google.common.collect.Lists;
import net.metromc.spigot.MetroMC;
import net.metromc.spigot.Module;
import net.metromc.spigot.command.command.CommandHelp;
import net.metromc.spigot.cooldown.recharge.Recharge;
import net.metromc.spigot.user.object.User;
import net.metromc.spigot.util.Permission;
import net.metromc.spigot.util.UtilString;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandManager extends Module {

    private final List<Command> commands = Lists.newArrayList();
    private Map<String, Command> knownCommands;

    public CommandManager(MetroMC plugin) {
        super(plugin, "Command Manager");

        try {
            SimpleCommandMap commandMap = ((CraftServer) Bukkit.getServer()).getCommandMap();
            Field field = commandMap.getClass().getDeclaredField("knownCommands");

            field.setAccessible(true);
            this.knownCommands = (Map<String, Command>) field.get(commandMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        unregisterMinecraftCommand("me");
        unregisterMinecraftCommand("plugins");
        unregisterMinecraftCommand("pl");
        unregisterMinecraftCommand("version");
        unregisterMinecraftCommand("ver");
        unregisterMinecraftCommand("about");
        unregisterMinecraftCommand("help");
        unregisterMinecraftCommand("?");
        unregisterMinecraftCommand("whisper");
        unregisterMinecraftCommand("tell");
        unregisterMinecraftCommand("msg");

        commands.add(new CommandHelp(plugin));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        List<String> args = UtilString.convert(e.getMessage().split(" "));  
        String cmd = args.get(0).replaceAll("/", "");

        for(Command command : commands) {
            if(Arrays.asList(command.getAliases()).contains(cmd.toLowerCase())) {
                e.setCancelled(true);
                args.remove(0);

                User user = getPlugin().getUserManager().getUser(e.getPlayer());

                if(Permission.allowed(user, command.getRank(), false)) {
                    if(Recharge.recharge(getPlugin(), e.getPlayer(), "Command " + command.getAliases()[0], command.getCooldown())) {
                        command.execute(e.getPlayer(), args.toArray(new String[args.size()]));
                    }
                }
            }
        }
    }

    public void unregisterMinecraftCommand(String command) {
        Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
            this.knownCommands.remove(command);
            this.knownCommands.remove("minecraft:" + command);
            this.knownCommands.remove("bukkit:" + command);
        }, 1L);
    }

    public List<Command> getCommands() {
        return commands;
    }
}

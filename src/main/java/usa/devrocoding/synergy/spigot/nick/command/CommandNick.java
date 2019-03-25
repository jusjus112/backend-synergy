package usa.devrocoding.synergy.spigot.nick.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandNick extends SynergyCommand {

    public CommandNick(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Nick Command", false,"nick");

        setPlayerUsage("<player|off>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (synergyUser.hasPermission("command.nick")) {
            if (args.length > 0 && args.length < 2){
                if (args[0].equalsIgnoreCase("off")){
                    synergyUser.unNick();
                }else{
                    String name = args[0];
                    synergyUser.nick(name);
                }
            }else{
                sendUsageMessage(player);
            }
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

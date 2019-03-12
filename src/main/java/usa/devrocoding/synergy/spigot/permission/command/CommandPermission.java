package usa.devrocoding.synergy.spigot.permission.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandPermission extends SynergyCommand {

    public CommandPermission(Core plugin){
        super(plugin, "Command to handle the permissions", true, "synergyperm", "sp");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {

    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

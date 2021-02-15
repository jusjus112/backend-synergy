package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilLoc;

public class CommandTop extends SynergyCommand {

    public CommandTop(Core plugin) {
        super(plugin, "command.top", "Synergy's Top Command", false,"top");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        player.teleport(UtilLoc.getHighestBlockLocation(player.getLocation()));
        synergyUser.info("I've teleported you to the highest block");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }

}

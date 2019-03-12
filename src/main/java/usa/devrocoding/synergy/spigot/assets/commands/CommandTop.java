package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.block.Block;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandTop extends SynergyCommand {

    public CommandTop(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Top Command", false,"top");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (synergyUser.hasPermission("top")) {
            player.teleport(player.getLocation().getBlock().getWorld().getHighestBlockAt(player.getLocation()).getLocation());
            synergyUser.info("I've teleported you to the highest block");
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandWorkbench extends SynergyCommand {

    public CommandWorkbench(Core plugin) {
        super(plugin, "command.workbench", "Synergy's Workbench Command", false,"workbench", "wb", "wbench");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        player.openWorkbench(null, true);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

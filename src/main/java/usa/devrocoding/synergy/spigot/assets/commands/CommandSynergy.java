package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandSynergy extends SynergyCommand {

    public CommandSynergy(Core plugin) {
        super(plugin, "Synergy's official command", false,"synergy");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.message(
                C.getLine(),
                "§eA §6Fully Custom §eBackend system for this network with over",
                "§668.000+ lines of code.",
                "§eAuthor: §3@JusJus#0112",
                "§eVersion: " + Core.getPlugin().getDescription().getVersion(),
                "§eThis system has their first code written on §eAugust 24, 2017 and released on May 2nd 2018 made with §c"+ C.Symbol.HEARTH,
                C.getLine()
        );
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

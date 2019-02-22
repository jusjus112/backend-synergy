package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandSynergy extends SynergyCommand {

    public CommandSynergy(Core plugin) {
        super(plugin, "Synergy's official command", true,"synergy");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.message(
                C.getLine(),
                "§eA §6Fully Custom §eNetwork system for this awesome network named \"ArcadeWars\"",
                "§eAuthor: §3JusJus§e",
                "§eVersion: " + Core.getPlugin().getDescription().getVersion(),
                "§eThis system has their first release written on §eAugust 24, 2018 §7made with §c"+ C.Symbol.HEARTH.getSymbol(),
                C.getLine()
        );
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        sender.sendMessage("Test1");
    }
}

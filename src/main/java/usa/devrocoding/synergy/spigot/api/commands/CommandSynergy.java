package usa.devrocoding.synergy.spigot.api.commands;

import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandSynergy extends SynergyCommand {

    public CommandSynergy(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's official command", "synergy");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String[] args) {
        synergyUser.message(
                C.getLineWithName(),
                "§eA §6custom §enetwork system for this awesome network",
                "§eAuthor: §3JusJus§e",
                "§eVersion: " + Core.getPlugin().getDescription().getVersion(),
                "§7This system has their first release written on §eJune 24, 2018 §7made with §c"+ C.Symbol.HEARTH.getSymbol(),
                C.getLine()
        );
    }

}

package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandVotes extends SynergyCommand {

    public CommandVotes(Core plugin) {
        super(plugin, "Synergy's Vote Command", false,"votes", "vote");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.sendModifactionMessage(
            MessageModification.RAW,
            "§cYou can vote on the following sites:",
            "  §d1. www.minecraft-mp.com/server/280161/vote/",
            "  §d2. www.minecraftservers.org/vote/607304/"
        );
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

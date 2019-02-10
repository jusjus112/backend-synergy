package usa.devrocoding.synergy.spigot.two_factor_authentication.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class Command2FA extends SynergyCommand {

    public Command2FA(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's 2fa command", false,"2fa");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.message(
                "§eA §6custom §enetwork system for this awesome network"
        );
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

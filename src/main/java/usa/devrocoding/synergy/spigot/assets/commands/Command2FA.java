package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class Command2FA extends SynergyCommand {

    public Command2FA(Core plugin) {
        super(plugin, Rank.ADMIN, "Synergy's 2fa Command", false,"2fa");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {

    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

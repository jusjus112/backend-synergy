package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandDiscord extends SynergyCommand {

    public CommandDiscord(Core plugin) {
        super(plugin, "Synergy's Discord Command", false,"discord");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.info("You can join our discord with: &bwww.discord.mirageprisons.net");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

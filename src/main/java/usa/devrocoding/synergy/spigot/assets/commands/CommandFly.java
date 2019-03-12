package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.MemoryUtils;

import static org.bukkit.Bukkit.getServer;

public class CommandFly extends SynergyCommand {

    public CommandFly(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Fly Command", false,"fly");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (synergyUser.hasPermission("fly.toggle")) {
            player.setAllowFlight(!player.getAllowFlight());
            synergyUser.info("I "+(player.getAllowFlight()? "enabled":"disabled")+" your fly for you");
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

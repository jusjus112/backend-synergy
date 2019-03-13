package usa.devrocoding.synergy.spigot.user.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.gui.HomeGUI;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandHome extends SynergyCommand {

    public CommandHome(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Home Command", false,"home");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (synergyUser.hasPermission("home")) {
            new HomeGUI(getPlugin(), synergyUser).open(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

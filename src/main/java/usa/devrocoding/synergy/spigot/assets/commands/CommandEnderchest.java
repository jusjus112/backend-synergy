package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandEnderchest extends SynergyCommand {

    public CommandEnderchest(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Enderchest Command", false,"enderchest", "ec", "echest");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (synergyUser.hasPermission("command.enderchest")) {
            player.openInventory(player.getEnderChest());
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

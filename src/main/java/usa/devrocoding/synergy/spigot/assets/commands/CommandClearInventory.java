package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandClearInventory extends SynergyCommand {

    public CommandClearInventory(Core plugin) {
        super(plugin, Rank.NONE, "Clear your inventory", false,"clearinventory", "ci", "clear");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length == 0){
            synergyUser.warning("Please use /clear confirm if you are sure!");
            return;
        }
        if (args[0].equalsIgnoreCase("confirm")) { // Only possible in your console
            player.getInventory().setContents(new ItemStack[]{});
            player.getInventory().setArmorContents(new ItemStack[]{});
            player.getInventory().setItemInOffHand(null);
            synergyUser.info("Successful cleared your inventory!");
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

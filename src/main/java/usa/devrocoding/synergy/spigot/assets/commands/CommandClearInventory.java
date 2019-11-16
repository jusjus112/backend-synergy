package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Collections;

public class CommandClearInventory extends SynergyCommand {

    public CommandClearInventory(Core plugin) {
        super(plugin, Rank.NONE, "Clear's an inventory", false,"clearinventory", "ci", "clear");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        player.getInventory().setContents(new ItemStack[]{});
        player.getInventory().setArmorContents(new ItemStack[]{});
        player.getInventory().setItemInOffHand(null);
        synergyUser.info("Successful cleared your inventory!");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

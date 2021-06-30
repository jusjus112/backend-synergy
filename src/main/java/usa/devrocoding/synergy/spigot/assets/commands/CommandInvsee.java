package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandInvsee extends SynergyCommand {

  public CommandInvsee(Core plugin) {
    super(plugin, "command.invsee", "Invsee an player's inventory",
        false,"invsee", "inventorysee");
  }

  @Override
  public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
    if (args.length > 0){
      SynergyUser targetUser = getPlugin().getUserManager().getUser(args[0], false);

      if (targetUser == null){
        synergyUser.error(args[0] + " is not online!");
        return;
      }

      synergyUser.getPlayer().openInventory(targetUser.getPlayer().getInventory());
    }
  }

  @Override
  public void execute(ConsoleCommandSender sender, String command, String[] args) {

  }
}

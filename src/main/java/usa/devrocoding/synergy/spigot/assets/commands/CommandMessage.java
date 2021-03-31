package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandMessage extends SynergyCommand {

  public CommandMessage(Core plugin) {
    super(plugin, "Synergy's Message Command", false,"kill");
  }

  @Override
  public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {

  }

  @Override
  public void execute(ConsoleCommandSender sender, String command, String[] args) {

  }
}

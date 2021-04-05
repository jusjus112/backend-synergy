package usa.devrocoding.synergy.spigot.economy.command;

import java.math.BigDecimal;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.economy.object.Economy;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandCrystal extends SynergyCommand {

  public CommandCrystal(Core plugin) {
    super(plugin, Rank.NONE, "Check your balance", true,
        "crystal", "crystals", "crys");
  }

  @Override
  public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
    if (args.length == 0){
      synergyUser.info("Your Crystals: " + synergyUser.getEconomy().getShards());
    }else{
      sendUsageMessage(player);
    }
  }

  @Override
  public void execute(ConsoleCommandSender sender, String command, String[] args) {
    if (args.length > 2) {
      int amount = 0;
      try{
        amount = Integer.parseInt(args[2]);
      }catch (Exception e){
        sender.sendMessage(args[2] + " is not a valid number (decimal)!");
      }

      SynergyUser targetUser = getPlugin().getUserManager().getFakeUser(args[1]);
      if (targetUser == null) {
        sender.sendMessage(args[1] + " doesn't exist in our database, nor is online!");
        return;
      }
      if (amount != 0) {
        Synergy.debug(targetUser.getDisplayName() + " = USER");
        Synergy.debug(amount + " = GIVEN CRYSTALS");

        if (amount < 0){
          sender.sendMessage("Crystals cannot go lower than zero!");
          return;
        }

        Economy economy = targetUser.getEconomy();

        if (args[0].equalsIgnoreCase("give")) {
          economy.setShards(economy.getShards() + amount);
          sender.sendMessage("Added " + amount +
              " to the crystal balance from " + targetUser.getDisplayName());
          targetUser.updateDatabase();
        } else if (args[0].equalsIgnoreCase("remove")) {
          economy.setShards(economy.getShards() - amount);
          sender.sendMessage("Removed " + amount +
              " from the crystal balance from " + targetUser.getDisplayName());
          targetUser.updateDatabase();
        } else if (args[0].equalsIgnoreCase("set")) {
          economy.setShards(amount);
          sender.sendMessage("Set the shard balance for " + targetUser.getDisplayName() +
              " to " + amount);
          targetUser.updateDatabase();
        }
      }
    }
  }
}

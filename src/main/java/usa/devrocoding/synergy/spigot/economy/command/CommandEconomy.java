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

public class CommandEconomy extends SynergyCommand {

  public CommandEconomy(Core plugin) {
    super(plugin, Rank.NONE, "Check your balance", false,
        "economy", "eco", "money", "coins", "balance", "bal");
  }

  @Override
  public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
    if (args.length == 0){
      synergyUser.info("Your Coins: " + synergyUser.getEconomy().getCoins());
    }else{
      if (synergyUser.getRank().isHigherThan(Rank.ADMIN)){

        if (args.length > 2) {
          BigDecimal bigDecimal = null;
          try{
            bigDecimal = new BigDecimal(args[2]);
          }catch (Exception e){
            synergyUser.error(args[2] + " is not a valid number (decimal)!");
          }

          SynergyUser targetUser = getPlugin().getUserManager().getFakeUser(args[1]);
          if (targetUser == null) {
            synergyUser.error(args[1] + " doesn't exist in our database, nor is online!");
            return;
          }
          if (bigDecimal != null) {
            Synergy.debug(targetUser.getDisplayName() + " = USER");
            Synergy.debug(bigDecimal.doubleValue() + " = GIVEN COINS");

            if (bigDecimal.doubleValue() < 0){
              synergyUser.error("Coins cannot go lower than zero!");
              return;
            }

            Economy economy = targetUser.getEconomy();

            if (args[0].equalsIgnoreCase("give")) {
              economy.setCoins(economy.getCoins() + bigDecimal.doubleValue());
              synergyUser.info("Added " + bigDecimal.doubleValue() +
                  " to the balance from " + targetUser.getDisplayName());
              targetUser.updateDatabase();
            } else if (args[0].equalsIgnoreCase("remove")) {
              economy.setCoins(economy.getCoins() - bigDecimal.doubleValue());
              synergyUser.info("Removed " + bigDecimal.doubleValue() +
                  " from the balance from " + targetUser.getDisplayName());
              targetUser.updateDatabase();
            } else if (args[0].equalsIgnoreCase("set")) {
              economy.setCoins(bigDecimal.doubleValue());
              synergyUser.info("Set the balance for " + targetUser.getDisplayName() +
                  " to " + bigDecimal.doubleValue());
              targetUser.updateDatabase();
            }
          }
        }
      }else{
        synergyUser.sendNoPermissions();
      }
    }
  }

  @Override
  public void execute(ConsoleCommandSender sender, String command, String[] args) {

  }
}

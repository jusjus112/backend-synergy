package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilLoc;

public class CommandWeather extends SynergyCommand {

    public CommandWeather(Core plugin) {
        super(plugin, "command.weather", "Synergy's Weather Command", false,"weather");

        setPlayerUsage("sun", "rain");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length == 1){
            if (args[0].equalsIgnoreCase("sun")){
                player.getWorld().setWeatherDuration(0);
                synergyUser.info("Removed the sadness from this world.");
            }else if (args[0].equalsIgnoreCase("rain")){
                player.getWorld().setWeatherDuration(0);
                synergyUser.info("Removed the happiness from this world.");
            }
        }else{
            sendUsageMessage(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }

}

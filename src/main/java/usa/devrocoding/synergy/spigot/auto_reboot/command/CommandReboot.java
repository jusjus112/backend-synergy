package usa.devrocoding.synergy.spigot.auto_reboot.command;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandReboot extends SynergyCommand {

    public CommandReboot(Core plugin) {
        super(plugin, "command.reboot", "Synergy's Reboot Command", true,"reboot", "stop", "shutdown");

        setConsoleUsage("[confirm]");
        setPlayerUsage("<seconds>");
    }

    @Override // /reboot <seconds>
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length == 1){
            int seconds = 5;
            try{
                seconds = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                synergyUser.error("Wrong format! " + args[1] + " is not a number.");
                return;
            }
            synergyUser.info("Starting the reboot sequence for you...");
            Core.getPlugin().getAutoRebootManager().rebootServer(SynergyPeriod.SECOND.getCustom(seconds));
        }else{
            sendUsageMessage(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length == 0){
            sender.sendMessage(Synergy.SynergyColor.ERROR+"Please use /stop confirm if you are sure!");
        }else{
            Integer secs = 5;
            if (args[0].equalsIgnoreCase("confirm")){ // Only possible in your console
                Bukkit.getServer().shutdown();
                return;
            }
            try{
                secs = Integer.valueOf(args[1]);
            }catch (NumberFormatException e){
                sender.sendMessage("Wrong format! " + args[1] + " is not a number.");
                return;
            }
            sender.sendMessage("Starting the reboot sequence for you...");
            Core.getPlugin().getAutoRebootManager().rebootServer(SynergyPeriod.SECOND.getCustom(secs));
        }
    }
}

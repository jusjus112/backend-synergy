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
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.info("Starting the reboot sequence for you...");
        Core.getPlugin().getAutoRebootManager().rebootServer(SynergyPeriod.MINUTE);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 0 && args.length < 2){
            if (args[0].equalsIgnoreCase("confirm")){
                Bukkit.getServer().shutdown();
            }
        }else{
            sender.sendMessage(Synergy.SynergyColor.ERROR+"Please use /stop confirm if you are sure!");
        }
    }
}

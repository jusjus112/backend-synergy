package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.lang.reflect.Method;

public class CommandSynergyReload extends SynergyCommand {

    public CommandSynergyReload(Core plugin) {
        super(plugin, "Synergy's official reload command", true,"synergyreload","synreload","synr","synrl");

        setPlayerUsage("<module>");
        setConsoleUsage("<module>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length > 0 && args.length < 2){
            for(Module module : Core.getPlugin().getModules()){
                if (module.getShortname().toLowerCase().equalsIgnoreCase(args[0].toLowerCase())){
                    if (module.isReloadable()) {
                        synergyUser.message("Reloading "+module.getName()+"....");
                        module.reload("accept");
                        synergyUser.info("Reloaded the module for you");
                        return;
                    }else{
                        synergyUser.warning("This module doesn't let me reload it!");
                        return;
                    }
                }
            }
            synergyUser.warning("I cannot find the module "+args[0]+"....");
        }else{
            sendUsageMessage(synergyUser.getPlayer());
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 0 && args.length < 2){
            for(Module module : Core.getPlugin().getModules()){
                if (module.getShortname().toLowerCase().equalsIgnoreCase(args[0].toLowerCase())) {
                    if (module.isReloadable()) {
                        module.reload("accept");
                        Synergy.info("Reloaded "+module.getName()+" for you!");
                        return;
                    }else{
                        Synergy.error("This module cannot be reloaded!");
                        return;
                    }
                }
            }
            Synergy.error("Module "+args[0]+" not found!");
        }else{
            sendUsageMessage(sender);
        }
    }
}

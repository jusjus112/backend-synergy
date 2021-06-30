package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.includes.SynergyResponse;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandSynergyReload extends SynergyCommand {

    public CommandSynergyReload(Core plugin) {
        super(plugin, "command.synergyreload", "Synergy's official onReload command", true,"synergyreload","synreload","synr","synrl");

        setPlayerUsage("<module>");
        setConsoleUsage("<module>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length == 1){
            for(Module module : Core.getPlugin().getModules()){
                if (module.getShortname().toLowerCase().equalsIgnoreCase(args[0].toLowerCase())){
                    if (module.isReloadable()) {
                        synergyUser.message("Reloading "+module.getName()+"....");
                        module.onReload(SynergyResponse.OK);
                        synergyUser.info("Reloaded the module for you");
                        return;
                    }else{
                        synergyUser.warning("This module doesn't let me onReload it!");
                        return;
                    }
                }
            }
            synergyUser.warning("I cannot find the module "+args[0]+"....");
        }else{
            synergyUser.sendModifactionMessage(
                    MessageModification.RAW,
                    C.getLineWithName("Reloadable Modules")
            );
            for(Module module : Core.getPlugin().getModules()){
                if (module.isReloadable()) {
                    synergyUser.sendModifactionMessage(
                            MessageModification.RAW,
                            "§e/"+command + " " + module.getShortname()+" §7- "+module.getName()
                        );
                }
            }
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 0 && args.length < 2){
            for(Module module : Core.getPlugin().getModules()){
                if (module.getShortname().toLowerCase().equalsIgnoreCase(args[0].toLowerCase())) {
                    if (module.isReloadable()) {
                        module.onReload("accept");
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

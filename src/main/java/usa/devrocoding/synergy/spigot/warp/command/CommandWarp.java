package usa.devrocoding.synergy.spigot.warp.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.warp.gui.WarpGUI;
import usa.devrocoding.synergy.spigot.warp.object.Warp;

public class CommandWarp extends SynergyCommand {

    public CommandWarp(Core plugin) {
        super(plugin, "command.warp", "Synergy's Warp Command", false,"warp");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length > 0 && args.length < 3){
            if (args[0].equalsIgnoreCase("create")){
                if (synergyUser.hasPermission("command.warp.create")){
                    try{
                        new Warp(player.getLocation(), args[1]);
                        synergyUser.info("I've added the warp '"+args[1]+"' to the server");
                    }catch (Exception e){
                        synergyUser.error(e.getMessage());
                    }
                }
            }
        }else {
            if (synergyUser.hasPermission("command.warp.gui")) {
                new WarpGUI(getPlugin(), synergyUser).open(player);
            }
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

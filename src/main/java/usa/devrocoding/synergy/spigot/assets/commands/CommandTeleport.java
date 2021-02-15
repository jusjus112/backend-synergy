package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandTeleport extends SynergyCommand {

    public CommandTeleport(Core plugin){
        super(plugin, "command.teleport", "Synergy Teleport Command", true, "tp", "teleport");

        setPlayerUsage("<player>","[target]");
        setConsoleUsage("<player>","<target>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length > 0 && args.length < 3){
            if (args.length == 1){
                SynergyUser target = getPlugin().getUserManager().getUser(args[0], false);
                if (target == null){
                    synergyUser.error("I cannot seem find the user "+ C.CHAT_HIGHLIGHT.getColor()+args[0]);
                    return;
                }
                synergyUser.teleport(target);
                synergyUser.info("I teleported you to "+C.CHAT_HIGHLIGHT.getColor()+target.getName());
            }else{
                SynergyUser target = getPlugin().getUserManager().getUser(args[0], false);
                SynergyUser target2 = getPlugin().getUserManager().getUser(args[1], false);
                if (target == null){
                    synergyUser.error("I cannot seem find the user "+ C.CHAT_HIGHLIGHT.getColor()+args[0]);
                    return;
                }else
                if (target2 == null){
                    synergyUser.error("I cannot seem find the user "+ C.CHAT_HIGHLIGHT.getColor()+args[1]);
                    return;
                }
                target.teleport(target2);
                target.info(synergyUser.getDisplayName()+C.INFO.getColor()+
                        " teleported you to "+C.CHAT_HIGHLIGHT.getColor()+target2.getName());
            }
        }else{
            sendUsageMessage(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

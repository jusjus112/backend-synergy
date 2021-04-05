package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandConsoleMessage extends SynergyCommand {

    public CommandConsoleMessage(Core plugin) {
        super(plugin, "command.consolemessage", "consolemessage", true,"consolemessage");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {

    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 1) {
            SynergyUser targetUser = getPlugin().getUserManager().getUser(args[0], false);

            if (targetUser == null){
                sender.sendMessage(args[0] + " is not online!");
                return;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                if (i > 1)
                    builder.append(" ");
                builder.append(args[i]);
            }

            targetUser.info(builder.toString());
        }
    }
}

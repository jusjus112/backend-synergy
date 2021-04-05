package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandMessageAll extends SynergyCommand {

    public CommandMessageAll(Core plugin) {
        super(plugin, "command.messageall", "Toggles fly", true,"messageall");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {

    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 1) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if (i > 0)
                    builder.append(" ");
                builder.append(args[i]);
            }

            getPlugin().getUserManager().getOnlineUsers().forEach(synergyUser -> {
                synergyUser.info(builder.toString());
            });
        }
    }
}

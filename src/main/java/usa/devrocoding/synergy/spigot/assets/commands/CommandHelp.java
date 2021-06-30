package usa.devrocoding.synergy.spigot.assets.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.includes.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Comparator;
import java.util.List;

public class CommandHelp extends SynergyCommand {

    public CommandHelp(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Help Command", true,"help", "?");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String commandString, String[] args) {
        List<SynergyCommand> commands = getPlugin().getCommandManager().getCommands();

        Comparator<SynergyCommand> comparator = (a, b) -> a.getRank().ordinal() > b.getRank().ordinal() ? -1 : (a.getRank().ordinal() < b.getRank().ordinal() ? 1 : a.getLabel().compareTo(b.getLabel()));

        commands.sort(comparator);

        Rank rank = synergyUser.getRank();
        synergyUser.sendModifactionMessage(
            MessageModification.RAW,
            "-= " + rank.getColor() + rank.name() + " =-"
        );

        for (SynergyCommand command : commands) {
            ChatColor color = command.getRank().getColor();

            if (command.hasPermission()){
                if (!synergyUser.hasPermission(command.getPermission(), false)){
                    continue;
                }
            }else if (synergyUser.getRank().getId() < command.getRank().getId()) {
                continue;
            }
            synergyUser.sendModifactionMessage(MessageModification.RAW, " /" + command.getLabel() + " - " + color + command.getDescription());
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String commandString, String[] args) {
        List<SynergyCommand> commands = getPlugin().getCommandManager().getCommands();
        for (SynergyCommand command : commands) {
            sender.sendMessage(" /" + command.getLabel() + " - " + command.getDescription());
        }
    }
}

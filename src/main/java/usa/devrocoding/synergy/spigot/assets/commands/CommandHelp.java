package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommandHelp extends SynergyCommand {

    public CommandHelp(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Help Command", true,"help", "?");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String commandString, String[] args) {
        List<SynergyCommand> commands = getPlugin().getCommandManager().getCommands();

        Comparator<SynergyCommand> comparator = (a, b) -> a.getRank().ordinal() > b.getRank().ordinal() ? -1 : (a.getRank().ordinal() < b.getRank().ordinal() ? 1 : a.getAliases()[0].compareTo(b.getAliases()[0]));

        Collections.sort(commands, comparator);

        Rank rank = null;

        for (SynergyCommand command : commands) {
            ChatColor color = command.getRank().getColor();

            if ((rank == null || !rank.equals(command.getRank())) && synergyUser.getRank().getId() >= command.getRank().getId()) {
                rank = command.getRank();
                synergyUser.sendModifactionMessage(MessageModification.RAW,"-= " + rank.getColor() + rank.name() + " =-");
            }

            if (synergyUser.getRank().getId() < command.getRank().getId()) {
                continue;
            }
            synergyUser.sendModifactionMessage(MessageModification.RAW, " /" + command.getAliases()[0] + " - " + color + command.getDescription());
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String commandString, String[] args) {
        List<SynergyCommand> commands = getPlugin().getCommandManager().getCommands();
        for (SynergyCommand command : commands) {
            sender.sendMessage(" /" + command.getAliases()[0] + " - " + command.getDescription());
        }
    }
}

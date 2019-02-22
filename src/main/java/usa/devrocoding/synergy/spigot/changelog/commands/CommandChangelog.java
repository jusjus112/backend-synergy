package usa.devrocoding.synergy.spigot.changelog.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.changelog.gui.ChangelogMainGUI;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandChangelog extends SynergyCommand {

    public CommandChangelog(Core plugin){
        super(plugin, Rank.NONE, "Command to see our changelogs.", false, "changelog");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        new ChangelogMainGUI(getPlugin()).open(player);
        player.sendMessage("Opening the changelog GUI!");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

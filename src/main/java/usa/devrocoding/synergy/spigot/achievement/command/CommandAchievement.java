package usa.devrocoding.synergy.spigot.achievement.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.includes.Rank;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.gui.AchievementGUI;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandAchievement extends SynergyCommand {

    public CommandAchievement(Core plugin) {
        super(plugin, Rank.NONE, "List of available achievements",
            false,"achievements", "am");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        new AchievementGUI(synergyUser, getPlugin()).open(player);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

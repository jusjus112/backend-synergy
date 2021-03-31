package usa.devrocoding.synergy.spigot.objectives.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.gui.AchievementGUI;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.objectives.gui.ObjectivesGUI;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandObjectives extends SynergyCommand {

    public CommandObjectives(Core plugin) {
        super(plugin, Rank.NONE, "List of available objectives",
            false,"objectives", "obj", "objective");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        new ObjectivesGUI(synergyUser, getPlugin()).open(player);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

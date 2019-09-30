package usa.devrocoding.synergy.spigot.punish.command;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.punish.gui.PunishGUI;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandPunish extends SynergyCommand {

    public CommandPunish(Core plugin){
        super(plugin, "", true, "punish", "ban");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        new PunishGUI(getPlugin(), synergyUser).open(player);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

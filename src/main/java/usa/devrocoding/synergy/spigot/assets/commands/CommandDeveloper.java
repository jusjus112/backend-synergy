package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.MemoryUtils;

import static org.bukkit.Bukkit.getServer;

public class CommandDeveloper extends SynergyCommand {

    public CommandDeveloper(Core plugin) {
        super(plugin, Rank.ADMIN, "Synergy's Developer Command", true,"synergydev","syndev","version","ver");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.message(
                C.getShortLineWithName("Technical Information"),
                "§eServer Version: §c" + getServer().getBukkitVersion(),
                "§eServer App: §6"+getServer().getVersion(),
                "§eSynergy Version: §6"+getPlugin().getDescription().getVersion(),
                "§eServer Port: §6"+getServer().getPort(),
                "§eCurrent Total Memory: §b"+MemoryUtils.getTotalMemory()+"M",
                C.getLine()
        );

        synergyUser.addPermissionNode("essentials.fly");
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        sender.sendMessage(LinuxColorCodes.ANSI_YELLOW+"-------- "+LinuxColorCodes.ANSI_CYAN+"Technical Information "+LinuxColorCodes.ANSI_YELLOW+"--------");
        sender.sendMessage("Server Version: " + getServer().getBukkitVersion());
        sender.sendMessage("Server App: "+getServer().getVersion());
        sender.sendMessage("Synergy Version: "+getPlugin().getDescription().getVersion());
        sender.sendMessage("Server Port: "+getServer().getPort());
        sender.sendMessage("Current Total Memory: "+MemoryUtils.getTotalMemory()+"M");
        sender.sendMessage(LinuxColorCodes.ANSI_YELLOW+"-------------------------------------");
    }
}

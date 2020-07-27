package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandPlugins extends SynergyCommand {

    public CommandPlugins(Core plugin) {
        super(plugin, Rank.NONE, "Synergy's Plugin Command", true,"plugins", "pl");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        int count = 0;
        if (Bukkit.getServer().getPluginManager().getPlugins().length > 65){
            StringBuilder stringBuilder = new StringBuilder();
            for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                if (count > 0){
                    stringBuilder.append(ChatColor.RESET+", ");
                }
                stringBuilder.append(
                        (plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) +
                                plugin.getName()
                );
                count++;
            }
            synergyUser.message(
                    C.getLineWithNameNoAttr(count+" plugins"),
                    stringBuilder.toString(),
                    C.getLine()
            );
        }else {
            synergyUser.message(
                    C.getLine()
            );
            for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
                synergyUser.message(
                        (plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) +
                                plugin.getName() + ChatColor.YELLOW + " - " + (plugin.getDescription().getDescription() == null ? "No description..." : plugin.getDescription().getDescription())
                );
                count++;
            }
            synergyUser.message(
                    C.getLineWithNameNoAttr(count+" plugins")
            );
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (count > 0){
                stringBuilder.append(LinuxColorCodes.ANSI_RESET+", ");
            }
            stringBuilder.append(
                    (plugin.isEnabled() ? LinuxColorCodes.ANSI_GREEN : LinuxColorCodes.ANSI_GREEN) +
                            plugin.getName()
            );
            count++;
        }
        sender.sendMessage(
                LinuxColorCodes.ANSI_YELLOW+"("+count+" plugins) "+stringBuilder.toString()+LinuxColorCodes.ANSI_RESET
        );
    }
}

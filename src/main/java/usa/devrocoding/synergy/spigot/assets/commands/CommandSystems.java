package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;
import java.util.List;

public class CommandSystems extends SynergyCommand {

    public CommandSystems(Core plugin) {
        super(plugin, Rank.NONE, "List of all running systems",
            true,"systems");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        synergyUser.sendModifactionMessage(
                MessageModification.RAW,
                Synergy.SynergyColor.getLine(),
                "§cAll of our custom systems that are running!"
        );
        List<String> systems = new ArrayList<>();
        for(Module module : getPlugin().getModules()){
            systems.add("§e♦ " + module.getShortname() + " System "+(module.isDisabled() ? "§c[DISABLED]" : "§a[ENABLED]"));
        }
        synergyUser.sendModifactionMessage(
                MessageModification.RAW,
                systems
        );
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        for(Module module : getPlugin().getModules()){
            sender.sendMessage("§e♦ " + module.getShortname() + " System "+(module.isDisabled() ? "§c[DISABLED]" : "§a[ENABLED]"));
        }
    }
}

package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.warp.object.Warp;

public class CommandSpawn extends SynergyCommand {

    public CommandSpawn(Core plugin) {
        super(plugin, "Teleport to the server spawn", false,"spawn");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        Warp warp = Core.getPlugin().getWarpManager().getWarp("spawn");
        warp.teleportTo(synergyUser);
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

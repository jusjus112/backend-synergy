package usa.devrocoding.synergy.spigot.assets.commands;

import lombok.RequiredArgsConstructor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandTime extends SynergyCommand {

    public CommandTime(Core plugin) {
        super(plugin, "command.time", "Synergy's Time Command", false,"time");

        setPlayerUsage("morning", "noon", "evening", "night");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length > 0){
            try{
                TimeType type = TimeType.valueOf(args[0].toUpperCase());
                player.getWorld().setTime(type.time);
                synergyUser.info("Set the world time to "+type.name().toLowerCase());
            }catch (Exception e){
                synergyUser.error("Cannot find time type for '"+args[0]+"'");
            }
        }else{
            sendUsageMessage(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }

    @RequiredArgsConstructor
    private enum TimeType{
        MORNING(1000),
        NOON(6000),
        EVENING(13000),
        NIGHT(18000);

        private final long time;

    }

}

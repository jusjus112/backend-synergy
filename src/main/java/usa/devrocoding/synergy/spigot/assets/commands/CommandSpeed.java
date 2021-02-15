package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandSpeed extends SynergyCommand {

    public CommandSpeed(Core plugin) {
        super(plugin, "command.speed", "Synergy's Speed Command", false,"speed");

        setPlayerUsage("<1-8>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length > 0){
            int speed = 0;
            String type;
            try{
                speed = Math.abs(Integer.parseInt(args[0]));
                if (player.isFlying()) {
                    player.setFlySpeed((float)speed / 10.0f);
                    type = "fly";
                }else {
                    player.setWalkSpeed((float)speed / 11.0f + 0.1F);
                    type = "walk";
                }
                synergyUser.info("Changed your "+type+"speed to '"+speed+"'");
            }catch(IllegalArgumentException e) {
                synergyUser.error("Invalid speed '"+args[0]+"'");
            }
        }else{
            sendUsageMessage(player);
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

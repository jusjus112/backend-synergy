package usa.devrocoding.synergy.spigot.assets.commands;

import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.object.SamMessage;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandGamemode extends SynergyCommand {

    public CommandGamemode(Core plugin) {
        super(plugin, Rank.ADMIN, "Synergy's Plugin Command",true,
                "gamemode", "gm", "gmc", "gms", "gma", "gmsp");

        setPlayerUsage("<gamemode>", "[player]");
        setConsoleUsage("<gamemode>","<player>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (!synergyUser.hasPermission("gamemode")){
            return;
        }
        if (command.equalsIgnoreCase("gm")||command.equalsIgnoreCase("gamemode")){
            if (args.length>0&&args.length<2){
                GameMode gm = getGamemode(args[0].toLowerCase());
                if (gm == null) {
                    sendUsageMessage(player);
                    return;
                }

                if (player.getGameMode() == gm){
                    synergyUser.sam(SamMessage.CANNOT_DO_THAT);
                    return;
                }

                player.setGameMode(gm);
                synergyUser.info(
                        "Oke player. I've put you in "+ C.CHAT_HIGHLIGHT.getColor()+gm.name().toLowerCase());
            }else{
                sendUsageMessage(player);
            }
        }else {
            if (command.length() > 1) {
                GameMode gm = getGamemode(command);
                if (gm == null) {
                    sendUsageMessage(player);
                    return;
                }

                if (player.getGameMode() == gm){
                    synergyUser.sam(SamMessage.CANNOT_DO_THAT);
                    return;
                }

                player.setGameMode(gm);
                synergyUser.info(
                        "Oke player. I've put you in "+ C.CHAT_HIGHLIGHT.getColor()+gm.name().toLowerCase());
            }else{
                sendUsageMessage(player);
            }
        }
    }

    private GameMode getGamemode(String arg){
        if (arg.equalsIgnoreCase("gmc")||
                arg.equalsIgnoreCase("1")||
                arg.equalsIgnoreCase("creative")||
                arg.equalsIgnoreCase("egmc")){
            return GameMode.CREATIVE;
        }else if (arg.equalsIgnoreCase("gms")||
                arg.equalsIgnoreCase("0")||
                arg.equalsIgnoreCase("egms")||
                arg.equalsIgnoreCase("survival")){
            return GameMode.SURVIVAL;
        }else if (arg.equalsIgnoreCase("gma")||
                arg.equalsIgnoreCase("egma")||
                arg.equalsIgnoreCase("adventure")||
                arg.equalsIgnoreCase("2")){
            return GameMode.ADVENTURE;
        }else if (arg.equalsIgnoreCase("gmsp")||
                arg.equalsIgnoreCase("egmsp")||
                arg.equalsIgnoreCase("spectator")||
                arg.equalsIgnoreCase("3")){
            return GameMode.CREATIVE;
        }
        return null;
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {
        if (args.length > 0 && args.length < 3){

        }else{
            sendUsageMessage(sender);
        }
    }
}

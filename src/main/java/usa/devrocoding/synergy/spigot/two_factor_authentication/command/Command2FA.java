package usa.devrocoding.synergy.spigot.two_factor_authentication.command;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class Command2FA extends SynergyCommand {

    public Command2FA(Core plugin){
        super(plugin, "synergy.2fa", "2fa Command",false, "2fa");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        if (args.length == 1){
            try{
                GoogleAuthManager googleAuthManager = Core.getPlugin().getGoogleAuthManager();
                int code = Integer.parseInt(args[0]);
                if (!synergyUser.hasFilledIn2FA()) {
                    if (googleAuthManager.validate(synergyUser.getAuthKey(), code)) {
//                        googleAuthManager.disable2faMode(synergyUser);

                        googleAuthManager.getFilledInPlayers().add(synergyUser.getUuid());
                        googleAuthManager.restore(synergyUser);
                        player.spigot().sendMessage(new TextComponent(Synergy.SynergyColor.INFO.getColor() + "Good luck on the server ;)"));
                    } else {
                        player.spigot().sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor() + "Your 2FA code is incorrect!"));
                    }
                }
            }catch (NumberFormatException e){
                player.spigot().sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor()+args[0]+" is not a number!"));
            }
        }else{
            player.spigot().sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor()+"Usage: /2fa <code>"));
        }
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }

}

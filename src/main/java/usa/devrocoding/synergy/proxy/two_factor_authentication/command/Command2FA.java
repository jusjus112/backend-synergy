package usa.devrocoding.synergy.proxy.two_factor_authentication.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;

public class Command2FA extends Command {

    public Command2FA(){
        super("2fa", "synergy.2fa");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            if (args.length > 0 && args.length < 2){
                try{
                    int code = Integer.valueOf(args[0]);
                    if (Core.getCore().getGoogleAuthManager().validate(Core.getCore().getGoogleAuthManager().getCachedKey((ProxiedPlayer) sender), code)){
                        Core.getCore().getGoogleAuthManager().disable2faMode((ProxiedPlayer) sender);
                        sender.sendMessage(new TextComponent(Synergy.SynergyColor.INFO.getColor()+"Good luck on the server ;)"));
                    }else{
                        sender.sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor()+"Your 2FA code is incorrect!"));
                    }
                }catch (NumberFormatException e){
                    sender.sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor()+args[0]+" is not a number!"));
                }
            }else{
                sender.sendMessage(new TextComponent(Synergy.SynergyColor.ERROR.getColor()+"Usage: /2fa <code>"));
            }
        }else{
            sender.sendMessage(new TextComponent(ChatColor.RED+"You're not a player!"));
        }
    }

}

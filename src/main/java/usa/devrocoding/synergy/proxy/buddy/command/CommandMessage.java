package usa.devrocoding.synergy.proxy.buddy.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

public class CommandMessage extends Command {

    public CommandMessage() {
        super("buddy", "synergy.buddy.command", "message", "msg", "m");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxyUser proxyUser = Core.getCore().getUserManager().getUser(((ProxiedPlayer) sender).getUniqueId());
            ProxyUser targetUser = Core.getCore().getUserManager().getUser(args[0]);

            if (targetUser == null){
                proxyUser.sendMessage("§c" + args[0] + " is not online!");
                return;
            }

            if (targetUser.hasFriend(proxyUser)){
                proxyUser.sendMessage("§cYou are not friends with '" + targetUser.getDisplayName() + "'");
                proxyUser.sendMessage("§cAdd them as a buddy / friend to send messages.");
                proxyUser.sendMessage("§cUse §e/f add " + targetUser.getProxiedPlayer().getName());
                return;
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                builder.append(args[i]);
            }

            targetUser.getProxiedPlayer().sendMessage(
                "§d§lPRIVATE §c"+proxyUser.getProxiedPlayer().getName() + ": §f" + builder.toString()
            );
        }
    }
}
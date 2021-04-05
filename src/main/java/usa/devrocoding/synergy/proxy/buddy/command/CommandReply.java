package usa.devrocoding.synergy.proxy.buddy.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

public class CommandReply extends Command {

    public CommandReply() {
        super("reply", "synergy.buddy.reply", "r");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxyUser proxyUser = Core.getCore().getUserManager().getUser(((ProxiedPlayer) sender).getUniqueId());
            if (proxyUser.getLastMessageReceived() != null){
                if (proxyUser.getLastMessageReceived().getProxiedPlayer() == null){
                    proxyUser.sendMessage(ChatColor.RED +
                        proxyUser.getLastMessageReceived().getName() + " is not online anymore!");
                    return;
                }
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    if (i > 0) builder.append(" ");
                    builder.append(args[i]);
                }
                Core.getCore().getBuddyManager().privateMessage(proxyUser,
                    proxyUser.getLastMessageReceived(), builder.toString());
            }else{
                proxyUser.sendMessage(ChatColor.RED + "You have no message to reply to!");
            }
        }
    }
}
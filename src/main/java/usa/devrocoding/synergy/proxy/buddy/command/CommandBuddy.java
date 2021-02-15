package usa.devrocoding.synergy.proxy.buddy.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.buddy.object.BuddyRequest;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;
import usa.devrocoding.synergy.spigot.assets.C;

public class CommandBuddy extends Command {

  public CommandBuddy(){
    super("buddy", "synergy.buddy.command", "buddy", "friend", "f", "b");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender instanceof ProxiedPlayer){
      ProxyUser proxyUser = Core.getCore().getUserManager().getUser(((ProxiedPlayer) sender).getUniqueId());
      if (args.length > 0){
        if (args.length == 2){
          if (args[0].equalsIgnoreCase("remove")){
            // TODO: Remove Friend
            proxyUser.sendMessage("REMOVING...");
            return;
          }
          ProxyUser targetUser = Core.getCore().getUserManager().getUser(args[1]);

          if (targetUser == null || targetUser.getProxiedPlayer() == null){
            proxyUser.sendMessage(ChatColor.RED + args[1] + " is not online!");
            return;
          }

          if (args[0].equalsIgnoreCase("add")){
            BuddyRequest buddyRequest = new BuddyRequest(proxyUser, targetUser);

            if (buddyRequest.send()){
              // TODO: Send success
            }
          }else {
            BuddyRequest buddyRequest = Core.getCore().getBuddyManager().getRequestByUser(proxyUser, targetUser);
            if (buddyRequest == null){
              proxyUser.sendMessage(ChatColor.RED + "You have no request from " + args[1]);
              return;
            }

            if (args[0].equalsIgnoreCase("accept")){
              buddyRequest.onAccept();
            }else if (args[0].equalsIgnoreCase("deny")){
              buddyRequest.onDeny();
            }
          }
        }else{
          if (args[0].equalsIgnoreCase("list")) {
            // TODO: List of friends
            if (proxyUser.getFriends().size() > 0){
              for (int i = 0; i < proxyUser.getFriends().size(); i++) {
                proxyUser.getProxiedPlayer().sendMessage(ChatColor.RED + (i + 1 + "") + ". " + proxyUser.getFriends().get(i));
              }
            }else {
              proxyUser.sendMessage(ChatColor.RED + "You don't have any friends ;(");
            }
          }
        }
      }else{
        sender.sendMessage(" ");
        sender.sendMessage("/f add <player>");
        sender.sendMessage("/f accept <player>");
        sender.sendMessage("/f deny <player>");
        sender.sendMessage("/f remove <player>");
        sender.sendMessage("/f list");
        sender.sendMessage(" ");
      }
    }
  }

}

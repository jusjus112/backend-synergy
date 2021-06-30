package usa.devrocoding.synergy.proxy.buddy.command;

import java.util.List;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import usa.devrocoding.synergy.includes.Pair;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.buddy.object.BuddyRequest;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

public class CommandBuddy extends Command {

  public CommandBuddy(){
    super("buddy", "synergy.buddy.command", "buddy", "friend", "f", "b","friends");
  }

  @Override
  public void execute(CommandSender sender, String[] args) {
    if (sender instanceof ProxiedPlayer){
      ProxyUser proxyUser = Core.getCore().getUserManager().getUser(((ProxiedPlayer) sender).getUniqueId());
      if (args.length > 0){
        if (args.length == 2){
          if (args[0].equalsIgnoreCase("remove")){
            String name = args[1];
            try{
              if (Core.getCore().getBuddyManager().removeFriend(proxyUser, name)){
                proxyUser.sendMessage(ChatColor.GREEN + "You are no longer buddies with " + args[1]);
              }else{
                proxyUser.sendMessage(ChatColor.RED + "You are not friend with " + args[1]);
              }
            }catch (Exception e){
              proxyUser.sendMessage(ChatColor.RED + e.getMessage());
            }
            return;
          }
          ProxyUser targetUser = Core.getCore().getUserManager().getUser(args[1]);

          if (targetUser == null || targetUser.getProxiedPlayer() == null){
            proxyUser.sendMessage(ChatColor.RED + args[1] + " is not online!");
            return;
          }

          if (args[0].equalsIgnoreCase("add")){
            BuddyRequest buddyRequest = Core.getCore().getBuddyManager().getRequestByUser(proxyUser, targetUser);
            if (buddyRequest == null){
              if (!proxyUser.canHaveMoreFriends()){
                proxyUser.sendMessage(ChatColor.RED +
                    "You cannot add more than "+proxyUser.getFriends().size()+" friends.");
                proxyUser.sendMessage(ChatColor.RED + "Remove someone from your friend list.");
                proxyUser.sendMessage(ChatColor.RED + "Or buy a rank on shop.mirageprisons.net to add more friends.");
                return;
              }
              if (!targetUser.canHaveMoreFriends()){
                proxyUser.sendMessage(ChatColor.RED + "This user cannot have more than " + targetUser.getFriends().size() + " friends");
                return;
              }
              buddyRequest = new BuddyRequest(proxyUser, targetUser);

              if (buddyRequest.send()){
                // TODO: Send success
              }else{
                proxyUser.sendMessage(ChatColor.RED + "You cannot add this player as a buddy!");
              }
            }else{
              buddyRequest.onAccept();
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
            if (proxyUser.getFriends().size() > 0){
              final int[] i = {0};
              List<Pair<UUID, String>> friends = proxyUser.getFriends();

              proxyUser.getProxiedPlayer().sendMessage("§eYou have a total of §6(" + friends.size()+") §efriend(s)!");
              friends.forEach(uuidStringPair -> {
                proxyUser.getProxiedPlayer().sendMessage(ChatColor.GREEN + (i[0] + 1 + "") + ". " + uuidStringPair.getRight());
                i[0]++;
              });
            }else {
              proxyUser.sendMessage(ChatColor.RED + "You don't have any buddies ;(");
            }
          }
        }
      }else{
        sender.sendMessage(" ");
        sender.sendMessage("§c/f add §e<player>");
        sender.sendMessage("§c/f accept §e<player>");
        sender.sendMessage("§c/f deny §e<player>");
        sender.sendMessage("§c/f remove §e<player>");
        sender.sendMessage("§c/f list");
        sender.sendMessage(" ");
      }
    }
  }

}

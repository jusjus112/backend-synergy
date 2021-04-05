package usa.devrocoding.synergy.proxy.buddy.object;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.buddy.BuddyManager;
import usa.devrocoding.synergy.proxy.user.object.ProxyUser;

public class BuddyRequest {

  private final BuddyManager buddyManager;
  @Getter
  private final ProxyUser senderUser;
  @Getter
  private final ProxyUser targetUser;

  public BuddyRequest(ProxyUser senderUser, ProxyUser targetUser){
    this.buddyManager = Core.getCore().getBuddyManager();
    this.senderUser = senderUser;
    this.targetUser = targetUser;
  }

  public void onAccept(){
    this.targetUser.sendMessage("You are now friends with " + this.senderUser.getDisplayName());
    this.senderUser.sendMessage(this.senderUser.getDisplayName() + " has accepted your request!");
    removeRequest();

    this.buddyManager.addFriend(this.senderUser, this.targetUser);
    this.buddyManager.addFriend(this.targetUser, this.senderUser);

  }
  public void onDeny(){
    removeRequest();
  }

  private void removeRequest(){
    Core.getCore().getBuddyManager().getBuddyRequests().remove(this.targetUser);
    Core.getCore().getBuddyManager().getBuddyRequests().remove(this.senderUser);
    this.targetUser.sendMessage(ChatColor.RED + "You declined the request from " +
        this.senderUser.getProxiedPlayer().getName());
  }

  public boolean send(){
    // Check if there is already an invite / request
    if (this.buddyManager.getBuddyRequests().containsKey(this.senderUser)){
      Synergy.debug("CONTAINS REQUEST");
      return false;
    }
    // Cannot add a already existing friend as a friend
    if (this.senderUser.hasFriend(this.targetUser)){
      Synergy.debug("CONTAINS FRIEND");
      return false;
    }
    // Cannot add a higher staff as friend.
    if (this.targetUser.getRank().isHigherThanAndEqualTo(Rank.ADMIN)){
      Synergy.debug("CONTAINS RANK");
      return false;
    }
    this.targetUser.getProxiedPlayer().sendMessage(getMessage());
    this.buddyManager.addBuddyRequest(this);
    this.senderUser.sendMessage("Request has been sent!");
    return true;
  }

  private TextComponent getMessage(){
    TextComponent textComponent = new TextComponent(" \n");
    textComponent.addExtra(ChatColor.YELLOW + "Friend request from " + this.senderUser.getDisplayName());

    TextComponent acceptText = new TextComponent("ACCEPT");
    acceptText.setColor(ChatColor.GREEN);
    acceptText.setBold(true);
    acceptText.setClickEvent(new ClickEvent(Action.RUN_COMMAND,
        "/buddy accept " + this.senderUser.getProxiedPlayer().getName()));

    TextComponent declineText = new TextComponent("DECLINE");
    declineText.setColor(ChatColor.RED);
    declineText.setBold(true);
    declineText.setClickEvent(new ClickEvent(Action.RUN_COMMAND,
        "/buddy deny " + this.senderUser.getProxiedPlayer().getName()));

    textComponent.addExtra("\n");
    textComponent.addExtra(acceptText);
    textComponent.addExtra("      ");
    textComponent.addExtra(declineText);
    textComponent.addExtra("\n");

    return textComponent;
  }

}

package usa.devrocoding.synergy.proxy.buddy.object;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
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

    this.buddyManager.addFriend(this.senderUser, this.targetUser);
  }
  public void onDeny(){

  }

  public boolean send(){
    if (this.buddyManager.getBuddyRequests().containsKey(this.senderUser)){
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
        "/buddy accept " + this.targetUser.getProxiedPlayer().getName()));

    TextComponent declineText = new TextComponent("DECLINE");
    declineText.setColor(ChatColor.RED);
    declineText.setBold(true);
    declineText.setClickEvent(new ClickEvent(Action.RUN_COMMAND,
        "/buddy deny " + this.targetUser.getProxiedPlayer().getName()));

    textComponent.addExtra("\n");
    textComponent.addExtra(acceptText);
    textComponent.addExtra("      ");
    textComponent.addExtra(declineText);
    textComponent.addExtra("\n");

    return textComponent;
  }

}

package usa.devrocoding.synergy.discord.protection.listener;

import java.awt.Color;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.object.DiscordRank;

public class GuildMessageReceiveListener extends ListenerAdapter {

  @Override
  public void onMessageReceived(MessageReceivedEvent e){
    // TODO: Delete and detect incoming bot/player spam

    { // Disable Tagging of higher staff
      if (e.getMessage().getMentionedMembers().isEmpty() ||
          Objects.requireNonNull(e.getMember()).getPermissions()
              .contains(DiscordRank.STAFF.getHighestPermission())) {
        return;
      }
      for (Member mentionedMember : e.getMessage().getMentionedMembers()) {
        if (mentionedMember.getPermissions().contains(DiscordRank.ADMIN.getHighestPermission())) {
          if (e.getMember().getPermissions().contains(DiscordRank.STAFF.getHighestPermission())){
            return;
          }
          e.getMessage().delete().queue();
          // TODO: Send message from bot saying you can't tag staff members
          e.getTextChannel().sendMessage(
              "**Warning: Please do not tag " + mentionedMember.getEffectiveName() +" **," + e.getMember().getAsMention()
          ).queue();
          break;
        }
      }

    }

    { // Disable advertising URL's
      Pattern p = Pattern.compile(Synergy.URL_REGEX);
      Matcher m = p.matcher(e.getMessage().getContentRaw());//replace with string to compare

      if (m.find()){
        e.getMessage().delete().queue();
        e.getTextChannel().sendMessage(
                "**Warning: Advertising website's or other discord servers is not allowed**, " + e.getMember().getAsMention()
        ).queue();
      }
    }
  }

}

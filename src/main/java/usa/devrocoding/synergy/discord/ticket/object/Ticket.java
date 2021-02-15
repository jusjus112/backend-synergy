package usa.devrocoding.synergy.discord.ticket.object;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

@Getter
public class Ticket {

  private final List<Member> members;
  private final String id;
  private TextChannel textChannel;
  @Setter
  private boolean closable = false;

  public Ticket(Member firstMember){
    this.members = Lists.newArrayList();
    this.members.add(firstMember);
    this.id = firstMember.getEffectiveName().substring(0, 4) + UtilString.getAlphaNumericString(3);
    this.open();
  }

  public Ticket(String id, List<Member> members, TextChannel textChannel){
    this.id = id;
    this.members = Lists.newArrayList();
    this.members.addAll(members);
    this.textChannel = textChannel;
  }

  public boolean addMember(Member member){
    try{
      if (this.members.contains(member)){
        return false;
      }
      this.members.add(member);
      this.textChannel.createPermissionOverride(member).grant(EnumSet.of(
          Permission.MESSAGE_WRITE,
          Permission.VIEW_CHANNEL,
          Permission.MESSAGE_HISTORY
      )).queue();
      return true;
    }catch (Exception e){
      return false;
    }
  }

  public boolean removeMember(Member member){
    try{
      if (!this.members.contains(member)){
        return false;
      }
      this.members.remove(member);
      Objects.requireNonNull(this.textChannel.getPermissionOverride(member)).delete().queue();
      return true;
    }catch (Exception e) {
      return false;
    }
  }

  public void open(){
    this.createTicketChannel();
  }

  public void close(){
    // TODO: Maybe log it or store it in a cache somewhere?
    this.textChannel.delete().queue();
    Discord.getTicketManager().getTickets().remove(this);
  }

  private void createTicketChannel(){
    Objects.requireNonNull(Discord.getJda().getCategoryById(Discord.getTicketManager().getTicketCategory()))
        .createTextChannel("ticket-" + this.id)
        .addPermissionOverride(
            this.members.get(0),
            EnumSet.of(
                Permission.MESSAGE_WRITE,
                Permission.VIEW_CHANNEL,
                Permission.MESSAGE_HISTORY
            ),
            null
        )
        .queue(textChannel -> {
          this.textChannel = textChannel;

          textChannel.sendMessage(
              new MessageBuilder(
                  "Hey you!! Please follow these instructions!",
                  "In order to assist you the best, we are asking you to give the following information: \n\n"
                      + "* Your Minecraft Username\n"
                      + "* The reason for opening this ticket\n\n"
                      + "Thank you, a member of our staff will help you as soon as possible!\n"
                      + "Use `!close` anytime if you want to close the ticket!"
              )
                  .build()
          ).queue();
        });
  }

}

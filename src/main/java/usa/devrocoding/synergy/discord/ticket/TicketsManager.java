package usa.devrocoding.synergy.discord.ticket;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.ticket.command.CommandTicketAdd;
import usa.devrocoding.synergy.discord.ticket.command.CommandTicketClose;
import usa.devrocoding.synergy.discord.ticket.command.CommandTicketRemove;
import usa.devrocoding.synergy.discord.ticket.listener.ReactionAddListener;
import usa.devrocoding.synergy.discord.ticket.object.Ticket;

public class TicketsManager extends DiscordModule {

  @Getter
  private final String ticketCategory = "770374872978292737";
  @Getter
  private final List<Ticket> tickets;

  public TicketsManager(){
    super("Tickets Manager");

    this.tickets = Lists.newArrayList();

    registerEventListeners(
        new ReactionAddListener()
    );

    registerCommands(
        new CommandTicketClose(),
        new CommandTicketAdd(),
        new CommandTicketRemove()
    );
  }

  @Override
  public void init(JDA jda) {
    Objects.requireNonNull(jda.getTextChannelById(Codes.SUPPORT_CHANNEL_ID))
        .addReactionById("770952856622596116", Codes.MIRAGE_PRISONS_ICON_ID).queue();

    initialize(jda);
  }

  public void initialize(JDA jda){
    Objects.requireNonNull(jda.getCategoryById(Codes.TICKET_CATEGORY_ID))
      .getTextChannels().forEach(textChannel -> {
        String id = textChannel.getName().split("-")[1];
        Ticket ticket = new Ticket(id, textChannel.getManager().getChannel().getMembers(), textChannel);
        this.tickets.add(ticket);
      }
    );
  }

  public int getTicketCountForMember(Member member){
    return (int) this.tickets.stream().filter(ticket -> ticket.getMembers().contains(member)).count();
  }

  public Ticket getTicketFromMessageChannel(MessageChannel messageChannel){
    for (Ticket ticket : this.tickets) {
      if (ticket.getTextChannel() == messageChannel){
        return ticket;
      }
    }
    return null;
  }

  public void createTicket(Member member){
    if (getTicketCountForMember(member) > 1){
      return;
    }
    Ticket ticket = new Ticket(member);
    this.tickets.add(ticket);
  }

}

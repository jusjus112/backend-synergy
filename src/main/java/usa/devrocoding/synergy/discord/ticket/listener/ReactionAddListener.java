package usa.devrocoding.synergy.discord.ticket.listener;

import java.util.Objects;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class ReactionAddListener extends ListenerAdapter {

  @Override
  public void onMessageReactionAdd(MessageReactionAddEvent event) {
    if (Objects.requireNonNull(event.getUser()).isBot()){
      return;
    }
    if (event.getGuild().getIdLong() == Long.parseLong(Codes.MIRAGE_PRISONS_DISCORD_ID)){
      // Open Ticket
      if (event.getTextChannel().getIdLong() == Long.parseLong(Codes.SUPPORT_CHANNEL_ID)){
        event.getTextChannel().removeReactionById(event.getMessageId(),
            event.getReactionEmote().getEmote(), Objects.requireNonNull(event.getUser())).queue();
        Discord.getTicketManager().createTicket(event.getMember());
      }else {

        // Close Ticket
        Discord.getTicketManager().getTickets().forEach(ticket -> {
          if (event.getTextChannel() == ticket.getTextChannel()){
            if (event.getReaction().getReactionEmote().getIdLong() == Codes.getIDWithoutName(Codes.YES_ICON_ID)) {
              if (ticket.isClosable()) {
                ticket.close();
              }
            }else if (event.getReaction().getReactionEmote().getIdLong() == Codes.getIDWithoutName(Codes.NO_ICON_ID)) {
              // TODO: Ticket closing cancelled!
              event.getTextChannel().editMessageById(
                event.getMessageId(),
                new MessageBuilder("Closing Cancelled!", "Closing this ticket has been cancelled!").build()
              ).queue(message -> {
                message.clearReactions().queue();
              });
              ticket.setClosable(false);
            }
          }
        });
      }
    }
  }
}

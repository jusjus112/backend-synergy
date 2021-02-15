package usa.devrocoding.synergy.discord.ticket.command;

import java.awt.Color;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.ticket.object.Ticket;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class CommandTicketClose extends DiscordCommand {

  public CommandTicketClose(){
    super("Close a ticket (Ticket Only!)", DiscordRank.NONE, "!", "close");
  }

  @Override
  public void execute(Member member, MessageChannel channel,
      Message message, String rawContent, String[] args){

    if (args.length == 0){
      Ticket ticket = Discord.getTicketManager().getTicketFromMessageChannel(channel);
      if (ticket == null || ticket.isClosable()){
        return;
      }
      ticket.setClosable(true);

      channel.sendMessage(
        new MessageBuilder("Are you sure?", "Are you sure you want to delete this ticket?")
        .overwriteColor(Color.ORANGE)
        .build()
      ).queue(message1 -> {
        message1.addReaction(Codes.YES_ICON_ID).queue();
        message1.addReaction(Codes.NO_ICON_ID).queue();
      });

    }
  }
}

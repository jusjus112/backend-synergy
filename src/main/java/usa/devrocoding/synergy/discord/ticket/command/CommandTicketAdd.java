package usa.devrocoding.synergy.discord.ticket.command;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.ticket.object.Ticket;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class CommandTicketAdd extends DiscordCommand {

  public CommandTicketAdd(){
    super("Add a member to a ticket (Ticket Only!)", DiscordRank.STAFF, "!", "add");

    setUsage("<user @>");
  }

  @Override
  public void execute(Member member, MessageChannel channel,
      Message message, String rawContent, String[] args){

    if (args.length == 1){
      Ticket ticket = Discord.getTicketManager().getTicketFromMessageChannel(channel);
      if (ticket == null || ticket.isClosable()){
        return;
      }

      try {
        List<Member> mentioned = message.getMentionedMembers();
        if (mentioned.isEmpty()){
          channel.sendMessage(
              new MessageBuilder("User is not a valid tag!",
                  "This is not a valid mentionable tag for a user! Example: user#1234")
                  .overwriteColor(Color.RED)
                  .build()
          ).queue();
          return;
        }
        Member target = message.getMentionedMembers().get(0);

        if (ticket.addMember(target)) {
          channel.sendMessage(
              new MessageBuilder("Added", "Added the user " + target.getAsMention())
                  .overwriteColor(Color.GREEN)
                  .build()
          ).queue();
        }else{
          channel.sendMessage(
              new MessageBuilder("User has already been added!",
                  "User is already part of this ticket -_-")
                  .overwriteColor(Color.RED)
                  .build()
          ).queue();
        }
      }catch (Exception e){
        e.printStackTrace();
        channel.sendMessage(
            new MessageBuilder("Something went wrong", e.getMessage())
                .overwriteColor(Color.RED)
                .setFooter("Usage: !add <user#1234>")
                .build()
        ).queue();
      }
    }
  }
}

package usa.devrocoding.synergy.discord.suggest.command;

import java.awt.Color;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.suggest.SuggestManager.SuggestType;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class CommandSuggest extends DiscordCommand {

  public CommandSuggest(){
    super("Suggest a new feature.", DiscordRank.NONE, "!", "suggest");

    setUsage("<suggestion>");
  }

  @Override
  public void execute(Member member, MessageChannel channel,
      Message message, String rawContent, String[] args){

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < args.length; i++) {
      if (i>0){
        builder.append(" ");
      }
      builder.append(args[i]);
    }

    try{
      Discord.getSuggestManager().suggest(
          member.getAsMention(),
          SuggestType.DISCORD,
          builder.toString()
      );
      message.getTextChannel().sendMessage(
          new MessageBuilder(
            "Suggestion has been sent!",
            "Thank you, I've added this suggestion for you " + member.getAsMention()
          )
          .overwriteColor(Color.GREEN)
          .build()
      ).queue();
    }catch (Exception e){
      channel.sendMessage(
          new MessageBuilder("Something went wrong with your suggestion", "Error: " + e.getMessage())
              .overwriteColor(Color.RED).build()
      ).queue();
    }
  }

}

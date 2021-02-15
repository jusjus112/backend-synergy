package usa.devrocoding.synergy.discord.suggest;

import java.awt.Color;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.suggest.command.CommandSuggest;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;

public class SuggestManager extends DiscordModule {

  private final String[] channels = new String[]{
      Codes.DISCORD_SUGGESTIONS_CHANNEL_ID, Codes.STAFF_DISCORD_SUGGESTIONS_CHANNEL_ID
  };

  public SuggestManager(){
    super("Suggest Manager");

    registerCommands(
        new CommandSuggest()
    );
  }

  @Override
  public void init(JDA jda) {

  }

  public void suggest(String userName, SuggestType suggestType, String suggestion) throws Exception{
    // TODO: Add cooldown and spam protection
    Arrays.stream(this.channels).forEach(s -> {
      Objects.requireNonNull(Discord.getJda()
        .getTextChannelById(s))
        .sendMessage(
            new MessageBuilder("New Suggestion | From " + suggestType.getName(), SetType.TITLE)
                .addField("Suggested by", userName, false)
                .addField("Suggestion", suggestion, false)
                .setFooter(new Date().toString())
                .overwriteColor(Color.MAGENTA)
                .build()
        ).queue(message -> {
          message.addReaction(Codes.YES_ICON_ID).queue();
          message.addReaction(Codes.NO_ICON_ID).queue();
        }
      );
    });
  }

  public enum SuggestType{
    SERVER("The Server"),
    DISCORD("Discord");

    @Getter
    private final String name;

    SuggestType(String name){
      this.name = name;
    }
  }

}

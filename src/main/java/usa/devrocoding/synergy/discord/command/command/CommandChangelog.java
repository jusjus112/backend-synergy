package usa.devrocoding.synergy.discord.command.command;

import java.util.Objects;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.assets.Codes;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;

public class CommandChangelog extends DiscordCommand {

  public CommandChangelog(){
    super("Adds you to the notification list when we have a new changelog.",
        DiscordRank.NONE, "!", "changelog");
  }

  @Override
  public void execute(Member member, MessageChannel channel, Message message, String rawContent,
      String[] args) {

    member.getGuild().addRoleToMember(member,
        Objects.requireNonNull(member.getGuild().getRoleById(Codes.CHANGELOG_ROLE_ID)))
        .queue(aVoid -> {
          channel.sendMessage("Added you " + member.getAsMention()).queue();
        }, throwable -> {
          // TODO: Failure
        });
  }
}

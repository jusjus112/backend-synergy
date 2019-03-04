package usa.devrocoding.synergy.discord.command.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class CommandSynergy extends DiscordCommand {

    public CommandSynergy(){
        super("", DiscordRank.ADMIN, "+", "synergy");
    }

    @Override
    public void execute(Member member, MessageChannel channel, Message message) {
        channel.sendMessage(
                new MessageBuilder("It works like a charm", "Indeed. I agree").build()
        ).queue();
    }
}

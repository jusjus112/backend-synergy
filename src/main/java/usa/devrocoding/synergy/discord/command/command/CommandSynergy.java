package usa.devrocoding.synergy.discord.command.command;

import java.util.Arrays;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;

public class CommandSynergy extends DiscordCommand {

    public CommandSynergy(){
        super("", DiscordRank.ADMIN, "!", "message");
        setShowInHelp(false);
    }

    @Override
    public void execute(Member member, MessageChannel channel,
        Message message, String rawContent, String[] args){

        if (args.length > 0) {
            String[] messages = rawContent.split("`");

            String[] messagesTitle = messages[0].split(" ");
            StringBuilder title = new StringBuilder();
            for (int i = 0; i < messagesTitle.length; i++) {
                if (i > 0)
                    title.append(" ");
                title.append(messagesTitle[i]);
            }

            if (messages.length > 1) {
                String[] messagesDescription = messages[1].split(" ");
                StringBuilder description = new StringBuilder();
                for (int i = 0; i < messagesDescription.length; i++) {
                    if (i > 0)
                        description.append(" ");
                    description.append(messagesDescription[i]);
                }
                channel.sendMessage(
                    new MessageBuilder(title.toString(), description.toString()).build()
                ).queue();
                return;
            }

            channel.sendMessage(
                new MessageBuilder(title.toString(), SetType.DESCRIPTION).build()
            ).queue();
        }
    }
}

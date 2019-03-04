package usa.devrocoding.synergy.discord.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;

import java.util.Arrays;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        for(DiscordCommand command : Discord.getCommandManager().getCommands()){
            if (command.getAliases().contains(content.toLowerCase())){
                if (member.hasPermission(command.getRank().getHighestPermission())) {
                    command.execute(member, channel, message);
                }
            }
        }
    }

}

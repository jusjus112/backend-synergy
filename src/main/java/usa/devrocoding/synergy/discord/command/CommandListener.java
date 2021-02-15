package usa.devrocoding.synergy.discord.command;

import java.awt.Color;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;

import java.util.Arrays;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();
        String[] args = content.split(" ");
        String commandInput = args[0];

        // Fixes
        content = content.replaceAll(args[0], "");
        args = ArrayUtils.remove(args, 0); // Remove command from args

        for(DiscordCommand command : Discord.getCommandManager().getCommands()){
            if (command.getAliases().contains(commandInput)){
                assert member != null;
                if (member.hasPermission(command.getRank().getHighestPermission())) {
                    if (Discord.getCommandManager().checkSpam(member, commandInput)){
                        channel.sendMessage(
                            "**Please do NOT spam commands** " + member.getAsMention() + "!"
                        ).queue();
                        break;
                    }
                    command.execute(member, channel, message, content, args);
                    break;
                }else{
                    channel.sendMessage(
                        new MessageBuilder("No Permissions!",
                            "You do not have the required permissions to use this command!")
                            .overwriteColor(Color.RED)
                            .build()
                    ).queue();
                }
            }
        }
    }

}

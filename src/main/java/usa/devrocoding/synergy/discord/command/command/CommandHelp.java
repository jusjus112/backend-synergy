package usa.devrocoding.synergy.discord.command.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.discord.object.DiscordRank;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;

public class CommandHelp extends DiscordCommand {

    public CommandHelp(){
        super("Shows a list of all of the available commands!", DiscordRank.NONE, "!", "help");
    }

    @Override
    public void execute(Member member, MessageChannel channel,
        Message message, String rawContent, String[] args){

        MessageBuilder messageBuilder = new MessageBuilder(
            "List of all the commands available!",
            SetType.TITLE
        );

        Discord.getCommandManager().getCommands().forEach(discordCommand -> {
            if (discordCommand.isShowInHelp()) {
                messageBuilder.addField(
                    discordCommand.getAliases().get(0) +
                        (discordCommand.getUsage() == null ? "" : " " + discordCommand.getUsage()),
                    discordCommand.getDescription(),
                    false
                );
            }
        });

        channel.sendMessage(
            messageBuilder.build()
        ).queue();
    }
}

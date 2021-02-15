package usa.devrocoding.synergy.discord.assets;

import java.awt.Color;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import net.dv8tion.jda.api.JDA;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.command.command.CommandChangelog;
import usa.devrocoding.synergy.discord.command.command.CommandHelp;
import usa.devrocoding.synergy.discord.command.command.CommandSynergy;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;

public class DiscordManager extends DiscordModule {

    private String exceptionMessageCache = "";

    public DiscordManager(){
        super("Discord Manager");

        registerCommands(
            new CommandSynergy(),
            new CommandHelp(),
            new CommandChangelog()
        );
    }

    @Override
    public void init(JDA jda) {

    }

    public void automatedError(String exceptionMessage, String exception, String stackTrace){
        if (this.exceptionMessageCache.equalsIgnoreCase(exceptionMessage)){
            return;
        }

        this.exceptionMessageCache = exceptionMessage;
        Objects.requireNonNull(Discord.getJda()
            .getTextChannelById(Codes.STAFF_DISCORD_ERROR_CHANNEL_ID))
            .sendMessage(
                new MessageBuilder("Exception Error | Automated System", SetType.TITLE)
                    .addField("Exception", exception, false)
                    .addField("ExceptionMessage", exceptionMessage, false)
                    .addField("Stacktrace", stackTrace, false)
                    .setFooter(new Date().toString())
                    .overwriteColor(Color.RED)
                    .build()
            )
            .queue();
    }

}

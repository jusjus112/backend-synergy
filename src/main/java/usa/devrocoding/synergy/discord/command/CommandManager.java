package usa.devrocoding.synergy.discord.command;

import com.google.common.collect.Lists;
import lombok.Getter;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.command.command.CommandSynergy;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;

import java.util.List;

public class CommandManager extends DiscordModule {

    @Getter
    private final List<DiscordCommand> commands = Lists.newArrayList();

    public CommandManager(){
        super("Command Manager");

        registerEventListeners(
                new CommandListener()
        );

        registerCommands(
                new CommandSynergy()
        );
    }

    public void addCommand(DiscordCommand discordCommand){
        if (!this.commands.contains(discordCommand)) {
            this.commands.add(discordCommand);
        }
    }

}

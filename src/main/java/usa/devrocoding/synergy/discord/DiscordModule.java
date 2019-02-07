package usa.devrocoding.synergy.discord;

import lombok.Getter;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;

public abstract class DiscordModule {

    @Getter
    private String name;
    @Getter
    public Object[] listeners;

    public DiscordModule(String name){
        this.name = name;
    }

    public void registerEventListeners(Object... listeners){
        this.listeners = listeners;
    }

    public void registerCommands(DiscordCommand... discordCommands){
        for (DiscordCommand command : discordCommands){
            Discord.getCommandManager().addCommand(command);
        }
    }

}

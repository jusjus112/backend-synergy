package usa.devrocoding.synergy.discord.assets;

import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.command.command.CommandSynergy;

public class DiscordManager extends DiscordModule {

    public DiscordManager(){
        super("Discord Manager");

        registerCommands(
                new CommandSynergy()
        );
    }

}

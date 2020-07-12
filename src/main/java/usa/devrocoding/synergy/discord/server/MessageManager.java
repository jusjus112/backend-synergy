package usa.devrocoding.synergy.discord.server;

import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.server.listener.GuildUserJoinListener;

public class MessageManager extends DiscordModule {

    public MessageManager(){
        super("");

        registerEventListeners(
                new GuildUserJoinListener()
        );
    }

}

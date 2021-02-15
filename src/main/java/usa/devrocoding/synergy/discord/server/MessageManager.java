package usa.devrocoding.synergy.discord.server;

import java.util.Objects;
import net.dv8tion.jda.api.JDA;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.server.listener.GuildUserJoinListener;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;

public class MessageManager extends DiscordModule {

    public MessageManager(){
        super("Message Manager");

        registerEventListeners(
                new GuildUserJoinListener()
        );
    }

    @Override
    public void init(JDA jda) {

    }
}

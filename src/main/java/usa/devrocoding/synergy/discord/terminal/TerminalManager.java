package usa.devrocoding.synergy.discord.terminal;

import net.dv8tion.jda.api.JDA;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.DiscordModule;
import usa.devrocoding.synergy.discord.terminal.playercount.CounterThread;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.proxy.Core;

import java.util.concurrent.TimeUnit;

public class TerminalManager extends DiscordModule {

    public TerminalManager() {
        super("Playercounter Manager");

//        thread(new CounterThread(), 10, 30, TimeUnit.SECONDS);
        // TODO: Enable server counter
    }

    @Override
    public void init(JDA jda) {

    }
}

package usa.devrocoding.synergy.discord.terminal.playercount;

import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.DiscordModule;

import java.util.concurrent.TimeUnit;

public class PlayercountManager extends DiscordModule {

    public PlayercountManager(){
        super("Playercounter Manager");

        thread(new CounterThread(), 10, 30, TimeUnit.SECONDS);
    }

}

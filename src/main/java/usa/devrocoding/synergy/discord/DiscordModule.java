package usa.devrocoding.synergy.discord;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.proxy.Core;

import java.util.concurrent.TimeUnit;

public abstract class DiscordModule {

    @Getter
    private String name;
    private Object[] listeners;

    public DiscordModule(String name){
        this.name = name;
    }

    public void registerEventListeners(Object... listeners){
        Discord.getJda().addEventListener(listeners);
    }

    public void registerCommands(DiscordCommand... discordCommands){
        for (DiscordCommand command : discordCommands){
            Discord.getCommandManager().addCommand(command);
        }
    }

    public void thread(Runnable runnable, long delay, long period, TimeUnit timeUnit){
        Core.getCore().getProxy().getScheduler().schedule(Core.getCore(), runnable, delay, period, timeUnit);
    }

}

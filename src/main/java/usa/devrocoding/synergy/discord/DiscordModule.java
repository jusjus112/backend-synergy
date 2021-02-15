package usa.devrocoding.synergy.discord;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.md_5.bungee.api.plugin.Plugin;
import usa.devrocoding.synergy.discord.command.object.DiscordCommand;
import usa.devrocoding.synergy.proxy.Core;

import java.util.concurrent.TimeUnit;

public abstract class DiscordModule {

    @Getter
    private static List<DiscordModule> modules = Lists.newArrayList();

    @Getter
    private final String name;
    @Getter
    private Object[] listeners;

    public DiscordModule(String name){
        this.name = name;
        modules.add(this);
    }

    public abstract void init(JDA jda);

    public void registerEventListeners(Object... listeners){
        this.listeners = listeners;
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

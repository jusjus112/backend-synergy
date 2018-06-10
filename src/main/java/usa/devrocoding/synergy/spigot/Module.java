package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.command.Command;

import java.util.Arrays;

public abstract class Module implements Listener {

    @Getter
    private final Core plugin;
    @Getter
    private final String name;

    public Module(Core plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getPlugin().getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void registerCommand(Command... commands) {
        for (Command command : commands) {
            plugin.getCommandManager().getCommands().add(command);
        }
    }

    public void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void runTaskSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }
}
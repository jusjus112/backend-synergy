package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;

import java.util.Arrays;

public abstract class Module implements Listener {

    @Getter
    private final Core plugin;
    @Getter
    private final String name;
    @Getter
    private final Sam sam;
    @Getter
    private boolean disabled = false;

    @Getter
    public static String loaded_msg = "";

    public Module(Core plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.sam = new Sam();

//        Synergy.info(name + " loaded....");
        if (loaded_msg.length() > 0){
            loaded_msg += ", ";
        }
        loaded_msg += name;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getPlugin().getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void registerCommand(SynergyCommand... commands) {
        for (SynergyCommand command : commands) {
            plugin.getCommandManager().getCommands().add(command);
        }
    }

    public void disable(){
        this.disabled = true;
    }

    public void runTaskAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void runTaskSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }
}

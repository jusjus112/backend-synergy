package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
    private boolean reloadable;

    @Getter
    public static int total = 0;

    public Module(Core plugin, String name, boolean reloadable) {
        this.plugin = plugin;
        this.name = name;
        this.sam = new Sam();
        this.reloadable = reloadable;
        total++;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        getPlugin().getModules().add(this);
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> getPlugin().getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void registerCommand(SynergyCommand... commands) {
        for (SynergyCommand command : commands) {
            plugin.getCommandManager().getCommands().add(command);
        }
    }

    public void registerPlaceholder(PlaceholderExpansion... expansions){
        if (getPlugin().getDependencyManager().isPluginEnabled("PlaceholderAPI")) {
            Arrays.stream(expansions).forEach(
                    expansion -> expansion.register()
            );
        }
    }

    public String getShortname(){
        return getName().split(" ")[0];
    }

    public abstract void reload(String response);

    public void disable(){
        this.disabled = true;
    }
}

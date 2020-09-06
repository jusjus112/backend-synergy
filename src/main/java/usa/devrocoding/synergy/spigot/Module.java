package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;

import java.util.Arrays;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;

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
            plugin.getCommandManager().registerCommand(command);
        }
    }

    public void registerObjectives(Objective... objectives){
        Arrays.stream(objectives).forEach(objective -> getPlugin().getObjectiveManager().registerObjective(objective));
    }

    public String getShortname(){
        return getName().split(" ")[0];
    }

    public abstract void reload(String response);

    public void disable(){
        this.disabled = true;
    }
}

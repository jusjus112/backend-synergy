package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.includes.SynergyResponse;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;

import java.util.Arrays;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;

@Getter
public abstract class Module implements Listener {

    private final Core plugin;
    private final String name;
    private SynergyCommand[] synergyCommands = null;
    private Listener[] listeners = null;
    private boolean disabled = false;
    private final boolean reloadable;
    @Getter
    public static int total = 0;

    public Module(Core plugin, String name, boolean reloadable) {

//        Thread.setDefaultUncaughtExceptionHandler(
//            (t, e) -> Synergy.error("Uncaught Exception detected in thread", t.toString(), e.toString()));

        this.plugin = plugin;
        this.name = name;
        this.reloadable = reloadable;
        total++;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        getPlugin().getModules().add(this);
    }

    public void registerListener(Listener... listeners) {
        this.listeners = listeners;
        Arrays.stream(listeners).forEach(listener ->
            getPlugin().getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void registerCommand(SynergyCommand... commands) {
        this.synergyCommands = commands;
        for (SynergyCommand command : commands) {
            plugin.getCommandManager().registerCommand(command);
        }
    }

    public void registerObjectives(Objective... objectives){
        Arrays.stream(objectives).forEach(objective -> getPlugin()
            .getObjectiveManager().registerObjective(objective));
    }

    public String getShortname(){
        return getName().split(" ")[0];
    }

    public void disable(){
        this.disabled = true;

        if (this.synergyCommands != null){
            for (SynergyCommand command : synergyCommands) {
                plugin.getCommandManager().unregisterCommand(command);
            }
        }

        Arrays.stream(listeners).forEach(listener -> {
            ((Event) listener).getHandlers().unregister(listener);
        });
    }

    public void reload(){
        this.disabled = true;
    }

    public abstract void onReload(SynergyResponse response);
    public abstract void onDisable();

    public void onServerLoad(){}
    public void onInit(){}
    public void onDeinit(){}
}

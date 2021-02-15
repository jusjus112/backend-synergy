package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import org.bukkit.event.Listener;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;

import java.util.Arrays;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;

@Getter
public abstract class Module implements Listener {

    private final Core plugin;
    private final String name;
    private final Sam sam;
    private boolean disabled = false;
    private final boolean reloadable;
    @Getter
    public static int total = 0;

    public Module(Core plugin, String name, boolean reloadable) {

//        Thread.setDefaultUncaughtExceptionHandler(
//            (t, e) -> Synergy.error("Uncaught Exception detected in thread", t.toString(), e.toString()));

        this.plugin = plugin;
        this.name = name;
        this.sam = new Sam();
        this.reloadable = reloadable;
        total++;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        getPlugin().getModules().add(this);
    }

    public void registerListener(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener ->
            getPlugin().getServer().getPluginManager().registerEvents(listener, plugin));
    }

    public void registerCommand(SynergyCommand... commands) {
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

    public abstract void reload(String response);

    public void onServerLoad(){}
    public void onInit(){}
    public void onDeinit(){}

    public void disable(){
        this.disabled = true;
    }
}

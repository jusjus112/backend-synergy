package usa.devrocoding.synergy.spigot.runnable;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

import java.util.Map;
import java.util.function.Consumer;

public class RunnableManager extends Module {

    private static final Map<String, SynergyRunnable> RUNNABLES = Maps.newConcurrentMap();

    public RunnableManager(Core plugin) {
        super(plugin, "Runnable Manager", false);
    }

    @Override
    public void reload(String response) {

    }

    public void runTask(String name, Consumer<Core> run) {
        createRunnable(name, run).runTask(getPlugin());
    }

    public void runTaskAsynchronously(String name, Consumer<Core> run) {
        createRunnable(name, run).runTaskAsynchronously(getPlugin());
    }

    public void runTaskLater(String name, Consumer<Core> run, long time) {
        createRunnable(name, run).runTaskLater(getPlugin(), time);
    }

    public void runTaskLaterAsynchronously(String name, Consumer<Core> run, long time) {
        createRunnable(name, run).runTaskLaterAsynchronously(getPlugin(), time);
    }

    public void runTaskTimer(String name, Consumer<Core> run, long delay, long period) {
        createRunnable(name, run).runTaskTimer(getPlugin(), delay, period);
    }

    public void runTaskTimerAsynchronously(String name, Consumer<Core> run, long delay, long period) {
        createRunnable(name, run).runTaskTimerAsynchronously(getPlugin(), delay, period);
    }

    public void updateTime(String name, long delay, long period) {
        SynergyRunnable synergyRunnable = RUNNABLES.get(name);

        if (synergyRunnable == null) {
            return;
        }

        SynergyRunnable newRunnable = synergyRunnable.clone();

        RUNNABLES.remove(name);

        runTaskTimer(name, newRunnable.getRun(), delay, period);

        return;
    }

    private SynergyRunnable createRunnable(String name, Consumer<Core> run) {
        SynergyRunnable runnable = new SynergyRunnable(getPlugin(), this, name, run);

        RUNNABLES.put(name, runnable);

        return runnable;
    }

    public Map<String, SynergyRunnable> getRunnables() {
        return RUNNABLES;
    }

}
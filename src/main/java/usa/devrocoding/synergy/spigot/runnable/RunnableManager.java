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

    public SynergyRunnable runTask(String name, Consumer<Core> run) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTask(getPlugin());
        return runnable;
    }

    public SynergyRunnable runTaskAsynchronously(String name, Consumer<Core> run) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTaskAsynchronously(getPlugin());
        return runnable;
    }

    public SynergyRunnable runTaskLater(String name, Consumer<Core> run, long time) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTaskLater(getPlugin(), time);
        return runnable;
    }

    public SynergyRunnable runTaskLaterAsynchronously(String name, Consumer<Core> run, long time) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTaskLaterAsynchronously(getPlugin(), time);
        return runnable;
    }

    public SynergyRunnable runTaskTimer(String name, Consumer<Core> run, long delay, long period) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTaskTimer(getPlugin(), delay, period);
        return runnable;
    }

    public SynergyRunnable runTaskTimerAsynchronously(String name, Consumer<Core> run, long delay, long period) {
        SynergyRunnable runnable = createRunnable(name, run);
        runnable.runTaskTimerAsynchronously(getPlugin(), delay, period);
        return runnable;
    }

    public void updateTime(String name, long delay, long period) {
        SynergyRunnable synergyRunnable = RUNNABLES.get(name);

        if (synergyRunnable == null) {
            return;
        }

        SynergyRunnable newRunnable = synergyRunnable.clone();

        RUNNABLES.remove(name);

        runTaskTimer(name, newRunnable.getRun(), delay, period);
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
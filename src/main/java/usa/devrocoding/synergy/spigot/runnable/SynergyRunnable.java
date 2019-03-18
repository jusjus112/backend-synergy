package usa.devrocoding.synergy.spigot.runnable;

import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.spigot.Core;

import java.util.function.Consumer;

public class SynergyRunnable extends BukkitRunnable implements Cloneable {

    private final Core plugin;
    private final RunnableManager runnableManager;
    @Getter
    private final String name;
    @Getter
    private final Consumer<Core> run;

    public SynergyRunnable(Core plugin, RunnableManager runnableManager, String name, Consumer<Core> run) {
        this.plugin = plugin;
        this.runnableManager = runnableManager;
        this.name = name;
        this.run = run;
    }

    @Override
    public void run() {
        run.accept(plugin);
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        runnableManager.getRunnables().remove(name);
    }

    public SynergyRunnable clone() {
        try {
            return (SynergyRunnable) super.clone();
        } catch (Exception ex) {
            return null;
        }
    }
}

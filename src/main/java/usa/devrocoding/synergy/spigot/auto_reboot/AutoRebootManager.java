package usa.devrocoding.synergy.spigot.auto_reboot;

import lombok.Getter;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.auto_reboot.command.CommandReboot;
import usa.devrocoding.synergy.spigot.auto_reboot.thread.RebootChecker;
import usa.devrocoding.synergy.spigot.auto_reboot.thread.Rebooter;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class AutoRebootManager extends Module {

    private long period = SynergyPeriod.MINUTE.getPeriod() * 5;
    @Getter
    private int restartHour;
    @Getter
    private boolean restarting = false;

    public AutoRebootManager(Core plugin){
        super(plugin, "Reboot Manager", false);

        registerCommand(
                new CommandReboot(getPlugin())
        );

        // Init the async timer
        this.restartHour = 0; // 12 AM
        getPlugin().getRunnableManager().runTaskTimerAsynchronously("Reboot Check", new RebootChecker(this),
                //      DELAY = 30 min             PERIOD = 1 hour
                SynergyPeriod.MINUTE.getPeriod()*61, period);
    }

    @Override
    public void reload(String response) {

    }

    public void rebootServer(SynergyPeriod synergyPeriod){
        restarting = true;
        getPlugin().getRunnableManager().runTaskTimerAsynchronously(
            "Rebooter",
            new Rebooter(synergyPeriod.getPeriod(), "Daily Restart"
            ), SynergyPeriod.SECOND.getPeriod(), SynergyPeriod.TICK.getPeriod()
        );
    }
}

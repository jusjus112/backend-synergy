package usa.devrocoding.synergy.spigot.auto_reboot;

import lombok.Getter;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.auto_reboot.thread.RebootChecker;
import usa.devrocoding.synergy.spigot.auto_reboot.thread.Rebooter;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class AutoRebootManager extends Module {

    private long period = SynergyPeriod.SECOND.getPeriod() * 10;
    @Getter
    private int restartHour;

    public AutoRebootManager(Core plugin){
        super(plugin, "Reboot Manager", false);

        // Init the async timer
        this.restartHour = 23;
        getPlugin().getRunnableManager().runTaskTimerAsynchronously("Reboot Check", new RebootChecker(this), SynergyPeriod.SECOND.getPeriod()*15, period);
    }

    @Override
    public void reload(String response) {

    }

    public void rebootServer(){
        getPlugin().getRunnableManager().runTaskTimerAsynchronously(
            "Rebooter",
            new Rebooter(
                    SynergyPeriod.MINUTE.getPeriod()*5
            ), SynergyPeriod.SECOND.getPeriod(), SynergyPeriod.TICK.getPeriod()
        );
    }
}

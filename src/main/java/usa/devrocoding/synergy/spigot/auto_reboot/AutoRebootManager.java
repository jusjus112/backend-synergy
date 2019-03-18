package usa.devrocoding.synergy.spigot.auto_reboot;

import lombok.Getter;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.auto_reboot.thread.Rebooter;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.Time;

public class AutoRebootManager extends Module {

    private long period = SynergyPeriod.HOUR.getPeriod();
    @Getter
    private Time restartTime;

    public AutoRebootManager(Core plugin){
        super(plugin, "Reboot Manager", false);

        // Init the async timer
//        getPlugin().getRunnableManager().runTaskTimerAsynchronously("Auto Reboot", new Rebooter(this), SynergyPeriod.SECOND.getPeriod()*30, period);
    }

    @Override
    public void reload(String response) {

    }

    public void rebootServer(){
        for(SynergyUser user : getPlugin().getUserManager().getOnlineUsers()){
            user.getDisplay().sendTitle("Server is restarting!");
        }
    }
}

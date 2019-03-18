package usa.devrocoding.synergy.spigot.auto_reboot.thread;

import lombok.Getter;
import org.bukkit.Bukkit;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilTime;

import java.util.function.Consumer;

public class Rebooter implements Consumer<Core> {

    @Getter
    private long timeUntilRestart;
    private long[] times = {
            SynergyPeriod.MINUTE.getPeriod()*5,SynergyPeriod.MINUTE.getPeriod()*3,SynergyPeriod.MINUTE.getPeriod()*2,SynergyPeriod.MINUTE.getPeriod(),
            SynergyPeriod.SECOND.getPeriod()*30,SynergyPeriod.SECOND.getPeriod()*20,SynergyPeriod.SECOND.getPeriod()*10,SynergyPeriod.SECOND.getPeriod()*5,
            SynergyPeriod.SECOND.getPeriod()*4,SynergyPeriod.SECOND.getPeriod()*3,SynergyPeriod.SECOND.getPeriod()*2,SynergyPeriod.SECOND.getPeriod()
    };

    public Rebooter(long timeUntilRestart){
        this.timeUntilRestart = ++timeUntilRestart;
    }

    @Override
    public void accept(Core core) {
        this.timeUntilRestart--;
        if (getTimeUntilRestart() <= 0){
            //TODO: Teleport everyone to the hub maybe???
            core.getServer().shutdown();
            core.getRunnableManager().getRunnables().get("Rebooter").cancel();
        }

        for(long time : times){
            if (getTimeUntilRestart() == time){
                String time_str = UtilTime.simpleTimeFormat(time);
                for (SynergyUser user : core.getUserManager().getUsers().values()){
                    user.getDisplay().sendTitleAndSubTitle("§c§lServer restarts in".toUpperCase(), time_str, 20, 10, 20);
                }
            }
        }
        this.timeUntilRestart--;
    }

    @Override
    public Consumer<Core> andThen(Consumer<? super Core> after) {
        return null;
    }
}

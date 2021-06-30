package usa.devrocoding.synergy.spigot.autoreboot.thread;

import lombok.Getter;
import org.bukkit.Sound;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.includes.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
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

    public Rebooter(long timeUntilRestart, String reason){
        this.timeUntilRestart = ++timeUntilRestart;

        String time_str = UtilTime.format((timeUntilRestart/20d));
        for (SynergyUser user : Core.getPlugin().getUserManager().getUsers().values()) {
            user.sendModifactionMessage(
                    MessageModification.RAW,
                    " ",
                    "§eServer restarts in §c§l" + time_str,
                    "§cReason: "+reason,
                    "§eDon't worry, your progress is saved automatically!",
                    "§eWe will be back directly after!",
                    " "
            );
            user.getSoundControl().play(Sound.BLOCK_NOTE_GUITAR);
        }
    }

    @Override
    public void accept(Core core) {
        this.timeUntilRestart--;
        if (getTimeUntilRestart() <= 0){
            //TODO: Teleport everyone to the hub maybe???
            Core.getPlugin().getAutoRebootManager().shutdownServer();
            core.getRunnableManager().getRunnables().get("Rebooter").cancel();
        }

        for(long time : times){
            if (getTimeUntilRestart() == time){
                if (!Core.getPlugin().getCooldownManager().isOnCooldown(getTimeUntilRestart())) {
                    String time_str = UtilTime.format((getTimeUntilRestart()/20d));
                    Synergy.info("Restarting in ".toUpperCase()+time_str);
                    for (SynergyUser user : core.getUserManager().getUsers().values()) {
                        boolean fade = getTimeUntilRestart() < (SynergyPeriod.SECOND.getPeriod()*10);
                        user.getDisplay().sendTitleAndSubTitle("§c§lServer restarts in".toUpperCase(), time_str, fade?0:20, 40, fade?0:20);
                        user.getSoundControl().pling();
                    }
                }else{
                    Core.getPlugin().getCooldownManager().addCooldown(time, SynergyPeriod.SECOND.getPeriod()*2);
                }
            }
        }
    }

    @Override
    public Consumer<Core> andThen(Consumer<? super Core> after) {
        return null;
    }
}

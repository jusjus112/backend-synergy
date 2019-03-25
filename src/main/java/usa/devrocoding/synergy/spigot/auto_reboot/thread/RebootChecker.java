package usa.devrocoding.synergy.spigot.auto_reboot.thread;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.auto_reboot.AutoRebootManager;

import java.time.LocalTime;
import java.util.function.Consumer;

public class RebootChecker implements Consumer<Core> {

    @Getter
    private AutoRebootManager manager;

    public RebootChecker(AutoRebootManager manager){
        this.manager = manager;
    }

    @Override
    public void accept(Core core) {
        if (LocalTime.now().getHour()==getManager().getRestartHour()){
            if (!getManager().isRestarting()) {
                getManager().rebootServer();
            }
            core.getRunnableManager().getRunnables().get("Reboot Check").cancel();
        }
    }

    @Override
    public Consumer<Core> andThen(Consumer<? super Core> after) {
        return null;
    }
}

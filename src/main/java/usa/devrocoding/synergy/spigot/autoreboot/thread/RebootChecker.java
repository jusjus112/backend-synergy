package usa.devrocoding.synergy.spigot.autoreboot.thread;

import lombok.Getter;
import usa.devrocoding.synergy.includes.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.autoreboot.AutoRebootManager;

import java.util.function.Consumer;

public class RebootChecker implements Consumer<Core> {

    @Getter
    private AutoRebootManager manager;

    public RebootChecker(AutoRebootManager manager){
        this.manager = manager;
    }

    @Override
    public void accept(Core core) {
        if (!getManager().isRestarting()) {
            getManager().rebootServer(SynergyPeriod.MINUTE.getCustom(2), "Automatic Performance Restart");
        }
        core.getRunnableManager().getRunnables().get("Reboot Check").cancel();
    }

    @Override
    public Consumer<Core> andThen(Consumer<? super Core> after) {
        return null;
    }
}

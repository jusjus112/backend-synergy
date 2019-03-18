package usa.devrocoding.synergy.spigot.auto_reboot.thread;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.auto_reboot.AutoRebootManager;

import java.time.LocalTime;
import java.util.function.Consumer;

public class Rebooter implements Consumer<Core> {

    @Getter
    private AutoRebootManager manager;

    public Rebooter(AutoRebootManager manager){
        this.manager = manager;
    }

    @Override
    public void accept(Core core) {
        if (LocalTime.now().getHour()==getManager().getRestartTime().getHours()){
            getManager().rebootServer();
        }
    }

    @Override
    public Consumer<Core> andThen(Consumer<? super Core> after) {
        return null;
    }
}

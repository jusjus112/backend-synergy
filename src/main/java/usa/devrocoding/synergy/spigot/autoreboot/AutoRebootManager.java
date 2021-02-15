package usa.devrocoding.synergy.spigot.autoreboot;

import java.util.Iterator;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.assets.object.SynergyPeriod;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.autoreboot.command.CommandReboot;
import usa.devrocoding.synergy.spigot.autoreboot.thread.RebootChecker;
import usa.devrocoding.synergy.spigot.autoreboot.thread.Rebooter;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class AutoRebootManager extends Module {

    @Getter
    private boolean restarting;

    public AutoRebootManager(Core plugin){
        super(plugin, "Reboot Manager", false);
        this.restarting = false;
        long restartDelay = SynergyPeriod.HOUR.getPeriod() * 3;

        registerCommand(
                new CommandReboot(getPlugin())
        );

        getPlugin().getRunnableManager().runTaskLaterAsynchronously("Reboot Check",
            new RebootChecker(this), restartDelay);
    }

    @Override
    public void reload(String response) {
        this.restarting = true;
    }

    public void rebootServer(long period, String reason){
        restarting = true;
        getPlugin().getRunnableManager().runTaskTimerAsynchronously(
            "Rebooter",
            new Rebooter(period, reason
            ), SynergyPeriod.SECOND.getPeriod(), SynergyPeriod.TICK.getPeriod()
        );
    }

    public void shutdownServer(){
        Iterator<SynergyUser> users = Core.getPlugin().getUserManager()
            .getUsers().values().iterator();
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!users.hasNext() || Bukkit.getOnlinePlayers().size() <= 0){
                    killServer();
                }
                for (int i = 0; i < 2; i++) {
                    if (users.hasNext()) {
                        // TODO: ConcurrentModificationException
                        String reason = "§5§lMiragePrisons\n§eAutomatic Server Shutdown";
                        try{
                            users.next().kick(reason);
                        }catch (Exception e){
                            Synergy.error(e.getMessage());
                            Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(reason));
                        }
                    }
                }
            }
        }.runTaskTimer(getPlugin(), 20, 20);
    }

    private void killServer(){
        Core.getPlugin().getServer().shutdown();
    }

}

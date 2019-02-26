package usa.devrocoding.synergy.spigot.changelog.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.changelog.object.Changelog;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilTime;

import java.util.Date;

public class ChangelogJoinListener implements Listener {

    @EventHandler
    public void onChangelogJoin(PlayerJoinEvent e){
        Changelog changelog = Core.getPlugin().getChangelogManager().getLatestChangelog();
        SynergyUser user = Core.getPlugin().getUserManager().getUser(e.getPlayer());

        if (changelog != null) {
            if (UtilTime.daysBetween(changelog.getDate(), new Date()) <= 5){
                user.message(
                        C.getLineWithName("Updates"),
                        "Did you already checked the new updates?",
                        "Changelog for §b" + changelog.getNiceDate(),
                        "Check out this changelog with §6§l/changelog",
                        C.getLine()
                );
        }
        }
    }

}

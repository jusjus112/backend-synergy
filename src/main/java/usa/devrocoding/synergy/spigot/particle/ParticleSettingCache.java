package usa.devrocoding.synergy.spigot.particle;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ParticleSettingCache implements Listener {

	private static Set<String> blacklist = new HashSet<>();

	@EventHandler
	// public void Update(UpdateEvent e) {
	// if (e.getType() != UpdateType.FIVE_SECOND)
	// return;
	// Set<String> blacklist = new HashSet<>();
	// for (Player pl : UtilServer.getPlayers()) {
	// if (MemberManager.getMember(pl, false) == null)
	// continue;
	// if (!MemberManager.getMember(pl,
	// false).getSettings().isEnabled(Setting.Particles))
	// blacklist.add(pl.getName());
	// }
	// this.blacklist = blacklist;
	// }

	public static boolean isBlacklisted(String player) {
		return blacklist.contains(player);
	}

}

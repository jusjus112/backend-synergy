package usa.devrocoding.synergy.spigot.scoreboard;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.scoreboard.listener.ScoreboardListener;
import usa.devrocoding.synergy.spigot.test.DefaultScoreboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class ScoreboardManager extends Module {

	@Getter
	private final Map<UUID, ZylemBoard> scoreboards = new HashMap<>();
	private final ScoreboardListener scoreboardListener;

	@Setter @Getter
	private ScoreboardPolicy defaultPolicy;
	@Getter
	private String title;
	private long lastUpdate = System.currentTimeMillis();

	public ScoreboardManager(Core backend) {
		super(backend, "Scoreboard Manager", false);
		this.title = "  " + Core.getPlugin().getManifest().main_color() + ChatColor.BOLD.toString() + Core.getPlugin().getManifest().backend_name() + "  ";

		this.scoreboardListener = new ScoreboardListener(backend, this);

		registerListener(scoreboardListener);
		setDefaultPolicy(new DefaultScoreboard());
		
		backend.getRunnableManager().runTaskTimerAsynchronously("Scoreboard Update", (plugin) -> {
			update();
		}, 40, 100L);

		new ScoreboardAnimation();
	}

	@Override
	public void onReload(String response) {

	}

	public void setScoreboardPolicy(ScoreboardPolicy scoreboardPolicy, SynergyUser synergyUser) {
		if (synergyUser == null || !synergyUser.getPlayer().isOnline()){
			return;
		}
		if (this.scoreboards.containsKey(synergyUser.getUuid())){
			this.scoreboards.get(synergyUser.getUuid()).setNewPolicy(scoreboardPolicy);
		}
	}

	public void setUpdateTime(long time) {
		getPlugin().getRunnableManager().updateTime("Scoreboard Update", 0, time);
	}

	public void update(boolean force) {
		// Will fix the java.util.ConcurrentModificationException
		if (!force && (System.currentTimeMillis() - lastUpdate < 500))
			return;
		lastUpdate = System.currentTimeMillis();
		for (ZylemBoard zylemBoard : scoreboards.values())
			zylemBoard.update();
	}

	public void update(){
		this.update(false);
	}

	public void update(Player p) {
		if (scoreboards.containsKey(p.getUniqueId()))
			scoreboards.get(p.getUniqueId()).update();
	}

	@Deprecated
	public void updateUndername() {
		for (ZylemBoard zylemBoard : scoreboards.values())
			zylemBoard.updateUndername();
	}

	public void setTitle(String title) {
		this.title = title;
		for (ZylemBoard zylemBoard : scoreboards.values())
			zylemBoard.setTitle(title);
	}

//	public void addCustomLines(List<String> customLines) {
//		if (customLines == null)
//			this.customLines = null;
//		else if (this.customLines == null)
//			this.customLines = customLines;
//		else
//			this.customLines.addAll(customLines);
//	}

	public String trimPrefix(String prefix) {
		if (prefix == null)
			return null;
		if (prefix.length() < 16)
			return prefix;
		if (prefix.split(" ").length <= 1)
			return prefix.substring(0, 16);
		String firstPart = prefix.split(" ")[0];
		String secondPart = prefix.substring((firstPart + " ").length());
		if (16 - (secondPart + " ").length() < 0){
			return prefix.substring(0, 16);
		}
		firstPart = firstPart.substring(0, 16 - (secondPart + " ").length());
		return firstPart + " " + secondPart;
	}
}

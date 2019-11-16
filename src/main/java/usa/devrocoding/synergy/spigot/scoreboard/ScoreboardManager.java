package usa.devrocoding.synergy.spigot.scoreboard;

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

public class ScoreboardManager extends Module {

	private final Map<UUID, ZylemBoard> scoreboards = new HashMap<>();
	private final ScoreboardListener scoreboardListener;

	private List<String> customLines;
	private ScoreboardPolicy scoreboardPolicy;
	private String title;
	private long lastUpdate = System.currentTimeMillis();

	public ScoreboardManager(Core backend) {
		super(backend, "Scoreboard Manager", false);
		this.title = "  " + Core.getPlugin().getManifest().main_color() + ChatColor.BOLD.toString() + Core.getPlugin().getManifest().backend_name() + "  ";

		this.scoreboardListener = new ScoreboardListener(backend, this);

		registerListener(scoreboardListener);

		setScoreboardPolicy(new DefaultScoreboard());
		
		backend.getRunnableManager().runTaskTimerAsynchronously("Scoreboard Update", (plugin) -> {
			update();
		}, 40, 100L);

		new ScoreboardAnimation();
	}

	@Override
	public void reload(String response) {

	}

	public void setUpdateTime(long time) {
		getPlugin().getRunnableManager().updateTime("Scoreboard Update", 0, time);
	}

	public void update() {
		// Will fix the java.util.ConcurrentModificationException
		if (System.currentTimeMillis() - lastUpdate < 500)
			return;
		lastUpdate = System.currentTimeMillis();
		for (ZylemBoard zylemBoard : scoreboards.values())
			zylemBoard.update();
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

	public Map<UUID, ZylemBoard> getScoreboards() {
		return scoreboards;
	}

	public ScoreboardPolicy getScoreboardPolicy() {
		return scoreboardPolicy;
	}

	public void setScoreboardPolicy(ScoreboardPolicy scoreboardPolicy) {
		this.scoreboardPolicy = scoreboardPolicy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		for (ZylemBoard zylemBoard : scoreboards.values())
			zylemBoard.setTitle(title);
	}

	public List<String> getCustomLines() {
		return customLines;
	}

	public void addCustomLines(List<String> customLines) {
		if (customLines == null)
			this.customLines = null;
		else if (this.customLines == null)
			this.customLines = customLines;
		else
			this.customLines.addAll(customLines);
	}

	public String trimPrefix(String prefix) {
		if (prefix == null)
			return null;
		if (prefix.length() < 16)
			return prefix;
		if (prefix.split(" ").length <= 1)
			return prefix.substring(0, 16);
		String firstPart = prefix.split(" ")[0];
		String secondPart = prefix.substring((firstPart + " ").length());
		firstPart = firstPart.substring(0, 16 - (secondPart + " ").length());
		return firstPart + " " + secondPart;
	}
}

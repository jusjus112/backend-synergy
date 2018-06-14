package usa.devrocoding.synergy.spigot.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZylemBoard {

	private static Core backend;

	private Player p;
	private SynergyUser user;

	private Scoreboard scoreboard;
	private Objective sidebarObjective, undernameObjective, tablistObjective;
	private List<String> sidebar;

	private static final Map<String, SynergyUser> staticNametags = new HashMap<>();

	public ZylemBoard(Core backend, Player p, SynergyUser user) {
		ZylemBoard.backend = backend;

		this.p = p;
		this.user = user;
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		this.sidebar = new ArrayList<>();
		for (SynergyUser staticNametag : staticNametags.values())
			setStaticNametag0(staticNametag);
		getPlayer().setScoreboard(scoreboard);
	}

	public void update() {
		updateSidebar();
		updateTeams();
		updateUndername();
		updateTablist();
	}

	public void updateSidebar() {
		if (backend.getScoreboardManager().getCustomLines() == null && backend.getScoreboardManager().getScoreboardPolicy().getSidebar(user) == null) {
			if (sidebarObjective != null) {
				sidebarObjective.unregister();
				sidebarObjective = null;
			}
			return;
		}

		setSidebar(backend.getScoreboardManager().getCustomLines() == null ? backend.getScoreboardManager().getScoreboardPolicy().getSidebar(user) : backend.getScoreboardManager().getCustomLines());
	}

	public void updateTeams() {
		for (Team team : new ArrayList<>(scoreboard.getTeams())) {
			if (staticNametags.containsKey(team.getName()))
				continue;
			Player pl = null;
			try {
				pl = Bukkit.getPlayer(team.getName().split("-")[1]);
			} catch (ArrayIndexOutOfBoundsException e) {
				//Temp solution
			}
			if (pl == null)
				team.unregister();
			else {
				String prefix = backend.getScoreboardManager().getScoreboardPolicy().getPrefix(user, backend.getUserManager().getUser(pl));
				String suffix = backend.getScoreboardManager().getScoreboardPolicy().getSuffix(user, backend.getUserManager().getUser(pl));
				if (prefix != null && !team.getPrefix().equals(prefix))
					team.setPrefix(backend.getScoreboardManager().trimPrefix(prefix));
				else if (prefix == null && team.getPrefix() != null)
					team.setPrefix("");
				if (suffix != null && !team.getSuffix().equals(suffix))
					team.setSuffix(backend.getScoreboardManager().trimPrefix(suffix));
				else if (suffix == null && team.getSuffix() != null)
					team.setSuffix("");
			}
		}
		for (Player pl : Bukkit.getOnlinePlayers()) {
			SynergyUser subject = backend.getUserManager().getUser(pl);
			String teamName = getTeamName(subject);
			if (scoreboard.getTeam(teamName) == null) {
				Team team = scoreboard.registerNewTeam(teamName);
				team.addEntry(pl.getName());
				String prefix = backend.getScoreboardManager().getScoreboardPolicy().getPrefix(user, backend.getUserManager().getUser(pl));
				String suffix = backend.getScoreboardManager().getScoreboardPolicy().getSuffix(user, backend.getUserManager().getUser(pl));
				if (prefix != null)
					team.setPrefix(backend.getScoreboardManager().trimPrefix(prefix));
				if (suffix != null)
					team.setSuffix(backend.getScoreboardManager().trimPrefix(suffix));
			}
		}
	}

	public void updateUndername() {
		if (backend.getScoreboardManager().getScoreboardPolicy().getUndername(user) == null) {
			if (undernameObjective != null) {
				undernameObjective.unregister();
				undernameObjective = null;
			}
		} else {
			if (undernameObjective == null) {
				undernameObjective = scoreboard.registerNewObjective("undername", "dummy");
				undernameObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
			}
			if (!undernameObjective.getDisplayName().equals(backend.getScoreboardManager().getScoreboardPolicy().getUndername(user)))
				undernameObjective.setDisplayName(backend.getScoreboardManager().getScoreboardPolicy().getUndername(user));
			for (Player pl : Bukkit.getOnlinePlayers()) {
				String name = pl.getName();
				int score = backend.getScoreboardManager().getScoreboardPolicy().getUndernameScore(user, backend.getUserManager().getUser(pl));
				if (undernameObjective.getScore(name).getScore() != score)
					undernameObjective.getScore(name).setScore(score);
			}
		}
	}

	public void updateTablist() {
		if (backend.getScoreboardManager().getScoreboardPolicy().getTablist(user) == null) {
			if (tablistObjective != null) {
				tablistObjective.unregister();
				tablistObjective = null;
			}
		} else {
			if (tablistObjective == null) {
				tablistObjective = scoreboard.registerNewObjective("tablist", "dummy");
				tablistObjective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			}
			if (!tablistObjective.getDisplayName().equals(backend.getScoreboardManager().getScoreboardPolicy().getTablist(user)))
				tablistObjective.setDisplayName(backend.getScoreboardManager().getScoreboardPolicy().getTablist(user));
			for (Player pl : Bukkit.getOnlinePlayers()) {
				String name = pl.getName();
				int score = backend.getScoreboardManager().getScoreboardPolicy().getTablistScore(user, backend.getUserManager().getUser(pl));
				if (tablistObjective.getScore(name).getScore() != score)
					tablistObjective.getScore(name).setScore(score);
			}
		}
	}

	public void setSidebar(List<String> sidebar) {
		if (sidebarObjective == null) {
			sidebarObjective = scoreboard.registerNewObjective("title", "dummy");
			sidebarObjective.setDisplayName(backend.getScoreboardManager().getTitle());
			sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		List<String> set = new ArrayList<>();
		if (sidebar != null && sidebar.size() > 0) {
			int score = 15;
			for (String line : sidebar) {
				if (score < 1)
					break;
				if (set.contains(line))
					continue;
				set.add(line);
				sidebarObjective.getScore(line).setScore(score);
				score--;
			}
		}
		for (String last : this.sidebar)
			if (!set.contains(last))
				scoreboard.resetScores(last);
		this.sidebar = set;
	}

	public void unregister() {
		if (sidebarObjective != null)
			sidebarObjective.unregister();
		if (undernameObjective != null)
			undernameObjective.unregister();
		if (tablistObjective != null)
			tablistObjective.unregister();
		sidebarObjective = null;
		undernameObjective = null;
		tablistObjective = null;
	}

	public void setTitle(String title) {
		if (sidebarObjective != null)
			sidebarObjective.setDisplayName(title);
	}

	public static void setStaticNametag(SynergyUser User) {
		if (!staticNametags.containsKey(getTeamName(User)))
			staticNametags.put(getTeamName(User), User);
		for (ZylemBoard zylemBoard : backend.getScoreboardManager().getScoreboards().values())
			zylemBoard.setStaticNametag0(User);
	}

	private void setStaticNametag0(SynergyUser user) {
		Team team = scoreboard.getTeam(getTeamName(user));

		if (team != null) {
			team.unregister();
		}

		team = scoreboard.registerNewTeam(getTeamName(user));
		String prefix = backend.getScoreboardManager().getScoreboardPolicy().getPrefix(null, user), suffix = backend.getScoreboardManager().getScoreboardPolicy().getSuffix(null, user);
		if (prefix != null) {
			team.setPrefix(prefix);
		}

		if (suffix != null) {
			team.setSuffix(suffix);
		}

		team.addEntry(user.getName());

		if (undernameObjective == null) {
			undernameObjective = scoreboard.registerNewObjective("undername", "dummy");
			undernameObjective.setDisplaySlot(DisplaySlot.BELOW_NAME);
			undernameObjective.setDisplayName(backend.getScoreboardManager().getScoreboardPolicy().getUndername(user));
		}

		undernameObjective.getScore(user.getName()).setScore(backend.getScoreboardManager().getScoreboardPolicy().getUndernameScore(null, user));
	}

	public static String getTeamName(SynergyUser subject) {
		if(subject == null) {
			return "NONE";
		}
		
		String sort = String.valueOf(Rank.values().length - subject.getRank().ordinal());

		while (sort.length() < String.valueOf(Rank.values().length).length()) {
			sort = "0" + sort;
		}

		String displayName = sort + "-" + subject.getName();
		displayName = displayName.substring(0, Math.min(displayName.length(), 16));
		return displayName;
	}

	public static Map<String, SynergyUser> getStaticNametags() {
		return staticNametags;
	}

	public Player getPlayer() {
		return p;
	}

	public SynergyUser getUser() {
		return user;
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}
}

package usa.devrocoding.synergy.spigot.scoreboard;

import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.List;

public interface ScoreboardPolicy {

	List<String> getSidebar(SynergyUser user);

	String getPrefix(SynergyUser perspective, SynergyUser subject);

	String getSuffix(SynergyUser perspective, SynergyUser subject);

	String getUndername(SynergyUser User);

	int getUndernameScore(SynergyUser perspective, SynergyUser subject);

	String getTablist(SynergyUser User);

	int getTablistScore(SynergyUser perspective, SynergyUser subject);
}

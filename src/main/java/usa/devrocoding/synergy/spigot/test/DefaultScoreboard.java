package usa.devrocoding.synergy.spigot.test;

import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardPolicy;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.Arrays;
import java.util.List;

public class DefaultScoreboard implements ScoreboardPolicy {

    @Override
    public List<String> getSidebar(SynergyUser user) {
        return Arrays.asList(
                " ",
                ChatColor.WHITE + "Rank: " + (user.getRank().equals(Rank.NONE) ? ChatColor.GRAY + "None" : user.getRank().getPrefix()),
                "  ");
    }

    @Override
    public String getPrefix(SynergyUser perspective, SynergyUser subject) {
        if (subject.getRank().getId() == Rank.NONE.getId()) {
            return subject.getRank().getPrefix();
        }

        return subject.getRank().getPrefix() + " ";
    }

    @Override
    public String getSuffix(SynergyUser perspective, SynergyUser subject) {
        return null;
    }

    @Override
    public String getUndername(SynergyUser user) {
        return null;
    }

    @Override
    public int getUndernameScore(SynergyUser perspective, SynergyUser subject) {
        return 0;
    }

    @Override
    public String getTablist(SynergyUser user) {
        return null;
    }

    @Override
    public int getTablistScore(SynergyUser perspective, SynergyUser subject) {
        return 0;
    }
}


package usa.devrocoding.synergy.spigot.achievement;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.achievement.achievements.ACHIEVEMENT_FIRST_JOINED;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.util.*;

public class AchievementManager extends Module {

    @Getter
    private Map<SynergyUser, List<Achievement>> achievements = new HashMap<>();
    @Getter
    private List<Achievement> availableAchievements = new ArrayList<>();

    public AchievementManager(Core plugin){
        super(plugin, "Achievement Manager", false);

        registerAchievements(
            new ACHIEVEMENT_FIRST_JOINED()
        );
    }

    @Override
    public void reload(String response) {

    }

    public void registerAchievements(Achievement... achievements){
        Arrays.stream(achievements).forEach(
            achievement -> {
                if (!getAvailableAchievements().contains(achievement)){
                    getAvailableAchievements().add(achievement);
                }
            }
        );
    }
}

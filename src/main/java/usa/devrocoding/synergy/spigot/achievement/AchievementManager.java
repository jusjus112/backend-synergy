package usa.devrocoding.synergy.spigot.achievement;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.achievement.achievements.*;
import usa.devrocoding.synergy.spigot.achievement.command.CommandAchievement;
import usa.devrocoding.synergy.spigot.achievement.listener.PlayerFinishedAchievementListener;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.util.*;

public class AchievementManager extends Module {

    @Getter
    private List<Achievement> availableAchievements = new ArrayList<>();

    public AchievementManager(Core plugin){
        super(plugin, "Achievement Manager", false);

        registerAchievements(
            new ACHIEVEMENT_FIRST_JOINED()
        );

        registerCommand(
                new CommandAchievement(getPlugin())
        );

        registerListener(
                new PlayerFinishedAchievementListener()
        );
    }

    @Override
    public void reload(String response) {

    }

    public GuiElement getElement(SynergyUser user, Achievement achievement){
        final Material material = user.hasAchievement(achievement)
                ? Material.MAGMA_CREAM // Achieved
                : Material.ENDER_PEARL; // Not achieved
        final ChatColor color = user.hasAchievement(achievement)
                ? ChatColor.GREEN // Achieved
                : ChatColor.RED; // Not achieved
        final String availability = user.hasAchievement(achievement)
                ? color+""+ChatColor.BOLD+"Achieved".toUpperCase()+" §eon 03-29-2019" // Achieved
                : color+""+ChatColor.BOLD+"Not Achieved Yet".toUpperCase(); // Not achieved
        return new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(material)
                        .setName(color+""+ChatColor.BOLD+achievement.getName())
                        .addLore(
                                " ",
                                "§6Unlock this by:"
                        )
                        .addLore(
                                achievement.getDescription()
                        )
                        .addLore(
                                " ",
                                "§eAnd get §6§l"+achievement.getRewardExperience()+" §eNetwork XP",
                                " ",
                                availability
                        )
                        .build();
            }

            @Override
            public void click(SynergyUser player, ClickType clickType) {

            }
        };
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

package usa.devrocoding.synergy.spigot.achievement;

import com.google.common.collect.Maps;
import java.sql.Timestamp;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.achievement.command.CommandAchievement;
import usa.devrocoding.synergy.spigot.achievement.listener.PlayerFinishedAchievementListener;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AchievementManager extends Module {

    @Getter
    private final List<Achievement> availableAchievements = new ArrayList<>();

    public AchievementManager(Core plugin){
        super(plugin, "Achievement Manager", false);

        new TableBuilder("achievements", getPlugin().getDatabaseManager())
                .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("achievement", SQLDataType.VARCHAR, 100, false, SQLDefaultType.NO_DEFAULT, false)
                .addColumn("achieved_on", SQLDataType.DATETIME, -1, false, SQLDefaultType.NO_DEFAULT, false)
                .setConstraints("uuid", "achievement")
            .execute();

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
                        "§6Description:"
                    )
                    .addLore(achievement.getDescription())
                    .addLore(" ", "§7§lRewards:")
                    .addLore(achievement.rewards(synergyUser))
                    .addLore(" ", availability)
                    .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

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

    public void saveAchievementsToDatabase(SynergyUser synergyUser){
        getPlugin().getRunnableManager().runTaskAsynchronously(
            "Save Achievements",
            core -> {
                synergyUser.getAchievementsToBeUpdated().forEach((achievement, date) -> {
                    HashMap<String, Object> data = new HashMap<String, Object>() {{
                        put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                        put("achievement", achievement.toString());
                        put("achieved_on", java.sql.Timestamp.from(date.toInstant()));
                    }};
                    getPlugin().getDatabaseManager().insert("achievements", data);
                });
                synergyUser.getAchievementsToBeUpdated().clear();
            }
        );
    }

    public Map<Achievement, Timestamp> retrieveAchievements(UUID uuid){
        Map<Achievement, Timestamp> achievements = Maps.newHashMap();
        getPlugin().getRunnableManager().runTaskAsynchronously(
                "Retrieve Achievements",
                core -> {
                    Map<Integer, Object> data = new HashMap<Integer, Object>(){{
                        put(1, UtilSQL.convertUniqueId(uuid));
                    }};
                    try{
                        ResultSet result = getPlugin().getDatabaseManager()
                            .getResults("achievements", "uuid=?", data);
                        while (result.next()){
                            for(Achievement achievement : getAvailableAchievements()){
                                if (achievement.getClass().getSimpleName().toUpperCase().equalsIgnoreCase(result.getString("achievement"))){
                                    achievements.put(achievement, result.getTimestamp("achieved_on"));
                                    break;
                                }
                            }
                        }
                    }catch (SQLException e){
                        Synergy.error("SQLError Achievements: "+e.getMessage());
                        Sam.getRobot().error(this, e);
                    }
                }
        );
        return achievements;
    }
}

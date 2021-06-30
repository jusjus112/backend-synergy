package usa.devrocoding.synergy.spigot.objectives;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.gui.Gui;
import usa.devrocoding.synergy.spigot.gui.GuiElement;
import usa.devrocoding.synergy.spigot.objectives.command.CommandObjectives;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;

public class ObjectiveManager extends Module {

    @Getter
    private final List<Objective> availableObjectives;

    public ObjectiveManager(Core plugin){
        super(plugin, "Objective Manager", false);

        this.availableObjectives = Lists.newArrayList();

        new TableBuilder("objectives", getPlugin().getDatabaseManager())
            .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("objective", SQLDataType.VARCHAR, 100, false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("solved_on", SQLDataType.DATETIME, -1, true, SQLDefaultType.NO_DEFAULT, false)
            .setConstraints("uuid", "objective")
        .execute();

        registerCommand(
            new CommandObjectives(plugin)
        );
    }

    @Override
    public void onServerLoad() {
        new BukkitRunnable(){
            @Override
            public void run() {
                availableObjectives.forEach(Objective::register);
            }
        }.runTaskLater(getPlugin(), 20L * 3);
    }

    @Override
    public void onReload(String s) {

    }

    public void startMostRecentObjective(SynergyUser synergyUser){
        Synergy.debug("STARTED MOST RECENT OBJECTIVE 2");
        for (Objective availableObjective : this.availableObjectives) {
            Synergy.debug(availableObjective.name() + " = STARTED MOST RECENT OBJECTIVE 3");
            if (availableObjective.isAbletoStart(synergyUser)){
                Synergy.debug("STARTED MOST RECENT OBJECTIVE 4");
                synergyUser.setCurrentObjective(availableObjective);
                break;
            }
        }
    }

    public Objective getObjectiveFromClass(@Nullable Class<? extends Objective> clazz){
        Optional<Objective> optionalObjective = this.availableObjectives.stream()
            .filter(objective -> objective.getClass() == clazz)
            .findFirst();

        Synergy.debug(optionalObjective + " = OPTIONAL");
        Synergy.debug(optionalObjective.isPresent() + " = OPTIONAL 2");
        return optionalObjective.orElse(null);
    }

    public void registerObjective(Objective objective){
        if (!this.availableObjectives.contains(objective)){
            this.availableObjectives.add(objective);
        }
    }

    public void saveObjectivesToDatabase(SynergyUser synergyUser){
        getPlugin().getRunnableManager().runTaskAsynchronously(
            "Save Objectives",
            core -> {
                synergyUser.getObjectivesToBeUpdated().forEach((objective, date) -> {
                    HashMap<String, Object> data = new HashMap<String, Object>() {{
                        put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                        put("objective", objective.getClass().getSimpleName().toUpperCase());
                        put("solved_on", java.sql.Timestamp.from(date.toInstant()));
                    }};
                    getPlugin().getDatabaseManager().insert("objectives", data);
                });
                synergyUser.getObjectivesToBeUpdated().clear();
            }
        );
    }

    public GuiElement getElement(SynergyUser user, Objective objective){
        final Material material = user.hasObjective(objective)
            ? Material.MAGMA_CREAM // Unlocked
            : Material.ENDER_PEARL; // Not Unlocked
        final ChatColor color = user.hasObjective(objective)
            ? ChatColor.GREEN // Unlocked
            : ChatColor.RED; // Not Unlocked

        final String availability = user.hasObjective(objective)
            ? color+""+ChatColor.BOLD+"Unlocked".toUpperCase()+" §eon " + user.getObjectives().get(objective).toString() // Unlocked
            : color+""+ChatColor.BOLD+"Not Unlocked Yet".toUpperCase(); // Not Unlocked
        return new GuiElement() {
            @Override
            public ItemStack getIcon(SynergyUser synergyUser) {
                return new ItemBuilder(material)
                    .setName(color+""+ChatColor.BOLD+objective.name())
                    .addLore(
                        " ",
                        "§eDescription:"
                    )
                    .addLore(
                        objective.description()
                    )
                    .addLore(
                        " ",
                        "§eReward(s):"
                    )
                    .addLore(
                        objective.rewards()
                    )
                    .addLore(
                        " ",
                        availability
                    )
                    .build();
            }

            @Override
            public void click(SynergyUser synergyUser, ClickType clickType, Gui gui) {

            }
        };
    }

    public Map<Objective, Timestamp> retrieveForPlayer(UUID uuid) throws SQLException {
        Map<Objective, Timestamp> objectives = Maps.newHashMap();

        ResultSet result = Core.getPlugin().getDatabaseManager().getResults(
            "objectives ", "uuid=?", new HashMap<Integer, Object>(){{
                put(1, UtilSQL.convertUniqueId(uuid));
            }}
        );

        Synergy.debug("retrieveForPlayer OBJECTIVES FOR PLAYER");
        while (result.next()){
            Synergy.debug(this.availableObjectives.size() + " = retrieveForPlayer OBJECTIVES FOR PLAYER");
            this.availableObjectives.iterator().forEachRemaining(objective -> {
                try {
                    Synergy.debug(objective.getClass().getSimpleName().toUpperCase() + " = OBJECTIVE CLASS");
                    Synergy.debug(result.getString("objective") + " = OBJECTIVE!!!!!!!!!");
                    if (objective.getClass().getSimpleName().toUpperCase()
                        .equalsIgnoreCase(result.getString("objective"))){
                        objectives.put(objective, result.getTimestamp("solved_on"));
                        Synergy.debug(result.getString("objective") + " = OBJECTIVE");
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
        }

        return objectives;
    }

}

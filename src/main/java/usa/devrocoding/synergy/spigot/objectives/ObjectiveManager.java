package usa.devrocoding.synergy.spigot.objectives;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.objectives.object.Objective;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class ObjectiveManager extends Module {

    @Getter
    private List<Objective> availableObjectives;

    public ObjectiveManager(Core plugin){
        super(plugin, "Objective Manager", false);

        this.availableObjectives = Lists.newArrayList();

        new TableBuilder("objectives", getPlugin().getDatabaseManager())
            .addColumn("uuid", SQLDataType.VARCHAR, 200, false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("objective", SQLDataType.VARCHAR, 100, false, SQLDefaultType.NO_DEFAULT, false)
            .addColumn("solved_on", SQLDataType.DATE, -1, true, SQLDefaultType.NO_DEFAULT, false)
        .execute();

        registerObjective(new TestObjective());
    }

    @Override
    public void reload(String s) {

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
                        put("uuid", synergyUser.getUuid().toString());
                        put("objective", objective.getClass().getSimpleName().toUpperCase());
                        put("solved_on", java.sql.Date.from(date.toInstant()));
                    }};
                    getPlugin().getDatabaseManager().insert("objectives", data);
                });
                synergyUser.getObjectivesToBeUpdated().clear();
            }
        );
    }

    public Map<Objective, Date> retrieveForPlayer(UUID uuid) throws SQLException {
        Map<Objective, Date> objectives = Maps.newHashMap();

        ResultSet result = Core.getPlugin().getDatabaseManager().getResults(
            "objectives ", "uuid=?", new HashMap<Integer, Object>(){{
                put(1, uuid.toString());
            }}
        );

        while (result.next()){
            for(Objective objective : this.availableObjectives){
                if (objective.getClass().getSimpleName().toUpperCase().equalsIgnoreCase(result.getString("objective"))){
                    objectives.put(objective, result.getTimestamp("solved_on"));
                    break;
                }
            }
        }

        return objectives;
    }

}

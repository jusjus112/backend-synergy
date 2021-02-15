package usa.devrocoding.synergy.spigot.economy;

import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.economy.command.CommandEconomy;
import usa.devrocoding.synergy.spigot.economy.object.Economy;
import usa.devrocoding.synergy.spigot.statistics.object.Statistic;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class EconomyManager extends Module {

    public EconomyManager(Core plugin){
        super(plugin, "Economy Manager", false);

        new TableBuilder("economy", plugin.getDatabaseManager())
            .addColumn("uuid", SQLDataType.BINARY, 16, false, SQLDefaultType.NO_DEFAULT, true)
            .addColumn("xp", SQLDataType.BIGINT, -1, false, SQLDefaultType.CUSTOM.setCustom(0), false)
            .addColumn("coins", SQLDataType.DECIMAL, -1, false, SQLDefaultType.CUSTOM.setCustom(0D), false)
            .addColumn("shards", SQLDataType.INTEGER, -1, false, SQLDefaultType.CUSTOM.setCustom(0), false)
            .execute();

        registerCommand(
            new CommandEconomy(getPlugin())
        );
    }

    @Override
    public void reload(String response) {

    }

    public Economy retrieveEconomy(UUID uuid){
        BigDecimal coins = new BigDecimal(0);
        int shards = 0;
        long experience = 0L;

        try{
            ResultSet result = Core.getPlugin().getDatabaseManager().getResults(
                "economy ", "uuid=?", new HashMap<Integer, Object>(){{
                    put(1, UtilSQL.convertUniqueId(uuid));
                }}
            );

            while (result.next()){
                coins = result.getBigDecimal("coins");
                shards = result.getInt("shards");
                experience = result.getLong("xp");
            }

        }catch (Exception ignored){}
        return new Economy(coins, shards, experience);
    }

    public void updateEconomyForUser(SynergyUser synergyUser){
        getPlugin().getRunnableManager().runTaskAsynchronously("Update User Economy", core -> {
            Economy economy = synergyUser.getEconomy();

            if (economy.getShards() <= 0 && economy.getCoins() <= 0 && economy.getExperience() <= 0){
                return;
            }

            Synergy.debug("UPDATING ECONOMY 1");
            HashMap<String, Object> data = new HashMap<String, Object>() {{
                put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                put("coins", economy.getCoinsDecimal());
                put("shards", economy.getShards());
                put("xp", economy.getExperience());
            }};

            Synergy.debug("UPDATING ECONOMY 2");
            if (!getPlugin().getDatabaseManager().insert("economy", data)) {
                Synergy.debug("UPDATING ECONOMY 3");
                Synergy.debug(data + " = UPDATING ECONOMY 4");
                Core.getPlugin().getDatabaseManager().update(
                    "economy",
                    data,
                    new HashMap<String, Object>() {{
                        put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
                    }}
                );
            }
        });
    }

}

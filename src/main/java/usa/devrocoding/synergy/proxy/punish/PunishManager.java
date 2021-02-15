package usa.devrocoding.synergy.proxy.punish;

import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.punish.PunishCategory;
import usa.devrocoding.synergy.spigot.punish.PunishLevel;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PunishManager extends ProxyModule {

    public PunishManager(Core plugin){
        super(plugin, "Punish Manager", false);
    }

    @Override
    public void reload() {

    }

    @Override
    public void deinit() {

    }

    public List<Punishment> retrievePunishments(UUID uuid){
        List<Punishment> punishments = new ArrayList<>();

        try{
            ResultSet result = getPlugin().getDatabaseManager().getResults(
                    "punishments ", "uuid=?", new HashMap<Integer, Object>(){{
                        put(1, UtilSQL.convertUniqueId(uuid));
                    }}
            );
            boolean update = false;

            while (result.next()){
                boolean active = result.getBoolean("active");
                Long till = Long.valueOf(result.getString("till"));
                if (active){
                    if (!(till < 0L) && till <= System.currentTimeMillis()){
                        active = false;
                        update = true;
                    }
                }

                punishments.add(new Punishment(
                        uuid,
                        PunishType.valueOf(result.getString("type")),
                        PunishCategory.valueOf(result.getString("category")),
                        PunishLevel.valueOf(result.getString("level")),
                        Long.parseLong(result.getString("issued")),
                        till,
                        UtilSQL.convertBinaryStream(result.getBinaryStream("punisher")),
                        active
                ));
            }
            if (update){
                updatePunishments(punishments);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return punishments;
    }

    public void updatePunishments(List<Punishment> punishments){
        for(Punishment punishment : punishments) {
            updatePunishment(punishment);
        }
    }

    public void updatePunishment(Punishment punishment){
        PunishManager.this.getPlugin().getDatabaseManager().update(
            "Update Punishments Player",
            new HashMap<String, Object>() {{
                put("uuid", UtilSQL.convertUniqueId(punishment.getPunished()));
                put("type", punishment.getType().name());
                put("category", punishment.getCategory().name());
                put("level", punishment.getLevel().name());
                put("issued", String.valueOf(punishment.getIssued()));
                put("till", String.valueOf(punishment.getTill()));
                put("punisher", UtilSQL.convertUniqueId(punishment.getPunisher()));
                put("active", punishment.isActive());
            }},
            new HashMap<String, Object>() {{
                put("uuid", UtilSQL.convertUniqueId(punishment.getPunished()));
                put("issued", punishment.getIssued());
            }}
        );
    }
}

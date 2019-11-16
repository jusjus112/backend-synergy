package usa.devrocoding.synergy.proxy.punish;

import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.punish.listener.UserLoginListener;
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

        registerListeners(
                new UserLoginListener()
        );
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
                        put(1, uuid.toString());
                    }}
            );
            boolean update = false;
            System.out.println(result);
            while (result.next()){
                boolean active = result.getBoolean("active");
                Long till = Long.valueOf(result.getString("till"));
                if (active){
                    if (till <= System.currentTimeMillis()){
                        active = false;
                        update = true;
                    }
                }

                punishments.add(new Punishment(
                        uuid,
                        PunishType.valueOf(result.getString("type")),
                        PunishCategory.valueOf(result.getString("category")),
                        PunishLevel.valueOf(result.getString("level")),
                        Long.valueOf(result.getString("issued")),
                        till,
                        UUID.fromString(result.getString("punisher")),
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
        PunishManager.this.getPlugin().getDatabaseManager().update("punishments", new HashMap<String, Object>() {{
            put("uuid", punishment.getPunished().toString());
            put("type", punishment.getType().name());
            put("category", punishment.getCategory().name());
            put("level", punishment.getLevel().name());
            put("issued", String.valueOf(punishment.getIssued()));
            put("till", String.valueOf(punishment.getTill()));
            put("punisher", punishment.getPunisher().toString());
            put("active", punishment.isActive());
        }}, "uuid = '" + punishment.getPunished().toString() + "' AND issued = '" + punishment.getIssued() + "'");
    }
}

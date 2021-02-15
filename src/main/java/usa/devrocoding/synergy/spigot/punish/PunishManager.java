package usa.devrocoding.synergy.spigot.punish;

import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.assets.PluginMessageBuilder;
import usa.devrocoding.synergy.assets.PluginMessageType;
import usa.devrocoding.synergy.spigot.punish.command.CommandPunish;
import usa.devrocoding.synergy.spigot.punish.listeners.MuteListener;
import usa.devrocoding.synergy.spigot.punish.listeners.SynergyReceiveListener;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PunishManager extends Module {

    public PunishManager(Core plugin){
        super(plugin, "Punish Manager", true);

        registerListener(
                new SynergyReceiveListener(),
                new MuteListener()
        );

        registerCommand(
                new CommandPunish(getPlugin())
        );
    }

    @Override
    public void reload(String response) {

    }

    public void punish(SynergyUser user, SynergyUser punisher, PunishIcon icon){
        punish(user, punisher, getPunishment(user.getUuid(), punisher.getUuid(), icon ));
    }

    private Punishment getPunishment(UUID punished, UUID punisher, PunishIcon icon) {
        long till = System.currentTimeMillis() + (icon.getPunishTime());
        if (icon.getPunishTime() == -1L) {
            till = -1L;
        }
        Punishment punishment = new Punishment(punished, icon.getType(), icon.getCategory(), icon.getPunishLevel(),
                System.currentTimeMillis(), till, punisher, true);
        return punishment;
    }

    public void punish(SynergyUser user, SynergyUser punisher, Punishment punishment){
        getPlugin().getRunnableManager().runTaskAsynchronously("Punish Player", core -> {
            core.getDatabaseManager().insert("punishments", new HashMap<String, Object>() {{
                put("uuid", UtilSQL.convertUniqueId(punishment.getPunished()));
                put("type", punishment.getType().name());
                put("category", punishment.getCategory().name());
                put("level", punishment.getLevel().name());
                put("issued", String.valueOf(punishment.getIssued()));
                put("till", String.valueOf(punishment.getTill()));
                put("punisher", UtilSQL.convertUniqueId(punishment.getPunisher()));
                put("active", punishment.isActive());
            }});
            Synergy.debug("PUNISHMENT ACTIVE - "+punishment.isActive());
            for(SynergyUser synergyUser : getPlugin().getUserManager().getOnlineUsers()){
                if (synergyUser.getUuid().equals(punishment.getPunished())){
                    synergyUser.getPunishments().add(punishment);
                    takeAction(user, punisher, punishment);
                    return;
                }
            }
            takeAction(user, punisher, punishment);
        });

    }

    public void takeAction(SynergyUser user, SynergyUser punisher, Punishment punishment){
        switch (punishment.getType()){
            case BAN:
                new PluginMessageBuilder(PluginMessageType.ACTION)
                        .target(user.getUuid())
                        .message("UPDATE_PUNISHMENTS")
                        .send();
                new PluginMessageBuilder(PluginMessageType.STAFF_MESSAGE)
                        .target(user.getUuid())
                        .prefix("§c§l[STAFF]:§r ")
                        .message(user.getName()+" has been banned by §e"+punisher.getName())
                        .send();
                break;
            case MUTE:
                new PluginMessageBuilder(PluginMessageType.PLAYER_MESSAGE)
                        .target(user.getUuid())
                        .message("You have been muted by "+punisher.getName())
                        .send();
                break;
            case WARNING:
                new PluginMessageBuilder(PluginMessageType.PLAYER_MESSAGE)
                        .target(user.getUuid())
                        .prefix(true)
                        .message(
                            "You have been warned by §e"+punisher.getName(),
                            "When exceeding 3+ warnings, you will be banned temporarily."
                        )
                        .send();
                new PluginMessageBuilder(PluginMessageType.STAFF_MESSAGE)
                        .target(user.getUuid())
                        .prefix("§c§l[STAFF]:§r ")
                        .message(user.getName()+" has been warned by §c"+punisher.getName())
                        .send();
                break;
            case KICK:
                user.kick(punishment.getBanMessage("MiragePrisons"));
        }
    }

    public void updatePunishments(List<Punishment> punishments){
        for(Punishment punishment : punishments) {
            updatePunishment(punishment);
        }
    }

    public void updatePunishment(Punishment punishment){
        getPlugin().getRunnableManager().runTaskAsynchronously("Update Punishments Player", core -> {
            core.getDatabaseManager().update(
                "punishments",
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

            Synergy.debug("PUNISHMENT ACTIVE 2 - "+punishment.isActive());
        });
    }

    public List<Punishment> retrievePunishments(UUID uuid){
        List<Punishment> punishments = new ArrayList<>();

        try{
            Synergy.debug("1 - PUNISHMENTS");
            Synergy.debug(uuid.toString() + " = 1 - PUNISHMENTS");
            ResultSet result = Core.getPlugin().getDatabaseManager().getResults(
                    "punishments ", "uuid=?", new HashMap<Integer, Object>(){{
                        put(1, UtilSQL.convertUniqueId(uuid));
                    }}
            );
            boolean update = false;
            Synergy.debug("2 - PUNISHMENTS");
            Synergy.debug(result.isClosed() + " = 2 - PUNISHMENTS");
            while (result.next()){
                Synergy.debug("3 - PUNISHMENTS");
                boolean active = result.getBoolean("active");
                long till = Long.parseLong(result.getString("till"));

                if (active){
                    if (!(till < 0L) && till <= System.currentTimeMillis()){
                        active = false;
                        update = true;
                    }
                }
                Synergy.debug("4 - PUNISHMENTS");
                Synergy.debug("PUNISHMENTS = "+result.getString("issued"));

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
            Synergy.debug("5 - PUNISHMENTS");
            if (update){
                Core.getPlugin().getPunishManager().updatePunishments(punishments);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return punishments;
    }
}

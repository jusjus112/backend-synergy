package usa.devrocoding.synergy.proxy.user.object;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.event.LoginEvent;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProxyUser {

    @Getter
    private UUID uuid;
    @Getter @Setter
    private List<Punishment> punishments = new ArrayList<>();

    public ProxyUser(UUID uuid){
        this.uuid = uuid;

//        Core.getCore().getUserManager().getProxyUsers().put(this.uuid, this);
    }

    public void checkPunishments(LoginEvent e){
        for (Punishment punishment : this.punishments) {
            if (punishment.getType() == PunishType.BAN && punishment.isActive()) {
                e.setCancelReason(punishment.getBanMessage());
                e.setCancelled(true);
                e.completeIntent(Core.getCore());
                return;
            }
        }
        e.completeIntent(Core.getCore());
    }

}

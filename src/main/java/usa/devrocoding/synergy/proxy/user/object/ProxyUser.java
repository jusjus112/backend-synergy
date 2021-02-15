package usa.devrocoding.synergy.proxy.user.object;

import com.google.common.collect.Lists;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import usa.devrocoding.synergy.assets.Rank;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.punish.object.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class ProxyUser {

    private final UUID uuid;
    @Setter
    private List<Punishment> punishments = new ArrayList<>();
    private final Rank rank;
    @Setter
    private List<UUID> friends;

    public ProxyUser(UUID uuid, Rank rank){
        this.uuid = uuid;
        this.rank = rank;
        this.friends = Lists.newArrayList();

        Core.getCore().getUserManager().getProxyUsers().put(this.uuid, this);
    }

    public ProxiedPlayer getProxiedPlayer(){
        return Core.getCore().getProxy().getPlayer(this.uuid);
    }

    public void checkPunishments(LoginEvent e){
        for (Punishment punishment : this.punishments) {
            if (punishment.getType() == PunishType.BAN && punishment.isActive()) {
                e.setCancelReason(punishment.getBanMessage("MiragePrisons"));
                e.setCancelled(true);
                e.completeIntent(Core.getCore());
                return;
            }
        }
        e.completeIntent(Core.getCore());
    }

    public String getDisplayName(){
        if (this.rank == Rank.NONE){
            return this.rank.getTextColor() + this.getProxiedPlayer().getDisplayName();
        }
        return this.rank.getPrefix() + " " + this.rank.getTextColor() + this.getProxiedPlayer().getDisplayName();
    }

    public final String prefix = "§d§l</> "+ ChatColor.RESET;
    public void sendMessage(String... messages){
        ProxiedPlayer proxiedPlayer = getProxiedPlayer();
        Arrays.stream(messages).forEach(s -> proxiedPlayer.sendMessage(prefix + s));
    }

}

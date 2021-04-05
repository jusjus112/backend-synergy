package usa.devrocoding.synergy.proxy.user.object;

import com.google.common.collect.Lists;
import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import usa.devrocoding.synergy.assets.Pair;
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
    private ProxyUser lastMessageReceived = null;
    @Setter
    private List<Pair<UUID, String>> friends;
    private final String name;

    public ProxyUser(UUID uuid, Rank rank, String name){
        this.uuid = uuid;
        this.rank = rank;
        this.name = name;
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

    public boolean canHaveMoreFriends(){
        int max;
        switch (getRank()){
            case SORCERER:
                max = 5;
            case MAGE:
                max = 10;
                break;
            case WIZARD:
                max = 15;
                break;
            case IMMORTAL:
                max = 20;
                break;
            case MIRAGE:
                max = 999;
                break;
            default:
                max = 3;
        }
        return getFriends().size() <= max;
    }

    public boolean hasFriend(ProxyUser proxyUser){
        return getFriends().stream().anyMatch(uuidStringPair ->
            uuidStringPair.getLeft().equals(proxyUser.getUuid()));
    }

    public String getDisplayName(){
//        if (this.rank == Rank.NONE){
//            return this.rank.getTextColor() + this.getProxiedPlayer().getDisplayName();
//        }
//        return this.rank.getPrefix() + " " + this.rank.getTextColor() + this.getProxiedPlayer().getDisplayName();
        return this.getProxiedPlayer().getDisplayName();
    }

    public final String prefix = "§d§l</> "+ ChatColor.RESET;
    public void sendMessage(String... messages){
        ProxiedPlayer proxiedPlayer = getProxiedPlayer();
        Arrays.stream(messages).forEach(s -> proxiedPlayer.sendMessage(prefix + s));
    }

}

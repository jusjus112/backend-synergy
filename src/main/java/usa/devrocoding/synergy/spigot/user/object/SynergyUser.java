package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.assets.C;

import java.util.Arrays;
import java.util.UUID;

public class SynergyUser {

    @Getter
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private Rank rank;
    @Getter
    private String ip;

    public SynergyUser(UUID uuid, String name, Rank rank, String ip){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.ip = ip;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public void message(String... messages){
        Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(C.translateColors(s)));
    }

}
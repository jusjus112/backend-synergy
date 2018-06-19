package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.language.Language;

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
    @Getter
    private Language language;
    @Getter @Setter
    private UserExperience userExperience;

    public SynergyUser(UUID uuid, String name, Rank rank, String ip, Language language){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.ip = ip;
        this.language = language;

        Core.getPlugin().getUserManager().getUsers().put(uuid, this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public void message(String... messages){
        Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(C.translateColors(s)));
    }

}
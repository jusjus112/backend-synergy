package usa.devrocoding.synergy.spigot.user.object;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.achievement.object.Achievement;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.bot_sam.object.SamMessage;
import usa.devrocoding.synergy.spigot.language.LanguageFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SynergyUser {

    @Getter
    private UUID uuid;
    @Getter
    private String name;
    @Getter
    private Rank rank;
    @Getter
    private LanguageFile language;
    @Getter @Setter
    private UserExperience userExperience = UserExperience.NOOB;
    @Getter @Setter
    private List<Achievement> achievements = new ArrayList<>();

    public SynergyUser(UUID uuid, String name, Rank rank, LanguageFile language){
        this.uuid = uuid;
        this.name = name;
        this.rank = rank;
        this.language = language;

        Core.getPlugin().getUserManager().getUsers().put(uuid, this);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(getUuid());
    }

    public void message(String... messages){
        Arrays.stream(messages).forEach(s -> getPlayer().sendMessage(C.translateColors(s)));
    }

    public void teleport(SynergyUser target){
        getPlayer().teleport(target.getPlayer());
    }

    public void info(String... messages){
        Sam.getRobot().info(getPlayer(), messages);
    }

    public void sam(SamMessage samMessage){
        Sam.getRobot().sam(getPlayer());
    }

    public void error(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }

    public void warning(String... messages){
        Sam.getRobot().warning(getPlayer(), messages);
    }
}
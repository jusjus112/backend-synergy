package usa.devrocoding.synergy.discord.object;

import lombok.Getter;
import net.dv8tion.jda.core.Permission;

public enum DiscordRank {

    NONE(Permission.MESSAGE_READ),
    ADMIN(Permission.ADMINISTRATOR);

    @Getter
    private Permission highestPermission;

    DiscordRank(Permission permission){
        this.highestPermission = permission;
    }

}

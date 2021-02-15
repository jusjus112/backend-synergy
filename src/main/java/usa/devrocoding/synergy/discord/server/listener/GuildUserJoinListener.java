package usa.devrocoding.synergy.discord.server.listener;

import java.util.Objects;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import usa.devrocoding.synergy.discord.assets.Codes;

public class GuildUserJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getGuild().getIdLong() == Long.parseLong(Codes.MIRAGE_PRISONS_DISCORD_ID)){
            event.getGuild().addRoleToMember(event.getMember(),
                Objects.requireNonNull(event.getGuild().getRoleById(Codes.PRISONER_ROLE_ID)))
                .queue();
        }else if (event.getGuild().getIdLong() == Long.parseLong(Codes.MIRAGE_PRISONS_STAFF_DISCORD_ID)){
            event.getGuild().addRoleToMember(event.getMember(),
                Objects.requireNonNull(event.getGuild().getRoleById(Codes.STAFF_NO_ROLE_ID)))
                .queue();
        }
    }
}

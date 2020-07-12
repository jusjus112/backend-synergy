package usa.devrocoding.synergy.discord.server.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUserJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getJDA().getRoleById("708890429625729045"))
                .queue();
    }
}

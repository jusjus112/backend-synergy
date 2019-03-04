package usa.devrocoding.synergy.discord.command.object;

import lombok.Getter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.object.DiscordRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DiscordCommand {

    @Getter
    private String description;
    @Getter
    private List<String> aliases;
    @Getter
    private DiscordRank rank;
    @Getter
    private String prefix;

    public DiscordCommand(String description, DiscordRank rank, String prefix, String... aliases){
        this.description = description;
        this.rank = rank;
        this.aliases = new ArrayList<>();
        this.prefix = prefix;

        Arrays.asList(aliases).forEach(cmd -> this.aliases.add(prefix+cmd));
    }

    public abstract void execute(Member member, MessageChannel channel, Message message);

}

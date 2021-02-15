package usa.devrocoding.synergy.discord.command.object;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import usa.devrocoding.synergy.discord.object.DiscordRank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class DiscordCommand {

    private String description;
    private List<String> aliases;
    private DiscordRank rank;
    private String prefix;
    @Setter
    private String usage;
    @Setter
    private boolean showInHelp = true;

    public DiscordCommand(String description, DiscordRank rank, String prefix, String... aliases){
        this.description = description;
        this.rank = rank;
        this.aliases = new ArrayList<>();
        this.prefix = prefix;

        Arrays.asList(aliases).forEach(cmd -> this.aliases.add(prefix+cmd));
    }

    public abstract void execute(Member member, MessageChannel channel,
        Message message, String rawContent, String[] args);

}

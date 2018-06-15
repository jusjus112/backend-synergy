package usa.devrocoding.synergy.spigot.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class SynergyCommand {

    @Getter
    private final Core plugin;
    @Getter
    private final Rank rank;
    @Getter
    private final String description;
    @Getter
    private final String[] aliases;
    private String[] usage;
    @Getter
    @Setter
    private double cooldown = 1.0;

    public SynergyCommand(Core plugin, String description, String... aliases) {
        this(plugin, Rank.NONE, description, aliases);
    }

    public SynergyCommand(Core plugin, Rank rank, String description, String... aliases) {
        this.plugin = plugin;
        this.rank = rank;
        this.description = description;
        this.aliases = aliases;
        this.usage = aliases;
    }

    public boolean checkArgs(String[] args) {
        if (usage.length > args.length) {
            return false;
        }

        return true;
    }

    public void setUsage(String... usage) {
        this.usage = usage;
    }

    public void couldNotFind(Player p, String thing, String attempt) {
        p.sendMessage(C.PRIMARY_MESSAGE + "Could not find " + thing + " " + C.MESSAGE_HIGHLIGHT + attempt + C.PRIMARY_MESSAGE + ".");
    }

    public abstract void execute(SynergyUser synergyUser, Player player, String[] args);

    public String getUsage() {
        String finalString = "";

        for (String string : usage) {
            finalString += string + " ";
        }

        return finalString;
    }

    public void sendUserMessage(SynergyUser user){

    }

    public void sendUsageMessage(Player player) {
        String usageString = "";

        if(usage.length != 0) {
            for(String s : usage)
                usageString+=s + " ";
        }

        player.sendMessage(C.PRIMARY_MESSAGE + "Usage: /" + aliases[0] + (usage.length != 0 ? " " + usageString.trim() : ""));
    }
}

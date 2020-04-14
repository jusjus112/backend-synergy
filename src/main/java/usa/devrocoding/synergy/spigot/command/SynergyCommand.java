package usa.devrocoding.synergy.spigot.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public abstract class SynergyCommand {

    @Getter
    private final Core plugin;
    @Getter
    private final Rank rank;
    @Getter
    private final boolean consoleAllowed;
    @Getter
    private final String description;
    @Getter
    private final String permission;
    @Getter
    private final String[] aliases;
    private String[] playerUsage, consoleUsage;
    @Getter
    @Setter
    private double cooldown = 1.0;

    public SynergyCommand(Core plugin, String description, boolean consoleAllowed, String... aliases) {
        this(plugin,null, Rank.NONE, description, consoleAllowed, aliases);
    }

    public SynergyCommand(Core plugin, String permission, String description, boolean consoleAllowed, String... aliases) {
        this(plugin, permission, Rank.NONE, description, consoleAllowed, aliases);
    }

    public SynergyCommand(Core plugin, Rank rank, String description, boolean consoleAllowed, String... aliases) {
        this(plugin, null, rank, description, consoleAllowed, aliases);
    }

    private SynergyCommand(Core plugin, String permission, Rank rank, String description, boolean consoleAllowed, String... aliases) {
        this.plugin = plugin;
        this.rank = rank;
        this.consoleAllowed = consoleAllowed;
        this.description = description;
        this.permission = permission;
        this.playerUsage = aliases;
        this.consoleUsage = aliases;

        String[] as = new String[aliases.length];
        for(int i=0;i<aliases.length;i++){
            as[i] = aliases[i].toLowerCase();
        }
        this.aliases = as;
    }

    public void setPlayerUsage(String... usage) {
        this.playerUsage = usage;
    }

    public void setConsoleUsage(String... usage) {
        this.consoleUsage = usage;
    }

    public void couldNotFind(Player p, String thing, String attempt) {
        p.sendMessage(
                     Message.format("command.player.not_found", null)
        );
//        p.sendMessage(C.PRIMARY + "Could not find " + thing + " " + C.MESSAGE_HIGHLIGHT + attempt + C.PRIMARY_MESSAGE + ".");
    }

    public abstract void execute(SynergyUser synergyUser, Player player, String command, String[] args);
    public abstract void execute(ConsoleCommandSender sender, String command, String[] args);

    public void sendUsageMessage(Player player) {
        String usageString = "";

        if(playerUsage.length != 0) {
            for(String s : playerUsage)
                usageString+=s + " ";
        }

        player.sendMessage(C.ERROR.getColor() + "Usage: /" + aliases[0] + (playerUsage.length != 0 ? " " + usageString.trim() : ""));
    }

    public void sendUsageMessage(ConsoleCommandSender console) {
        String usageString = "";

        if(consoleUsage.length != 0) {
            for(String s : consoleUsage)
                usageString+=s + " ";
        }

        console.sendMessage("Usage: /" + aliases[0] + (consoleUsage.length != 0 ? " " + usageString.trim() : ""));
    }
}

package usa.devrocoding.synergy.spigot.punish.command;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.command.SynergyCommand;
import usa.devrocoding.synergy.spigot.punish.gui.PunishGUI;
import usa.devrocoding.synergy.spigot.user.event.UserLoadEvent;
import usa.devrocoding.synergy.spigot.user.object.Rank;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class CommandPunish extends SynergyCommand {

    public CommandPunish(Core plugin){
        super(plugin, "command.punish", "Punish a player", true, "punish", "ban");

        setPlayerUsage("<player>");
    }

    @Override
    public void execute(SynergyUser synergyUser, Player player, String command, String[] args) {
        getPlugin().getRunnableManager().runTaskAsynchronously("open punish menu", core -> {
            if (args.length > 0 && args.length < 3){
                String name = args[0];
                SynergyUser target = getPlugin().getUserManager().getUser(name, true);
                if (target == null){
                    target = new SynergyUser(
                            Bukkit.getOfflinePlayer(name).getUniqueId(),
                            name,
                            Rank.NONE,
                            Core.getPlugin().getLanguageManager().getLanguage("en"),
                            UserLoadEvent.UserLoadType.NEW,
                            false
                    );
                }
                target.delete();
                if (target.getRank().getId() > synergyUser.getRank().getId()){
                    synergyUser.info("This user has a higher rank than you!");
                    return;
                }
                PunishGUI punishGUI = new PunishGUI(getPlugin(), target, synergyUser);
                getPlugin().getRunnableManager().runTask("open punish gui", core1 -> {
                    punishGUI.open(player);
                });
            }else{
                sendUsageMessage(player);
            }

        });
    }

    @Override
    public void execute(ConsoleCommandSender sender, String command, String[] args) {

    }
}

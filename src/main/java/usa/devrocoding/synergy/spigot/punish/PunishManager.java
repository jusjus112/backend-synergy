package usa.devrocoding.synergy.spigot.punish;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.punish.command.CommandPunish;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;

public class PunishManager extends Module {

    public PunishManager(Core plugin){
        super(plugin, "Punish Manager", true);

        registerCommand(
                new CommandPunish(getPlugin())
        );
    }

    @Override
    public void reload(String response) {

    }

    public void punish(SynergyUser user){

    }
}

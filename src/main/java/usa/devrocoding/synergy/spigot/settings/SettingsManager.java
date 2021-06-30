package usa.devrocoding.synergy.spigot.settings;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;

public class SettingsManager extends Module {

    public SettingsManager(Core plugin){
        super(plugin, "Settings Manager", false);
    }

    @Override
    public void onReload(String response) {

    }
}

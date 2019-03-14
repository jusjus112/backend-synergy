package usa.devrocoding.synergy.spigot.test;

import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.api.SynergyPluginMani;

@SynergyPluginMani(plugin_name = "Test Plugin", permission_prefix = "synergy.game.", plugin_color = ChatColor.DARK_AQUA)
public class TestPlugin extends SynergyPlugin {

    @Override
    public void init() {
        // Same as onEnable
    }

    @Override
    public void deinit() {
        // Same as onDisable
    }
}

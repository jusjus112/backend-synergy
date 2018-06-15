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

    @Override
    public void onPreReload() {
        // Called as very first when the plugin loads
    }

    @Override
    public void onReload() {
        // Called when the plugin is being reloaded and comes after all the files and important things
    }
}

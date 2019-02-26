package usa.devrocoding.synergy.proxy;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;

import java.util.Arrays;

public abstract class ProxyModule {

    @Getter
    private Core plugin;
    @Getter
    private String name;
    @Getter
    private boolean reloadable;

    public ProxyModule(Core plugin, String name, boolean reloadable){
        this.plugin = plugin;
        this.name = name;
        this.reloadable = reloadable;

        getPlugin().getProxyModules().add(this);
    }

    // Called when reloading the module
    public abstract void reload();
    public abstract void deinit();

    public String getShortName(){
        return this.name.split(" ")[0];
    }

    public void registerCommands(Command... commands){
        Arrays.asList(commands).forEach(
                command -> getPlugin().getProxy().getPluginManager().registerCommand(getPlugin(), command)
        );
    }

    public void registerListeners(Listener... listeners){
        Arrays.asList(listeners).forEach(
                listener -> getPlugin().getProxy().getPluginManager().registerListener(getPlugin(), listener)
        );
    }

}

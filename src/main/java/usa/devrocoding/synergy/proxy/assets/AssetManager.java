package usa.devrocoding.synergy.proxy.assets;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.assets.commands.CommandSynergyProxyReload;
import usa.devrocoding.synergy.proxy.maintenance.listener.ProxyPingListener;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;

import java.util.HashMap;
import java.util.Map;

public class AssetManager extends ProxyModule {

    @Getter
    private ProxyYMLFile ymlFile;
    @Getter
    private Map<String, String> discordChannels = new HashMap<>();

    public AssetManager(Core plugin){
        super(plugin, "Assets Manager", true);

        registerCommands(
                new CommandSynergyProxyReload()
        );
    }

    @Override
    public void reload() {
        loadDiscordSettings();
    }

    @Override
    public void deinit() {

    }

    public void loadDiscordSettings(){
        for(String server : getPlugin().getProxy().getServers().keySet()){
            if (this.ymlFile.getConfiguration().contains("discord.channels."+server)) {
                discordChannels.put(server, this.ymlFile.getConfiguration().getString("discord.channels." + server));
            }else{
                Synergy.error("Error loading discord settings for server "+server);
            }
        }
    }

    public void initDiscordSettings(){
        HashMap<String, Object> map = new HashMap<>();

        for(String server : getPlugin().getProxy().getServers().keySet()){
            map.put("discord.channels."+server, "0000000000000000");
        }

        map.put("sql.host", "127.0.0.1");
        map.put("sql.database", "database");
        map.put("sql.username", "username");
        map.put("sql.password", "password");
        map.put("sql.port", 3306);

        this.ymlFile = new ProxyYMLFile(getPlugin(), getPlugin().getDataFolder(), "settings");
        this.ymlFile.set(map);
    }

}

package usa.devrocoding.synergy.proxy.assets;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder;
import usa.devrocoding.synergy.discord.utilities.MessageBuilder.SetType;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.assets.commands.CommandSynergyProxyReload;
import usa.devrocoding.synergy.proxy.assets.listener.AutomatedReportsListener;
import usa.devrocoding.synergy.proxy.maintenance.listener.ProxyPingListener;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AssetManager extends ProxyModule {

    @Getter
    private ProxyYMLFile ymlFile;
    @Getter
    private final Map<String, String> discordChannels = new HashMap<>();
    @Getter
    private final List<String> offlineServers = new ArrayList<>();

    public AssetManager(Core plugin){
        super(plugin, "Assets Manager", true);

        registerListeners(
            new AutomatedReportsListener()
        );

        registerCommands(
                new CommandSynergyProxyReload()
        );

        initServerChecker();
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

        map.put("server.isProduction", false);

        this.ymlFile = new ProxyYMLFile(getPlugin(), getPlugin().getDataFolder(), "settings");
        this.ymlFile.set(map);
    }

    public void initServerChecker(){
        getPlugin().getProxy().getScheduler().schedule(getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(ServerInfo server : getPlugin().getProxy().getServers().values()){
                    server.ping((serverPing, throwable) -> {
                        if (throwable != null){
                            if (!AssetManager.this.offlineServers.contains(server.getName())) {
                                AssetManager.this.offlineServers.add(server.getName());
                                Synergy.error(server.getName()+" cannot be pinged! Might be offline!");
//                                Discord.getJda().getTextChannelById("732884673172078594").sendMessage(
//                                        new MessageBuilder(
//                                            "⚠⚠ WARNING | Server might be offline ⚠⚠",
//                                            SetType.TITLE
////                                                "@here, **"+server.getName().toUpperCase() + "** Is offline or doesn't respond to any pings!"
//                                        )
//                                        .addField(
//                                            "Server Affected",
//                                            server.getName() + " ("+server.getAddress().getPort()+")",
//                                            false
//                                        )
//                                        .addField(
//                                            "Reason of downtime",
//                                            throwable.getLocalizedMessage(),
//                                            false
//                                        )
//                                        .overwriteColor(Color.RED)
//                                        .build()
//                                ).queue();
                            }
                        }else{
                            if (AssetManager.this.offlineServers.contains(server.getName())) {
                                AssetManager.this.offlineServers.remove(server.getName());
                                Synergy.success(server.getName()+" is back online!");
//                                Discord.getJda().getTextChannelById("732884673172078594").sendMessage(
//                                        new MessageBuilder(
//                                            server.getName() + " has received our ping!",
//                                                server.getName() + "is back up and is responding to our pings."
//                                        )
//                                                .overwriteColor(Color.GREEN)
//                                                .build()
//                                ).queue();
                            }
                        }
                    });
                }
            }
        }, 1, 2, TimeUnit.SECONDS);


    }

}

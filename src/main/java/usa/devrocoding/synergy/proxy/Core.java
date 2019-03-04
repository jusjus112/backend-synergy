package usa.devrocoding.synergy.proxy;

import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.proxy.assets.AssetManager;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;
import usa.devrocoding.synergy.proxy.plugin_messaging.PluginMessaging;
import usa.devrocoding.synergy.proxy.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.user.object.UserExperience;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Core extends Plugin {

    @Getter
    public static Core core;

    @Getter
    private List<ProxyModule> proxyModules = new ArrayList<>();

    @Getter
    private AssetManager assetManager;
    @Getter
    private PluginMessaging pluginMessaging;
    @Getter
    private DatabaseManager databaseManager;
    @Getter
    private GoogleAuthManager googleAuthManager;
    @Getter
    private MaintenanceManager maintenanceManager;

    @Override
    public void onLoad() {
        core = this;
    }

    @Override
    public void onEnable() {
        // Load all the modules
        this.assetManager = new AssetManager(this);
        this.pluginMessaging = new PluginMessaging(this);
        this.maintenanceManager = new MaintenanceManager(this);

        // Load all the settings e.t.c.
        this.assetManager.initDiscordSettings();
        this.assetManager.loadDiscordSettings();

        this.maintenanceManager.loadData();

        // Load the sql Service so SQL can be used
        try{
            // Initialize SQL
            ProxyYMLFile f = this.getAssetManager().getYmlFile();

            this.databaseManager = new DatabaseManager(new SQLService(
                    f.getConfiguration().getString("sql.host"),
                    f.getConfiguration().getString("sql.database"),
                    f.getConfiguration().getString("sql.username"),
                    f.getConfiguration().getString("sql.password"),
                    f.getConfiguration().getInt("sql.port")));

            // Connect to SQL
            Synergy.info("Connecting to SQL....");
            this.databaseManager.connect();
            Synergy.info("Connected to your SQL Service Provider");

            // Generate Tables
            new TableBuilder("two_factor_authentication", this.databaseManager)
                    .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                    .addColumn("account_name", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .addColumn("key", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .execute();

        }catch (SQLException e){
            Synergy.error("I can't connect to your SQL Service provider");
            Synergy.error("Can't enable proxy without Synergy!");
            getProxy().stop();
            return;
        }
//        catch (ClassNotFoundException e){
//            Synergy.error("OMG, there is no SQL server installed on this proxy..... -_-");
//            Synergy.error("Can't enable proxy without Synergy!");
//            getProxy().stop();
//            return;
//        }

        this.googleAuthManager = new GoogleAuthManager(this);

        // Initialize the discord bot
        Discord.initTerminal();

        Synergy.info("Synergy Loaded");
    }

    @Override
    public void onDisable() {
        for(ProxyModule proxyModule : getProxyModules()){
            proxyModule.deinit();
        }
    }
}

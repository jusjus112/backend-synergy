package usa.devrocoding.synergy.proxy;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.discord.Discord;
import usa.devrocoding.synergy.proxy.announcements.AnnouncerManager;
import usa.devrocoding.synergy.proxy.assets.AssetManager;
import usa.devrocoding.synergy.proxy.buddy.BuddyManager;
import usa.devrocoding.synergy.proxy.files.ProxyYMLFile;
import usa.devrocoding.synergy.proxy.maintenance.MaintenanceManager;
import usa.devrocoding.synergy.proxy.party.PartyManager;
import usa.devrocoding.synergy.proxy.plugin_messaging.PluginMessaging;
import usa.devrocoding.synergy.proxy.punish.PunishManager;
import usa.devrocoding.synergy.proxy.suggest.SuggestManager;
import usa.devrocoding.synergy.proxy.user.UserManager;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Core extends Plugin {

    @Getter
    public static Core core;
    private final List<ProxyModule> proxyModules = new ArrayList<>();

    private AssetManager assetManager;
    private PluginMessaging pluginMessaging;
    private DatabaseManager databaseManager;
    private MaintenanceManager maintenanceManager;
    private PunishManager punishManager;
    private PartyManager partyManager;
    private UserManager userManager;
    private AnnouncerManager announcerManager;
    private SuggestManager suggestManager;
    private BuddyManager buddyManager;

    @Override
    public void onLoad() {
        core = this;
    }

    /**
     * TODO: Restart the proxy every XX hours
     */

    @Override
    public void onEnable() {
        Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> System.out.println(ChatColor.YELLOW +s));
        System.out.println("  ");

        this.assetManager = new AssetManager(this);
        // Load all the settings e.t.c.
        this.assetManager.initDiscordSettings();
        this.assetManager.loadDiscordSettings();

        this.maintenanceManager = new MaintenanceManager(this);
        this.maintenanceManager.loadData();

        ProxyYMLFile f = this.assetManager.getYmlFile();
        // Load the sql Service so SQL can be used
        try{
            // Initialize SQL

            this.databaseManager = new DatabaseManager(new SQLService(
                    f.getConfiguration().getString("sql.host"),
                    f.getConfiguration().getString("sql.database"),
                    f.getConfiguration().getInt("sql.port"),
                    f.getConfiguration().getString("sql.username"),
                    f.getConfiguration().getString("sql.password")
                ));

            // Connect to SQL
            Synergy.info("Connecting to SQL....");
            Synergy.info("Connected to your SQL Service Provider");

        }catch (Exception e){
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

        Synergy.setProduction(f.getConfiguration().getBoolean("server.isProduction"));

        // Load all the modules
        this.userManager = new UserManager(this);
        this.pluginMessaging = new PluginMessaging(this);
        this.punishManager = new PunishManager(this);
        this.partyManager = new PartyManager(this);
        this.announcerManager = new AnnouncerManager(this);
        this.suggestManager = new SuggestManager(this);
        this.buddyManager = new BuddyManager(this);

        // Initialize the discord bot
        if (Synergy.isProduction()) {
            Discord.initTerminal(f.getConfiguration().getString("discord.token"));
        }else{
            Synergy.warn("Synergy is in TEST mode! Production systems are not active!");
        }

//        this.assetManager.initServerChecker();

        Synergy.info("Synergy Loaded");
    }

    @Override
    public void onDisable() {
        for(ProxyModule proxyModule : getProxyModules()){
            proxyModule.deinit();
        }
    }
}

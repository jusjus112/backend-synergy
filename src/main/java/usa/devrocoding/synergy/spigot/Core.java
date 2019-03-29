package usa.devrocoding.synergy.spigot;

import com.google.inject.Injector;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.spigot.achievement.AchievementManager;
import usa.devrocoding.synergy.spigot.api.SpigotAPI;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.assets.*;
import usa.devrocoding.synergy.spigot.assets.lobby.LobbyManager;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.auto_reboot.AutoRebootManager;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.bot_sam.object.ErrorHandler;
import usa.devrocoding.synergy.spigot.changelog.ChangelogManager;
import usa.devrocoding.synergy.spigot.command.CommandManager;
import usa.devrocoding.synergy.spigot.discord.DiscordManager;
import usa.devrocoding.synergy.spigot.economy.EconomyManager;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.gui.GuiManager;
import usa.devrocoding.synergy.spigot.hologram.HologramManager;
import usa.devrocoding.synergy.spigot.language.LanguageManager;
import usa.devrocoding.synergy.spigot.nick.NickManager;
import usa.devrocoding.synergy.spigot.plugin_messaging.PluginMessagingManager;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
import usa.devrocoding.synergy.proxy.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.user.object.UserExperience;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;
import usa.devrocoding.synergy.spigot.version.VersionManager;
import usa.devrocoding.synergy.spigot.warp.WarpManager;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy", proxy = "BungeeCord", server_name = "NoblesseMC")
public class Core extends JavaPlugin {

    @Getter @Setter
    private static Core plugin;

    @Getter
    private boolean loaded = false,
                    disabled = false;

    @Getter
    public List<Module> modules = new ArrayList<>();

    @Getter
    private PluginManager pluginManager;
    @Getter
    private VersionManager versionManager;
    @Getter
    private AutoRebootManager autoRebootManager;
    @Getter
    private CommandManager commandManager;
    @Getter
    private RunnableManager runnableManager;
    @Getter
    private DatabaseManager databaseManager;
    @Getter
    private GuiManager GUIManager;
    @Getter
    private ScoreboardManager scoreboardManager;
    @Getter
    private UserManager userManager;
    @Getter
    private EconomyManager economyManager;
    @Getter
    private HologramManager hologramManager;
    @Getter
    private DiscordManager discordManager;
    @Getter
    private LanguageManager languageManager;
    @Getter
    private GlobalManager globalManager;
    @Getter
    private Message message;
    @Getter
    private ChangelogManager changelogManager;
    @Getter
    private PluginMessagingManager pluginMessagingManager;
    @Getter
    private CacheManager cacheManager;
    @Getter
    private WarpManager warpManager;
    @Getter
    private CooldownManager cooldownManager;
    @Getter
    private AchievementManager achievementManager;
    @Getter
    private NickManager nickManager;
    @Getter
    private DependencyManager dependencyManager;

    @Getter
    private LobbyManager lobbyManager = null;
    @Getter
    private SynergyMani manifest;

    @Override
    public void onEnable(){
        setPlugin(this);

        SynergyBinder binder = new SynergyBinder(this);
        Injector injector = binder.createInjector();
        injector.injectMembers(this);

        this.versionManager = new VersionManager(this);

        Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> getServer().getConsoleSender().sendMessage(C.PLUGIN+s));
        System.out.println("  ");

        // Init sam the robot
        new Sam();
        getLogger().addHandler(new ErrorHandler(Sam.getRobot()));

        this.runnableManager = new RunnableManager(this);

        this.languageManager = new LanguageManager(this);

        // Register Language Keys
//        this.languageManager.registerLanguages(LanguageStrings.values());

        this.pluginManager = new PluginManager(this);

        this.pluginManager.load();

        // Load the Manifest so other it can be used in other classes
        this.manifest = this.getClass().getAnnotation(SynergyMani.class);

        // Load the sql Service so SQL can be used
        try{
            // Initialize SQL
            YMLFile f = getPluginManager().getFileStructure().getYMLFile("settings");
            this.databaseManager = new DatabaseManager(new SQLService(
                    f.get().getString("sql.host"),
                    f.get().getString("sql.database"),
                    f.get().getString("sql.username"),
                    f.get().getString("sql.password"),
                    f.get().getInt("sql.port")));

            // Connect to SQL
            Synergy.info("Connecting to SQL....");
            this.databaseManager.connect();
            Synergy.success("Connected to your SQL Service Provider");

             // Generate Tables
            new TableBuilder("users", this.databaseManager)
                    .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                    .addColumn("name", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .addColumn("rank", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .addColumn("user_experience", SQLDataType.VARCHAR, 100,false, SQLDefaultType.CUSTOM.setCustom(UserExperience.NOOB.toString().toUpperCase()), false)
                    .addColumn("xp", SQLDataType.DOUBLE, -1,true, SQLDefaultType.CUSTOM.setCustom(0), false)
                    .execute();
            new TableBuilder("user_achievements", this.databaseManager)
                    .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                    .addColumn("achievement", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .addColumn("achieved_on", SQLDataType.DATE, -1, false, SQLDefaultType.NO_DEFAULT, false)
                    .execute();

        }catch (FileNotFoundException e){
            //TODO: Create one instead
            Sam.getRobot().error(null, "File 'settings.yml' doesn't exists", "I can't fix it myself so it has to be done by a developer!", e);
            getPluginLoader().disablePlugin(this);
            return;
        }catch (SQLException e){
            Synergy.error("I can't connect to your SQL Service provider", "Check your SQL settings in the 'settings.yml'");
            getPluginLoader().disablePlugin(this);
            return;
        }
//        catch (ClassNotFoundException e){
//            Synergy.error("OMG, there is no SQL Server here...", "Install a SQL Server");
//            getPluginLoader().disablePlugin(this);
//            return;
//        }

        this.message = new Message(this);

        // Initialize all the messages that are being sent..
        try{
            this.message.init(getPluginManager().getFileStructure().getYMLFile("en"));
            C.initColors();
        }catch (Exception e){
            Sam.getRobot().error(null, e.getMessage(), "Try to contact the server developer", e);
        }

        // Load the modules

        // WARNING !!! WARNING (Do not place any modules with commands above this line)
        this.commandManager = new CommandManager(this);
        ///////////////
        this.cooldownManager = new CooldownManager(this);
        this.userManager = new UserManager(this);


        this.autoRebootManager = new AutoRebootManager(this);
        this.dependencyManager = new DependencyManager(this);
        this.globalManager = new GlobalManager(this);
        this.GUIManager = new GuiManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.pluginMessagingManager = new PluginMessagingManager(this);
        this.economyManager = new EconomyManager(this);
        this.hologramManager = new HologramManager(this);
        this.discordManager = new DiscordManager();
        this.changelogManager = new ChangelogManager(this);
        this.cacheManager = new CacheManager(this);
        this.warpManager = new WarpManager(this);
        this.achievementManager = new AchievementManager(this);
        this.nickManager = new NickManager(this);

        // Init the utilities
        this.globalManager.setUtilDisplay(new UtilDisplay());

        try{
            if (getPluginManager().getFileStructure().getYMLFile("settings").get().getBoolean("network.isLobby")){
                this.lobbyManager = new LobbyManager(this);
            }
        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }

        Synergy.info("Using version adapter "+this.versionManager.getServerVersion());

        // Init the cache
        this.cacheManager.loadCache();

        // Disable this to disable the API
        Synergy.setSpigotAPI(this);

        // Update all the messages that are being sent..
        Message.update();

        Synergy.info("Loaded a total of "+Module.getTotal()+" Modules & Systems!");
        this.loaded = true;
        this.disabled = false;

        int plugins = getPluginManager().getPlugins().size();
        if (plugins > 0){
            for(SynergyPlugin plugin : getPluginManager().getPlugins()){
                plugin.init();
            }
            Synergy.info(plugins+" registered plugins initialized");
        }

        Synergy.success("Synergy is up and running!");

        // Calling the method once the main thread finished
        getServer().getScheduler().scheduleSyncDelayedTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                onServerEnabled();
            }
        });
    }

    public void onDisable(){
        this.warpManager.saveAllWarps();

        for(SynergyPlugin plugin : getPluginManager().getPlugins()){
            plugin.deinit();
        }

    }

    /*
     * This method is being executed after the server is
     * completely enabled and all the plugin are loaded!
     */
    public void onServerEnabled(){
        // Init all the warps because of a world manager problem
        this.warpManager.cacheSavedWarps();
    }

    // Called when typed /synergy restart/reload
    public void onRestart(){
        this.loaded = false;
        onDisable();
        onEnable();
    }

}

package usa.devrocoding.synergy.spigot;

import com.google.inject.Injector;
import lombok.Getter;
import lombok.Setter;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.spigot.achievement.AchievementManager;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.assets.*;
import usa.devrocoding.synergy.spigot.assets.lobby.LobbyManager;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.assets.wand.WandManager;
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
import usa.devrocoding.synergy.spigot.objectives.ObjectiveManager;
import usa.devrocoding.synergy.spigot.plugin_messaging.PluginMessagingManager;
import usa.devrocoding.synergy.spigot.punish.PunishManager;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
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

@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy", proxy = "BungeeCord", server_name = "MiragePrisons")
public class Core extends SynergyPlugin {

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
    @Getter @Setter
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
    private WandManager wandManager;
    @Getter
    private PunishManager punishManager;
    @Getter
    private ObjectiveManager objectiveManager;

    @Getter
    private LobbyManager lobbyManager = null;
    @Getter
    private SynergyMani manifest;

    @Override
    public void init() {}

    @Override
    public void preInit(){
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
        if (!this.pluginManager.initDatabase()){
            this.setEnabled(false);
            return;
        }

        this.message = new Message(this);

        // Initialize all the messages that are being sent..
        try{
            this.message.init(getPluginManager().getFileStructure().getYMLFile("en"));
            C.initColors();
        }catch (Exception e){
            Sam.getRobot().error(null, e.getMessage(), "Try to contact the server developer", e);
        }

        // Load the modules \\
        // WARNING !!! WARNING (Do not place any modules above this line)
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
        this.wandManager = new WandManager(this);
        this.punishManager = new PunishManager(this);
        this.objectiveManager = new ObjectiveManager(this);

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

//        try{
//            Metrics metrics = new Metrics(this); // Enable the stats
//            if (!metrics.isEnabled()){
//                Synergy.error("Plugin stopped sending data to bStats! bStats error..");
//            }
//        }catch (Exception e){
//            Synergy.error(e.getMessage());
//        }

        Synergy.info("Loaded a total of "+Module.getTotal()+" Modules & Systems!");
        Module.total = 0;

        this.loaded = true;
        this.disabled = false;

        Synergy.success("Synergy is up and running!");

        // Calling the method once the main thread finished
        new BukkitRunnable(){
            @Override
            public void run() {
                onServerEnabled();
            }
        }.runTask(this);
    }

    @Override
    public void deinit() {}

    @Override
    public void preDeInit(){
        if (this.isLoaded()) { // Make sure the modules are loaded to use the modules
            this.warpManager.saveAllWarps();
        }
    }

    /*
     * This method is being executed after the server is
     * completely enabled and all the plugin are loaded!
     */
    private void onServerEnabled(){
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

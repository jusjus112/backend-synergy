package usa.devrocoding.synergy.spigot;

import com.google.inject.Injector;
import lombok.Getter;
import lombok.Setter;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.spigot.achievement.AchievementManager;
import usa.devrocoding.synergy.spigot.api.SynergyPlugin;
import usa.devrocoding.synergy.spigot.assets.*;
import usa.devrocoding.synergy.spigot.assets.lobby.LobbyManager;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.assets.wand.WandManager;
import usa.devrocoding.synergy.spigot.autoreboot.AutoRebootManager;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.botsam.object.ErrorHandler;
import usa.devrocoding.synergy.spigot.changelog.ChangelogManager;
import usa.devrocoding.synergy.spigot.command.CommandManager;
import usa.devrocoding.synergy.spigot.discord.DiscordManager;
import usa.devrocoding.synergy.spigot.economy.EconomyManager;
import usa.devrocoding.synergy.spigot.gui.GuiManager;
import usa.devrocoding.synergy.spigot.hologram.HologramManager;
import usa.devrocoding.synergy.spigot.language.LanguageManager;
import usa.devrocoding.synergy.spigot.nick.NickManager;
import usa.devrocoding.synergy.spigot.objectives.ObjectiveManager;
import usa.devrocoding.synergy.spigot.pluginmessaging.PluginMessagingManager;
import usa.devrocoding.synergy.spigot.protect.ProtectManager;
import usa.devrocoding.synergy.spigot.punish.PunishManager;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
import usa.devrocoding.synergy.spigot.statistics.StatisticsManager;
import usa.devrocoding.synergy.spigot.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.utilities.UtilDisplay;
import usa.devrocoding.synergy.spigot.version.VersionManager;
import usa.devrocoding.synergy.spigot.warp.WarpManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy", proxy = "BungeeCord", server_name = "Server Name")
public class Core extends SynergySpigot {

    private boolean loaded = false,
                    ready = false,
                    disabled = false;

    public List<Module> modules = new ArrayList<>();

    private PluginManager pluginManager;
    private VersionManager versionManager;
    private AutoRebootManager autoRebootManager;
    private CommandManager commandManager;
    private RunnableManager runnableManager;
    @Setter
    private DatabaseManager databaseManager;
    private GuiManager GUIManager;
    private ScoreboardManager scoreboardManager;
    private UserManager userManager;
    private EconomyManager economyManager;
    private HologramManager hologramManager;
    private DiscordManager discordManager;
    private LanguageManager languageManager;
    private GlobalManager globalManager;
    private Message message;
    private ChangelogManager changelogManager;
    private PluginMessagingManager pluginMessagingManager;
    private CacheManager cacheManager;
    private WarpManager warpManager;
    private CooldownManager cooldownManager;
    private AchievementManager achievementManager;
    private NickManager nickManager;
    private DependencyManager dependencyManager;
    private WandManager wandManager;
    private PunishManager punishManager;
    private ObjectiveManager objectiveManager;
    private StatisticsManager statisticManager;
    private GoogleAuthManager googleAuthManager;
    private ProtectManager protectManager;
    private LobbyManager lobbyManager = null;
    private SynergyMani manifest;

    @Override
    public void init() {}

    @Override
    public void preInit(){
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
        // TODO: Implement this later on, it's not working yet!

        this.pluginManager = new PluginManager(this);
        this.pluginManager.load();

        // Load the Manifest so other it can be used in other classes
        this.manifest = this.getClass().getAnnotation(SynergyMani.class);

        // Load the sql Service so SQL can be used
        if (!this.pluginManager.initDatabase()){
            this.setEnabled(false);
            return;
        }

        try{
            Synergy.setProduction(getPluginManager().getFileStructure().getYMLFile("settings")
                .get().getBoolean("network.isProduction"));
        }catch (FileNotFoundException e){
            Synergy.error("Error loading settings.yml: " + e.getMessage());
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
        this.googleAuthManager = new GoogleAuthManager(this);
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
        this.statisticManager = new StatisticsManager(this);
        this.protectManager = new ProtectManager(this);

        // Init the utilities
        this.globalManager.setUtilDisplay(new UtilDisplay());

        try{
            if (getPluginManager().getFileStructure().getYMLFile("settings")
                .get().getBoolean("network.isLobby")){
                this.lobbyManager = new LobbyManager(this);
            }
        }catch (FileNotFoundException e){
            Synergy.error(e.getMessage());
        }

        // Init modules
        this.modules.forEach(Module::onInit);

        Synergy.info("Using version adapter "+this.versionManager.getServerVersion());

        // Init the cache
        this.cacheManager.loadCache();

        // Disable this to disable the API
        Synergy.setSpigotAPI(this);

        // Update all the messages that are being sent..
        Message.update();

        try{
            new Metrics(this,6197); // Enable the stats
        }catch (Exception e){
            Synergy.error(e.getMessage());
            Synergy.error("Plugin stopped sending data to bStats! bStats error..");
        }

        Synergy.info("Loaded a total of "+Module.getTotal()+" Modules & Systems!");
        Module.total = 0;

        this.loaded = true;
        this.disabled = false;


        if (!Synergy.isProduction()) {
            Synergy.warn("Synergy is in TEST mode! Production systems are not active!");
        }

        Synergy.success("Synergy is up and running!");

        // Calling the method once the main thread finished
        new BukkitRunnable(){
            @Override
            public void run() {
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        ready = true;
                    }
                }.runTaskLater(Core.this, 20L * 5);
                onServerEnabled();
            }
        }.runTask(this);
    }

    @Override
    public void deinit() {
        this.modules.forEach(Module::onDeinit);
    }

    @Override
    public void preDeInit(){
        if (this.isLoaded()) { // Make sure the modules are loaded to use the modules
            this.warpManager.saveWarps();
        }
    }

    /**
     * This method is being executed after the server is
     *  completely enabled and all the plugin are loaded!
     */
    private void onServerEnabled(){
        // Init all the warps because of a world manager problem
        this.warpManager.cacheSavedWarps();

        // Init modules
        this.modules.forEach(Module::onServerLoad);
    }

    // Called when typed /synergy restart/onReload
    public void onRestart(){
        this.loaded = false;
        onDisable();
        onEnable();
    }

}
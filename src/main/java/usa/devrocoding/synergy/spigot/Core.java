package usa.devrocoding.synergy.spigot;

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
import usa.devrocoding.synergy.spigot.api.SpigotAPI;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.assets.GlobalManager;
import usa.devrocoding.synergy.spigot.assets.PluginManager;
import usa.devrocoding.synergy.spigot.assets.SynergyMani;
import usa.devrocoding.synergy.spigot.assets.object.Message;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.bot_sam.object.ErrorHandler;
import usa.devrocoding.synergy.spigot.changelog.ChangelogManager;
import usa.devrocoding.synergy.spigot.command.CommandManager;
import usa.devrocoding.synergy.spigot.discord.DiscordManager;
import usa.devrocoding.synergy.spigot.economy.EconomyManager;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.gui.GuiManager;
import usa.devrocoding.synergy.spigot.hologram.HologramManager;
import usa.devrocoding.synergy.spigot.language.Language;
import usa.devrocoding.synergy.spigot.language.LanguageManager;
import usa.devrocoding.synergy.spigot.language.LanguageStrings;
import usa.devrocoding.synergy.spigot.plugin_messaging.PluginMessagingManager;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
import usa.devrocoding.synergy.spigot.two_factor_authentication.GoogleAuthManager;
import usa.devrocoding.synergy.spigot.user.UserManager;
import usa.devrocoding.synergy.spigot.user.object.UserExperience;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy.")
public class Core extends JavaPlugin {

    @Getter @Setter
    private static Core plugin;

    @Getter
    private boolean loaded = false,
                    disabled = false;

    @Getter
    private PluginManager pluginManager;
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
    private GoogleAuthManager googleAuthManager;
    @Getter
    private ChangelogManager changelogManager;
    @Getter
    private PluginMessagingManager pluginMessagingManager;

    @Getter
    private SynergyMani manifest;

    public void onEnable(){
        setPlugin(this);

        Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> getServer().getConsoleSender().sendMessage(C.PLUGIN.getColor()+s));
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
            this.databaseManager.connect();

             // Generate Tables
            new TableBuilder("synergy_users")
                    .addColumn("uuid", SQLDataType.VARCHAR, 300,false, SQLDefaultType.NO_DEFAULT, true)
                    .addColumn("name", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
                    .addColumn("rank", SQLDataType.VARCHAR, 100,false, SQLDefaultType.CUSTOM.setCustom("NONE"), false)
                    .addColumn("userexperience", SQLDataType.VARCHAR, 100,false, SQLDefaultType.CUSTOM.setCustom(UserExperience.NOOB), false)
                    .execute();

        }catch (FileNotFoundException e){
            Sam.getRobot().error(null, "File 'settings.yml' doesn't exists", "Did you touched the file? If not, ask my creator", e);
            getPluginLoader().disablePlugin(this);
            return;
        }catch (SQLException e){
            Sam.getRobot().error(null, "I can't connect to your SQL Service provider", "Check your SQL settings in the 'settings.yml'", e);
            getPluginLoader().disablePlugin(this);
            return;
        }catch (ClassNotFoundException e){
            Sam.getRobot().error(null, "OMG, there is no SQL Server here... MAYDAY", "Install a SQL Server bb", e);
            getPluginLoader().disablePlugin(this);
            return;
        }

        // Load the BungeeCord or Redis channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        this.message = new Message(this);

        // Initialize all the messages that are being sent..
        try{
            this.message.init(getPluginManager().getFileStructure().getYMLFile("en"));
            C.initColors();
        }catch (Exception e){
            Sam.getRobot().error(null, e.getMessage(), "Try to contact the server developer", e);
        }

        // Load the modules
        this.commandManager = new CommandManager(this);
        this.globalManager = new GlobalManager(this);
        this.GUIManager = new GuiManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.userManager = new UserManager(this);
        this.economyManager = new EconomyManager(this);
        this.hologramManager = new HologramManager(this);
        this.discordManager = new DiscordManager();
        this.googleAuthManager = new GoogleAuthManager();
        this.changelogManager = new ChangelogManager(this);
        this.pluginMessagingManager = new PluginMessagingManager(this);

        // Disable this to disable the API
        Synergy.setSpigotAPI(new SpigotAPI());

        // Update all the messages that are being sent..
        Message.update();

        //TODO: Load all the used systems and commands for the server to being used.
        Synergy.info("All systems are loaded!");
        Synergy.info("Enabling Google AUTH");

//        getGoogleAuthManager().getTwoFactorKey();
//        getGoogleAuthManager().isCorrect();

        this.loaded = true;
        this.disabled = false;
    }

    public void onDisable(){
        this.loaded = false;
        try{
            // Initialize all the messages that are being sent..
            this.message.deint(getPluginManager().getFileStructure().getYMLFile("en"));
        }catch (Exception e){
            Sam.getRobot().error(null, e.getMessage(), "Try to contact the server developer", e);
        }
        this.disabled = true;
    }

    // Called when typed /synergy restart/reload
    public void onRestart(){
        this.loaded = false;
        onDisable();
        onEnable();
    }

}

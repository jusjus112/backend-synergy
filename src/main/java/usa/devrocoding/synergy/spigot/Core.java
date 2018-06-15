package usa.devrocoding.synergy.spigot;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import usa.devrocoding.synergy.services.SQLService;
import usa.devrocoding.synergy.services.sql.DatabaseManager;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.api.SpigotAPI;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.assets.PluginManager;
import usa.devrocoding.synergy.spigot.assets.SynergyMani;
import usa.devrocoding.synergy.spigot.command.CommandManager;
import usa.devrocoding.synergy.spigot.discord.DiscordManager;
import usa.devrocoding.synergy.spigot.economy.EconomyManager;
import usa.devrocoding.synergy.spigot.files.yml.YMLFile;
import usa.devrocoding.synergy.spigot.gui.GuiManager;
import usa.devrocoding.synergy.spigot.hologram.HologramManager;
import usa.devrocoding.synergy.spigot.language.LanguageManager;
import usa.devrocoding.synergy.spigot.runnable.RunnableManager;
import usa.devrocoding.synergy.spigot.scoreboard.ScoreboardManager;
import usa.devrocoding.synergy.spigot.user.UserManager;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;

@SynergyMani(backend_name = "Synergy", main_color = ChatColor.AQUA, permission_prefix = "synergy.")
public class Core extends JavaPlugin {

    @Getter @Setter
    private static Core plugin;

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
    private SynergyMani manifest;

    public void onEnable(){
        setPlugin(this);

        // Load Files and other important things
        this.pluginManager = new PluginManager(this);

        // Print our logo into the console
        Arrays.stream(Synergy.getLogos().logo_colossal).forEach(s -> getServer().getConsoleSender().sendMessage(C.PLUGIN_COLOR.color()+s));
        System.out.println("  ");

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
        }catch (FileNotFoundException e){
            Synergy.error("Can't seem to find the file 'settings.yml'");
            getPluginLoader().disablePlugin(this);
            return;
        }catch (SQLException e){
            Synergy.error("Can't connect to your SQL service provider", e.getMessage());
            getPluginLoader().disablePlugin(this);
            return;
        }

        // Load the BungeeCord or Redis channels
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        // Load the modules
        this.commandManager = new CommandManager(this);
        this.runnableManager = new RunnableManager(this);
        this.GUIManager = new GuiManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
        this.userManager = new UserManager(this);
        this.economyManager = new EconomyManager(this);
        this.hologramManager = new HologramManager(this);
        this.discordManager = new DiscordManager();
        this.languageManager = new LanguageManager(this);

        // Disable this to disable the API
        Synergy.setSpigotAPI(new SpigotAPI());
    }

    public void onDisable(){

    }

}

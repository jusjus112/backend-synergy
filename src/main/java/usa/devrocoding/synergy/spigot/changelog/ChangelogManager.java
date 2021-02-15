package usa.devrocoding.synergy.spigot.changelog;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.botsam.Sam;
import usa.devrocoding.synergy.spigot.changelog.commands.CommandChangelog;
import usa.devrocoding.synergy.spigot.changelog.listeners.ChangelogJoinListener;
import usa.devrocoding.synergy.spigot.changelog.object.Changelog;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChangelogManager extends Module {

    @Getter
    private List<Changelog> changelogs = new ArrayList<>();

    public ChangelogManager(Core plugin){
        super(plugin, "Changelog Manager", true);

        registerCommand(
                new CommandChangelog(plugin)
        );

        registerListener(
                new ChangelogJoinListener()
        );

        try{
            getPlugin().getPluginManager().getFileStructure().getYMLFile("example_changelog").set(
                    new HashMap<String, Object>(){{
                        put("changelog.title", "&e&lUpdates &7From &6&l%date%");
                        put("changelog.date", "09-11-2020");
                        put("changelog.time", "12:30 PM");
                        put("changelog.show_till", "25-11-2020");
                        put("changelog.changelog", new ArrayList<String>(){{
                            add("This is page 1");
                            add("This is page 2");
                            add("This is page 3");
                        }});
                    }}
            );
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Try to contact the server developer", e);
        }
    }

    @Override
    public void reload(String response) {
        cacheChangelogs();
    }

    public void addChangelog(Changelog changelog){
        this.changelogs.add(changelog);
    }

    public Changelog getLatestChangelog(){
        for(Changelog changelog : getChangelogs()){
            if (new Date().before(changelog.getShowTill())){
                return changelog;
            }
        }
        return null;
    }

    public void cacheChangelogs(){
        File dataFolder = new File(getPlugin().getDataFolder()+File.separator+"changelogs"+File.separator+"server");
        File[] files = dataFolder.listFiles();
        this.changelogs.clear();

        if (files != null && files.length > 0) {
            try {
                for (File file : files) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String title;
                    Date date;
                    Date showTill;

                    String time = config.getString("changelog.time");

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm aa", Locale.ENGLISH);
                    date = format.parse(config.getString("changelog.date")+" "+time);

                    showTill = new SimpleDateFormat("dd-MM-yyyy")
                        .parse(config.getString("changelog.show_till"));

                    title = config.getString("changelog.title").replace("%date%",
                            new SimpleDateFormat("MMM dd, yyyy - HH:mm aa").format(date));

                    List<String> pages = Lists.newArrayList();
                    for(String s : config.getStringList("changelog.changelog")){
                        pages.add(C.translateColors(s));
                    }

                    new Changelog(title, date, showTill, pages);
                }
                Synergy.info(getChangelogs().size()+" changelog's loaded!");
            }catch (Exception e){
                Sam.getRobot().error(this, e.getMessage(), "Contact the server developer!", e);
            }
        }
    }

}

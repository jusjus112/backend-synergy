package usa.devrocoding.synergy.spigot.changelog;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;
import usa.devrocoding.synergy.spigot.changelog.commands.CommandChangelog;
import usa.devrocoding.synergy.spigot.changelog.object.Changelog;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChangelogManager extends Module {

    @Getter
    private List<Changelog> changelogs = new ArrayList<>();

    public ChangelogManager(Core plugin){
        super(plugin, "Changelog Manager");

        registerCommand(
                new CommandChangelog(plugin)
        );

        try{
            getPlugin().getPluginManager().getFileStructure().getYMLFile("example_changelog").set(
                    new HashMap<String, Object>(){{
                        put("changelog.title", "&e&lChangelog &7for &6&l%date%");
                        put("changelog.date", "06-02-2019");
                        put("changelog.time", "12:30 PM");
                        put("changelog.order", "1");
                        put("changelog.changelog", new ArrayList<String>(){{
                            add("FIXED:");
                            add("- Bug #2325 | Dupe glitch playervaults");
                            add("ADDED:");
                            add("- Feature #04133 | Added this changelog system");
                            add("- Feature #01312 | Added /request and /bler");
                            add("REMOVED:");
                            add("- Feature #01312 | Added /request and /bler");
                        }});
                    }}
            );
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Try to contact the server developer", e);
        }
    }

    public void addChangelog(Changelog changelog){
        this.changelogs.add(changelog);
    }

    public void cacheChangelogs(){
        File dataFolder = new File(getPlugin().getDataFolder()+File.separator+"changelogs"+File.separator+"server");
        File[] files = dataFolder.listFiles();

        if (files != null && files.length > 0) {
            try {
                for (File file : files) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String title;
                    int order;
                    List<String> lines = new ArrayList<>();
                    Date date;

                    order = config.getInt("changelog.order");
                    String time = config.getString("changelog.time");

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm aa", Locale.ENGLISH);
                    date = format.parse(config.getString("changelog.date")+" "+time);
                    title = config.getString("changelog.title").replace("%date%",
                            new SimpleDateFormat("MMM dd, yyyy - HH:mm aa").format(date));

                    for(String l : config.getStringList("changelog.changelog")){
                        String line = l;
                        if (l.startsWith("-")){
                            line = "§6"+l;
                        }else if (l.endsWith(":")){
                            line = "§b§l"+l;
                        }
                        lines.add(C.colorize(line));
                    }

                    new Changelog(title, order, date, lines);
                }
                Synergy.info(getChangelogs().size()+" changelog's loaded!");
            }catch (Exception e){
                Sam.getRobot().error(this, e.getMessage(), "Contact the server developer!", e);
            }
        }
    }

}

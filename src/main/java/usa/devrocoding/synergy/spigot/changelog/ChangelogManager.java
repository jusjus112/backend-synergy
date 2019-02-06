package usa.devrocoding.synergy.spigot.changelog;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChangelogManager extends Module {

    public ChangelogManager(Core plugin){
        super(plugin, "Changelog Manager");

        try{
            getPlugin().getPluginManager().getFileStructure().getYMLFile("example_changelog").setHeader("This file is not being used. Make sure to copy this to a working file and edit it.").set(
                    new HashMap<String, Object>(){{
                        put("changelog.title", "&e&lChangelog &7for &6&lMarch 2nd, 2019");
                        put("changelog.date", "06-02-2019");
                        put("changelog.changelog", new ArrayList<String>(){{
                            add("!@#$%^&**Test1..");
                            add("!@#$%^&**Test2..");
                            add("!@#$%^&**Test3..");
                        }});
                    }}
            );
        }catch (FileNotFoundException e){
            Sam.getRobot().error(this, e.getMessage(), "Try to contact the server developer", e);
        }
    }



}

package usa.devrocoding.synergy.spigot.bot_sam;

import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.bot_sam.object.ErrorHandler;

public class Sam {

    private static Sam sam;
    public static Sam getRobot(){ return sam; }
    public Sam(){ sam = this; }

    public static String getName(){
        return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"SAM";
    }

    public void error(String cause, String solution, Exception exception){
        new ErrorHandler(this).error(cause, solution, exception);
    }

}

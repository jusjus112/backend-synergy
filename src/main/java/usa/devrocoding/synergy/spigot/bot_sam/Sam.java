package usa.devrocoding.synergy.spigot.bot_sam;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.object.ErrorHandler;
import usa.devrocoding.synergy.spigot.bot_sam.object.SamMessage;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

import java.util.Arrays;

public class Sam {

    @Getter
    public final String prefix = "§d§l</> §7"+ChatColor.BOLD+"Synergy"+C.CHAT.getColor()+ChatColor.BOLD+": "+ChatColor.RESET;
    @Getter
    public final String announcement = "§d§l</> §7"+ChatColor.BOLD+"IMPORTANT"+C.CHAT.getColor()+ChatColor.BOLD+" "+ChatColor.RESET;

    @Getter
    private static Sam robot;

    public Sam(){ robot = this; }

    public static String getName(){
        return ChatColor.LIGHT_PURPLE+""+ChatColor.BOLD+"SAM";
    }

    public void error(Module module, String cause, String solution, Exception exception){
        new ErrorHandler(this).error(module, cause, solution, exception);
    }

    public void info(Player player, String... messages){
        Arrays.stream(messages).forEach(s -> player.sendMessage(prefix+C.INFO.getColor()+C.translateColors(s)));
    }

    public void important(Player player, String... messages){
        Arrays.stream(messages).forEach(s -> player.sendMessage(announcement+C.SUCCESS.getColor()+C.translateColors(s)));
    }

    public void announcement(Player player, String... messages){
        player.sendMessage("  ");
        player.sendMessage(UtilString.centered("&C&LANNOUNCEMENT"));
        Arrays.stream(messages).forEach(s -> player.sendMessage(UtilString.centered(s)));
        player.sendMessage("  ");
    }

    public void sam(Player player){
        player.sendMessage(prefix+C.INFO.getColor()+SamMessage.CANNOT_DO_THAT.getRandom());
    }

    public void warning(Player player, String... messages){
        Arrays.stream(messages).forEach(s -> player.sendMessage(prefix+C.ERROR+(s==null?SamMessage.ERROR.getRandom():s)));
    }

}

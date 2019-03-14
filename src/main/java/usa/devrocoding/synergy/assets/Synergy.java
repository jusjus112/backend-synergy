package usa.devrocoding.synergy.assets;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import usa.devrocoding.synergy.assets.letters.LetterGenerator;
import usa.devrocoding.synergy.assets.letters.Logo;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.api.BungeeAPI;
import usa.devrocoding.synergy.spigot.api.SpigotAPI;

import java.util.Arrays;

public class Synergy {

    public enum SynergyColor{
        CHAT(ChatColor.GRAY),
                INFO(ChatColor.YELLOW),
                PREFIX(ChatColor.GOLD),
                ITEM_TITLE(ChatColor.YELLOW),
                PRIMARY(ChatColor.GRAY),
                MESSAGE_HIGHLIGHT(ChatColor.GREEN),
                ERROR(ChatColor.RED),
                SUCCESS(ChatColor.GREEN),
                SAM(ChatColor.LIGHT_PURPLE),
                CHAT_HIGHLIGHT(ChatColor.AQUA),
                SCOREBOARD_TITLE(ChatColor.GREEN),
                SCOREBOARD_SUBTITLE(ChatColor.GRAY),
                PLUGIN(ChatColor.YELLOW);

        @Override
        public String toString() {
            return getColor().toString();
        }

        @Getter
        private ChatColor color;

        SynergyColor(ChatColor color){
            this.color = color;
        }

        public static String getLine(){
            return "«§e§m----------------------------------§r»";
        }

        public static String getLineWithoutSymbols(){
            return "---------------------------------------";
        }

        public static String getLineWithName(){
            return "«§e"+ChatColor.STRIKETHROUGH+"-------------"+ ChatColor.RESET+" §6Synergy " +"§e§m-------------"+ChatColor.RESET+"»";
        }
        public static String getLineWithName(ChatColor color){
            return "«"+color+ChatColor.STRIKETHROUGH+"-------------"+ ChatColor.RESET+" §6Synergy " +color+"§m-------------"+ChatColor.RESET+"»";
        }

        public static String getLineWithName(String name){
            return "«"+ChatColor.STRIKETHROUGH+"---------------"+ChatColor.AQUA+" "+name+" "+ChatColor.RESET+ChatColor.STRIKETHROUGH+"----------------"+ChatColor.RESET+"»";
        }

        public static String getShortLineWithName(String name){
            return "«"+ChatColor.STRIKETHROUGH+"--------"+ChatColor.AQUA+" "+name+""+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET+"»";
        }

        public static String getLineWithNameNoAttr(String name){
            return "«"+ChatColor.STRIKETHROUGH+"--------- "+SynergyColor.INFO.getColor()+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------"+ChatColor.RESET+"»";
        }

        public static String getLineWithNameWithoutSymbols(String name){
            return ChatColor.STRIKETHROUGH+"-------------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------------"+ChatColor.RESET;
        }

        public static String getShortLineWithNameWithoutSymbols(String name){
            return ChatColor.STRIKETHROUGH+"-------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET;
        }
    }

    @Setter @Getter
    private static usa.devrocoding.synergy.spigot.Core spigotAPI;
    @Setter @Getter
    private static BungeeAPI bungeeAPI;
    @Getter
    private static LetterGenerator letterGenerator = new LetterGenerator();
    @Getter
    private static Logo logos = new Logo();

    public static void debug(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(LinuxColorCodes.ANSI_PURPLE+"[Synergy DEBUG] "+s+LinuxColorCodes.ANSI_RESET));
    }

    public static void info(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("info", LinuxColorCodes.ANSI_YELLOW, s)));
    }

    public static void discord(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("", LinuxColorCodes.ANSI_YELLOW, s)));
    }

    public static void success(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("success", LinuxColorCodes.ANSI_GREEN, s)));
    }

    public static void normal(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("", LinuxColorCodes.ANSI_RESET, s)));
    }

    public static void error(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("error", LinuxColorCodes.ANSI_RED, s)));
    }

    public static void warn(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("warn", LinuxColorCodes.ANSI_RED, s)));
    }

    private static String format(String prefix, String color, String message){
        return LinuxColorCodes.ANSI_YELLOW+"Synergy"+color+(prefix.length()>0?" "+prefix.toUpperCase():"")+LinuxColorCodes.ANSI_CYAN+" </> "+color+message+LinuxColorCodes.ANSI_RESET;
    }

}

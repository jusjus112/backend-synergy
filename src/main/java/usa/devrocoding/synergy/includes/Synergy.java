package usa.devrocoding.synergy.includes;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import usa.devrocoding.synergy.includes.handler.SynergyPlugin;
import usa.devrocoding.synergy.includes.letters.LetterGenerator;
import usa.devrocoding.synergy.includes.letters.Logo;
import usa.devrocoding.synergy.includes.object.LinuxColorCodes;

import java.util.Arrays;
import java.util.Random;

public class Synergy {

    public static Collection<SynergyPlugin> plugins;

    public static void registerSpigotModule(SynergyPlugin plugin){
        plugins.add(plugin);
    }

    public final static String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

    @Getter @Setter
    private static boolean production = false;

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
        private final ChatColor color;

        SynergyColor(ChatColor color){
            this.color = color;
        }

        public static String rainbow(String original){
            StringBuilder builder = new StringBuilder();
            for(char c : original.toCharArray()){
                ChatColor color = ChatColor.values()[new Random(ChatColor.values().length).nextInt()];
                builder.append(color+String.valueOf(c));
            }
            return builder.toString();
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
            return "«"+ChatColor.STRIKETHROUGH+"---------------"+ChatColor.RESET+ChatColor.AQUA+" "+name+" "+ChatColor.RESET+ChatColor.STRIKETHROUGH+"----------------"+ChatColor.RESET+"»";
        }

        public static String getShortLineWithName(String name){
            return "«"+ChatColor.STRIKETHROUGH+"--------"+ChatColor.AQUA+" "+name+""+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET+"»";
        }

        public static String getLineWithNameNoAttr(String name){
            return "«"+ChatColor.STRIKETHROUGH+"---------"+ChatColor.RESET+" "+SynergyColor.INFO.getColor()+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------"+ChatColor.RESET+"»";
        }

        public static String getLineWithNameWithoutSymbols(String name){
            return ChatColor.STRIKETHROUGH+"-------------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------------"+ChatColor.RESET;
        }

        public static String getShortLineWithNameWithoutSymbols(String name){
            return ChatColor.STRIKETHROUGH+"-------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET;
        }
    }

    @Getter
    private static final LetterGenerator letterGenerator = new LetterGenerator();
    @Getter
    private static final Logo logos = new Logo();

    public static void debug(String... messages){
        if (isProduction()) return;
        Arrays.stream(messages).forEach(s -> System.out.println(LinuxColorCodes.ANSI_RED+"[Synergy DEBUG] "+LinuxColorCodes.ANSI_YELLOW+s+LinuxColorCodes.ANSI_RESET));
    }

    public static void info(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("info", LinuxColorCodes.ANSI_YELLOW, s)));
    }

    public static void discord(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(format("bot", LinuxColorCodes.ANSI_YELLOW, s)));
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

    public static String format(String prefix, String color, String message){
        return LinuxColorCodes.ANSI_YELLOW+"Synergy"+color+(prefix.length()>0?" "+prefix.toUpperCase():"")+LinuxColorCodes.ANSI_CYAN+" </> "+color+message+LinuxColorCodes.ANSI_RESET;
    }

}

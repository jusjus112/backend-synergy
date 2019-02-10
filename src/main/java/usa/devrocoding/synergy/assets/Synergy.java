package usa.devrocoding.synergy.assets;

import lombok.Getter;
import lombok.Setter;
import usa.devrocoding.synergy.assets.letters.LetterGenerator;
import usa.devrocoding.synergy.assets.letters.Logo;
import usa.devrocoding.synergy.assets.object.LinuxColorCodes;
import usa.devrocoding.synergy.spigot.api.BungeeAPI;
import usa.devrocoding.synergy.spigot.api.SpigotAPI;

import java.util.Arrays;

public class Synergy {

    @Setter @Getter
    private static SpigotAPI spigotAPI;
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
        Arrays.stream(messages).forEach(s -> System.out.println(LinuxColorCodes.ANSI_YELLOW+"[Synergy INFO] "+s+LinuxColorCodes.ANSI_RESET));
    }

    public static void normal(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println("[Synergy] "+s));
    }

    public static void withPrefix(String prefix, String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println("[Synergy "+prefix+"] "+s));
    }

    public static void error(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(LinuxColorCodes.ANSI_RED+"[Synergy ERROR] "+s+LinuxColorCodes.ANSI_RESET));
    }

    public static void warn(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println(LinuxColorCodes.ANSI_YELLOW+"[Synergy WARN] "+s+LinuxColorCodes.ANSI_RESET));
    }

}

package usa.devrocoding.synergy.spigot.api;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public class Synergy {

    @Setter @Getter
    private static SynergyAPI API;

    public static void debug(String... messages){
        Arrays.stream(messages).forEach(s -> System.out.println("[Synergy DEBUG] "+s));
    }

}

package usa.devrocoding.synergy.spigot.assets;

import org.bukkit.ChatColor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SynergyMani {

    /**
     * The name of this backend system
     */
    String backend_name() default "Synergy";

    /**
     * The prefix for all of our permissions.
     * @IMPORTANT: Don't forget the . at the end!
     */
    String permission_prefix() default "synergy";

    /**
     * The Main color of this backend system
     */
    ChatColor main_color() default ChatColor.AQUA;

    String proxy() default "BungeeCord";

    String server_name() default "SynergyMC";

}

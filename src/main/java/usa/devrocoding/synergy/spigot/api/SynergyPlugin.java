package usa.devrocoding.synergy.spigot.api;

import org.bukkit.ChatColor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface SynergyPlugin {

    /**
     * The name of this backend system
     */
    String plugin_name() default "SynergyPlugin";

    /**
     * The prefix for all of our permissions.
     * @IMPORTANT: Don't forget the . at the end!
     */
    String[] permission_prefix() default "synergy.";

    /**
     * The Main color of this plugin
     */
    ChatColor plugin_color() default ChatColor.GOLD;

}

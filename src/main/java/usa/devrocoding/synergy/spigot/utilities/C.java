package usa.devrocoding.synergy.spigot.utilities;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum C {

    CHAT_COLOUR(ChatColor.GRAY),
    PREFIX(ChatColor.GOLD),
    PRIMARY_MESSAGE(ChatColor.GRAY),
    MESSAGE_HIGHLIGHT(ChatColor.GREEN),
    ERROR_COLOUR(ChatColor.RED),
    SUCCESS_COLOUR(ChatColor.RED),
    CHAT_HIGHLIGHT_COLOUR(ChatColor.AQUA),
    TITLE_COLOR(ChatColor.GREEN),
    SUBTITLE_COLOR(ChatColor.GRAY);

    private ChatColor color;

    C(ChatColor chatColor){
        this.color = chatColor;
    }

    public ChatColor color(){
        return this.color;
    }

    public static String translateColors(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
 
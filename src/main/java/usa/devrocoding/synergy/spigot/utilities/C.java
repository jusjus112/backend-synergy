package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.ChatColor;

public class C {

    public final static ChatColor CHAT_COLOUR = ChatColor.GRAY;
    public final static ChatColor PREFIX = ChatColor.GOLD;
    public final static ChatColor PRIMARY_MESSAGE = ChatColor.GRAY;
    public final static ChatColor MESSAGE_HIGHLIGHT = ChatColor.GREEN;
    public final static ChatColor ERROR_COLOUR = ChatColor.RED;
    public final static ChatColor SUCCESS_COLOUR = ChatColor.RED;
    public final static ChatColor CHAT_HIGHLIGHT_COLOUR = ChatColor.AQUA;
    public final static ChatColor TITLE_COLOR = ChatColor.GREEN;
    public final static ChatColor SUBTITLE_COLOR = ChatColor.GRAY;

    public static String translateColors(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
 
package usa.devrocoding.synergy.spigot.assets;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.assets.object.Message;

import java.util.Arrays;

public enum C {

    CHAT(ChatColor.GRAY, "colors"),
    INFO(ChatColor.YELLOW, "colors"),
    PREFIX(ChatColor.GOLD, "colors"),
    ITEM_TITLE(ChatColor.YELLOW, "colors"),
    PRIMARY(ChatColor.GRAY, "colors"),
    MESSAGE_HIGHLIGHT(ChatColor.GREEN, "colors"),
    ERROR(ChatColor.RED, "colors"),
    SUCCESS(ChatColor.RED, "colors"),
    SAM(ChatColor.LIGHT_PURPLE, "colors"),
    CHAT_HIGHLIGHT(ChatColor.AQUA, "colors"),
    SCOREBOARD_TITLE(ChatColor.GREEN, "scoreboard.colors"),
    SCOREBOARD_SUBTITLE(ChatColor.GRAY, "scoreboard.colors"),
    PLUGIN(ChatColor.YELLOW, "colors");

    public enum Symbol{
        HEARTH("❤"),
        KLAVER("♠");

        @Getter
        private String symbol;

        Symbol(String symbol){
            this.symbol = symbol;
        }
    }

    @Getter @Setter
    private ChatColor color;
    @Getter
    private String messageKey;

    C(ChatColor chatColor, String messageKey){
        this.color = chatColor;
        this.messageKey = messageKey;
    }

    public static void initColors(){
        for(C color : values()){
//            Synergy.debug(color.messageKey+" - "+color.toString().toLowerCase());
            color.setColor(
                    ChatColor.getByChar
                            (
                                    Message.format(
                                    color.messageKey+"."+color.toString().toLowerCase(),
                                    "&"+color.getColor().getChar()
                                    ).replace("&",""
                            )
                    )
            );
        }
    }

    public static String translateColors(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String getLine(){
        return "«§m----------------------------------§r»";
    }

    public static String getLineWithoutSymbols(){
        return "---------------------------------------";
    }

    public static String getLineWithName(){
        return "«"+ChatColor.STRIKETHROUGH+"-------------- "+ Core.getPlugin().getManifest().backend_name() +" -----------------"+ChatColor.RESET+"»";
    }

    public static String getLineWithName(String name){
        return "«"+ChatColor.STRIKETHROUGH+"--------------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ----------------"+ChatColor.RESET+"»";
    }

    public static String getShortLineWithName(String name){
        return "«"+ChatColor.STRIKETHROUGH+"-------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET+"»";
    }

    public static String getLineWithNameNoAttr(String name){
        return "«"+ChatColor.STRIKETHROUGH+"--------- "+C.INFO.getColor()+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------"+ChatColor.RESET+"»";
    }

    public static String getLineWithNameWithoutSymbols(String name){
        return ChatColor.STRIKETHROUGH+"-------------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" ---------------"+ChatColor.RESET;
    }

    public static String getShortLineWithNameWithoutSymbols(String name){
        return ChatColor.STRIKETHROUGH+"-------- "+ChatColor.AQUA+name+ChatColor.RESET+ChatColor.STRIKETHROUGH+" --------"+ChatColor.RESET;
    }

    public static void sendConsoleColors(String... messages) {
        Arrays.stream(messages).forEach(s -> Core.getPlugin().getServer().getConsoleSender().sendMessage(s));
    }

    public static String colorize(String value){
        return ChatColor.translateAlternateColorCodes('&',value);
    }

    @Override
    public String toString() {
        return getColor()+"";
    }
}
 
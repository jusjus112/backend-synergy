package usa.devrocoding.synergy.proxy.assets.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.StringUtils;
import usa.devrocoding.synergy.assets.Synergy;

public class UtilMOTD {

    public static String getCenteredMOTD(String first, String second){
        if (first.length() > 45 || (second == null || second.length() > 45)){
            throw new StringIndexOutOfBoundsException("A single line can only have 45 characters and cannot be null!");
        }else{
            final int max = 57;
            String first_line = centerLine(first, max),
                    second_line = centerLine(second, max);
            return ChatColor.translateAlternateColorCodes('&', first_line + "&r\n" + second_line);
        }
    }

    /**
     * Wil center the given line with the given length.
     *
     * @param s String to center
     * @param max Maximum length of the string
     */
    private static String centerLine(String s, int max){
        StringBuilder builder = new StringBuilder();
        int count = 0, colors = 0;

//        colors = StringUtils.countMatches(s, "&l");
//        max -= colors;

//        for(double i=(Math.ceil(max-s.length()))/2;i>0;i--){
//            builder.append(" ");
//        }
        builder.append(StringUtils.center(s, max));
        return builder.toString();
    }

}

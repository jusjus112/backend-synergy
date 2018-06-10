package usa.devrocoding.synergy.spigot.utilities;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

public class UtilString {

    public static String[] convert(List<String> l) {
        return l.toArray(new String[l.size()]);
    }

    public static List<String> convert(String[] array) {
        List<String> l = new ArrayList<>();
        for (String s : array) {
            l.add(s);
        }
        return l;
    }

    public static List<String> split(String string, int length) {
        List<String> l = Lists.newArrayList();
        String[] splitted = string.split("\n");
        int length1 = splitted.length;

        for(int i = 0; i < length1; ++i) {
            String a = splitted[i];
            String line = "";
            String[] cur = a.split(" ");
            int length2 = cur.length;

            for(int i1 = 0; i1 < length2; ++i1) {
                String word = cur[i1];
                line = line + word + " ";
                if (line.trim().length() >= length) {
                    l.add(line.trim());
                    line = "";
                }
            }

            line = line.trim();
            if (!line.isEmpty()) {
                l.add(line);
            }
        }

        return l;
    }

    public static String removeRecurringSpaces(String string) {
        return string.trim().replaceAll(" +", " ");
    }

    public static String getWord(String message, int index) {
        if (index < 0 || index > message.length() - 1)
            return null;
        if (index == 0 || message.substring(index - 1, index).equals(" "))
            return "";
        String word = "";
        for (int i = index; i > 0; i--) {
            String character = message.substring(i - 1, i);
            if (character.equals(" "))
                break;
            word = character + word;
        }
        for (int i = index; i < message.length(); i++) {
            String character = message.substring(i, i + 1);
            if (character.equals(" "))
                break;
            word = word + character;
        }
        return word;
    }

    public static String serialiseList(List<String> l) {
        return serialiseList(l, ":");
    }

    public static String formatWithComas(int number) {
        DecimalFormat df = new DecimalFormat("###,###,###,###,###,###,###,###,###");

        return df.format(number);
    }

    public static String serialiseList(List<String> l, String delimiter) {
        String s = "";
        for (int i = 0; i < l.size(); i++)
            s += l.get(i) + (i == l.size() - 1 ? "" : delimiter);
        return s;
    }

    public static List<String> deserialiseList(String s) {
        return deserialiseList(s, ":");
    }

    public static List<String> deserialiseList(String s, String delimiter) {
        return new ArrayList<>(Arrays.asList(s.split(delimiter)));
    }

    public static String formatTime(int time) {
        int mins = time / 60;
        int secondsInteger = time % 60;
        String seconds = String.valueOf(secondsInteger);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }
        return mins + ":" + seconds;
    }

    public static String getWhitespace(int length) {
        String s = "";
        for (int i = 0; i < length; i++) {
            s += " ";
        }
        return s;
    }

    public static String capitaliseFirstCharacter(String string) {
        if (string == null || string.length() == 0)
            return string;
        return string.substring(0, 1).toUpperCase() + string.substring(1, string.length());
    }

    public static Boolean booleanValueOf(String bool) {
        if (bool.equalsIgnoreCase("true") || bool.equals("1"))
            return true;
        if (bool.equalsIgnoreCase("false") || bool.equals("0"))
            return false;
        return null;
    }

    public static Boolean getBoolean(String bool) {
        if (bool.equals("0") || bool.equalsIgnoreCase("false") || bool.equalsIgnoreCase("off") || bool.equalsIgnoreCase("no") || bool.equalsIgnoreCase("disable") || bool.equalsIgnoreCase("disabled"))
            return false;
        if (bool.equals("1") || bool.equalsIgnoreCase("true") || bool.equalsIgnoreCase("on") || bool.equalsIgnoreCase("yes") || bool.equalsIgnoreCase("enable") || bool.equalsIgnoreCase("enabled"))
            return true;
        return null;
    }

    public static String serialiseMap(Map<String, String> map) {
        String s = "";
        for (Map.Entry<String, String> entry : map.entrySet())
            s += entry.getKey() + ":" + entry.getValue() + ",";
        s = s.substring(0, Math.max(0, s.length() - 1));
        return s;
    }

    public static Map<String, String> deserialiseMap(String string) {
        Map<String, String> map = new HashMap<>();
        for (String s : string.split(",")) {
            if (s.split(":").length != 2)
                continue;
            map.put(s.split(":")[0], s.split(":")[1]);
        }
        return map;
    }


    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
                else
                    continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0)
                return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static List<String> splitWithColumn(String s) {
        List<String> l = new ArrayList<>();
        if (s == null)
            return l;
        if (s.length() <= 1)
            return l;
        l = Arrays.asList(s.split(":"));
        return l;
    }

    public static HashSet<String> splitWithColumnHashSet(String s) {
        HashSet<String> l = new HashSet<>();
        if (s == null)
            return l;
        if (s.length() <= 1)
            return l;
        String[] array = s.split(":");
        for (String cur : array) {
            if (cur.length() <= 1)
                continue;
            l.add(cur);
        }
        return l;
    }

    public static String[] trim(String text, String colour) {
        List<String> l = new ArrayList<String>();
        int index = 0;
        while (index < text.length()) {
            l.add(colour + text.substring(index, Math.min(index + 30, text.length())));
            index += 30;
        }
        return l.toArray(new String[l.size()]);
    }

    public static List<String> trimList(String text, String colour) {
        List<String> l = new ArrayList<String>();
        int index = 0;
        while (index < text.length()) {
            l.add(colour + text.substring(index, Math.min(index + 30, text.length())));
            index += 30;
        }
        return l;
    }

    public static String[] getString(List<String> l) {
        return l.toArray(new String[l.size()]);
    }

    public static List<String> getString(String[] array) {
        List<String> l = new ArrayList<>();
        for (String s : array) {
            l.add(s);
        }
        return l;
    }

    public static boolean containsIgnoreCase(String text, String content) {
        return org.apache.commons.lang.StringUtils.containsIgnoreCase(text, content);
    }

    public static String replaceAllIgnoreCase(String text, String regex, String replacement) {
        return text.replaceAll("(?i)" + regex, replacement);
    }

    public static String removeBrackets(String text) {
        return text.replaceAll("[\\[\\](){}]", "");
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String rotateString(String s, int amount) {
        String prefix = s.substring(s.length() - amount, s.length());
        s = s.substring(0, s.length() - amount);
        return prefix + s;
    }

    public static String getNiceName(String s) {
        s = s.replaceAll("_", " ").trim().toLowerCase();
        s = capitalizeString(s);
        return s;
    }

    public static List<String> spilt(String input, int lenght) {
        return Lists.newArrayList(Splitter.fixedLength(lenght).split(input));
    }

    public static BufferedImage stringToBufferedImage(Font font, String s) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();
        g.setFont(font);

        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = font.getStringBounds(s, frc);
        g.dispose();

        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()),
                BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(java.awt.Color.black);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();

        g.drawString(s, x, y);
        g.dispose();

        return img;
    }

}
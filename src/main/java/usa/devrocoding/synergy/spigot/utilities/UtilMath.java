package usa.devrocoding.synergy.spigot.utilities;

import java.math.BigDecimal;
import org.bukkit.Location;

import java.text.DecimalFormat;
import java.util.Random;
import usa.devrocoding.synergy.includes.Synergy;

/**
 * @Author Hypixel LLC
 * @Website hypixel.net
 */
public class UtilMath {

    public static double PI = 3.141592;
    public static double e = 2.71;

    public static Random random = new Random();

    public static String formatNumber(int number) {
        String s = String.valueOf(number);
        if (s.length() <= 4)
            return s;
        for (int i = s.length() - 3; i > 0; i -= 3)
            s = s.substring(0, i) + "," + s.substring(i, s.length());
        return s;
    }

    public static int getPercentage(int fraction, int total) {
        int percent = (int) ((double) fraction / (double) total * 100);
        if (percent >= 100)
            return 100;
        return (int) ((double) fraction / (double) total * 100);
    }

    public static double trim(double d) {
        return trim(d, 1);
    }

    public static BigDecimal trimNoTrailingZeros(double d, int degree){
        return BigDecimal.valueOf(trim(d, degree)).stripTrailingZeros();
    }

    public static double trim(double d, int degree) {
        if (Double.isNaN(d) || Double.isInfinite(d))
            d = 0;
        StringBuilder format = new StringBuilder("#.#");
        for (int i = 1; i < degree; i++)
            format.append("#");
        try {
            return Double.parseDouble(new DecimalFormat(format.toString()).format(d));
        } catch (NumberFormatException exception) {
            return d;
        }

    }

    public static double square(double a) {
        return a * a;
    }

    public static String getKD(int kills, int deaths) {
        double d = (double) kills / (double) deaths;
        if (Double.isNaN(d) || Double.isInfinite(d))
            return "0.00";
        return new DecimalFormat("0.00").format(d);
    }

    public static String formatDouble1DP(double d) {
        String s = new DecimalFormat("0.0").format(d);
        if (s.length() == 1 || s.length() == 2) {
            s = s + ".0";
        }
        return s;
    }

    public static String formatDouble2DP(double d) {
        String s = new DecimalFormat("0.00").format(d);
        if (s.length() == 1 || s.length() == 2) {
            s = s + ".00";
        }
        return s;
    }

    public static String formatDouble3DP(double d) {
        String s = new DecimalFormat("0.000").format(d);
        if (s.length() == 1 || s.length() == 2) {
            s = s + ".0";
        }
        return s;
    }

    public static double getDistance2D(Location l1, Location l2) {
        double x1 = l1.getX();
        double z1 = l1.getZ();
        double x2 = l2.getX();
        double z2 = l2.getZ();
        double x = x1 - x2;
        double z = z1 - z2;
        return Math.sqrt(x * x + z * z);
    }

    public static double getX(Location l1, Location l2, double L) {
        double x1 = l1.getX();
        double x2 = l2.getX();
        double z1 = l1.getZ();
        double z2 = l2.getZ();

        double top = L * (x2 - x1);
        double bottom = Math.sqrt((x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1));

        return (top / bottom) + x2;
    }

    public static double getZ(Location l1, Location l2, double L) {
        double x1 = l1.getX();
        double x2 = l2.getX();
        double z1 = l1.getZ();
        double z2 = l2.getZ();

        double top = L * (z2 - z1);
        double bottom = Math.sqrt((x2 - x1) * (x2 - x1) + (z2 - z1) * (z2 - z1));

        return (top / bottom) + z2;
    }

    public static String formatDouble(double d) {
        String s = new DecimalFormat("0.0").format(d);
        if (s.length() == 1 || s.length() == 2) {
            s = s + ".0";
        }
        return s;
    }

    public static int addVariation(int i) {
        int half = (int) Math.ceil(Double.valueOf(i) / 2);
        return half + new Random().nextInt(half + (i % 2 == 0 ? 1 : 0));
    }

    public static boolean getChance(double percent){
        double random = getRandom(0D, 100D);
        if (random <= 1) {
            Synergy.debug(random + " = RANDOM");
            Synergy.debug(percent + " = percent");
        }
        return random <= percent;
    }

    public static boolean getChance(float percent){
        return getRandom(0D, 100D) <= percent;
    }

    public static double getRandom(double min, double max){
        return min + (max - min) * random.nextDouble();
    }

}

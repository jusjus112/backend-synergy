package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class UtilTime {

	public static boolean elapsed(long from, long required) {
		return System.currentTimeMillis() - from > required;
	}

	public static String format(long milliSeconds) {
		return format(milliSeconds / 1000D);
	}

	public static String format(double seconds) {
		// Occurs when Unix time stamp out of sync of different machines
		if (seconds < 0)
			seconds = 0;
		double minutes = seconds / 60D;
		double hours = minutes / 60D;
		double days = hours / 24D;
		double months = days / 30D;
		double years = days / 365D;
		if (minutes < 1)
			return UtilMath.trim(seconds) + " Seconds";
		else if (hours < 1)
			return UtilMath.trim(minutes) + " Minutes";
		else if (days < 1)
			return UtilMath.trim(hours) + " Hours";
		else if (months < 1)
			return UtilMath.trim(days) + " Days";
		else if (years < 1)
			return UtilMath.trim(months) + " Months";
		else
			return UtilMath.trim(years) + " Years";
	}

	public static int daysBetween(Date d1, Date d2){
		return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static double getTimeSinceLastResponseInSeconds(long lastResponse) {
		return (System.currentTimeMillis() - lastResponse) / 1000D;
	}
	
	public static String format(int mins) {
        double hours = Double.valueOf(mins) / Double.valueOf(60);
        double days = Double.valueOf(hours) / Double.valueOf(24);
        if (hours < 1)
            return new DecimalFormat("0.0").format(mins) + " Minutes";
        if (days < 1)
            return new DecimalFormat("0.0").format(hours) + " Hours";
        return new DecimalFormat("0.0").format(days) + " Days";
    }
	
	public static String simpleTimeFormat(long time) {
		DecimalFormat decimalFormat = new DecimalFormat("0.0");
		double secs = (double) (time / 1000L);
		double mins = secs / 60.0D;
		double hours = mins / 60.0D;
		double days = hours / 24.0D;
		double months = days / 30.0D;
		
		return mins < 1.0D
				? decimalFormat.format(secs) + " Seconds" : (hours < 1.0D ? decimalFormat.format(mins % 60.0D) + " Minutes" : (days < 1.0D ? decimalFormat.format(hours % 24.0D) + " Hours"
						: months < 1.0D ? decimalFormat.format(days % 30.0D) + " Days" : decimalFormat.format(months) + " Months"));
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

	public static ChatColor getTimeColour(int time) {
		double percent = Double.valueOf(time) / Double.valueOf(60 * 5) * 100;
		if (percent >= 75) {
			return ChatColor.GREEN;
		} else if (percent >= 50) {
			return ChatColor.YELLOW;
		} else if (percent >= 25) {
			return ChatColor.GOLD;
		}
		return ChatColor.RED;
	}
	
	public static String getDate(long l) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date resultdate = new Date(l);
        String date = sdf.format(resultdate);
        
        return date;
	}

	public static String formatMilliSecondsToTime(Long l)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm z");

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(l.longValue());

		return sdf.format(calendar.getTime());
	}

	public static String formatMilliSecondsToDate(Long l)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(l.longValue());

		return sdf.format(calendar.getTime());
	}
	
	public static String formatTime(long time) {
        int secs = (int) (time / 1000);
        int mins = secs / 60;
        int hours = mins / 60;
        int days = hours / 24;
        
        if (mins == 0) {
            return secs + " seconds";
        } else if (hours == 0) {
            return mins % 60 + "§7 minutes, " + "§e" + secs % 60 + "§7 seconds";
        } else if (days == 0) {
            return hours % 24 + " hours, " + mins % 60 + " minutes, " + secs % 60 + " seconds";
        } else {
            return days + " days, " + hours % 24 + " hours, " + mins % 60 + " minutes, " + secs % 60 + " seconds";
        }
    }
	
	public static String getLastLoginFormat(long lastLogin) {
		double secs = (System.currentTimeMillis() - lastLogin) / 1000;
		double mins = secs / 60;
		double hours = mins / 60;
		double days = hours / 24;
		if (mins < 1) {
			return UtilMath.formatDouble1DP(secs) + " Seconds";
		} else if (hours < 1) {
			return UtilMath.formatDouble1DP(mins % 60) + " Minutes";
		} else if (days < 1) {
			return UtilMath.formatDouble1DP(hours % 24) + " Hours";
		} else {
			return UtilMath.formatDouble1DP(days) + " Days";
		}
	}

}

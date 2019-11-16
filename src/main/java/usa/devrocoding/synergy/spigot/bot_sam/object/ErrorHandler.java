package usa.devrocoding.synergy.spigot.bot_sam.object;

import org.bukkit.ChatColor;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.C;
import usa.devrocoding.synergy.spigot.bot_sam.Sam;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ErrorHandler extends Handler {

    private Sam sam;

    public ErrorHandler(Sam sam){
        this.sam = sam;
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel() == Level.WARNING){
            try{
                error(
                        null,
                        record.getThrown()
                                != null ?
                                record.getThrown().toString() :
                                "",
                        "Check log for more details!",
                        record.getThrown()
                                .getStackTrace(),
                        record.getMessage(),
                        record.getMessage()
                                .split("]")
                                [0]
                                .replace("[", ""));
                record.setThrown(null);
                record.setMessage(null);
            }catch (Exception disabled){
//                disabled.printStackTrace();
            }
        }
    }

    @Override
    public void flush() {}

    @Override
    public void close() throws SecurityException {}

    private void save(Module module, String cause, String solution, StackTraceElement[] stackTraceElements, String message, String creator){
        try {
            Calendar calender = Calendar.getInstance();
            SimpleDateFormat fileName = new SimpleDateFormat("dd-mm-yyyy_hh-mm");

            File errorLog = new File(Core.getPlugin().getDataFolder() + File.separator+"logs"+File.separator+"errors", "ERROR_"+(fileName.format(calender.getTime()))+".txt");
            if (!errorLog.exists()) {
                errorLog.getParentFile().mkdirs();
                errorLog.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(errorLog, true);
            PrintWriter writer = new PrintWriter(fileWriter);

            SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("dd/mm/yyyy - h:mm a");

            writer.println(C.getLineWithNameNoAttr("SYNERGY"));
            writer.println("Error Date: "+dateAndTimeFormat.format(calender.getTime()));
            writer.println("Created By: "+(creator==null?"Synergy":creator));
            writer.println("Cause: "+cause);
            writer.println("Description: "+solution);
            if (module != null){
                writer.println("System: "+module.getName());
            }
            writer.println("Exception Message: "+message);
            writer.println("  ");

            if (stackTraceElements != null){
                for(StackTraceElement st : stackTraceElements){
                    writer.println("    "+st.toString());
                }
            }
            writer.println("  ");
            writer.println("  ");
            writer.flush();
            writer.close();
        }catch (Exception eb){
            error(null, "Error in my error system! Can't create the error file!", "Errorception. Contact my developer", null);
        }
    }

    public void error(Module module, String cause, String solution, StackTraceElement[] stackTraceElements, String message, String creator){
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("dd MMM, yyyy | h:mm a");
        SimpleDateFormat fileName = new SimpleDateFormat("dd-mm-yyyy_hh-mm");

        String[] consoleMessage = new String[]{
                C.getLineWithNameWithoutSymbols(ChatColor.YELLOW+"Synergy "+ChatColor.AQUA+"</>"),
                ChatColor.RED+"We've intercepted an error for you!",
                ChatColor.RED+(stackTraceElements == null ? "It hurts when something isn't working!" : "I have saved this error for you to 'plugins/Synergy/logs/errors/ERROR_"+fileName.format(calender.getTime())+".txt'"),
                ChatColor.RED+"Error Date: "+ChatColor.AQUA+dateAndTimeFormat.format(calender.getTime()),
                ChatColor.RED+"Cause: "+ChatColor.AQUA+cause,
                ChatColor.RED+"What might help: "+ChatColor.AQUA+solution,
                stackTraceElements == null ? ChatColor.RED+"I didn't create a file for you ;(" : ChatColor.RED+"Check the error log for my detailed version!",
                ChatColor.YELLOW+C.getLineWithoutSymbols()+ChatColor.RESET
        };

        if (stackTraceElements != null){
            save(module, cause, solution, stackTraceElements, message, creator);
        }

        C.sendConsoleColors(consoleMessage);
    }

    public void error(Module module, String cause, String solution, Exception e){
        error(module, cause, solution, e.getStackTrace(), e.getMessage(), "Sam the robot (Synergy)");
    }

}

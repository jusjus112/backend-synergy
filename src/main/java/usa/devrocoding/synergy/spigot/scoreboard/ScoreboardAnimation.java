package usa.devrocoding.synergy.spigot.scoreboard;

import usa.devrocoding.synergy.spigot.Core;

public class ScoreboardAnimation {

    public static String text = "  "+Core.getPlugin().getManifest().server_name()+"  ";
    public static String frame;

    String primary = "§e§l";
    String secondary = "§5§l";
    String transition = "§d§l";
    int counter = -1;
    boolean stage = false;

    public ScoreboardAnimation(){
        Core.getPlugin().getRunnableManager().runTaskTimerAsynchronously("scoreboard animation", core -> {
            update();
        }, 55, 2);
    }

    public void update() {
        if (Core.getPlugin().getScoreboardManager().getScoreboards().isEmpty()){
            return;
        }

        counter++;
        String s = "";
        if (counter < 0) {
            if (counter > -48 && counter < -36) {
                if (counter % 4 == 0) {
                    stage = !stage;
                }
                s = (stage ? primary : secondary) + text;
            } else {
                if (counter >= -36) {
                    s = primary + text;
                } else {
                    s = secondary + text;
                }
            }
        } else {
            if (counter == 0) {
                s = primary + text;
            } else if (counter == text.length() + 1) {
                s = secondary + text;
                counter = -52;
            } else {
                s = secondary + text.substring(0, counter - 1) + transition + text.substring(counter - 1, counter)
                        + primary + text.substring(counter, text.length());
            }
        }
        frame = s;
        set(s);
    }

    private void set(String s) {
        for (ZylemBoard zylemBoard : Core.getPlugin().getScoreboardManager().getScoreboards().values()) {
            if (zylemBoard != null && zylemBoard.getSidebarObjective() != null) {
                zylemBoard.
                        getSidebarObjective().
                        setDisplayName(
                                s
                        );
            }
        }
    }

}

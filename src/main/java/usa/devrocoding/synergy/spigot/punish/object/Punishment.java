package usa.devrocoding.synergy.spigot.punish.object;

import lombok.Getter;
import lombok.Setter;
import usa.devrocoding.synergy.spigot.punish.PunishCategory;
import usa.devrocoding.synergy.spigot.punish.PunishLevel;
import usa.devrocoding.synergy.spigot.punish.PunishType;
import usa.devrocoding.synergy.spigot.utilities.UtilTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

public class Punishment {

    @Getter
    private UUID punished;
    @Getter
    private PunishType type;
    @Getter
    private PunishCategory category;
    @Getter
    private PunishLevel level;
    @Getter
    private long issued;
    @Getter
    private long till;
    @Getter
    private UUID Punisher;
    @Setter
    private boolean active;

    public Punishment(UUID punished, PunishType punishType, PunishCategory punishCategory, PunishLevel punishLevel,
                      long issued, long till, UUID punisher, boolean active){
        this.punished = punished;
        this.type = punishType;
        this.category = punishCategory;
        this.level = punishLevel;
        this.issued = issued;
        this.till = till;
        this.Punisher = punisher;
        this.active = active;
        if (type == PunishType.WARNING) {
            this.active = false;
        }
    }

    public String getBanMessage() {
        return "§7§l<§e/§7§l> §f§lYOUR ACCOUNT HAS BEEN §c§lBANNED §f§lFROM MIRAGEPRISONS §7§l<§e/§7§l>"+"\n"+
                " \n"+
//                "§7Banned by: §e"+this.getPunisherName()+"\n"+
                "§7Ban Category: §c"+this.getCategory().getName()+"\n"+
                "§7Ban Level: §c"+this.getLevel().getName()+"\n"+
                " \n"+
                (isPermanent() ? "§f§lThis ban is §c§lPERMANENT" : "§f§lUNBANNED ON §b§l"+this.getPlainerMessage())+"\n"+
                "§7You may appeal your ban at §ewww.mirageprisons.net";
    }

    public boolean isActive() {
        if (!active)
            return false;
        if (isPermanent())
            return true;
        return getTill() > System.currentTimeMillis();
    }

    public String getIssuedFormatted() {
        long yourmilliseconds = getIssued();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date resultdate = new Date(yourmilliseconds);
        String data = sdf.format(resultdate);
        return data;
    }

    public boolean isPermanent() {
        return getTill() < 0L;
    }

    public int getMinutesActive() {
        return (int) (((getTill() - getIssued()) / 1000) / 60);
    }

    public String getPlainerMessage() {
        if (isPermanent()){
            return "§cPermanent Punishment";
        }
        return UtilTime.formatMilliSecondsToDate(getTill())+" "+UtilTime.formatMilliSecondsToTime(getTill());
    }

}

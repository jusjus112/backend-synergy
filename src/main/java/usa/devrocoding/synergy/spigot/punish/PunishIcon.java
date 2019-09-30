package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.spigot.utilities.ItemBuilder;
import usa.devrocoding.synergy.spigot.utilities.UtilTime;

import java.util.Arrays;

public enum PunishIcon {

    GeneralOne(
            PunishCategory.Algemeen,
            PunishLevel.one,
            PunishType.Ban,
            600000,
            new String[] {
                "§e§lMisbruik van report/support", "Report: JusJus is de beste developer ooit!"
            }),
    GeneralTwo(
            PunishCategory.Algemeen, PunishLevel.two, PunishType.Ban, 1209600000,
            new String[] { "§e§lBug abuse", "Terug springen in de safe zone", "of moneyfarming" }),
    ChatOne(
            PunishCategory.Chat, PunishLevel.one, PunishType.Mute, 259200000L,
            new String[] { "§e§lLicht schelden", "Je zuigt", "Geen respect naar staff", "",
                    "§e§lLicht spammen", "LOLOLOLOLOLOLOL.", "", "§e§lLigt adverteren",
                    "Hey iemand hypixel?", }),
    ChatTwo(PunishCategory.Chat, PunishLevel.two,
            PunishType.Mute, 604800000,
            new String[] { "§e§lMedium spam",
                    "omg mist69 OMG PLEASE IK WIL EEN SCREENSHOT!", "",
                    "§e§lMedium Advertisment",
                    "Hey mensen join mijn server stopreadingthip:25544 ",
                    "voor gratis spullen en staff.", "", "§e§lMedium schelden",
                    "Je bent een debiel" }),
    ChatThree(PunishCategory.Chat,
            PunishLevel.three, PunishType.Mute, 1209600000,
            new String[] { "§e§lZwaar Adverteren",
                    "SNEL JOIN ME SERVER PLAY.THENOOTMC.NET IK GEEF STAFF ",
                    "AAN DE EERSTE 5 MENSEN!!!!!!!11", "",
                    "§e§lZwaar schelden", "Je bent een kanker debiel",
                    "", "§e§lZwaar spammen",
                    "Heel vaak hetzelfde zeggen" }),
    HackingOne(
            PunishCategory.hacking, PunishLevel.one,
            PunishType.Ban, 604800000L,
            new String[] { "§e§lLichte hacks",
                    "Gebruik maken van anti-afk, auto eat of derp hacks." }),
    HackingTwo(
            PunishCategory.hacking,
            PunishLevel.two,
            PunishType.Ban, 2592000000L,
            new String[] {
                    "§e§lMedium hacks",
                    "Gameplay aanpassende mods",
                    "bijvoorbeeld pvp-mods of auto speed" }),
    HackingThree(
            PunishCategory.hacking,
            PunishLevel.three,
            PunishType.Ban,
            5184000000L,
            new String[] {
                    "§e§lZware hacks",
                    "Xrax, nodes, wurst, anti kb, killaura etc", }),
    Warning(
            PunishCategory.Overig,
            PunishLevel.warning,
            PunishType.Warning,
            -1,
            new String[] {
                    "Bijvoorbeeld kleine fouten zoals",
                    "adverteren, spammen of overtollig caps gebruik.",
                    "Als ze door dan de punishment geven die erbij hoort" }),
    PermanentMute(
            PunishCategory.Overig,
            PunishLevel.permanentMute,
            PunishType.Mute,
            -1,
            new String[] {
                    "Vaak gemute worden zorgt ervoor dat die nooit meer kan praten", }),
    PermanentBan(
            PunishCategory.Overig,
            PunishLevel.permanentBan,
            PunishType.Ban,
            -1,
            new String[] {
                    "Fraude op de buycraft, alternate account of VPN",
                    "Stop een speler om ooit nog te joinen." });
    ;

    @Getter
    private PunishCategory category;
    @Getter
    private PunishLevel punishLevel;
    @Getter
    private PunishType type;
    @Getter
    private long punishTime;
    @Getter
    private String reason;
    @Getter
    private String[] description;

    private PunishIcon(PunishCategory category, PunishLevel level, PunishType type, long punishTime, String reason) {
        this.category = category;
        this.punishLevel = level;
        this.type = type;
        this.punishTime = punishTime;
        this.reason = reason;
        this.description = null;
    }

    private PunishIcon(PunishCategory category, PunishLevel level, PunishType type, long punishTime, String[] description) {
        this.category = category;
        this.punishLevel = level;
        this.type = type;
        this.punishTime = punishTime;
        this.description = description;
    }

    public ItemStack getItemStack(){
        return new ItemBuilder(getPunishLevel().getItemStack().getType())
                .setName("§b"+getPunishLevel().getName())
                .addLore("Straffen: "+ UtilTime.simpleTimeFormat(getPunishTime()) + " "+getType().name())
                .addLore(getDescription())
                .build();
    }

}

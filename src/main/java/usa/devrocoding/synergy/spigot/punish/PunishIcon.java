package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;

public enum PunishIcon {

    GENERALONE(
            PunishCategory.GENERAL,
            PunishLevel.ONE,
            PunishType.BAN,
            600000,
            new String[] {
                "§e§lAbuse of commands", "[Report]: Love this server"
            }),
    GENERALTWO(
            PunishCategory.GENERAL, PunishLevel.TWO, PunishType.BAN, 1209600000,
            new String[] { "§e§lBug abuse", "Using bugs to get an advantage.", "Or moneyfarming." }),
    CHATONE(
            PunishCategory.CHAT, PunishLevel.ONE, PunishType.MUTE, 259200000L,
            new String[] { "§e§lLight Cursing", "Je zuigt", "Geen respect naar staff", "",
                    "§e§lLicht spammen", "LOLOLOLOLOLOLOL.", "", "§e§lLigt adverteren",
                    "Hey iemand hypixel?", }),
    CHATTWO(PunishCategory.CHAT, PunishLevel.TWO,
            PunishType.MUTE, 604800000,
            new String[] { "§e§lMedium Spam",
                    "omg mist69 OMG PLEASE IK WIL EEN SCREENSHOT!", "",
                    "§e§lMedium Advertisment",
                    "Hey mensen join mijn server stopreadingthip:25544 ",
                    "voor gratis spullen en staff.", "", "§e§lMedium schelden",
                    "Je bent een debiel" }),
    CHATTHREE(PunishCategory.CHAT,
            PunishLevel.THREE, PunishType.MUTE, 1209600000,
            new String[] { "§e§lHeavy Advertising",
                    "SNEL JOIN ME SERVER PLAY.THENOOTMC.NET IK GEEF STAFF ",
                    "AAN DE EERSTE 5 MENSEN!!!!!!!11", "",
                    "§e§lZwaar schelden", "Je bent een kanker debiel",
                    "", "§e§lZwaar spammen",
                    "Heel vaak hetzelfde zeggen" }),
    HACKINGONE(
            PunishCategory.HACKING, PunishLevel.ONE,
            PunishType.BAN, 604800000L,
            new String[] { "§e§lLow Tier Modded Client",
                    "Gebruik maken van anti-afk, auto eat of derp hacks." }),
    HACKINGTWO(
            PunishCategory.HACKING,
            PunishLevel.TWO,
            PunishType.BAN, 2592000000L,
            new String[] {
                    "§e§lMedium Tier Modded Client",
                    "Gameplay changing mods (Killaura is heavy)",
                    "pvp-mods or auto speed" }),
    HACKINGTHREE(
            PunishCategory.HACKING,
            PunishLevel.THREE,
            PunishType.BAN,
            5184000000L,
            new String[] {
                    "§e§lHeavy Tier Modded Client",
                    "Xray, nodes, wurst, anti kb, killaura etc", }),
    WARNING(
            PunishCategory.OTHER,
            PunishLevel.WARNING,
            PunishType.WARNING,
            -1,
            new String[] {
                    "Notify the player that they made a mistake.",
                    "Advertising or spamming.",
                    "Als ze door dan de punishment geven die erbij hoort" }),
    PermanentMUTE(
            PunishCategory.OTHER,
            PunishLevel.PermanentMute,
            PunishType.MUTE,
            -1,
            new String[] {
                    "Remove the ability to speak from the player. Permanent.", }),
    PermanentBAN(
            PunishCategory.OTHER,
            PunishLevel.PermanentBan,
            PunishType.BAN,
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

}

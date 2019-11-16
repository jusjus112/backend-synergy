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
            new String[] { "§e§lBug abuse", "Terug springen in de safe zONE", "of mONEyfarming" }),
    CHATONE(
            PunishCategory.CHAT, PunishLevel.ONE, PunishType.MUTE, 259200000L,
            new String[] { "§e§lLicht schelden", "Je zuigt", "Geen respect naar staff", "",
                    "§e§lLicht spammen", "LOLOLOLOLOLOLOL.", "", "§e§lLigt adverteren",
                    "Hey iemand hypixel?", }),
    CHATTWO(PunishCategory.CHAT, PunishLevel.TWO,
            PunishType.MUTE, 604800000,
            new String[] { "§e§lMedium spam",
                    "omg mist69 OMG PLEASE IK WIL EEN SCREENSHOT!", "",
                    "§e§lMedium Advertisment",
                    "Hey mensen join mijn server stopreadingthip:25544 ",
                    "voor gratis spullen en staff.", "", "§e§lMedium schelden",
                    "Je bent een debiel" }),
    CHATTHREE(PunishCategory.CHAT,
            PunishLevel.THREE, PunishType.MUTE, 1209600000,
            new String[] { "§e§lZwaar Adverteren",
                    "SNEL JOIN ME SERVER PLAY.THENOOTMC.NET IK GEEF STAFF ",
                    "AAN DE EERSTE 5 MENSEN!!!!!!!11", "",
                    "§e§lZwaar schelden", "Je bent een kanker debiel",
                    "", "§e§lZwaar spammen",
                    "Heel vaak hetzelfde zeggen" }),
    HACKINGONE(
            PunishCategory.HACKING, PunishLevel.ONE,
            PunishType.BAN, 604800000L,
            new String[] { "§e§lLichte hacks",
                    "Gebruik maken van anti-afk, auto eat of derp hacks." }),
    HACKINGTWO(
            PunishCategory.HACKING,
            PunishLevel.TWO,
            PunishType.BAN, 2592000000L,
            new String[] {
                    "§e§lMedium hacks",
                    "Gameplay aanpassende mods",
                    "bijvoorbeeld pvp-mods of auto speed" }),
    HACKINGTHREE(
            PunishCategory.HACKING,
            PunishLevel.THREE,
            PunishType.BAN,
            5184000000L,
            new String[] {
                    "§e§lZware hacks",
                    "Xrax, nodes, wurst, anti kb, killaura etc", }),
    WARNING(
            PunishCategory.OTHER,
            PunishLevel.WARNING,
            PunishType.WARNING,
            -1,
            new String[] {
                    "Bijvoorbeeld kleine fouten zoals",
                    "adverteren, spammen of overtollig caps gebruik.",
                    "Als ze door dan de punishment geven die erbij hoort" }),
    PermanentMUTE(
            PunishCategory.OTHER,
            PunishLevel.PermanentMute,
            PunishType.MUTE,
            -1,
            new String[] {
                    "Vaak geMUTE worden zorgt ervoor dat die nooit meer kan praten", }),
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

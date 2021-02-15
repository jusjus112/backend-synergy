package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;
import usa.devrocoding.synergy.assets.Rank;

@Getter
public enum PunishIcon {

    GENERALONE(
            PunishCategory.GENERAL,
            PunishLevel.ONE,
            PunishType.BAN,
            600000,
            Rank.HELPER,
            "Abuse of commands",
            new String[] {
                "Using commands for other purposes ",
                "other than what it is used for."
            }),
    GENERALTWO(
            PunishCategory.GENERAL, PunishLevel.TWO, PunishType.BAN, 1209600000,
            Rank.HELPER,"Bug abuse",
            new String[] { "Using bugs to get an advantage.", "Finding bugs and not reporting them." }),
    CHATONE(
            PunishCategory.CHAT, PunishLevel.ONE, PunishType.MUTE, 259200000L,
            Rank.HELPER,"Light Cursing",
            new String[] {
                "Cursing in chat which is not hurting people",
                "but are not community friendly.",
                "This also includes advertising."
            }),
    CHATTWO(PunishCategory.CHAT, PunishLevel.TWO,
            PunishType.MUTE, 604800000,
            Rank.JRMODERATOR,"Medium Spam",
            new String[] {
                "Cursing in chat what is hurting people.",
                "Saying bad things to each other.",
                "This also includes advertising."
            }
    ),
    CHATTHREE(PunishCategory.CHAT,
            PunishLevel.THREE, PunishType.MUTE, 1209600000,
            Rank.SRMODERATOR,"Heavy Advertising",
            new String[] {
                "Cursing in chat what is extremely hurting people.",
                "Saying really bad stuff to each other.",
                "This also includes highly advertising."
            }),
    HACKINGONE(
            PunishCategory.HACKING, PunishLevel.ONE,
            PunishType.BAN, 604800000L,
            Rank.HELPER,"Low Tier Modded Client",
            new String[] {
                "Using an anti-afk tool.",
                "Or a minimap to get an advantage in PVP.",
                "Low tier mod clients is not hacking, but not allowed.."
            }),
    HACKINGTWO(
            PunishCategory.HACKING,
            PunishLevel.TWO,
            PunishType.BAN, 2592000000L,
            Rank.SRMODERATOR,"Medium Tier Modded Client",
            new String[] {
                "PVP mods, or hacks such as X-Ray",
                "and gameplay changing mods."
            }),
    HACKINGTHREE(
            PunishCategory.HACKING,
            PunishLevel.THREE,
            PunishType.BAN,
            5184000000L,
            Rank.SRMODERATOR,"Heavy Tier Modded Client",
            new String[] {
                "All hack clients, that includes fly, killaura and more.",
                "Gameplay chaning hack clients, which are absurd."
            }),
    WARNING(
            PunishCategory.OTHER,
            PunishLevel.WARNING,
            PunishType.WARNING,
            -1,
            Rank.HELPER,null,
            new String[] {
                    "Notify the player that they made a mistake.",
                    "Advertising or spamming.",
                    "After 3+ warnings, they'll get a automatic ban." }),
    PermanentMUTE(
            PunishCategory.OTHER,
            PunishLevel.PermanentMute,
            PunishType.MUTE,
            -1,
            Rank.SRMODERATOR,null,
            new String[] {
                    "Remove the ability to speak from the player. Permanent.", }),
    PermanentBAN(
            PunishCategory.OTHER,
            PunishLevel.PermanentBan,
            PunishType.BAN,
            -1,
            Rank.ADMIN,null,
            new String[] {
                "Scamming us on the buycraft.",
                "Or breaking some other highly not allowed rule.",
                "This will ban the player forever."
            }),
    KICK(
        PunishCategory.OTHER,
        PunishLevel.KICK,
        PunishType.KICK,
        1,
        Rank.HELPER,null,
        new String[] {
            "Kick this player from this server."
        });
    ;

    private final PunishCategory category;
    private final PunishLevel punishLevel;
    private final PunishType type;
    private final Rank neededRank;
    private final long punishTime;
    private String reason;
    private final String name;
    private final String[] description;

    PunishIcon(PunishCategory category, PunishLevel level, PunishType type, long punishTime, Rank neededRank, String name, String reason) {
        this(category, level, type, punishTime, neededRank, name, new String[]{});

        this.reason = reason;
    }

    PunishIcon(PunishCategory category, PunishLevel level, PunishType type, long punishTime, Rank neededRank, String name, String[] description) {
        this.category = category;
        this.punishLevel = level;
        this.type = type;
        this.neededRank = neededRank;
        this.name = name;
        this.punishTime = punishTime;
        this.description = description;
    }

}

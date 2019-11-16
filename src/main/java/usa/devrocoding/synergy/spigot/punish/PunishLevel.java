package usa.devrocoding.synergy.spigot.punish;

public enum PunishLevel {

    ONE(
            "Strength 1",
            1
    ),
    TWO(
            "Strength 2",
            1
    ),
    THREE(
            "Strength 3",
            1
    ),
    WARNING(
            "Waarschuwing",
            3
    ),
    PermanentMute(
            "Pernamenten Mute",
            3
    ),
    PermanentBan(
            "Pernamenten Ban",
            3
    );

    private String name;
    private int severity;

    private PunishLevel(String name, int severity) {
        this.name = name;
        this.severity = severity;
    }

    public String getName() {
        return name;
    }

    public int getSeverity() {
        return severity;
    }

}

package usa.devrocoding.synergy.spigot.punish;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
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
            "Warning",
            3
    ),
    PermanentMute(
            "Permanent Mute",
            3
    ),
    PermanentBan(
            "Permanent Ban",
            3
    ),
    KICK(
        "Kick",
        1
    );

    private final String name;
    private final int severity;

}

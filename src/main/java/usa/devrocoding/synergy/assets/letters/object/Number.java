package usa.devrocoding.synergy.assets.letters.object;

import lombok.Getter;

public enum Number {

    ONE(
            "  _ ",
            " / |",
            " | |",
            " | |",
            " |_|"
    ),
    TWO(
            "  ____  ",
            " |___ \\",
            "   __) |",
            "  / __/ ",
            " |_____|"
    ),
    THREE(
            "  _____ ",
            " |___ / ",
            "   |_ \\ ",
            "  ___) |",
            " |____/ "
    ),
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    ZERO;

    @Getter
    private String[] converted;

    Number(String... converted){
        this.converted = converted;
    }

    public static Number getNumber(int number){
        switch (number){
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            case 9:
                return NINE;
            default:
                return ZERO;
        }
    }

}

package usa.devrocoding.synergy.spigot.gui.object;

import lombok.Getter;

public enum GuiSize {
    ONE_ROW(9),
    TWO_ROWS(18),
    THREE_ROWS(27),
    FOUR_ROWS(36),
    FIVE_ROWS(45),
    SIX_ROWS(54);

    @Getter
    private final int slots;

    GuiSize(int slots){
        this.slots = slots;
    }

    public static GuiSize match(String text){
        return valueOf(text.toUpperCase());
    }

}

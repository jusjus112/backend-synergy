package usa.devrocoding.synergy.includes;

import lombok.Getter;

public enum PluginMessageType {

    ACTION,
    STAFF_MESSAGE,
    AUTOMATED_ERROR,
    PLAYER_MESSAGE;

    @Getter
    private String ID;

    PluginMessageType(){
        this.ID = super.toString().toUpperCase();
    }

    @Override
    public String toString() {
        return getID();
    }
}

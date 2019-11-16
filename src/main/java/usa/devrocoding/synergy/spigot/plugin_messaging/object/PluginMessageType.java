package usa.devrocoding.synergy.spigot.plugin_messaging.object;

import lombok.Getter;

public enum PluginMessageType {

    ACTION,
    STAFF_MESSAGE,
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

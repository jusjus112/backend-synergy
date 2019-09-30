package usa.devrocoding.synergy.spigot.plugin_messaging.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;

public class SynergyReceiveEvent extends SynergyEvent {

    @Getter
    private String channel, content;

    public SynergyReceiveEvent(String channel, String content){
        this.channel = channel;
        this.content = content;
    }

}

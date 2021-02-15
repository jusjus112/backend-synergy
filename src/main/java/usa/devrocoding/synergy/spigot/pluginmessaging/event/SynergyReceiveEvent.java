package usa.devrocoding.synergy.spigot.pluginmessaging.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.assets.PluginMessageBuilder;

public class SynergyReceiveEvent extends SynergyEvent {

    @Getter
    private String rawContent;
    @Getter
    private PluginMessageBuilder pluginMessage;

    public SynergyReceiveEvent(String content, PluginMessageBuilder pluginMessage){
        this.rawContent = content;
        this.pluginMessage = pluginMessage;
    }

}

package usa.devrocoding.synergy.spigot.plugin_messaging.event;

import lombok.Getter;
import usa.devrocoding.synergy.spigot.listeners.SynergyEvent;
import usa.devrocoding.synergy.spigot.plugin_messaging.PluginMessage;

public class SynergyReceiveEvent extends SynergyEvent {

    @Getter
    private String rawContent;
    @Getter
    private PluginMessage pluginMessage;

    public SynergyReceiveEvent(String content, PluginMessage pluginMessage){
        this.rawContent = content;
        this.pluginMessage = pluginMessage;
    }

}

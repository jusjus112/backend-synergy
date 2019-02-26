package usa.devrocoding.synergy.proxy.plugin_messaging.object;

import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

public class SynergyPMEvent extends Event {

    @Getter
    private String channel;
    @Getter
    private Object data;
    @Getter
    private ProxiedPlayer receiver;

    public SynergyPMEvent(String channel, Object data, ProxiedPlayer receiver){
        this.channel = channel;
        this.data = data;
        this.receiver = receiver;
    }

}

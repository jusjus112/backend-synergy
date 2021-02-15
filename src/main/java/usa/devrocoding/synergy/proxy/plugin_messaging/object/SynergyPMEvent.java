package usa.devrocoding.synergy.proxy.plugin_messaging.object;

import lombok.Getter;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.Nullable;
import usa.devrocoding.synergy.assets.PluginMessageType;

public class SynergyPMEvent extends Event {

    @Getter
    private PluginMessageType type;
    @Getter
    private String[] data;
    @Getter
    private ProxiedPlayer receiver;
    @Getter @Nullable
    private ProxiedPlayer target;

    public SynergyPMEvent(PluginMessageType type, ProxiedPlayer receiver, ProxiedPlayer target, String[] content){
        this.type = type;
        this.receiver = receiver;
        this.target = target;
        this.data = content;
    }

}

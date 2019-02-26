package usa.devrocoding.synergy.proxy.plugin_messaging;

import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.plugin_messaging.listener.PluginMessageListener;

public class PluginMessaging extends ProxyModule {

    private String[] channels = new String[]{
            "BungeeCord"
    };

    public PluginMessaging(Core plugin){
        super(plugin, "PluginMessage Manager", false);

        registerListeners(
                new PluginMessageListener()
        );
        registerChannels();
    }

    @Override
    public void reload() {

    }

    @Override
    public void deinit() {

    }

    public void registerChannels(){
        for(String channel : this.channels){
            getPlugin().getProxy().registerChannel(channel);
        }
    }

}

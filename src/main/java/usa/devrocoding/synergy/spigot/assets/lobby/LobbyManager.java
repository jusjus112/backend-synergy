package usa.devrocoding.synergy.spigot.assets.lobby;

import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.assets.lobby.server_selector.ServerSelector;

public class LobbyManager extends Module {

    private ServerSelector serverSelector;

    public LobbyManager(Core plugin){
        super(plugin, "Lobby manager", true);

        this.serverSelector = new ServerSelector();
        this.serverSelector.setup();
    }

    @Override
    public void reload(String response) {
        this.serverSelector.loadData();
    }
}

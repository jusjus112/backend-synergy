package usa.devrocoding.synergy.proxy.party;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.party.command.CommandParty;
import usa.devrocoding.synergy.proxy.party.object.Party;

import java.util.HashMap;
import java.util.Map;

public class PartyManager extends ProxyModule {

    private Map<ProxiedPlayer, Party> parties = new HashMap<>();

    public PartyManager(Core plugin){
        super(plugin, "Party Manager", false);

        registerCommands(
                new CommandParty()
        );
    }

    @Override
    public void reload() {

    }

    @Override
    public void deinit() {

    }

    public void addParty(Party party){
        this.parties.put(party.getHost(), party);
    }

    public Party getParty(ProxiedPlayer proxiedPlayer) throws NullPointerException{
        return this.parties.get(proxiedPlayer);
    }
}

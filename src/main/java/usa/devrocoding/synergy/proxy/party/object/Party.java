package usa.devrocoding.synergy.proxy.party.object;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import usa.devrocoding.synergy.proxy.Core;

import java.util.ArrayList;
import java.util.List;

public class Party {

    @Getter
    private List<ProxiedPlayer> members = new ArrayList<>();
    @Getter @Setter
    private ProxiedPlayer host;
    @Getter
    private List<ProxiedPlayer> invitedPlayers = new ArrayList<>();

    public Party(ProxiedPlayer player){
        this.host = player;

        Core.getCore().getPartyManager().addParty(this);
    }

    public void disband(){

    }

}

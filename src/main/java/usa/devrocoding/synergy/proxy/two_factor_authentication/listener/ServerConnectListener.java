package usa.devrocoding.synergy.proxy.two_factor_authentication.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.two_factor_authentication.GoogleAuthManager;

public class ServerConnectListener implements Listener {

    @EventHandler
    public void onLogin(ServerConnectedEvent e){
        GoogleAuthManager gam = Core.getCore().getGoogleAuthManager();

        if (gam.has2fa(e.getPlayer())){
            String key = gam.getCachedKey(e.getPlayer());

            if (key != null) {
                if (e.getPlayer().isConnected()) {
                    e.getPlayer().sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName("2FA")));
                    e.getPlayer().sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "Because of your rank you have")
                    );
                    e.getPlayer().sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "to fill in a 2FA verification code!")
                    );
                    e.getPlayer().sendMessage(new TextComponent(
                            Synergy.SynergyColor.PREFIX.getColor() + "Google Auth Code: §b§l" + key)
                    );
                    e.getPlayer().sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "Fill in this code in the google auth app.")
                    );
                    e.getPlayer().sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "And use " + Synergy.SynergyColor.PREFIX.getColor() + "/2fa <code>")
                    );
                    e.getPlayer().sendMessage(new TextComponent(Synergy.SynergyColor.getLine()));

                }
            }
        }
    }

}

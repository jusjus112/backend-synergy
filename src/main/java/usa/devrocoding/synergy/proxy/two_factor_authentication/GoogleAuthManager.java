package usa.devrocoding.synergy.proxy.two_factor_authentication;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import usa.devrocoding.synergy.assets.Synergy;
import usa.devrocoding.synergy.proxy.Core;
import usa.devrocoding.synergy.proxy.ProxyModule;
import usa.devrocoding.synergy.proxy.two_factor_authentication.listener.ServerConnectListener;
import usa.devrocoding.synergy.proxy.two_factor_authentication.command.Command2FA;
import usa.devrocoding.synergy.proxy.two_factor_authentication.listener.LoginListener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleAuthManager extends ProxyModule implements ICredentialRepository {

    public GoogleAuthManager(Core plugin){
        super(plugin, "GoogleAuth Manager", false);

        registerListeners(
                new LoginListener()
//                new ServerConnectListener()
        );

        registerCommands(
                new Command2FA()
        );
    }

    @Override
    public void reload() {

    }

    @Override
    public void deinit() {

    }

    @Override
    public String getSecretKey(String userName) {
        return userName;
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {}

    final long KEY_VALIDATION_INTERVAL_MS = TimeUnit.SECONDS.toMillis(30);
    int lastUsedPassword = -1; // last successfully used password
    long lastVerifiedTime = 0; // time of last success
    final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    AtomicInteger windowSize = new AtomicInteger(3);

    private Map<ProxiedPlayer, String> notFilledInPlayers = new HashMap<>();

    public boolean has2fa(ProxiedPlayer player){
        return this.notFilledInPlayers.containsKey(player);
    }

    //User user
    public String getTwoFactorKey(UUID uuid) {
        gAuth.setCredentialRepository(this);
        final GoogleAuthenticatorKey googleAuthkey = gAuth.createCredentials(uuid.toString());
        return googleAuthkey.getKey();
    }

    public boolean validate(String secret, int code){
        gAuth.setCredentialRepository(this);
        return gAuth.authorize(secret, code);
    }

    public void enable2faMode(ProxiedPlayer player, String key){
        this.notFilledInPlayers.put(player, key);

        GoogleAuthManager gam = Core.getCore().getGoogleAuthManager();

        if (gam.has2fa(player)){
            if (key != null) {
                if (player.isConnected()) {
                    player.sendMessage(new TextComponent(Synergy.SynergyColor.getLineWithName("2FA")));
                    player.sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "Because of your rank you have")
                    );
                    player.sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "to fill in a 2FA verification code!")
                    );
                    player.sendMessage(new TextComponent(
                            Synergy.SynergyColor.PREFIX.getColor() + "Google Auth Code: §b§l" + key)
                    );
                    player.sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "Fill in this code in the google auth app.")
                    );
                    player.sendMessage(new TextComponent(
                            Synergy.SynergyColor.INFO.getColor() + "And use " + Synergy.SynergyColor.PREFIX.getColor() + "/2fa <code>")
                    );
                    player.sendMessage(new TextComponent(Synergy.SynergyColor.getLine()));
                }
            }
        }
    }

    public void disable2faMode(ProxiedPlayer player){
        if (this.notFilledInPlayers.containsKey(player)){
            this.notFilledInPlayers.remove(player);
        }
    }

    public String getCachedKey(ProxiedPlayer player){
        if (this.notFilledInPlayers.containsKey(player)){
            return this.notFilledInPlayers.get(player);
        }
        return null;
    }

    public ResultSet getUserData(String uuid){
        try {
            ResultSet resultSet = Core.getCore().getDatabaseManager().getResults(
                    "SELECT * FROM two_factor_authentication WHERE uuid = '"+uuid+"'"
            );
            return resultSet;
        }catch (SQLException e){
            Synergy.error(e.getMessage());
        }
        return null;
    }

    public void addToDatabase(ProxiedPlayer player, String key){
        Core.getCore().getDatabaseManager().execute("two_factor_authentication", new HashMap<String, Object>() {{
            put("uuid", player.getUniqueId().toString());
            put("name", player.getName());
            put("key", key);
        }});
    }

}

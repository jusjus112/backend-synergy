package usa.devrocoding.synergy.spigot.two_factor_authentication;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import usa.devrocoding.synergy.includes.Synergy;
import usa.devrocoding.synergy.includes.TinyURL;
import usa.devrocoding.synergy.services.sql.SQLDataType;
import usa.devrocoding.synergy.services.sql.SQLDefaultType;
import usa.devrocoding.synergy.services.sql.TableBuilder;
import usa.devrocoding.synergy.services.sql.UtilSQL;
import usa.devrocoding.synergy.spigot.Core;
import usa.devrocoding.synergy.spigot.Module;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilString;

@Getter
public class GoogleAuthManager extends Module implements ICredentialRepository {

    /*
    https://www.google.com/chart?chs=128x128&cht=qr&chl=otpauth://totp/JusJusOneOneTwo?secret=KDNHMUX6SJ5TMJCS
     */

    private final Map<SynergyUser, Location> teleportedToSpawn;
    private final List<UUID> filledInPlayers;

    public GoogleAuthManager(Core plugin){
        super(plugin, "GoogleAuth Manager", false);

        // Generate Tables
        new TableBuilder("two_factor_authentication", plugin.getDatabaseManager())
            .addColumn("uuid", SQLDataType.BINARY, 16,false, SQLDefaultType.NO_DEFAULT, true)
            .addColumn("key", SQLDataType.VARCHAR, 100,false, SQLDefaultType.NO_DEFAULT, false)
            .execute();

//        registerListener(
//            new ServerConnectListener(),
//            new PlayerMoveListener(),
//            new PlayerQuitListener(),
//            new PlayerChatEvent(),
//            new PlayerExecuteCommandListener()
//        );
//
//        registerCommand(
//            new Command2FA(getPlugin())
//        );

        this.teleportedToSpawn = Maps.newHashMap();
        this.filledInPlayers = Lists.newArrayList();
    }

    @Override
    public void onReload(String response) {

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

    private String generateQRCode(String user, String secret){
        String url = "https://www.google.com/chart?chs=128x128&cht=qr&chl=otpauth://totp/"
            + "MiragePrisons-" + user
            + "?secret=" + secret;

        return new TinyURL(url).generateURL();
    }

    private TextComponent clickableURL(String user, String secret){
        String url = generateQRCode(user, secret);
        TextComponent component = new TextComponent(url);
        component.setColor(Synergy.SynergyColor.PREFIX.getColor());
        component.setClickEvent(new ClickEvent(Action.OPEN_URL, url));
        return component;
    }

//    public void setEnabled2FAForUser(SynergyUser synergyUser){
//        if (!synergyUser.is2FAEnabled()){
//            getPlugin().getDatabaseManager().update(
//                "two_factor_authentication",
//                new HashMap<String, Object>(){{
//                    put("enabled", true);
//                }},
//                new HashMap<String, Object>(){{
//                    put("uuid", UtilSQL.convertUniqueId(synergyUser.getUuid()));
//                }}
//            );
//        }
//    }

    public void sendMessage(SynergyUser synergyUser){
        if (!synergyUser.hasFilledIn2FA()){
            if (synergyUser.has2FAKey()) {
                synergyUser.getPlayer().spigot().sendMessage(new TextComponent(UtilString.centered("&5&l2FA")));
                synergyUser.getPlayer().sendMessage(" ");
                if (true) { // TODO: Find a workaround. And add a cache
                    synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                        Synergy.SynergyColor.INFO.getColor() + "Because of your rank you have")
                    );
                    synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                        Synergy.SynergyColor.INFO.getColor() + "to enable 2FA on your account!")
                    );
                    synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                        Synergy.SynergyColor.INFO.getColor()
                            + "Click on this link to scan the QR code!")
                    );
                    synergyUser.getPlayer().spigot().sendMessage(this.clickableURL(
                        synergyUser.getName(), synergyUser.getAuthKey()));
                    synergyUser.getPlayer().sendMessage(" ");
                }else{
                    synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                        Synergy.SynergyColor.INFO.getColor() + "2FA has been enabled!")
                    );
                    synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                        Synergy.SynergyColor.INFO.getColor() + "Go to your app, and look for the code!")
                    );
                }
                synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                    Synergy.SynergyColor.INFO.getColor() + "Then use "
                        + Synergy.SynergyColor.PREFIX.getColor() + "/2fa <code>")
                );
                synergyUser.getPlayer().spigot().sendMessage(new TextComponent(
                    Synergy.SynergyColor.INFO.getColor() + "" + ChatColor.ITALIC +
                    "Or use " + Synergy.SynergyColor.PREFIX.getColor() + "/2fa link" +
                        Synergy.SynergyColor.INFO.getColor() + ", to get the QR code again!")
                );
                synergyUser.getPlayer().sendMessage(" ");
            }
        }
    }

    public void restore(SynergyUser synergyUser){
        if (Core.getPlugin().getGoogleAuthManager().getTeleportedToSpawn().containsKey(synergyUser)){
            Location location = Core.getPlugin().getGoogleAuthManager().getTeleportedToSpawn().get(synergyUser);
            synergyUser.getPlayer().teleport(location);
        }
    }

    public void getUserStuffFromDatabase(UUID uuid, SynergyUser synergyUser){
        try {
            ResultSet resultSet = getPlugin().getDatabaseManager().getResults(
                "two_factor_authentication","uuid=?", new HashMap<Integer, Object>(){{
                    put(1, UtilSQL.convertUniqueId(uuid));
                }}
            );
            if (resultSet.next()){
                Synergy.debug("!!!!! USING OLD CODE");
                synergyUser.setAuthKey(resultSet.getString("key"));
            }else{
                Synergy.debug("!!!!! GENERATING NEW CODE");
                String key = getTwoFactorKey(uuid);
                this.addToDatabase(uuid, key);
                synergyUser.setAuthKey(key);
            }
        }catch (SQLException e){
            e.printStackTrace();
            Synergy.error(e.getMessage());
        }
    }

    public void addToDatabase(UUID uuid, String key){
        new BukkitRunnable(){
            @Override
            public void run() {
                getPlugin().getDatabaseManager().insert("two_factor_authentication",
                    new HashMap<String, Object>() {{
                        put("uuid", UtilSQL.convertUniqueId(uuid));
                        put("key", key);
                    }}
                );
            }
        }.runTaskAsynchronously(getPlugin());
    }

}

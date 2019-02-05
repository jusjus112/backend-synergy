package usa.devrocoding.synergy.spigot.two_factor_authentication;

import com.mojang.authlib.yggdrasil.response.User;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleAuthManager {

    final long KEY_VALIDATION_INTERVAL_MS = TimeUnit.SECONDS.toMillis(30);
    int lastUsedPassword = -1; // last successfully used password
    long lastVerifiedTime = 0; // time of last success
    final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    AtomicInteger windowSize = new AtomicInteger(3);

    private String getTwoFactorKey(User user) {
        final GoogleAuthenticatorKey googleAuthkey = gAuth.createCredentials();
        String key = googleAuthkey.getKey();
        System.out.print("GOOGLE KEY: "+key);
        return key;
    }

}

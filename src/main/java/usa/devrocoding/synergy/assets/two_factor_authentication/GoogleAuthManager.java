package usa.devrocoding.synergy.assets.two_factor_authentication;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleAuthManager implements ICredentialRepository {

    @Override
    public String getSecretKey(String userName) {
        return null;
    }

    @Override
    public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {}

    final long KEY_VALIDATION_INTERVAL_MS = TimeUnit.SECONDS.toMillis(30);
    int lastUsedPassword = -1; // last successfully used password
    long lastVerifiedTime = 0; // time of last success
    final GoogleAuthenticator gAuth = new GoogleAuthenticator();
    AtomicInteger windowSize = new AtomicInteger(3);

    //User user
    public String getTwoFactorKey() {
        gAuth.setCredentialRepository(this);
        final GoogleAuthenticatorKey googleAuthkey = gAuth.createCredentials("JusJus");
        String key = googleAuthkey.getKey();
        System.out.print("GOOGLE KEY: "+key);
        System.out.print("GOOGLE VERICODE: "+googleAuthkey.getVerificationCode());
        return key;
    }

    public void isCorrect(){
        System.out.println("Enter your key: ");
        Scanner scanner = new Scanner(System.in);
        int code_line = Integer.valueOf(scanner.nextLine());
        System.out.println("Your key is " + code_line);
        System.out.println("Valid? = " + gAuth.authorizeUser("JusJus", code_line));
    }

}

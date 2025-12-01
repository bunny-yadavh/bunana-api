package in.bunny.banana.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashedPassword {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    // Generate Salt
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password with PBKDF2
    public static String hashPassword(String password, String salt) {
        char[] chars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        try {
            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error hashing password!", e);
        }
    }

    // Verify password
    public static boolean verifyPassword(String enteredPassword, String storedHash, String storedSalt) {
        String newHash = hashPassword(enteredPassword, storedSalt);
        return newHash.equals(storedHash);
    }
}

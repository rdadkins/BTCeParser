package co.bitsquared.btceparser.trade.security;

import com.sun.istack.internal.Nullable;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * AES256 requires that you install the JCE Unlimited Strength files to accept key sizes above 128 bits.
 */
public class AES256 {

    public static final String ENCRYPTION_METHOD = "AES";
    public static final String CIPHER_METHOD = "AES/CBC/PKCS5Padding";
    public static final String KEY_FACTORY_METHOD = "PBKDF2WithHmacSHA1";
    public static final String CHAR_SET = "UTF-8";
    public static final int ITERATION_COUNT = 2048;
    public static final int KEY_LENGTH = 256;
    public static final int SALT_LENGTH = 32;
    public static final String SPLIT = "!";

    /**
     * Encrypt the data with the password provided.
     * @param data the plain text data to encrypt.
     * @param password the password to encrypt the data with.
     * @return a multi-level formatted string that holds pieces together of encrypted text. Returns null if there is an error.
     */
    @Nullable
    public static String encrypt(String data, String password) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_METHOD);
            SecureRandom random = new SecureRandom();
            byte[] salt = getSalt(random);
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ENCRYPTION_METHOD);
            Cipher cipher = Cipher.getInstance(CIPHER_METHOD);
            byte[] ivBytes = new byte[cipher.getBlockSize()];
            random.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] encryptedText = cipher.doFinal(data.getBytes(CHAR_SET));
            return Base64.encodeBase64String(salt) + SPLIT + Base64.encodeBase64String(ivBytes) + SPLIT + Base64.encodeBase64String(encryptedText);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the data with the password provided.
     * @param data the formatted string from encrypt().
     * @param password the password to encrypt the data with.
     * @return the decrypted text. Returns null if there is an error.
     */
    @Nullable
    public String decrypt(String data, String password) {
        String[] fields = data.split(SPLIT);
        byte[] salt = Base64.decodeBase64(fields[0]);
        byte[] iv = Base64.decodeBase64(fields[1]);
        byte[] message = Base64.decodeBase64(fields[2]);
        try {
            SecretKeySpec secretKey = deriveKey(salt, password);
            Cipher cipher = Cipher.getInstance(CIPHER_METHOD);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            return new String(cipher.doFinal(message));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                BadPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getSalt(SecureRandom random) {
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return saltBytes;
    }

    private static SecretKeySpec deriveKey(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_METHOD);
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, ENCRYPTION_METHOD);
    }

}

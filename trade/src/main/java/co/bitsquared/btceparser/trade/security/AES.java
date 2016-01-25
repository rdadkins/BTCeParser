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

public abstract class AES {

    public static final String ENCRYPTION_METHOD = "AES";
    public static final String CIPHER_METHOD = "AES/CBC/PKCS5Padding";
    public static final String KEY_FACTORY_METHOD = "PBKDF2WithHmacSHA1";
    public static final String CHAR_SET = "UTF-8";
    public static final int ITERATION_COUNT = 2048;
    public static final int SALT_LENGTH = 32;
    public static final String SPLIT = "!";

    /**
     * Encrypt the data with the password provided.
     * @param data the plain text data to encrypt.
     * @param password the password to encrypt the data with.
     * @throws NoSuchAlgorithmException thrown when the algorithm does not exist.
     * @throws InvalidKeySpecException thrown when the supplied KeySpec does not work.
     * @throws NoSuchPaddingException thrown when the padding method does not exist.
     * @throws InvalidAlgorithmParameterException thrown when the version of Java does not contain the algorithm being used.
     * @throws InvalidKeyException thrown when the SecretKey is of invalid length.
     * @throws UnsupportedEncodingException thrown when the char set does not exist in this version of Java.
     * @throws BadPaddingException thrown when there is improper padding
     * @throws IllegalBlockSizeException thrown when the input being decrypted is not a multiple of the block size.
     * @return a multi-level formatted string that holds pieces together of encrypted text. Returns null if there is an error.
     */
    @Nullable
    public static String encrypt(String data, String password, AESKeySize keySize) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, UnsupportedEncodingException, IllegalBlockSizeException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_METHOD);
        SecureRandom random = new SecureRandom();
        byte[] salt = getSalt(random);
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, keySize.getKeySize());
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ENCRYPTION_METHOD);
        Cipher cipher = Cipher.getInstance(CIPHER_METHOD);
        byte[] ivBytes = new byte[cipher.getBlockSize()];
        random.nextBytes(ivBytes);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
        byte[] encryptedText = cipher.doFinal(data.getBytes(CHAR_SET));
        return Base64.encodeBase64String(salt) + SPLIT + Base64.encodeBase64String(ivBytes) + SPLIT + Base64.encodeBase64String(encryptedText);
    }

    /**
     * Decrypt the data with the password provided.
     * @param data the formatted string from encrypt().
     * @param password the password to encrypt the data with.
     * @throws BadPaddingException this is typically thrown when the password provided isn't correct.
     * @throws IllegalBlockSizeException thrown when the input being decrypted is not a multiple of the block size.
     * @throws InvalidAlgorithmParameterException thrown when the version of Java does not contain the algorithm being used.
     * @throws InvalidKeyException thrown when the SecretKey is of invalid length.
     * @throws NoSuchPaddingException thrown when the padding method does not exist.
     * @throws NoSuchAlgorithmException thrown when the algorithm does not exist.
     * @throws InvalidKeySpecException thrown when the supplied KeySpec is wrong.
     * @return the decrypted text. Returns null if there is an error.
     */
    @Nullable
    public static String decrypt(String data, String password, AESKeySize keySize) throws BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        String[] fields = data.split(SPLIT);
        byte[] salt = Base64.decodeBase64(fields[0]);
        byte[] iv = Base64.decodeBase64(fields[1]);
        byte[] message = Base64.decodeBase64(fields[2]);
        SecretKeySpec secretKey = deriveKey(salt, password, keySize.getKeySize());
        Cipher cipher = Cipher.getInstance(CIPHER_METHOD);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        return new String(cipher.doFinal(message));
    }

    private static byte[] getSalt(SecureRandom random) {
        byte[] saltBytes = new byte[SALT_LENGTH];
        random.nextBytes(saltBytes);
        return saltBytes;
    }

    private static SecretKeySpec deriveKey(byte[] salt, String password, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_FACTORY_METHOD);
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();
        return new SecretKeySpec(keyBytes, ENCRYPTION_METHOD);
    }

    public abstract String encrypt(String data, String password);

    public abstract String decrypt(String data, String password);

}

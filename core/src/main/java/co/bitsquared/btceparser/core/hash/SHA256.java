package co.bitsquared.btceparser.core.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static final String HASHING_METHOD = "SHA-256";

    public static String getHash(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(HASHING_METHOD);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(input.getBytes());
        byte byteData[] = md.digest();
        StringBuilder hexString = new StringBuilder();
        String hex;
        for (byte aByteData: byteData) {
            hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

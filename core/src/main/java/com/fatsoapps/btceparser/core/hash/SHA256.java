package com.fatsoapps.btceparser.core.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    public static String getHash(String input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert md != null;
        md.update(input.getBytes());
        byte byteData[] = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByteData: byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }
        StringBuilder hexString = new StringBuilder();
        for (byte aByteData: byteData) {
            String hex = Integer.toHexString(0xff & aByteData);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

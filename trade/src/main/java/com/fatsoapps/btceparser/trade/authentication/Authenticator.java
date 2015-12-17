package com.fatsoapps.btceparser.trade.authentication;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Authenticator {

    private static final String INSTANCE = "HmacSHA512";
    private String apiKey;
    private String secret;

    public Authenticator(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public Map<String,String> getHeaders(Map<String, String> parameters, int nonce) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("key", apiKey);
        headers.put("sign", sign(parameters, nonce));
        return headers;
    }

    private String getKey() {
        return apiKey;
    }

    private String sign(Map<String, String> parameters, int nonce) {
        String parameterData = getBodyData(parameters, nonce);
        try {
            Mac mac = Mac.getInstance(INSTANCE);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), INSTANCE);
            mac.init(secretKeySpec);
            return Hex.encodeHexString(mac.doFinal(parameterData.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.err.println(INSTANCE + " instance is not available.");
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.err.println(INSTANCE + " provided with wrong type of SecretKeySpec.");
            e.printStackTrace();
        }
        return null;
    }

    private String getBodyData(Map<String, String> parameters, int nonce) {
        String line = "";
        for (Map.Entry<String, String> value: parameters.entrySet()) {
            if (line.length() > 0) {
                line += "&";
            }
            line += value.getKey() + "=" + value.getValue();
        }
        return line;
    }

}

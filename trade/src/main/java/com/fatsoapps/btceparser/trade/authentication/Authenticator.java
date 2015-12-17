package com.fatsoapps.btceparser.trade.authentication;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authenticator {

    private static final String INSTANCE = "HmacSHA512";
    private String apiKey;
    private String secret;

    public Authenticator(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    public String getKey() {
        return apiKey;
    }

    public String sign(Map<String, String> parameters, int nonce) {
        String parameterData = getData(parameters, nonce);
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

    private String getData(Map<String, String> parameters, int nonce) {
        String line = "";
        for (Map.Entry<String, String> value: parameters.entrySet()) {
            if (line.length() > 0) {
                line += "&";
            }
            line += value.getKey() + "=" + value.getValue();
        }
        return line + "&nonce=" + nonce;
    }

}

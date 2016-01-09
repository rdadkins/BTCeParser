package co.bitsquared.btceparser.trade.authentication;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Authenticator {

    private static final String INSTANCE = "HmacSHA512";
    private String apiKey;
    private String secret;
    private int nonce;

    public Authenticator(String apiKey, String secret, int nonce) {
        this.apiKey = apiKey;
        this.secret = secret;
        this.nonce = nonce;
    }

    public Map<String,String> getHeaders(Map<String, String> parameters) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("key", apiKey);
        headers.put("sign", sign(parameters));
        return headers;
    }

    protected String getKey() {
        return apiKey;
    }

    protected String getSecret() {
        return secret;
    }

    /**
     * Get the current nonce.
     * @param increment determines whether to increment on return or not (post increment).
     * @return the new nonce.
     */
    public int getNonce(boolean increment) {
        return increment ? nonce++ : nonce;
    }

    private String sign(Map<String, String> parameters) {
        String parameterData = getBodyData(parameters);
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

    private String getBodyData(Map<String, String> parameters) {
        String line = "";
        for (Map.Entry<String, String> value: parameters.entrySet()) {
            if (line.length() > 0) {
                line += "&";
            }
            line += value.getKey() + "=" + value.getValue();
        }
        return line;
    }

    public void writeToFile(File file, String password) {
        StoredInfo.write(file, this, password);
    }

}

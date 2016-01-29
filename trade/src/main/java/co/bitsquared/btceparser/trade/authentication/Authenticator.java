package co.bitsquared.btceparser.trade.authentication;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public final class Authenticator {

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
        Map<String, String> headers = new HashMap<>();
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

    /**
     * Attempts to write this Authenticator to a file with a desired password. Exceptions will be thrown if there is a
     * problem with the encryption method.
     * @param file the output file
     * @param password the password used to encrypt this data.
     */
    public void writeToFile(File file, String password) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        StoredInfo.write(file, this, password);
    }

    /**
     * Attempts to write the nonce to a file without overwriting the encrypted key / secret.
     * @param file the output file
     */
    public void writeNonceToFile(File file) throws IOException {
        StoredInfo.writeNonce(file, this);
    }

}

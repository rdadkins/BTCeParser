package co.bitsquared.btceparser.trade.authentication;

import co.bitsquared.btceparser.trade.callbacks.NonceLeftCallback;
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
    private static final long MAX_NONCE = 4294967294L;
    private final String apiKey;
    private final String secret;
    private NonceLeftCallback nonceCallback;
    private long nonce;

    public Authenticator(String apiKey, String secret, long nonce) {
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
    public long getNonce(boolean increment) {
        long tempNonce = increment ? nonce++ : nonce;
        if (nonceCallback != null) {
            nonceCallback.nonceRemaining(MAX_NONCE - tempNonce);
        }
        return tempNonce;
    }

    public void registerNonceLeftCallback(NonceLeftCallback callback) {
        nonceCallback = callback;
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
     * @throws IOException if the file does not exist
     * @throws NoSuchPaddingException if the padding method does not exist
     * @throws InvalidAlgorithmParameterException if the algorithm parameters do not make sense
     * @throws IllegalBlockSizeException if the block size is illegal
     * @throws BadPaddingException if the padding is bad
     * @throws NoSuchAlgorithmException if the algorithm doesn't exist
     * @throws InvalidKeyException if encoding is wrong
     * @throws InvalidKeySpecException if the key spec is wrong
     *
     */
    public void writeToFile(File file, String password) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        StoredInfo.write(file, this, password);
    }

    /**
     * Attempts to write the nonce to a file without overwriting the encrypted key / secret.
     * @param file the output file
     * @throws IOException if the file does not exist
     */
    public void writeNonceToFile(File file) throws IOException {
        StoredInfo.writeNonce(file, this);
    }

}

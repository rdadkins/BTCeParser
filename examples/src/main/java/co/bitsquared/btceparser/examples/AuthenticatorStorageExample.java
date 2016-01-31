package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.authentication.StoredInfo;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class AuthenticatorStorageExample implements BaseExample {

    private String key = "someRandomKeyAssignedByBTCe";
    private String secret = "TheSecretThatTheyGiveYouGoesHere";
    private int nonce = 1000;
    private String fileName = "StoredInfo";
    private String password = "MySuperSecretPassword";
    private String initialSign;

    public static void main(String[] args) {
        new AuthenticatorStorageExample().startExample();
    }

    public void startExample() {
        try {
            writeToFile();
            readFromFile();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile() throws NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        Authenticator authenticator = new Authenticator(key, secret, nonce);
        initialSign = authenticator.getHeaders(ParameterBuilder.createBuilder().build()).get("sign");
        File file = new File(fileName);
        authenticator.writeToFile(file, password);
    }

    private void readFromFile() throws BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        File file = new File(fileName);
        Authenticator authenticator = StoredInfo.read(file, password);
        String newSign = authenticator.getHeaders(ParameterBuilder.createBuilder().build()).get("sign");
        System.out.println("Sign matches: " + newSign.equals(initialSign));
    }

}

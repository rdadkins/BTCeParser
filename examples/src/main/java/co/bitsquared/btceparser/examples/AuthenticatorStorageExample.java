package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.authentication.StoredInfo;

import java.io.File;

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
        writeToFile();
        readFromFile();
    }

    private void writeToFile() {
        Authenticator authenticator = new Authenticator(key, secret, nonce);
        initialSign = authenticator.getHeaders(ParameterBuilder.createBuilder().build()).get("sign");
        File file = new File(fileName);
        authenticator.writeToFile(file, password);
    }

    private void readFromFile() {
        File file = new File(fileName);
        Authenticator authenticator = StoredInfo.read(file, password);
        String newSign = authenticator.getHeaders(ParameterBuilder.createBuilder().build()).get("sign");
        System.out.println("Sign matches: " + newSign.equals(initialSign));
    }

}

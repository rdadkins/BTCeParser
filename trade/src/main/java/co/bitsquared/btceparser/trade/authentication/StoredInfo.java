package co.bitsquared.btceparser.trade.authentication;

import co.bitsquared.btceparser.trade.security.AES256;

import java.io.*;

public class StoredInfo {

    /**
     * Reads an Authenticator from a file and a password.
     * @param file the associated file with the encrypted Key / Secret.
     * @param password the password used to encrypt the file's content.
     * @return an Authenticator from the file, null if there is an error.
     */
    public static Authenticator read(File file, String password) {
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!file.canRead() || input == null) {
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            String decryptedKey = AES256.decrypt(readLine(reader), password);
            String decryptedSecret = AES256.decrypt(readLine(reader), password);
            String nonce = readLine(reader);
            reader.close();
            return new Authenticator(decryptedKey, decryptedSecret, Integer.valueOf(nonce));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    /**
     * Write an Authenticator to a file with an associated password. This method works by encrypting each piece of information
     * and writing it to a file (i.e.: encrypt the key and secret separately and write as separate lines).
     * @param file the target file to write the information.
     * @param authenticator the authenticator with the Key / Secret.
     * @param password the password used to encrypt the information.
     */
    public static void write(File file, Authenticator authenticator, String password) {
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!file.canWrite() || output == null) {
            return;
        }
        String encryptedKey = AES256.encrypt(authenticator.getKey(), password);
        String encryptedSecret = AES256.encrypt(authenticator.getSecret(), password);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        try {
            writeLine(writer, encryptedKey);
            writeLine(writer, encryptedSecret);
            writeLine(writer, authenticator.getNonce(false));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeLine(BufferedWriter writer, int value) throws IOException {
        writeLine(writer, value + "");
    }

    private static void writeLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

}

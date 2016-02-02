package co.bitsquared.btceparser.trade.authentication;

import co.bitsquared.btceparser.trade.security.AES;
import co.bitsquared.btceparser.trade.security.AESKeySize;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public final class StoredInfo {

    public static AESKeySize KEY_SIZE = AESKeySize.SIZE_128;

    /**
     * Reads an Authenticator from a file and a password.
     * @param file the associated file with the encrypted Key / Secret.
     * @param password the password used to encrypt the file's content.
     * @throws IOException if there is a problem reading the input file.
     * @throws BadPaddingException
     * @return an Authenticator from the file, null if there is an error.
     */
    public static Authenticator read(File file, String password) throws IOException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
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
        String decryptedKey = AES.decrypt(readLine(reader), password, KEY_SIZE);
        String decryptedSecret = AES.decrypt(readLine(reader), password, KEY_SIZE);
        String nonce = readLine(reader);
        Authenticator authenticator = new Authenticator(decryptedKey, decryptedSecret, Integer.valueOf(nonce));
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authenticator;
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
    public static void write(File file, Authenticator authenticator, String password) throws IOException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        if (!file.canWrite()) return;
        FileOutputStream output = getOutputStream(file);
        String encryptedKey = AES.encrypt(authenticator.getKey(), password, KEY_SIZE);
        String encryptedSecret = AES.encrypt(authenticator.getSecret(), password, KEY_SIZE);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
        writeLine(writer, encryptedKey);
        writeLine(writer, encryptedSecret);
        writeLine(writer, authenticator.getNonce(false) + "");
        writer.close();
    }

    public static void writeNonce(File file, Authenticator authenticator) throws IOException {
        if (!file.canWrite() || !file.exists()) return;
        FileInputStream input = getInputStream(file);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
        String encryptedKey = readLine(inputReader);
        String encryptedSecret = readLine(inputReader);
        inputReader.close();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        writeLine(writer, encryptedKey);
        writeLine(writer, encryptedSecret);
        writeLine(writer, authenticator.getNonce(true) + "");
        writer.close();
    }

    private static FileInputStream getInputStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    private static FileOutputStream getOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file);
    }

    private static void writeLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

}

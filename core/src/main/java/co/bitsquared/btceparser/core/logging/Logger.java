package co.bitsquared.btceparser.core.logging;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static Logger ourInstance;
    private static String FILE_PATH = "BTCeParserLogs";
    private static final String FILE_NAME = "log.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SS] ");
    private File file;
    private FileWriter writer;

    private Logger() {
        createPath();
        file = new File(FILE_PATH + "/" + FILE_NAME);
        try {
            file.createNewFile();
            open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (ourInstance == null) {
            ourInstance = new Logger();
        }
        return ourInstance;
    }

    public static void setFilePath(String filePath) {
        FILE_PATH = filePath;
    }

    public void logLines(String... messages) {
        try {
            open();
            for (String message: messages) {
                writer.write(getTimeStamp());
                writer.write(message);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTimeStamp() {
        return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
    }

    private void createPath() {
        new File(FILE_PATH).mkdir();
    }

    private void open() throws IOException {
        writer = new FileWriter(file, true);
    }

}

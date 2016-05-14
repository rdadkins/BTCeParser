package co.bitsquared.btceparser.core.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractLogger {

    /**
     * Default file path is $PROJECT_FOLDER/BTCeParser/Logs. If a secondary path is defined, the path will turn in to
     * $PROJECT_FOLDER/BTCeParser/Logs/$SECONDARY_PATH.
     */
    private static final String FILE_PATH = "BTCeParser" + File.separator + "Logs";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] ");
    private File file;
    private boolean includeDate;

    protected AbstractLogger(String fileName, boolean includeDate) throws IOException {
        this.includeDate = includeDate;
        String totalPath = FILE_PATH;
        if (getSecondaryPath() != null) {
            totalPath += File.separator + getSecondaryPath();
        }
        File filePath = new File(totalPath);
        createPath(filePath);
        this.file = new File(filePath, fileName);
        createFile();
    }

    /**
     * Defines a nested path / folder in which to place a file. The parent folder follows $PROJECT_FOLDER/BTCeParser/Logs
     * @return a string if a nested path should be used, null if no path is desired.
     */
    protected abstract String getSecondaryPath();

    /**
     * Logs each line to the specified file. Each line is tagged with a timestamp if includeDate is true.
     */
    protected synchronized final void logLines(String... lines) {
        try {
            FileWriter writer = getFileWriter();
            for (String line: lines) {
                if (includeDate) {
                    line = getTimeStamp() + line;
                }
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTimeStamp() {
        return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
    }

    private void createPath(File filePath) {
        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                System.err.println("Path may not have been created");
            }
        }
    }

    private void createFile() {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    System.err.println("File may not have been created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private FileWriter getFileWriter() throws IOException {
        return new FileWriter(file, true);
    }

}

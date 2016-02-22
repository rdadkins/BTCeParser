package co.bitsquared.btceparser.core.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AbstractLogger {

    private final File filePath;
    private final File file;

    public AbstractLogger(File filePath, File file) throws IOException {
        this.filePath = filePath;
        this.file = file;
        createPath();
        createFile();
    }

    public final void logLines(String... lines) {
        try {
            FileWriter writer = getFileWriter();
            for (String line: lines) {
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPath() {
        if (!filePath.mkdir()) {
            System.err.println("Path may not have been created");
        }
    }

    private void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private FileWriter getFileWriter() throws IOException {
        return new FileWriter(file, true);
    }

}

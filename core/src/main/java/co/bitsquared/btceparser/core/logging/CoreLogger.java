package co.bitsquared.btceparser.core.logging;

import java.io.IOException;

public class CoreLogger extends AbstractLogger {

    private static CoreLogger ourInstance;
    private static final String SECONDARY_PATH = "core";
    private static final String FILE_NAME = "log.txt";
    private static boolean includeDate = false;

    private CoreLogger(String fileName, boolean includeDate) throws IOException {
        super(fileName, includeDate);
    }

    public static CoreLogger getInstance() {
        if (ourInstance == null) {
            try {
                ourInstance = new CoreLogger(FILE_NAME, includeDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ourInstance;
    }

    @Override
    protected String getSecondaryPath() {
        return SECONDARY_PATH;
    }

}

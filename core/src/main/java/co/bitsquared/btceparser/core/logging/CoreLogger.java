package co.bitsquared.btceparser.core.logging;

import co.bitsquared.btceparser.core.data.LoggableData;

import java.io.IOException;

public class CoreLogger extends AbstractLogger {

    private static CoreLogger instance;
    private static final String SECONDARY_PATH = "core";
    private static final String FILE_NAME = "log.txt";
    private static boolean includeDate = false;

    static {
        try {
            instance = new CoreLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CoreLogger() throws IOException {
        super(FILE_NAME, includeDate);
    }

    public static CoreLogger getInstance() {
        return instance;
    }

    /**
     * Logs a LoggableData's to a file. The information that is logged comes from LoggableData.toLoggableString().
     */
    public void log(LoggableData data) {
        logLines(data.toLoggableString());
    }

    @Override
    protected String getSecondaryPath() {
        return SECONDARY_PATH;
    }

}

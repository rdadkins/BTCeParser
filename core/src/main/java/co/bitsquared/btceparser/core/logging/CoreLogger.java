package co.bitsquared.btceparser.core.logging;

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

    @Override
    protected String getSecondaryPath() {
        return SECONDARY_PATH;
    }

}

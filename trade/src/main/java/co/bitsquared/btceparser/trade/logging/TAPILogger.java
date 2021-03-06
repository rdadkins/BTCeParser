package co.bitsquared.btceparser.trade.logging;

import co.bitsquared.btceparser.core.logging.AbstractLogger;

import java.io.IOException;

public class TAPILogger extends AbstractLogger {

    private static TAPILogger instance;
    private static final String SECONDARY_PATH = "tapi";
    private static final String FILE_NAME = "log.txt";
    private static final boolean includeDate = false;

    private TAPILogger() throws IOException {
        super(FILE_NAME, includeDate);
    }

    public static TAPILogger getInstance() {
        if (instance == null) {
            try {
                instance = new TAPILogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    protected String getSecondaryPath() {
        return SECONDARY_PATH;
    }

}

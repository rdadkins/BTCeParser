package co.bitsquared.btceparser.trade.logging;

import co.bitsquared.btceparser.core.logging.AbstractLogger;

import java.io.File;
import java.io.IOException;

public class TAPILogger extends AbstractLogger {

    private static final File filePath = new File("BTCeParser/BTCeAccountLogs");

    public TAPILogger(File file) throws IOException {
        super(filePath, file);
    }

}

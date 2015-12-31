package co.bitsquared.btceparser.trade.exceptions;

import java.util.Arrays;

public class MissingParametersException extends RuntimeException {

    public MissingParametersException(String... parameters) {
        super("Parameters missing: " + Arrays.toString(parameters));
    }

}

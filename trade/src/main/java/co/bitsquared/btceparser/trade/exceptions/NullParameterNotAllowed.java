package co.bitsquared.btceparser.trade.exceptions;

import java.util.Arrays;

/**
 * NullParameterNotAllowed is thrown when a null parameter is passed to ParameterBuilder when it is required AND there
 * are no defaults set anywhere for that type. The easiest example would be when a user passes null to addOrderType().
 * ParameterBuilder can't decide on a buy / sell for the user, so this exception is thrown.
 */
public class NullParameterNotAllowed extends RuntimeException {

    private static final String DEFAULT_ERROR_MESSAGE = "The following parameters were passed as null when they are required: ";

    public NullParameterNotAllowed(String... parameterAsString) {
        super(DEFAULT_ERROR_MESSAGE + Arrays.toString(parameterAsString));
    }

}

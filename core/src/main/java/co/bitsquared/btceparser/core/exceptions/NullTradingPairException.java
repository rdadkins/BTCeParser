package co.bitsquared.btceparser.core.exceptions;

public class NullTradingPairException extends NullPointerException {

    private static final String MESSAGE = "TradingPair cannot be null.";

    public NullTradingPairException() {
        super(MESSAGE);
    }

}

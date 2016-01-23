package co.bitsquared.btceparser.core.exceptions;

/**
 * NoTradingPairSuppliedException is thrown when a Trade is being read from a String and there is no defined TradingPair
 * key.
 * @see co.bitsquared.btceparser.core.data.Trade
 */
public class NoTradingPairSuppliedException extends RuntimeException {

    public NoTradingPairSuppliedException(String tradeAsString) {
        super("No TradingPair supplied in " + tradeAsString);
    }

}

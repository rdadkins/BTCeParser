package co.bitsquared.btceparser.trade.data;

/**
 * OrderMode is a sorting mode used in ParameterBuilder when viewing TradeHistory or TransHistory. The default value is
 * DESC (descending mode).
 * @see co.bitsquared.btceparser.trade.ParameterBuilder
 * @see co.bitsquared.btceparser.trade.requests.TradeHistoryRequest
 * @see co.bitsquared.btceparser.trade.requests.TransHistoryRequest
 */
public enum OrderMode {

    /**
     * Ascending mode in sorting [smallest to largest].
     */
    ASC("ASC"),

    /**
     * Descending mode in sorting [largest to smallest].
     */
    DESC("DESC");

    public static final OrderMode DEFAULT_ORDER_MODE = DESC;

    private final String modeAsString;

    OrderMode(String modeAsString) {
        this.modeAsString = modeAsString;
    }

    public String getModeAsString() {
        return modeAsString;
    }

}

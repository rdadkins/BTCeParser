package co.bitsquared.btceparser.core;

import co.bitsquared.btceparser.core.data.TradingPair;

/**
 * A list of all methods defined in the public API. This also includes constants defined in the API documentation. All of
 * these methods are tied in to PublicRequests.
 *
 * @see co.bitsquared.btceparser.core.requests
 */
public enum API {

    /**
     * INFO provides general information of active trading pairs on the exchange.
     */
    INFO("https://btc-e.com/api/3/info"),

    /**
     * TICKER provides more in-depth information about all active trading pairs on the exchange such as price information and volume.
     */
    TICKER("https://btc-e.com/api/3/ticker/"),

    /**
     * DEPTH provides information about a specific trading pairs current list of orders.
     */
    DEPTH("https://btc-e.com/api/3/depth/"),

    /**
     * TRADES provides information about the most recent executed trades for a trading pair.
     */
    TRADES("https://btc-e.com/api/3/trades/");

    /**
     * The default order limit when requesting depth information.
     */
    public static final int DEFAULT_ORDER_LIMIT = 150;

    /**
     * The default trade limit when requesting recent trades.
     */
    public static final int DEFAULT_TRADE_LIMIT = 150;

    /**
     * The maximum order limit when requesting depth information.
     */
    public static final int MAX_ORDER_LIMIT = 2000;

    /**
     * The maximum trade limit when requesting recent trades.
     */
    public static final int MAX_TRADE_LIMIT = 2000;

    private String url;

    API(String url) {
        this.url = url;
    }

    /**
     * Returns a formatted URL based on a TradingPair. If the method is INFO, a TradingPair is not required.
     * @param pair the {@link TradingPair} to get a URL for. This can be null if it is known that URL being used is {@link #INFO}
     * @return a url to match this pair
     */
    public String getUrl(TradingPair pair) {
        if (this == INFO) {
            return url;
        }
        return url + pair.toString();
    }

}

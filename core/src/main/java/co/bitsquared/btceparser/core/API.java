package co.bitsquared.btceparser.core;

import co.bitsquared.btceparser.core.data.TradingPair;

public enum API {

    INFO("https://btc-e.com/api/3/info"),
    TICKER("https://btc-e.com/api/3/ticker/"),
    DEPTH("https://btc-e.com/api/3/depth/"),
    TRADES("https://btc-e.com/api/3/trades/");

    public static final int DEFAULT_ORDER_LIMIT = 150;
    public static final int DEFAULT_TRADE_LIMIT = 150;
    public static final int MAX_ORDER_LIMIT = 2000;
    public static final int MAX_TRADE_LIMIT = 2000;

    private String url;

    API(String url) {
        this.url = url;
    }

    public String getUrl(TradingPair pair) {
        if (this == INFO) {
            return url;
        }
        return url + pair.toString();
    }

}

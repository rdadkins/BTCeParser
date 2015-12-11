package com.fatsoapps.btceparser.core;

public enum API {

    INFO("https://btc-e.com/api/3/info"),
    TICKER("https://btc-e.com/api/3/ticker/"),
    DEPTH("https://btc-e.com/api/3/depth/"),
    TRADES("https://btc-e.com/api/3/trades/");

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

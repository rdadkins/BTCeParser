package co.bitsquared.btceparser.core.utils;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.data.CoinTicker;
import org.json.JSONObject;

public class Utils {

    /**
     * Extracts an array of CoinInfo from a JSONObject and a list of desired TradingPairs.
     * @param pairs the JSONObject from a BTCe response. Typically it is the 'pairs' object.
     * @param tradingPairs the list of TradingPairs that you want to be included.
     * @return an array of CoinInfo.
     */
    public static CoinInfo[] extractCoinInfo(JSONObject pairs, TradingPair... tradingPairs) {
        CoinInfo[] info = new CoinInfo[tradingPairs.length];
        int position = 0;
        for (TradingPair tradingPair: tradingPairs) {
            JSONObject pairInfo = pairs.getJSONObject(tradingPair.toString());
            info[position++] = new CoinInfo(tradingPair, pairInfo);
        }
        return info;
    }

    /**
     * Extracts a single CoinTicker from a JSONObject and a matching TradingPair.
     * @param body the JSONObject from a BTCe response. Typically it is just the body object.
     * @param tradingPair the matching TradingPair to pull the additional JSONObject from.
     * @return a CoinTicker from body.
     */
    public static CoinTicker extractCoinTicker(JSONObject body, TradingPair tradingPair) {
        JSONObject coinTicker = body.getJSONObject(tradingPair.toString());
        return new CoinTicker(tradingPair, coinTicker);
    }

}

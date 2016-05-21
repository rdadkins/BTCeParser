package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoinTicker extends LoggableData {

    public static final String HIGH_KEY = "high";
    public static final String LOW_KEY = "low";
    public static final String AVERAGE_KEY = "avg";
    public static final String LAST_KEY = "last";
    public static final String VOLUME_KEY = "vol";
    public static final String BUY_KEY = "buy";
    public static final String SELL_KEY = "sell";
    public static final String UPDATED_KEY = "updated";
    private final TradingPair TRADING_PAIR;

    private HashMap<String, Object> dataMap;

    public CoinTicker(TradingPair tradingPair, JSONObject object) {
        TRADING_PAIR = tradingPair;
        dataMap = new HashMap<>();
        dataMap.put(TRADING_PAIR_KEY, TRADING_PAIR.toString());
        dataMap.put(HIGH_KEY, getDouble(object, HIGH_KEY));
        dataMap.put(LOW_KEY, getDouble(object, LOW_KEY));
        dataMap.put(AVERAGE_KEY, getDouble(object, AVERAGE_KEY));
        dataMap.put(LAST_KEY, getDouble(object, LAST_KEY));
        dataMap.put(VOLUME_KEY, getDouble(object, VOLUME_KEY));
        dataMap.put(BUY_KEY, getDouble(object, BUY_KEY));
        dataMap.put(SELL_KEY, getDouble(object, SELL_KEY));
        dataMap.put(UPDATED_KEY, getDouble(object, UPDATED_KEY));
    }

    public TradingPair getTradingPair() {
        return TRADING_PAIR;
    }

    public double getHighPrice() {
        return (double) dataMap.get(HIGH_KEY);
    }

    public double getLowPrice() {
        return (double) dataMap.get(LOW_KEY);
    }

    public double getAveragePrice() {
        return (double) dataMap.get(AVERAGE_KEY);
    }

    public double getLastPrice() {
        return (double) dataMap.get(LAST_KEY);
    }

    public double getVolume() {
        return (double) dataMap.get(VOLUME_KEY);
    }

    public double getBuyPrice() {
        return (double) dataMap.get(BUY_KEY);
    }

    public double getSellPrice() {
        return (double) dataMap.get(SELL_KEY);
    }

    public double getLastUpdatedTime() {
        return (double) dataMap.get(UPDATED_KEY);
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        return dataMap;
    }

    @Override
    public String toString() {
        return "[" + getTradingPair().name() +"]\n" +
                "|-[High: " + getHighPrice() +"]\n" +
                "|-[Low: " + getLowPrice() + "]\n" +
                "|-[Average: " + getAveragePrice() + "]\n" +
                "|-[Last: " + getLastPrice() + "]\n" +
                "|-[Volume: " + getVolume() + "]\n" +
                "|-[Buy: " + getBuyPrice() + "]\n" +
                "|-[Sell: " + getSellPrice() + "]\n" +
                "|-[Last Updated: " + getLastUpdatedTime() + "]";
    }

}

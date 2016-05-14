package co.bitsquared.btceparser.core.data;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoinInfo extends LoggableData {

    public static final String DECIMAL_PLACES = "decimal_places";
    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";
    public static final String MIN_AMOUNT = "min_amount";
    public static final String HIDDEN = "hidden";
    public static final String FEE = "fee";

    private final Map<String, Object> dataMap;
    private final TradingPair TRADING_PAIR;

    public CoinInfo(TradingPair tradingPair, JSONObject data) {
        TRADING_PAIR = tradingPair;
        dataMap = new HashMap<>();
        dataMap.put(TRADING_PAIR_KEY, TRADING_PAIR.toString());
        dataMap.put(DECIMAL_PLACES, getInt(data, DECIMAL_PLACES));
        dataMap.put(MIN_PRICE, getDouble(data, MIN_PRICE));
        dataMap.put(MAX_PRICE, getDouble(data, MAX_PRICE));
        dataMap.put(MIN_AMOUNT, getDouble(data, MIN_AMOUNT));
        dataMap.put(HIDDEN, getInt(data, HIDDEN));
        dataMap.put(FEE, getDouble(data, FEE));
    }

    /**
     * Returns the amount of decimal places allowed for trading.
     */
    public int getDecimalPlaces() {
        return (int) dataMap.get(DECIMAL_PLACES);
    }

    /**
     * Returns the minimum price allowed for a valid trade.
     */
    public double getMinPrice() {
        return (double) dataMap.get(MIN_PRICE);
    }

    /**
     * Returns the maximum price allowed for a valid trade.
     */
    public double getMaxPrice() {
        return (double) dataMap.get(MAX_PRICE);
    }

    /**
     * Returns the minimum amount allowed for a valid trade.
     */
    public double getMinAmount() {
        return (double) dataMap.get(MIN_AMOUNT);
    }

    /**
     * Returns the fee associated with this TradingPair
     */
    public double getFee() {
        return (double) dataMap.get(FEE);
    }

    /**
     * Determines whether this pair is hidden from the public.
     */
    public boolean isHidden() {
        return (int) dataMap.get(HIDDEN) == 1;
    }

    /**
     * Returns the TradingPair associated with this information
     */
    public TradingPair getTradingPair() {
        return TRADING_PAIR;
    }

    @Override
    public String toLoggableString() {
        return getTradingPair().name() +
                " Decimal Places: " + getDecimalPlaces() +
                " Fee: " + getFee() +
                " Hidden: " + isHidden() +
                " Min Amount: " + getMinAmount() +
                " Min Price: " + getMinPrice() +
                " Max Price: " + getMaxPrice();
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        return dataMap;
    }

    @Override
    public String toString() {
        return "[" + getTradingPair().name() + "]\n" +
                "|-[Decimal Places: " + getDecimalPlaces() + "]\n" +
                "|-[Minimum Price: " + getMinPrice() + "]\n" +
                "|-[Maximum Price: " + getMaxPrice() + "]\n" +
                "|-[Minimum Amount: " + getMinAmount() + "]\n" +
                "|-[Fee: " + getFee() + "]\n" +
                "|-[Hidden: " + isHidden() + "]";
    }

}

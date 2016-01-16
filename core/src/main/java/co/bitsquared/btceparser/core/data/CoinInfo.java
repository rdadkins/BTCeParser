package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.TradingPair;
import org.json.JSONObject;

public class CoinInfo {

    private final TradingPair tradingPair;

    private int decimalPlaces;
    private double minPrice;
    private double maxPrice;
    private double minAmount;
    private boolean hidden;
    private double fee;

    public CoinInfo(TradingPair tradingPair, JSONObject data) {
        this.tradingPair = tradingPair;
        extractData(data);
    }

    /**
     * Returns the amount of decimal places allowed for trading.
     */
    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Returns the minimum price allowed for a valid trade.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price allowed for a valid trade.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Returns the minimum amount allowed for a valid trade.
     */
    public double getMinAmount() {
        return minAmount;
    }

    /**
     * Returns the fee associated with this TradingPair
     */
    public double getFee() {
        return fee;
    }

    /**
     * Determines whether this pair is hidden from the public.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Returns the TradingPair associated with this information
     */
    public TradingPair getTradingPair() {
        return tradingPair;
    }

    private void extractData(JSONObject object) {
        decimalPlaces = getInt(object, "decimal_places");
        minPrice = getDouble(object, "min_price");
        maxPrice = getDouble(object, "max_price");
        minAmount = getDouble(object, "min_amount");
        hidden = getInt(object, "hidden") == 1;
        fee = getDouble(object, "fee");
    }

    private int getInt(JSONObject pair, String key) {
        return pair.getInt(key);
    }

    private double getDouble(JSONObject pairs, String key) {
        return pairs.getDouble(key);
    }

}

package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.exceptions.NoTradingPairSuppliedException;
import co.bitsquared.btceparser.core.hash.SHA256;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Trade extends LoggableData {

    public static final String TYPE_KEY = "type";
    public static final String PRICE_KEY = "price";
    public static final String AMOUNT_KEY = "amount";
    public static final String TRANSACTION_ID_KEY = "tid";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String TRADING_PAIR_KEY = "TRADING_PAIR_KEY";

    private HashMap<String, Object> dataMap;
    private final TradingPair TRADING_PAIR;

    /**
     * Create a Trade from a TradingPair and a JSONObject from a Request response.
     * @param tradingPair the TradingPair that matches to this trade.
     * @param tradeObject a JSONObject that came from a Request.
     */
    public Trade(TradingPair tradingPair, JSONObject tradeObject) {
        TRADING_PAIR = tradingPair;
        dataMap = new HashMap<>();
        dataMap.put(TRADING_PAIR_KEY, TRADING_PAIR.toString());
        dataMap.put(TYPE_KEY, tradeObject.get(TYPE_KEY).equals("ask") ? DepthType.ASK : DepthType.BID);
        dataMap.put(PRICE_KEY, getDouble(tradeObject, PRICE_KEY));
        dataMap.put(AMOUNT_KEY, getDouble(tradeObject, AMOUNT_KEY));
        dataMap.put(TRANSACTION_ID_KEY, getLong(tradeObject, TRANSACTION_ID_KEY));
        dataMap.put(TIMESTAMP_KEY, getLong(tradeObject, TIMESTAMP_KEY));
    }

    /**
     * Creates a Trade from a saved string (most commonly stored on a file).
     * @param tradeAsString a string that has been stored from calling asJSONString().
     * @throws org.json.JSONException if tradeAsString is not formatted properly.
     * @throws NoTradingPairSuppliedException if tradeAsString does NOT contain a proper TradingPair.
     * @return a Trade object from the string provided.
     *
     * @since deprecated since v2.2.0;
     */
    @Deprecated
    public static Trade fromSavedFile(String tradeAsString) {
        JSONObject tradeObject = new JSONObject(tradeAsString);
        String tradingPairAsString = tradeObject.getString(TRADING_PAIR_KEY);
        if (tradingPairAsString != null) {
            return new Trade(TradingPair.valueOf(tradingPairAsString), tradeObject);
        }
        throw new NoTradingPairSuppliedException(tradeAsString);
    }

    public TradingPair getTradingPair() {
        return TRADING_PAIR;
    }

    public DepthType getDepthType() {
        return (DepthType) dataMap.get(TYPE_KEY);
    }

    public double getRate() {
        return (double) dataMap.get(PRICE_KEY);
    }

    public BaseCurrency<?> getRateAsCurrency() {
        return TRADING_PAIR.getOrderTemplate(getRate(), 0, getDepthType()).getRate();
    }

    public double getAmount() {
        return (double) dataMap.get(AMOUNT_KEY);
    }

    public BaseCurrency<?> getAmountAsCurrency() {
        return TRADING_PAIR.getOrderTemplate(0, getAmount(), getDepthType()).getAmount();
    }

    public long getTransactionID() {
        return (long) dataMap.get(TRANSACTION_ID_KEY);
    }

    public long getTimestamp() {
        return (long) dataMap.get(TIMESTAMP_KEY);
    }

    /**
     * Use toJSONObject() instead.
     * @since v2.2.0
     */
    @Deprecated
    public String asJSONString() {
        return new JSONObject().append(TRADING_PAIR_KEY, TRADING_PAIR.name()).
                append(TYPE_KEY, getDepthType().toString()).
                append(PRICE_KEY, getRate()).
                append(AMOUNT_KEY, getAmount()).
                append(TRANSACTION_ID_KEY, getTransactionID()).
                append(TIMESTAMP_KEY, getTimestamp()).toString();
    }

    /**
     * Hashes this Trade using SHA256. Since each Trade is marked with a TransactionID, this method should never* return
     * duplicate hashes.
     * @return a SHA256 hash from this trade.
     */
    public String getTradeHash() {
        return SHA256.getHash(toJSONObject().toString());
    }

    @Override
    public String toLoggableString() {
        return getTradingPair().name() + " " +
                getDepthType().name() + " " + getAmount() + " at " + getRate() +
                " Created: " + getTimestamp() +
                " ID: " + getTransactionID();
    }

    @Override
    public Map<String, Object> getDataAsMap() {
        return dataMap;
    }

}

package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.exceptions.NoTradingPairSuppliedException;
import co.bitsquared.btceparser.core.hash.SHA256;
import org.json.JSONObject;

public class Trade {

    public static final String TYPE_KEY = "type";
    public static final String PRICE_KEY = "price";
    public static final String AMOUNT_KEY = "amount";
    public static final String TRANSACTION_ID_KEY = "tid";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String TRADING_PAIR_KEY = "TRADING_PAIR_KEY";

    private TradingPair tradingPair;
    private DepthType depthType;
    private double rate;
    private double amount;
    private long transactionID;
    private long timestamp;

    /**
     * Create a Trade from a TradingPair and a JSONObject from a Request response.
     * @param tradingPair the TradingPair that matches to this trade.
     * @param tradeObject a JSONObject that came from a Request.
     */
    public Trade(TradingPair tradingPair, JSONObject tradeObject) {
        this.tradingPair = tradingPair;
        extractJSON(tradeObject);
    }

    /**
     * Creates a Trade from a saved string (most commonly stored on a file).
     * @param tradeAsString a string that has been stored from calling asJSONString().
     * @throws org.json.JSONException if tradeAsString is not formatted properly.
     * @throws NoTradingPairSuppliedException if tradeAsString does NOT contain a proper TradingPair.
     * @return a Trade object from the string provided.
     */
    public static Trade fromSavedFile(String tradeAsString) {
        JSONObject tradeObject = new JSONObject(tradeAsString);
        String tradingPairAsString = tradeObject.getString(TRADING_PAIR_KEY);
        if (tradingPairAsString != null) {
            return new Trade(TradingPair.valueOf(tradingPairAsString), tradeObject);
        }
        throw new NoTradingPairSuppliedException(tradeAsString);
    }

    public TradingPair getTradingPair() {
        return tradingPair;
    }

    public DepthType getDepthType() {
        return depthType;
    }

    public double getRate() {
        return rate;
    }

    public BaseCurrency<?> getRateAsCurrency() {
        return tradingPair.getOrderTemplate(rate, 0, depthType).getRate();
    }

    public double getAmount() {
        return amount;
    }

    public BaseCurrency<?> getAmountAsCurrency() {
        return tradingPair.getOrderTemplate(0, amount, depthType).getAmount();
    }

    public long getTransactionID() {
        return transactionID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String asJSONString() {
        return new JSONObject().append(TRADING_PAIR_KEY, tradingPair.name()).
                append(TYPE_KEY, depthType.toString()).
                append(PRICE_KEY, rate).
                append(AMOUNT_KEY, amount).
                append(TRANSACTION_ID_KEY, transactionID).
                append(TIMESTAMP_KEY, timestamp).toString();
    }

    /**
     * Hashes this Trade using SHA256. Since each Trade is marked with a TransactionID, this method should never* return
     * duplicate hashes.
     * @return a SHA256 hash from this trade.
     */
    public String getTradeHash() {
        return SHA256.getHash(asJSONString());
    }

    private void extractJSON(JSONObject tradeObject) {
        depthType = tradeObject.getString(TYPE_KEY).equals("ask") ? DepthType.ASK : DepthType.BID;
        rate = tradeObject.getDouble(PRICE_KEY);
        amount = tradeObject.getDouble(AMOUNT_KEY);
        transactionID = tradeObject.getLong(TRANSACTION_ID_KEY);
        timestamp = tradeObject.getLong(TIMESTAMP_KEY);
    }

}

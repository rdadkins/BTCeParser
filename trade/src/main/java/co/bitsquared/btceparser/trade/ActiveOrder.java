package co.bitsquared.btceparser.trade;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.DepthType;
import org.json.JSONObject;

import java.math.BigDecimal;

public class ActiveOrder {

    private static final String PAIR = "pair";
    private static final String TYPE = "type";
    private static final String SELL = "sell";
    private static final String AMOUNT = "amount";
    private static final String RATE = "rate";
    private static final String TIMESTAMP_CREATED = "timestamp_created";

    private int orderID;
    private TradingPair tradingPair;
    private DepthType depthType;
    private BaseCurrency<?> amount;
    private BaseCurrency<?> rate;
    private long timestampCreated;
    private static final int status = 0;

    public ActiveOrder(int orderID, JSONObject orderAsJSON) {
        this.orderID = orderID;
        extractJSON(orderAsJSON);
    }

    private void extractJSON(JSONObject orderAsJSON) {
        tradingPair = TradingPair.extract(orderAsJSON.getString(PAIR));
        depthType = orderAsJSON.getString(TYPE).equals(SELL) ? DepthType.BID : DepthType.ASK;
        double rawAmount = orderAsJSON.getDouble(AMOUNT);
        amount = (BaseCurrency<?>) tradingPair.getPriceCurrency().multiply(new BigDecimal(rawAmount));
        double rawRate = orderAsJSON.getDouble(RATE);
        rate = (BaseCurrency<?>) tradingPair.getTargetCurrency().multiply(new BigDecimal(rawRate));
        timestampCreated = orderAsJSON.getLong(TIMESTAMP_CREATED);
    }

    public int getOrderID() {
        return orderID;
    }

    public TradingPair getTradingPair() {
        return tradingPair;
    }

    public DepthType getDepthType() {
        return depthType;
    }

    public BaseCurrency<?> getAmount() {
        return amount;
    }

    public BaseCurrency<?> getRate() {
        return rate;
    }

    public long getTimeStamp() {
        return timestampCreated;
    }

    public int getStatus() {
        return status;
    }

}

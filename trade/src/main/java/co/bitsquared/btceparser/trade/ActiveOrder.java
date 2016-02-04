package co.bitsquared.btceparser.trade;

import co.bitsquared.btceparser.core.DepthType;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.currency.BaseCurrency;
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
    private static final int STATUS = 0;

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

    /**
     * @return the unique order ID that was assigned to this order when it was created.
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * @return the associated TradingPair of this order.
     */
    public TradingPair getTradingPair() {
        return tradingPair;
    }

    /**
     * @return the type of order this is.
     */
    public DepthType getDepthType() {
        return depthType;
    }

    /**
     * Represents the amount that you are exchanging the amount for. If you are exchanging X coins for Y price, this returns X.
     * @return a BaseCurrency that is amount of the order.
     */
    public BaseCurrency<?> getAmount() {
        return amount;
    }

    /**
     * Represents the price that you are exchanging the amount for. If you are exchanging X coins for Y price, this returns Y.
     * @return a BaseCurrency that is the price of the order.
     */
    public BaseCurrency<?> getRate() {
        return rate;
    }

    /**
     * Get the timestamp of when this order was created.
     * @return a long that represents the date that this order was created.
     */
    public long getTimeStamp() {
        return timestampCreated;
    }

    /**
     * Deprecated API return type. Is always 0.
     * @return 0.
     */
    public int getStatus() {
        return STATUS;
    }

    @Override
    public String toString() {
        return String.format("[%d] [%s] [%s] %s @ %s", orderID, tradingPair.name(), depthType.name(), amount.asBigDecimal(), rate.asBigDecimal());
    }

}

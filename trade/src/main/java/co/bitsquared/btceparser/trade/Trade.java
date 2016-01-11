package co.bitsquared.btceparser.trade;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.DepthType;
import org.json.JSONObject;

public class Trade {

    public static final String PAIR = "pair";
    public static final String SELL = "sell";
    public static final String AMOUNT = "amount";
    public static final String RATE = "rate";
    public static final String ORDER_ID = "order_id";
    public static final String IS_YOUR_ORDER = "is_your_order";
    public static final String TIMESTAMP = "timestamp";
    public static final String TYPE = "type";

    private int tradeID;
    private TradingPair tradingPair;
    private DepthType depthType;
    private double amount;
    private double rate;
    private long orderID;
    private boolean isUserOrder;
    private long timeStamp;

    public Trade(int tradeID, JSONObject object) {
        this.tradeID = tradeID;
        extractJSON(object);
    }

    private void extractJSON(JSONObject object) {
        tradingPair = TradingPair.extract(object.getString(PAIR));
        depthType = object.getString(TYPE).equals(SELL) ? DepthType.ASK : DepthType.BID;
        amount = object.getDouble(AMOUNT);
        rate = object.getDouble(RATE);
        orderID = object.getLong(ORDER_ID);
        isUserOrder = object.getInt(IS_YOUR_ORDER) == 1;
        timeStamp = object.getLong(TIMESTAMP);
    }

    public int getTradeID() {
        return tradeID;
    }

    public TradingPair getTradingPair() {
        return tradingPair;
    }

    public DepthType getDepthType() {
        return depthType;
    }

    public double getAmount() {
        return amount;
    }

    public double getRate() {
        return rate;
    }

    public long getOrderID() {
        return orderID;
    }

    public boolean isUserOrder() {
        return isUserOrder;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}

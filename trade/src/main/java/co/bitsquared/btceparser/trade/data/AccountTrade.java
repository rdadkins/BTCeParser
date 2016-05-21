package co.bitsquared.btceparser.trade.data;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.data.DepthType;
import org.json.JSONObject;

public final class AccountTrade {

    public static final String PAIR = "pair";
    public static final String SELL = "sell";
    public static final String AMOUNT = "amount";
    public static final String RATE = "rate";
    public static final String ORDER_ID = "order_id";
    public static final String IS_YOUR_ORDER = "is_your_order";
    public static final String TIMESTAMP = "timestamp";
    public static final String TYPE = "type";
    public static final String TRADE_ID = "tradeID";

    private final int tradeID;
    private final TradingPair tradingPair;
    private final DepthType depthType;
    private final double amount;
    private final double rate;
    private final long orderID;
    private final boolean isUserOrder;
    private final long timeStamp;

    public AccountTrade(int tradeID, JSONObject object) {
        this.tradeID = tradeID;
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

    public String asJSONString() {
        return new JSONObject().append(PAIR, tradingPair.name()).
                append(TRADE_ID, tradeID).
                append(TYPE, depthType.toString()).
                append(RATE, rate).
                append(AMOUNT, amount).
                append(ORDER_ID, orderID).
                append(IS_YOUR_ORDER, isUserOrder ? 1 : 0).
                append(TIMESTAMP, timeStamp).toString();
    }

}

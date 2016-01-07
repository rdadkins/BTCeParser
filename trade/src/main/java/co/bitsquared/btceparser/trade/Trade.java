package co.bitsquared.btceparser.trade;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.requests.DepthType;
import org.json.JSONObject;

public class Trade {

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
        tradingPair = TradingPair.extract(object.getString("pair"));
        depthType = object.getString("type").equals("sell") ? DepthType.ASK : DepthType.BID;
        amount = object.getDouble("amount");
        rate = object.getDouble("rate");
        orderID = object.getLong("order_id");
        isUserOrder = object.getInt("is_your_order") == 1;
        timeStamp = object.getLong("timestamp");
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

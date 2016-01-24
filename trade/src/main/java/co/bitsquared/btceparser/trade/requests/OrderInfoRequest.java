package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.DepthType;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.OrderInfoCallback;
import org.json.JSONObject;

import java.util.Set;

public class OrderInfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ORDER_INFO;

    public static final String[] PARAMS = new String[]{"order_id"};

    private OrderInfoCallback callback;

    public OrderInfoRequest(Authenticator authenticator, OrderInfoCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public OrderInfoRequest(Authenticator authenticator, OrderInfoCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        Set set = returnObject.keySet();
        if (set.size() == 0) {
            return;
        }
        int orderID = Integer.parseInt(set.iterator().next().toString());
        JSONObject orderObject = returnObject.getJSONObject(orderID + "");
        TradingPair tradingPair = TradingPair.extract(orderObject.getString(PAIR));
        DepthType type = orderObject.getString(TYPE).equals(SELL) ? DepthType.ASK : DepthType.BID;
        double startAmount = orderObject.getDouble(START_AMOUNT);
        double amount = orderObject.getDouble(AMOUNT);
        double rate = orderObject.getDouble(RATE);
        long timeStamp = orderObject.getLong(TIMESTAMP_CREATED);
        int status = orderObject.getInt(STATUS);
        callback.onSuccess(orderID, tradingPair, type, startAmount, amount, rate, timeStamp, status);
    }

    @Override
    protected String[] getRequiredParams() {
        return PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

}

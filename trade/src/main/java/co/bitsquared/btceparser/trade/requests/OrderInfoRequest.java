package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.data.DepthType;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.OrderInfoCallback;
import org.json.JSONObject;

import java.util.Set;

public class OrderInfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ORDER_INFO;

    public static final String[] PARAMS = new String[]{"order_id"};

    private OrderInfoRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.1 - use OrderInfoRequest.Builder
     */
    @Deprecated
    public OrderInfoRequest(Authenticator authenticator, OrderInfoCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.1 - use OrderInfoRequest.Builder
     */
    @Deprecated
    public OrderInfoRequest(Authenticator authenticator, OrderInfoCallback callback, long timeout) {
        super(authenticator, callback, timeout);
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
        listeners.stream().filter(callback -> callback instanceof OrderInfoCallback).
                forEach(callback -> execute(
                        () -> ((OrderInfoCallback) callback).onSuccess(orderID, tradingPair, type, startAmount, amount, rate, timeStamp, status)
                )
        );
    }

    @Override
    public String[] getRequiredParams() {
        return PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

    public static class Builder extends AccountRequest.Builder<Builder> {

        public Builder(Authenticator authenticator) {
            super(authenticator);
        }

        @Override
        protected Builder retrieveInstance() {
            return this;
        }

        @Override
        public OrderInfoRequest build() {
            return new OrderInfoRequest(this);
        }

    }

}

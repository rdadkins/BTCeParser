package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.data.DepthType;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.OrderInfoCallback;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

import java.util.Set;

import static co.bitsquared.btceparser.trade.Constant.*;

public class OrderInfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ORDER_INFO;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.ORDER_ID};

    public OrderInfoRequest(Authenticator authenticator, OrderInfoCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

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
        TradingPair tradingPair = TradingPair.extract(orderObject.getString(PAIR.asAPIFriendlyValue()));
        DepthType type = orderObject.getString(TYPE.asAPIFriendlyValue()).equals(SELL.asAPIFriendlyValue()) ? DepthType.ASK : DepthType.BID;
        double startAmount = orderObject.getDouble(START_AMOUNT.asAPIFriendlyValue());
        double amount = orderObject.getDouble(AMOUNT.asAPIFriendlyValue());
        double rate = orderObject.getDouble(RATE.asAPIFriendlyValue());
        long timeStamp = orderObject.getLong(TIMESTAMP_CREATED.asAPIFriendlyValue());
        int status = orderObject.getInt(STATUS.asAPIFriendlyValue());
        listeners.stream().filter(callback -> callback instanceof OrderInfoCallback).forEach(callback ->
                execute(() -> ((OrderInfoCallback) callback).onSuccess(orderID, tradingPair, type, startAmount, amount, rate, timeStamp, status))
        );
    }

    @Override
    public Parameter[] getRequiredParameters() {
        return PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

}

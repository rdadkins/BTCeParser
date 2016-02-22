package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.ActiveOrder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.ActiveOrderCallback;
import org.json.JSONObject;

public class ActiveOrderRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ACTIVE_ORDERS;

    public ActiveOrderRequest(Authenticator authenticator, ActiveOrderCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public ActiveOrderRequest(Authenticator authenticator, ActiveOrderCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    protected void assignMethod(ParameterBuilder parameterBuilder) {
        parameterBuilder.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        ActiveOrder[] openOrders = new ActiveOrder[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int orderID = Integer.valueOf(object.toString());
            openOrders[position++] = new ActiveOrder(orderID, returnObject.getJSONObject(orderID + ""));
        }
        listeners.stream().filter(callback -> callback instanceof ActiveOrderCallback).forEach(callback ->
                execute(() -> ((ActiveOrderCallback) callback).onSuccess(openOrders))
        );
    }

    @Override
    public String[] getRequiredParams() {
        return NO_PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }


}

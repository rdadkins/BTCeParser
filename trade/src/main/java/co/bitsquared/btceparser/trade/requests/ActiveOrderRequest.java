package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.ActiveOrder;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.ActiveOrderCallback;
import org.json.JSONObject;

public class ActiveOrderRequest extends AccountRequest {

    private ActiveOrderCallback callback;

    public ActiveOrderRequest(Authenticator authenticator, ActiveOrderCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public ActiveOrderRequest(Authenticator authenticator, ActiveOrderCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.ACTIVE_ORDERS);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        ActiveOrder[] openOrders = new ActiveOrder[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int orderID = Integer.valueOf(object.toString());
            openOrders[position++] = new ActiveOrder(orderID, returnObject.getJSONObject(orderID + ""));
        }
        callback.onSuccess(openOrders);
    }

    @Override
    protected String[] getRequiredParams() {
        return NO_PARAMS;
    }

}

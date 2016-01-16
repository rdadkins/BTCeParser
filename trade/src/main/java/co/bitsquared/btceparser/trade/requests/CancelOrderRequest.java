package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CancelOrderCallback;
import org.json.JSONObject;

public class CancelOrderRequest extends AccountRequest {

    public static final String[] PARAMS = new String[]{"order_id"};

    private CancelOrderCallback callback;

    public CancelOrderRequest(Authenticator authenticator, CancelOrderCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public CancelOrderRequest(Authenticator authenticator, CancelOrderCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.CANCEL_ORDER);
        super.processRequest(parameters);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        int orderId = returnObject.getInt(ORDER_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        callback.onSuccess(orderId, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return PARAMS;
    }

}

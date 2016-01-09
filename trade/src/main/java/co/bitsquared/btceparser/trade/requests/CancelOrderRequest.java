package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CancelOrderCallback;
import co.bitsquared.btceparser.trade.exceptions.MissingParametersException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class CancelOrderRequest extends AccountRequest {

    private CancelOrderCallback callback;

    public CancelOrderRequest(Authenticator authenticator, long timeout, CancelOrderCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.CANCEL_ORDER);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        int orderId = returnObject.getInt("order_id");
        Funds[] funds = extractFunds(returnObject.getJSONObject("funds"));
        callback.onSuccess(orderId, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return new String[]{"order_id"};
    }

    @Override
    public void failed(UnirestException e) {
        callback.onError(e.getMessage());
    }

    @Override
    public void cancelled() {
        callback.onError("User cancelled request");
    }

}

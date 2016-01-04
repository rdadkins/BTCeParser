package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.OpenOrder;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.ActiveOrderCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class ActiveOrderRequest extends AccountRequest {

    private ActiveOrderCallback callback;

    public ActiveOrderRequest(Authenticator authenticator, long timeout, ActiveOrderCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        parameters.method(TAPI.ACTIVE_ORDERS);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        OpenOrder[] openOrders = new OpenOrder[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int orderID = Integer.valueOf(object.toString());
            openOrders[position++] = new OpenOrder(orderID, returnObject.getJSONObject(orderID + ""));
        }
        callback.onSuccess(openOrders);
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

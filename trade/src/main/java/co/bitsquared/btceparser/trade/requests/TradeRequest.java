package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class TradeRequest extends AccountRequest {

    private static final String RECEIVED = "received";
    private static final String REMAINS = "remains";
    private static final String ORDER_ID = "order_id";
    private static final String FUNDS = "funds";

    private TradeRequestCallback callback;

    public TradeRequest(Authenticator authenticator, int timeout, TradeRequestCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder builder) {
        builder.method(TAPI.TRADE);
        super.processRequest(builder);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        double received = returnObject.getDouble(RECEIVED);
        double remains = returnObject.getDouble(REMAINS);
        int orderID = returnObject.getInt(ORDER_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        callback.onSuccess(received, remains, orderID, funds);
    }

    public void failed(UnirestException e) {

    }

    public void cancelled() {

    }

}

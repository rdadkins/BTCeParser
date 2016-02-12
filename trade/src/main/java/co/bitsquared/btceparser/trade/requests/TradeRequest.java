package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import org.json.JSONObject;

public class TradeRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRADE;

    private static final String[] PARAMS = new String[]{"pair", "type", "rate", "amount"};

    public TradeRequest(Authenticator authenticator, TradeRequestCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public TradeRequest(Authenticator authenticator, TradeRequestCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        double received = returnObject.getDouble(RECEIVED);
        double remains = returnObject.getDouble(REMAINS);
        int orderID = returnObject.getInt(ORDER_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        listeners.stream().filter(callback -> callback instanceof TradeRequest).forEach(callback ->
                execute(() -> ((TradeRequestCallback) callback).onSuccess(received, remains, orderID, funds))
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

}

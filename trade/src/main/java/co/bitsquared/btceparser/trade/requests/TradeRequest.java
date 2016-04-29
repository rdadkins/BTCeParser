package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

import static co.bitsquared.btceparser.trade.Constants.*;

public class TradeRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRADE;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.PAIR, Parameter.TYPE, Parameter.RATE, Parameter.AMOUNT};

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
        double received = returnObject.getDouble(RECEIVED.asAPIFriendlyValue());
        double remains = returnObject.getDouble(REMAINS.asAPIFriendlyValue());
        int orderID = returnObject.getInt(ORDER_ID.asAPIFriendlyValue());
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS.asAPIFriendlyValue()));
        listeners.stream().filter(callback -> callback instanceof TradeRequest).forEach(callback ->
                execute(() -> ((TradeRequestCallback) callback).onSuccess(received, remains, orderID, funds))
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

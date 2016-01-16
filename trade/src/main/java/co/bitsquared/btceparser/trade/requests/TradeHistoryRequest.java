package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.Trade;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeHistoryCallback;
import org.json.JSONObject;

public class TradeHistoryRequest extends AccountRequest {

    private TradeHistoryCallback callback;

    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.TRADE_HISTORY);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        Trade[] trades = new Trade[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            trades[position++] = new Trade(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        callback.onSuccess(trades);
    }

    @Override
    protected String[] getRequiredParams() {
        return NO_PARAMS;
    }

}

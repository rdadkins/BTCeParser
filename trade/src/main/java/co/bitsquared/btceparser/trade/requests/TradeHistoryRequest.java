package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.AccountTrade;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeHistoryCallback;
import org.json.JSONObject;

public class TradeHistoryRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRADE_HISTORY;

    private TradeHistoryCallback callback;

    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        AccountTrade[] accountTrades = new AccountTrade[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            accountTrades[position++] = new AccountTrade(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        callback.onSuccess(accountTrades);
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

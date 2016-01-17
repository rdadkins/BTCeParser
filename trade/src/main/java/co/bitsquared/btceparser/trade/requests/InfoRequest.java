package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.InfoCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class InfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.GETINFO;

    public static final int TRANSACTION_COUNT = 0;

    private InfoCallback callback;

    public InfoRequest(Authenticator authenticator, InfoCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public InfoRequest(Authenticator authenticator, InfoCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        JSONObject funds = returnObject.getJSONObject(FUNDS);
        Funds[] accountFunds = extractFunds(funds);
        JSONObject rights = returnObject.getJSONObject(RIGHTS);
        boolean accessInfo = rights.getInt(INFO) == 1;
        boolean canTrade = rights.getInt(TRADE) == 1;
        boolean canWithdraw = rights.getInt(WITHDRAW) == 1;
        int openOrders = returnObject.getInt(OPEN_ORDERS);
        long serverTime = returnObject.getLong(SERVER_TIME);
        callback.onSuccess(accountFunds, accessInfo, canTrade, canWithdraw, TRANSACTION_COUNT, openOrders, serverTime);
    }

    @Override
    protected String[] getRequiredParams() {
        return NO_PARAMS;
    }

}

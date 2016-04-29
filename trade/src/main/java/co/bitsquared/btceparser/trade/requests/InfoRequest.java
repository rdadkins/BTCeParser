package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.InfoCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import org.json.JSONObject;

import static co.bitsquared.btceparser.trade.Constants.*;

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
        JSONObject funds = returnObject.getJSONObject(FUNDS.asAPIFriendlyValue());
        Funds[] accountFunds = extractFunds(funds);
        JSONObject rights = returnObject.getJSONObject(RIGHTS.asAPIFriendlyValue());
        boolean accessInfo = rights.getInt(INFO.asAPIFriendlyValue()) == 1;
        boolean canTrade = rights.getInt(TRADE.asAPIFriendlyValue()) == 1;
        boolean canWithdraw = rights.getInt(WITHDRAW.asAPIFriendlyValue()) == 1;
        int openOrders = returnObject.getInt(OPEN_ORDERS.asAPIFriendlyValue());
        long serverTime = returnObject.getLong(SERVER_TIME.asAPIFriendlyValue());
        listeners.stream().filter(callback -> callback instanceof InfoCallback).forEach(callback ->
                execute(() -> ((InfoCallback) callback).onSuccess(accountFunds, accessInfo, canTrade, canWithdraw, TRANSACTION_COUNT, openOrders, serverTime))
        );
    }

    @Override
    public ParameterBuilder.Parameter[] getRequiredParameters() {
        return new ParameterBuilder.Parameter[0];
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

}

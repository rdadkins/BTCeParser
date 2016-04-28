package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.InfoCallback;
import org.json.JSONObject;

public class InfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.GETINFO;

    public static final int TRANSACTION_COUNT = 0;

    private InfoCallback callback;

    private InfoRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.1 - use InfoRequest.Builder
     */
    @Deprecated
    public InfoRequest(Authenticator authenticator, InfoCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.1 - use InfoRequest.Builder
     */
    @Deprecated
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
        listeners.stream().filter(callback -> callback instanceof InfoCallback).
                forEach(callback -> execute(
                        () -> ((InfoCallback) callback).onSuccess(accountFunds, accessInfo, canTrade, canWithdraw, TRANSACTION_COUNT, openOrders, serverTime)
                )
        );
    }

    @Override
    public String[] getRequiredParams() {
        return NO_PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

    public static class Builder extends AccountRequest.Builder<Builder> {

        public Builder(Authenticator authenticator) {
            super(authenticator);
        }

        @Override
        protected Builder retrieveInstance() {
            return this;
        }

        @Override
        public InfoRequest build() {
            return new InfoRequest(this);
        }

    }

}

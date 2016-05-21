package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeHistoryCallback;
import co.bitsquared.btceparser.trade.data.AccountTrade;
import org.json.JSONObject;

public class TradeHistoryRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRADE_HISTORY;

    private TradeHistoryRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.0 - use TradeHistoryRequest.Builder
     */
    @Deprecated
    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.0 - use TradeHistoryRequest.Builder
     */
    @Deprecated
    public TradeHistoryRequest(Authenticator authenticator, TradeHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final AccountTrade[] accountTrades = new AccountTrade[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            accountTrades[position++] = new AccountTrade(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof TradeHistoryCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((TradeHistoryCallback) callback).onSuccess(accountTrades);
                    }
                });
            }
        }
    }

    @Override
    public ParameterBuilder.Parameter[] getRequiredParameters() {
        return new ParameterBuilder.Parameter[0];
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
        public TradeHistoryRequest build() {
            return new TradeHistoryRequest(this);
        }

    }

}

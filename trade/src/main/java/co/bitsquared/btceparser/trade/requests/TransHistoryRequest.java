package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TransactionHistoryCallback;
import co.bitsquared.btceparser.trade.data.Transaction;
import org.json.JSONObject;

public class TransHistoryRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRANS_HISTORY;

    private TransHistoryRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.0 - use TransHistoryRequest.Builder
     */
    @Deprecated
    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.0 - use TransHistoryRequest.Builder
     */
    @Deprecated
    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final Transaction[] transactions = new Transaction[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            transactions[position++] = new Transaction(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof TransactionHistoryCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((TransactionHistoryCallback) callback).onSuccess(transactions);
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
        public TransHistoryRequest build() {
            return new TransHistoryRequest(this);
        }

    }

}

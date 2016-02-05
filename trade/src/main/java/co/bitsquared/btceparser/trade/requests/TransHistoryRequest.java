package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.data.Transaction;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TransactionHistoryCallback;
import org.json.JSONObject;

public class TransHistoryRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRANS_HISTORY;

    private TransactionHistoryCallback callback;

    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        Transaction[] transactions = new Transaction[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            transactions[position++] = new Transaction(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        callback.onSuccess(transactions);
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

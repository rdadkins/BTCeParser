package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.Transaction;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TransactionHistoryCallback;
import org.json.JSONObject;

public class TransHistoryRequest extends AccountRequest {

    private TransactionHistoryCallback callback;

    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public TransHistoryRequest(Authenticator authenticator, TransactionHistoryCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.TRANS_HISTORY);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        Transaction[] transactions = new Transaction[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int transactionID = Integer.valueOf(object.toString());
            transactions[position++] = new Transaction(transactionID, returnObject.getJSONObject(transactionID + ""));
        }
        callback.onSuccess(transactions);
    }

    @Override
    protected String[] getRequiredParams() {
        return NO_PARAMS;
    }

}

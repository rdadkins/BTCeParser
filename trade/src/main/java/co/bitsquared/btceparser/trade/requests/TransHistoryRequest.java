package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Transaction;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TransactionHistoryCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class TransHistoryRequest extends AccountRequest {

    private TransactionHistoryCallback callback;

    public TransHistoryRequest(Authenticator authenticator, long timeout, TransactionHistoryCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
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
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

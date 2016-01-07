package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Trade;
import co.bitsquared.btceparser.trade.Transaction;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeHistoryCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class TradeHistoryRequest extends AccountRequest {

    private TradeHistoryCallback callback;

    public TradeHistoryRequest(Authenticator authenticator, long timeout, TradeHistoryCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
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
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

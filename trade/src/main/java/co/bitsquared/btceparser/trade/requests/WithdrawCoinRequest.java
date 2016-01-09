package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.AccountCallback;
import co.bitsquared.btceparser.trade.callbacks.WithdrawCoinCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class WithdrawCoinRequest extends AccountRequest {

    private WithdrawCoinCallback callback;

    public WithdrawCoinRequest(Authenticator authenticator, long timeout, WithdrawCoinCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.WITHDRAW_COIN);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        int transactionID = returnObject.getInt("tId");
        double amountSent = returnObject.getDouble("amountSent");
        Funds[] funds = extractFunds(returnObject.getJSONObject("funds"));
        callback.onSuccess(transactionID, amountSent, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return new String[]{"coinName", "amount", "address"};
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.WithdrawCoinCallback;
import org.json.JSONObject;

public class WithdrawCoinRequest extends AccountRequest {

    public static final String[] PARAMS = new String[]{"coinName", "amount", "address"};

    private WithdrawCoinCallback callback;

    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback, long timeout) {
        super(authenticator, callback, timeout);
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
        int transactionID = returnObject.getInt(T_ID);
        double amountSent = returnObject.getDouble(AMOUNT_SENT);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        callback.onSuccess(transactionID, amountSent, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return PARAMS;
    }

}

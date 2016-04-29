package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.WithdrawCoinCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

import static co.bitsquared.btceparser.trade.Constant.*;

public class WithdrawCoinRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.WITHDRAW_COIN;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.COIN_NAME, Parameter.AMOUNT, Parameter.ADDRESS};

    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        int transactionID = returnObject.getInt(T_ID.asAPIFriendlyValue());
        double amountSent = returnObject.getDouble(AMOUNT_SENT.asAPIFriendlyValue());
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS.asAPIFriendlyValue()));
        listeners.stream().filter(callback -> callback instanceof WithdrawCoinCallback).forEach(callback ->
                execute(() -> ((WithdrawCoinCallback) callback).onSuccess(transactionID, amountSent, funds))
        );
    }

    @Override
    public Parameter[] getRequiredParameters() {
        return PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

}

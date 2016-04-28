package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.WithdrawCoinCallback;
import org.json.JSONObject;

public class WithdrawCoinRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.WITHDRAW_COIN;

    public static final String[] PARAMS = new String[]{"coinName", "amount", "address"};

    private WithdrawCoinRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.1 - use WithdrawCoinRequest.Builder
     */
    @Deprecated
    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.1 - use WithdrawCoinRequest.Builder
     */
    @Deprecated
    public WithdrawCoinRequest(Authenticator authenticator, WithdrawCoinCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        int transactionID = returnObject.getInt(T_ID);
        double amountSent = returnObject.getDouble(AMOUNT_SENT);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        listeners.stream().filter(callback -> callback instanceof WithdrawCoinCallback).
                forEach(callback -> execute(
                        () -> ((WithdrawCoinCallback) callback).onSuccess(transactionID, amountSent, funds)
                )
        );
    }

    @Override
    public String[] getRequiredParams() {
        return PARAMS;
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
        public WithdrawCoinRequest build() {
            return new WithdrawCoinRequest(this);
        }

    }

}

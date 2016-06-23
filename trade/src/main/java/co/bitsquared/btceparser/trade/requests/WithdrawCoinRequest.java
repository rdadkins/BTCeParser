package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
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

    private WithdrawCoinRequest(Builder builder) {
        super(builder);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final int transactionID = returnObject.getInt(T_ID.asAPIFriendlyValue());
        final double amountSent = returnObject.getDouble(AMOUNT_SENT.asAPIFriendlyValue());
        final Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS.asAPIFriendlyValue()));
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof WithdrawCoinCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((WithdrawCoinCallback) callback).onSuccess(transactionID, amountSent, funds);
                    }
                });
            }
        }
    }

    @Override
    public Parameter[] getRequiredParameters() {
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

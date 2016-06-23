package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.InfoCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import org.json.JSONObject;

import static co.bitsquared.btceparser.trade.Constant.*;

public class InfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.GETINFO;

    public static final int TRANSACTION_COUNT = 0;

    private InfoCallback callback;

    private InfoRequest(Builder builder) {
        super(builder);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final JSONObject funds = returnObject.getJSONObject(FUNDS.asAPIFriendlyValue());
        final Funds[] accountFunds = extractFunds(funds);
        JSONObject rights = returnObject.getJSONObject(RIGHTS.asAPIFriendlyValue());
        final boolean accessInfo = rights.getInt(INFO.asAPIFriendlyValue()) == 1;
        final boolean canTrade = rights.getInt(TRADE.asAPIFriendlyValue()) == 1;
        final boolean canWithdraw = rights.getInt(WITHDRAW.asAPIFriendlyValue()) == 1;
        final int openOrders = returnObject.getInt(OPEN_ORDERS.asAPIFriendlyValue());
        final long serverTime = returnObject.getLong(SERVER_TIME.asAPIFriendlyValue());
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof InfoCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((InfoCallback) callback).onSuccess(accountFunds, accessInfo, canTrade, canWithdraw, TRANSACTION_COUNT, openOrders, serverTime);
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
        public InfoRequest build() {
            return new InfoRequest(this);
        }

    }

}

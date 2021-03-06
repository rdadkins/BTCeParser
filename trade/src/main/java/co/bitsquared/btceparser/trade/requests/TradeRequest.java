package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

import static co.bitsquared.btceparser.trade.Constant.*;

public class TradeRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.TRADE;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.PAIR, Parameter.TYPE, Parameter.RATE, Parameter.AMOUNT};

    private TradeRequest(Builder builder) {
        super(builder);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final double received = returnObject.getDouble(RECEIVED.asAPIFriendlyValue());
        final double remains = returnObject.getDouble(REMAINS.asAPIFriendlyValue());
        final int orderID = returnObject.getInt(ORDER_ID.asAPIFriendlyValue());
        final Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS.asAPIFriendlyValue()));
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof TradeRequestCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((TradeRequestCallback) callback).onSuccess(received, remains, orderID, funds);
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
        public TradeRequest build() {
            return new TradeRequest(this);
        }
    }

}

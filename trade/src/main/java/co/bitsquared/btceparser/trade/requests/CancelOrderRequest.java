package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.Constant;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CancelOrderCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

public class CancelOrderRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.CANCEL_ORDER;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.ORDER_ID};

    private CancelOrderRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.0 - use CancelOrderRequest.Builder
     */
    @Deprecated
    public CancelOrderRequest(Authenticator authenticator, CancelOrderCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.0 - use CancelOrderRequest.Builder
     */
    @Deprecated
    public CancelOrderRequest(Authenticator authenticator, CancelOrderCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final int orderId = returnObject.getInt(Constant.FUNDS.asAPIFriendlyValue());
        final Funds[] funds = extractFunds(returnObject.getJSONObject(Constant.FUNDS.asAPIFriendlyValue()));
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof CancelOrderCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((CancelOrderCallback) callback).onSuccess(orderId, funds);
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
        public CancelOrderRequest build() {
            return new CancelOrderRequest(this);
        }
    }

}

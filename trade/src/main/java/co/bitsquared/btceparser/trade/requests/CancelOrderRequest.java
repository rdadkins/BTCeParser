package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CancelOrderCallback;
import org.json.JSONObject;

public class CancelOrderRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.CANCEL_ORDER;

    public static final String[] PARAMS = new String[]{"order_id"};

    private CancelOrderRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.1 - use CancelOrderRequest.Builder
     */
    @Deprecated
    public CancelOrderRequest(Authenticator authenticator, CancelOrderCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2. - use CancelOrderRequest.Builder
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
        int orderId = returnObject.getInt(ORDER_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        listeners.stream().filter(callback -> callback instanceof CancelOrderCallback).
                forEach(callback -> execute(
                        () -> ((CancelOrderCallback) callback).onSuccess(orderId, funds)
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
        public CancelOrderRequest build() {
            return new CancelOrderRequest(this);
        }
    }

}

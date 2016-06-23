package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.ActiveOrderCallback;
import co.bitsquared.btceparser.trade.data.ActiveOrder;
import org.json.JSONObject;

public class ActiveOrderRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ACTIVE_ORDERS;

    private ActiveOrderRequest(Builder builder) {
        super(builder);
    }

    @Override
    protected void assignMethod(ParameterBuilder parameterBuilder) {
        parameterBuilder.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final ActiveOrder[] openOrders = new ActiveOrder[returnObject.keySet().size()];
        int position = 0;
        for (Object object: returnObject.keySet()) {
            int orderID = Integer.valueOf(object.toString());
            openOrders[position++] = new ActiveOrder(orderID, returnObject.getJSONObject(orderID + ""));
        }
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof ActiveOrderCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((ActiveOrderCallback) callback).onSuccess(openOrders);
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
        public ActiveOrderRequest build() {
            return new ActiveOrderRequest(this);
        }

    }

}

package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.trade.Constant;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CreateCouponCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

public class CreateCouponRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.CREATE_COUPON;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.CURRENCY, Parameter.AMOUNT};

    private CreateCouponRequest(Builder builder) {
        super(builder);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        final String coupon = returnObject.getString(Constant.COUPON.asAPIFriendlyValue());
        final long transactionID = returnObject.getLong(Constant.TRANS_ID.asAPIFriendlyValue());
        final Funds[] funds = extractFunds(returnObject.getJSONObject(Constant.FUNDS.asAPIFriendlyValue()));
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof CreateCouponCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((CreateCouponCallback) callback).onSuccess(coupon, transactionID, funds);
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
        public CreateCouponRequest build() {
            return new CreateCouponRequest(this);
        }

    }

}

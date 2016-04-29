package co.bitsquared.btceparser.trade.requests;

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

    public CreateCouponRequest(Authenticator authenticator, CreateCouponCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public CreateCouponRequest(Authenticator authenticator, CreateCouponCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        String coupon = returnObject.getString(Constant.COUPON.asAPIFriendlyValue());
        long transactionID = returnObject.getLong(Constant.TRANS_ID.asAPIFriendlyValue());
        Funds[] funds = extractFunds(returnObject.getJSONObject(Constant.FUNDS.asAPIFriendlyValue()));
        listeners.stream().filter(callback -> callback instanceof CreateCouponCallback).forEach(callback ->
                execute(() -> ((CreateCouponCallback) callback).onSuccess(coupon, transactionID, funds))
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

package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CreateCouponCallback;
import org.json.JSONObject;

public class CreateCouponRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.CREATE_COUPON;

    public static final String[] PARAMS = new String[]{"currency", "amount"};

    private CreateCouponCallback callback;

    public CreateCouponRequest(Authenticator authenticator, CreateCouponCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public CreateCouponRequest(Authenticator authenticator, CreateCouponCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        String coupon = returnObject.getString(COUPON);
        long transactionID = returnObject.getLong(TRANS_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        callback.onSuccess(coupon, transactionID, funds);
    }

    @Override
    public String[] getRequiredParams() {
        return PARAMS;
    }

    @Override
    public UpdatingAccountRequest asUpdatingRequest() {
        return new UpdatingAccountRequest(this, DEFAULT_UPDATING_TIME);
    }

}

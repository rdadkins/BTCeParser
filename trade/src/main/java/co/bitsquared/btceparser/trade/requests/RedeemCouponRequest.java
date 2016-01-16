package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.RedeemCouponCallback;
import org.json.JSONObject;

public class RedeemCouponRequest extends AccountRequest {

    private static final String[] PARAMS = new String[]{"coupon"};

    private RedeemCouponCallback callback;

    public RedeemCouponRequest(Authenticator authenticator, RedeemCouponCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    public RedeemCouponRequest(Authenticator authenticator, RedeemCouponCallback callback, long timeout) {
        super(authenticator, callback, timeout);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.REDEEM_COUPON);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        double couponAmount = returnObject.getDouble(COUPON_AMOUNT);
        Currency couponCurrency = Currency.toCurrency(returnObject.getString(COUPON_CURRENCY).toLowerCase());
        long transactionID = returnObject.getLong(TRANS_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        callback.onSuccess(couponAmount, couponCurrency, transactionID, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return PARAMS;
    }

}

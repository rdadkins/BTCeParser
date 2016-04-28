package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.data.TradableCurrency;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.RedeemCouponCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import org.json.JSONObject;

public class RedeemCouponRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.REDEEM_COUPON;

    private static final String[] PARAMS = new String[]{"coupon"};

    private RedeemCouponRequest(Builder builder) {
        super(builder);
    }

    /**
     * @deprecated since v2.2.1 - use RedeemCouponRequest.Builder
     */
    @Deprecated
    public RedeemCouponRequest(Authenticator authenticator, RedeemCouponCallback callback) {
        this(authenticator, callback, DEFAULT_TIMEOUT);
    }

    /**
     * @deprecated since v2.2.1 - use RedeemCouponRequest.Builder
     */
    @Deprecated
    public RedeemCouponRequest(Authenticator authenticator, RedeemCouponCallback callback, long timeout) {
        super(authenticator, callback, timeout);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        double couponAmount = returnObject.getDouble(COUPON_AMOUNT);
        TradableCurrency couponCurrency = TradableCurrency.toCurrency(returnObject.getString(COUPON_CURRENCY).toLowerCase());
        long transactionID = returnObject.getLong(TRANS_ID);
        Funds[] funds = extractFunds(returnObject.getJSONObject(FUNDS));
        listeners.stream().filter(callback -> callback instanceof RedeemCouponCallback).
                forEach(callback -> execute(
                        () -> ((RedeemCouponCallback) callback).onSuccess(couponAmount, couponCurrency, transactionID, funds)
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
        public RedeemCouponRequest build() {
            return new RedeemCouponRequest(this);
        }

    }

}

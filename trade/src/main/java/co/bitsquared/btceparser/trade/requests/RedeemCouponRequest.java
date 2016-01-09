package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.RedeemCouponCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class RedeemCouponRequest extends AccountRequest {

    private RedeemCouponCallback callback;

    public RedeemCouponRequest(Authenticator authenticator, long timeout, RedeemCouponCallback callback) {
        super(authenticator, timeout, callback);
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
        double couponAmount = returnObject.getDouble("couponAmount");
        Currency couponCurrency = Currency.toCurrency(returnObject.getString("couponCurrency").toLowerCase());
        long transactionID = returnObject.getLong("transID");
        Funds[] funds = extractFunds(returnObject.getJSONObject("funds"));
        callback.onSuccess(couponAmount, couponCurrency, transactionID, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return new String[]{"coupon"};
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

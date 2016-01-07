package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CreateCouponCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class CreateCouponRequest extends AccountRequest {

    private CreateCouponCallback callback;

    public CreateCouponRequest(Authenticator authenticator, long timeout, CreateCouponCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        String coupon = returnObject.getString("coupon");
        long transactionID = returnObject.getLong("transID");
        Funds[] funds = extractFunds(returnObject.getJSONObject("funds"));
        callback.onSuccess(coupon, transactionID, funds);
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

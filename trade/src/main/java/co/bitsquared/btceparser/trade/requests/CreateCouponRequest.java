package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.Funds;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.CreateCouponCallback;
import co.bitsquared.btceparser.trade.exceptions.MissingParametersException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class CreateCouponRequest extends AccountRequest {

    private CreateCouponCallback callback;

    public CreateCouponRequest(Authenticator authenticator, long timeout, CreateCouponCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        checkValidParams(parameters, this);
        parameters.method(TAPI.CREATE_COUPON);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        String coupon = returnObject.getString("coupon");
        long transactionID = returnObject.getLong("transID");
        Funds[] funds = extractFunds(returnObject.getJSONObject("funds"));
        callback.onSuccess(coupon, transactionID, funds);
    }

    @Override
    protected String[] getRequiredParams() {
        return new String[]{"currency", "amount"};
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

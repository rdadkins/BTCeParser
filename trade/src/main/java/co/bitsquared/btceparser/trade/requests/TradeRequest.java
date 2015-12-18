package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.trade.APIMethod;
import co.bitsquared.btceparser.trade.Currency;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TradeRequest extends AccountRequest {

    public TradeRequest(Authenticator authenticator, int timeout, int nonce, TradeRequestCallback callback) {
        super(authenticator, timeout, nonce, callback);
    }

    @Override
    public void processRequest(ParameterBuilder builder) {
        super.processRequest(builder.method(APIMethod.TRADE));
    }

    @Override
    public void completed(HttpResponse<JsonNode> httpResponse) {
        super.completed(httpResponse);
        JSONObject response = httpResponse.getBody().getObject();
        if (response.getInt("success") != 1) {
            return;
        }
        JSONObject returnObject = response.getJSONObject("return");
        double received = returnObject.getDouble("received");
        double remains = returnObject.getDouble("remains");
        int orderID = returnObject.getInt("order_id");
        System.out.println(returnObject.getJSONArray("funds").length());
//        Map<Currency, Double> currencyMap = getCurrencyMap(returnObject.getJSONObject("funds"));
        System.out.println(httpResponse.getBody());
    }

    private Map<Currency, Double> getCurrencyMap(JSONObject funds) {
        Map<Currency, Double> map = new HashMap<>();
        Iterator currencies = funds.keys();
        return null;
    }

    public void failed(UnirestException e) {
        if (callback != null) {
            callback.onError(e.getMessage());
        }
    }

    public void cancelled() {

    }

}

package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.requests.DepthType;
import co.bitsquared.btceparser.trade.ParameterBuilder;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.OrderInfoCallback;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.Set;

public class OrderInfoRequest extends AccountRequest {

    private OrderInfoCallback callback;

    public OrderInfoRequest(Authenticator authenticator, long timeout, OrderInfoCallback callback) {
        super(authenticator, timeout, callback);
        this.callback = callback;
    }

    @Override
    public void processRequest(ParameterBuilder parameters) {
        parameters.method(TAPI.ORDER_INFO);
        super.processRequest(parameters);
    }

    @Override
    public void processReturn(JSONObject returnObject) {
        Set set = returnObject.keySet();
        if (set.size() == 0) {
            return;
        }
        int orderID = Integer.parseInt(set.iterator().next().toString());
        JSONObject orderObject = returnObject.getJSONObject(orderID + "");
        TradingPair tradingPair = TradingPair.extract(orderObject.getString("pair"));
        DepthType type = orderObject.getString("type").equals("sell") ? DepthType.ASK : DepthType.BID;
        double startAmount = orderObject.getDouble("start_amount");
        double amount = orderObject.getDouble("amount");
        double rate = orderObject.getDouble("rate");
        long timeStamp = orderObject.getLong("timestamp_created");
        int status = orderObject.getInt("status");
        callback.onSuccess(orderID, tradingPair, type, startAmount, amount, rate, timeStamp, status);
    }

    @Override
    public void failed(UnirestException e) {

    }

    @Override
    public void cancelled() {

    }

}

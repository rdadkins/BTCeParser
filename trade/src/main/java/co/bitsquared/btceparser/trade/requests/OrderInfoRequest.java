package co.bitsquared.btceparser.trade.requests;

import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.core.data.DepthType;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.trade.TAPI;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.OrderInfoCallback;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder.Parameter;
import org.json.JSONObject;

import java.util.Set;

import static co.bitsquared.btceparser.trade.Constant.*;

public class OrderInfoRequest extends AccountRequest {

    public static final TAPI METHOD = TAPI.ORDER_INFO;

    public static final Parameter[] PARAMS = new Parameter[]{Parameter.ORDER_ID};

    private OrderInfoRequest(Builder builder) {
        super(builder);
    }

    @Override
    public void assignMethod(ParameterBuilder parameters) {
        parameters.method(METHOD);
    }

    @Override
    protected void processReturn(JSONObject returnObject) {
        Set set = returnObject.keySet();
        if (set.size() == 0) {
            return;
        }
        final int orderID = Integer.parseInt(set.iterator().next().toString());
        JSONObject orderObject = returnObject.getJSONObject(orderID + "");
        final TradingPair tradingPair = TradingPair.extract(orderObject.getString(PAIR.asAPIFriendlyValue()));
        final DepthType type = orderObject.getString(TYPE.asAPIFriendlyValue()).equals(SELL.asAPIFriendlyValue()) ? DepthType.ASK : DepthType.BID;
        final double startAmount = orderObject.getDouble(START_AMOUNT.asAPIFriendlyValue());
        final double amount = orderObject.getDouble(AMOUNT.asAPIFriendlyValue());
        final double rate = orderObject.getDouble(RATE.asAPIFriendlyValue());
        final long timeStamp = orderObject.getLong(TIMESTAMP_CREATED.asAPIFriendlyValue());
        final int status = orderObject.getInt(STATUS.asAPIFriendlyValue());
        for (final BaseRequestCallback callback: listeners) {
            if (callback instanceof OrderInfoCallback) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        ((OrderInfoCallback) callback).onSuccess(orderID, tradingPair, type, startAmount, amount, rate, timeStamp, status);
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
        public OrderInfoRequest build() {
            return new OrderInfoRequest(this);
        }

    }

}

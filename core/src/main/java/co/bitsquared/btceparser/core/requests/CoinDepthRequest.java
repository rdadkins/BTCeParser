package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinDepthCallback;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.exceptions.NullTradingPairException;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoinDepthRequest extends PublicRequest {

    private static final API METHOD = API.DEPTH;
    private static final String LIMIT_HEADER = "limit";

    private TradingPair tradingPair;
    private Map<String, Object> parameters = new HashMap<>();

    private CoinDepthRequest(Builder builder) {
        super(builder);
        tradingPair = builder.tradingPair;
        setDepthLimit(builder.depthLimit);
    }

    public void setDepthLimit(int depthLimit) {
        if (depthLimit > 0 && depthLimit <= API.MAX_ORDER_LIMIT) {
            parameters.put(LIMIT_HEADER, depthLimit);
        } else {
            parameters.put(LIMIT_HEADER, API.DEFAULT_ORDER_LIMIT);
        }
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        for (BaseRequestCallback callback: listeners) {
            if (!(callback instanceof CoinDepthCallback)) {
                continue;
            }
            final CoinDepthCallback depthCallback = (CoinDepthCallback) callback;
            OrderBook existingOrderBook = depthCallback.getExistingOrderBook();
            final OrderBook newOrderBook = Utils.extractOrderBook(body, tradingPair);
            if (existingOrderBook == null) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        depthCallback.onSuccess(newOrderBook);
                    }
                });
            } else {
                existingOrderBook.combineBooks(newOrderBook);
                execute(new Runnable() {
                    @Override
                    public void run() {
                        depthCallback.orderBookUpdated();
                    }
                });
            }
        }
    }

    @Override
    public Map<String, Object> getHeaders() {
        return parameters;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 10);
    }

    public static class Builder extends PublicRequest.Builder<Builder> {

        private TradingPair tradingPair;
        private int depthLimit = API.DEFAULT_ORDER_LIMIT;;

        public Builder(TradingPair tradingPair) {
            if (tradingPair == null) {
                throw new NullTradingPairException();
            }
            this.tradingPair = tradingPair;
        }

        /**
         * Sets the depth limit for this request. Default is set to {@code API.DEFAULT_ORDER_LIMIT} if this value is not set or
         * the value passed is out of range.
         * @param depthLimit the limit of orders for this request
         */
        public Builder depthLimit(int depthLimit) {
            this.depthLimit = depthLimit;
            return this;
        }

        @Override
        protected String getTargetUrl() {
            return METHOD.getUrl(tradingPair);
        }

        @Override
        protected Builder retrieveInstance() {
            return this;
        }

        @Override
        public CoinDepthRequest build() {
            return new CoinDepthRequest(this);
        }

    }

}

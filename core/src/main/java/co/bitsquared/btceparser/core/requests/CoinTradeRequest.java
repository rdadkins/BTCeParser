package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.callbacks.CoinTradeCallback;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.data.Trade;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.exceptions.NullTradingPairException;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CoinTradeRequest pulls recently executed trades with regards to the limit supplied. This is NOT a class to execute
 * a trade. Instead, to create a trade, that is found in the trade module.
 */
public class CoinTradeRequest extends PublicRequest {

    private static final API METHOD = API.TRADES;
    private static final String LIMIT_HEADER = "limit";

    private TradingPair tradingPair;
    private Map<String, Object> parameters = new HashMap<>();

    private CoinTradeRequest(Builder builder) {
        super(builder);
        setTradeLimit(builder.tradeLimit);
    }

    public CoinTradeRequest(TradingPair tradingPair, int limit, CoinTradeCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.tradingPair = tradingPair;
        setTradeLimit(limit);
    }

    public void setTradeLimit(int tradeLimit) {
        int limit;
        if (tradeLimit > 0 && tradeLimit <= API.MAX_TRADE_LIMIT) {
            limit = tradeLimit;
        } else {
            limit = API.DEFAULT_TRADE_LIMIT;
        }
        parameters.put(LIMIT_HEADER, limit);
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        listeners.stream().filter(callback -> callback instanceof CoinTradeCallback).forEach(callback -> {
            CoinTradeCallback listener = (CoinTradeCallback) callback;
            OrderBook existingOrderBook = listener.getExistingOrderBook();
            ArrayList<Trade> trades = Utils.extractTrades(body, tradingPair);
            if (existingOrderBook != null) {
                existingOrderBook.addTrades(trades);
                execute(listener::orderBookUpdated);
            }
            execute(() -> listener.onSuccess(trades));
        });
    }

    @Override
    protected Map<String, Object> getHeaders() {
        return parameters;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 5);
    }

    public static class Builder extends Request.Builder<Builder> {

        private TradingPair tradingPair;
        private int tradeLimit = API.DEFAULT_TRADE_LIMIT;

        public Builder(TradingPair tradingPair) {
            if (tradingPair == null) {
                throw new NullTradingPairException();
            }
            this.tradingPair = tradingPair;
        }

        /**
         * Sets the trade limit for this request. Default is set to {@code API.DEFAULT_TRADE_LIMIT} if this value is not set or
         * the value passed is out of range.
         */
        public Builder tradeLimit(int tradeLimit) {
            this.tradeLimit = tradeLimit;
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
        public CoinTradeRequest build() {
            return new CoinTradeRequest(this);
        }

    }

}

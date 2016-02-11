package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinDepthCallback;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoinDepthRequest extends PublicRequest {

    private static final API METHOD = API.DEPTH;
    private static final String LIMIT_HEADER = "limit";

    private TradingPair tradingPair;
    private Map<String, Object> parameters;
    private int depthLimit = API.DEFAULT_ORDER_LIMIT;

    public CoinDepthRequest(TradingPair tradingPair, int depthLimit, CoinDepthCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.tradingPair = tradingPair;
        parameters = new HashMap<>();
        setDepthLimit(depthLimit);
    }

    public void setDepthLimit(int depthLimit) {
        if (depthLimit > 0 && depthLimit <= API.MAX_ORDER_LIMIT) {
            this.depthLimit = depthLimit;
        }
        parameters.put(LIMIT_HEADER, this.depthLimit);
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        listeners.stream().filter(callback -> callback instanceof CoinDepthCallback).forEach(callback -> {
            CoinDepthCallback listener = (CoinDepthCallback) callback;
            OrderBook existingOrderBook = listener.getExistingOrderBook();
            OrderBook newOrderBook = Utils.extractOrderBook(body, tradingPair);
            if (existingOrderBook == null) {
                execute(() -> listener.onSuccess(newOrderBook));
            } else {
                existingOrderBook.combineBooks(newOrderBook);
                execute(listener::orderBookUpdated);
            }
        });
    }

    @Override
    public Map<String, Object> getHeaders() {
        return parameters;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 10);
    }

}

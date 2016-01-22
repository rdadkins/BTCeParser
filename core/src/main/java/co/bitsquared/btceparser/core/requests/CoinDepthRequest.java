package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
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
    private CoinDepthCallback listener;
    private Map<String, Object> parameters;
    private int depthLimit = API.DEFAULT_ORDER_LIMIT;

    public CoinDepthRequest(TradingPair tradingPair, int depthLimit, CoinDepthCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.tradingPair = tradingPair;
        this.listener = listener;
        parameters = new HashMap<String, Object>();
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
        if (listener != null) {
            OrderBook existingOrderBook = listener.getExistingOrderBook();
            OrderBook newOrderBook = Utils.extractOrderBook(body, tradingPair);
            if (existingOrderBook == null) {
                listener.onSuccess(newOrderBook);
            } else {
                existingOrderBook.combineBooks(newOrderBook);
                listener.orderBookUpdated();
            }
        }
    }

    @Override
    public Map<String, Object> getHeaders() {
        return parameters;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(url, listener, this, 5);
    }

}

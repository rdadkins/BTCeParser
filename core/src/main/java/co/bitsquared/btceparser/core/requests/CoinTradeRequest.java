package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTradeCallback;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.data.Trade;
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
    private CoinTradeCallback listener;
    private Map<String, Object> parameters = new HashMap<>();
    private int tradeLimit = API.DEFAULT_TRADE_LIMIT;

    public static void main(String[] args) {
        new CoinTradeRequest(TradingPair.BTC_USD, 10, null).processRequest();
    }

    public CoinTradeRequest(TradingPair tradingPair, int limit, CoinTradeCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.tradingPair = tradingPair;
        this.listener = listener;
        setTradeLimit(limit);
    }

    public void setTradeLimit(int tradeLimit) {
        if (tradeLimit > 0 && tradeLimit <= API.MAX_TRADE_LIMIT) {
            this.tradeLimit = tradeLimit;
        }
        parameters.put(LIMIT_HEADER, this.tradeLimit);
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        if (listener != null) {
            OrderBook existingOrderBook = listener.getExistingOrderBook();
            ArrayList<Trade> trades = Utils.extractTrades(body, tradingPair);
            if (existingOrderBook != null) {
                existingOrderBook.addTrades(trades);
                listener.orderBookUpdated();
            }
            listener.onSuccess(trades);
        }
    }

    @Override
    protected Map<String, Object> getHeaders() {
        return parameters;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 5);
    }

}

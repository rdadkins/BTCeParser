package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoCallback;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.Map;

/**
 * CoinInfoRequest gathers trading parameter information on a desired coin. Information that is obtained is used in how
 * to create valid trades with regards to decimal places, min / max price, fees, and the minimum amount to trade.
 */
public class CoinInfoRequest extends PublicRequest {

    private static final API METHOD = API.INFO;

    private TradingPair[] tradingPairs;

    public CoinInfoRequest(CoinInfoCallback listener, TradingPair... tradingPairs) {
        this(listener, DEFAULT_TIMEOUT, tradingPairs);
    }

    public CoinInfoRequest(CoinInfoCallback listener, long timeout, TradingPair... tradingPairs) {
        super(METHOD.getUrl(null), listener, timeout);
        if (tradingPairs.length == 0) {
            throw new RuntimeException("You must supply at least one TradingPair to make a CoinInfoRequest");
        }
        this.tradingPairs = tradingPairs;
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        JSONObject pairs = body.getJSONObject("pairs");
        CoinInfo[] coinInfoPairs = Utils.extractCoinInfo(pairs, tradingPairs);
        listeners.stream().filter(callback -> callback instanceof CoinInfoCallback).forEach(callback -> {
            CoinInfoCallback listener = (CoinInfoCallback) callback;
            for (CoinInfo coinInfo: coinInfoPairs) {
                execute(() -> listener.onSuccess(coinInfo));
            }
        });
    }

    @Override
    public Map<String, Object> getHeaders() {
        return DEFAULT_PARAMETERS;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 10);
    }

}

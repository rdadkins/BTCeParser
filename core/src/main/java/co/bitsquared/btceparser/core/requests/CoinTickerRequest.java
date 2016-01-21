package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.Map;

public class CoinTickerRequest extends PublicRequest {

    private static final API METHOD = API.TICKER;

    private CoinTickerCallback listener;
    private final TradingPair tradingPair;

    public CoinTickerRequest(TradingPair tradingPair, CoinTickerCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.listener = listener;
        this.tradingPair = tradingPair;
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        if (listener != null) {
            CoinTicker ticker = Utils.extractCoinTicker(body, tradingPair);
            listener.onSuccess(ticker);
        }
    }

    @Override
    public Map<String, Object> getHeaders() {
        return DEFAULT_PARAMETERS;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(url, listener, this, 4);
    }

}

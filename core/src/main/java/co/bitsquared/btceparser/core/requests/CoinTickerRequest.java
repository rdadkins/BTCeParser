package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import org.json.JSONObject;

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
            JSONObject coinTicker = body.getJSONObject(tradingPair.toString());
            listener.onSuccess(new CoinTicker(tradingPair, coinTicker));
        }
    }

}

package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.exceptions.NullTradingPairException;
import co.bitsquared.btceparser.core.utils.Utils;
import org.json.JSONObject;

import java.util.Map;

public class CoinTickerRequest extends PublicRequest {

    private static final API METHOD = API.TICKER;

    private final TradingPair tradingPair;

    private CoinTickerRequest(Builder builder) {
        super(builder);
        tradingPair = builder.tradingPair;
    }

    /**
     * @deprecated use CoinTickerRequest.Builder
     */
    @Deprecated
    public CoinTickerRequest(TradingPair tradingPair, CoinTickerCallback listener) {
        super(METHOD.getUrl(tradingPair), listener);
        this.tradingPair = tradingPair;
    }

    @Override
    protected void processResponseBody(JSONObject body) {
        listeners.stream().filter(callback -> callback instanceof CoinTickerCallback).forEach(callback -> {
            CoinTickerCallback listener = (CoinTickerCallback) callback;
            CoinTicker ticker = Utils.extractCoinTicker(body, tradingPair);
            execute(() -> listener.onSuccess(ticker));
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

    public static class Builder extends PublicRequest.Builder {

        private TradingPair tradingPair;

        public Builder(TradingPair tradingPair) {
            if (tradingPair == null) {
                throw new NullTradingPairException();
            }
            this.tradingPair = tradingPair;
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
        public CoinTickerRequest build() {
            return new CoinTickerRequest(this);
        }

    }

}

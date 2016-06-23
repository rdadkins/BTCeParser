package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.TradingPair;
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

    @Override
    protected void processResponseBody(final JSONObject body) {
        for (BaseRequestCallback callback: listeners) {
            if (!(callback instanceof CoinTickerRequest)) {
                continue;
            }
            final CoinTickerCallback tickerCallback = (CoinTickerCallback) callback;
            execute(new Runnable() {
                @Override
                public void run() {
                    tickerCallback.onSuccess(Utils.extractCoinTicker(body, tradingPair));
                }
            });
        }
    }

    @Override
    public Map<String, Object> getHeaders() {
        return DEFAULT_PARAMETERS;
    }

    @Override
    public PublicUpdatingRequest asUpdatingRequest() {
        return new PublicUpdatingRequest(this, 10);
    }

    public static class Builder extends PublicRequest.Builder<Builder> {

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

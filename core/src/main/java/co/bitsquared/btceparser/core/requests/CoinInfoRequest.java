package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.callbacks.BaseRequestCallback;
import co.bitsquared.btceparser.core.callbacks.CoinInfoCallback;
import co.bitsquared.btceparser.core.data.CoinInfo;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.exceptions.NullTradingPairException;
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

    private CoinInfoRequest(Builder builder) {
        super(builder);
        this.tradingPairs = builder.tradingPairs;
    }

    /**
     * @deprecated use CoinInfoRequest.Builder
     */
    @Deprecated
    public CoinInfoRequest(CoinInfoCallback listener, TradingPair... tradingPairs) {
        this(listener, DEFAULT_TIMEOUT, tradingPairs);
    }

    /**
     * @deprecated use CoinInfoRequest.Builder
     */
    @Deprecated
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
        for (BaseRequestCallback callback: listeners) {
            if (!(callback instanceof CoinInfoCallback)) {
                continue;
            }
            final CoinInfoCallback infoCallback = (CoinInfoCallback) callback;
            for (final CoinInfo coinInfo: coinInfoPairs) {
                execute(new Runnable() {
                    @Override
                    public void run() {
                        infoCallback.onSuccess(coinInfo);
                    }
                });
            }
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

        private TradingPair[] tradingPairs;

        /**
         * Creates a Builder with a default tradingPair and any additional pairs to be processed. This allows for 1-to-many
         * TradingPair's to be defined.
         * @param tradingPair the original tradingPair to process
         * @param additionalPairs additional tradingPairs to gather information on.
         */
        public Builder(TradingPair tradingPair, TradingPair... additionalPairs) {
            if (tradingPair == null) {
                throw new NullTradingPairException();
            }
            tradingPairs = new TradingPair[additionalPairs.length + 1];
            tradingPairs[0] = tradingPair;
            if (additionalPairs.length > 0) {
                System.arraycopy(additionalPairs, 0, tradingPairs, 1, additionalPairs.length);
            }
        }

        /**
         * Creates a CoinInfoRequest.Builder that contains ALL trading pairs found in TradingPair.
         *
         * @see co.bitsquared.btceparser.core.data.TradingPair
         */
        public Builder() {
            tradingPairs = TradingPair.values();
        }

        @Override
        protected String getTargetUrl() {
            return METHOD.getUrl(null);
        }

        @Override
        protected Builder retrieveInstance() {
            return this;
        }

        @Override
        public CoinInfoRequest build() {
            return new CoinInfoRequest(this);
        }

    }

}

package co.bitsquared.btceparser.core.requests;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoCallback;
import co.bitsquared.btceparser.core.data.CoinInfo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;

/**
 * CoinInfoRequest gathers trading parameter information on a desired coin. Information that is obtained is used in how
 * to create valid trades with regards to decimal places, min / max price, fees, and the minimum amount to trade.
 */
public class CoinInfoRequest extends PublicRequest {

    private static final API METHOD = API.INFO;

    private CoinInfoCallback listener;
    private TradingPair[] tradingPairs;

    public CoinInfoRequest(CoinInfoCallback listener, TradingPair... tradingPairs) {
        this(listener, DEFAULT_TIMEOUT, tradingPairs);
    }

    public CoinInfoRequest(CoinInfoCallback listener, long timeout, TradingPair... tradingPairs) {
        super(METHOD.getUrl(null), listener, timeout);
        if (tradingPairs.length == 0) {
            throw new RuntimeException("You must supply at least one TradingPair to make a CoinInfoRequest");
        }
        this.listener = listener;
        this.tradingPairs = tradingPairs;
    }

    @Override
    protected void processResponse(HttpResponse<JsonNode> response) {
        if (listener != null) {
            JSONObject pairs = response.getBody().getObject().getJSONObject("pairs");
            for (TradingPair tradingPair: tradingPairs) {
                JSONObject pairInfo = pairs.getJSONObject(tradingPair.toString());
                listener.onSuccess(tradingPair, new CoinInfo(tradingPair, pairInfo));
            }
        }
    }

}

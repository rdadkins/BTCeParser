package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinInfoUpdater;
import co.bitsquared.btceparser.core.callbacks.RequestCallback;
import co.bitsquared.btceparser.core.requests.AsyncRequest;
import co.bitsquared.btceparser.core.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * CoinInfo gathers the general coin information via GETINFO method that returns information of how to trade a coin / TradingPair.
 * @see co.bitsquared.btceparser.core.TradingPair for auto get requests on static initilization.
 */
public class CoinInfo implements RequestCallback<JsonNode> {

    private final TradingPair tradingPair;
    private CoinInfoUpdater updater;
    private AsyncRequest request;
    private JSONObject rawData;

    private int decimalPlaces;
    private double minPrice;
    private double maxPrice;
    private double minAmount;
    private boolean hidden;
    private double fee;

    public CoinInfo(TradingPair tradingPair, CoinInfoUpdater updater) {
        this.tradingPair = tradingPair;
        this.updater = updater;
    }

    public CoinInfo(TradingPair tradingPair, JSONObject data) {
        this.tradingPair = tradingPair;
        rawData = data;
        extractData(rawData);
    }

    public void processInfo() {
        if (rawData != null) {
            return;
        }
        if (request == null) {
            request = new AsyncRequest(API.INFO.getUrl(null), 10000, this, RequestType.INFO);
        }
        request.processRequest();
    }

    public void cancelled() {
        if (updater != null) {
            updater.cancelled();
        }
    }

    public void completed(HttpResponse<JsonNode> response, RequestType source) {
        JSONObject pairs = response.getBody().getObject().getJSONObject("pairs");
        rawData = pairs.getJSONObject(tradingPair.toString());
        extractData(rawData);
        if (updater != null) {
            updater.onInfoUpdate(tradingPair, decimalPlaces, minPrice, maxPrice, minAmount, hidden, fee);
        }
    }

    /**
     * Returns the amount of decimal places allowed for trading.
     */
    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Returns the minimum price allowed for a valid trade.
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price allowed for a valid trade.
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Returns the minimum amount allowed for a valid trade.
     */
    public double getMinAmount() {
        return minAmount;
    }

    /**
     * Determines whether this pair is hidden from the public.
     */
    public boolean isHidden() {
        return hidden;
    }

    public TradingPair getTradingPair() {
        return tradingPair;
    }

    private void extractData(JSONObject object) {
        decimalPlaces = getInt(object, "decimal_places");
        minPrice = getDouble(object, "min_price");
        maxPrice = getDouble(object, "max_price");
        minAmount = getDouble(object, "min_amount");
        hidden = getInt(object, "hidden") == 1;
        fee = getDouble(object, "fee");
    }

    public void failed(UnirestException reason) {
        if (updater != null) {
            updater.error(reason);
        }
    }

    private int getInt(JSONObject pair, String key) {
        return pair.getInt(key);
    }

    private double getDouble(JSONObject pairs, String key) {
        return pairs.getDouble(key);
    }

}

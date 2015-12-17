package com.fatsoapps.btceparser.core.data;

import com.fatsoapps.btceparser.core.API;
import com.fatsoapps.btceparser.core.TradingPair;
import com.fatsoapps.btceparser.core.callbacks.CoinInfoUpdater;
import com.fatsoapps.btceparser.core.callbacks.RequestCallback;
import com.fatsoapps.btceparser.core.requests.AsyncRequest;
import com.fatsoapps.btceparser.core.requests.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * CoinInfo gathers the general coin information via GETINFO method that returns information of how to trade a coin / TradingPair.
 */
public class CoinInfo implements RequestCallback<JsonNode> {

    private final TradingPair tradingPair;
    private CoinInfoUpdater updater;
    private AsyncRequest request;

    public CoinInfo(TradingPair tradingPair, CoinInfoUpdater updater) {
        this.tradingPair = tradingPair;
        this.updater = updater;
    }

    public void processInfo() {
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
        JSONObject myPair = pairs.getJSONObject(tradingPair.toString());
        int decimalPlaces = getInt(myPair, "decimal_places");
        double minPrice = getDouble(myPair, "min_price");
        double maxPrice = getDouble(myPair, "max_price");
        double minAmount = getDouble(myPair, "min_amount");
        boolean hidden = getInt(myPair, "hidden") == 1;
        double fee = getDouble(myPair, "fee");
        if (updater != null) {
            updater.onInfoUpdate(tradingPair, decimalPlaces, minPrice, maxPrice, minAmount, hidden, fee);
        }
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

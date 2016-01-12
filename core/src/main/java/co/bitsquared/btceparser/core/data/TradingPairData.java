package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.TradingPairUpdater;
import co.bitsquared.btceparser.core.callbacks.RequestCallback;
import co.bitsquared.btceparser.core.requests.PublicRequest;
import co.bitsquared.btceparser.core.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class TradingPairData implements RequestCallback<JsonNode> {

	private PublicRequest tickerRequest;
	private TradingPairUpdater dataUpdateListener;
	private final TradingPair tradePair;
	private double highPrice;
	private double lowPrice;
	private double volume;
	private double averagePrice;
	private double buyPrice;
	private double sellPrice;
	private double lastPrice;
	private int lastUpdated;

	public TradingPairData(TradingPair tradePair, TradingPairUpdater dataUpdateListener) {
		this.tradePair = tradePair;
		this.dataUpdateListener = dataUpdateListener;
	}

    /**
     * Updates all information at once.
     */
	public void updateAllInfo() {
		updateInfo(RequestType.ALL);
	}

    /**
     * Updates a certain piece via RequestType and that corresponding method will be called through TradingPairUpdater
     */
	public void updateInfo(RequestType source){
		if (tickerRequest == null) {
			tickerRequest = new PublicRequest(API.TICKER.getUrl(tradePair), 10000, this, source);
		}
		if (!tickerRequest.isDone()) {
			tickerRequest.cancelRequest();
		}
		tickerRequest.processRequest();
	}
	
	public void completed(HttpResponse<JsonNode> response, RequestType source) {
        handleInfo(response.getBody().getObject().getJSONObject(tradePair.toString()), source);
        if (source != RequestType.ALL) {
            update(RequestType.TIME);
        }
	}

    private void handleInfo(JSONObject objects, RequestType source) {
        setValues(objects);
        update(source);
    }

    private void setValues(JSONObject tickerObjects){
        highPrice = tickerObjects.getDouble("high");
        lowPrice = tickerObjects.getDouble("low");
        averagePrice = tickerObjects.getDouble("avg");
        lastPrice = tickerObjects.getDouble("last");
        volume = tickerObjects.getDouble("vol");
        buyPrice = tickerObjects.getDouble("buy");
        sellPrice = tickerObjects.getDouble("sell");
        lastUpdated = tickerObjects.getInt("updated");
    }

    private void update(RequestType source) {
        switch (source) {
            case ALL:
                dataUpdateListener.onUpdateAll(lastPrice, volume, highPrice, lowPrice, lastUpdated, buyPrice, sellPrice, averagePrice);
                break;
            case VOLUME:
                dataUpdateListener.onUpdateVolume(volume); break;
            case AVERAGE:
                dataUpdateListener.onUpdateAveragePrice(averagePrice); break;
            case LAST:
                dataUpdateListener.onUpdateLastPrice(lastPrice); break;
            case HIGH:
                dataUpdateListener.onUpdateHighPrice(highPrice); break;
            case LOW:
                dataUpdateListener.onUpdateLowPrice(lowPrice); break;
            case BUY:
                dataUpdateListener.onUpdateBuyPrice(buyPrice); break;
            case SELL:
                dataUpdateListener.onUpdateSellPrice(sellPrice); break;
            case TIME:
                dataUpdateListener.onUpdateTimeStamp(lastUpdated); break;
        }
    }

	public double getHighPrice() {
		return highPrice;
	}

	public double getLowPrice() {
		return lowPrice;
	}

	public double getVolume() {
		return volume;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public double getBuyPrice() {
		return buyPrice;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public int getLastUpdatedTime() {
		return lastUpdated;
	}

	public TradingPair getTradePair() {
		return tradePair;
	}

	public void failed(UnirestException reason) {

	}
	
	public void cancelled() {
		
	}

}

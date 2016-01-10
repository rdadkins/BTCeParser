package co.bitsquared.btceparser.core.data;

import co.bitsquared.btceparser.core.API;
import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinDataUpdater;
import co.bitsquared.btceparser.core.callbacks.RequestCallback;
import co.bitsquared.btceparser.core.currency.BaseCurrency;
import co.bitsquared.btceparser.core.requests.AsyncRequest;
import co.bitsquared.btceparser.core.DepthType;
import co.bitsquared.btceparser.core.requests.UpdatingDepthRequest;
import co.bitsquared.btceparser.core.currency.Currency;
import co.bitsquared.btceparser.core.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CoinData implements RequestCallback<JsonNode> {

	private CoinDataUpdater dataUpdateListener;
	private final TradingPair tradePair;
	private double highPrice;
	private double lowPrice;
	private double volume;
	private double averagePrice;
	private double buyPrice;
	private double sellPrice;
	private double lastPrice;
	private int lastUpdated;
    private OrderBook orderBook;

	public CoinData(TradingPair tradePair, CoinDataUpdater dataUpdateListener) {
		this.tradePair = tradePair;
		this.dataUpdateListener = dataUpdateListener;
	}

	public void updateLastPrice(){
		updateInfo(RequestType.LAST);
	}
	
	public void updateHighPrice(){
		updateInfo(RequestType.HIGH);
	}
	
	public void updateLowPrice(){
		updateInfo(RequestType.LOW);
	}
	
	public void updateBuyPrice(){
		updateInfo(RequestType.BUY);
	}
	
	public void updateSellPrice(){
		updateInfo(RequestType.SELL);
	}
	
	public void updateVolume(){
		updateInfo(RequestType.VOLUME);
	}
	
	public void updateAllInfo() {
		updateInfo(RequestType.ALL);
	}
	
	public void updateInfo(RequestType source){
		new AsyncRequest(API.TICKER.getUrl(tradePair), 10000, this, source).processRequest();
	}
	
	public void updateDepth() {
        updateDepth(50, 0);
	}
	
	public void updateDepth(int depth, int interval) {
        new UpdatingDepthRequest(API.DEPTH.getUrl(tradePair), 10000, depth, interval, this).processRequest();
	}
	
	public void completed(HttpResponse<JsonNode> response, RequestType source) {
		if (source == RequestType.DEPTH) {
			handleOrders(response.getBody().getObject().getJSONObject(tradePair.toString()));
		} else {
			handleInfo(response.getBody().getObject().getJSONObject(tradePair.toString()), source);
			update(RequestType.TIME);
		}
	}

	public void failed(UnirestException reason) {
		System.out.println("Failed: " + reason);
	}
	
	public void cancelled() {
		
	}

	private void handleOrders(JSONObject objects) {
        orderBook.addAskBook(processOrders(objects, "asks"));
        orderBook.addBidBook(processOrders(objects, "bids"));
		System.out.println("Bid Volume | Depth: " + orderBook.getTotalBidVolume().asBigDecimal() + " | " + orderBook.getTotalBidDepth(true).asBigDecimal() + " | " + orderBook.getTotalBidDepth(false).asBigDecimal());
		System.out.println("Ask Volume: " + orderBook.getTotalAskVolume().asBigDecimal());
	}

	private ArrayList<Order> processOrders(JSONObject objects, String orderType) {
        ArrayList<Order> orderBook = new ArrayList<Order>();
        JSONArray trades = objects.getJSONArray(orderType);
        DepthType type = (orderType.equals("asks") ? DepthType.ASK : DepthType.BID);
        Order order;
        for (int i = trades.length() - 1; i > -1; i--) {
            order = tradePair.getOrderTemplate(
                    trades.getJSONArray(i).getDouble(0),
                    trades.getJSONArray(i).getDouble(1),
                    type
            );
            orderBook.add(order);
        }
        return orderBook;
	}

    private BaseCurrency<?> processOrder(BaseCurrency<?> baseCurrency, double value) {
        if (baseCurrency instanceof Currency) {
            value *= 1000;
        }
        return (BaseCurrency<?>) baseCurrency.multiply(BigDecimal.valueOf(value));
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
                update(RequestType.VOLUME);
                update(RequestType.AVERAGE);
                update(RequestType.LAST);
                update(RequestType.HIGH);
                update(RequestType.LOW);
                update(RequestType.BUY);
                update(RequestType.SELL);
                update(RequestType.FEE);
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
	
	protected void closeConnections() {
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		// TODO - better toString representation of CoinData
		return tradePair + ":" + highPrice;
	}

}

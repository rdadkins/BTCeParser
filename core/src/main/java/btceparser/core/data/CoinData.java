package btceparser.core.data;

import btceparser.core.API;
import btceparser.core.TradingPair;
import btceparser.core.callbacks.DepthDataUpdater;
import btceparser.core.callbacks.RequestCallback;
import btceparser.core.callbacks.CoinDataUpdater;
import btceparser.core.currency.Coin;
import btceparser.core.currency.Currency;
import btceparser.core.requests.AsyncRequest;
import btceparser.core.requests.DepthType;
import btceparser.core.requests.RequestType;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import static btceparser.core.requests.RequestType.*;

public class CoinData implements RequestCallback<JsonNode> {

	private JSONObject currentList;
	private CoinDataUpdater dataUpdateListener;
	private DepthDataUpdater depthListener;
	private TradingPair tradePair;
	private double highPrice;
	private double lowPrice;
	private double volume;
	private double averagePrice;
	private double buyPrice;
	private double sellPrice;
	private double lastPrice;
	private double fee;
	private int decimalPlaces = 3;
	private int lastUpdated;
	private int serverTime;

	public CoinData(TradingPair tradePair, CoinDataUpdater dataUpdateListener, DepthDataUpdater depthListener) {
		this.tradePair = tradePair;
		this.dataUpdateListener = dataUpdateListener;
		this.depthListener = depthListener;
		updateCoinSpecs();
	}
	
	public void updateLastPrice(){
		updateInfo(LAST);
	}
	
	public void updateHighPrice(){
		updateInfo(HIGH);
	}
	
	public void updateLowPrice(){
		updateInfo(LOW);
	}
	
	public void updateBuyPrice(){
		updateInfo(BUY);
	}
	
	public void updateSellPrice(){
		updateInfo(SELL);
	}
	
	public void updateVolume(){
		updateInfo(VOLUME);
	}
	
	public void updateAllInfo(){
		updateInfo(ALL);
	}
	
	public void updateInfo(RequestType source){
		new AsyncRequest(API.TICKER.getUrl(tradePair), 10000, this, source).processRequest();
	}
	
	private void updateCoinSpecs(){
		try {
			HttpResponse<JsonNode> result = Unirest.get(API.INFO.getUrl(null)).asJson();
			JSONObject pairInfo = result.getBody().getObject().getJSONObject("pairs").getJSONObject(tradePair.toString());
			fee = pairInfo.getDouble("fee");
			decimalPlaces = pairInfo.getInt("decimal_places");
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	public void updateDepth() {
		new AsyncRequest(API.DEPTH.getUrl(tradePair), 10000, this, DEPTH).processRequest();
	}
	
	public void updateDepth(int numberOfRequests) {
		new AsyncRequest(API.DEPTH.getUrl(tradePair), 10000, numberOfRequests, this, DEPTH).processRequest();
	}
	
	public void completed(HttpResponse<JsonNode> response, RequestType source) {
		if (source == DEPTH) {
			handleOrders(response.getBody().getObject().getJSONObject(tradePair.toString()));
		} else {
			handleInfo(response.getBody().getObject().getJSONObject(tradePair.toString()), source);
		}
	}

	public void completed(HttpResponse<JsonNode> httpResponse) {

	}

	public void failed(UnirestException reason) {
		System.out.println("Failed: " + reason);
	}
	
	public void cancelled() {
		
	}

	private void handleOrders(JSONObject objects) {
		depthListener.updateDepth(processOrders(objects, "asks"), processOrders(objects, "bids"));
	}

	private ArrayList<Order> processOrders(JSONObject objects, String orderType) {
		ArrayList<Order> orderBook = new ArrayList<Order>();
		Currency dollar = new Currency();
		Coin coin;
		dollar.setDecimalPlaces(decimalPlaces);
		
		JSONArray trades = objects.getJSONArray(orderType);
        DepthType type = (orderType.equals("asks") ? DepthType.ASK : DepthType.BID);
        for (int i = trades.length() -1; i != 0; i--) {
            dollar = new Currency(trades.getJSONArray(i).getDouble(0));
            coin = new Coin(trades.getJSONArray(i).getDouble(1));
			orderBook.add(new Order(dollar, coin, type));
        }
		return orderBook;
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
                update(VOLUME);
                update(AVERAGE);
                update(LAST);
                update(HIGH);
                update(LOW);
                update(BUY);
                update(SELL);
                update(FEE);
                break;
            case VOLUME:
                dataUpdateListener.updateVolume(volume); break;
            case AVERAGE:
                dataUpdateListener.updateAveragePrice(averagePrice); break;
            case LAST:
                dataUpdateListener.updateLastPrice(lastPrice); break;
            case HIGH:
                dataUpdateListener.updateHighPrice(highPrice); break;
            case LOW:
                dataUpdateListener.updateLowPrice(lowPrice); break;
            case BUY:
                dataUpdateListener.updateBuyPrice(buyPrice); break;
            case SELL:
                dataUpdateListener.updateSellPrice(sellPrice); break;
            case FEE:
                dataUpdateListener.updateFee(fee); break;
		}
	}
	
	protected void closeConnections() {
		try {
			Unirest.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(){
		return tradePair + ":" + highPrice;
	}

}

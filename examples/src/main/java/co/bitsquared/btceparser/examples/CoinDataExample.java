package co.bitsquared.btceparser.examples;

import co.bitsquared.btceparser.core.TradingPair;
import co.bitsquared.btceparser.core.callbacks.DepthUpdater;
import co.bitsquared.btceparser.core.data.CoinData;
import co.bitsquared.btceparser.core.data.Order;

import java.util.ArrayList;

public class CoinDataExample implements BaseExample, DepthUpdater {

    private TradingPair pair;
    private CoinData coinData;

    public static void main(String[] args) {
        new CoinDataExample().startExample();
    }

    public CoinDataExample() {
        pair = TradingPair.BTC_USD;
        coinData = new CoinData(pair, this);
    }

    public void startExample() {
        coinData.updateAllInfo();
    }

    public void updateAll(ArrayList<Object> objects) {

    }

    public void onUpdateLastPrice(double lastPrice) {
        System.out.println("Last Price: " + lastPrice);
    }

    public void onUpdateVolume(double volume) {
        System.out.println("Volume: " + volume);
    }

    public void onUpdateHighPrice(double highPrice) {
        System.out.println("High Price: " + highPrice);
    }

    public void onUpdateLowPrice(double lowPrice) {
        System.out.println("Low Price: " + lowPrice);
    }

    public void onUpdateTimeStamp(int timeStamp) {
        System.out.println("Timestamp: " + timeStamp);
    }

    public void onUpdateBuyPrice(double buyPrice) {
        System.out.println("Buy Price: " + buyPrice);
    }

    public void onUpdateSellPrice(double sellPrice) {
        System.out.println("Sell Price: " + sellPrice);
    }

    public void onUpdateAveragePrice(double averagePrice) {
        System.out.println("Average Price: " + averagePrice);
    }

    public void onUpdateFee(double fee) {
        System.out.println("Fee: " + fee);
    }

    public void onComplete() {

    }

    public void onError(String reason) {

    }

    public void onDepthUpdate(ArrayList<Order> bids, ArrayList<Order> asks) {

    }
}

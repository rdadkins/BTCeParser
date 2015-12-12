package com.fatsoapps.btceparser.examples;

import com.fatsoapps.btceparser.core.TradingPair;
import com.fatsoapps.btceparser.core.callbacks.DepthDataUpdater;
import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.data.CoinData;
import com.fatsoapps.btceparser.core.data.Order;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CoinInformationExample implements BaseExample, DepthDataUpdater {

    private TradingPair pair;
    private CoinData coinData;

    public static void main(String[] args) {
        new CoinInformationExample().startExample();
    }

    public CoinInformationExample() {
        pair = TradingPair.BTC_USD;
        coinData = new CoinData(pair, this);
    }

    public void startExample() {
        coinData.updateDepth(5);
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

    public void updateDepth(ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> bids, ArrayList<Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>>> asks) {
        BaseCurrency<?> bidDepth = bids.get(0).getPriceCurrency().multiply(BigDecimal.ZERO);
        for (Order bid: bids) {
            bidDepth = bidDepth.add(bid.getOrderTotal());
        }
        System.out.println("Bid Depth: " + bidDepth.asBigDecimal());
        BaseCurrency<?> askDepth = asks.get(0).getPriceCurrency().multiply(BigDecimal.ZERO);
        for (Order ask: asks) {
            askDepth = askDepth.add(ask.getOrderTotal());
        }
        System.out.println("Ask Depth: " + askDepth.asBigDecimal());
    }
}

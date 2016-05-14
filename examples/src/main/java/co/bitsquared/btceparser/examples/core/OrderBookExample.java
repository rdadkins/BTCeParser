package co.bitsquared.btceparser.examples.core;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.callbacks.CoinDepthCallback;
import co.bitsquared.btceparser.core.callbacks.CoinTradeCallback;
import co.bitsquared.btceparser.core.data.OrderBook;
import co.bitsquared.btceparser.core.data.Trade;
import co.bitsquared.btceparser.core.requests.CoinDepthRequest;
import co.bitsquared.btceparser.core.requests.CoinTradeRequest;
import co.bitsquared.btceparser.core.requests.PublicUpdatingRequest;
import co.bitsquared.btceparser.examples.BaseExample;

import java.util.ArrayList;

public class OrderBookExample implements BaseExample, CoinTradeCallback, CoinDepthCallback {

    private TradingPair tradingPair = TradingPair.BTC_USD;
    private OrderBook myOrderBook;

    public static void main(String[] args) {
        new OrderBookExample().startExample();
    }

    public void startExample() {
        startUpdatingTradeRequest();
        startUpdatingDepthRequest();
    }

    private void startUpdatingTradeRequest() {
        CoinTradeRequest tradeRequest = new CoinTradeRequest.Builder(tradingPair).callback(this).tradeLimit(100).build();
        PublicUpdatingRequest publicUpdatingRequest = tradeRequest.asUpdatingRequest();
        publicUpdatingRequest.updateInterval(7, false);
        publicUpdatingRequest.processRequest();
    }

    private void startUpdatingDepthRequest() {
        CoinDepthRequest depthRequest = new CoinDepthRequest(tradingPair, 100, this);
        PublicUpdatingRequest depthUpdatingRequest = depthRequest.asUpdatingRequest();
        depthUpdatingRequest.updateInterval(13, false);
        depthUpdatingRequest.processRequest();
    }

    public void onSuccess(ArrayList<Trade> executedTrades) {
        System.out.println("Total new trades: " + executedTrades.size());
    }

    public void onSuccess(OrderBook orderBook) {
        System.out.println("Separate OrderBook supplied.");
        myOrderBook = orderBook;
    }

    public OrderBook getExistingOrderBook() {
        return myOrderBook;
    }

    public void orderBookUpdated() {
        System.out.println("OrderBook has been updated.");
        System.out.println("\tBid Volume: " + myOrderBook.getTotalBidVolume().asBigDecimal() +
                "\n\tBid Depth: " + myOrderBook.getTotalBidDepth(0.0).asBigDecimal());
        System.out.println("\tAsk Volume: " + myOrderBook.getTotalAskVolume().asBigDecimal() +
                "\n\tAsk Depth: " + myOrderBook.getTotalAskDepth(0.0).asBigDecimal());
    }

    public void cancelled() {
        System.out.println("User cancelled this request.");
    }

    public void error(String reason) {
        System.out.println("Error: " + reason);
    }

    public void onSuccess() {
    }

}

package co.bitsquared.btceparser.examples.trade;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.currency.Coin;
import co.bitsquared.btceparser.core.currency.Currency;
import co.bitsquared.btceparser.examples.BaseExample;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.data.OrderType;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import co.bitsquared.btceparser.trade.requests.TradeRequest;

/**
 * TradeExample shows how to use TradeRequest. This example requires you to supply your key / secret. This example will
 * create a trade in the form of selling 0.01 BTC at a rate of 500 USD. When onSuccess(received...) is called, you will
 * see how your trade was processed.
 */
public class TradeExample implements BaseExample, TradeRequestCallback {

    private String key = "YourKeyHere";
    private String secret = "YourSecretHere";

    public static void main(String[] args) {
        new TradeExample().startExample();
    }

    public void startExample() {
        Authenticator authenticator = new Authenticator(key, secret, 1);
        TradeRequest tradeRequest = new TradeRequest.Builder(authenticator).callback(this).build();
        tradeRequest.assignParameters(getParams());
        tradeRequest.processRequest();
    }

    private ParameterBuilder getParams() {
        return ParameterBuilder.createBuilder().
                addTradingPair(TradingPair.BTC_USD).
                addOrderType(OrderType.SELL).
                addRate(new Currency(500)).
                addAmount(Coin.fromDouble(0.01));
    }

    public void onSuccess(double received, double remains, int orderID, Funds[] funds) {
        System.out.println("Order ID: " + orderID);
        System.out.println("Received: " + received);
        System.out.println("Remains: " + remains);
        for (Funds fund: funds) {
            System.out.println(fund);
        }
    }

    public void onSuccessReturn() {
        System.out.println("Trade was submitted successfully");
    }

    public void onUnsuccessfulReturn(String reason) {
        System.out.println(reason);
    }

    public void cancelled() {

    }

    public void error(String reason) {
        System.out.println("Network error: " + reason);
    }

    public void onSuccess() {
        System.out.println("Successful connection");
    }

}

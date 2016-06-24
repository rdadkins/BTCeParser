package co.bitsquared.btceparser.examples.bot;

import co.bitsquared.btceparser.bot.actions.MarketChangeAction;
import co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm;
import co.bitsquared.btceparser.bot.conditions.MarketChangeCondition;
import co.bitsquared.btceparser.bot.data.Comparison;
import co.bitsquared.btceparser.bot.data.PreOrder;
import co.bitsquared.btceparser.bot.data.TradeType;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.core.requests.CoinTickerRequest;
import co.bitsquared.btceparser.core.requests.PublicUpdatingRequest;
import co.bitsquared.btceparser.examples.BaseExample;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import co.bitsquared.btceparser.trade.data.Funds;

/**
 * This example shows how to use the base bot implementation by using a MarketChangeAlgorithm. In the constructor, all
 * backbone tools defining the algorithm are created and supplied to MarketChangeAlgorithm. In {@code startExample()}, an
 * updating request is defined and a key point to be made is that the updating request allows for a callback to be registered to it.
 * Since MarketChangeAlgorithm implements CoinTickerCallback, this will allow for the algorithm itself to be registered.
 * Since we want to know the trades being executed by the algorithm, we call {@code TradeAlgorithm.registerTradeCallback()}. A key
 *
 * @see co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm
 * @see co.bitsquared.btceparser.bot.actions.MarketChangeAction
 * @see co.bitsquared.btceparser.bot.conditions.MarketChangeCondition
 */
public class MarketChangeExample implements BaseExample, CoinTickerCallback, TradeRequestCallback {

    private TradingPair tradingPair = TradingPair.BTC_USD;
    private String key = "yourApiKey";
    private String secret = "yourApiSecret";
    private long nonce = 1;
    private MarketChangeAlgorithm algorithm;

    public static void main(String[] args) {
        new MarketChangeExample().startExample();
    }

    public MarketChangeExample() {
        PreOrder preOrder = new PreOrder(tradingPair, TradeType.BUY, 400, 0.05);
        MarketChangeCondition condition = new MarketChangeCondition(400, Comparison.GREATER_THAN);
        MarketChangeAction marketAction = new MarketChangeAction(preOrder, condition);
        Authenticator authenticator = new Authenticator(key, secret, nonce);
        algorithm = new MarketChangeAlgorithm(authenticator, marketAction);
    }

    public void startExample() {
        CoinTickerRequest request = new CoinTickerRequest.Builder(tradingPair).callback(this).build();
        PublicUpdatingRequest updatingRequest = request.asUpdatingRequest();
        updatingRequest.registerBaseRequestCallback(algorithm);
        updatingRequest.processRequest();
        algorithm.registerTradeCallback(this);
    }

    public void onSuccess(CoinTicker coinTicker) {
        System.out.println(coinTicker.toString());
    }

    public void cancelled() {

    }

    public void error(String s) {
        System.out.println("Error: " + s);
    }

    public void onSuccess() {

    }

    public void onSuccess(double received, double remains, int orderID, Funds[] funds) {
        System.out.println("Trade executed: " + received);
    }

    public void onSuccessReturn() {

    }

    public void onUnsuccessfulReturn(String reason) {

    }

    @Override
    public void onFailure() {

    }

}

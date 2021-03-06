package co.bitsquared.btceparser.bot.algorithms;

import co.bitsquared.btceparser.bot.actions.BaseAction;
import co.bitsquared.btceparser.bot.data.PreOrder;
import co.bitsquared.btceparser.trade.authentication.Authenticator;
import co.bitsquared.btceparser.trade.callbacks.TradeRequestCallback;
import co.bitsquared.btceparser.trade.data.Funds;
import co.bitsquared.btceparser.trade.requests.TradeRequest;

/**
 * This is the basis of a trading algorithms defined. Each implementing class must be an implementation of TradeRequestCallback
 * and some sort of other BaseRequest. This allows for algorithms to register themselves as listeners to Requests.
 *
 * @see co.bitsquared.btceparser.core.requests.Request
 */
public abstract class TradeAlgorithm implements TradeRequestCallback {

    private final BaseAction action;
    private final TradeRequest tradeRequest;

    public TradeAlgorithm(Authenticator authenticator, BaseAction action) {
        this.action = action;
        tradeRequest = new TradeRequest.Builder(authenticator).callback(this).build();
    }

    public final void executeTrade() {
        PreOrder preOrder = action.getPreOrder();
        if (preOrder.isActiveTrade()) {
            tradeRequest.processRequest(preOrder.asParams());
        }
    }

    public final void registerTradeCallback(TradeRequestCallback callback) {
        tradeRequest.registerBaseRequestCallback(callback);
    }

    public final void onSuccess(double received, double remains, int orderID, Funds[] funds) {

    }

    public final void onSuccessReturn() {

    }

    public final void onUnsuccessfulReturn(String reason) {

    }

    public final void onSuccess() {

    }

    public final void cancelled() {

    }

    public final void error(String s) {

    }

}

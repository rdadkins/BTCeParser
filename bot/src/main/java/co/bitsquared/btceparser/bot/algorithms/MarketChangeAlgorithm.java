package co.bitsquared.btceparser.bot.algorithms;

import co.bitsquared.btceparser.bot.actions.MarketChangeAction;
import co.bitsquared.btceparser.core.callbacks.CoinTickerCallback;
import co.bitsquared.btceparser.core.data.CoinTicker;
import co.bitsquared.btceparser.trade.authentication.Authenticator;

/**
 * MarketChangeAlgorithm simply waits for the price to reach a certain point to execute a PreOrder.
 * <br /> Example: You bought 10 BTC at 350 and you set a MarketChangeAlgorithm to automatically sell some (or all) of the 10 BTC you
 * purchased at a certain rate (say 375). You lock in a guaranteed rate if the market reaches that point.
 * <br /> Example: Similarly, you sold 10 BTC at 350 and you want to buy back 10 more BTC at 325. This only happens when the current
 * asking price is at the price you set.
 *
 * @see co.bitsquared.btceparser.bot.data.PreOrder
 */
public class MarketChangeAlgorithm extends TradeAlgorithm implements CoinTickerCallback {

    private MarketChangeAction action;

    public MarketChangeAlgorithm(Authenticator authenticator, MarketChangeAction action) {
        super(authenticator, action);
        this.action = action;
    }

    public void onSuccess(CoinTicker coinTicker) {
        if (action.conditionsAreMet(coinTicker)) {
            executeTrade();
        }
    }

}

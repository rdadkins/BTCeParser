package co.bitsquared.btceparser.bot.data;

import co.bitsquared.btceparser.core.data.TradingPair;
import co.bitsquared.btceparser.trade.requests.ParameterBuilder;

/**
 * A PreOrder is a pre-defined order that can be executed once certain conditions are met. These can be defined by a user
 * or automatically as the 'bot' implementation grows.
 */
public class PreOrder {

    private final TradingPair tradingPair;
    private final TradeType tradeType;
    private final double rate;
    private final double amount;

    public PreOrder(TradingPair tradingPair, TradeType tradeType, double rate, double amount) {
        this.tradingPair = tradingPair;
        this.tradeType = tradeType;
        this.rate = rate;
        this.amount = amount;
    }

    public ParameterBuilder asParams() {
        ParameterBuilder builder = ParameterBuilder.createBuilder();
        if (tradeType != TradeType.HOLD) {
            builder.addTradingPair(tradingPair).addRate(rate).addAmount(amount).addOrderType(tradeType.toOrderType());
        }
        return builder;
    }

    /**
     * Determines if this PreOrder is an actual order by checking if the TradeType provided is TradeType.HOLD.
     * @return whether or not this is an active trade
     */
    public boolean isActiveTrade() {
        return tradeType != TradeType.HOLD;
    }

}

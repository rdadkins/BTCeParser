package co.bitsquared.btceparser.bot.data;

import co.bitsquared.btceparser.trade.requests.ParameterBuilder;

/**
 * A PreOrder is a pre-defined order that can be executed once certain conditions are met. These can be defined by a user
 * or automatically as the 'bot' implementation grows.
 */
public class PreOrder {

    private final TradeType tradeType;
    private final double rate;
    private final double amount;

    public PreOrder(TradeType tradeType, double rate, double amount) {
        this.tradeType = tradeType;
        this.rate = rate;
        this.amount = amount;
    }

    public ParameterBuilder asParams() {
        ParameterBuilder builder = ParameterBuilder.createBuilder();
        if (tradeType != TradeType.HOLD) {
            builder.addRate(rate).addAmount(amount).addOrderType(tradeType.toOrderType());
        }
        return builder;
    }

}

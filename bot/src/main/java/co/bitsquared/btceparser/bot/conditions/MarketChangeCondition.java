package co.bitsquared.btceparser.bot.conditions;

import co.bitsquared.btceparser.bot.data.Comparison;
import co.bitsquared.btceparser.core.data.CoinTicker;

/**
 * MarketChangeCondition is a part of the MarketChangeAlgorithm logic. The idea is to check the current (last) price in
 * comparison to a rate defined.
 *
 * @see co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm
 */
public class MarketChangeCondition extends Condition<CoinTicker> {

    private final double RATE;
    private final Comparison COMPARISON;

    public MarketChangeCondition(double rate, Comparison comparison) {
        RATE = rate;
        COMPARISON = comparison;
    }

    @Override
    public boolean isValid(CoinTicker object) {
        return COMPARISON.compare(object.getLastPrice(), RATE);
    }

    protected Comparison getComparison() {
        return COMPARISON;
    }

    protected double getRate() {
        return RATE;
    }

}

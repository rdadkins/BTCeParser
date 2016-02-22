package co.bitsquared.btceparser.bot.conditions;

import co.bitsquared.btceparser.bot.data.PriceComparison;
import co.bitsquared.btceparser.core.data.CoinTicker;

/**
 * MarketChangeCondition is a part of the MarketChangeAlgorithm logic. The idea is to check the current (last) price in
 * comparison to a rate defined.
 *
 * @see co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm
 */
public class MarketChangeCondition extends Condition<CoinTicker> {

    private final double RATE;
    private final PriceComparison COMPARISON;

    public MarketChangeCondition(double rate, PriceComparison comparison) {
        RATE = rate;
        COMPARISON = comparison;
    }

    @Override
    public boolean isValid(CoinTicker object) {
        if (COMPARISON == PriceComparison.GREATER_THAN) {
            return object.getLastPrice() >= RATE;
        } else {
            return object.getLastPrice() <= RATE;
        }
    }

}

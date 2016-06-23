package co.bitsquared.btceparser.bot.conditions;

import co.bitsquared.btceparser.bot.data.Comparison;
import co.bitsquared.btceparser.core.data.CoinTicker;

/**
 * PercentChangeCondition is an extension of MarketChangeCondition which is a part of MarketChangeAlgorithm logic. Percent
 * changes work in the logic of checking the last price and taking the percentage change with respect to a base price provided.
 *
 * Ex: Base rate = 500, Comparison = GREATER_THAN, Percent change = 2%
 *      The price needs to reach around 510.21 for this condition to be satisfied
 *          (510.21 - 500) / 500 = 2.042%. Compare 2.042 is greater than / equal to 2% = true.
 *
 *
 * @see co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm
 * @see co.bitsquared.btceparser.bot.conditions.MarketChangeCondition
 */
public class PercentChangeCondition extends MarketChangeCondition {

    private final double PERCENTAGE;

    /**
     * Creates a PercentChangeCondition based on a fixed rate, a comparison on percentages, and a percentage mark.
     * @param baseRate - the fixed rate in which the last price is compared to for a percentage.
     * @param comparison - how percentages are compared.
     * @param percentage - the whole value of a percentage (2% and not 0.02).
     */
    public PercentChangeCondition(double baseRate, Comparison comparison, double percentage) {
        super(baseRate, comparison);
        this.PERCENTAGE = percentage;
    }

    @Override
    public boolean isValid(CoinTicker coinTicker) {
        double rate = getRate();
        double percentChange = (coinTicker.getLastPrice() - rate) / rate;
        return getComparison().compare(percentChange * 100, PERCENTAGE);
    }

}

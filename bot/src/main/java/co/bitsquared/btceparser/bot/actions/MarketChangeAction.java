package co.bitsquared.btceparser.bot.actions;

import co.bitsquared.btceparser.bot.conditions.MarketChangeCondition;
import co.bitsquared.btceparser.bot.data.PreOrder;
import co.bitsquared.btceparser.core.data.CoinTicker;

/**
 * MarketChangeAction is a part of the MarketChangeAlgorithm logic. This class just serves as a middle man between the
 * algorithm and the condition.
 *
 * @see co.bitsquared.btceparser.bot.algorithms.MarketChangeAlgorithm
 * @see co.bitsquared.btceparser.bot.conditions.MarketChangeCondition
 */
public class MarketChangeAction extends BaseAction<CoinTicker> {

    private final MarketChangeCondition CONDITION;

    public MarketChangeAction(PreOrder preOrder, MarketChangeCondition condition) {
        super(preOrder);
        CONDITION = condition;
    }

    @Override
    public boolean conditionsAreMet(CoinTicker object) {
        return CONDITION.isValid(object);
    }

}

package co.bitsquared.btceparser.bot.actions;

import co.bitsquared.btceparser.bot.data.PreOrder;

/**
 * A BaseAction is defined by a certain type that is obtained from public API data. BaseAction has a PreOrder defined in
 * case if the conditionsAreMet().
 *
 * @see co.bitsquared.btceparser.core.data
 */
public abstract class BaseAction<T> {

    private final PreOrder PRE_ORDER;

    public BaseAction(PreOrder preOrder) {
        PRE_ORDER = preOrder;
    }

    public abstract boolean conditionsAreMet(T object);

    public final PreOrder getPreOrder() {
        return PRE_ORDER;
    }

}

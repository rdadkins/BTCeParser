package co.bitsquared.btceparser.bot.conditions;

/**
 * Each condition is defined by a type that is typically pulled from the public API.
 * @param <T> the public API data type to work with.
 *
 * @see co.bitsquared.btceparser.core.data
 */
public abstract class Condition<T> {

    public abstract boolean isValid(T object);

}

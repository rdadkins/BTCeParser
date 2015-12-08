package com.fatsoapps.btceparser.core.currency;

public interface BaseCurrency<T> {

    /**
     * Add amount to this currency and return the new currency.
     * @param amount the amount to be added.
     * @return the new BaseCurrency object.
     */
    T add(T amount);

    /**
     * Subtract amount from this currency and return the new currency.
     * @param amount the amount to be subtracted.
     * @return the new BaseCurrency object.
     */
    T subtract(T amount);

    /**
     * Multiple this by amount and return the new currency.
     * @param amount the amount to be multiplied by.
     * @return the new BaseCurrency object.
     */
    T multiply(T amount);

    /**
     * Multiple this by amount and return the new currency.
     * @param amount the arbitrary currency amount to be multiplied by (1.30 for USD, 0.098 for coins).
     * @return the new BaseCurrency object
     */
    T multiply(double amount);

    /**
     * Multiply this currency by another BaseCurrency and return the other currency object.
     * @param other the opposing BaseCurrency to multiply by.
     * @return the new BaseCurrency in form of U.
     */
    <U extends BaseCurrency<U>> U multiply(U other);

    /**
     * Divide this by amount and return the new currency.
     * @param amount the amount to be divided by.
     * @return the new BaseCurrency object.
     */
    T divide(T amount);

    /**
     * Divide this by amount and return the new currency.
     * @param amount the amount to be divided by.
     * @return the new BaseCurrency object
     */
    T divide(double amount);

    /**
     * Divide this currency by another BaseCurrency and return the other currency object.
     * @param other the opposing BaseCurrency to divide by.
     * @return the new baseCurrency in form of U.
     */
    <U extends BaseCurrency<U>> U divide(U other);

    /**
     * Convert this to a double for math operations.
     * @return the amount of this as a double.
     */
    double asDouble();

    /**
     * Convert this to a long for math operations. Typically used to avoid rounding errors when multiplying / dividing.
     * @return the smallest divisor of this currency (penny for USD, satoshi for coins).
     */
    long asSmallestDivisor();

}

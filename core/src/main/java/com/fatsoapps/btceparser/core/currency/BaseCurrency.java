package com.fatsoapps.btceparser.core.currency;

import java.math.BigDecimal;

public interface BaseCurrency<T> {

    /**
     * Add other to this currency and return the new currency.
     * @param other the other to be added.
     * @return the new BaseCurrency object.
     */
    T add(T other);

    /**
     * Subtract other from this currency and return the new currency.
     * @param other the other to be subtracted.
     * @return the new BaseCurrency object.
     */
    T subtract(T other);

    /**
     * Multiply this by other and return the new currency.
     * @param other the other to be multiplied by.
     * @return the new BaseCurrency object.
     */
    T multiply(T other);

    /**
     * Multiply this by amount and return the new currency.
     * @param amount the arbitrary currency amount to be multiplied by (1.30 for USD, 0.098 for coins).
     * @return the new BaseCurrency object
     */
    T multiply(double amount);

    /**
     * Multiply this by amount and return the new currency.
     * @param amount the currency object represented as a BigDecimal.
     * @return the new BaseCurrency object.
     */
    T multiply(BigDecimal amount);

    /**
     * Multiply this currency by another BaseCurrency and return the other currency object.
     * @param other the opposing BaseCurrency to multiply by.
     * @return the new BaseCurrency from the product.
     */
    BaseCurrency<?> multiply(BaseCurrency<?> other);

    /**
     * Divide this by other and return the new currency.
     * @param other the other to be divided by.
     * @return the new BaseCurrency object.
     */
    T divide(T other);

    /**
     * Divide this by amount and return the new currency.
     * @param amount the amount to be divided by.
     * @return the new BaseCurrency object
     */
    T divide(double amount);

    /**
     * Divide this by amount and return the new currency.
     * @param amount the amount to be divided by.
     * @return the new BaseCurrency object.
     */
    T divide(BigDecimal amount);

    /**
     * Divide this currency by another BaseCurrency and return the other currency object.
     * @param other the opposing BaseCurrency to divide by.
     * @return the new BaseCurrency from the dividend.
     */
    BaseCurrency<?> divide(BaseCurrency<?> other);

    /**
     * Convert this to a double for math operations.
     * @return the amount of this as a double.
     */
    BigDecimal asBigDecimal();

    /**
     * Convert this to a long for math operations. Typically used to avoid rounding errors when multiplying / dividing.
     * @return the smallest divisor of this currency (penny for USD, satoshi for coins).
     */
    long asSmallestDivisor();

}

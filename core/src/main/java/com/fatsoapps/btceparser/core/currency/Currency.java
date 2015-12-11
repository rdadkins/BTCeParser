package com.fatsoapps.btceparser.core.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Currency implements BaseCurrency<Currency> {

	private final long currency;
	private static long CENTS_PER_CURRENCY = 1000;

    public Currency(double currency){
        this((long) Math.floor(currency * CENTS_PER_CURRENCY));
    }

	public Currency(long smallestDivisor){
        this.currency = smallestDivisor;
	}

    public Currency add(Currency other) {
        return new Currency(asSmallestDivisor() + other.asSmallestDivisor());
    }

    public Currency subtract(Currency other) {
        return new Currency(asSmallestDivisor() - other.asSmallestDivisor());
    }

    public Currency multiply(Currency other) {
        return new Currency(asSmallestDivisor() * other.asSmallestDivisor());
    }

    public Currency multiply(double amount) {
        return multiply(BigDecimal.valueOf(amount));
    }

    public Currency multiply(BigDecimal amount) {
        return new Currency(asBigDecimal().multiply(amount).doubleValue());
    }

    /**
     * Any BaseCurrency<?> multiplied by Currency will return a Currency object. Coin * Currency != Coin.
     * @param other the opposing BaseCurrency to multiply by.
     * @return a new Currency based off of the product.
     */
    public Currency multiply(BaseCurrency<?> other) {
        return multiply(other.asBigDecimal());
    }

    public Currency divide(Currency other) {
        return divide(other.asBigDecimal());
    }

    public Currency divide(double amount) {
        return divide(BigDecimal.valueOf(amount));
    }

    public Currency divide(BigDecimal amount) {
        return new Currency(asBigDecimal().divide(amount, RoundingMode.HALF_DOWN).doubleValue());
    }

    public Currency divide(BaseCurrency<?> other) {
        return divide(other.asBigDecimal());
    }

    public BigDecimal asBigDecimal() {
        return new BigDecimal(1.0 * currency / CENTS_PER_CURRENCY).setScale(3, RoundingMode.HALF_DOWN);
    }

    public long asSmallestDivisor() {
        return currency;
    }
    
}

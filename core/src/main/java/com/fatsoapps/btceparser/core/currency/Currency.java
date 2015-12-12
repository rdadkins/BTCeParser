package com.fatsoapps.btceparser.core.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Currency implements BaseCurrency<Currency> {

    private BigDecimal value;
    private static RoundingMode roundingMode = RoundingMode.HALF_EVEN;
    private int decimalPlaces = 3;

    public Currency(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Currency(BigDecimal decimal) {
        value = decimal.setScale(decimalPlaces, roundingMode);
    }

    public Currency add(Currency other) {
        return new Currency(asBigDecimal().add(other.asBigDecimal()));
    }

    public BaseCurrency<?> add(BaseCurrency<?> other) {
        if (other instanceof Currency) {
            return add((Currency) other);
        }
        return new Currency(asBigDecimal().add(other.asBigDecimal()));
    }

    public Currency subtract(Currency other) {
        return new Currency(asBigDecimal().subtract(other.asBigDecimal()));
    }

    public BaseCurrency<?> subtract(BaseCurrency<?> other) {
        if (other instanceof Currency) {
            return subtract((Currency) other);
        }
        return new Currency(asBigDecimal().subtract(other.asBigDecimal()));
    }

    public Currency multiply(Currency other) {
        return new Currency(asBigDecimal().multiply(other.asBigDecimal()));
    }

    /**
     * Anything multiplied by Currency should return the product in the form of Currency.
     */
    public Currency multiply(BaseCurrency<?> other) {
        return multiply(other.asBigDecimal());
    }

    /**
     * Anything multiplied by Currency should return the product in the form of Currency.
     */
    public Currency multiply(BigDecimal value) {
        return new Currency(asBigDecimal().multiply(value));
    }

    public Currency divide(Currency other) {
        return divide(other.asBigDecimal());
    }

    /**
     * Anything that we divide Currency by should return a new Currency object.
     * $500.00 / $250.00 = $2.00
     * $500.00 / 1.25 BTC = $400.00 / BTC
     */
    public Currency divide(BaseCurrency<?> other) {
        return divide(other.asBigDecimal());
    }

    public Currency divide(BigDecimal value) {
        return new Currency(asBigDecimal().divide(value, roundingMode));
    }

    public BigDecimal asBigDecimal() {
        return value;
    }

    public Currency setDecimalPlaces(int decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
        return new Currency(asBigDecimal());
    }


}

package co.bitsquared.btceparser.core.currency;

import java.math.BigDecimal;

public final class Currency implements BaseCurrency<Currency> {

    private final static int DECIMAL_PLACES = 3;

    private final BigDecimal value;

    public Currency(double value) {
        this(BigDecimal.valueOf(value));
    }

    public Currency(BigDecimal decimal) {
        this(decimal, DECIMAL_PLACES);
    }

    protected Currency(BigDecimal decimal, int decimalPlaces) {
        value = decimal.setScale(decimalPlaces, ROUNDING_MODE);
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
        return new Currency(asBigDecimal().divide(value, ROUNDING_MODE));
    }

    public BigDecimal asBigDecimal() {
        return value;
    }

    public Currency setDecimalPlaces(int decimalPlaces) {
        return new Currency(asBigDecimal(), decimalPlaces);
    }

    public boolean isSamePrice(BaseCurrency<?> other) {
        return other instanceof Currency && asBigDecimal().compareTo(other.asBigDecimal()) == 0;
    }

    public boolean isAmountPositive() {
        return value.compareTo(BigDecimal.ZERO) == 1;
    }

    @Override
    public String toString() {
        return asBigDecimal().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return value != null ? value.equals(currency.value) : currency.value == null;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + DECIMAL_PLACES;
        return result;
    }

}

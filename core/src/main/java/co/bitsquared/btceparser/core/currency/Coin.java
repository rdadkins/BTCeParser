package co.bitsquared.btceparser.core.currency;

import java.math.BigDecimal;

public final class Coin implements BaseCurrency<Coin> {

	private static final int DECIMAL_PLACES = 8;
	private static final int SATOSHIS_PER_COIN = 100000000;

	private final BigDecimal value;

	private Coin(BigDecimal value) {
		this.value = value.setScale(DECIMAL_PLACES, ROUNDING_MODE);
	}

	public static Coin fromSatoshis(long satoshis) {
		return new Coin(BigDecimal.valueOf(1.0 * satoshis / SATOSHIS_PER_COIN));
	}

	public static Coin fromDouble(double coins) {
		return new Coin(BigDecimal.valueOf(coins));
	}

	public Coin add(Coin other) {
		return new Coin(asBigDecimal().add(other.asBigDecimal()));
	}

	public BaseCurrency<?> add(BaseCurrency<?> other) {
		if (other instanceof Coin) {
			return add((Coin) other);
		}
		return this;
	}

	public Coin subtract(Coin other) {
		return new Coin(asBigDecimal().subtract(other.asBigDecimal()));
	}

	public BaseCurrency<?> subtract(BaseCurrency<?> other) {
		if (other instanceof Coin) {
			return subtract((Coin) other);
		}
		return this;
	}

	public Coin multiply(Coin other) {
		return new Coin(asBigDecimal().multiply(other.asBigDecimal()));
	}

	/**
	 * If other is a Coin, multiply them together. If other is Currency, return a new Currency object.
	 */
	public BaseCurrency<?> multiply(BaseCurrency<?> other) {
		if (other instanceof Coin) {
			return multiply((Coin) other);
		} else if (other instanceof Currency) {
			return (Currency) other.multiply(asBigDecimal());
		}
		return (BaseCurrency<?>) other.multiply(asBigDecimal());
	}

	public Coin multiply(BigDecimal value) {
		return new Coin(asBigDecimal().multiply(value));
	}

	public Coin divide(Coin other) {
		return divide(other.asBigDecimal());
	}

	/**
	 * 1 BTC / 0.005 LTC = 200 BTC / LTC
	 * 1 BTC / $100.00 = 0.01 Dollar / BTC
	 */
	public BaseCurrency<?> divide(BaseCurrency<?> other) {
		if (other instanceof Coin) {
			return divide(other.asBigDecimal());
		} else if (other instanceof Currency) {
			return new Currency(divide(other.asBigDecimal()).asBigDecimal(), DECIMAL_PLACES);
		}
		return this;
	}

	public Coin divide(BigDecimal value) {
		return new Coin(asBigDecimal().divide(value, ROUNDING_MODE));
	}

	public BigDecimal asBigDecimal() {
		return value;
	}

    public Coin setDecimalPlaces(int decimalPlaces) {
        return this;
    }

	public boolean isSamePrice(BaseCurrency<?> other) {
		return other instanceof Coin && asBigDecimal().compareTo(other.asBigDecimal()) == 0;
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
		Coin coin = (Coin) o;
		return value != null ? value.equals(coin.value) : coin.value == null;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}

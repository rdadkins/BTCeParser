package com.fatsoapps.btceparser.core.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Coin is an extension of BaseCurrency that is a placeholder for crypto currencies. The max value of Coin can be
 * 2^63 - 1 (9,223,372,036,854,775,807 satoshis <->  92,233,720,368.54775807 Coins).
 */
public final class Coin implements BaseCurrency<Coin>, Comparable<Coin> {

	private final long satoshis;
	public final static int SATOSHI_PER_COIN = 100000000;
    private static final RoundingMode roundingMode = RoundingMode.HALF_DOWN;

	private Coin(long satoshis) {
		this.satoshis = satoshis;
	}

	private Coin(double coins){
		this.satoshis = (long) (coins * SATOSHI_PER_COIN);
	}

	public static Coin fromSatoshis(long satoshis) {
		return new Coin(satoshis);
	}

	public static Coin fromDouble(double amountOfCoins) {
		return new Coin(amountOfCoins);
	}

	public Coin add(Coin other) {
		return new Coin(asSmallestDivisor() + other.asSmallestDivisor());
	}

	public Coin subtract(Coin other) {
		return new Coin(asSmallestDivisor() - other.asSmallestDivisor());
	}

	public Coin multiply(Coin other) {
		return new Coin(asSmallestDivisor() * other.asSmallestDivisor());
	}

	public Coin multiply(double amount) {
		return new Coin(asBigDecimal().doubleValue() * amount);
	}

    public Coin multiply(BigDecimal amount) {
        return new Coin(asBigDecimal().multiply(amount).doubleValue());
    }

    /**
     * Multiply this by the other BaseCurrency.
     * @param other the opposing BaseCurrency to multiply by.
     * @return type depends on what other is. If it is Currency you should expect Currency. Otherwise it will be coin.
     */
    public BaseCurrency<?> multiply(BaseCurrency<?> other) {
		return other.multiply(this);
	}

	public Coin divide(Coin other) {
		return new Coin(asSmallestDivisor() / other.asSmallestDivisor());
	}

	public Coin divide(double amount) {
        return divide(BigDecimal.valueOf(amount));
	}

    public Coin divide(BigDecimal amount) {
        return new Coin(asBigDecimal().divide(amount, roundingMode).doubleValue());
    }

    /**
     * Divide this by the other BaseCurrency.
     * @param other the opposing BaseCurrency to divide by.
     * @return type depends on what other is. If it is Currency you should expect Currency. Otherwise it will be Coin.
     */
    public BaseCurrency<?> divide(BaseCurrency<?> other) {
        return other.divide(this);
    }

	public BigDecimal asBigDecimal() {
        return new BigDecimal(1.0 * asSmallestDivisor() / SATOSHI_PER_COIN).setScale(8, roundingMode);
	}

	public long asSmallestDivisor() {
		return satoshis;
	}

	public int compareTo(Coin other) {
        return asBigDecimal().compareTo(other.asBigDecimal());
	}

	public int hashCode() {
		long hash = satoshis;
		while (hash >= Integer.MAX_VALUE) {
			hash = satoshis % 17;
		}
		return (int) hash;
	}

}

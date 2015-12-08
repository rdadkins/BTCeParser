package com.fatsoapps.btceparser.core.currency;

public final class Coin implements BaseCurrency<Coin>, Comparable<Coin> {

	private final long satoshis;
	public final static long SATOSHI_PER_COIN = 100000000L;

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
		return new Coin(asDouble() * amount);
	}

	public <U extends BaseCurrency<U>> U multiply(U other) {
		return other.multiply(asDouble());
	}

	public Coin divide(Coin other) {
		return new Coin(asSmallestDivisor() / other.asSmallestDivisor());
	}

	public Coin divide(double amount) {
		return new Coin(asDouble() / amount);
	}

	public <U extends BaseCurrency<U>> U divide(U other) {
		return other.divide(asDouble());
	}

	public double asDouble(){
		return (asSmallestDivisor() / (1.0 * SATOSHI_PER_COIN));
	}

	public long asSmallestDivisor() {
		return satoshis;
	}

	public int compareTo(Coin other) {
		return Double.compare(this.asDouble(), other.asDouble());
	}

	public int hashCode() {
		long hash = satoshis;
		while (hash >= Integer.MAX_VALUE) {
			hash = satoshis % 17;
		}
		return (int) hash;
	}

}

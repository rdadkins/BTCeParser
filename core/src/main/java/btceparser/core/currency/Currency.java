package btceparser.core.currency;

public class Currency {

	private long whole;
	private long remainder;
	private static long CENTS_PER_CURRENCY = 1000;
	private static int decimalPlaces = 3;
	
	public Currency(){
		this(0,0);
	}
	
	public Currency(long remainder){
		this.remainder = remainder;
		adjustValues();
	}
	
	public Currency(double currency){
		this((long) Math.floor(currency * CENTS_PER_CURRENCY));
	}
	
	public Currency(long whole, long remainder) {
		this.whole = whole;
		this.remainder = remainder;
		adjustValues();
	}
	
	public Currency add(Currency wholeObject){
		return this.add(wholeObject.whole, wholeObject.remainder);
	}
	
	public Currency add(long whole, long remainder){
		this.whole += whole;
		this.remainder += remainder;
		adjustValues();
		return new Currency(this.whole, this.remainder);
	}
	
	public Currency subtract(Currency wholeObject){
		return subtract(wholeObject.whole, wholeObject.remainder);
	}
	
	public Currency subtract(long whole, long remainder){
		this.whole -= whole;
		this.remainder -= remainder;
		adjustValues();
		return new Currency(this.whole, this.remainder);
	}
	
	public Currency multiply(double wholeAmount){
		double currencyMultiplied = toDouble() * wholeAmount;
		return new Currency(currencyMultiplied);
	}
	
	private void adjustValues() {
		while (remainder >= CENTS_PER_CURRENCY) {
			whole++;
			remainder -= CENTS_PER_CURRENCY;
		}
		while (remainder < 0) {
			whole--;
			remainder += CENTS_PER_CURRENCY;
		}
	}
	
	public long getRemainderValue(){
		return CENTS_PER_CURRENCY;
	}

	public long getWholeValue(){
		return whole;
	}
	
	public long getRemainder(){
		return remainder;
	}
	
	private long getTotal(Currency currency){
		long otherTotal = currency.getWholeValue() * CENTS_PER_CURRENCY;
		otherTotal += currency.getRemainder();
		return otherTotal;
	}
	
	public boolean isGreaterThan(Currency comparable){
		return this.getTotal(this) > getTotal(comparable);
	}
	
	public boolean equalsValue(Currency comparable){
		return (this.toString().compareTo(comparable.toString()) == 0);
	}
	
	public void setDecimalPlaces(int places){
		if(places > 3){
			decimalPlaces = places;
			CENTS_PER_CURRENCY = (long) Math.pow(10, places);
		}
	}
	
	public String toString(){
		return formatDouble();
	}
	
	public double toDouble(){
		return Double.parseDouble(formatDouble());
	}
	
	private String formatDouble(){
		String dollars = String.valueOf(whole);
		String cents = String.format("%0" + decimalPlaces + "d", this.remainder);
		return dollars + "." + cents;
	}
	
}

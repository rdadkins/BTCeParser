package btceparser.core.data;

import btceparser.core.currency.Coin;
import btceparser.core.currency.Currency;
import btceparser.core.requests.DepthType;
import btceparser.hash.SHA256;

/**
 * Order is a wrapper class around a BID / ASK within the API. Each order must define the base currency (Paying USD) and
 * the coin amount (for this coin type).
 */
public class Order {

	private Currency price;
	private Coin coins;
	private DepthType type;
	private String hash;
	
	public Order(Currency dollar, Coin coins, DepthType type){
		this.price = dollar;
		this.coins = coins;
		this.type = type;
		hashOrder();
	}
	
	private void hashOrder() {
		hash = SHA256.getHash(System.currentTimeMillis() + price.toDouble() + "order" + coins.asSatoshi() + "order" + type.name() + "order" + price.getRemainderValue()).substring(0,32);
	}

	public Currency getCurrencyObject(){
		return price;
	}
	
	public Coin getCoins(){
		return coins;
	}
	
	public double getPrice(){
		return price.toDouble();
	}
	
	public Currency getDollarTotal(){
		return price.multiply(coins.asCoin());
	}
	
	public DepthType getOrderType(){
		return type;
	}
	
	public String getHash(){
		return hash;
	}
	
	public boolean compareOrders(Order order) {
		return hash.compareTo(order.hash) == 0;
	}
	
	public String toString(){
		return "[" + type + "] " + coins.asCoin() + " @ " + price + " = " + getDollarTotal();
	}
	
}

package btceparser.core.currency;

public class Coin {

	private long satoshis;
	public static final long SATOSHI_PER_COIN = 100000000L;
	public static final long SATOSHI = 1;
	
	public Coin(){
		this(0);
	}
	
	public Coin(long satoshis) {
		this.satoshis = satoshis;
	}
	
	public Coin(double coins){
		this.satoshis = (long) (coins * SATOSHI_PER_COIN);
	}
	
	public Coin addSatoshis(long satoshis){
		return new Coin(this.satoshis += satoshis);
	}
	
	public Coin addCoins(double coins){
		satoshis += (long) (coins * SATOSHI_PER_COIN);
		return new Coin(satoshis);
	}
	
	public Coin subtractSatoshis(long satoshis){
		return new Coin(this.satoshis -= satoshis);
	}
	
	public Coin subtractCoins(double coins){
		satoshis -= (long) (coins * SATOSHI_PER_COIN);
		return new Coin(satoshis);
	}
	
	public double asCoin(){
		return (asSatoshi() / (1.0 * SATOSHI_PER_COIN));
	}
	
	public long asSatoshi(){
		return satoshis;
	}
	
}

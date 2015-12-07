package btceparser.core;

public enum TradingPair {

	BTC_USD ("btc_usd"),
	LTC_USD ("ltc_usd"),
	LTC_BTC ("ltc_btc"),
	BTC_RUR ("btc_rur"),
	PPC_BTC ("ppc_btc"),
	USD_RUR ("usd_rur"),
	PPC_USD ("ppc_usd"),
	BTC_EUR ("btc_eur"),
	LTC_EUR ("ltc_eur"),
	NMC_BTC ("nmc_btc"),
	NMC_USD ("nmc_usd"),
	EUR_USD ("eur_usd"),
	NVC_BTC ("nvc_btc"),
	LTC_CNH ("ltc_cnh"),
	USD_CNH ("usd_cnh"),
	BTC_CNH ("btc_cnh"),
	EUR_RUR ("eur_rur"),
	CNC_BTC ("cnh_btc");

	private String pair;
	
	TradingPair(String pair){
		this.pair = pair;
	}
	
	@Override
	public String toString(){
		return pair.toLowerCase();
	}
	
}

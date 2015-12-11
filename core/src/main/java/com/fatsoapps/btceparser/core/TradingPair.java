package com.fatsoapps.btceparser.core;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.currency.Coin;
import com.fatsoapps.btceparser.core.currency.Currency;

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

	public BaseCurrency<?> getBidCurrency() {
		return getCurrencyType(0);
	}

	public BaseCurrency<?> getAskCurrency() {
		return getCurrencyType(1);
	}

	private BaseCurrency<?> getCurrencyType(int index) {
		String currencyType = pair.split("_")[index];
		if (currencyType.equals("usd") || currencyType.equals("eur") || currencyType.equals("cnh") || currencyType.equals("rur")) {
			return new Currency(0);
		}
		return Coin.fromSatoshis(0);
	}
	
	@Override
	public String toString(){
		return pair.toLowerCase();
	}

}

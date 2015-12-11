package com.fatsoapps.btceparser.core;

import com.fatsoapps.btceparser.core.currency.BaseCurrency;
import com.fatsoapps.btceparser.core.currency.Coin;
import com.fatsoapps.btceparser.core.currency.Currency;
import com.fatsoapps.btceparser.core.data.Order;
import com.fatsoapps.btceparser.core.requests.DepthType;

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

    public Order<? extends BaseCurrency<?>, ? extends BaseCurrency<?>> getOrderTemplate(double askValue, double bidValue, DepthType type) {
        BaseCurrency<?> askCurrency = (BaseCurrency<?>) getAskCurrency().multiply(askValue);
        BaseCurrency<?> bidCurrency = (BaseCurrency<?>) getBidCurrency().multiply(bidValue);
        if (bidCurrency instanceof Currency) {
            if (askCurrency instanceof Currency) {
                return new Order<Currency, Currency>((Currency) askCurrency, (Currency) bidCurrency, type);
            } else {
                return new Order<Coin, Currency>((Coin) askCurrency, (Currency) bidCurrency, type);
            }
        } else {
            if (askCurrency instanceof Currency) {
                return new Order<Currency, Coin>((Currency) askCurrency, (Coin) bidCurrency, type);
            } else {
                return new Order<Coin, Coin>((Coin) askCurrency, (Coin) bidCurrency, type);
            }
        }
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
			return new Currency(1.0);
		}
		return Coin.fromDouble(1.0);
	}
	
	@Override
	public String toString(){
		return pair.toLowerCase();
	}

}
